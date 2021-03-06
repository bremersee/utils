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

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <p>
 * Some methods to work with a {@link ResourceBundle}.
 * </p>
 * 
 * @author Christian Bremer
 */
public abstract class ResourceBundleUtils {

	/**
	 * Never construct.
	 */
	private ResourceBundleUtils() {
		super();
	}

	/**
	 * Return the resource bundle with the specified base name and locale.
	 * 
	 * @param baseName
	 *            the base name of the resource bundle, a fully qualified class
	 *            name
	 * @param locale
	 *            the locale for which a resource bundle is desired
	 * @return a resource bundle for the given base name and locale
	 * @see ResourceBundle#getBundle(String, Locale)
	 */
	@SuppressWarnings("unused")
    public static ResourceBundle getResourceBundle(final String baseName, final Locale locale) {
		return getResourceBundle(baseName, locale, null);
	}

	/**
	 * Return the resource bundle with the specified base name and locale.
	 * 
	 * @param baseName
	 *            the base name of the resource bundle, a fully qualified class
	 *            name
	 * @param locale
	 *            the locale for which a resource bundle is desired
	 * @param classLoader
	 *            the class loader from which to load the resource bundle
	 * @return a resource bundle for the given base name and locale
	 * @see ResourceBundle#getBundle(String, Locale)
	 */
	@SuppressWarnings("WeakerAccess")
    public static ResourceBundle getResourceBundle(final String baseName, final Locale locale, final ClassLoader classLoader) {
		Validate.notEmpty(baseName, "Base name of the resource bundle must not be null or blank.");
		if (classLoader == null) {
			return ResourceBundle.getBundle(baseName, locale == null ? Locale.getDefault() : locale);
		}
		return ResourceBundle.getBundle(baseName, locale == null ? Locale.getDefault() : locale, classLoader);
	}

	/**
	 * Gets a string for the given key from this resource bundle or one of its
	 * parents. If the string contains placeholder like {@code {0}}, they will
	 * be replaced with the specified argument.
	 * 
	 * @param baseName
	 *            the base name of the resource bundle, a fully qualified class
	 *            name
	 * @param locale
	 *            the locale for which a resource bundle is desired
	 * @param messageCode
	 *            the key for the desired string
	 * @param messageArgs
	 *            some arguments, may be {@code null}
	 * @param defaultMessage
	 *            a default message
	 * @return the string for the given key or the default message
	 */
	@SuppressWarnings({"unused", "SameParameterValue"})
    public static String getLocalizedMessage(final String baseName, final Locale locale, final String messageCode, final Object[] messageArgs,
                                             final String defaultMessage) {
		return getLocalizedMessage(baseName, locale, null, messageCode, messageArgs, defaultMessage);
	}

	/**
	 * Gets a string for the given key from this resource bundle or one of its
	 * parents. If the string contains placeholder like {@code {0}}, they will
	 * be replaced with the specified argument.
	 * 
	 * @param baseName
	 *            the base name of the resource bundle, a fully qualified class
	 *            name
	 * @param locale
	 *            the locale for which a resource bundle is desired
	 * @param classLoader
	 *            the class loader from which to load the resource bundle
	 * @param messageCode
	 *            the key for the desired string
	 * @param messageArgs
	 *            some arguments, may be {@code null}
	 * @param defaultMessage
	 *            a default message
	 * @return the string for the given key or the default message
	 */
	@SuppressWarnings({"WeakerAccess", "SameParameterValue"})
    public static String getLocalizedMessage(final String baseName, final Locale locale, final ClassLoader classLoader,
                                             final String messageCode, final Object[] messageArgs, final String defaultMessage) {

        ResourceBundle resourceBundle = getResourceBundle(baseName, locale, classLoader);
        if (messageCode != null && resourceBundle.containsKey(messageCode)) {
            String msg = resourceBundle.getString(messageCode);
            if (messageArgs == null || messageArgs.length == 0) {
                return msg;
            } else {
                int i = 0;
                while (i < messageArgs.length) {
                    String target = "{" + i + "}";
                    String replacement = messageArgs[i] == null ? "" : messageArgs[i].toString();
                    msg = msg.replace(target, replacement);
                    i++;
                }
                return msg;
            }
        }
		return defaultMessage == null ? "" : defaultMessage;
	}

}
