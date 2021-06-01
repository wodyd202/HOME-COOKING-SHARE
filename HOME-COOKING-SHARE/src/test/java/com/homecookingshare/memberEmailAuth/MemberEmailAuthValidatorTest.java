package com.homecookingshare.memberEmailAuth;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.homecookingshare.command.memberEmailAuth.exception.InvalidEmailAuthenticationException;
import com.homecookingshare.command.memberEmailAuth.infra.validator.EmailAuthenticationValidator;
import com.homecookingshare.command.memberEmailAuth.model.MemberEmailAuthCommand;
import com.homecookingshare.common.Validator;

public class MemberEmailAuthValidatorTest {
	
	Validator<MemberEmailAuthCommand.Verifycation> validator = new EmailAuthenticationValidator();
	
	@Test
	void 인증키_누락() {
		MemberEmailAuthCommand.Verifycation command = new MemberEmailAuthCommand.Verifycation("test@naver.com","");
		assertThrows(InvalidEmailAuthenticationException.class, ()->{
			validator.validate(command);
		});
	}
	
	@Test
	void 인증자_이메일_누락() {
		MemberEmailAuthCommand.Verifycation command = new MemberEmailAuthCommand.Verifycation("","fkdsjhfsjkd");
		assertThrows(InvalidEmailAuthenticationException.class, ()->{
			validator.validate(command);
		});
	}
	
	@Test
	void 값_정상_입력() {
		MemberEmailAuthCommand.Verifycation command = new MemberEmailAuthCommand.Verifycation("test@naver.com","fkdsjhfsjkd");
		validator.validate(command);
	}
}
