package com.homecookingshare.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.homecookingshare.member.domain.Email;
import com.homecookingshare.member.domain.aggregate.Member;
import com.homecookingshare.member.domain.aggregate.exception.InvalidMemberException;
import com.homecookingshare.member.service.register.RegisterMember;
import com.homecookingshare.member.service.update.ChangeImageMember;

public class MemberUpdateServiceTest extends MemberTest {

	@Test
	@DisplayName("이미지 파일이 존재하지 않을 때_에러")
	void update_3() {
		assertThrows(InvalidMemberException.class, ()->{
			ChangeImageMember updateMember = ChangeImageMember
					.builder()
					.email("wodyd202@naver.com")
					.build();
			memberUpdateService.register(updateMember);
		});
	}
	
	@Test
	@DisplayName("이미지 파일이 아닐 때_에러")
	void update_2() {
		assertThrows(InvalidMemberException.class, ()->{
			ChangeImageMember updateMember = ChangeImageMember
					.builder()
					.email("wodyd202@naver.com")
					.img(new MockMultipartFile("file.exe",new byte[] {}))
					.build();
			memberUpdateService.register(updateMember);
		});
	}
	
	@Test
	@DisplayName("정상 수정 케이스")
	void update_1() {
		registerMember(RegisterMember.builder().email("wodyd202@naver.com").nickName("닉네임").password("password").build());
		ChangeImageMember updateMember = ChangeImageMember
				.builder()
				.email("wodyd202@naver.com")
				.img(new MockMultipartFile("file.png",new byte[] {}))
				.build();
		memberUpdateService.register(updateMember);
		Optional<Member> findByEmail = memberRepository.findByEmail(new Email("wodyd202@naver.com"));
		if(!findByEmail.isPresent()) {
			fail();
		}
		Member member = findByEmail.get();
		assertThat(member.getProfile().getImg()).isNotNull();
	}
}
