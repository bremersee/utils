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

package org.bremersee.utils;

/**
 * <p>
 * Intercepter for reading characters.
 * </p>
 * 
 * @author Christian Bremer
 *
 */
public interface ReaderListener {

    /**
     * This method is called by reading a characters.
     * 
     * @param buffer
     *            the char buffer
     * @param offset
     *            the start offset in the char buffer
     * @param len
     *            the number of chars in the char buffer
     */
    @SuppressWarnings("SameParameterValue")
    void onReadChars(char[] buffer, int offset, int len);
}
