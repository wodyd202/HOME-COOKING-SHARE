package com.homecookingshare.query.member.infra;

import java.util.List;
import java.util.Optional;

import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.read.Member;
import com.homecookingshare.domain.recipe.read.Recipe;

public interface MemberReadRepository {
	void save(Member member);

	Optional<Member> findByEmail(Email memberEmail);
	
	List<Recipe> findInterestRecipes(Email memberEmail);
}
