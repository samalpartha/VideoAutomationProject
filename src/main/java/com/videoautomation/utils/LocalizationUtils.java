package com.videoautomation.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Localization Utilities (Chapter 14)
 * Handles pseudo-localization for Video Automation.
 * Shared logic concept, duplicated here for project independence.
 */
public class LocalizationUtils {

    private static final Map<Character, Character> CHAR_MAP = new HashMap<>();

    static {
        CHAR_MAP.put('a', 'á');
        CHAR_MAP.put('A', 'Á');
        CHAR_MAP.put('e', 'é');
        CHAR_MAP.put('E', 'É');
        CHAR_MAP.put('i', 'í');
        CHAR_MAP.put('I', 'Í');
        CHAR_MAP.put('o', 'ó');
        CHAR_MAP.put('O', 'Ó');
        CHAR_MAP.put('u', 'ú');
        CHAR_MAP.put('U', 'Ú');
        CHAR_MAP.put('c', 'ç');
        CHAR_MAP.put('C', 'Ç');
        CHAR_MAP.put('n', 'ñ');
        CHAR_MAP.put('N', 'Ñ');
        CHAR_MAP.put('y', 'ý');
        CHAR_MAP.put('Y', 'Ý');
    }

    /**
     * Transforms text into pseudo-localized format.
     */
    public static String pseudoLocalize(String text) {
        return pseudoLocalize(text, 1.3);
    }

    public static String pseudoLocalize(String text, double expansionFactor) {
        StringBuilder transformed = new StringBuilder();
        for (char c : text.toCharArray()) {
            transformed.append(CHAR_MAP.getOrDefault(c, c));
        }

        int targetLength = (int) Math.ceil(text.length() * expansionFactor);
        int paddingLength = Math.max(0, targetLength - text.length());

        StringBuilder padding = new StringBuilder();
        for (int i = 0; i < paddingLength; i++) {
            padding.append("!");
        }

        return String.format("[%s %s]", transformed, padding);
    }
}
