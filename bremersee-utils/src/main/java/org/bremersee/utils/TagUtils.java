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

/**
 * <p>
 * Some methods to work with a tags.
 * </p>
 * 
 * @author Christian Bremer
 */
public abstract class TagUtils {

    private TagUtils() {
        // utility class, never constructed
    }

    /**
     * Build tags from a string. Each tag must have a minimum length of
     * {@code 2}.
     * 
     * @param freeText
     *            the string
     * @return the tags
     */
    public static String[] buildTags(String freeText) {
        return buildTags(freeText, 2);
    }

    /**
     * Build tags from a string. Each tag must have a minimum length of the
     * specified value.
     * 
     * @param freeText
     *            the string
     * @param minLengthOfSingleTag
     *            the minimum length
     * @return the tags
     */
    public static String[] buildTags(String freeText, int minLengthOfSingleTag) {
        if (minLengthOfSingleTag <= 0) {
            minLengthOfSingleTag = 1;
        }
        if (freeText == null || freeText.trim().length() < minLengthOfSingleTag) {
            return null;
        }
        List<String> tokens = new LinkedList<String>();
        StringTokenizer tokenizer = new StringTokenizer(freeText);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            token = token.replace('+', ' ');
            token = token.replace("\\ ", "+");
            token = token.trim();
            if (token.length() >= minLengthOfSingleTag) {
                tokens.add(token.trim());
            }
        }
        if (tokens.isEmpty()) {
            return new String[] { freeText };
        }
        return tokens.toArray(new String[tokens.size()]);
    }

    /**
     * Build a string from the tags. The string has a maximum length of
     * {@code 255}.
     * 
     * @param tags
     *            the tags
     * @return the string
     */
    public static String buildTagString(String[] tags) {
        return buildTagString(tags, 255);
    }

    /**
     * Build a string from the tags. The string has a maximum length of the
     * specified value.
     * 
     * @param tags
     *            the tags
     * @param maxLengthOfTagString
     *            the maximum length
     * @return the string
     */
    public static String buildTagString(String[] tags, int maxLengthOfTagString) {
        long maxLength = Integer.valueOf(maxLengthOfTagString).longValue();
        StringBuilder sb = new StringBuilder();
        if (tags != null) {
            for (int i = 0; i < tags.length; i++) {
                String token = tags[i];
                if (token != null) {
                    token = token.trim();
                    token = token.replace("+", "\\+");
                    token = token.replace(" ", "+");
                    if (Integer.valueOf(sb.length() + token.length() + 1).longValue() > maxLength) {
                        break;
                    }
                    if (i > 0) {
                        sb.append(' ');
                    }
                    sb.append(token);
                }
            }
        }
        return sb.toString();
    }

    /**
     * Build a string with text: First {@link TagUtils#buildTags(String, int)}
     * is called with a minimum length of {@code 2} than
     * {@link TagUtils#buildTagString(String[], int)} with a maximum length of
     * {@code 255}.
     * 
     * @param freeText
     *            a string with tags
     * @return the parsed tags
     */
    public static String buildTagString(String freeText) {
        return buildTagString(freeText, 2, 255);
    }

    /**
     * Build a string with text: First {@link TagUtils#buildTags(String, int)}
     * than {@link TagUtils#buildTagString(String[], int)} with a maximum length
     * of {@code 255}.
     * 
     * @param freeText
     *            a string with tags
     * @param minLengthOfSingleTag
     *            the minimum length of one tag
     * @return the parsed tags
     */
    public static String buildTagString(String freeText, int minLengthOfSingleTag) {
        return buildTagString(freeText, minLengthOfSingleTag, 255);
    }

    /**
     * Build a string with text: First {@link TagUtils#buildTags(String, int)}
     * is called than {@link TagUtils#buildTagString(String[], int)}.
     * 
     * @param freeText
     *            a string with tags
     * @param minLengthOfSingleTag
     *            the minimum length of one tag
     * @param maxLengthOfTagString
     *            the maximum length of the parsed tags
     * @return the parsed tags
     */
    public static String buildTagString(String freeText, int minLengthOfSingleTag, int maxLengthOfTagString) {
        String[] tags = buildTags(freeText, minLengthOfSingleTag);
        return buildTagString(tags, maxLengthOfTagString);
    }

}
