package org.apache.commons.net.ftp;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Formatter;
import org.xmlpull.v1.XmlPullParser;

public class FTPFile implements Serializable {
    public static final int DIRECTORY_TYPE = 1;
    public static final int EXECUTE_PERMISSION = 2;
    public static final int FILE_TYPE = 0;
    public static final int GROUP_ACCESS = 1;
    public static final int READ_PERMISSION = 0;
    public static final int SYMBOLIC_LINK_TYPE = 2;
    public static final int UNKNOWN_TYPE = 3;
    public static final int USER_ACCESS = 0;
    public static final int WORLD_ACCESS = 2;
    public static final int WRITE_PERMISSION = 1;
    private static final long serialVersionUID = 9010790363003271996L;
    private Calendar _date;
    private String _group;
    private int _hardLinkCount;
    private String _link;
    private String _name;
    private final boolean[][] _permissions;
    private String _rawListing;
    private long _size;
    private int _type;
    private String _user;

    public FTPFile() {
        this._permissions = (boolean[][]) Array.newInstance(Boolean.TYPE, new int[]{UNKNOWN_TYPE, UNKNOWN_TYPE});
        this._rawListing = null;
        this._type = UNKNOWN_TYPE;
        this._hardLinkCount = USER_ACCESS;
        this._size = -1;
        this._user = XmlPullParser.NO_NAMESPACE;
        this._group = XmlPullParser.NO_NAMESPACE;
        this._date = null;
        this._name = null;
    }

    public void setRawListing(String rawListing) {
        this._rawListing = rawListing;
    }

    public String getRawListing() {
        return this._rawListing;
    }

    public boolean isDirectory() {
        return this._type == WRITE_PERMISSION;
    }

    public boolean isFile() {
        return this._type == 0;
    }

    public boolean isSymbolicLink() {
        return this._type == WORLD_ACCESS;
    }

    public boolean isUnknown() {
        return this._type == UNKNOWN_TYPE;
    }

    public void setType(int type) {
        this._type = type;
    }

    public int getType() {
        return this._type;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getName() {
        return this._name;
    }

    public void setSize(long size) {
        this._size = size;
    }

    public long getSize() {
        return this._size;
    }

    public void setHardLinkCount(int links) {
        this._hardLinkCount = links;
    }

    public int getHardLinkCount() {
        return this._hardLinkCount;
    }

    public void setGroup(String group) {
        this._group = group;
    }

    public String getGroup() {
        return this._group;
    }

    public void setUser(String user) {
        this._user = user;
    }

    public String getUser() {
        return this._user;
    }

    public void setLink(String link) {
        this._link = link;
    }

    public String getLink() {
        return this._link;
    }

    public void setTimestamp(Calendar date) {
        this._date = date;
    }

    public Calendar getTimestamp() {
        return this._date;
    }

    public void setPermission(int access, int permission, boolean value) {
        this._permissions[access][permission] = value;
    }

    public boolean hasPermission(int access, int permission) {
        return this._permissions[access][permission];
    }

    public String toString() {
        return getRawListing();
    }

    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();
        Formatter fmt = new Formatter(sb);
        sb.append(formatType());
        sb.append(permissionToString(USER_ACCESS));
        sb.append(permissionToString(WRITE_PERMISSION));
        sb.append(permissionToString(WORLD_ACCESS));
        Object[] objArr = new Object[WRITE_PERMISSION];
        objArr[USER_ACCESS] = Integer.valueOf(getHardLinkCount());
        fmt.format(" %4d", objArr);
        objArr = new Object[WORLD_ACCESS];
        objArr[USER_ACCESS] = getGroup();
        objArr[WRITE_PERMISSION] = getUser();
        fmt.format(" %-8s %-8s", objArr);
        objArr = new Object[WRITE_PERMISSION];
        objArr[USER_ACCESS] = Long.valueOf(getSize());
        fmt.format(" %8d", objArr);
        Calendar timestamp = getTimestamp();
        if (timestamp != null) {
            objArr = new Object[WRITE_PERMISSION];
            objArr[USER_ACCESS] = timestamp;
            fmt.format(" %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", objArr);
            objArr = new Object[WRITE_PERMISSION];
            objArr[USER_ACCESS] = timestamp;
            fmt.format(" %1$tZ", objArr);
            sb.append(' ');
        }
        sb.append(' ');
        sb.append(getName());
        fmt.close();
        return sb.toString();
    }

    private char formatType() {
        switch (this._type) {
            case USER_ACCESS /*0*/:
                return '-';
            case WRITE_PERMISSION /*1*/:
                return 'd';
            case WORLD_ACCESS /*2*/:
                return 'l';
            default:
                return '?';
        }
    }

    private String permissionToString(int access) {
        StringBuilder sb = new StringBuilder();
        if (hasPermission(access, USER_ACCESS)) {
            sb.append('r');
        } else {
            sb.append('-');
        }
        if (hasPermission(access, WRITE_PERMISSION)) {
            sb.append('w');
        } else {
            sb.append('-');
        }
        if (hasPermission(access, WORLD_ACCESS)) {
            sb.append('x');
        } else {
            sb.append('-');
        }
        return sb.toString();
    }
}
