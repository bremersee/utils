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
import org.bremersee.utils.CastUtils;
import org.junit.Test;

import java.util.*;

/**
 * @author Christian Bremer
 */
public class CastUtilsTests {

    @Test
    public void testCastIterator() {
        Iterator<Object> source = new HashSet<>().iterator();

        Iterator<String> destination =  CastUtils.cast(source);
        TestCase.assertEquals(source, destination);

        destination = CastUtils.cast(source, String.class);
        TestCase.assertEquals(source, destination);
    }

    @Test
    public void testCastCollection() {
        Collection<Object> source = new ArrayList<>();
        source.add("First element");

        Collection<String> destination =  CastUtils.cast(source);
        TestCase.assertEquals(source, destination);

        destination = CastUtils.cast(source, String.class);
        TestCase.assertEquals(source, destination);
    }

    @Test
    public void testCastList() {
        List<Object> source = new ArrayList<>();
        source.add("First element");

        List<String> destination =  CastUtils.cast(source);
        TestCase.assertEquals(source, destination);

        destination = CastUtils.cast(source, String.class);
        TestCase.assertEquals(source, destination);
    }

    @Test
    public void testCastSet() {
        Set<Object> source = new HashSet<>();
        source.add("First element");

        Set<String> destination =  CastUtils.cast(source);
        TestCase.assertEquals(source, destination);

        destination = CastUtils.cast(source, String.class);
        TestCase.assertEquals(source, destination);
    }

    @Test
    public void testCastMap() {
        Map<Object, Object> source = new HashMap<>();
        source.put("First key", "First value");

        Map<String, String> destination =  CastUtils.cast(source);
        TestCase.assertEquals(source, destination);

        destination = CastUtils.cast(source, String.class, String.class);
        TestCase.assertEquals(source, destination);
    }

    @Test
    public void testCastMapEntry() {
        Map.Entry<Object, Object> source = new TreeMap<>().firstEntry();

        Map.Entry<String, String> destination =  CastUtils.cast(source);
        TestCase.assertEquals(source, destination);

        destination = CastUtils.cast(source, String.class, String.class);
        TestCase.assertEquals(source, destination);
    }

}
