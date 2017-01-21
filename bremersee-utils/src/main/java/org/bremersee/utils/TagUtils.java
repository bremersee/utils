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

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * <p>
 * Some methods to work with a tags.
 * </p>
 *
 * @author Christian Bremer
 */
public abstract class TagUtils {

    /**
     * Never construct.
     */
    private TagUtils() {
        super();
    }

    private static String doCaseFormat(final String text, final CaseFormat caseFormat) {
        if (text == null) {
            return null;
        }
        final CaseFormat format = caseFormat == null ? CaseFormat.UNTOUCHED : caseFormat;
        switch (format) {
            case TO_LOWER_CASE:
                return text.toLowerCase();
            case TO_UPPER_CASE:
                return text.toUpperCase();
            default:
                return text;
        }
    }

    /**
     * Build tags from a string. Each tag must have a minimum length of
     * {@code 2}.
     *
     * @param freeText the text
     * @return the tags
     */
    @SuppressWarnings("unused")
    public static String[] buildTags(final String freeText) {
        return buildTags(freeText, 2);
    }

    /**
     * Build tags from a text. Each tag must have a minimum length of the specified value.
     * If a token should contain space characters (e. g. {@code 'free wifi'}),
     * replace the spaces with a plus character (e. g. {@code 'free+wifi'}).
     *
     * @param freeText             the text
     * @param minLengthOfSingleTag the minimum length
     * @return the tags
     */
    @SuppressWarnings("WeakerAccess")
    public static String[] buildTags(final String freeText, final int minLengthOfSingleTag) {
        return buildTags(freeText, minLengthOfSingleTag, CaseFormat.UNTOUCHED);
    }

    /**
     * Build tags from a text. Each tag must have a minimum length of the specified value.
     * If a token should contain space characters (e. g. {@code 'free wifi'}),
     * replace the spaces with a plus character (e. g. {@code 'free+wifi'}).
     *
     * @param freeText             the text
     * @param minLengthOfSingleTag the minimum length
     * @param caseFormat           should the tags be lower case, upper case or untouched?
     * @return the tags
     */
    @SuppressWarnings("WeakerAccess")
    public static String[] buildTags(final String freeText, final int minLengthOfSingleTag, final CaseFormat caseFormat) {

        final int minLen = minLengthOfSingleTag <= 0 ? 1 : minLengthOfSingleTag;
        if (freeText == null || freeText.trim().length() < minLen) {
            return new String[0];
        }
        final List<String> tokens = new LinkedList<>();
        final StringTokenizer tokenizer = new StringTokenizer(freeText);
        while (tokenizer.hasMoreTokens()) {

            final String[] plusTokens = tokenizer.nextToken().split(Pattern.quote("+"));
            StringBuilder tokenBuilder = new StringBuilder();
            for (final String plusToken : plusTokens) {
                if (tokenBuilder.length() == 0) {
                    tokenBuilder.append(plusToken.trim().replaceAll("[^\\p{L}\\p{Nd}]+", ""));
                } else {
                    tokenBuilder.append(" ").append(plusToken.trim().replaceAll("[^\\p{L}\\p{Nd}]+", ""));
                }
            }
            if (tokenBuilder.length() >= minLen) {
                tokens.add(doCaseFormat(tokenBuilder.toString(), caseFormat));
            }
        }
        if (tokens.isEmpty()) {
            return new String[]{doCaseFormat(freeText, caseFormat)};
        }
        return tokens.toArray(new String[tokens.size()]);
    }

    /**
     * Build a string from the tags. The string has a maximum length of
     * {@code 255}.
     *
     * @param tags the tags
     * @return the string
     */
    @SuppressWarnings("unused")
    public static String buildTagString(final String[] tags) {
        return buildTagString(tags, 255);
    }

