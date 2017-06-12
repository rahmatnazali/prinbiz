package org.apache.commons.net.ftp.parser;

import com.hiti.ui.drawview.DrawView;
import com.hiti.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileEntryParserImpl;
import org.apache.commons.net.nntp.NNTP;
import org.ksoap2.SoapEnvelope;

public class MLSxEntryParser extends FTPFileEntryParserImpl {
    private static final MLSxEntryParser PARSER;
    private static final HashMap<String, Integer> TYPE_TO_INT;
    private static int[] UNIX_GROUPS;
    private static int[][] UNIX_PERMS;

    static {
        PARSER = new MLSxEntryParser();
        TYPE_TO_INT = new HashMap();
        TYPE_TO_INT.put("file", Integer.valueOf(0));
        TYPE_TO_INT.put("cdir", Integer.valueOf(1));
        TYPE_TO_INT.put("pdir", Integer.valueOf(1));
        TYPE_TO_INT.put("dir", Integer.valueOf(1));
        UNIX_GROUPS = new int[]{0, 1, 2};
        r0 = new int[8][];
        r0[1] = new int[]{2};
        r0[2] = new int[]{1};
        r0[3] = new int[]{2, 1};
        r0[4] = new int[]{0};
        r0[5] = new int[]{0, 2};
        r0[6] = new int[]{0, 1};
        r0[7] = new int[]{0, 1, 2};
        UNIX_PERMS = r0;
    }

    public FTPFile parseFTPEntry(String entry) {
        String[] parts = entry.split(" ", 2);
        int length = parts.length;
        if (r0 != 2) {
            return null;
        }
        FTPFile file = new FTPFile();
        file.setRawListing(entry);
        file.setName(parts[1]);
        String[] facts = parts[0].split(";");
        boolean hasUnixMode = parts[0].toLowerCase(Locale.ENGLISH).contains("unix.mode=");
        for (String fact : facts) {
            String[] factparts = fact.split("=");
            length = factparts.length;
            if (r0 == 2) {
                String factname = factparts[0].toLowerCase(Locale.ENGLISH);
                String factvalue = factparts[1];
                String valueLowerCase = factvalue.toLowerCase(Locale.ENGLISH);
                if ("size".equals(factname)) {
                    file.setSize(Long.parseLong(factvalue));
                } else {
                    if ("sizd".equals(factname)) {
                        file.setSize(Long.parseLong(factvalue));
                    } else {
                        if ("modify".equals(factname)) {
                            SimpleDateFormat simpleDateFormat;
                            if (factvalue.contains(".")) {
                                simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
                            } else {
                                simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                            }
                            TimeZone GMT = TimeZone.getTimeZone("GMT");
                            sdf.setTimeZone(GMT);
                            GregorianCalendar gc = new GregorianCalendar(GMT);
                            try {
                                gc.setTime(sdf.parse(factvalue));
                            } catch (ParseException e) {
                            }
                            file.setTimestamp(gc);
                        } else {
                            if ("type".equals(factname)) {
                                Integer intType = (Integer) TYPE_TO_INT.get(valueLowerCase);
                                if (intType == null) {
                                    file.setType(3);
                                } else {
                                    file.setType(intType.intValue());
                                }
                            } else {
                                if (factname.startsWith("unix.")) {
                                    String unixfact = factname.substring("unix.".length()).toLowerCase(Locale.ENGLISH);
                                    if ("group".equals(unixfact)) {
                                        file.setGroup(factvalue);
                                    } else {
                                        if ("owner".equals(unixfact)) {
                                            file.setUser(factvalue);
                                        } else {
                                            if ("mode".equals(unixfact)) {
                                                int off = factvalue.length() - 3;
                                                for (int i = 0; i < 3; i++) {
                                                    int ch = factvalue.charAt(off + i) - 48;
                                                    if (ch >= 0 && ch <= 7) {
                                                        for (int p : UNIX_PERMS[ch]) {
                                                            file.setPermission(UNIX_GROUPS[i], p, true);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else if (!hasUnixMode) {
                                    if ("perm".equals(factname)) {
                                        doUnixPerms(file, valueLowerCase);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return file;
    }

    private void doUnixPerms(FTPFile file, String valueLowerCase) {
        for (char c : valueLowerCase.toCharArray()) {
            switch (c) {
                case DrawView.COLOR_GARNISH_STATE /*97*/:
                    file.setPermission(0, 1, true);
                    break;
                case DrawView.BORDER_STATE /*99*/:
                    file.setPermission(0, 1, true);
                    break;
                case SoapEnvelope.VER10 /*100*/:
                    file.setPermission(0, 1, true);
                    break;
                case ControllerState.PLAY_PHOTO /*101*/:
                    file.setPermission(0, 0, true);
                    break;
                case 'l':
                    file.setPermission(0, 2, true);
                    break;
                case 'm':
                    file.setPermission(0, 1, true);
                    break;
                case 'p':
                    file.setPermission(0, 1, true);
                    break;
                case 'r':
                    file.setPermission(0, 0, true);
                    break;
                case NNTP.DEFAULT_PORT /*119*/:
                    file.setPermission(0, 1, true);
                    break;
                default:
                    break;
            }
        }
    }

    public static FTPFile parseEntry(String entry) {
        return PARSER.parseFTPEntry(entry);
    }

    public static MLSxEntryParser getInstance() {
        return PARSER;
    }
}
