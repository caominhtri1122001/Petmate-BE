package com.example.petmate.service.third_party.vnpay;

import com.example.petmate.dto.PaymentDto;

import java.io.UnsupportedEncodingException;

public interface VnPayService {
	PaymentDto doPayment(String ipAddress, long price, String requestId) throws UnsupportedEncodingException;
}
