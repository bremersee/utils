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
import org.bremersee.utils.TimeZoneUtils;
import org.junit.Test;

import java.util.TimeZone;

/**
 * @author Christian Bremer
 */
public class TimeZoneUtilsTests {

    @Test
    public void testTimeZoneIdValidation() {

        System.out.println("Testing validation of time zone ...");

        String timeZoneId = TimeZoneUtils.validateTimeZoneId("invalid");
        System.out.println("- Time zone is invalid, default not present, result = " + timeZoneId);
        TestCase.assertEquals(TimeZone.getDefault().getID(), timeZoneId);

        timeZoneId = TimeZoneUtils.validateTimeZoneId("GMT");
        System.out.println("- Time zone is valid, default not present, result = " + timeZoneId);
        TestCase.assertEquals("GMT", timeZoneId);

        timeZoneId = TimeZoneUtils.validateTimeZoneId("invalid", "invalid");
        System.out.println("- Both are invalid, time zone and default time zone, result = " + timeZoneId);
        TestCase.assertEquals(TimeZone.getDefault().getID(), timeZoneId);

        timeZoneId = TimeZoneUtils.validateTimeZoneId("invalid", "Europe/Berlin");
        System.out.println("- Time zone is invalid, default time zone is valid, result = " + timeZoneId);
        TestCase.assertEquals("Europe/Berlin", timeZoneId);

        timeZoneId = TimeZoneUtils.validateTimeZoneId("GMT", "Europe/Berlin");
        System.out.println("- Both are valid, time zone and default time zone, result = " + timeZoneId);
        TestCase.assertEquals("GMT", timeZoneId);

        System.out.println("Testing validation of time zone ... DONE!");
    }

}
