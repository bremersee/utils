/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bremersee.utils;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * Some methods to work with passwords.
 * </p>
 * 
 * @author Christian Bremer
 */
public abstract class PasswordUtils {

    /**
     * An array with regular expressions to validate the quality of a password:
     * <ul>
     * <li>{@code PARTIAL_REGEX_CHECKS[0] : lower chars ({@code a-z})</li>
     * <li>{@code PARTIAL_REGEX_CHECKS[1] : upper chars ({@code A-Z})</li>
     * <li>{@code PARTIAL_REGEX_CHECKS[2] : numbers ({@code 0-9})</li>
     * <li>{@code PARTIAL_REGEX_CHECKS[3] : symbols (
     * {@code ~!@#$%^&*_-+=`|\(){}[]:;"'<>,.?/})</li>
     * </ul>
     */
    public static final String[] PARTIAL_REGEX_CHECKS = { ".*[a-z]+.*", // lower
            ".*[A-Z]+.*", // upper
            ".*[\\d]+.*", // digits
            ".*[~!@#$%^&*_\\-+=`|\\\\(){}\\[\\]:;\"'<>,.?/]+.*" // symbols
    };

    /**
     * Symbols
     */
    private static final String SYMBOLS = "~!@#$%^&*_-+=`|\\(){}[]:;\"'<>,.?/";

    /**
     * Numbers
     */
    private static final String NUMBERS = "0123456789";

    /**
     * Upper characters
     */
    private static final String UPPER_CHARS;

    /**
     * Lower characters
     */
    private static final String LOWER_CHARS;

    /**
     * All password characters
     */
    private static final char[] PASSWORD_CHARS;

    /**
     * Build upper chars, lower chars and all password characters.
     */
    static {
        StringBuilder sb = new StringBuilder();
        char A = 'A';
        char Z = 'Z';
        for (int i = (int) A; i <= (int) Z; i++) {
            sb.append((char) i);
        }
        UPPER_CHARS = sb.toString();

        char a = 'a';
        char z = 'z';
        for (int i = (int) a; i <= (int) z; i++) {
            sb.append((char) i);
        }
        LOWER_CHARS = sb.toString().substring(UPPER_CHARS.length());

        sb.append(NUMBERS);
        sb.append(SYMBOLS);

        PASSWORD_CHARS = new char[sb.length()];
        sb.getChars(0, sb.length(), PASSWORD_CHARS, 0);
    }

    private PasswordUtils() {
        // utility class, never constructed
    }

    /**
     * Create a new random password.
     * 
     * @return a new random password
     */
    public static String createRandomClearPassword() {
        int length = 14;
        int varLength = Double.valueOf(Math.floor(Math.random() * 15)).intValue();
        return createRandomClearPassword(length + varLength);
    }

    /**
     * Create a new random password with the specified length.
     * 
     * @param length
     *            the length of the password
     * @return a new random password
     */
    public static String createRandomClearPassword(int length) {
        if (length < 0) {
            length = 0;
        }
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = findChar();
        }
        if (length > 0 && !containsLowerCase(chars)) {
            char c = 'l';
            while (c == 'l') {
                int n = Double.valueOf(Math.floor(Math.random() * LOWER_CHARS.length())).intValue();
                c = LOWER_CHARS.charAt(n);
            }
            chars[0] = c;
        }
        if (length > 1 && !containsUpperCase(chars)) {
            char c = 'I';
            while (c == 'I') {
                int n = Double.valueOf(Math.floor(Math.random() * UPPER_CHARS.length())).intValue();
                c = UPPER_CHARS.charAt(n);
            }
            chars[1] = c;
        }
        if (length > 2 && !containsNumber(chars)) {
            int n = Double.valueOf(Math.floor(Math.random() * NUMBERS.length())).intValue();
            char c = NUMBERS.charAt(n);
            chars[2] = c;
        }
        if (length > 3 && !containsSymbol(chars)) {
            int n = Double.valueOf(Math.floor(Math.random() * SYMBOLS.length())).intValue();
            char c = SYMBOLS.charAt(n);
            chars[3] = c;
        }
        return new String(chars);
    }

    /**
     * Calculates the quality of the password.
     * 
     * @param clearPassword
     *            the clear password
     * @param minLength
     *            the minimum length of the password (optional)
     * @return a value between 0 (bad quality) and 1 (very good quality
     */
    public static double getPasswordQuality(String clearPassword, Integer minLength) {
        if (StringUtils.isBlank(clearPassword) || (minLength != null && clearPassword.length() < minLength)) {
            return 0.;
        }
        double value = 1. / PARTIAL_REGEX_CHECKS.length;
        double result = 0.;
        for (int i = 0; i < PARTIAL_REGEX_CHECKS.length; i++) {
            if (Pattern.matches(PARTIAL_REGEX_CHECKS[i], clearPassword)) {
                result = result + value;
            }
        }
        return Double.valueOf(result).floatValue();
    }

    private static char findChar() {
        int n = Double.valueOf(Math.floor(Math.random() * PASSWORD_CHARS.length)).intValue();
        char c = PASSWORD_CHARS[n];
        if (c != 'l' && c != 'I') {
            return c;
        }
        return findChar();
    }

    private static boolean containsLowerCase(char[] cs) {
        String s = cs == null ? "" : new String(cs);
        return s.matches(PARTIAL_REGEX_CHECKS[0]);
    }

    private static boolean containsUpperCase(char[] cs) {
        String s = cs == null ? "" : new String(cs);
        return s.matches(PARTIAL_REGEX_CHECKS[1]);
    }

    private static boolean containsNumber(char[] cs) {
        String s = cs == null ? "" : new String(cs);
        return s.matches(PARTIAL_REGEX_CHECKS[2]);
    }

    private static boolean containsSymbol(char[] cs) {
        String s = cs == null ? "" : new String(cs);
        return s.matches(PARTIAL_REGEX_CHECKS[3]);
    }

}
