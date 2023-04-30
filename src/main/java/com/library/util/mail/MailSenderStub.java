package com.library.util.mail;

import org.springframework.stereotype.Component;

// 가짜
@Component  // 1-1. IoC 컨테이너 등록
public class MailSenderStub implements MailSender {

	@Override
	public boolean send() {
		return true;
	}

}
