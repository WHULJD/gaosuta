package org.varks.society.local.business.login;

import org.varks.society.local.entities.Login;

public class LoginTranslator {
	private PasswordTranslator passwordTranslator;
	
	public Login foreToEntity(ForeLogin foreLogin) {
		String userName = foreLogin.getUserName();
		String password = passwordEncode(foreLogin.getPassword());
		return new Login(userName, password);
	}
	
//	public ForeLogin entityToFore(Login login) {
//		String userName = login.getUserName();
//		String password = passwordDecode(login.getPassword());
//		return new ForeLogin(userName, password);
//	}
	
	//对前端密码进行加密
	protected String passwordEncode(String password) {
		return passwordTranslator.encode(password);
	}

	//对后台密码进行解密
//	protected String passwordDecode(String password) {
//		return passwordTranslator.decode(password);
//	}

	public PasswordTranslator getPasswordTranslator() {
		return passwordTranslator;
	}

	public void setPasswordTranslator(PasswordTranslator passwordTranslator) {
		this.passwordTranslator = passwordTranslator;
	}
	
	
}
