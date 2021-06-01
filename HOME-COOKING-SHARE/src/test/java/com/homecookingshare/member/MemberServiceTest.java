package com.homecookingshare.member;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.homecookingshare.command.member.exception.AlreadyDeletedMemberException;
import com.homecookingshare.command.member.exception.AlreadyExistMemberException;
import com.homecookingshare.command.member.infra.JpaMemberRepository;
import com.homecookingshare.command.member.model.MemberCommand.ChangeImage;
import com.homecookingshare.command.member.model.MemberCommand.ChangePassword;
import com.homecookingshare.command.member.model.MemberCommand.RegisterMember;
import com.homecookingshare.command.member.service.MemberService;
import com.homecookingshare.command.member.service.SimpleMemberService;
import com.homecookingshare.common.Validator;
import com.homecookingshare.common.fileUpload.FileUploader;
import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.Member;

@SuppressWarnings("unchecked")
public class MemberServiceTest {
	
	JpaMemberRepository memberRepository = mock(JpaMemberRepository.class);
	MemberService memberService = new SimpleMemberService(mock(PasswordEncoder.class), memberRepository);
	
	@Test
	void 사용자_등록() {
		RegisterMember command = new RegisterMember("test@naver.com", "passwordPass", "nickName");
		memberService.create(mock(Validator.class), command);
		
		verify(memberRepository,times(1))
				.save(any(Member.class));
	}
	
	@Test
	void 사용자_중복_실패() {
		RegisterMember command = new RegisterMember("test@naver.com", "passwordPass", "nickName");

		Member mockMember = mock(Member.class);
		
		when(memberRepository.findById(new Email("test@naver.com")))
			.thenReturn(Optional.of(mockMember));

		assertThrows(AlreadyExistMemberException.class, ()->{
			memberService.create(mock(Validator.class), command);
		});
	}

	@Test
	void 이미_회원_탈퇴한_사람이_다시_가입하는_경우_실패() {
		RegisterMember command = new RegisterMember("test@naver.com", "passwordPass", "nickName");
		
		Member mockMember = mock(Member.class);
		when(mockMember.isDeleted())
				.thenReturn(true);
		
		when(memberRepository.findById(new Email("test@naver.com")))
			.thenReturn(Optional.of(mockMember));
		
		assertThrows(AlreadyDeletedMemberException.class, ()->{
			memberService.create(mock(Validator.class), command);
		});
	}
	
	@Test
	void 사용자_이미지_변경() {
		MultipartFile imageFile = new MockMultipartFile("fsdjkhfsd.jpg", new byte[] {});

		ChangeImage command = new ChangeImage(imageFile);
		
		FileUploader mockFileUploader = mock(FileUploader.class);

		when(memberRepository.findById(new Email("test@naver.com")))
			.thenReturn(Optional.of(mock(Member.class)));
		
		memberService.changeImage(mockFileUploader ,mock(Validator.class), new Email("test@naver.com"), command);

		verify(mockFileUploader,times(1))
			.uploadFile(any(), any());
	}
	
	@Test
	void 사용자_비밀번호_변경() {
		ChangePassword command = new ChangePassword("originPassword", "changePassword");
		
		when(memberRepository.findById(new Email("test@naver.com")))
			.thenReturn(Optional.of(mock(Member.class)));
		
		memberService.changePassword(mock(Validator.class), new Email("test@naver.com"), command);
	}
	
}
