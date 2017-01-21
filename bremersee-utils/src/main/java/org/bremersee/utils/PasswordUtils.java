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

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

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
     * <li>{@code PARTIAL_REGEX_CHECKS[0]} : lower chars ({@code a-z})</li>
     * <li>{@code PARTIAL_REGEX_CHECKS[1]} : upper chars ({@code A-Z})</li>
     * <li>{@code PARTIAL_REGEX_CHECKS[2]} : numbers ({@code 0-9})</li>
     * <li>{@code PARTIAL_REGEX_CHECKS[3]} : symbols (
     * {@code ~!@#$%^&*_-+=`|\(){}[]:;"'<>,.?/})</li>
     * </ul>
     */
    @SuppressWarnings("WeakerAccess")
    public static final String[] PARTIAL_REGEX_CHECKS = { // NOSONAR
            ".*[a-z]+.*", // lower
            ".*[A-Z]+.*", // upper
            ".*[\\d]+.*", // digits
            ".*[~!@#$%^&*_\\-+=`|\\\\(){}\\[\\]:;\"'<>,.?/]+.*" // symbols
    };

    /**
     * Symbols
     */
    private static final String SYMBOLS = "~!@#$%&*_-+=(){}[]:;'<>,.?";
    //private static final String SYMBOLS = "~!@#$%^&*_-+=`|\\(){}[]:;\"'<>,.?/"; // SONAR

    /**
     * Numbers
     */
    private static final String NUMBERS = "123456789"; // WITHOUT '0' because 'O' looks like it.

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

    private static final int PASSWORD_CHARS_LENGTH_WITHOUT_SYMBOLS;

    private static final NumberFormat QUALITY_RESULT_NUMBER_FORMATTER = NumberFormat.getNumberInstance(Locale.US);

    static {
        final StringBuilder sb = new StringBuilder();
        final char A = 'A'; // NOSONAR
        final char Z = 'Z'; // NOSONAR
        for (int i = (int) A; i <= (int) Z; i++) {
            sb.append((char) i);
        }
        UPPER_CHARS = sb.toString();

        final char a = 'a';
        final char z = 'z';
        for (int i = (int) a; i <= (int) z; i++) {
            sb.append((char) i);
        }
        LOWER_CHARS = sb.toString().substring(UPPER_CHARS.length());

        sb.append(NUMBERS);
        PASSWORD_CHARS_LENGTH_WITHOUT_SYMBOLS = sb.length();
        sb.append(SYMBOLS);

        PASSWORD_CHARS = new char[sb.length()];
        sb.getChars(0, sb.length(), PASSWORD_CHARS, 0);

        QUALITY_RESULT_NUMBER_FORMATTER.setGroupingUsed(false);
        QUALITY_RESULT_NUMBER_FORMATTER.setMaximumFractionDigits(2);
        QUALITY_RESULT_NUMBER_FORMATTER.setMinimumIntegerDigits(1);
    }

    /**
     * Never construct.
     */
    private PasswordUtils() {
        super();
    }

    /**
     * Create a new random password (with symbols and a variable length greater than 14).
     * 
     * @return a new random password
     */
    public static String createRandomClearPassword() {
        return createRandomClearPassword(true);
    }

    /**
     * Create a new random password (with or without symbols and a variable length greater than 14).
     *
     * @param withSymbols with or without symbols?
     * @return a new random password
     */
    @SuppressWarnings({"WeakerAccess", "SameParameterValue"})
    public static String createRandomClearPassword(final boolean withSymbols) {
        return createRandomClearPassword(14, true, withSymbols);
    }

    /**
     * Create a new random password with symbols and the specified length.
     * 
     * @param length
     *            the length of the password
     * @return a new random password
     */
    @SuppressWarnings("unused")
    public static String createRandomClearPassword(final int length) {
        return createRandomClearPassword(length, false, true);
    }

    /**
     * Creates a new random password.
     * @param length the minimum length of the password
     * @param withVariableLength should the length be variable?
     * @param withSymbols should the password contains symbols?
     * @return the new random password
     */
    @SuppressWarnings("WeakerAccess")
    public static String createRandomClearPassword(final int length, // NOSONAR
                                                   final boolean withVariableLength,
                                                   final boolean withSymbols) {

        final Random random = new Random();
        final int len;
        if (length < 0) {
            len = 0;
        } else {
            if (withVariableLength) {
                final int varLength = random.nextInt (length + 1);
                len = length + varLength;
            } else {
                len = length;
            }
        }
        final char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = findChar(withSymbols, random);
        }
        if (len > 0 && !containsLowerCase(chars)) {
            char c = 'l';
            while (c == 'l') {
                final int n = random.nextInt(LOWER_CHARS.length());
                c = LOWER_CHARS.charAt(n);
            }
            chars[0] = c;
        }
        if (len > 1 && !containsUpperCase(chars)) {
            char c = 'I';
            while (c == 'I') {
                final int n = random.nextInt(UPPER_CHARS.length());
                c = UPPER_CHARS.charAt(n);
            }
            chars[1] = c;
        }
        if (len > 2 && !containsNumber(chars)) {
            final int n = random.nextInt(NUMBERS.length());
            char c = NUMBERS.charAt(n);
            chars[2] = c;
        }
        if (withSymbols && len > 3 && !containsSymbol(chars)) {
            final int n = random.nextInt(SYMBOLS.length());
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
    @SuppressWarnings("SameParameterValue")
    public static double getPasswordQuality(final String clearPassword, final Integer minLength) {

        if (StringUtils.isBlank(clearPassword) || (minLength != null && clearPassword.length() < minLength)) {
            return 0.;
        }
        final double value = 1. / PARTIAL_REGEX_CHECKS.length;
        double result = 0.;
        for (String PARTIAL_REGEX_CHECK : PARTIAL_REGEX_CHECKS) {
            if (Pattern.matches(PARTIAL_REGEX_CHECK, clearPassword)) {
                result = result + value;
            }
        }
        return new BigDecimal(QUALITY_RESULT_NUMBER_FORMATTER.format(result)).doubleValue();
    }

    private static char findChar(final boolean withSymbols, final Random random) {
        final Random ran = random == null ? new Random() : random;
        final int len = withSymbols ? PASSWORD_CHARS.length : PASSWORD_CHARS_LENGTH_WITHOUT_SYMBOLS;
        final int n = ran.nextInt(len);
        final char c = PASSWORD_CHARS[n];
        if (c != 'l' && c != 'I' && c != 'O') {
            return c;
        }
        return findChar(withSymbols, ran);
    }

    private static boolean containsLowerCase(final char[] cs) {
        final String s = cs == null ? "" : new String(cs);
        return s.matches(PARTIAL_REGEX_CHECKS[0]);
    }

    private static boolean containsUpperCase(final char[] cs) {
        final String s = cs == null ? "" : new String(cs);
        return s.matches(PARTIAL_REGEX_CHECKS[1]);
    }

    private static boolean containsNumber(final char[] cs) {
        final String s = cs == null ? "" : new String(cs);
        return s.matches(PARTIAL_REGEX_CHECKS[2]);
    }

    private static boolean containsSymbol(final char[] cs) {
        final String s = cs == null ? "" : new String(cs);
        return s.matches(PARTIAL_REGEX_CHECKS[3]);
    }

}
