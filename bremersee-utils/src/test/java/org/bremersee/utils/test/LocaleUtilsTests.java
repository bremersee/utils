/*
 * Copyright 2017 the original author or authors.
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

import junit.framework.TestCase;
import org.bremersee.utils.LocaleUtils;
import org.junit.Test;

import java.util.Locale;

/**
 * @author Christian Bremer
 */
public class LocaleUtilsTests {

    @Test
    public void testLanguageCodeValidation() {

        System.out.println("Testing validation of language code ...");

        String lc = LocaleUtils.validateLanguageCode("invalid");
        System.out.println("- Language code is invalid, default language code is not present, result = " + lc);
        TestCase.assertEquals(Locale.getDefault().getLanguage(), lc);

        lc = LocaleUtils.validateLanguageCode("ja");
        System.out.println("- Language code is valid, default language code is not present, result = " + lc);
        TestCase.assertEquals("ja", lc);

        lc = LocaleUtils.validateLanguageCode("invalid", "invalid");
        System.out.println("- Both are invalid, language code and default language code, result = " + lc);
        TestCase.assertEquals(Locale.getDefault().getLanguage(), lc);

        lc = LocaleUtils.validateLanguageCode("invalid", "en");
        System.out.println("- Language code is invalid, default language code is valid, result = " + lc);
        TestCase.assertEquals("en", lc);

        lc = LocaleUtils.validateLanguageCode("en", "de");
        System.out.println("- Both are valid, language code and default language code, result = " + lc);
        TestCase.assertEquals("en", lc);

        System.out.println("Testing validation of language code ... DONE!");
    }

    @Test
    public void testCountryCodeValidation() {

        System.out.println("Testing validation of country code ...");

        String cc = LocaleUtils.validateCountryCode("invalid");
        System.out.println("- Country code is invalid, default country code is not present, result = " + cc);
        TestCase.assertEquals(Locale.getDefault().getCountry(), cc);

        cc = LocaleUtils.validateCountryCode("US");
        System.out.println("- Country code isvalid, default country code is not present, result = " + cc);
        TestCase.assertEquals("US", cc);

        cc = LocaleUtils.validateCountryCode("invalid", "invalid");
        System.out.println("- Both are invalid, country code and default country code, result = " + cc);
        TestCase.assertEquals(Locale.getDefault().getCountry(), cc);

        cc = LocaleUtils.validateCountryCode("invalid", "GB");
        System.out.println("- Country code is invalid, default country code is valid, result = " + cc);
        TestCase.assertEquals("GB", cc);

        cc = LocaleUtils.validateCountryCode("GB", "DE");
        System.out.println("- Both are valid, country code and default country code, result = " + cc);
        TestCase.assertEquals("GB", cc);

        System.out.println("Testing validation of country code ... DONE!");
    }

    @Test
    public void testFromString() {

        System.out.println("Testing locale from String ...");

        Locale fr = Locale.FRANCE;

        Locale locale = LocaleUtils.fromString("fr_FR", true);
        System.out.println("fr_FR -> " + locale.toString());
        TestCase.assertEquals(fr, locale);

        locale = LocaleUtils.fromString("fra_FRA", true);
        System.out.println("fra_FRA -> " + locale.toString());
        TestCase.assertEquals(fr, locale);

        fr = Locale.FRENCH;
        locale = LocaleUtils.fromString("fra", false);
        System.out.println("fra -> " + locale.toString());
        TestCase.assertEquals(fr, locale);

        fr = Locale.FRENCH;
        locale = LocaleUtils.fromString("fr", false);
        System.out.println("fr -> " + locale.toString());
        TestCase.assertEquals(fr, locale);

        System.out.println("Testing locale from String ... DONE!");
    }

}
