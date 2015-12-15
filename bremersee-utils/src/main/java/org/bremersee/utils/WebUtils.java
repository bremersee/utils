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

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * <p>
 * Some methods to work with a web stuff.
 * </p>
 * 
 * @author Christian Bremer
 */
public abstract class WebUtils {

    private WebUtils() {
        // utility class, never constructed
    }

    /**
     * Add a URL parameter to the specified URL with UTF-8 encoding.
     * 
     * @param url
     *            the original URL
     * @param name
     *            the name of the parameter
     * @param value
     *            the value of the parameter
     * @return the URL with the appended parameter
     */
    public static String addUrlParameter(String url, String name, String value) {
        return addUrlParameter(url, name, value, StandardCharsets.UTF_8);
    }

    /**
     * Add a URL parameter to the specified URL with the specified encoding
     * (charset).
     * 
     * @param url
     *            the original URL
     * @param name
     *            the name of the parameter
     * @param value
     *            the value of the parameter
     * @param charset
     *            the encoding (charset)
     * @return the URL with the appended parameter
     */
    public static String addUrlParameter(String url, String name, String value, Charset charset) {

        StringBuilder sb = new StringBuilder(url);
        try {
            String encName = URLEncoder.encode(name, charset.name());
            String encValue = URLEncoder.encode(value, charset.name());

            if (url.indexOf('?') > -1) {
                sb.append('&');
            } else {
                sb.append('?');
            }

            sb.append(encName);
            sb.append('=');
            sb.append(encValue);

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return sb.toString();
    }

    /**
     * Get the language string of a jQuery Datepicker for the specified
     * {@link Locale}.
     * 
     * @param inLocale
     *            the locale
     * @return the jQuery Datepicker language string
     */
    public static String getDatepickerLocale(Locale inLocale) {

        if (inLocale == null) {
            inLocale = Locale.getDefault();
        }
        String id = inLocale.toString().replace('_', '-');
        if (id.startsWith("ar-DZ")) {
            return "ar-DZ";
        }
        if (id.startsWith("ar")) {
            return "ar";
        }
        if (id.startsWith("bg")) {
            return "bg";
        }
        if (id.startsWith("ca")) {
            return "ca";
        }
        if (id.startsWith("cs")) {
            return "cs";
        }
        if (id.startsWith("da")) {
            return "da";
        }
        if (id.startsWith("de")) {
            return "de";
        }
        if (id.startsWith("el")) {
            return "el";
        }
        if (id.startsWith("en-AU")) {
            return "en-AU";
        }
        if (id.startsWith("en-GB")) {
            return "en-GB";
        }
        if (id.startsWith("en-NZ")) {
            return "en-NZ";
        }
        if (id.startsWith("en")) {
            return "en-GB";
        }
        if (id.startsWith("es")) {
            return "es";
        }
        if (id.startsWith("et")) {
            return "et";
        }
        if (id.startsWith("fi")) {
            return "fi";
        }
        if (id.startsWith("fr-CH")) {
            return "fr-CH";
        }
        if (id.startsWith("fr")) {
            return "fr";
        }
        if (id.startsWith("hi")) {
            return "hi";
        }
        if (id.startsWith("hr")) {
            return "hr";
        }
        if (id.startsWith("hu")) {
            return "hu";
        }
        if (id.startsWith("in")) {
            return "id";
        }
        if (id.startsWith("is")) {
            return "is";
        }
        if (id.startsWith("it")) {
            return "it";
        }
        if (id.startsWith("iw")) {
            return "he";
        }
        if (id.startsWith("ja")) {
            return "ja";
        }
        if (id.startsWith("ko")) {
            return "ko";
        }
        if (id.startsWith("lt")) {
            return "lt";
        }
        if (id.startsWith("lv")) {
            return "lv";
        }
        if (id.startsWith("mk")) {
            return "mk";
        }
        if (id.startsWith("ms")) {
            return "ms";
        }
        if (id.startsWith("nl-BE")) {
            return "nl-BE";
        }
        if (id.startsWith("nl")) {
            return "nl";
        }
        if (id.startsWith("no")) {
            return "no";
        }
        if (id.startsWith("pl")) {
            return "pl";
        }
        if (id.startsWith("pt-BR")) {
            return "pt-BR";
        }
        if (id.startsWith("pt")) {
            return "pt";
        }
        if (id.startsWith("ro")) {
            return "ro";
        }
        if (id.startsWith("ru")) {
            return "ru";
        }
        if (id.startsWith("sk")) {
            return "sk";
        }
        if (id.startsWith("sl")) {
            return "sl";
        }
        if (id.startsWith("sq")) {
            return "sq";
        }
        if (id.startsWith("sr")) {
            return "sr";
        }
        if (id.startsWith("sv")) {
            return "sv";
        }
        if (id.startsWith("th")) {
            return "th";
        }
        if (id.startsWith("tr")) {
            return "tr";
        }
        if (id.startsWith("uk")) {
            return "uk";
        }
        if (id.startsWith("vi")) {
            return "vi";
        }
        if (id.startsWith("zh-CN")) {
            return "zh-CN";
        }
        if (id.startsWith("zh-HK")) {
            return "zh-HK";
        }
        if (id.startsWith("zh-TW")) {
            return "zh-TW";
        }
        if (id.startsWith("zh")) {
            return "zh-CN";
        }
        return "en-GB";
    }

}
