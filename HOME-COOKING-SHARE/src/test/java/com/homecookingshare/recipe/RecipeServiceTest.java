package com.homecookingshare.recipe;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.homecookingshare.command.member.infra.JpaMemberRepository;
import com.homecookingshare.command.recipe.infra.JpaRecipeRepository;
import com.homecookingshare.command.recipe.infra.validator.AddMakeProcessValidator;
import com.homecookingshare.command.recipe.infra.validator.AddMaterialValidator;
import com.homecookingshare.command.recipe.infra.validator.ChangeLevelValidator;
import com.homecookingshare.command.recipe.infra.validator.ChangeMainImageValidator;
import com.homecookingshare.command.recipe.infra.validator.ChangeServingValidator;
import com.homecookingshare.command.recipe.infra.validator.ChangeTitleValidator;
import com.homecookingshare.command.recipe.infra.validator.RemoveMaterialValidator;
import com.homecookingshare.command.recipe.infra.validator.RemoveMakeProcessValidator;
import com.homecookingshare.command.recipe.model.RecipeCommand;
import com.homecookingshare.command.recipe.model.RecipeCommand.AddMakeProcess;
import com.homecookingshare.command.recipe.service.RecipeService;
import com.homecookingshare.command.recipe.service.SimpleRecipeService;
import com.homecookingshare.common.Validator;
import com.homecookingshare.common.fileUpload.FileUploader;
import com.homecookingshare.domain.member.Member;
import com.homecookingshare.domain.recipe.Cooker;
import com.homecookingshare.domain.recipe.MakeProcess;
import com.homecookingshare.domain.recipe.Recipe;
import com.homecookingshare.domain.recipe.Recipe.Level;
import com.homecookingshare.domain.recipe.Recipe.RecipeCategory;
import com.homecookingshare.domain.recipe.Recipe.Serving;
import com.homecookingshare.domain.recipe.RecipeId;
import com.homecookingshare.domain.recipe.RecipeMainImage;

@SuppressWarnings({"rawtypes","unchecked"})
public class RecipeServiceTest {

	JpaMemberRepository jpaMemberRepsitory = mock(JpaMemberRepository.class);
	JpaRecipeRepository jpaRecipeRepository = mock(JpaRecipeRepository.class);
	RecipeService recipeService = new SimpleRecipeService(jpaRecipeRepository, jpaMemberRepsitory);
	
	RecipeId targetRecipeId = new RecipeId("recipeId");
	
	Cooker mockCooker = mock(Cooker.class);

	Recipe mockRecipe = mock(Recipe.class);

	@Test
	void ??????_??????() {
		Validator<RecipeCommand.ChangeServing> validator = new ChangeServingValidator();
		RecipeCommand.ChangeServing command = new RecipeCommand.ChangeServing(Serving.ONE);
		
		recipeService.changeServing(validator,targetRecipeId,command,mockCooker);
	}
	
	@Test
	void ??????_??????() {
		Validator<RecipeCommand.RemoveMaterial> validator = new RemoveMaterialValidator();
		RecipeCommand.RemoveMaterial command = new RecipeCommand.RemoveMaterial("??????", "?????? ??????");
		
		recipeService.removeMaterial(validator,targetRecipeId,command,mockCooker);
	}
	
	@Test
	void ??????_??????() {
		Validator<RecipeCommand.AddMaterial> validator = new AddMaterialValidator();
		RecipeCommand.AddMaterial command = new RecipeCommand.AddMaterial("??????","?????? ??????");
		
		recipeService.addMaterial(validator,targetRecipeId,command,mockCooker);
	}
	
	@Test
	void ??????_?????????_??????() {
		Validator<RecipeCommand.ChangeMainImage> validator = new ChangeMainImageValidator();
		RecipeCommand.ChangeMainImage command = new RecipeCommand.ChangeMainImage(new MockMultipartFile("file.jpg", "file.jpg","",new byte[] {}));
		
		when(mockRecipe.getMainImage())
			.thenReturn(mock(RecipeMainImage.class));
		recipeService.changeMainImage(validator,mock(FileUploader.class),targetRecipeId,command,mockCooker);
	}
	
	@Test
	void ?????????_??????() {
		Validator<RecipeCommand.ChangeLevel> validator = new ChangeLevelValidator();
		RecipeCommand.ChangeLevel command = new RecipeCommand.ChangeLevel(Level.FOUR);
		recipeService.changeLevel(validator, targetRecipeId, command, mockCooker);
	}
	
	@Test
	void ????????????_??????() {
		Validator<RecipeCommand.RemoveMakeProcess> validator = new RemoveMakeProcessValidator();
		RecipeCommand.RemoveMakeProcess command = new RecipeCommand.RemoveMakeProcess(1);
		recipeService.removeMakeProcess(validator, targetRecipeId, command, mockCooker);
	}
	
	@Test
	void ????????????_??????() {
		Validator<AddMakeProcess> validator = new AddMakeProcessValidator();
		
		RecipeCommand.AddMakeProcess command = new RecipeCommand.AddMakeProcess(
				new MockMultipartFile("??????.jpg","??????.jpg","",new byte[] {}), "????????? ??????", 0);

		when(mockRecipe.addMakeProcess(any()))
			.thenReturn(mock(MakeProcess.class));
		
		recipeService.addMakeProcess(validator, mock(FileUploader.class), targetRecipeId, command, mockCooker);
	}
	
	@Test
	void ?????????_?????????_??????() {
		Validator<RecipeCommand.ChangeTitle> validator = new ChangeTitleValidator();

		RecipeCommand.ChangeTitle command = new RecipeCommand.ChangeTitle("????????? ??????");
		recipeService.changeTitle(validator, targetRecipeId,command, mockCooker);
		
		verify(mockRecipe,times(1))
			.changeTitle(any());
	}
	
	@Test
	void ?????????_??????() {
		Validator validator = mock(Validator.class);
		FileUploader fileUploader = mock(FileUploader.class);

		RecipeCommand.RegisterRecipe command = new RecipeCommand.RegisterRecipe(
					"?????????",
					new MockMultipartFile("file.jpg", "file.jpg","",new byte[] {}),
					RecipeCategory.Chinese,
					Level.FOUR,
					Serving.FIVE,
					60000,
					Arrays.asList("?????????"),
					Arrays.asList("??????"),
					Arrays.asList(new MockMultipartFile("??????.jpg","??????.jpg","" ,new byte [] {})),
					Arrays.asList("??????"),
					Arrays.asList(0)
				);
		
		recipeService.create(validator, fileUploader, command, new Cooker("test@naver.com"));
		
		verify(jpaRecipeRepository,times(1))
			.save(any(Recipe.class));
	}
	
	@BeforeEach
	void setUp() {
		when(jpaMemberRepsitory.findById(any()))
			.thenReturn(Optional.of(mock(Member.class)));
		when(mockCooker.isMyRecipe(any()))
			.thenReturn(true);
		when(jpaRecipeRepository.findById(targetRecipeId))
		.thenReturn(Optional.of(mockRecipe));
	}
}
