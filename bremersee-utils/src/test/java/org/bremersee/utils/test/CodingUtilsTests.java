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

import junit.framework.TestCase;
import org.bremersee.utils.CodingUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.Provider;

/**
 * @author Christian Bremer
 */
public class CodingUtilsTests {

    @Test
    public void testMessageDigest() {
        MessageDigest md = CodingUtils.getInstanceSilently("SHA");
        TestCase.assertNotNull(md);

        md = CodingUtils.getInstanceSilently("MD5", (Provider)null);
        TestCase.assertNotNull(md);
        md = CodingUtils.getInstanceSilently("SHA", (Provider)null);
        TestCase.assertNotNull(md);

        md = CodingUtils.getInstanceSilently("MD5", "SUN");
        TestCase.assertNotNull(md);
        md = CodingUtils.getInstanceSilently("SHA", "SUN");
        TestCase.assertNotNull(md);
    }
    
    @Test
    public void testDigest() {
        
        System.out.println("Testing hash ...");

        final MessageDigest md = CodingUtils.getInstanceSilently("MD5");
        final String s1 = "Hello world";

        byte[] result = CodingUtils.digestSilently(md, s1.getBytes());
        md.reset();
        TestCase.assertNotNull(result);

        ByteArrayInputStreamListener listener = new ByteArrayInputStreamListener();
        result = CodingUtils.digestSilently(md, s1.getBytes(), listener);
        md.reset();
        TestCase.assertNotNull(result);
        TestCase.assertEquals(s1.getBytes().length, listener.getBytes().length);
        listener.reset();

        result = CodingUtils.digestSilently(md, new ByteArrayInputStream(s1.getBytes()), false);
        md.reset();
        TestCase.assertNotNull(result);

        result = CodingUtils.digestSilently(md, new ByteArrayInputStream(s1.getBytes()), false, listener);
        md.reset();
        TestCase.assertNotNull(result);
        TestCase.assertEquals(s1.getBytes().length, listener.getBytes().length);
        listener.reset();

        System.out.println("MD5 hash of '" + s1 + "': " + CodingUtils.toHex(result));
        System.out.println("OK");
    }

    @Test
    public void testToHex() {

        System.out.println("Testing to hex ...");

        final String source = "Hello world";
        final String hex = CodingUtils.toHex(source.getBytes(), true);
        TestCase.assertNotNull(hex);

        System.out.println("Hex of '" + source + "': " + hex);
        System.out.println("OK");
    }

    @Test
    public void testToBytes() {

        System.out.println("Testing to bytes ...");

        final String source = "Hello world";

        final byte[] bytesA = CodingUtils.toBytesSilently(source, "UTF-8");
        TestCase.assertNotNull(bytesA);

        final byte[] bytesB = CodingUtils.toBytesSilently(source, Charset.forName("UTF-8"));
        TestCase.assertNotNull(bytesB);

        TestCase.assertEquals(CodingUtils.toStringSilently(bytesA, "UTF-8"),
                CodingUtils.toStringSilently(bytesB, Charset.forName("UTF-8")));
        System.out.println("OK");
    }

}
