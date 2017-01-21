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

import org.bremersee.utils.WebUtils;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author Christian Bremer
 */
public class WebUtilsTests {

    @Test
    public void testAddUrlParameter() {
        System.out.println("Testing adding of web parameters ...");
        String url = "http://bremersee.org";
        String newUrl = WebUtils.addUrlParameter(url, "first", "Christian");
        newUrl = WebUtils.addUrlParameter(newUrl, "last", "Bremer");
        TestCase.assertEquals(url + "?first=Christian&last=Bremer", newUrl);
        System.out.println("OK");
    }

}
