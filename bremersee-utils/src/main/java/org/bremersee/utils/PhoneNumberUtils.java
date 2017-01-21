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

import org.apache.commons.lang3.Validate;

/**
 * *
 * <p>
 * Some methods to work with phone numbers.
 * </p>
 *
 * @author Christian Bremer
 */
public abstract class PhoneNumberUtils {

    private PhoneNumberUtils() {
        // utility class, never constructed
    }

    /**
     * Create a new phone number from the specified one that only contains
     * numbers, '*', '#'.
     *
     * @param phoneNumber the original phone number
     * @return the cleaned phone number
     */
    public static String cleanPhoneNumber(final String phoneNumber) {
        return cleanPhoneNumber(phoneNumber, false);
    }

    /**
     * Create a new phone number from the specified one that only contains
     * numbers, '*', '#' and '+'.
     *
     * @param phoneNumber           the original phone number
     * @param preferPlusInsteadOf00 if {@code true} a beginning with {@code 00} will be replaced with {@code +};
     *                              if {@code false} a beginning with {@code +} will be replaced with {@code 00}
     * @return the cleaned phone number
     */
    public static String cleanPhoneNumber(final String phoneNumber, final boolean preferPlusInsteadOf00) {

        Validate.notEmpty(phoneNumber, "Phone number must not be null or blank.");

        String cleanedPhoneNumber = phoneNumber.trim();

        if (cleanedPhoneNumber.startsWith("+") && !preferPlusInsteadOf00) {
            cleanedPhoneNumber = "00" + cleanedPhoneNumber.substring(1);
        }

        cleanedPhoneNumber = cleanedPhoneNumber.toUpperCase();
        cleanedPhoneNumber = cleanedPhoneNumber.replace('A', '2');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('B', '2');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('C', '2');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('D', '3');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('E', '3');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('F', '3');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('G', '4');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('H', '4');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('I', '4');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('J', '5');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('K', '5');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('L', '5');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('M', '6');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('N', '6');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('O', '6');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('P', '7');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('Q', '7');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('R', '7');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('S', '7');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('T', '8');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('U', '8');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('V', '8');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('W', '9');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('X', '9');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('Y', '9');
        cleanedPhoneNumber = cleanedPhoneNumber.replace('Z', '9');

        int x1 = (int) '*';
        int x2 = (int) '#';

        int n0 = (int) '0';
        int n9 = (int) '9';

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cleanedPhoneNumber.length(); i++) {

            int n = (int) cleanedPhoneNumber.charAt(i);
            if (n == x1 || n == x2 || (n0 <= n && n <= n9)) {
                sb.append((char) n);
            }
        }

        cleanedPhoneNumber = sb.toString();

        if (cleanedPhoneNumber.startsWith("00") && preferPlusInsteadOf00) {
            cleanedPhoneNumber = "+" + cleanedPhoneNumber.substring(2);
        }

        return cleanedPhoneNumber;
    }
}
