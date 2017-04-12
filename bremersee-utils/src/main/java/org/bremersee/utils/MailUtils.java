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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;

/**
 * <p>
 * Methods to work with email addresses (from {@code bremersee.org}).<br>
 * Be aware: Other mail domains / server may treat email addresses in a
 * different way.
 * </p>
 *
 * @author Christian Bremer
 */
public abstract class MailUtils {

    /**
     * Simple email address regex that fits
     * <a href="https://tools.ietf.org/html/rfc5322">RFC 5322</a> and
     * <a href="https://tools.ietf.org/html/rfc6531">RFC 6531</a>.
     */
    public static final String EMAIL_REGEX = "^\\S+@\\S+$";

    /**
     * Email address regex for {@code bremersee.org} (
     * <a href="https://tools.ietf.org/html/rfc5322">RFC 5322</a>, but without
     * {@code '%'}).<br>
     * <a href="https://tools.ietf.org/html/rfc6531">RFC 6531</a> (characters
     * above ASCII-Codes 127) is not supported.
     */
    public static final String BREMERSEE_EMAIL_REGEX = "[a-zA-Z0-9!#$&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$&'*+/=" +
            "?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?";

    /**
     * A set of characters that are allowed in the local part of an email
     * address from {@code bremersee.org}.
     */
    private static final Set<Character> LEGAL_CHARS_IN_MAIL_LOCAL_PART;

    /**
     * A map of characters to replace some illegal characters in the local prt
     * of an email address from {@code bremersee.org}.
     */
    private static final Map<Character, String> REPLACE_CHARS_IN_MAIL_LOCAL_PART_MAP;

    static {
        Set<Character> tmpCharSet = new HashSet<>();
        char n0 = '0';
        char n9 = '9';
        char A = 'A'; // NOSONAR
        char Z = 'Z'; // NOSONAR
        char a = 'a';
        char z = 'z';
        char[] x = {'#', '$', '&', '\'', '*', '+', '-', '/', '=', '?', '^', '_', '`', '{', '|', '}', '~', '.'};

        StringBuilder sb = new StringBuilder();
        for (int i = (int) n0; i <= (int) n9; i++) {
            sb.append((char) i);
        }
        for (int i = (int) A; i <= (int) Z; i++) {
            sb.append((char) i);
        }
        for (int i = (int) a; i <= (int) z; i++) {
            sb.append((char) i);
        }
        sb.append(x);
        for (int i = 0; i < sb.length(); i++) {
            tmpCharSet.add(sb.charAt(i));
        }
        LEGAL_CHARS_IN_MAIL_LOCAL_PART = Collections.unmodifiableSet(tmpCharSet);

        Map<Character, String> tmpReplaceCharsMap = new HashMap<>();
        tmpReplaceCharsMap.put('(', "{");
        tmpReplaceCharsMap.put(')', "}");
        tmpReplaceCharsMap.put('[', "{");
        tmpReplaceCharsMap.put(']', "}");
        tmpReplaceCharsMap.put('\\', "/");

        tmpReplaceCharsMap.put('Ä', "Ae");
        tmpReplaceCharsMap.put('Ö', "Oe");
        tmpReplaceCharsMap.put('Ü', "Ue");

        tmpReplaceCharsMap.put('ä', "ae");
        tmpReplaceCharsMap.put('ö', "oe");
        tmpReplaceCharsMap.put('ü', "ue");

        tmpReplaceCharsMap.put('ß', "ss");

        tmpReplaceCharsMap.put('Á', "A");
        tmpReplaceCharsMap.put('É', "E");
        tmpReplaceCharsMap.put('Í', "I");
        tmpReplaceCharsMap.put('Ó', "O");
        tmpReplaceCharsMap.put('Ú', "U");

        tmpReplaceCharsMap.put('á', "a");
        tmpReplaceCharsMap.put('é', "e");
        tmpReplaceCharsMap.put('í', "i");
        tmpReplaceCharsMap.put('ó', "o");
        tmpReplaceCharsMap.put('ú', "u");

        tmpReplaceCharsMap.put('À', "A");
        tmpReplaceCharsMap.put('È', "E");
        tmpReplaceCharsMap.put('Ì', "I");
        tmpReplaceCharsMap.put('Ò', "O");
        tmpReplaceCharsMap.put('Ù', "U");

        tmpReplaceCharsMap.put('à', "a");
        tmpReplaceCharsMap.put('è', "e");
        tmpReplaceCharsMap.put('ì', "i");
        tmpReplaceCharsMap.put('ò', "o");
        tmpReplaceCharsMap.put('ù', "u");

        tmpReplaceCharsMap.put('Â', "A");
        tmpReplaceCharsMap.put('Ê', "E");
        tmpReplaceCharsMap.put('Î', "I");
        tmpReplaceCharsMap.put('Ô', "O");
        tmpReplaceCharsMap.put('Û', "U");

        tmpReplaceCharsMap.put('â', "a");
        tmpReplaceCharsMap.put('ê', "e");
        tmpReplaceCharsMap.put('î', "i");
        tmpReplaceCharsMap.put('ô', "o");
        tmpReplaceCharsMap.put('û', "u");

        REPLACE_CHARS_IN_MAIL_LOCAL_PART_MAP = Collections.unmodifiableMap(tmpReplaceCharsMap);
    }

    /**
     * Never construct.
     */
    private MailUtils() {
        super();
    }

    /**
     * Build an email address from the local part and the mail domain name.<br>
     * Illegal characters in the local part will be replaced by legal ones.<br>
     * The mail domain name will not be validated.<br>
     * So this method can be used to generate mail addresses from the name of a
     * group, for example.
     *
     * @param localPart  the local part of the email address
     * @param mailDomain the mail domain name
     * @return a legal email address (the local part may has changed)
     */
    @SuppressWarnings("SameParameterValue")
    public static String buildMailAddress(String localPart, String mailDomain) {

        Validate.notBlank(localPart, "localPart must not be null or blank");
        Validate.notBlank(mailDomain, "mailDomain must not be null or blank");

        StringBuilder localPartBuilder = new StringBuilder();
        for (int i = 0; i < localPart.length(); i++) {
            Character c = localPart.charAt(i);
            if (LEGAL_CHARS_IN_MAIL_LOCAL_PART.contains(c)) {
                localPartBuilder.append(c);
            } else {
                String r = REPLACE_CHARS_IN_MAIL_LOCAL_PART_MAP.get(c);
                if (r == null) {
                    localPartBuilder.append('_');
                } else {
                    localPartBuilder.append(r);
                }
            }
        }
        return localPartBuilder.toString() + "@" + mailDomain;
    }

}
