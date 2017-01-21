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

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.Map.Entry;

/**
 * <p>
 * Some methods to work with maps.
 * </p>
 *
 * @author Christian Bremer
 */
public abstract class MapUtils {

    /**
     * Never construct.
     */
    private MapUtils() {
        super();
    }

    /**
     * <p>
     * A map entry comparator.
     * </p>
     *
     * @author Christian Bremer
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static class MapEntryComparator implements Comparator<Map.Entry<?, ?>> {

        private final Comparator valueComparator;

        /**
         * Create a map entry comparator with the specified value comparator.
         * <br/>
         * If the value comparator is {@code null}, the natural sort order will
         * be used.
         *
         * @param valueComparator a value comparator or {@code null}
         */
        MapEntryComparator(final Comparator<?> valueComparator) {
            this.valueComparator = valueComparator;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(final Entry<?, ?> e1, final Entry<?, ?> e2) {
            int c = 0;
            if (e1 != null && e1.getValue() != null && e2 != null && e2.getValue() != null) {

                final Object value1 = e1.getValue();
                final Object value2 = e2.getValue();
                if (valueComparator != null) {
                    c = valueComparator.compare(value1, value2);
                } else if (value1 instanceof Comparable && value2 instanceof Comparable) {
                    c = ((Comparable) value1).compareTo(value2);
                }
            }
            if (c == 0) {
                c = -1;
            }
            return c;
        }

    }

    /**
     * Sort the map by the values natural sort order and return an unmodifiable
     * map.
     *
     * @param map the map
     * @return the sorted map
     */
    public static <K, V> Map<K, V> sort(final Map<? extends K, ? extends V> map) {
        return sort(map, null);
    }

    /**
     * Sort the map by it's values and return an unmodifiable map.
     *
     * @param map             the map
     * @param valueComparator a value comparator
     * @return the sorted map
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> sort(final Map<? extends K, ? extends V> map,
                                        final Comparator<? extends V> valueComparator) {

        Validate.notNull(map, "Map must not be null.");
        final List<Entry<?, ?>> entries = new ArrayList<Entry<?, ?>>(map.entrySet()); // NOSONAR
        Collections.sort(entries, new MapEntryComparator(valueComparator));
        final Map<Object, Object> sortedMap = new LinkedHashMap<>(entries.size());
        for (final Entry<?, ?> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return (Map<K, V>) Collections.unmodifiableMap(sortedMap);
    }

    /**
     * Get the first value with the specified key. If no value exists with the
     * specified key, the default value will be returned.
     *
     * @param map          the map
     * @param key          the key
     * @param defaultValue a default value
     * @return the first value if it exists otherwise the default value
     */
    public static Object getFirstValue(final Map<?, ?> map, final Object key,
                                       final Object defaultValue) {
        final List<?> list = getValueAsList(map, key);
        if (list.isEmpty()) {
            return defaultValue;
        }
        return list.get(0);
    }

    /**
     * Get a value as collection. If no value exists with the specified key, an
     * empty collection will be returned.
     *
     * @param map the map
     * @param key the key
     * @return a collection
     */
    @SuppressWarnings("ManualArrayToCollectionCopy")
    public static Collection<?> getValueAsCollection(Map<?, ?> map, Object key) { // NOSONAR
        Validate.notNull(map, "Map must not be null.");
        Validate.notNull(map, "Key must not be null.");
        final Object value = map.get(key);
        if (value == null) {
            return Collections.emptyList();
        }
        if (value instanceof Collection) {
            return (Collection<?>) value;
        }
        if (value.getClass().isArray()) {
            if (value instanceof boolean[]) {
                final boolean[] values = (boolean[]) value;
                final List<Object> list = new ArrayList<>(values.length);
                for (final boolean v : values) {
                    list.add(v);
                }
                return list;
            } else if (value instanceof byte[]) {
                final byte[] values = (byte[]) value;
                final List<Object> list = new ArrayList<>(values.length);
                for (final byte v : values) {
                    list.add(v);
                }
                return list;
            } else if (value instanceof short[]) {
                final short[] values = (short[]) value;
                final List<Object> list = new ArrayList<>(values.length);
                for (final short v : values) {
                    list.add(v);
                }
                return list;
            } else if (value instanceof int[]) {
                final int[] values = (int[]) value;
                final List<Object> list = new ArrayList<>(values.length);
                for (final int v : values) {
                    list.add(v);
                }
                return list;
            } else if (value instanceof long[]) {
                final long[] values = (long[]) value;
                final List<Object> list = new ArrayList<>(values.length);
                for (final long v : values) {
                    list.add(v);
                }
                return list;
            } else if (value instanceof char[]) {
                final char[] values = (char[]) value;
                final List<Object> list = new ArrayList<>(values.length);
                for (final char v : values) {
                    list.add(v);
                }
                return list;
            } else if (value instanceof float[]) {
                final float[] values = (float[]) value;
                final List<Object> list = new ArrayList<>(values.length);
                for (final float v : values) {
                    list.add(v);
                }
                return list;
            } else if (value instanceof double[]) {
                final double[] values = (double[]) value;
                final List<Object> list = new ArrayList<>(values.length);
                for (final double v : values) {
                    list.add(v);
                }
                return list;
            } else {
                final Object[] values = (Object[]) value;
                final List<Object> list = new ArrayList<>(values.length);
                for (final Object v : values) {
                    list.add(v);
                }
                return list;
            }
        }
        final List<Object> list = new ArrayList<>(1);
        list.add(value);
        return list;
    }

