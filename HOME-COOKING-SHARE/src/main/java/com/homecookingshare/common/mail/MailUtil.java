package com.homecookingshare.common.mail;

public interface MailUtil {
	void sendEmail(String toAddress, String subject, String body);
}
