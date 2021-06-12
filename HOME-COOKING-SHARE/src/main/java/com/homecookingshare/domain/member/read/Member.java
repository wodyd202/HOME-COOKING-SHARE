package com.homecookingshare.domain.member.read;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.homecookingshare.domain.member.AuthType;
import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.InterestRecipes;
import com.homecookingshare.domain.member.MemberRule;
import com.homecookingshare.domain.member.MemberState;
import com.homecookingshare.domain.member.Password;
import com.homecookingshare.domain.member.Profile;
import com.homecookingshare.domain.recipe.read.Recipe;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements Serializable {
	private static final long serialVersionUID = 1L;
	private Email email;
	private Password password;
	private Profile profile;
	private MemberRule rule;
	private AuthType authType;
	private MemberState state;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createDateTime;

	public void authSuccess() {
		this.authType = AuthType.YES;
	}

	private InterestRecipes interestRecipes;
	
	public void changeImage(String imageName) {
		this.profile.changeImage(imageName);
	}

	public void changePassword(Password password) {
		this.password = password;
	}

	public void interestRecipe(Recipe targetRecipe) {
		interestRecipes.interest(targetRecipe);
	}

	public void unInterestRecipe(Recipe targetRecipe) {
		interestRecipes.unInterest(targetRecipe);
	}
}
