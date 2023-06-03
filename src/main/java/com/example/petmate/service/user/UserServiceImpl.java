package com.example.petmate.service.user;

import com.example.petmate.constant.ResponseCodes;
import com.example.petmate.constant.UserRole;
import com.example.petmate.dto.UserDto;
import com.example.petmate.entity.User;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.mapper.UserMapper;
import com.example.petmate.model.request.*;
import com.example.petmate.model.response.AddAdminResponse;
import com.example.petmate.model.response.UserLoginResponse;
import com.example.petmate.repository.UserRepository;
import com.example.petmate.service.third_party.firebase.FirebaseStorageService;
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
import java.io.IOException;
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

	private final FirebaseStorageService firebaseStorageService;
	@Value("${spring.mail.username}")
	private String fromMail;

	public UserServiceImpl(UserRepository userRepository, JavaMailSender javaMailSender, FirebaseStorageService firebaseStorageService) {
		this.userRepository = userRepository;
		this.javaMailSender = javaMailSender;
		this.firebaseStorageService = firebaseStorageService;
	}

	@Override
	public UserLoginResponse userRegister(UserRegisterRequest request) throws ResponseException {
		log.info("userRegister...");
		UserLoginResponse response = new UserLoginResponse();

		try {
			User existed = userRepository.findByEmail(request.getEmailAddress());
			if (Objects.nonNull(existed)) {
				throw new ResponseException(ResponseCodes.PM_ERROR_ACCOUNT_ALREADY_EXISTS);
			}
			User entity = userRepository.save(UserMapper.toUserEntity(request));
			response.setUserId(entity.getId().toString());
			response.setName(entity.getFirstName());
			response.setEmail(entity.getEmail());
			response.setRole(entity.getRole().toString());
			response.setImage(entity.getUserImgUrl());
			response.setJwt(JwtUtils.createJwt(entity.getId().toString(), entity.getRole().toString()));
		} catch (ResponseException e) {
			throw e;
		} catch (Exception e) {
			throw new ResponseException(ResponseCodes.PM_ERROR_REGISTER, e.toString());
		}

		return response;
	}

	@Override
	public AddAdminResponse addAdmin(AddAdminRequest request) throws ResponseException {
		log.info("Add Admin...");
		try {
			log.info(request.getLastName());
			User existed = userRepository.findByEmail(request.getEmailAddress());
			if (Objects.nonNull(existed)) {
				throw new ResponseException(ResponseCodes.PM_ERROR_ACCOUNT_ALREADY_EXISTS);
			}
			userRepository.save(UserMapper.toAdminEntity(request));
		} catch (ResponseException e) {
			throw e;
		} catch (Exception e) {
			throw new ResponseException(ResponseCodes.PM_ERROR_REGISTER, e.toString());
		}
		return UserMapper.toAddAdminResponse(request);
	}

	@Override
	public boolean updateAdmin(String id, UpdateAdminRequest request) throws ResponseException, IOException {
		String image = "";
		if (request.getImage() != null) {
			image = firebaseStorageService.uploadImage(request.getImage());
		}
		Optional<User> admin = userRepository.findById(UUID.fromString(id));

		if ((admin.get().getRole() == UserRole.ADMIN) && admin.isPresent()) {
			admin.get().setFirstName(request.getFirstName());
			admin.get().setLastName(request.getLastName());
			admin.get().setEmail(request.getEmailAddress());
			admin.get().setDateOfBirth(TimeUtils.converToLocalDateTimeNoIso(request.getDateOfBirth()));
			admin.get().setGender(request.isGender());
			admin.get().setPhone(request.getPhone());
			if (image != "") {
				admin.get().setUserImgUrl(image);
			}
			userRepository.save(admin.get());
		} else {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}

		return true;
	}

	@Override
	public boolean deleteAdmin(String id) {
		Optional<User> admin = userRepository.findById(UUID.fromString(id));

		if ((admin.get().getRole() == UserRole.ADMIN) && admin.isPresent()) {
			userRepository.deleteById(UUID.fromString(id));
		} else {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}

		return true;
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
			response.setName(entity.getFirstName());
			response.setEmail(entity.getEmail());
			response.setRole(entity.getRole().toString());
			response.setImage(entity.getUserImgUrl());
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
	public UserDto getUserById(String id) {
		Optional<User> user = userRepository.findById(UUID.fromString(id));

		if (user.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}

		return UserMapper.toDto(user.get());
	}

	@Override
	public boolean updateProfileUser(String id, UpdateProfileUserRequest request) throws ResponseException, IOException {
		String image = "";
		if (request.getImage() != null) {
			image = firebaseStorageService.uploadImage(request.getImage());
		}
		Optional<User> user = userRepository.findById(UUID.fromString(id));

		if (user.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}

		user.get().setFirstName(request.getFirstName());
		user.get().setLastName(request.getLastName());
		user.get().setEmail(request.getEmailAddress());
		user.get().setDateOfBirth(TimeUtils.converToLocalDateTimeNoIso(request.getDateOfBirth()));
		user.get().setGender(request.isGender());
		user.get().setPhone(request.getPhone());
		if (image != "") {
			user.get().setUserImgUrl(image);
		}
		userRepository.save(user.get());

		return true;
	}

	@Override
	public boolean changePassword(String id, ChangePasswordRequest request) {
		try {
			Optional<User> user = userRepository.findById(UUID.fromString(id));
			if (user.isEmpty()) {
				throw new ResponseException(ResponseCodes.PM_ERROR_ACCOUNT_NOT_FOUND);
			}
			String password = StringUtils.getShaStringWithSalt(request.getOldPassword(), user.get().getEmail());
			if (!user.get().getPassword().equals(password)) {
				throw new ResponseException(ResponseCodes.PM_WRONG_OLD_PASSWORD);
			}

			user.get().setPassword(StringUtils.getShaStringWithSalt(request.getPassword(),
					user.get().getEmail()));
			userRepository.save(user.get());
		} catch (ResponseException e) {
			throw e;
		} catch (Exception e) {
			throw new ResponseException(ResponseCodes.PM_ERROR_INTERNAL_SERVER, e.toString());
		}
		return true;
	}

	@Override
	public void forgotPassword(String email) throws ResponseException {
		log.info("forgotPassword...");
		try {
			User existed = userRepository.findByEmail(email);
			if (Objects.isNull(existed)) {
				throw new ResponseException(ResponseCodes.PM_ERROR_ACCOUNT_NOT_FOUND);
			}
			sendEmail(email, "google.com/", existed.getFirstName(), existed.getLastName());
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
	public boolean updateEmployee(String id, UpdateEmployeeRequest request) throws ResponseException, IOException {
		String image = "";
		if (request.getImage() != null) {
			image = firebaseStorageService.uploadImage(request.getImage());
		}
		Optional<User> user = userRepository.findById(UUID.fromString(id));
		log.info(request.getFirstName());
		if (user.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}

		user.get().setFirstName(request.getFirstName());
		user.get().setLastName(request.getLastName());
		user.get().setEmail(request.getEmailAddress());
		user.get().setDateOfBirth(TimeUtils.converToLocalDateTimeNoIso(request.getDateOfBirth()));
		user.get().setGender(request.isGender());
		user.get().setPhone(request.getPhone());
		if (image != "") {
			user.get().setUserImgUrl(image);
		}
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

		if ((employee.get().getRole() == UserRole.EMPLOYEE) && employee.isPresent()) {
			userRepository.deleteById(UUID.fromString(id));
		} else {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}

		return true;
	}

	@Override
	public List<UserDto> getAllCustomer() {
		List<User> customers = userRepository.getAllCustomer();
		return UserMapper.toDtoList(customers);
	}

	@Override
	public UserDto getCustomerById(String id) {
		Optional<User> customer = userRepository.findById(UUID.fromString(id));

		if ((customer.get().getRole() == UserRole.CUSTOMER) && customer.isPresent()) {
			return UserMapper.toDto(customer.get());
		} else {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
	}

	@Override
	public boolean updateCustomer(String id, UpdateCustomerRequest request) throws ResponseException, IOException {
		String image = "";
		if (request.getImage() != null) {
			image = firebaseStorageService.uploadImage(request.getImage());
		}

		Optional<User> customer = userRepository.findById(UUID.fromString(id));

		if ((customer.get().getRole() == UserRole.CUSTOMER) && customer.isPresent()) {
			customer.get().setFirstName(request.getFirstName());
			customer.get().setLastName(request.getLastName());
			customer.get().setEmail(request.getEmailAddress());
			customer.get().setDateOfBirth(TimeUtils.converToLocalDateTimeNoIso(request.getDateOfBirth()));
			customer.get().setGender(request.isGender());
			customer.get().setPhone(request.getPhone());
			if (image != "") {
				customer.get().setUserImgUrl(image);
			}
			userRepository.save(customer.get());
		} else {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}

		return true;
	}

	@Override
	public boolean deleteCustomer(String id) {
		Optional<User> customer = userRepository.findById(UUID.fromString(id));

		if ((customer.get().getRole() == UserRole.CUSTOMER) && customer.isPresent()) {
			userRepository.deleteById(UUID.fromString(id));
		} else {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}

		return true;
	}

	@Override
	public boolean resetPasswordToDefault(String id) {
		Optional<User> account = userRepository.findById(UUID.fromString(id));

		account.get().setPassword(StringUtils.getShaStringWithSalt("123456",
				account.get().getEmail()));
		userRepository.save(account.get());
		return true;
	}

	private void sendEmail(String email, String url, String firstName, String lastName)
			throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromMail, "PetMate Support");
		helper.setTo(email);

		String subject = "Password Reset Request for PetMate";

		String content = "<p>Dear " + firstName + " " + lastName + ",</p>"
				+ "<p>To reset your password, please click this link: [" + "<a href=\"" + url
				+ "\">Reset Password Link</a>]</p>"
				+ "<p>If you did not request a password reset, please ignore this email.</p>"
				+ "<p>For security reasons, this link will expire in 24 hours. If you have any questions, please contact us at caominhtri112201@gmail.com.</p>"
				+ "<p>Thank you for being our valued customer.</p>" + "<p>Best regards.</p>" + "<p>PetMate team</p>";

		helper.setSubject(subject);
		helper.setText(content, true);

		javaMailSender.send(message);
	}
}
