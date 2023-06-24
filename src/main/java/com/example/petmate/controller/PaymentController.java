package com.example.petmate.controller;

import com.example.petmate.config.PaymentConfig;
import com.example.petmate.service.third_party.vnpay.VnPayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/payment")
@Slf4j
public class PaymentController {

	private final VnPayService vnPayService;

	public PaymentController(VnPayService vnPayService) {
		this.vnPayService = vnPayService;
	}

	@Operation(summary = "api for payment")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api for payment", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@GetMapping
	public ResponseEntity<?> doPayment(HttpServletRequest req, long price, String info)
			throws UnsupportedEncodingException {
		String ipAddress = PaymentConfig.getIpAddress(req);
		return ResponseEntity.status(HttpStatus.OK).body(vnPayService.doPayment(ipAddress, price, info));
	}
}
