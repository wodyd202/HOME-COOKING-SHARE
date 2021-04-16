package com.homecookingshare.member.aggregate;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PayloadConverter implements AttributeConverter<Object, String> {

	@Override
	public String convertToDatabaseColumn(Object attribute) {
		try {
			return new ObjectMapper().writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
		}
		return "";
	}

	@Override
	public Object convertToEntityAttribute(String dbData) {
		return dbData;
	}

}
