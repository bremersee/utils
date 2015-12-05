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

import org.bremersee.utils.TagUtils;
import org.junit.Test;

/**
 * @author Christian Bremer
 */
public class TagUtilsTests {

    @Test
    public void testTags() {
        System.out.println("Testing creation of tags ...");
        String freeText = ""
                + "Durch so viel Formen geschritten,\n"
                + "durch Ich und Wir und Du,\n"
                + "doch alles blieb erlitten\n"
                + "durch die ewige Frage: wozu?";
        
        String tagText = TagUtils.buildTagString(freeText, 2);
        System.out.println(tagText);
        System.out.println("OK");
    }
    
}
