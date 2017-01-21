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

import java.util.regex.Pattern;

import org.bremersee.utils.MailUtils;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author Christian Bremer
 */
public class MailUtilsTests {

    private Pattern emailPattern;

    private Pattern bremerseeEmailPattern;

    @Before
    public void createPatterns() throws Exception {
        emailPattern = Pattern.compile(MailUtils.EMAIL_REGEX);
        bremerseeEmailPattern = Pattern.compile(MailUtils.BREMERSEE_EMAIL_REGEX);
    }

    @Test
    public void testEmailPattern() throws Exception {
        System.out.println("Testing email pattern ...");
        TestCase.assertEquals(true, emailPattern.matcher("andré.römer@äther.de").matches());
        System.out.println("OK");
    }

    @Test
    public void testBremerseeEmailPattern() throws Exception {
        System.out.println("Testing bremersee email pattern ...");
        TestCase.assertEquals(false, bremerseeEmailPattern.matcher("andré.römer@äther.de").matches());
        String s1 = MailUtils.buildMailAddress("andré.römer", "aether.de");
        System.out.println("andré.römer@aether.de  -->  " + s1);
        TestCase.assertEquals(true, bremerseeEmailPattern.matcher(s1).matches());
        s1 = "09AZa.#$&'*+-/=?^_`{|}~@bremersee.org";
        TestCase.assertEquals(true, bremerseeEmailPattern.matcher(s1).matches());
        System.out.println("OK");
    }

}
