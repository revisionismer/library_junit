package com.library.util.mail;

import org.springframework.stereotype.Component;

@Component
public class MailSenderAdapter implements MailSender {
	
	private Mail mail;

	public MailSenderAdapter() {
		this.mail = new Mail();
	}

	@Override
	public boolean send() {
		return mail.sendMail();
	}

}
