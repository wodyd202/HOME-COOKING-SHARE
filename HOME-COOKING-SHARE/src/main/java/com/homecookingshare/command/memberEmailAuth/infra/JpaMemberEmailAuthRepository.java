package com.homecookingshare.command.memberEmailAuth.infra;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.homecookingshare.domain.authKey.MemberEmailAuthKey;
import com.homecookingshare.domain.authKey.TargetEmail;

public interface JpaMemberEmailAuthRepository extends CrudRepository<MemberEmailAuthKey, Long> {
	Optional<MemberEmailAuthKey> findTop1ByEmailOrderBySeqDesc(TargetEmail targetEmail);
}
