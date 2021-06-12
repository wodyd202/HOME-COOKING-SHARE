package com.homecookingshare.query.member.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homecookingshare.config.security.LoginUser;
import com.homecookingshare.domain.member.read.Member;
import com.homecookingshare.domain.recipe.read.Recipe;
import com.homecookingshare.query.member.infra.MemberReadRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "사용자 조회 관련 API")
@RestController
@RequestMapping("api/v1/member")
@AllArgsConstructor
public class MemberQueryApi {
	private MemberReadRepository memberReadRepository;
	
	@ApiOperation("관심 준 레시피 목록 가져오기")
	@GetMapping("interest-recipes")
	public ResponseEntity<List<Recipe>> getInterestRecipes(
			@ApiIgnore
			@LoginUser Member loginMember
		){
		List<Recipe> findInterestRecipes = memberReadRepository.findInterestRecipes(loginMember.getEmail());
		return new ResponseEntity<>(findInterestRecipes, HttpStatus.OK);
	}
}
