package com.something.riskmanagement.common.util.message;

import com.something.riskmanagement.common.BaseEnum;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.List;
import java.util.Locale;

/**
 * Created by h_gohargazi
 * on 9/5/2023
 */

public class MessageUtil {

    private final MessageSource messageSource;

    public MessageUtil(List<String> messageFileNames) {
        ResourceBundleMessageSource aMessageSource = new ResourceBundleMessageSource();
        String[] messageFileNamesArray = new String[messageFileNames.size()];
        aMessageSource.setBasenames(messageFileNames.toArray(messageFileNamesArray));
        aMessageSource.setDefaultEncoding("UTF-8");
        this.messageSource = aMessageSource;
    }

    public String getMessage(String message, Object[] entries, Locale locale) {
        try {
            return messageSource.getMessage(message, entries, locale);
        } catch (NoSuchMessageException e) {
            return message;
        }
    }

    public String getMessage(String message, Object[] entries, String defaultMessage, Locale locale) {
        return messageSource.getMessage(message, entries, defaultMessage, locale);
    }

    public String getEnumValue(BaseEnum e, Locale locale) {
        return messageSource.getMessage(e.getValue(), new Object[0], e.getValue(), locale);
    }

    public String getEnumValue(Enum e, Locale locale) {
        return messageSource.getMessage(e.name(), new Object[0], e.name(), locale);
    }
}
