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
import java.io.StringReader;
import java.io.StringWriter;

import org.bremersee.utils.CodingUtils;
import org.bremersee.utils.IoUtils;
import org.bremersee.utils.ReaderListener;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Christian Bremer
 */
public class IoUtilsTests {

    @Test
    public void testCopyingBytes() {
        
        System.out.println("Testing copying bytes ...");
        final byte[] bytes = CodingUtils.toBytesSilently("Hello world", "UTF-8");
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        long len = IoUtils.copySilently(in, out, true);
        Assert.assertArrayEquals(bytes, out.toByteArray());
        Assert.assertEquals(len, Integer.valueOf(bytes.length).longValue());

        ByteArrayInputStreamListener listener = new ByteArrayInputStreamListener();
        in = new ByteArrayInputStream(bytes);
        out = new ByteArrayOutputStream();
        IoUtils.copySilently(in, out, true, listener);
        Assert.assertArrayEquals(bytes, listener.getBytes());

        System.out.println("OK");
    }
    
    @Test
    public void testCopyingCharacters() {
        
        System.out.println("Testing copying characters ...");
        final String s1 = "Hello world";
        StringWriter writer = new StringWriter();
        final StringBuilder sb = new StringBuilder();
        final long len = IoUtils.copySilently(new StringReader(s1), writer, true, new ReaderListener() {
            
            @Override
            public void onReadChars(char[] buffer, int offset, int len) {
                sb.append(buffer, offset, len);
            }
        });
        Assert.assertEquals(s1, writer.toString());
        Assert.assertEquals(s1, sb.toString());
        Assert.assertEquals(len, Integer.valueOf(s1.length()).longValue());

        writer = new StringWriter();
        IoUtils.copySilently(new StringReader(s1), writer, true);
        Assert.assertEquals(s1, writer.toString());

        System.out.println("OK");
    }
    
}
