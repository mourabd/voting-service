package com.subjects.votingservice.util;

/**
 * Class of utils.
 */
@SuppressWarnings("PMD.UseLocaleWithCaseConversions")
public class Utils {

    /**
     * Generates code based on given pattern.
     *
     * @param text source for generated code
     * @return generated code
     */
    public static String generateCode(String text) {
        return text.isBlank() ? "" : text.toUpperCase().replace(' ', '-');
    }
}