    /**
     * Build a string from the tags. The string has a maximum length of the
     * specified value.
     *
     * @param tags                 the tags
     * @param maxLengthOfTagString the maximum length
     * @return the string
     */
    @SuppressWarnings("WeakerAccess")
    public static String buildTagString(final String[] tags, final int maxLengthOfTagString) {
        if (tags == null || tags.length == 0) {
            return "";
        }
        final int maxLen = maxLengthOfTagString < 0 ? 0 : maxLengthOfTagString;
        final StringBuilder textBuilder = new StringBuilder();
        for (final String tag : tags) {
            final String value;
            if (textBuilder.length() == 0) {
                value = tag.replaceAll(" ", "+");
            } else {
                value = " " + tag.replaceAll(" ", "+");
            }
            int len = textBuilder.length() + value.length();
            if (len < maxLen) {
                textBuilder.append(value);
            } else {
                break;
            }
        }
        return textBuilder.toString();
    }

    /**
     * Build a string with text: First {@link TagUtils#buildTags(String, int)}
     * is called with a minimum length of {@code 2} than
     * {@link TagUtils#buildTagString(String[], int)} with a maximum length of
     * {@code 255}.
     *
     * @param freeText a string with tags
     * @return the parsed tags
     */
    @SuppressWarnings("unused")
    public static String buildTagString(final String freeText) {
        return buildTagString(freeText, 2, 255);
    }

    /**
     * Build a string with text: First {@link TagUtils#buildTags(String, int)}
     * than {@link TagUtils#buildTagString(String[], int)} with a maximum length
     * of {@code 255}.
     *
     * @param freeText             a string with tags
     * @param minLengthOfSingleTag the minimum length of one tag
     * @return the parsed tags
     */
    @SuppressWarnings({"SameParameterValue", "unused"})
    public static String buildTagString(final String freeText, final int minLengthOfSingleTag) {
        return buildTagString(freeText, minLengthOfSingleTag, 255);
    }

    /**
     * Build a string with text: First {@link TagUtils#buildTags(String, int)}
     * than {@link TagUtils#buildTagString(String[], int)} with a maximum length
     * of {@code 255}.
     *
     * @param freeText             a string with tags
     * @param minLengthOfSingleTag the minimum length of one tag
     * @param caseFormat           should the tags be lower case, upper case or untouched?
     * @return the parsed tags
     */
    @SuppressWarnings("unused")
    public static String buildTagString(final String freeText, final int minLengthOfSingleTag, CaseFormat caseFormat) {
        return buildTagString(freeText, minLengthOfSingleTag, 255, caseFormat);
    }

    /**
     * Build a string with text: First {@link TagUtils#buildTags(String, int)}
     * is called than {@link TagUtils#buildTagString(String[], int)}.
     *
     * @param freeText             a string with tags
     * @param minLengthOfSingleTag the minimum length of one tag
     * @param maxLengthOfTagString the maximum length of the parsed tags
     * @return the parsed tags
     */
    @SuppressWarnings({"WeakerAccess", "SameParameterValue"})
    public static String buildTagString(final String freeText, final int minLengthOfSingleTag,
                                        final int maxLengthOfTagString) {
        return buildTagString(freeText, minLengthOfSingleTag, maxLengthOfTagString, CaseFormat.UNTOUCHED);
    }

    /**
     * Build a string with text: First {@link TagUtils#buildTags(String, int)}
     * is called than {@link TagUtils#buildTagString(String[], int)}.
     *
     * @param freeText             a string with tags
     * @param minLengthOfSingleTag the minimum length of one tag
     * @param maxLengthOfTagString the maximum length of the parsed tags
     * @param caseFormat           should the tags be lower case, upper case or untouched?
     * @return the parsed tags
     */
    @SuppressWarnings("WeakerAccess")
    public static String buildTagString(final String freeText, final int minLengthOfSingleTag,
                                        final int maxLengthOfTagString, final CaseFormat caseFormat) {
        final String[] tags = buildTags(freeText, minLengthOfSingleTag, caseFormat);
        return buildTagString(tags, maxLengthOfTagString);
    }

}
