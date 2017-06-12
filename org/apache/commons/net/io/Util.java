package org.apache.commons.net.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

public final class Util {
    public static final int DEFAULT_COPY_BUFFER_SIZE = 1024;

    private Util() {
    }

    public static final long copyStream(InputStream source, OutputStream dest, int bufferSize, long streamSize, CopyStreamListener listener, boolean flush) throws CopyStreamException {
        long total = 0;
        if (bufferSize < 0) {
            bufferSize = DEFAULT_COPY_BUFFER_SIZE;
        }
        byte[] buffer = new byte[bufferSize];
        while (true) {
            try {
                int bytes = source.read(buffer);
                if (bytes == -1) {
                    break;
                } else if (bytes == 0) {
                    bytes = source.read();
                    if (bytes < 0) {
                        break;
                    }
                    dest.write(bytes);
                    if (flush) {
                        dest.flush();
                    }
                    total++;
                    if (listener != null) {
                        listener.bytesTransferred(total, 1, streamSize);
                    }
                } else {
                    dest.write(buffer, 0, bytes);
                    if (flush) {
                        dest.flush();
                    }
                    total += (long) bytes;
                    if (listener != null) {
                        listener.bytesTransferred(total, bytes, streamSize);
                    }
                }
            } catch (IOException e) {
                throw new CopyStreamException("IOException caught while copying.", total, e);
            }
        }
        return total;
    }

    public static final long copyStream(InputStream source, OutputStream dest, int bufferSize, long streamSize, CopyStreamListener listener) throws CopyStreamException {
        return copyStream(source, dest, bufferSize, streamSize, listener, true);
    }

    public static final long copyStream(InputStream source, OutputStream dest, int bufferSize) throws CopyStreamException {
        return copyStream(source, dest, bufferSize, -1, null);
    }

    public static final long copyStream(InputStream source, OutputStream dest) throws CopyStreamException {
        return copyStream(source, dest, DEFAULT_COPY_BUFFER_SIZE);
    }

    public static final long copyReader(Reader source, Writer dest, int bufferSize, long streamSize, CopyStreamListener listener) throws CopyStreamException {
        long total = 0;
        if (bufferSize < 0) {
            bufferSize = DEFAULT_COPY_BUFFER_SIZE;
        }
        char[] buffer = new char[bufferSize];
        while (true) {
            int chars = source.read(buffer);
            if (chars == -1) {
                break;
            } else if (chars == 0) {
                chars = source.read();
                if (chars < 0) {
                    break;
                }
                dest.write(chars);
                dest.flush();
                total++;
                if (listener != null) {
                    listener.bytesTransferred(total, chars, streamSize);
                }
            } else {
                try {
                    dest.write(buffer, 0, chars);
                    dest.flush();
                    total += (long) chars;
                    if (listener != null) {
                        listener.bytesTransferred(total, chars, streamSize);
                    }
                } catch (IOException e) {
                    throw new CopyStreamException("IOException caught while copying.", total, e);
                }
            }
        }
        return total;
    }

    public static final long copyReader(Reader source, Writer dest, int bufferSize) throws CopyStreamException {
        return copyReader(source, dest, bufferSize, -1, null);
    }

    public static final long copyReader(Reader source, Writer dest) throws CopyStreamException {
        return copyReader(source, dest, DEFAULT_COPY_BUFFER_SIZE);
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeQuietly(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }
}
