package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import java.util.List;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.xmlpull.v1.XmlPullParser;

public class MVSFTPEntryParser extends ConfigurableFTPFileEntryParserImpl {
    static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd HH:mm";
    static final String FILE_LIST_REGEX = "\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s+[FV]\\S*\\s+\\S+\\s+\\S+\\s+(PS|PO|PO-E)\\s+(\\S+)\\s*";
    static final int FILE_LIST_TYPE = 0;
    static final String JES_LEVEL_1_LIST_REGEX = "(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*";
    static final int JES_LEVEL_1_LIST_TYPE = 3;
    static final String JES_LEVEL_2_LIST_REGEX = "(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+).*";
    static final int JES_LEVEL_2_LIST_TYPE = 4;
    static final String MEMBER_LIST_REGEX = "(\\S+)\\s+\\S+\\s+\\S+\\s+(\\S+)\\s+(\\S+)\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s*";
    static final int MEMBER_LIST_TYPE = 1;
    static final int UNIX_LIST_TYPE = 2;
    static final int UNKNOWN_LIST_TYPE = -1;
    private int isType;
    private UnixFTPEntryParser unixFTPEntryParser;

    public MVSFTPEntryParser() {
        super(XmlPullParser.NO_NAMESPACE);
        this.isType = UNKNOWN_LIST_TYPE;
        super.configure(null);
    }

    public FTPFile parseFTPEntry(String entry) {
        boolean isParsed = false;
        FTPFile f = new FTPFile();
        if (this.isType == 0) {
            isParsed = parseFileList(f, entry);
        } else if (this.isType == MEMBER_LIST_TYPE) {
            isParsed = parseMemberList(f, entry);
            if (!isParsed) {
                isParsed = parseSimpleEntry(f, entry);
            }
        } else if (this.isType == UNIX_LIST_TYPE) {
            isParsed = parseUnixList(f, entry);
        } else if (this.isType == JES_LEVEL_1_LIST_TYPE) {
            isParsed = parseJeslevel1List(f, entry);
        } else if (this.isType == JES_LEVEL_2_LIST_TYPE) {
            isParsed = parseJeslevel2List(f, entry);
        }
        if (isParsed) {
            return f;
        }
        return null;
    }

    private boolean parseFileList(FTPFile file, String entry) {
        if (!matches(entry)) {
            return false;
        }
        file.setRawListing(entry);
        String name = group(UNIX_LIST_TYPE);
        String dsorg = group(MEMBER_LIST_TYPE);
        file.setName(name);
        if ("PS".equals(dsorg)) {
            file.setType(FILE_LIST_TYPE);
        } else if (!"PO".equals(dsorg) && !"PO-E".equals(dsorg)) {
            return false;
        } else {
            file.setType(MEMBER_LIST_TYPE);
        }
        return true;
    }

    private boolean parseMemberList(FTPFile file, String entry) {
        if (!matches(entry)) {
            return false;
        }
        file.setRawListing(entry);
        String name = group(MEMBER_LIST_TYPE);
        String datestr = group(UNIX_LIST_TYPE) + " " + group(JES_LEVEL_1_LIST_TYPE);
        file.setName(name);
        file.setType(FILE_LIST_TYPE);
        try {
            file.setTimestamp(super.parseTimestamp(datestr));
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean parseSimpleEntry(FTPFile file, String entry) {
        if (entry == null || entry.trim().length() <= 0) {
            return false;
        }
        file.setRawListing(entry);
        file.setName(entry.split(" ")[FILE_LIST_TYPE]);
        file.setType(FILE_LIST_TYPE);
        return true;
    }

    private boolean parseUnixList(FTPFile file, String entry) {
        if (this.unixFTPEntryParser.parseFTPEntry(entry) == null) {
            return false;
        }
        return true;
    }

    private boolean parseJeslevel1List(FTPFile file, String entry) {
        if (!matches(entry) || !group(JES_LEVEL_1_LIST_TYPE).equalsIgnoreCase("OUTPUT")) {
            return false;
        }
        file.setRawListing(entry);
        file.setName(group(UNIX_LIST_TYPE));
        file.setType(FILE_LIST_TYPE);
        return true;
    }

    private boolean parseJeslevel2List(FTPFile file, String entry) {
        if (!matches(entry) || !group(JES_LEVEL_2_LIST_TYPE).equalsIgnoreCase("OUTPUT")) {
            return false;
        }
        file.setRawListing(entry);
        file.setName(group(UNIX_LIST_TYPE));
        file.setType(FILE_LIST_TYPE);
        return true;
    }

    public List<String> preParse(List<String> orig) {
        if (orig != null && orig.size() > 0) {
            String header = (String) orig.get(FILE_LIST_TYPE);
            if (header.indexOf("Volume") >= 0 && header.indexOf("Dsname") >= 0) {
                setType(FILE_LIST_TYPE);
                super.setRegex(FILE_LIST_REGEX);
            } else if (header.indexOf("Name") >= 0 && header.indexOf("Id") >= 0) {
                setType(MEMBER_LIST_TYPE);
                super.setRegex(MEMBER_LIST_REGEX);
            } else if (header.indexOf("total") == 0) {
                setType(UNIX_LIST_TYPE);
                this.unixFTPEntryParser = new UnixFTPEntryParser();
            } else if (header.indexOf("Spool Files") >= 30) {
                setType(JES_LEVEL_1_LIST_TYPE);
                super.setRegex(JES_LEVEL_1_LIST_REGEX);
            } else if (header.indexOf("JOBNAME") != 0 || header.indexOf("JOBID") <= 8) {
                setType(UNKNOWN_LIST_TYPE);
            } else {
                setType(JES_LEVEL_2_LIST_TYPE);
                super.setRegex(JES_LEVEL_2_LIST_REGEX);
            }
            if (this.isType != JES_LEVEL_1_LIST_TYPE) {
                orig.remove(FILE_LIST_TYPE);
            }
        }
        return orig;
    }

    void setType(int type) {
        this.isType = type;
    }

    protected FTPClientConfig getDefaultConfiguration() {
        return new FTPClientConfig(FTPClientConfig.SYST_MVS, DEFAULT_DATE_FORMAT, null, null, null, null);
    }
}
