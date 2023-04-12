package com.example.petmate.utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
public class StringUtils {

	private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);

	public static boolean isStringEmpty(String dataStr) {
		return (dataStr == null || dataStr.trim().equals("") || dataStr.equals("null") || dataStr.equals("NULL"));
	}

	public static boolean isListEmpty(List dataList) {
		return (dataList == null || dataList.size() == 0);
	}

	public static String getShaStringWithSalt(String inputStr, String salt) {
		String shaStr = "";
		if (inputStr!=null) {
			if (salt==null) {
				salt = "";
			} else if (salt.length()>16) {
				salt = salt.substring(0, 16);
			}
			inputStr = inputStr + salt;
			shaStr = getShaString(inputStr);
		}
		return shaStr;
	}

	public static String getShaString(String inputStr) {
		String shaStr = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(inputStr.getBytes(StandardCharsets.UTF_8));
			shaStr = bytesToHex(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			log.error("SecurityUtil getShaString NoSuchAlgorithmException: "+e.getMessage());
		}
		return shaStr;
	}

	private static String bytesToHex(byte[] bytes) {
		byte[] hexChars = new byte[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars, StandardCharsets.UTF_8);
	}
}