    /**
     * Get a value as list. If no value exists with the specified key, an empty
     * list will be returned.
     *
     * @param map the map
     * @param key the key
     * @return a list
     */
    public static List<?> getValueAsList(final Map<?, ?> map, final Object key) { // NOSONAR
        final Collection<?> col = getValueAsCollection(map, key);
        if (col instanceof List) {
            return (List<?>) col;
        }
        return new ArrayList<Object>(col); // NOSONAR
    }

    /**
     * Get a value as set. If no value exists with the specified key, an empty
     * set will be returned.
     *
     * @param map the map
     * @param key the key
     * @return a set
     */
    @SuppressWarnings("SameParameterValue")
    public static Set<?> getValueAsSet(final Map<?, ?> map, final Object key) { // NOSONAR
        final Collection<?> col = getValueAsCollection(map, key);
        if (col instanceof Set) {
            return (Set<?>) col;
        }
        return new LinkedHashSet<Object>(col); // NOSONAR
    }

    /**
     * Get a value as string. If no value exists with the specified key, the
     * default value will be returned. If the value cannot be cast to the return
     * type, the default value will be returned, too.
     *
     * @param map          the map
     * @param key          the key
     * @param defaultValue a default value
     * @return the value of the key
     */
    @SuppressWarnings("SameParameterValue")
    public static String getValueAsString(final Map<?, ?> map, final Object key,
                                          final String defaultValue) {
        final Object value = getFirstValue(map, key, defaultValue);
        if (value == null) {
            return defaultValue;
        }
        return value.toString();
    }

    /**
     * Get a value as boolean. If no value exists with the specified key, the
     * default value will be returned. If the value cannot be cast to the return
     * type, the default value will be returned, too.
     *
     * @param map          the map
     * @param key          the key
     * @param defaultValue a default value
     * @return the value of the key
     */
    @SuppressWarnings("SameParameterValue")
    public static Boolean getValueAsBoolean(final Map<?, ?> map, final Object key,
                                            final Boolean defaultValue) {
        final Object value = getFirstValue(map, key, defaultValue);
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        try {
            return Boolean.valueOf(value.toString().toLowerCase());
        } catch (Exception e) { // NOSONAR
            return defaultValue;
        }
    }

