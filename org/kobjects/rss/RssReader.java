package org.kobjects.rss;

import java.io.IOException;
import java.io.Reader;
import org.kobjects.xml.XmlReader;

public class RssReader {
    public static final int AUTHOR = 4;
    public static final int DATE = 3;
    public static final int DESCRIPTION = 2;
    public static final int LINK = 1;
    public static final int TITLE = 0;
    XmlReader xr;

    public RssReader(Reader reader) throws IOException {
        this.xr = new XmlReader(reader);
    }

    void readText(StringBuffer buf) throws IOException {
        while (this.xr.next() != DATE) {
            switch (this.xr.getType()) {
                case DESCRIPTION /*2*/:
                    readText(buf);
                    break;
                case AUTHOR /*4*/:
                    buf.append(this.xr.getText());
                    break;
                default:
                    break;
            }
        }
    }

    public String[] next() throws IOException {
        String[] item = new String[5];
        while (this.xr.next() != LINK) {
            if (this.xr.getType() == DESCRIPTION) {
                String n = this.xr.getName().toLowerCase();
                if (n.equals("item") || n.endsWith(":item")) {
                    while (this.xr.next() != DATE) {
                        if (this.xr.getType() == DESCRIPTION) {
                            String name = this.xr.getName().toLowerCase();
                            int cut = name.indexOf(":");
                            if (cut != -1) {
                                name = name.substring(cut + LINK);
                            }
                            StringBuffer buf = new StringBuffer();
                            readText(buf);
                            String text = buf.toString();
                            if (name.equals("title")) {
                                item[0] = text;
                            } else if (name.equals("link")) {
                                item[LINK] = text;
                            } else if (name.equals("description")) {
                                item[DESCRIPTION] = text;
                            } else if (name.equals("date")) {
                                item[DATE] = text;
                            } else if (name.equals("author")) {
                                item[AUTHOR] = text;
                            }
                        }
                    }
                    return item;
                }
            }
        }
        return null;
    }
}
