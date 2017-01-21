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

package org.bremersee.utils.test;

import org.bremersee.utils.PhoneNumberUtils;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author Christian Bremer
 */
public class PhoneNumberUtilsTests {

    @Test
    public void testPhoneNumberCleaner() {
        System.out.println("Testing phone number cleaner ...");

        String s1 = "+49 171 123 456 789";
        String s2 = PhoneNumberUtils.cleanPhoneNumber(s1);
        System.out.println(s1 + "  -->  " + s2);
        TestCase.assertEquals("0049171123456789", s2);

        s1 = "0049 171 123 456 789";
        s2 = PhoneNumberUtils.cleanPhoneNumber(s1, true);
        System.out.println(s1 + "  -->  " + s2);
        TestCase.assertEquals("+49171123456789", s2);

        System.out.println("OK");
    }

}
