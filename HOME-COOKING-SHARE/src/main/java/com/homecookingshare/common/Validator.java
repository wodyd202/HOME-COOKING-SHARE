package com.homecookingshare.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

public interface Validator<T> {
	void validate(T obj);

	default void verfyGtFirstNumberValue(long value, int first, CustomArgumentException e) {
		if (first >= value) {
			throw e;
		}
	}

	default void verfyGteFirstAndLteLastNumberValue(long value, long first, long last, CustomArgumentException e) {
		if (first > value || value > last) {
			throw e;
		}
	}

	default void verfyRegexPatternStringValue(String value, String regex, int start, int end,
			CustomArgumentException e) {
		verfyRegexPatternStringValue(value, regex, e);
		if (value.length() < start || value.length() > end) {
			throw e;
		}
	}

	default void verfyRegexPatternStringValue(String value, String regex, CustomArgumentException e) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(value);
		if (!m.matches()) {
			throw e;
		}
	}

	default void verfyNotNullObject(Object obj, CustomArgumentException e) {
		if (obj == null) {
			throw e;
		}
	}

	default void verfyNotEmptyStringValue(String value, CustomArgumentException e) {
		if (value == null || value.isEmpty()) {
			throw e;
		}
	}
	
	default void verfyIsImageFile(MultipartFile file, CustomArgumentException e) {
		String name = file.getOriginalFilename();
		int lastIndexOf = name.lastIndexOf(".");
		String extention = name.substring(lastIndexOf, name.length()).toUpperCase();
		if (!extention.equals(".JPG") && !extention.equals(".JPEG") && !extention.equals(".PNG")) {
			throw e;
		}
	}
}
