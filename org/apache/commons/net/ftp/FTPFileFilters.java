package org.apache.commons.net.ftp;

public class FTPFileFilters {
    public static final FTPFileFilter ALL;
    public static final FTPFileFilter DIRECTORIES;
    public static final FTPFileFilter NON_NULL;

    /* renamed from: org.apache.commons.net.ftp.FTPFileFilters.1 */
    static class C08191 implements FTPFileFilter {
        C08191() {
        }

        public boolean accept(FTPFile file) {
            return true;
        }
    }

    /* renamed from: org.apache.commons.net.ftp.FTPFileFilters.2 */
    static class C08202 implements FTPFileFilter {
        C08202() {
        }

        public boolean accept(FTPFile file) {
            return file != null;
        }
    }

    /* renamed from: org.apache.commons.net.ftp.FTPFileFilters.3 */
    static class C08213 implements FTPFileFilter {
        C08213() {
        }

        public boolean accept(FTPFile file) {
            return file != null && file.isDirectory();
        }
    }

    static {
        ALL = new C08191();
        NON_NULL = new C08202();
        DIRECTORIES = new C08213();
    }
}
