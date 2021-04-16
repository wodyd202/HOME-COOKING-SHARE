package com.homecookingshare.recipe;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import com.homecookingshare.member.Email;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * @author LJY
 * 
 * 1. 레시피 id는 UUID로 사용한다.
 * 2. 레시피 title은 [한글 + 공백] 최대 12자까지 허용한다.
 * 3. 레시피 content는 [한글 + 공백 + 영어(대,소문자) + 숫자] 최대 100자까지 허용한다.
 * 4. 조리 시간은 최대 30일(86400 * 30 * 1000)까지 허용한다.
 * 5. 레시피 tip은 [한글 + 공백 + 영어(대,소문자) + 숫자] 최대 100자까지 허용한다.
 * 6. 재료의 타이틀은 [한글 + 숫자 + 공백] 최대 10자까지 허용한다.
 * 7. 재료의 용량은 [한글 + 숫자 + 공백 + 영어(대,소문자)] 최대 10자까지 허용한다.
 * 8. 레시피 카테고리는 등록 전 이미 등록된 카테고리인지 확인한다.
 * 
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe {

	private RecipeId id;
	private RecipeTitle title;
	private RecipeDetail detail;
	private RecipeCategory category;
	
	private Email writer;
	private Date createDate;
	
	public static RecipeId createId() {
		return new RecipeId(UUID.randomUUID().toString());
	}

	public Recipe(RecipeId createId, RegisterRecipe registerRecipe) {
		this.id = createId;
		this.title = new RecipeTitle(registerRecipe.getTitle());
		this.category = new RecipeCategory(registerRecipe.getCategory());
		this.writer = new Email(registerRecipe.getWriter());
		this.createDate = new Date();

		this.detail = RecipeDetail.builder().content(registerRecipe.getContent()).level(registerRecipe.getLevel())
				.serving(registerRecipe.getServing()).time(registerRecipe.getTime()).tip(registerRecipe.getTip())
				.marterials(registerRecipe.getMaterial().stream()
						.map(c -> new RecipeMarterial(c.getMaterial(), c.getCapacity())).collect(Collectors.toSet()))
				.build();
	}

	public enum Serving {
		SERVING_1, SERVING_2, SERVING_3, SERVING_4, SERVING_5,
	}

	public enum RecipeLevel {
		LEVEL_1, LEVEL_2, LEVEL_3, LEVEL_4, LEVEL_5
	}
}
