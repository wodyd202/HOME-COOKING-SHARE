package com.homecookingshare.query.member.projector;

import com.homecookingshare.domain.authKey.event.AuthSuccessed;
import com.homecookingshare.domain.member.event.ChangedMemberImage;
import com.homecookingshare.domain.member.event.ChangedMemberPassword;
import com.homecookingshare.domain.member.event.RegisterdMember;

public interface MemberProjector {
	void on(RegisterdMember event);

	void on(AuthSuccessed event);

	void on(ChangedMemberImage event);

	void on(ChangedMemberPassword event);
}
