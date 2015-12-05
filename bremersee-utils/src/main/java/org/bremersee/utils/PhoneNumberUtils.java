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
     * numbers, '*' or '#'.
     * 
     * @param phoneNumber
     *            the original phone number
     * @return the cleaned phone number
     */
    public static String cleanPhoneNumber(String phoneNumber) {

        Validate.notEmpty(phoneNumber, "Phone number must not be null or blank.");

        phoneNumber = phoneNumber.trim();

        if (phoneNumber.startsWith("+")) {
            phoneNumber = "00" + phoneNumber.substring(1);
        }

        phoneNumber = phoneNumber.toUpperCase();
        phoneNumber = phoneNumber.replace('A', '2');
        phoneNumber = phoneNumber.replace('B', '2');
        phoneNumber = phoneNumber.replace('C', '2');
        phoneNumber = phoneNumber.replace('D', '3');
        phoneNumber = phoneNumber.replace('E', '3');
        phoneNumber = phoneNumber.replace('F', '3');
        phoneNumber = phoneNumber.replace('G', '4');
        phoneNumber = phoneNumber.replace('H', '4');
        phoneNumber = phoneNumber.replace('I', '4');
        phoneNumber = phoneNumber.replace('J', '5');
        phoneNumber = phoneNumber.replace('K', '5');
        phoneNumber = phoneNumber.replace('L', '5');
        phoneNumber = phoneNumber.replace('M', '6');
        phoneNumber = phoneNumber.replace('N', '6');
        phoneNumber = phoneNumber.replace('O', '6');
        phoneNumber = phoneNumber.replace('P', '7');
        phoneNumber = phoneNumber.replace('Q', '7');
        phoneNumber = phoneNumber.replace('R', '7');
        phoneNumber = phoneNumber.replace('S', '7');
        phoneNumber = phoneNumber.replace('T', '8');
        phoneNumber = phoneNumber.replace('U', '8');
        phoneNumber = phoneNumber.replace('V', '8');
        phoneNumber = phoneNumber.replace('W', '9');
        phoneNumber = phoneNumber.replace('X', '9');
        phoneNumber = phoneNumber.replace('Y', '9');
        phoneNumber = phoneNumber.replace('Z', '9');

        int x1 = (int) '*';
        int x2 = (int) '#';

        int n0 = (int) '0';
        int n9 = (int) '9';

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < phoneNumber.length(); i++) {

            int n = (int) phoneNumber.charAt(i);
            if (n == x1 || n == x2 || (n0 <= n && n <= n9)) {
                sb.append((char) n);
            }
        }

        return sb.toString();
    }

}