    /**
     * Get a value as {@code BigInteger}. If no value exists with the specified
     * key, the default value will be returned. If the value cannot be cast to
     * the return type, the default value will be returned, too.
     *
     * @param map          the map
     * @param key          the key
     * @param defaultValue a default value
     * @return the value of the key
     */
    @SuppressWarnings("SameParameterValue")
    public static BigInteger getValueAsBigInteger(final Map<?, ?> map, final Object key,
                                                  final BigInteger defaultValue) {
        final Object value = getFirstValue(map, key, defaultValue);
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof BigInteger) {
            return (BigInteger) value;
        }
        try {
            return new BigInteger(value.toString());
        } catch (Exception e) { // NOSONAR
            return defaultValue;
        }
    }

    /**
     * Get a value as long. If no value exists with the specified key, the
     * default value will be returned. If the value cannot be cast to the return
     * type, the default value will be returned, too.
     *
     * @param map          the map
     * @param key          the key
     * @param defaultValue a default value
     * @return the value of the key
     */
    @SuppressWarnings("SameParameterValue")
    public static Long getValueAsLong(final Map<?, ?> map, final Object key,
                                      final Long defaultValue) {
        final Object value = getFirstValue(map, key, defaultValue);
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Long) {
            return (Long) value;
        }
        try {
            return Long.valueOf(value.toString());
        } catch (Exception e) { // NOSONAR
            return defaultValue;
        }
    }

    /**
     * Get a value as integer. If no value exists with the specified key, the
     * default value will be returned. If the value cannot be cast to the return
     * type, the default value will be returned, too.
     *
     * @param map          the map
     * @param key          the key
     * @param defaultValue a default value
     * @return the value of the key
     */
    @SuppressWarnings("SameParameterValue")
    public static Integer getValueAsInteger(final Map<?, ?> map, final Object key,
                                            final Integer defaultValue) {
        final Object value = getFirstValue(map, key, defaultValue);
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        try {
            return Integer.valueOf(value.toString());
        } catch (Exception e) { // NOSONAR
            return defaultValue;
        }
    }

    /**
     * Get a value as byte. If no value exists with the specified key, the
     * default value will be returned. If the value cannot be cast to the return
     * type, the default value will be returned, too.
     *
     * @param map          the map
     * @param key          the key
     * @param defaultValue a default value
     * @return the value of the key
     */
    @SuppressWarnings("SameParameterValue")
    public static Byte getValueAsByte(final Map<?, ?> map, final Object key,
                                      final Byte defaultValue) {
        final Object value = getFirstValue(map, key, defaultValue);
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Byte) {
            return (Byte) value;
        }
        try {
            return Byte.valueOf(value.toString());
        } catch (Exception e) { // NOSONAR
            return defaultValue;
        }
    }

    /**
     * Get a value as short. If no value exists with the specified key, the
     * default value will be returned. If the value cannot be cast to the return
     * type, the default value will be returned, too.
     *
     * @param map          the map
     * @param key          the key
     * @param defaultValue a default value
     * @return the value of the key
     */
    @SuppressWarnings("unused")
    public static Short getValueAsShort(final Map<?, ?> map, final Object key,
                                        final Short defaultValue) {
        final Object value = getFirstValue(map, key, defaultValue);
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Short) {
            return (Short) value;
        }
        try {
            return Short.valueOf(value.toString());
        } catch (Exception e) { // NOSONAR
            return defaultValue;
        }
    }

    /**
     * Get a value as {@link BigDecimal}. If no value exists with the specified
     * key, the default value will be returned. If the value cannot be cast to
     * the return type, the default value will be returned, too.
     *
     * @param map          the map
     * @param key          the key
     * @param defaultValue a default value
     * @return the value of the key
     */
    @SuppressWarnings("SameParameterValue")
    public static BigDecimal getValueAsBigDecimal(final Map<?, ?> map, final Object key,
                                                  final BigDecimal defaultValue) {
        final Object value = getFirstValue(map, key, defaultValue);
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        try {
            return new BigDecimal(value.toString());
        } catch (Exception e) { // NOSONAR
            return defaultValue;
        }
    }

    /**
     * Get a value as double. If no value exists with the specified key, the
     * default value will be returned. If the value cannot be cast to the return
     * type, the default value will be returned, too.
     *
     * @param map          the map
     * @param key          the key
     * @param defaultValue a default value
     * @return the value of the key
     */
    @SuppressWarnings("SameParameterValue")
    public static Double getValueAsDouble(final Map<?, ?> map, final Object key,
                                          final Double defaultValue) {
        final Object value = getFirstValue(map, key, defaultValue);
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Double) {
            return (Double) value;
        }
        try {
            return Double.valueOf(value.toString());
        } catch (Exception e) { // NOSONAR
            return defaultValue;
        }
    }

    /**
     * Get a value as float. If no value exists with the specified key, the
     * default value will be returned. If the value cannot be cast to the return
     * type, the default value will be returned, too.
     *
     * @param map          the map
     * @param key          the key
     * @param defaultValue a default value
     * @return the value of the key
     */
    @SuppressWarnings("SameParameterValue")
    public static Float getValueAsFloat(final Map<?, ?> map, final Object key,
                                        final Float defaultValue) {
        final Object value = getFirstValue(map, key, defaultValue);
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Float) {
            return (Float) value;
        }
        try {
            return Float.valueOf(value.toString());
        } catch (Exception e) { // NOSONAR
            return defaultValue;
        }
    }

    /**
     * Get a value as enumeration. If no value exists with the specified key,
     * default value will be returned. If the value cannot be cast to the return
     * type, the default value will be returned, too.
     *
     * @param map          the map
     * @param key          the key
     * @param enumType     the enumeration type (can only be null if default value is not
     *                     null or a value of the specified key exists and has a type of
     *                     the wanted enumeration
     * @param defaultValue a default value
     * @return the value of the key
     */
    @SuppressWarnings("SameParameterValue")
    public static <T extends Enum<T>> T getValueAsEnum(final Map<?, ?> map,
                                                       final Object key, final Class<T> enumType,
                                                       final T defaultValue) {

        final Object value = getFirstValue(map, key, defaultValue);
        if (value == null) {
            return defaultValue;
        }
        try {
            //noinspection unchecked
            return (T) value;

        } catch (Exception ignored) { // NOSONAR

            final Class<T> eType;
            if (enumType == null && defaultValue != null) {
                eType = defaultValue.getDeclaringClass();
            } else {
                eType = enumType;
            }
            try {
                return Enum.valueOf(eType, value.toString());
            } catch (Exception e) { // NOSONAR
                return defaultValue;
            }
        }
    }

}
