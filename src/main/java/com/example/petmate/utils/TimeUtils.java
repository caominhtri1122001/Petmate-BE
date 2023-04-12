package com.example.petmate.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
	private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
			.withZone(ZoneId.of("UTC"));

	private static final DateTimeFormatter ISO_FORMATTER_HAS_MMS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
			.withZone(ZoneId.of("UTC"));

	private TimeUtils() {
	}

	public static long getNowTimes() {
		return System.currentTimeMillis();
	}

	public static LocalDateTime convertToLocalDateTime(Long epochTime) {
		return epochTime == null ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(epochTime), ZoneId.of("UTC"));
	}

	public static LocalDateTime convertToLocalDateTime(String isoDateTime) {
		return LocalDateTime.parse(isoDateTime, ISO_FORMATTER);
	}

	public static LocalDateTime converToLocalDateTimeNoIso(String dateTime) {
		return LocalDateTime.parse(dateTime, ISO_FORMATTER_HAS_MMS);
	}

	public static OffsetDateTime convertToOffsetDateTimeISO(String localDateTimeWithOffset) {
		return OffsetDateTime.parse(localDateTimeWithOffset, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}

	public static Long convertToEpochMilli(LocalDateTime dateTime) {
		return dateTime == null ? null : dateTime.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
	}

	public static Long convertToEpochMilli(OffsetDateTime offsetDateTime) {
		return convertToEpochMilli(offsetDateTime.withOffsetSameInstant(ZoneOffset.UTC)
				.toLocalDateTime());
	}

	public static LocalDateTime parseIsoOffsetDateTime(String localDateTimeWithOffset) {
		OffsetDateTime offsetDateTime = OffsetDateTime.parse(localDateTimeWithOffset, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		return offsetDateTime.withOffsetSameInstant(ZoneOffset.UTC)
				.toLocalDateTime();
	}

	public static String convertToIsoString(LocalDateTime dateTime) {
		if (dateTime == null) {
			return null;
		}
		return ISO_FORMATTER.format(dateTime);
	}
}
