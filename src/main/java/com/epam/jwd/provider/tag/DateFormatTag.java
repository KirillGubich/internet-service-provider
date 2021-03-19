package com.epam.jwd.provider.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateFormatTag extends TagSupport {
    private LocalDate date;
    private String lang;

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter
                    .ofLocalizedDate(FormatStyle.MEDIUM)
                    .withLocale(new Locale(lang));
            String formattedDate = formatter.format(date);
            pageContext.getOut().write(formattedDate);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
