package org.apache.commons.net.ftp.parser;

import com.hiti.jumpinfo.JumpInfo;
import com.hiti.sql.oadc.OADCItem;
import com.hiti.ui.drawview.DrawView;
import com.hiti.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import java.text.ParseException;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.ksoap2.SoapEnvelope;

public class MacOsPeterFTPEntryParser extends ConfigurableFTPFileEntryParserImpl {
    static final String DEFAULT_DATE_FORMAT = "MMM d yyyy";
    static final String DEFAULT_RECENT_DATE_FORMAT = "MMM d HH:mm";
    private static final String REGEX = "([bcdelfmpSs-])(((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-])))\\+?\\s+((folder\\s+)|((\\d+)\\s+(\\d+)\\s+))(\\d+)\\s+((?:\\d+[-/]\\d+[-/]\\d+)|(?:\\S{3}\\s+\\d{1,2})|(?:\\d{1,2}\\s+\\S{3}))\\s+(\\d+(?::\\d+)?)\\s+(\\S*)(\\s*.*)";

    public MacOsPeterFTPEntryParser() {
        this(null);
    }

    public MacOsPeterFTPEntryParser(FTPClientConfig config) {
        super(REGEX);
        configure(config);
    }

    public FTPFile parseFTPEntry(String entry) {
        FTPFile file = new FTPFile();
        file.setRawListing(entry);
        boolean isDevice = false;
        if (!matches(entry)) {
            return null;
        }
        int type;
        String typeStr = group(1);
        String hardLinkCount = OADCItem.WATCH_TYPE_NON;
        String filesize = group(20);
        String datestr = group(21) + " " + group(22);
        String name = group(23);
        String endtoken = group(24);
        try {
            file.setTimestamp(super.parseTimestamp(datestr));
        } catch (ParseException e) {
        }
        switch (typeStr.charAt(0)) {
            case JumpInfo.REQUEST_SNAP_PRINT /*45*/:
            case ControllerState.NO_PLAY_ITEM /*102*/:
                type = 0;
                break;
            case DrawView.GS_GARNISH_STATE /*98*/:
            case DrawView.BORDER_STATE /*99*/:
                isDevice = true;
                type = 0;
                break;
            case SoapEnvelope.VER10 /*100*/:
                type = 1;
                break;
            case ControllerState.PLAY_PHOTO /*101*/:
                type = 2;
                break;
            case 'l':
                type = 2;
                break;
            default:
                type = 3;
                break;
        }
        file.setType(type);
        int g = 4;
        int access = 0;
        while (access < 3) {
            file.setPermission(access, 0, !group(g).equals("-"));
            file.setPermission(access, 1, !group(g + 1).equals("-"));
            String execPerm = group(g + 2);
            if (execPerm.equals("-") || Character.isUpperCase(execPerm.charAt(0))) {
                file.setPermission(access, 2, false);
            } else {
                file.setPermission(access, 2, true);
            }
            access++;
            g += 4;
        }
        if (!isDevice) {
            try {
                file.setHardLinkCount(Integer.parseInt(hardLinkCount));
            } catch (NumberFormatException e2) {
            }
        }
        file.setUser(null);
        file.setGroup(null);
        try {
            file.setSize(Long.parseLong(filesize));
        } catch (NumberFormatException e3) {
        }
        if (endtoken == null) {
            file.setName(name);
            return file;
        }
        name = name + endtoken;
        if (type == 2) {
            int end = name.indexOf(" -> ");
            if (end == -1) {
                file.setName(name);
                return file;
            }
            file.setName(name.substring(0, end));
            file.setLink(name.substring(end + 4));
            return file;
        }
        file.setName(name);
        return file;
    }

    protected FTPClientConfig getDefaultConfiguration() {
        return new FTPClientConfig(FTPClientConfig.SYST_UNIX, DEFAULT_DATE_FORMAT, DEFAULT_RECENT_DATE_FORMAT, null, null, null);
    }
}
