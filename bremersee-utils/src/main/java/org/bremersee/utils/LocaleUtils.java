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
public abstract class LocaleUtils {

    /**
     * All available two letter language codes of {@link Locale}
     */
    private static final Set<String> LANG_CODES = new HashSet<>();

    /**
     * Map with all three letter language codes of {@link Locale}:
     * key (iso3 lower case) - value (two letter code lower case)
     */
    private static final Map<String, String> ISO3_LANG_CODES = new HashMap<>();

    /**
     * All available two letter country codes of {@link Locale}
     */
    private static final Set<String> COUNTRY_CODES = new HashSet<>();

    /**
     * Map with all three letter country codes of {@link Locale}:
     * key (iso3 upper case) - value (two letter code upper case)
     */
    private static final Map<String, String> ISO3_COUNTRY_CODES = new HashMap<>();

    static {
        // fill out LANG_CODES,
        for (Locale locale : Locale.getAvailableLocales()) {
            String lang = locale.getLanguage();
            if (lang != null) {
                LANG_CODES.add(lang.toLowerCase());
                try {
                    String iso3 = locale.getISO3Language();
                    if (iso3 != null) { // NOSONAR
                        ISO3_LANG_CODES.put(iso3.toLowerCase(), lang.toLowerCase());
                    }
                } catch (RuntimeException re) { // NOSONAR
                    // ignored
                }
            }
            String country = locale.getCountry();
            if (country != null) {
                COUNTRY_CODES.add(country.toUpperCase());
                try {
                    String iso3 = locale.getISO3Country();
                    if (iso3 != null) { // NOSONAR
                        ISO3_COUNTRY_CODES.put(iso3.toUpperCase(), country.toUpperCase());
                    }
                } catch (RuntimeException re) { // NOSONAR
                    // ignored
                }
            }
        }
    }

    /**
     * Never construct.
     */
    private LocaleUtils() {
        super();
    }

    /**
     * Validates the specified language code. The language code must be one of {@link Locale#getAvailableLocales()} as
     * two letter or iso3 letter code. If the language code is not valid or is {@code null}, the language code of the
     * system will be returned.
     *
     * @param languageCode the language code to validate (can be {@code null})
     * @return the validated two letter language code
     */
    public static String validateLanguageCode(final String languageCode) {
        return validateLanguageCode(languageCode, null);
    }

    /**
     * Validates the specified language code. The language code must be one of {@link Locale#getAvailableLocales()} as
     * two letter or iso3 letter code. If the language code is not valid, the default language code will be validated.
     * If even the default language code is not valid, the language code of the system will be returned. So even if one
     * of the language codes is {@code null}, a valid language code will be returned.
     *
     * @param languageCode        the language code to validate (can be {@code null})
     * @param defaultLanguageCode the default language code (can be {@code null})
     * @return the validated two letter language code
     */
    @SuppressWarnings("SameParameterValue")
    public static String validateLanguageCode(final String languageCode, String defaultLanguageCode) {
        final String lang = extractLanguageCode(languageCode);
        if (lang != null && (lang.length() == 2 || lang.length() == 3)) {
            if (LANG_CODES.contains(lang)) {
                return lang;
            }
            String langFromIso3 = ISO3_LANG_CODES.get(lang);
            if (langFromIso3 != null) {
                return langFromIso3;
            }
        }
        if (defaultLanguageCode == null) {
            return StringUtils.isBlank(Locale.getDefault().getLanguage()) ? "de" : Locale.getDefault().getLanguage();
        }
        return validateLanguageCode(defaultLanguageCode, null);
    }

    private static String extractLanguageCode(final String languageCode) {
        if (languageCode == null || languageCode.length() < 2) {
            return null;
        }
        final int index = languageCode.indexOf('_');
        final String value;
        if (index >= 2) {
            value = languageCode.substring(0, index);
        } else {
            value = languageCode;
        }
        if (value.length() >= 3) {
            return value.substring(0, 3).toLowerCase();
        } else {
            // has max length of 2
            return value.toLowerCase();
        }
    }

