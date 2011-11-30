package org.varks.society.local.business.login;

import org.springframework.security.authentication.encoding.PasswordEncoder;


public class DefaultPasswordTranslator implements PasswordTranslator {

	private PasswordEncoder encoder;
	
	@Override
	public String encode(String forePassword) {
		return encoder.encodePassword(forePassword, "userName");
//		return forePassword;
	}

//	@Override
//	public String decode(String entityPassword) {
//		return entityPassword;
//	}

	public void setEncoder(PasswordEncoder encoder) {
		this.encoder = encoder;
	}

	public PasswordEncoder getEncoder() {
		return encoder;
	}

}
