package app.config;

import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class PropertiesTextManager {

    private static final String DEFAULT_BUNDLE = "messages";

    public static String get(String key) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(DEFAULT_BUNDLE, Locale.getDefault());
            return bundle.getString(key);
        } catch (Exception e) {
            return String.format("[ %s ] %s", key, e.getMessage());
        }
    }

    public static String get(String key, Locale locale) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(DEFAULT_BUNDLE, locale);
            return bundle.getString(key);
        } catch (Exception e) {
            return String.format("[ %s ] %s", key, e.getMessage());
        }
    }


}

