package com.revenat.myresume.presentation.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.revenat.myresume.application.util.DataUtil;
import com.revenat.myresume.domain.entity.LanguageLevel;

/**
 * For each valid {@link LanguageLevel#getLevel()} provide appropriate
 * {@code ordinal} and {@code caption} representation.
 * 
 * @author Vitaliy Dragun
 *
 */
public class LanguageLevelDataTag extends SimpleTagSupport {

	private String level;
	private String varOrdinal;
	private String varCaption;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getVarOrdinal() {
		return varOrdinal;
	}

	public void setVarOrdinal(String varOrdinal) {
		this.varOrdinal = varOrdinal;
	}

	public String getVarCaption() {
		return varCaption;
	}

	public void setVarCaption(String varCaption) {
		this.varCaption = varCaption;
	}

	@Override
	public void doTag() throws JspException, IOException {
		LanguageLevel languageLevel = LanguageLevel.getLevel(level);
		String caption = DataUtil.capitalizeName(languageLevel.getLevel()).replace("_", "-");
		Integer ordinal = languageLevel.ordinal();

		getJspContext().setAttribute(varCaption, caption, PageContext.REQUEST_SCOPE);
		getJspContext().setAttribute(varOrdinal, ordinal, PageContext.REQUEST_SCOPE);
	}

}
