package com.homecookingshare.command.recipe.infra.validator;

import org.springframework.web.multipart.MultipartFile;

import com.homecookingshare.command.recipe.exception.InvalidRecipeException;
import com.homecookingshare.command.recipe.model.RecipeCommand.AddMakeProcess;
import com.homecookingshare.command.recipe.model.RecipeCommand.AddMaterial;
import com.homecookingshare.common.Validator;
import com.homecookingshare.domain.recipe.Recipe.Level;
import com.homecookingshare.domain.recipe.Recipe.RecipeCategory;
import com.homecookingshare.domain.recipe.Recipe.Serving;

public abstract class AbstractRecipeValidator<T> implements Validator<T> {
	protected void titleValidation(String title) {
		verifyNotEmptyStringValue(title, new InvalidRecipeException("레시피 제목을 입력해주세요"));
		verifyLengthBetweenFirstAndSecond(title, 2, 50, new InvalidRecipeException("레시피 제목은 2자 이상 50자 이하로 입력해주세요"));
		verifyContainUnAllowedSpecialChar(title, new InvalidRecipeException("레시피 제목에 허용하지 않는 특수문자가 포함되어있습니다."));
	}
	
	protected void materialValidation(AddMaterial material) {
		verifyNotNullObject(material, new InvalidRecipeException("레시피 재료를 입력해주세요."));
		verifyNotEmptyStringValue(material.getName(), new InvalidRecipeException("레시피 재료명을 입력해주세요."));
		verifyNotEmptyStringValue(material.getCapacity(), new InvalidRecipeException("레시피 재료의 용량을 입력해주세요."));
		verifyLengthBetweenFirstAndSecond(material.getName(), 1, 20, new InvalidRecipeException("레시피 재료명은 1자 이상 20자 이하로 입력해주세요."));
		verifyLengthBetweenFirstAndSecond(material.getCapacity(), 1, 20, new InvalidRecipeException("레시피 재료의 용량은 1자 이상 20자 이하로 입력해주세요."));
		verifyContainUnAllowedSpecialChar(material.getName(), new InvalidRecipeException("레시피 재료명에 허용하지 않는 특수문자가 포함되어있습니다."));
		verifyContainUnAllowedSpecialChar(material.getCapacity(), new InvalidRecipeException("레시피 재료의 용량에 허용하지 않는 특수문자가 포함되어있습니다."));
	}

	protected void makeProcessValidation(AddMakeProcess makeProcess) {
		verifyNotNullObject(makeProcess, new InvalidRecipeException("레시피 제작 과정을 입력해주세요."));
		verifyNotNullObject(makeProcess.getFile(), new InvalidRecipeException("레시피 제작 과정의 이미지를 입력해주세요."));
		verifyIsImageFile(makeProcess.getFile(), new InvalidRecipeException("레시피 제작 과정의 이미지는 이미지 파일만 등록할 수 있습니다."));
		verifyNotEmptyStringValue(makeProcess.getContent(), new InvalidRecipeException("레시피 제작 과정의 내용을 입력해주세요."));
		verifyContainUnAllowedSpecialChar(makeProcess.getContent(), new InvalidRecipeException("레시피 제작 과정의 내용에 허용하지 않는 특수문자가 포함되어있습니다."));
	}
	
	protected void makingTimeValidation(long makingTime) {
		if(makingTime % 1000 != 0 || makingTime < 1000) {
			throw new InvalidRecipeException("제작 시간을 1000 단위로 입력해주세요.");
		}
	}
	
	protected void mainImageValidation(MultipartFile file) {
		verifyNotNullObject(file, new InvalidRecipeException("레시피 메인 이미지를 입력해주세요."));
		verifyIsImageFile(file, new InvalidRecipeException("메인 이미지는 이미지 파일만 업로드 가능합니다."));
	}
	
	protected void servingValidation(Serving serving) {
		verifyNotNullObject(serving, new InvalidRecipeException("레시피 인분을 입력해주세요."));
	}
	
	protected void levelValidation(Level level) {
		verifyNotNullObject(level, new InvalidRecipeException("레시피 난이도를 입력해주세요."));
	}
	
	protected void categoryValidation(RecipeCategory category) {
		verifyNotNullObject(category, new InvalidRecipeException("레시피 카테고리를 입력해주세요."));
	}
}
