package com.homecookingshare.domain.recipe;

import java.io.IOException;
import java.util.List;

import javax.persistence.AttributeConverter;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MaterialConverter implements AttributeConverter<List<Material>, String>{
	private ObjectMapper objectMapper;
	
	@Override
	public String convertToDatabaseColumn(List<Material> attribute) {
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Material> convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, List.class);
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
	}

}
