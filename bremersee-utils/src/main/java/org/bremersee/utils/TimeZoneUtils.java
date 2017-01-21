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

package org.bremersee.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author Christian Bremer
 */
public abstract class TimeZoneUtils {

    /**
     * Map with all time zones of {@link TimeZone}: key (lower case) - value (e. g. Europe/Berlin)
     */
    private static final Map<String, String> TIME_ZONE_IDS = new HashMap<>();

    static {
        // fill out TIME_ZONE_IDS, ISO3_LANG_CODES, COUNTRY_CODES, ISO3_COUNTRY_CODES
        for (String id : TimeZone.getAvailableIDs()) {
            TIME_ZONE_IDS.put(id.toLowerCase(), id);
        }
    }

    /**
     * Never construct.
     */
    private TimeZoneUtils() {
        super();
    }

    /**
     * Validates the specified time zone ID. The time zone ID must be one of {@link TimeZone#getAvailableIDs()}.
     * If the time zone ID is not valid or {@code null}, the time zone ID of the system will be returned.
     *
     * @param timeZoneId the time zone ID to be validated (can be {@code null})
     * @return the validated time zone ID
     */
    public static String validateTimeZoneId(final String timeZoneId) {
        return validateTimeZoneId(timeZoneId, null);
    }

    /**
     * Validates the specified time zone ID. The time zone ID must be one of {@link TimeZone#getAvailableIDs()}.
     * If the time zone ID is not valid, the default time zone ID will be validated. If even the default time zone ID
     * is not valid, the time zone ID of the system will be returned. So even if one of the time zones is {@code null},
     * a valid time zone ID will be returned.
     *
     * @param timeZoneId        the time zone ID to be validated (can be {@code null})
     * @param defaultTimeZoneId the default time zone ID (can be {@code null})
     * @return the validated time zone ID
     */
    public static String validateTimeZoneId(final String timeZoneId, final String defaultTimeZoneId) {
        if (StringUtils.isNotBlank(timeZoneId)) {
            String id = TIME_ZONE_IDS.get(timeZoneId.toLowerCase());
            if (id != null) {
                return id;
            }
        }
        if (defaultTimeZoneId == null) {
            return TimeZone.getDefault().getID();
        }
        return validateTimeZoneId(defaultTimeZoneId, null);
    }

}
