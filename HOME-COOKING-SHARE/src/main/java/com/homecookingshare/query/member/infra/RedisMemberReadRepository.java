package com.homecookingshare.query.member.infra;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.stereotype.Repository;

import com.homecookingshare.domain.member.AuthType;
import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.InterestRecipes;
import com.homecookingshare.domain.member.MemberRule;
import com.homecookingshare.domain.member.MemberState;
import com.homecookingshare.domain.member.Password;
import com.homecookingshare.domain.member.Profile;
import com.homecookingshare.domain.member.read.Member;
import com.homecookingshare.domain.recipe.Cooker;
import com.homecookingshare.domain.recipe.RecipeId;
import com.homecookingshare.domain.recipe.RecipeMainImage;
import com.homecookingshare.domain.recipe.RecipeTitle;
import com.homecookingshare.domain.recipe.Recipe.RecipeCategory;
import com.homecookingshare.domain.recipe.read.Recipe;

@Repository
@SuppressWarnings({"rawtypes","unchecked"})
public class RedisMemberReadRepository implements MemberReadRepository{
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Value("${redis.member.key}")
	private String MEMBER_KEY;
	
	@Value("${redis.member-list.key}")
	private String MEMBER_LIST_KEY;
	
	@Value("${redis.member-recipe-interest.key}")
	private String MEMBER_RECIPE_INTEREST_KEY;
	
	@Value("${redis.recipe.key}")
	private String RECIPE_KEY;
	
	@Override
	public void save(Member member) {
		Email email = member.getEmail();
		SetOperations opsForSet = redisTemplate.opsForSet();
		opsForSet.add(MEMBER_LIST_KEY, email.getValue());

		redisTemplate.delete(MEMBER_RECIPE_INTEREST_KEY + email.getValue());
		Set<RecipeId> interestRecipes = member.getInterestRecipes().getInterestRecipes();
		interestRecipes.forEach(recipeId->{
			opsForSet.add(MEMBER_RECIPE_INTEREST_KEY + email.getValue(), recipeId.getRecipeId());
		});
		
		HashOperations hashOperations = redisTemplate.opsForHash();
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		hashOperations.put(MEMBER_KEY + email.getValue(), "authType", member.getAuthType().toString());
		hashOperations.put(MEMBER_KEY + email.getValue(), "createDateTime", simpleDateFormat.format(member.getCreateDateTime()));
		hashOperations.put(MEMBER_KEY + email.getValue(), "password", member.getPassword().getValue());
		hashOperations.put(MEMBER_KEY + email.getValue(), "nickName", member.getProfile().getNickName());
		hashOperations.put(MEMBER_KEY + email.getValue(), "rule", member.getRule().toString());
		hashOperations.put(MEMBER_KEY + email.getValue(), "state", member.getState().toString());
		if(member.getProfile().getImg() != null) {
			hashOperations.put(MEMBER_KEY + email.getValue(), "image", member.getProfile().getImg());
		}
	}

	@Override
	public Optional<Member> findByEmail(Email email) {
		HashOperations hashOperations = redisTemplate.opsForHash();
		SetOperations setOperations = redisTemplate.opsForSet();
		
		if(hashOperations.get(MEMBER_KEY + email.getValue(), "rule") == null) {
			return Optional.ofNullable(null);
		}
		
		InterestRecipes interestRecipes = new InterestRecipes();

		setOperations.members(MEMBER_RECIPE_INTEREST_KEY + email.getValue()).forEach(recipeId -> {
			interestRecipes.interest(new Recipe(new RecipeId(recipeId.toString())));
		});
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			Member member = Member.builder()
					.email(email)
					.password(new Password(hashOperations.get(MEMBER_KEY + email.getValue(), "password").toString()))
					.authType(AuthType.valueOf(hashOperations.get(MEMBER_KEY + email.getValue(), "authType").toString()))
					.rule(MemberRule.valueOf(hashOperations.get(MEMBER_KEY + email.getValue(), "rule").toString()))
					.state(MemberState.valueOf(hashOperations.get(MEMBER_KEY + email.getValue(), "state").toString()))
					.profile(new Profile(hashOperations.get(MEMBER_KEY + email.getValue(), "nickName").toString()))
					.createDateTime(simpleDateFormat.parse(hashOperations.get(MEMBER_KEY + email.getValue(), "createDateTime").toString()))
					.interestRecipes(interestRecipes)
					.build();
			if(hashOperations.get(MEMBER_KEY + email.getValue(), "image") != null) {
				member.changeImage(hashOperations.get(MEMBER_KEY + email.getValue(), "image").toString());
			}
			return Optional.of(member);
		} catch (ParseException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public List<Recipe> findInterestRecipes(Email memberEmail) {
		List<Recipe> recipes = new ArrayList<>();
		SortQuery<String> query = SortQueryBuilder.sort(MEMBER_RECIPE_INTEREST_KEY + memberEmail.getValue())
				.get(RECIPE_KEY + "*->cooker")
				.get(RECIPE_KEY + "*->title")
				.get(RECIPE_KEY + "*->mainImage")
				.get(RECIPE_KEY + "*->category")
				.alphabetical(true)
				.build();
		List<Object> result = redisTemplate.sort(query);
		int totalElement = result.size();
		for(int i = 0;i < totalElement; i += 4) {
			Recipe recipe = Recipe.builder()
					.cooker(new Cooker(result.get(i).toString()))
					.title(new RecipeTitle(result.get(i + 1).toString()))
					.mainImage(new RecipeMainImage(result.get(i + 2).toString()))
					.category(RecipeCategory.valueOf(result.get(i + 3).toString()))
					.build();
			recipes.add(recipe);
		}
		return recipes;
	}

}
