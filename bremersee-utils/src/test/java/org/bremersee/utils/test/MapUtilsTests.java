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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import org.bremersee.utils.MapUtils;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author Christian Bremer
 */
public class MapUtilsTests {

    final private Map<String, Object> map = new LinkedHashMap<>();

    private enum MapEnum {
        VAL1
    }

    private String _string = "Hello";

    private BigInteger _bigInteger = new BigInteger("1234567890987654321");

    private Long _long = 1234567890987654321L;

    private Integer _int = 1234567890;

    private Byte _byte = 64;

    private BigDecimal _bigDecimal = new BigDecimal("4.5");

    private Double _double = 0.123456789;

    private Float _float = 1234.1f;

    private Boolean _boolean = true;

    private MapEnum _enum = MapEnum.VAL1;

    @Before
    public void createMap() {
        map.put("String", _string);
        map.put("bigInteger", _bigInteger);
        map.put("long", _long);
        map.put("int", _int);
        map.put("byte", _byte);
        map.put("bigDecimal", _bigDecimal);
        map.put("double", _double);
        map.put("float", _float);
        map.put("boolean", _boolean);
        map.put("enum", _enum);

        map.put("bigIntegerStr", _bigInteger.toString());
        map.put("longStr", _long.toString());
        map.put("intStr", _int.toString());
        map.put("byteStr", _byte.toString());
        map.put("bigDecimalStr", _bigDecimal.toString());
        map.put("doubleStr", _double.toString());
        map.put("floatStr", _float.toString());
        map.put("booleanStr", _boolean.toString());
        map.put("enumStr", _enum.name());

        List<Object> list = new ArrayList<>();
        list.add(_string);
        list.add(_int);
        map.put("list", list);
    }

    @Test
    public void testMap() throws Exception {
        System.out.println("Testing map getters ...");
        TestCase.assertEquals(_string, MapUtils.getFirstValue(map, "String", null));
        TestCase.assertEquals(_string, MapUtils.getFirstValue(map, "list", null));
        TestCase.assertEquals(_string, MapUtils.getValueAsList(map, "String").get(0));
        TestCase.assertEquals(_bigInteger.toString(), MapUtils.getValueAsString(map, "bigInteger", null));
        TestCase.assertEquals(_bigInteger, MapUtils.getValueAsBigInteger(map, "bigIntegerStr", null));
        TestCase.assertEquals(_long, MapUtils.getValueAsLong(map, "long", null));
        TestCase.assertEquals(_long, MapUtils.getValueAsLong(map, "longStr", null));
        TestCase.assertEquals(_int, MapUtils.getValueAsInteger(map, "int", null));
        TestCase.assertEquals(_int, MapUtils.getValueAsInteger(map, "intStr", null));
        TestCase.assertEquals(_byte, MapUtils.getValueAsByte(map, "byte", null));
        TestCase.assertEquals(_byte, MapUtils.getValueAsByte(map, "byteStr", null));
        TestCase.assertEquals(_bigDecimal, MapUtils.getValueAsBigDecimal(map, "bigDecimalStr", null));
        TestCase.assertEquals(_double, MapUtils.getValueAsDouble(map, "double", null));
        TestCase.assertEquals(_double, MapUtils.getValueAsDouble(map, "doubleStr", null));
        TestCase.assertEquals(_float, MapUtils.getValueAsFloat(map, "float", null));
        TestCase.assertEquals(_float, MapUtils.getValueAsFloat(map, "floatStr", null));
        TestCase.assertEquals(_boolean, MapUtils.getValueAsBoolean(map, "boolean", null));
        TestCase.assertEquals(_boolean, MapUtils.getValueAsBoolean(map, "booleanStr", null));
        //noinspection unchecked
        TestCase.assertEquals(_enum, MapUtils.getValueAsEnum(map, "enum", null, null));
        TestCase.assertEquals(_enum, MapUtils.getValueAsEnum(map, "enumStr", MapEnum.class, null));

        Set<?> set = MapUtils.getValueAsSet(map, "bigDecimal");
        TestCase.assertTrue(!set.isEmpty());
        TestCase.assertEquals(new ArrayList<>(set).get(0), _bigDecimal);

        Collection<?> col = MapUtils.getValueAsCollection(map, "bigDecimal");
        TestCase.assertTrue(!col.isEmpty());
        TestCase.assertEquals(new ArrayList<>(col).get(0), _bigDecimal);

        System.out.println("OK");
    }

    @Test
    public void testSortMap() {
        System.out.println("Sorting map ...");
        Map<String, Object> sortableMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sortableMap.put(entry.getKey(), entry.getValue().toString());
        }
        System.out.println("Unsorted map: " + sortableMap);
        System.out.println("Unsorted map: " + MapUtils.sort(sortableMap));
    }

    @Test
    public void testSortMapWithComparator() {
        System.out.println("Sorting map ...");
        System.out.println("Unsorted map: " + map);
        System.out.println("Unsorted map: " + MapUtils.sort(map, new Comparator<Object>() {
            @Override
            public int compare(Object o0, Object o1) {
                return o0.toString().compareTo(o1.toString());
            }
        }));
    }

}
