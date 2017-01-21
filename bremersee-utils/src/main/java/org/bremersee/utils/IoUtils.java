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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * <p>
 * Some IO utilities.
 * </p>
 * 
 * @author Christian Bremer
 */
public abstract class IoUtils {

    /**
     * Never construct.
     */
    private IoUtils() {
        super();
    }

    /**
     * Copies data.
     * 
     * @param inputStream
     *            the input data
     * @param outputStream
     *            the output destination
     * @param closeStreams
     *            if {@code true} the streams will be closed otherwise they will stay open
     * @return the length of copied data
     * @throws IORuntimeException
     *             if copying fails
     */
    @SuppressWarnings("SameParameterValue")
    public static long copySilently(final InputStream inputStream, final OutputStream outputStream,
                                    final boolean closeStreams) {
        return copySilently(inputStream, outputStream, closeStreams, null);
    }

    /**
     * Copies data.
     * 
     * @param inputStream
     *            the input data
     * @param outputStream
     *            the output destination
     * @param closeStreams
     *            if {@code true} the streams will be closed otherwise they will stay open
     * @param listener
     *            a listener (may be {@code null})
     * @return the length of copied data
     * @throws IORuntimeException
     *             if copying fails
     */
    public static long copySilently(final InputStream inputStream, final OutputStream outputStream,
                                    final boolean closeStreams, final InputStreamListener listener) {
        long totalLen = 0L;
        int len;
        byte[] buf = new byte[4096];
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
                totalLen = totalLen + len;
                if (listener != null) {
                    listener.onReadBytes(buf, 0, len);
                }
            }
            outputStream.flush();

        } catch (Exception e) {
            throw new IORuntimeException(e);
        } finally {
        	if (closeStreams) {
        	    closeSilently(inputStream);
        	    closeSilently(outputStream);
        	}
        }
        return totalLen;
    }

    /**
     * Copies characters.
     * 
     * @param reader
     *            the reader
     * @param writer
     *            the writer
     * @param closeReaderAndWriter
     *            if {@code true} the reader and writer will be closed otherwise they will stay open
     * @return the size of the characters
     * @throws IORuntimeException
     *             if copying fails
     */
    @SuppressWarnings("SameParameterValue")
    public static long copySilently(final Reader reader, final Writer writer, final boolean closeReaderAndWriter) {
        return copySilently(reader, writer, closeReaderAndWriter, null);
    }

    /**
     * Copies characters.
     * 
     * @param reader
     *            the reader
     * @param writer
     *            the writer
     * @param closeReaderAndWriter
     *            if {@code true} the reader and writer will be closed otherwise they will stay open
     * @param listener
     *            a listener (may be {@code null})
     * @return the size of the characters
     * @throws IORuntimeException
     *             if copying fails
     */
    public static long copySilently(final Reader reader, final Writer writer, final boolean closeReaderAndWriter,
                                    final ReaderListener listener) {

        long totalLen = 0L;
        int len;
        char[] buf = new char[4096];
        try {
            while ((len = reader.read(buf)) != -1) {
                writer.write(buf, 0, len);
                totalLen = totalLen + len;
                if (listener != null) {
                    listener.onReadChars(buf, 0, len);
                }
            }
            writer.flush();

        } catch (Exception e) {
            throw new IORuntimeException(e);
        } finally {
			if (closeReaderAndWriter) {
			    closeSilently(reader);
			    closeSilently(writer);
			}
		}
        return totalLen;
    }

    @SuppressWarnings("WeakerAccess")
    public static void closeSilently(final InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException ignored) { // NOSONAR
                // ignored
            }
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static void closeSilently(final OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException ignored) { // NOSONAR
                // ignored
            }
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static void closeSilently(final Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException ignored) { // NOSONAR
                // ignored
            }
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static void closeSilently(final Writer writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException ignored) { // NOSONAR
                // ignored
            }
        }
    }

}
