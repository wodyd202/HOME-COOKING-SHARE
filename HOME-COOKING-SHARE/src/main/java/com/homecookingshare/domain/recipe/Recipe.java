package com.homecookingshare.domain.recipe;

import java.util.Date;
import java.util.stream.Collectors;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.homecookingshare.command.recipe.model.RecipeCommand.AddMakeProcess;
import com.homecookingshare.command.recipe.model.RecipeCommand.ChangeLevel;
import com.homecookingshare.command.recipe.model.RecipeCommand.RegisterRecipe;
import com.homecookingshare.command.recipe.model.RecipeCommand.RemoveMakeProcess;
import com.homecookingshare.domain.recipe.event.AddedMakeProcess;
import com.homecookingshare.domain.recipe.event.ChangedLevel;
import com.homecookingshare.domain.recipe.event.RegisteredRecipe;
import com.homecookingshare.domain.recipe.event.RemovedMakeProcess;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "RECIPE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Recipe extends AbstractAggregateRoot<Recipe> {
	public enum RecipeCategory { Korean, Chinese, Japanese, Western }
	public enum Serving { ONE, TWO, THREE, FOUR, FIVE, ETC }
	public enum Level { ONE, TWO, THREE, FOUR, FIVE }

	@EmbeddedId
	private RecipeId id;

	@Embedded
	private Cooker cooker;
	
	@Embedded
	private RecipeTitle title;

	private String mainImage;
	
	@Enumerated(EnumType.STRING)
	private RecipeCategory category;

	@Enumerated(EnumType.STRING)
	private Serving serving;

	@Enumerated(EnumType.STRING)	
	private Level level;

	@Embedded
	private MakingTime time;
		
	@Embedded
	private Materials materials;
	
	@Embedded
	private MakeProcesses makeProcess;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;

	private Recipe(
			RegisterRecipe command,
			Cooker cooker
		) {
		this.id = RecipeId.create();
		this.title = new RecipeTitle(command.getTitle());
		this.category = command.getCategory();
		this.serving = command.getServing();
		this.level = command.getLevel();
		this.time = new MakingTime(command.getMakingTime());
		
		this.materials = new Materials(command.getMaterials().stream().map(Material::new).collect(Collectors.toList()));
		this.makeProcess = new MakeProcesses(command.getMakeProcesses().stream().map(MakeProcess::new).collect(Collectors.toList()));
		
		this.mainImage = this.makeProcess.getMakeProcess().get(command.getMainImageIdx()).getImagePath();
		this.createDateTime = new Date();
		this.cooker = cooker;
		registerEvent(new RegisteredRecipe(id, cooker, title, mainImage, category, serving, level, time, materials, makeProcess, createDateTime));
	}
	
	public static Recipe create(RegisterRecipe command, Cooker cooker) {
		return new Recipe(command,cooker);
	}

	public void changeTitle(String title) {
		this.title = new RecipeTitle(title);
		registerEvent(new ChangedTitle(this.id, this.title));
	}

	public MakeProcess addMakeProcess(AddMakeProcess command) {
		MakeProcess realMakeProcess = new MakeProcess(command);
		this.makeProcess.add(realMakeProcess);
		registerEvent(new AddedMakeProcess(this.id, this.makeProcess));
		return realMakeProcess;
	}

	public void removeMakeProccess(RemoveMakeProcess command) {
		this.makeProcess.remove(command.getProcessOrder());
		registerEvent(new RemovedMakeProcess(this.id, this.makeProcess));
	}

	public void changeLevel(ChangeLevel command) {
		this.level = command.getLevel();
		registerEvent(new ChangedLevel(this.id, this.level));
	}
}
