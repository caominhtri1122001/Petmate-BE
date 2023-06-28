package com.example.petmate.service.sitter;

import com.example.petmate.constant.ResponseCodes;
import com.example.petmate.constant.UserRole;
import com.example.petmate.entity.Sitter;
import com.example.petmate.entity.User;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.mapper.SitterMapper;
import com.example.petmate.model.request.SitterRequest;
import com.example.petmate.model.response.LocationResponse;
import com.example.petmate.model.response.SitterInfoResponse;
import com.example.petmate.model.response.SitterResponse;
import com.example.petmate.repository.SitterRepository;
import com.example.petmate.repository.UserRepository;
import com.example.petmate.service.third_party.locationIQ.LocationIqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class SitterServiceImpl implements SitterService {

	private final SitterRepository sitterRepository;
	private final UserRepository userRepository;

	private final LocationIqService locationIqService;

	private final JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String fromMail;

	public SitterServiceImpl(SitterRepository sitterRepository, UserRepository userRepository,
							 LocationIqService locationIqService, JavaMailSender javaMailSender) {
		this.sitterRepository = sitterRepository;
		this.userRepository = userRepository;
		this.locationIqService = locationIqService;
		this.javaMailSender = javaMailSender;
	}

	@Override
	public SitterResponse createSitter(SitterRequest request) {
		log.info(request.getAddress());
		Optional<Sitter> sitter = sitterRepository.findByUserId(UUID.fromString(request.getUserId()));
		if (sitter.isPresent()) {
			throw new ResponseException(ResponseCodes.RT_ERROR_ALREADY_REQUESTED);
		}
		LocationResponse result = locationIqService.getTheLocation(
				request.getAddress() + " " + request.getDistrict() + " " + request.getCity());
		if (Objects.isNull(result)) {
			throw new ResponseException(ResponseCodes.RT_ERROR_WRONG_ADDRESS);
		}
		Optional<User> user = userRepository.findById(UUID.fromString(request.getUserId()));
		return SitterMapper.toResponse(sitterRepository.save(SitterMapper.toEntity(request,result)), user.get());
	}

	@Override
	public List<SitterInfoResponse> getListSitter(double lat, double lng) {
		List<SitterInfoResponse> result = new ArrayList<>();
		List<Sitter> sitters = sitterRepository.findAll();
		sitters.forEach(sitter -> {
			Optional<User> user = userRepository.findById(sitter.getUserId());
			if(user.get().getRole() == UserRole.EMPLOYEE) {
				double distance = calculateDistance(lat,lng,sitter.getLatitude(),sitter.getLongitude());
				if( distance < 20) {
					result.add(SitterMapper.toSitterInfoResponse(sitter, user.get(), distance));
				}
			}
		});
		return result;
	}

	private double calculateDistance(double userLat, double userLng, double sitterLat, double sitterLng) {
		final double earthRadius = 6371;
		double dLat = Math.toRadians(sitterLat - userLat);
		double dLng = Math.toRadians(sitterLng - userLng);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(sitterLat))
				* Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = earthRadius * c;
		return Double.parseDouble(String.format("%.2f", distance));
	}

	@Override
	public List<SitterInfoResponse> getListSitterRequest() {
		List<SitterInfoResponse> result = new ArrayList<>();
		List<Sitter> sitters = sitterRepository.findByStatus(false);
		sitters.forEach(sitter -> {
			Optional<User> user = userRepository.findById(sitter.getUserId());
			result.add(SitterMapper.toSitterInfoResponse(sitter, user.get(), 0));
		});
		return result;
	}

	@Override
	public SitterInfoResponse getSitterById(String sitterId) {
		Optional<Sitter> sitter = sitterRepository.findById(UUID.fromString(sitterId));
		if (sitter.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		Optional<User> user = userRepository.findById(sitter.get().getUserId());
		return SitterMapper.toSitterInfoResponse(sitter.get(), user.get(), 0);
	}

	@Override
	public SitterInfoResponse getSitterByUserId(String userId) {
		Optional<Sitter> sitter = sitterRepository.findByUserId(UUID.fromString(userId));
		if (sitter.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		Optional<User> user = userRepository.findById(sitter.get().getUserId());
		return SitterMapper.toSitterInfoResponse(sitter.get(), user.get(), 0);
	}

	@Override
	public boolean acceptRequestSitter(String id) throws MessagingException, UnsupportedEncodingException {
		Optional<Sitter> sitter = sitterRepository.findByUserId(UUID.fromString(id));
		if (sitter.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		sitter.get().setStatus(true);

		sitterRepository.save(sitter.get());
		Optional<User> user = userRepository.findById(sitter.get().getUserId());
		user.get().setRole(UserRole.EMPLOYEE);
		userRepository.save(user.get());
		sendEmail(user.get().getEmail(), user.get().getFirstName(), user.get().getLastName(), true);

		return true;
	}

	@Override
	public boolean deleteRequestSitter(String id) throws MessagingException, UnsupportedEncodingException {
		Optional<Sitter> sitter = sitterRepository.findByUserId(UUID.fromString(id));
		if (sitter.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		Optional<User> user = userRepository.findById(sitter.get().getUserId());
		sitterRepository.delete(sitter.get());
		sendEmail(user.get().getEmail(), user.get().getFirstName(), user.get().getLastName(), false);
		return true;
	}


	private void sendEmail(String email, String firstName, String lastName, boolean status)
			throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromMail, "PetMate Support");
		helper.setTo(email);

		String subject = "Announcement of Becoming a Sitter";
		String content = "<p>Dear " + firstName + " " + lastName + ",</p>"
				+ "<p>We are delighted to inform you that your application to become a sitter at PetMate has been accepted. Congratulations!</p>"
				+ "<p>We believe that your experience and passion for animal care make you an excellent fit for our team. </p>"
				+ "<p>Thank you for choosing PetMate as your preferred place to pursue your passion for pet care. We are excited to welcome you to our team!</p>" + "<p>Best regards.</p>" + "<p>PetMate team</p>";


		if (!status) {
			subject = "Regretful Decline of Your Sitter Application at PetMate :(";
			content = "<p>Dear " + firstName + " " + lastName + ",</p>"
					+ "<p>We hope this email finds you well. We appreciate your interest in becoming a sitter at PetMate and taking the time to apply for the position.</p>"
					+ "<p>After careful consideration and evaluation of all applicants, we regret to inform you that we are unable to proceed with your application at this time. </p>"
					+ "<p>We thank you for your understanding and wish you the very best in your future endeavors.</p>" + "<p>Best regards.</p>" + "<p>PetMate team</p>";
		}

		helper.setSubject(subject);
		helper.setText(content, true);

		javaMailSender.send(message);
	}
}
