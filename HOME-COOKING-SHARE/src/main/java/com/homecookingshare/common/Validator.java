package com.homecookingshare.common;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

public interface Validator<T> {
	List<Character> NOT_ALLOWED_SPECIAL_CHAR = Arrays.asList('<', '>', '?', '"', '\'');

	void validate(T obj);

	default void verifyContainUnAllowedSpecialChar(String value, CustomArgumentException e) {
		for (int i = 0; i < value.length(); i++) {
			if(NOT_ALLOWED_SPECIAL_CHAR.contains(value.charAt(i))) {
				throw e;
			}
		}
	}

	default void verifyGtFirstNumberValue(long value, int first, CustomArgumentException e) {
		if (first >= value) {
			throw e;
		}
	}

	default void verifyGteFirstAndLteLastNumberValue(long value, long first, long last, CustomArgumentException e) {
		if (first > value || value > last) {
			throw e;
		}
	}

	default void verifyRegexPatternStringValue(String value, String regex, int start, int end,
			CustomArgumentException e) {
		verifyRegexPatternStringValue(value, regex, e);
		if (value.length() < start || value.length() > end) {
			throw e;
		}
	}

	default void verifyRegexPatternStringValue(String value, String regex, CustomArgumentException e) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(value);
		if (!m.matches()) {
			throw e;
		}
	}

	default void verifyNotNullObject(Object obj, CustomArgumentException e) {
		if (obj == null) {
			throw e;
		}
	}

	default void verifyNotEmptyStringValue(String value, CustomArgumentException e) {
		if (value == null || value.isEmpty()) {
			throw e;
		}
	}

	default void verifyIsImageFile(MultipartFile file, CustomArgumentException e) {
		String name = file.getOriginalFilename();
		int lastIndexOf = name.lastIndexOf(".");
		String extention = name.substring(lastIndexOf, name.length()).toUpperCase();
		if (!extention.equals(".JPG") && !extention.equals(".JPEG") && !extention.equals(".PNG")) {
			throw e;
		}
	}

	default void verifyLengthBetweenFirstAndSecond(String value, int first, int second, CustomArgumentException e) {
		int length = value.length();
		if(length >= first && length <= second) {return;}
		throw e;
	}
}
