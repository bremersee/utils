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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.bremersee.utils.CodingUtils;
import org.bremersee.utils.InputStreamListener;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author Christian Bremer
 */
public class CodingUtilsTests {
    
    @Test
    public void testDigestSilenty() {
        
        System.out.println("Testing hash ...");
        final String s1 = "Hello world";
        
        final ByteArrayInputStream in = new ByteArrayInputStream(CodingUtils.toBytesSilently(s1, "UTF-8"));
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        byte[] digest = CodingUtils.digestSilenty(
                CodingUtils.getInstanceSilently("MD5"), 
                in, 
                true,
                new InputStreamListener() {
                    
                    @Override
                    public void onReadBytes(byte[] buffer, int offset, int len) {
                        out.write(buffer, offset, len);
                    }
                });
        
        System.out.println("MD5 hash of '" + s1 + "': " + CodingUtils.toHex(digest));
        String s2 = CodingUtils.toStringSilently(out.toByteArray(), "UTF-8");
        TestCase.assertEquals(s1, s2);
        System.out.println("OK");
    }

}
