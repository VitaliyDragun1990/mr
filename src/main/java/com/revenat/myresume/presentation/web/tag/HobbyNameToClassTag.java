package com.revenat.myresume.presentation.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class HobbyNameToClassTag extends SimpleTagSupport {

	private String hobbyName;
	private String var;
	public String getHobbyName() {
		return hobbyName;
	}
	public void setHobbyName(String hobbyName) {
		this.hobbyName = hobbyName;
	}
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		String hobbyClassName = "hobby-" + hobbyName.replace(" ", "-").toLowerCase();
		getJspContext().setAttribute(var, hobbyClassName, PageContext.REQUEST_SCOPE);
	}
	
}
