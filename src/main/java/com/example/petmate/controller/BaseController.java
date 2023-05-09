package com.example.petmate.controller;

import com.example.petmate.exception.ResponseException;
import com.example.petmate.utils.JwtUtils;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BaseController {
	protected String checkUser(HttpServletRequest request) throws ResponseException {
		JsonObject jwt = JwtUtils.checkRequestJwt(request);
		return JwtUtils.getUserIdFromToken(request.getHeader("Authorization").substring(7));
	}
}
