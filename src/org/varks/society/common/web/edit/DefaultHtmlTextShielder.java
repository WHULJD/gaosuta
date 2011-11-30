package org.varks.society.common.web.edit;

public class DefaultHtmlTextShielder implements HtmlTextShielder {

	@Override
	public String shield(String text) {
		text = text.replaceAll("<", "&lt");
		text = text.replace(">", "&gt");
		
		return text;
	}
	
}
