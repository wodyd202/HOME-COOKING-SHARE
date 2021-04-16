package com.homecookingshare.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.web.MockMultipartFile;

import com.homecookingshare.common.fileUpload.FileUploader;
import com.homecookingshare.member.aggregate.Member;
import com.homecookingshare.member.infrastructure.MemberEventRepository;
import com.homecookingshare.member.infrastructure.MemberQueryRepository;
import com.homecookingshare.member.service.eventListener.MemberCommandListener;
import com.homecookingshare.member.service.eventListener.MemberQueryListener;
import com.homecookingshare.member.service.register.RegisterMember;
import com.homecookingshare.member.service.update.ChangeMemberImage;
import com.homecookingshare.member.service.update.ChangeMemberImageValidator;
import com.homecookingshare.member.service.update.MemberChangeImageService;

public class MemberChangeImgServiceTest {

	ChangeMemberImageValidator changeMemberImageValidator;
	MemberEventRepository memberEventRepository;
	MemberQueryRepository memberQueryRepository;
	MemberChangeImageService memberChangeImageService;
	FileUploader fileUploader;

	@BeforeEach
	void setUp() {
		fileUploader = mock(FileUploader.class);
		changeMemberImageValidator = new ChangeMemberImageValidator();

		memberEventRepository = new FakeMemberEventRepository();
		memberQueryRepository = new FakeMemberQueryRepository();

		MemberCommandListener memberCommandListener = new MemberCommandListener(memberEventRepository);
		MemberQueryListener memberQueryListener = new MemberQueryListener(memberQueryRepository);

		ApplicationEventPublisher publisher = new MockApplicationEventPublisher(memberCommandListener,
				memberQueryListener);

		memberChangeImageService = new MemberChangeImageService(changeMemberImageValidator, memberQueryRepository,
				fileUploader, publisher);

		RegisterMember registerMember = RegisterMember.builder().email("wodyd202@naver.com").nickName("닉네임")
				.password("password").build();
		memberQueryRepository.save(new Member(registerMember));
	}

	@Test
	@DisplayName("정상 변경 케이스")
	void test_1() {
		ChangeMemberImage changeMemberImage = ChangeMemberImage.builder().changer("wodyd202@naver.com")
				.image(new MockMultipartFile("file.png", new byte[] {})).build();
		memberChangeImageService.update(changeMemberImage);
		Optional<Member> findByEmail = memberQueryRepository.findByEmail(new Email("wodyd202@naver.com"));
		if (!findByEmail.isPresent()) {
			fail();
		}
		assertThat(findByEmail.get().getProfile().getImg()).isNotNull();
	}
}
