package com.homecookingshare.command.recipe.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.homecookingshare.command.recipe.exception.InvalidRecipeException;
import com.homecookingshare.domain.recipe.Recipe.Level;
import com.homecookingshare.domain.recipe.Recipe.RecipeCategory;
import com.homecookingshare.domain.recipe.Recipe.Serving;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class RecipeCommand {

	@Setter
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class AddMaterial {
		private String name;
		private String capacity;
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class RemoveMaterial {
		private String name;
		private String capacity;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class AddMakeProcess {
		private MultipartFile file;
		private String content;
		int processOrder;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class RegisterRecipe {
		private String title;
		private MultipartFile mainImage;
		private RecipeCategory category;
		private Level level;
		private Serving serving;
		private long makingTime;

		private List<String> materialNames;
		private List<String> materialCapacitys;

		private List<MultipartFile> makeProcessImages;
		private List<String> makeProcessContents;
		private List<Integer> makeProcessIdxs;

		public List<AddMaterial> getMaterials() {
			List<AddMaterial> materials = new ArrayList<>();
			if(materialNames == null || materialCapacitys == null) {
				return materials;
			}
			if (materialNames.size() != materialCapacitys.size()) {
				throw new InvalidRecipeException("레시피 재료 정보가 잘못 입력되었습니다.");
			}
			for (int i = 0; i < materialNames.size(); i++) {
				materials.add(new AddMaterial(materialNames.get(i), materialCapacitys.get(i)));
			}
			return materials;
		}

		public List<AddMakeProcess> getMakeProcesses() {
			List<AddMakeProcess> makeProcesses = new ArrayList<>();
			if(makeProcessImages == null || makeProcessContents == null || makeProcessIdxs == null) {
				return makeProcesses;
			}
			if(makeProcessImages.size() != makeProcessContents.size() || makeProcessContents.size() != makeProcessIdxs.size()) {
				throw new InvalidRecipeException("레시피 제작 과정이 잘못 입력되었습니다.");
			}
			for (int i = 0; i < makeProcessImages.size(); i++) {
				makeProcesses.add(new AddMakeProcess(makeProcessImages.get(i), makeProcessContents.get(i), makeProcessIdxs.get(i)));
			}
			return makeProcesses;
		}
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ChangeTitle {
		private String title;
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class RemoveMakeProcess {
		private int processOrder;
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ChangeLevel {
		private Level level;
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ChangeServing {
		private Serving serving;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ChangeMainImage {
		private MultipartFile file;
	}
}