    /**
     * Validates the specified country code. The country code must be one of {@link Locale#getAvailableLocales()} as
     * two letter or iso3 letter code. If the country code is not valid or is {@code null}, the country code of the
     * system will be returned.
     *
     * @param countryCode the country code to validate (can be {@code null})
     * @return the validated two letter country code
     */
    public static String validateCountryCode(final String countryCode) {
        return validateCountryCode(countryCode, null);
    }

    /**
     * Validates the specified country code. The country code must be one of {@link Locale#getAvailableLocales()} as
     * two letter or iso3 letter code. If the country code is not valid, the default country code will be validated.
     * If even the default country code is not valid, the country code of the system will be returned. So even if one
     * of the country codes is {@code null}, a valid country code will be returned.
     *
     * @param countryCode        the country code to validate (can be {@code null})
     * @param defaultCountryCode the default country code (can be {@code null})
     * @return the validated two letter country code
     */
    @SuppressWarnings("SameParameterValue")
    public static String validateCountryCode(final String countryCode, final String defaultCountryCode) {
        final String cc = extractCountryCode(countryCode);
        if (cc != null && (cc.length() == 2 || cc.length() == 3)) {
            if (COUNTRY_CODES.contains(cc)) {
                return cc;
            }
            String country = ISO3_COUNTRY_CODES.get(cc);
            if (country != null) {
                return country;
            }
        }
        if (StringUtils.isBlank(defaultCountryCode)) {
            return StringUtils.isBlank(Locale.getDefault().getCountry()) ? "DE" : Locale.getDefault().getCountry();
        }
        return validateCountryCode(defaultCountryCode, null);
    }

    private static String extractCountryCode(final String countryCode) {
        if (countryCode == null) {
            return null;
        }
        final String[] values = countryCode.split("_");
        if (values.length >= 2) {
            return extractCountryCode(values[1]);
        }
        final String value = values[0];
        if (value.length() < 2) {
            return null;
        }
        if (value.length() >= 3) {
            return value.substring(0, 3).toUpperCase();
        } else {
            // has max length of 2
            return value.toUpperCase();
        }
    }

    /**
     * <p>Converts a String to a Locale.</p>
     * <p>
     * <p>This method takes the string format of a locale and creates the
     * locale object from it.</p>
     * <p>
     * <pre>
     *   LocaleUtils.toLocale(null)         = new Locale(Locale.getDefault().getLanguage())
     *   LocaleUtils.toLocale("")           = new Locale(Locale.getDefault().getLanguage())
     *   LocaleUtils.toLocale("en")         = new Locale("en")
     *   LocaleUtils.toLocale("en_GB")      = new Locale("en", "GB")
     *   LocaleUtils.toLocale("en_GB_xxx")  = new Locale("en", "GB", "xxx")
     * </pre>
     * <p>
     * This method validates the input not strictly:
     * <pre>
     *   The language code can be lowercase, uppercase or an ISO3 language code.
     *   The country code must be lowercase, uppercase or an ISO3 country code.
     *   The separator must be an underscore.
     *   The length doesn't matter.
     * </pre>
     *
     * @param localeAsString  the locale String to convert, {@code null} returns a locale with the deafult language
     * @param countryRequired if {@code true} the country will always be present in the resulting locale
     * @return the locale
     */
    public static Locale fromString(String localeAsString, boolean countryRequired) {
        if (localeAsString == null) {
            return Locale.getDefault();
        }
        final String languageCode = validateLanguageCode(localeAsString);
        final String[] values = localeAsString.split("_");
        String countryCode = values.length >= 2 ? values[1] : null;
        if (countryRequired) {
            countryCode = validateCountryCode(countryCode);
        } else {
            if (countryCode != null && !COUNTRY_CODES.contains(countryCode.toUpperCase())) {
                countryCode = ISO3_COUNTRY_CODES.get(countryCode.toUpperCase());
            }
        }
        if (countryCode == null) {
            return new Locale(languageCode);
        }
        if (values.length >= 3 && values[2].length() > 0) {
            return new Locale(languageCode, countryCode, values[2]);
        }
        return new Locale(languageCode, countryCode);
    }

}
