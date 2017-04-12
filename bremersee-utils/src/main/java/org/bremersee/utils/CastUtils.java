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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * This utility class has methods to cast collections.
 * </p>
 *
 * @author Christian Bremer
 */
@SuppressWarnings("unchecked")
public abstract class CastUtils {

    /**
     * Never construct.
     */
    private CastUtils() {
        super();
    }

    /**
     * Casts an iterator.
     *
     * @param iter the iterator
     * @param <T>  type of the entries
     * @return the casted iterator
     */
    public static <T> Iterator<T> cast(Iterator<?> iter) {
        return (Iterator<T>) iter;
    }

    /**
     * Casts an iterator.
     *
     * @param iter the iterator
     * @param t    type of the entries
     * @param <T>  type of the entries
     * @return the casted iterator
     */
    @SuppressWarnings("unused")
    public static <T> Iterator<T> cast(Iterator<?> iter, Class<T> t) {
        return (Iterator<T>) iter;
    }

    /**
     * Casts a collection.
     *
     * @param col the collection
     * @param <T> type of the entries
     * @return the casted collection
     */
    public static <T> Collection<T> cast(Collection<?> col) {
        return (Collection<T>) col;
    }

    /**
     * Casts a collection.
     *
     * @param col the collection
     * @param t   type of the entries
     * @param <T> type of the entries
     * @return the casted collection
     */
    @SuppressWarnings("unused")
    public static <T> Collection<T> cast(Collection<?> col, Class<T> t) {
        return (Collection<T>) col;
    }

    /**
     * Casts a list.
     *
     * @param list the list
     * @param <T>  type of the entries
     * @return the casted list
     */
    public static <T> List<T> cast(List<?> list) {
        return (List<T>) list;
    }

    /**
     * Casts a list.
     *
     * @param list the list
     * @param t    type of the entries
     * @param <T>  type of the entries
     * @return the casted list
     */
    @SuppressWarnings("unused")
    public static <T> List<T> cast(List<?> list, Class<T> t) {
        return (List<T>) list;
    }

    /**
     * Casts a set.
     *
     * @param set the set
     * @param <T> type of the entries
     * @return the casted set
     */
    public static <T> Set<T> cast(Set<?> set) {
        return (Set<T>) set;
    }

    /**
     * Casts a set.
     *
     * @param set the set
     * @param t   type of the entries
     * @param <T> type of the entries
     * @return the casted set
     */
    @SuppressWarnings("unused")
    public static <T> Set<T> cast(Set<?> set, Class<T> t) {
        return (Set<T>) set;
    }

    /**
     * Casts a map.
     *
     * @param map the map
     * @param <T> type of the keys
     * @param <U> type of the values
     * @return the casted map
     */
    public static <T, U> Map<T, U> cast(Map<?, ?> map) {
        return (Map<T, U>) map;
    }

    /**
     * Casts a map.
     *
     * @param map the map
     * @param t   type of the keys
     * @param u   type of the values
     * @param <T> type of the keys
     * @param <U> type of the values
     * @return the casted map
     */
    @SuppressWarnings("unused")
    public static <T, U> Map<T, U> cast(Map<?, ?> map, Class<T> t, Class<U> u) {
        return (Map<T, U>) map;
    }

    /**
     * Casts a map entry.
     *
     * @param mapEntry the map entry
     * @param <T>      type of the key
     * @param <U>      type of the value
     * @return the casted map entry
     */
    public static <T, U> Map.Entry<T, U> cast(Map.Entry<?, ?> mapEntry) {
        return (Map.Entry<T, U>) mapEntry;
    }

    /**
     * Casts a map entry.
     *
     * @param mapEntry the map entry
     * @param t        type of the key
     * @param u        type of the value
     * @param <T>      type of the key
     * @param <U>      type of the value
     * @return the casted map entry
     */
    @SuppressWarnings("unused")
    public static <T, U> Map.Entry<T, U> cast(Map.Entry<?, ?> mapEntry, Class<T> t, Class<U> u) {
        return (Map.Entry<T, U>) mapEntry;
    }

}
