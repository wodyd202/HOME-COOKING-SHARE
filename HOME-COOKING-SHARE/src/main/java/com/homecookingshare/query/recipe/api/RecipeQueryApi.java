package com.homecookingshare.query.recipe.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homecookingshare.command.recipe.exception.RecipeNotFoundException;
import com.homecookingshare.domain.recipe.RecipeId;
import com.homecookingshare.domain.recipe.read.Recipe;
import com.homecookingshare.query.recipe.infra.RecipeReadRepository;
import com.homecookingshare.query.recipe.model.RecipeSearch;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@Api(tags = "레시피 조회 관련 API")
@RestController
@RequestMapping("api/v1/recipe")
@AllArgsConstructor
public class RecipeQueryApi {
	private RecipeReadRepository recipeReadRepository;
	
	@ApiOperation("레시피 정보 조회")
	@GetMapping("{recipeId}")
	public ResponseEntity<Recipe> findByRecipeId(@PathVariable RecipeId recipeId){
		Recipe recipe = recipeReadRepository.findByRecipeId(recipeId).orElseThrow(()->new RecipeNotFoundException("해당 레시피 정보가 존재하지 않습니다."));
		return new ResponseEntity<>(recipe, HttpStatus.OK);
	}
	
	@ApiOperation("레시피 리스트 조회")
	@GetMapping
	public ResponseEntity<List<Recipe>> findAll(RecipeSearch dto){
		List<Recipe> findAll = recipeReadRepository.findAll(dto);
		return new ResponseEntity<>(findAll, HttpStatus.OK);
	}
}
