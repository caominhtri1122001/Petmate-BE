package com.example.petmate.service.user;

import com.example.petmate.constant.ResponseCodes;
import com.example.petmate.entity.User;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.mapper.UserMapper;
import com.example.petmate.model.request.UserLoginRequest;
import com.example.petmate.model.request.UserRegisterRequest;
import com.example.petmate.model.response.UserLoginResponse;
import com.example.petmate.model.response.UserRegisterResponse;
import com.example.petmate.repository.UserRepository;
import com.example.petmate.utils.JwtUtils;
import com.example.petmate.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

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
}
