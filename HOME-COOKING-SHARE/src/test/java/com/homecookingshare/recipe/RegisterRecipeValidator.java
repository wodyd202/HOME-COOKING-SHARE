package com.homecookingshare.recipe;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.homecookingshare.common.Validator;
import com.homecookingshare.recipe.Recipe.RecipeLevel;
import com.homecookingshare.recipe.Recipe.Serving;

public class RegisterRecipeValidator implements Validator<RegisterRecipe> {

	@Override
	public void validate(RegisterRecipe obj) {
		verfyIsEmptyObjects(obj);
		
		String title = obj.getTitle();
		String content = obj.getContent();
		long time = obj.getTime();
		String tip = obj.getTip();
		verfyRegexPatternStringValue(title, "^[가-힣\\s]*$",1, 12, invalidRecipe("레시피 타이틀은 [한글 + 공백] 조합 최대 12자까지 입력해주세요.", "title"));
		verfyRegexPatternStringValue(content, "^[가-힣0-9a-zA-Z\\s]*$", 1, 100, invalidRecipe("레시피 내용은 [한글,공백,영어,숫자] 조합 최대 100자까지 입력해주세요.", "content"));
		verfyGteFirstAndLteLastNumberValue(time, 1, 86400 * 30, invalidRecipe("소요시간은 최대 30일까지로 입력해주세요.", "time"));
		if(tip != null && !tip.isEmpty()) {
			verfyRegexPatternStringValue(tip, "^[가-힣0-9a-zA-Z\\s]*$",1,100,invalidRecipe("레시피 팁은 [한글,공백,영어,숫자] 조합 최대 100자까지 입력해주세요.", "tip"));
		}
	}
	
	private void verfyIsEmptyObjects(RegisterRecipe obj) {
		Set<RegisterMaterial> material = obj.getMaterial();
		String title = obj.getTitle();
		String writer = obj.getWriter();
		MultipartFile mainImg = obj.getMainImg();
		String content = obj.getContent();
		RecipeLevel level = obj.getLevel();
		Serving serving = obj.getServing();
		long time = obj.getTime();
		String category = obj.getCategory();
		verfyNotEmptyStringValue(category, invalidRecipe("요리 카테고리를 입력해주세요.", "category"));
		verfyGtFirstNumberValue(time, 0, invalidRecipe("조리 시간을 입력해주세요.", "time"));
		verfyNotNullObject(serving, invalidRecipe("레시피 목표 인분을 입력해주세요.", "serving"));
		verfyNotNullObject(level, invalidRecipe("레시피 난이도를 입력해주세요.", "level"));
		verfyNotEmptyStringValue(content, invalidRecipe("레시피 내용을 입력해주세요.", "content"));
		verfyNotNullObject(mainImg, invalidRecipe("레시피 메인 이미지를 등록해주세요.", "mainImg"));
		verfyNotEmptyStringValue(writer, invalidRecipe("레시피 등록자를 입력해주세요.", "writer"));
		verfyNotEmptyStringValue(title, invalidRecipe("레시피 제목을 입력해주세요.", "title"));
		verfyNotNullObject(material, invalidRecipe("재료를 하나 이상 입력해주세요.","material"));
		material.forEach(c->{
			verfyNotNullObject(c, invalidRecipe("재료를 하나 이상 입력해주세요.","marterial"));
			verfyNotEmptyStringValue(c.getMaterial(), invalidRecipe("재료를 정보를 다시 확인해주세요.", "material"));
			verfyNotEmptyStringValue(c.getCapacity(), invalidRecipe("재료를 용량 정보를 다시 확인해주세요.", "material"));
		});
	}
	
	private InvalidRecipeException invalidRecipe(String msg,String field) {
		return new InvalidRecipeException(msg, field);
	}

}
