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

import org.bremersee.utils.PasswordUtils;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author Christian Bremer
 */
public class PasswordUtilsTests {
    
    @Test
    public void testPasswordQuality() {
        
        System.out.println("Testing password quality ...");
        
        double result = PasswordUtils.getPasswordQuality("", null);
        System.out.println("Result (Password = ''): " + result);
        TestCase.assertTrue(0. == result);
        
        result = PasswordUtils.getPasswordQuality("abc", null);
        System.out.println("Result (Password = 'abc'): " + result);
        TestCase.assertTrue(0. < result && result < 0.5);
        
        result = PasswordUtils.getPasswordQuality("abcABC", null);
        System.out.println("Result (Password = 'abcABC'): " + result);
        TestCase.assertTrue(0.25 < result && result < 0.75);

        result = PasswordUtils.getPasswordQuality("abcABC09", null);
        System.out.println("Result (Password = 'abcABC09'): " + result);
        TestCase.assertTrue(0.5 < result && result < 1.);

        result = PasswordUtils.getPasswordQuality("!$_abcABC09", null);
        System.out.println("Result (Password = '!$_abcABC09'): " + result);
        TestCase.assertTrue(0.75 < result && result <= 1.);

        String s1 = PasswordUtils.createRandomClearPassword();
        result = PasswordUtils.getPasswordQuality(s1, null);
        System.out.println("Result (Password = '" + s1 + "'): " + result);
        TestCase.assertTrue(0.75 < result && result <= 1.);
        
        System.out.println("OK");
    }

    @Test
    public void testPasswordWithoutSymbols() {

        System.out.println("Testing password without symbols ...");

        String password = PasswordUtils.createRandomClearPassword(false);
        double result = PasswordUtils.getPasswordQuality(password, null);
        System.out.println("Result (Password = '" + password + "'): " + result);
        TestCase.assertTrue(0.5 < result && result < 1.);

        System.out.println("OK");
    }

}
