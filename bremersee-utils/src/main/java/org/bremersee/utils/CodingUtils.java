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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.Provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * <p>
 * Some coding utilities for generating hashes, encoding and decoding.
 * </p>
 * 
 * @author Christian Bremer
 *
 */
public abstract class CodingUtils {

	private CodingUtils() {
		// utility class, never constructed
	}

	/**
	 * Returns a MessageDigest object that implements the specified digest
	 * algorithm.
	 * 
	 * @param algorithm
	 *            the name of the algorithm requested
	 * @return a MessageDigest object that implements the specified algorithm
	 * @throws RuntimeException
	 *             if a MessageDigestSpi implementation for the specified
	 *             algorithm is not available
	 */
	public static MessageDigest getInstanceSilently(String algorithm) {
		Validate.notBlank(algorithm, "Algotithm must not be null or blank.");
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns a MessageDigest object that implements the specified digest
	 * algorithm.
	 * 
	 * @param algorithm
	 *            the name of the algorithm requested
	 * @param provider
	 *            the provider (may be {@code null})
	 * @return a MessageDigest object that implements the specified algorithm
	 * @throws RuntimeException
	 *             if a MessageDigestSpi implementation for the specified
	 *             algorithm is not available from the specified Provider object
	 */
	public static MessageDigest getInstanceSilently(String algorithm, String provider) {
		Validate.notBlank(algorithm, "Algotithm must not be null or blank.");
		if (StringUtils.isBlank(provider)) {
			return getInstanceSilently(algorithm);
		}
		try {
			return MessageDigest.getInstance(algorithm, provider);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns a MessageDigest object that implements the specified digest
	 * algorithm.
	 * 
	 * @param algorithm
	 *            the name of the algorithm requested
	 * @param provider
	 *            the provider (may be {@code null})
	 * @return a MessageDigest object that implements the specified algorithm
	 * @throws RuntimeException
	 *             if a MessageDigestSpi implementation for the specified
	 *             algorithm is not available from the specified Provider object
	 */
	public static MessageDigest getInstanceSilently(String algorithm, Provider provider) {
		Validate.notBlank(algorithm, "Algotithm must not be null or blank.");
		if (provider == null) {
			return getInstanceSilently(algorithm);
		}
		try {
			return MessageDigest.getInstance(algorithm, provider);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Calculates a hash value.
	 * 
	 * @param md
	 *            the {@link MessageDigest}
	 * @param bytes
	 *            a byte array
	 * @return the array of bytes for the hash value
	 * @throws IllegalArgumentException
	 *             if message digest or bytes are {@code null}
	 * @throws RuntimeException
	 *             if calculation of the hash fails
	 */
	public static byte[] digestSilenty(MessageDigest md, byte[] bytes) {
		return digestSilenty(md, bytes, null);
	}

	/**
	 * Calculates a hash value.
	 * 
	 * @param md
	 *            the {@link MessageDigest}
	 * @param bytes
	 *            a byte array
	 * @param listener
	 *            an input stream listener (may by {@code null})
	 * @return the array of bytes for the hash value
	 * @throws IllegalArgumentException
	 *             if message digest or bytes are {@code null}
	 * @throws RuntimeException
	 *             if calculation of the hash fails
	 */
	public static byte[] digestSilenty(MessageDigest md, byte[] bytes, InputStreamListener listener) {
		Validate.notNull(bytes, "Bytes must not be null.");
		return digestSilenty(md, new ByteArrayInputStream(bytes), true, listener);
	}

	/**
	 * Calculates a hash value.
	 * 
	 * @param md
	 *            the {@link MessageDigest}
	 * @param inputStream
	 *            an input stream
	 * @param closeInputStream
	 *            if {@code true} the input stream will be closed, otherwise it
	 *            will stay open
	 * @return the array of bytes for the hash value
	 * @throws IllegalArgumentException
	 *             if message digest or input stream are {@code null}
	 * @throws RuntimeException
	 *             if calculation of the hash fails
	 */
	public static byte[] digestSilenty(MessageDigest md, InputStream inputStream, boolean closeInputStream) {
		return digestSilenty(md, inputStream, closeInputStream, null);
	}

	/**
	 * Calculates a hash value.
	 * 
	 * @param md
	 *            the {@link MessageDigest}
	 * @param inputStream
	 *            an input stream
	 * @param closeInputStream
	 *            if {@code true} the input stream will be closed, otherwise it
	 *            will stay open
	 * @param listener
	 *            an input stream listener (may by {@code null})
	 * @return the array of bytes for the hash value
	 * @throws IllegalArgumentException
	 *             if message digest or input stream are {@code null}
	 * @throws RuntimeException
	 *             if calculation of the hash fails
	 */
	public static byte[] digestSilenty(MessageDigest md, InputStream inputStream, boolean closeInputStream,
			InputStreamListener listener) {
		Validate.notNull(md, "MessageDigest must not be null.");
		Validate.notNull(md, "InputStream must not be null.");
		try {
			md.reset();
			int len;
			byte[] buffer = new byte[4096];
			while ((len = inputStream.read(buffer)) != -1) {
				md.update(buffer, 0, len);
				if (listener != null) {
					listener.onReadBytes(buffer, 0, len);
				}
			}
			return md.digest();

		} catch (Exception e) {
			throw new RuntimeException(e);

		} finally {
			if (closeInputStream) {
				try {
					inputStream.close();
				} catch (Exception e) {
					// ignored
				}
			}
		}
	}

	/**
	 * Returns a string with the hex values of the specified byte array.
	 * 
	 * @param bytes
	 *            a byte array (may be {@code null} - an empty string will be
	 *            returned than)
	 * @return a string with the hex values of the specified byte array
	 * @throws IllegalArgumentException
	 *             if bytes are {@code null}
	 */
	public static String toHex(byte[] bytes) {
		Validate.notNull(bytes, "Bytes must not be null.");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	/**
	 * Encodes the specified string into a sequence of bytes using the named
	 * charset, storing the result into a new byte array.
	 * 
	 * @param text
	 *            the string
	 * @param charsetName
	 *            the charset name
	 * @return the resultant byte array
	 * @throws IllegalArgumentException
	 *             if the text is {@code null}
	 * @throws RuntimeException
	 *             if the named charset is not supported
	 */
	public static byte[] toBytesSilently(String text, String charsetName) {
		Validate.notNull(text, "Text must not be null.");
		if (StringUtils.isBlank(charsetName)) {
			return text.getBytes();
		}
		try {
			return text.getBytes(charsetName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Constructs a new String by decoding the specified array of bytes using
	 * the specified charset.
	 * 
	 * @param bytes
	 *            the bytes
	 * @param charsetName
	 *            the name of the charset
	 * @return the string
	 * @throws IllegalArgumentException
	 *             if bytes are {@code null}
	 * @throws RuntimeException
	 *             if the named charset is not supported
	 */
	public static String toStringSilently(byte[] bytes, String charsetName) {
		Validate.notNull(bytes, "Bytes must not be null.");
		if (StringUtils.isBlank(charsetName)) {
			return new String(bytes);
		}
		try {
			return new String(bytes, charsetName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
