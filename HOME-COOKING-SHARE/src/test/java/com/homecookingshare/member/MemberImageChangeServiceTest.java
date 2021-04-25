package com.homecookingshare.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationListener;
import org.springframework.mock.web.MockMultipartFile;

import com.homecookingshare.MockApplicationEventPublisher;
import com.homecookingshare.command.member.entity.Member;
import com.homecookingshare.command.member.infrastructure.MemberRepository;
import com.homecookingshare.command.member.service.register.RegisterMemberCommand;
import com.homecookingshare.command.member.service.update.MemberImageChangeCommand;
import com.homecookingshare.command.member.service.update.MemberImageChangeService;
import com.homecookingshare.command.member.service.update.MemberImageChangeValidator;
import com.homecookingshare.command.member.values.Email;
import com.homecookingshare.common.fileUpload.FileUploader;
import com.homecookingshare.member.fake.FakeMemberQueryRepository;
import com.homecookingshare.member.fake.FakeMemberRepository;
import com.homecookingshare.query.eventHandler.MemberImageChangeEventHandler;
import com.homecookingshare.query.infrastructure.MemberQueryRepository;
import com.homecookingshare.query.projections.QueryMember;

public class MemberImageChangeServiceTest {

	MemberRepository memberRepository;
	MemberQueryRepository memberQueryRepository;
	MemberImageChangeService memberImageChangeService;
	MemberImageChangeValidator memberImageChangeValidator;
	
	@BeforeEach
	void setUp() {
		memberQueryRepository = new FakeMemberQueryRepository();
		memberRepository = new FakeMemberRepository();
		FileUploader fileUploader = mock(FileUploader.class);
		memberImageChangeValidator = new MemberImageChangeValidator();
		
		ApplicationListener<?> applicationListeners = new MemberImageChangeEventHandler(memberQueryRepository);
		memberImageChangeService = new MemberImageChangeService(new MockApplicationEventPublisher(applicationListeners),memberImageChangeValidator,memberRepository,fileUploader);
		
		RegisterMemberCommand command = RegisterMemberCommand.builder()
				.email("wodyd202@naver.com")
				.nickName("wodyd")
				.password("password").build();
		Member member = new Member(command);
		memberQueryRepository.save(new QueryMember(member));
		memberRepository.save(member);
	}
	
	@Test
	@DisplayName("사용자 수정 테스트")
	void test_1() {
		memberImageChangeService.register(new MemberImageChangeCommand(new Email("wodyd202@naver.com"),new MockMultipartFile("file.png", new byte[] {})));
		Optional<Member> findByEmail = memberRepository.findByEmail(new Email("wodyd202@naver.com"));
		assertThat(findByEmail.isPresent()).isTrue();
		assertThat(findByEmail.get().getProfile().getImg()).isNotNull();
	}
}
