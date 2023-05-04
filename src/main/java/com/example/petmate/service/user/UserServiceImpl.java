package com.example.petmate.service.user;

import com.example.petmate.constant.ResponseCodes;
import com.example.petmate.constant.UserRole;
import com.example.petmate.dto.UserDto;
import com.example.petmate.entity.Pet;
import com.example.petmate.entity.User;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.mapper.UserMapper;
import com.example.petmate.model.request.*;
import com.example.petmate.model.response.AddEmployeeResponse;
import com.example.petmate.model.response.UserLoginResponse;
import com.example.petmate.model.response.UserRegisterResponse;
import com.example.petmate.repository.UserRepository;
import com.example.petmate.utils.JwtUtils;
import com.example.petmate.utils.StringUtils;
import com.example.petmate.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	private final JavaMailSender javaMailSender;

	public UserServiceImpl(UserRepository userRepository, JavaMailSender javaMailSender) {
		this.userRepository = userRepository;
		this.javaMailSender = javaMailSender;
	}

	@Value("${spring.mail.username}")
	private String fromMail;

	@Override
	public UserRegisterResponse userRegister(UserRegisterRequest request) throws ResponseException {
		log.info("userRegister...");
		try {
			User existed = userRepository.findByEmail(request.getEmailAddress());
			if (Objects.nonNull(existed)) {
				throw new ResponseException(ResponseCodes.PM_ERROR_ACCOUNT_ALREADY_EXISTS);
			}
			userRepository.save(UserMapper.toUserEntity(request));
		} catch (ResponseException e) {
			throw e;
		} catch (Exception e) {
			throw new ResponseException(ResponseCodes.PM_ERROR_REGISTER, e.toString());
		}
		return UserMapper.toUserRegisterResponse(request);
	}

	@Override
	public AddEmployeeResponse addEmployee(AddEmployeeRequest request) throws ResponseException {
		log.info("Add Employee...");
		try {
			log.info(request.getLastName());
			User existed = userRepository.findByEmail(request.getEmailAddress());
			if (Objects.nonNull(existed)) {
				throw new ResponseException(ResponseCodes.PM_ERROR_ACCOUNT_ALREADY_EXISTS);
			}
			userRepository.save(UserMapper.toEmployeeEntity(request));
		} catch (ResponseException e) {
			throw e;
		} catch (Exception e) {
			throw new ResponseException(ResponseCodes.PM_ERROR_REGISTER, e.toString());
		}
		return UserMapper.toAddEmployeeResponse(request);
	}


	@Override
	public UserLoginResponse userLogin(UserLoginRequest request) throws ResponseException {
		log.info("userLogin...");
		UserLoginResponse response = new UserLoginResponse();
		try {
			User entity = userRepository.findByEmail(request.getEmail());
			if (Objects.isNull(entity)) {
				throw new ResponseException(ResponseCodes.PM_ERROR_ACCOUNT_NOT_FOUND);
			}
			String password = StringUtils.getShaStringWithSalt(request.getPassword(), request.getEmail());
			if (!entity.getPassword().equals(password)) {
				throw new ResponseException(ResponseCodes.PM_ERROR_LOGIN);
			}
			response.setUserId(entity.getId().toString());
			response.setEmail(entity.getEmail());
			response.setJwt(JwtUtils.createJwt(entity.getId().toString(), entity.getRole().toString()));
		} catch (ResponseException e) {
			throw e;
		} catch (Exception e) {
			throw new ResponseException(ResponseCodes.PM_ERROR_LOGIN, e.toString());
		}
		return response;
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		return UserMapper.toDtoList(users);
	}

	@Override
	public void forgotPassword(String email) throws ResponseException {
		log.info("forgotPassword...");
		try {
			User existed = userRepository.findByEmail(email);
			if (Objects.isNull(existed)) {
				throw new ResponseException(ResponseCodes.PM_ERROR_ACCOUNT_NOT_FOUND);
			}
			sendEmail(email, "google.com/" ,existed.getFirstName(),existed.getLastName());
		} catch (ResponseException e) {
			throw e;
		} catch (Exception e) {
			throw new ResponseException(ResponseCodes.PM_ERROR_INTERNAL_SERVER, e.toString());
		}
	}

	@Override
	public boolean resetPassword(ResetPasswordRequest request) throws ResponseException {
		log.info("resetPassword...");
		try {

		} catch (ResponseException e) {
			throw e;
		} catch (Exception e) {
			throw new ResponseException(ResponseCodes.PM_ERROR_INTERNAL_SERVER, e.toString());
		}
		return false;
	}

	@Override
	public boolean updateEmployee(String id, UpdateEmployeeRequest request) throws ResponseException {
		Optional<User> user = userRepository.findById(UUID.fromString(id));
		if (user.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}

		user.get().setFirstName(request.getFirstName());
		user.get().setLastName(request.getLastName());
		user.get().setEmail(request.getEmailAddress());
		user.get().setDateOfBirth(TimeUtils.converToLocalDateTimeNoIso(request.getDateOfBirth()));
		userRepository.save(user.get());

		return true;
	}

	@Override
	public UserDto getEmployeeById(String id) {
		Optional<User> user = userRepository.findById(UUID.fromString(id));

		if (user.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}

		return UserMapper.toDto(user.get());
	}

	@Override
	public List<UserDto> getAllEmployee() {
		List<User> employees = userRepository.getAllEmployee();
		return UserMapper.toDtoList(employees);
	}

	@Override
	public boolean deleteEmployee(String id) {
		Optional<User> employee = userRepository.findById(UUID.fromString(id));

		if ((employee.get().getRole() == UserRole.EMPLOYEE) && employee.isPresent() ) {
			userRepository.deleteById(UUID.fromString(id));
		} else {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}

		return true;
	}

	private void sendEmail(String email, String url, String firstName, String lastName) throws MessagingException,
			UnsupportedEncodingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromMail, "PetMate Support");
		helper.setTo(email);

		String subject = "Password Reset Request for PetMate";

		String content = "<p>Dear " + firstName + " " + lastName + ",</p>"
				+ "<p>To reset your password, please click this link: [" + "<a href=\"" + url + "\">Reset Password Link</a>]</p>"
				+ "<p>If you did not request a password reset, please ignore this email.</p>"
				+ "<p>For security reasons, this link will expire in 24 hours. If you have any questions, please contact us at caominhtri112201@gmail.com.</p>"
				+ "<p>Thank you for being our valued customer.</p>"
				+ "<p>Best regards.</p>"
				+ "<p>PetMate team</p>";

		helper.setSubject(subject);
		helper.setText(content, true);

		javaMailSender.send(message);
	}
}
