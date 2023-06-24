package com.example.petmate.service.third_party.vnpay;

import com.example.petmate.config.PaymentConfig;
import com.example.petmate.dto.PaymentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Service
public class VnPayServiceImpl implements VnPayService {
	@Value("${vnpay.code}")
	private String vnp_TmnCode;

	@Value("${vnpay.secret}")
	private String vnp_HashSecret;

	@Value("${vnpay.base-url}")
	private String vnp_PayUrl;

	@Value("${vnpay.return-url}")
	private String vnp_Returnurl;
	@Override
	public PaymentDto doPayment(String ipAddress, long price, String requestId) throws UnsupportedEncodingException {
		long amount = price * 100;

		String vnp_TxnRef = PaymentConfig.getRandomNumber(8);

		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());

		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", PaymentConfig.vnp_Version);
		vnp_Params.put("vnp_Command", PaymentConfig.vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Locale", "vn");
		vnp_Params.put("vnp_CurrCode", "VND");
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", "Data:" + requestId);
		vnp_Params.put("vnp_OrderType", "other");
		vnp_Params.put("vnp_Amount", String.valueOf(amount));
		vnp_Params.put("vnp_ReturnUrl", vnp_Returnurl);
		vnp_Params.put("vnp_IpAddr", ipAddress);
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
		vnp_Params.put("vnp_BankCode", "VNBANK");

		List fieldNames = new ArrayList(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				//Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				//Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = PaymentConfig.hmacSHA512(vnp_HashSecret, hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = vnp_PayUrl + "?" + queryUrl;
		PaymentDto result = new PaymentDto();
		result.setStatus("OK");
		result.setMessage("Successfully");
		result.setUrl(paymentUrl);
		return result;
	}
}
