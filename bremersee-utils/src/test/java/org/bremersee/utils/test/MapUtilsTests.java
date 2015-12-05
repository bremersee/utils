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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bremersee.utils.MapUtils;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author Christian Bremer
 */
public class MapUtilsTests {
    
    final private Map<String, Object> map = new LinkedHashMap<String, Object>();
    
    private static enum MapEnum {
        VAL0, VAL1, VAL2;
    }
    
    private String _string = "Hello";
    
    private Long _long = 1234567890987654321L;
    
    private Integer _int = 1234567890;
    
    private Byte _byte = 64;
    
    private Double _double = 0.123456789;
    
    private Float _float = 1234.1f;
    
    private Boolean _boolean = true;
    
    private MapEnum _enum = MapEnum.VAL1;
    
    @Before
    public void createMap() {
        map.put("String", _string);
        map.put("long", _long);
        map.put("int", _int);
        map.put("byte", _byte);
        map.put("double", _double);
        map.put("float", _float);
        map.put("boolean", _boolean);
        map.put("enum", _enum);
        
        map.put("longStr", _long.toString());
        map.put("intStr", _int.toString());
        map.put("byteStr", _byte.toString());
        map.put("doubleStr", _double.toString());
        map.put("floatStr", _float.toString());
        map.put("booleanStr", _boolean.toString());
        map.put("enumStr", _enum.name());
        
        List<Object> list = new ArrayList<Object>();
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
        TestCase.assertEquals(_long, MapUtils.getValueAsLong(map, "long", null));
        TestCase.assertEquals(_long, MapUtils.getValueAsLong(map, "longStr", null));
        TestCase.assertEquals(_int, MapUtils.getValueAsInteger(map, "int", null));
        TestCase.assertEquals(_int, MapUtils.getValueAsInteger(map, "intStr", null));
        TestCase.assertEquals(_byte, MapUtils.getValueAsByte(map, "byte", null));
        TestCase.assertEquals(_byte, MapUtils.getValueAsByte(map, "byteStr", null));
        TestCase.assertEquals(_double, MapUtils.getValueAsDouble(map, "double", null));
        TestCase.assertEquals(_double, MapUtils.getValueAsDouble(map, "doubleStr", null));
        TestCase.assertEquals(_float, MapUtils.getValueAsFloat(map, "float", null));
        TestCase.assertEquals(_float, MapUtils.getValueAsFloat(map, "floatStr", null));
        TestCase.assertEquals(_boolean, MapUtils.getValueAsBoolean(map, "boolean", null));
        TestCase.assertEquals(_boolean, MapUtils.getValueAsBoolean(map, "booleanStr", null));
        TestCase.assertEquals(_enum, MapUtils.getValueAsEnum(map, "enum", null, null));
        TestCase.assertEquals(_enum, MapUtils.getValueAsEnum(map, "enumStr", MapEnum.class, null));
        System.out.println("OK");
    }

}
