package com.homecookingshare.command.recipe.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homecookingshare.command.recipe.infra.validator.AddMakeProcessValidator;
import com.homecookingshare.command.recipe.infra.validator.AddMaterialValidator;
import com.homecookingshare.command.recipe.infra.validator.ChangeLevelValidator;
import com.homecookingshare.command.recipe.infra.validator.ChangeMainImageValidator;
import com.homecookingshare.command.recipe.infra.validator.ChangeServingValidator;
import com.homecookingshare.command.recipe.infra.validator.ChangeTitleValidator;
import com.homecookingshare.command.recipe.infra.validator.RegisterRecipeValidator;
import com.homecookingshare.command.recipe.infra.validator.RemoveMakeProcessValidator;
import com.homecookingshare.command.recipe.infra.validator.RemoveMaterialValidator;
import com.homecookingshare.command.recipe.model.RecipeCommand;
import com.homecookingshare.command.recipe.service.RecipeService;
import com.homecookingshare.common.fileUpload.FileUploader;
import com.homecookingshare.config.security.LoginUser;
import com.homecookingshare.domain.member.read.Member;
import com.homecookingshare.domain.recipe.Cooker;
import com.homecookingshare.domain.recipe.Recipe;
import com.homecookingshare.domain.recipe.RecipeId;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "레시피 상태 관련 API")
@RestController
@RequestMapping("api/v1/recipe")
@AllArgsConstructor
public class RecipeCommandAPi {
	private RecipeService recipeService;
	
	private RegisterRecipeValidator registerRecipeValidator;
	private AddMakeProcessValidator addMakeProcessValidator;
	private AddMaterialValidator addMaterialValidator;
	private ChangeLevelValidator changeLevelValidator;
	private ChangeMainImageValidator changeMainImageValidator;
	private ChangeServingValidator changeServingValidator;
	private ChangeTitleValidator changeTitleValidator;
	private RemoveMaterialValidator removeMaterialValidator;
	private RemoveMakeProcessValidator removeMakeProcessValidator;
	
	private FileUploader fileUploader;
	
	@ApiOperation("레시피 등록")
	@PostMapping
	public ResponseEntity<Recipe> execute(
			RecipeCommand.RegisterRecipe command,
			@ApiIgnore
			@LoginUser Member loginMember
		){
		Recipe createRecipe = recipeService.create(registerRecipeValidator, fileUploader, command, Cooker.of(loginMember));
		return new ResponseEntity<>(createRecipe, HttpStatus.CREATED);
	}
	
	@ApiOperation("조리 과정 추가")
	@PutMapping("{targetRecipeId}/make-proccess")
	public ResponseEntity<Recipe> execute(
			@PathVariable RecipeId targetRecipeId,
			RecipeCommand.AddMakeProcess command,
			@ApiIgnore
			@LoginUser Member loginMember
		){
		Recipe recipe = recipeService.addMakeProcess(addMakeProcessValidator, fileUploader, targetRecipeId, command, Cooker.of(loginMember));
		return new ResponseEntity<>(recipe, HttpStatus.OK);
	}
	
	@ApiOperation("조리 과정 삭제")
	@DeleteMapping("{targetRecipeId}/make-proccess")
	public ResponseEntity<Recipe> execute(
			@PathVariable RecipeId targetRecipeId,
			@RequestBody RecipeCommand.RemoveMakeProcess command,
			@ApiIgnore
			@LoginUser Member loginMember
		){
		Recipe recipe = recipeService.removeMakeProcess(removeMakeProcessValidator, targetRecipeId, command, Cooker.of(loginMember));
		return new ResponseEntity<>(recipe, HttpStatus.OK);
	}
	
	@ApiOperation("조리 재료 추가")
	@PutMapping("{targetRecipeId}/material")
	public ResponseEntity<Recipe> execute(
			@PathVariable RecipeId targetRecipeId,
			@RequestBody RecipeCommand.AddMaterial command,
			@ApiIgnore
			@LoginUser Member loginMember
		){
		Recipe recipe = recipeService.addMaterial(addMaterialValidator, targetRecipeId, command, Cooker.of(loginMember));
		return new ResponseEntity<>(recipe, HttpStatus.OK);
	}
	
	@ApiOperation("조리 재료 삭제")
	@DeleteMapping("{targetRecipeId}/material")
	public ResponseEntity<Recipe> execute(
			@PathVariable RecipeId targetRecipeId,
			@RequestBody RecipeCommand.RemoveMaterial command,
			@ApiIgnore
			@LoginUser Member loginMember
		){
		Recipe recipe = recipeService.removeMaterial(removeMaterialValidator, targetRecipeId, command, Cooker.of(loginMember));
		return new ResponseEntity<>(recipe, HttpStatus.OK);
	}	
	
	@ApiOperation("난이도 변경")
	@PutMapping("{targetRecipeId}/level")
	public ResponseEntity<Recipe> execute(
			@PathVariable RecipeId targetRecipeId,
			@RequestBody RecipeCommand.ChangeLevel command,
			@ApiIgnore
			@LoginUser Member loginMember
		){
		Recipe recipe = recipeService.changeLevel(changeLevelValidator, targetRecipeId, command, Cooker.of(loginMember));
		return new ResponseEntity<>(recipe, HttpStatus.OK);
	}
	
	@ApiOperation("인분 변경")
	@PutMapping("{targetRecipeId}/serving")
	public ResponseEntity<Recipe> execute(
			@PathVariable RecipeId targetRecipeId,
			@RequestBody RecipeCommand.ChangeServing command,
			@ApiIgnore
			@LoginUser Member loginMember
		){
		Recipe recipe = recipeService.changeServing(changeServingValidator, targetRecipeId, command, Cooker.of(loginMember));
		return new ResponseEntity<>(recipe, HttpStatus.OK);
	}
	
	@ApiOperation("레시피 타이틀 변경")
	@PutMapping("{targetRecipeId}/title")
	public ResponseEntity<Recipe> execute(
			@PathVariable RecipeId targetRecipeId,
			@RequestBody RecipeCommand.ChangeTitle command,
			@ApiIgnore
			@LoginUser Member loginMember
		){
		Recipe recipe = recipeService.changeTitle(changeTitleValidator, targetRecipeId, command, Cooker.of(loginMember));
		return new ResponseEntity<>(recipe, HttpStatus.OK);
	}
	
	@ApiOperation("레시피 메인 이미지 변경")
	@PutMapping("{targetRecipeId}/main-image")
	public ResponseEntity<Recipe> execute(
			@PathVariable RecipeId targetRecipeId,
			RecipeCommand.ChangeMainImage command,
			@ApiIgnore
			@LoginUser Member loginMember
		){
		Recipe recipe = recipeService.changeMainImage(changeMainImageValidator, fileUploader, targetRecipeId, command, Cooker.of(loginMember));
		return new ResponseEntity<>(recipe, HttpStatus.OK);
	}
}
