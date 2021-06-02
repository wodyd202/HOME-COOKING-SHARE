package com.homecookingshare.domain.recipe;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;

import com.homecookingshare.command.recipe.exception.InvalidRecipeException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MakeProcesses implements Serializable {
	private static final long serialVersionUID = 1L;

	@ElementCollection
	@CollectionTable(name = "RECIPE_PROCESS", joinColumns = @JoinColumn(name = "RECIPE_ID", referencedColumnName = "recipeId"))
	private List<MakeProcess> makeProcess;

	public void add(MakeProcess makeProcess) {
		int size = this.makeProcess.size();
		if (makeProcess.getProcessOrder() > size) {
			throw new InvalidRecipeException("조리 순서가 잘못 되었습니다.");
		}
		for (int i = makeProcess.getProcessOrder() + 1; i < size; i++) {
			this.makeProcess.get(i).increasmentProccessOrder();
		}
		this.makeProcess.add(makeProcess.getProcessOrder(), makeProcess);
	}

	public void remove(int processOrder) {
		int size = makeProcess.size();
		if (processOrder >= size) {
			throw new InvalidRecipeException("삭제하고자 하는 순서가 잘못되었습니다.");
		}
		for (int i = processOrder + 1; i < size; i++) {
			this.makeProcess.get(i).decreasmentProccessOrder();
		}
		makeProcess.remove(processOrder);
	}
}
