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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.Provider;

/**
 * <p>
 * Some coding utilities for generating hashes, encoding and decoding.
 * </p>
 *
 * @author Christian Bremer
 */
public abstract class CodingUtils {

    /**
     * Never construct.
     */
    private CodingUtils() {
        super();
    }

    /**
     * Returns a MessageDigest object that implements the specified digest
     * algorithm.
     *
     * @param algorithm the name of the algorithm requested
     * @return a MessageDigest object that implements the specified algorithm
     * @throws CodingException if a MessageDigestSpi implementation for the specified
     *                         algorithm is not available
     */
    public static MessageDigest getMessageDigestSilently(final String algorithm) {
        Validate.notBlank(algorithm, "Algotithm must not be null or blank."); // NOSONAR
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            throw new CodingException(e);
        }
    }

    /**
     * Returns a MessageDigest object that implements the specified digest
     * algorithm.
     *
     * @param algorithm the name of the algorithm requested
     * @param provider  the provider (may be {@code null})
     * @return a MessageDigest object that implements the specified algorithm
     * @throws CodingException if a MessageDigestSpi implementation for the specified
     *                         algorithm is not available from the specified Provider object
     */
    @SuppressWarnings("SameParameterValue")
    public static MessageDigest getMessageDigestSilently(String algorithm, String provider) {
        Validate.notBlank(algorithm, "Algotithm must not be null or blank.");
        if (StringUtils.isBlank(provider)) {
            return getMessageDigestSilently(algorithm);
        }
        try {
            return MessageDigest.getInstance(algorithm, provider);
        } catch (Exception e) {
            throw new CodingException(e);
        }
    }

    /**
     * Returns a MessageDigest object that implements the specified digest
     * algorithm.
     *
     * @param algorithm the name of the algorithm requested
     * @param provider  the provider (may be {@code null})
     * @return a MessageDigest object that implements the specified algorithm
     * @throws CodingException if a MessageDigestSpi implementation for the specified
     *                         algorithm is not available from the specified Provider object
     */
    @SuppressWarnings("SameParameterValue")
    public static MessageDigest getMessageDigestSilently(String algorithm, Provider provider) {
        Validate.notBlank(algorithm, "Algotithm must not be null or blank.");
        if (provider == null) {
            return getMessageDigestSilently(algorithm);
        }
        try {
            return MessageDigest.getInstance(algorithm, provider);
        } catch (Exception e) {
            throw new CodingException(e);
        }
    }

    /**
     * Calculates a hash value.
     *
     * @param md    the {@link MessageDigest}
     * @param bytes a byte array
     * @return the array of bytes for the hash value
     * @throws IllegalArgumentException if message digest or bytes are {@code null}
     * @throws CodingException          if calculation of the hash fails
     */
    public static byte[] digestSilently(MessageDigest md, byte[] bytes) {
        return digestSilently(md, bytes, null);
    }

    /**
     * Calculates a hash value.
     *
     * @param md       the {@link MessageDigest}
     * @param bytes    a byte array
     * @param listener an input stream listener (may be {@code null})
     * @return the array of bytes for the hash value
     * @throws IllegalArgumentException if message digest or bytes is {@code null}
     * @throws CodingException          if calculation of the hash fails
     */
    public static byte[] digestSilently(MessageDigest md, byte[] bytes, InputStreamListener listener) {
        Validate.notNull(bytes, "Bytes must not be null.");
        return digestSilently(md, new ByteArrayInputStream(bytes), true, listener);
    }

    /**
     * Calculates a hash value.
     *
     * @param md               the {@link MessageDigest}
     * @param inputStream      an input stream
     * @param closeInputStream if {@code true} the input stream will be closed, otherwise it
     *                         will stay open
     * @return the array of bytes for the hash value
     * @throws IllegalArgumentException if message digest or input stream is {@code null}
     * @throws CodingException          if calculation of the hash fails
     */
    @SuppressWarnings("SameParameterValue")
    public static byte[] digestSilently(MessageDigest md, InputStream inputStream, boolean closeInputStream) {
        return digestSilently(md, inputStream, closeInputStream, null);
    }

    /**
     * Calculates a hash value.
     *
     * @param md               the {@link MessageDigest}
     * @param inputStream      an input stream
     * @param closeInputStream if {@code true} the input stream will be closed, otherwise it
     *                         will stay open
     * @param listener         an input stream listener (may be {@code null})
     * @return the array of bytes for the hash value
     * @throws IllegalArgumentException if message digest or input stream is {@code null}
     * @throws CodingException          if calculation of the hash fails
     */
    public static byte[] digestSilently(MessageDigest md, InputStream inputStream, boolean closeInputStream,
                                        InputStreamListener listener) {
        Validate.notNull(md, "MessageDigest must not be null.");
        Validate.notNull(inputStream, "InputStream must not be null."); // NOSONAR
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
            throw new CodingException(e);

        } finally {
            if (closeInputStream) {
                try {
                    inputStream.close();
                } catch (Exception e) { // NOSONAR
                    // ignored
                }
            }
        }
    }

    /**
     * Returns a string with the hex values of the specified byte array.
     *
     * @param bytes a byte array (may be {@code null}
     * @return a string (lower case) with the hex values of the specified byte array or
     * {@code null} if the the byte array is {@code null}
     */
    public static String toHex(byte[] bytes) {
        return toHex(bytes, false);
    }

    /**
     * Returns a string with the hex values of the specified byte array.
     *
     * @param bytes     a byte array (may be {@code null}
     * @param upperCase should the result by upper case?
     * @return a string with the hex values of the specified byte array or
     * {@code null} if the the byte array is {@code null}
     */
    public static String toHex(byte[] bytes, boolean upperCase) {
        if (bytes == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return upperCase ? sb.toString().toUpperCase() : sb.toString().toLowerCase();
    }

    /**
     * Encodes the specified string into a sequence of bytes using the named
     * charset, storing the result into a new byte array.
     *
     * @param text        the string
     * @param charsetName the charset name
     * @return the resultant byte array or {@code null} if the string is
     * {@code null}
     * @throws CodingException if the named charset is not supported
     */
    @SuppressWarnings("SameParameterValue")
    public static byte[] toBytesSilently(String text, String charsetName) {
        if (text == null) {
            return null; // NOSONAR
        }
        if (StringUtils.isBlank(charsetName)) {
            return text.getBytes();
        }
        try {
            return text.getBytes(charsetName);
        } catch (Exception e) {
            throw new CodingException(e);
        }
    }

    /**
     * Encodes the specified string into a sequence of bytes using the named
     * charset, storing the result into a new byte array.
     *
     * @param text    the string
     * @param charset the charset
     * @return the resultant byte array or {@code null} if the string is
     * {@code null}
     */
    public static byte[] toBytesSilently(String text, Charset charset) {
        if (text == null) {
            return null; // NOSONAR
        }
        if (charset == null) {
            return text.getBytes();
        }
        return text.getBytes(charset);
    }

    /**
     * Constructs a new String by decoding the specified array of bytes using
     * the specified charset.
     *
     * @param bytes       the bytes
     * @param charsetName the name of the charset
     * @return the string or {@code null} if the bytes are {@code null}
     * @throws CodingException if the named charset is not supported
     */
    @SuppressWarnings("SameParameterValue")
    public static String toStringSilently(byte[] bytes, String charsetName) {
        if (bytes == null) {
            return null;
        }
        if (StringUtils.isBlank(charsetName)) {
            return new String(bytes);
        }
        try {
            return new String(bytes, charsetName);
        } catch (Exception e) {
            throw new CodingException(e);
        }
    }

    /**
     * Constructs a new String by decoding the specified array of bytes using
     * the specified charset.
     *
     * @param bytes   the bytes
     * @param charset the charset
     * @return the string or {@code null} if the bytes are {@code null}
     */
    public static String toStringSilently(byte[] bytes, Charset charset) {
        if (bytes == null) {
            return null;
        }
        if (charset == null) {
            return new String(bytes);
        }
        return new String(bytes, charset);
    }

}
