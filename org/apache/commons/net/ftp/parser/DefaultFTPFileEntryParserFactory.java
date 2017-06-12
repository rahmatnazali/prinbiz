package org.apache.commons.net.ftp.parser;

import java.util.Locale;
import java.util.regex.Pattern;
import org.apache.commons.net.ftp.Configurable;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFileEntryParser;

public class DefaultFTPFileEntryParserFactory implements FTPFileEntryParserFactory {
    private static final String JAVA_IDENTIFIER = "\\p{javaJavaIdentifierStart}(\\p{javaJavaIdentifierPart})*";
    private static final String JAVA_QUALIFIED_NAME = "(\\p{javaJavaIdentifierStart}(\\p{javaJavaIdentifierPart})*\\.)+\\p{javaJavaIdentifierStart}(\\p{javaJavaIdentifierPart})*";
    private static final Pattern JAVA_QUALIFIED_NAME_PATTERN;

    static {
        JAVA_QUALIFIED_NAME_PATTERN = Pattern.compile(JAVA_QUALIFIED_NAME);
    }

    public FTPFileEntryParser createFileEntryParser(String key) {
        if (key != null) {
            return createFileEntryParser(key, null);
        }
        throw new ParserInitializationException("Parser key cannot be null");
    }

    private FTPFileEntryParser createFileEntryParser(String key, FTPClientConfig config) {
        Class<?> parserClass;
        FTPFileEntryParser parser = null;
        if (JAVA_QUALIFIED_NAME_PATTERN.matcher(key).matches()) {
            try {
                parserClass = Class.forName(key);
                parser = (FTPFileEntryParser) parserClass.newInstance();
            } catch (ClassCastException e) {
                throw new ParserInitializationException(parserClass.getName() + " does not implement the interface " + "org.apache.commons.net.ftp.FTPFileEntryParser.", e);
            } catch (Exception e2) {
                throw new ParserInitializationException("Error initializing parser", e2);
            } catch (ExceptionInInitializerError e3) {
                throw new ParserInitializationException("Error initializing parser", e3);
            } catch (ClassNotFoundException e4) {
            }
        }
        if (parser == null) {
            String ukey = key.toUpperCase(Locale.ENGLISH);
            if (ukey.indexOf(FTPClientConfig.SYST_UNIX) >= 0) {
                parser = new UnixFTPEntryParser(config);
            } else if (ukey.indexOf(FTPClientConfig.SYST_VMS) >= 0) {
                parser = new VMSVersioningFTPEntryParser(config);
            } else if (ukey.indexOf(FTPClientConfig.SYST_NT) >= 0) {
                parser = createNTFTPEntryParser(config);
            } else if (ukey.indexOf(FTPClientConfig.SYST_OS2) >= 0) {
                parser = new OS2FTPEntryParser(config);
            } else if (ukey.indexOf(FTPClientConfig.SYST_OS400) >= 0 || ukey.indexOf(FTPClientConfig.SYST_AS400) >= 0) {
                parser = createOS400FTPEntryParser(config);
            } else if (ukey.indexOf(FTPClientConfig.SYST_MVS) >= 0) {
                parser = new MVSFTPEntryParser();
            } else if (ukey.indexOf(FTPClientConfig.SYST_NETWARE) >= 0) {
                parser = new NetwareFTPEntryParser(config);
            } else if (ukey.indexOf(FTPClientConfig.SYST_MACOS_PETER) >= 0) {
                parser = new MacOsPeterFTPEntryParser(config);
            } else if (ukey.indexOf(FTPClientConfig.SYST_L8) >= 0) {
                parser = new UnixFTPEntryParser(config);
            } else {
                throw new ParserInitializationException("Unknown parser type: " + key);
            }
        }
        if (parser instanceof Configurable) {
            ((Configurable) parser).configure(config);
        }
        return parser;
    }

    public FTPFileEntryParser createFileEntryParser(FTPClientConfig config) throws ParserInitializationException {
        return createFileEntryParser(config.getServerSystemKey(), config);
    }

    public FTPFileEntryParser createUnixFTPEntryParser() {
        return new UnixFTPEntryParser();
    }

    public FTPFileEntryParser createVMSVersioningFTPEntryParser() {
        return new VMSVersioningFTPEntryParser();
    }

    public FTPFileEntryParser createNetwareFTPEntryParser() {
        return new NetwareFTPEntryParser();
    }

    public FTPFileEntryParser createNTFTPEntryParser() {
        return createNTFTPEntryParser(null);
    }

    private FTPFileEntryParser createNTFTPEntryParser(FTPClientConfig config) {
        if (config != null && FTPClientConfig.SYST_NT.equals(config.getServerSystemKey())) {
            return new NTFTPEntryParser(config);
        }
        return new CompositeFileEntryParser(new FTPFileEntryParser[]{new NTFTPEntryParser(config), new UnixFTPEntryParser(config)});
    }

    public FTPFileEntryParser createOS2FTPEntryParser() {
        return new OS2FTPEntryParser();
    }

    public FTPFileEntryParser createOS400FTPEntryParser() {
        return createOS400FTPEntryParser(null);
    }

    private FTPFileEntryParser createOS400FTPEntryParser(FTPClientConfig config) {
        if (config != null && FTPClientConfig.SYST_OS400.equals(config.getServerSystemKey())) {
            return new OS400FTPEntryParser(config);
        }
        return new CompositeFileEntryParser(new FTPFileEntryParser[]{new OS400FTPEntryParser(config), new UnixFTPEntryParser(config)});
    }

    public FTPFileEntryParser createMVSEntryParser() {
        return new MVSFTPEntryParser();
    }
}
