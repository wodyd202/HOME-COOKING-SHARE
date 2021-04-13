package com.homecookingshare.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Validator<T> {
	void validate(T obj);

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

	default void verfyNotEmptyStringValue(String value, CustomArgumentException e) throws CustomArgumentException {
		if (value == null || value.isEmpty()) {
			throw e;
		}
	}
}
