package org.apache.commons.net.nntp;

import com.hiti.jumpinfo.JumpInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Vector;
import org.apache.commons.net.MalformedServerReplyException;
import org.apache.commons.net.io.DotTerminatedMessageReader;
import org.apache.commons.net.io.DotTerminatedMessageWriter;
import org.apache.commons.net.io.Util;
import org.apache.commons.net.pop3.POP3;

public class NNTPClient extends NNTP {
    private void __parseArticlePointer(String reply, ArticleInfo pointer) throws MalformedServerReplyException {
        String[] tokens = reply.split(" ");
        if (tokens.length >= 3) {
            int i = 1 + 1;
            int i2;
            try {
                pointer.articleNumber = Long.parseLong(tokens[1]);
                i2 = i + 1;
                try {
                    pointer.articleId = tokens[i];
                    return;
                } catch (NumberFormatException e) {
                }
            } catch (NumberFormatException e2) {
                i2 = i;
            }
        }
        throw new MalformedServerReplyException("Could not parse article pointer.\nServer reply: " + reply);
    }

    private static void __parseGroupReply(String reply, NewsgroupInfo info) throws MalformedServerReplyException {
        String[] tokens = reply.split(" ");
        if (tokens.length >= 5) {
            int i = 1 + 1;
            int i2;
            try {
                info._setArticleCount(Long.parseLong(tokens[1]));
                i2 = i + 1;
                try {
                    info._setFirstArticle(Long.parseLong(tokens[i]));
                    i = i2 + 1;
                    info._setLastArticle(Long.parseLong(tokens[i2]));
                    i2 = i + 1;
                    info._setNewsgroup(tokens[i]);
                    info._setPostingPermission(0);
                    return;
                } catch (NumberFormatException e) {
                }
            } catch (NumberFormatException e2) {
                i2 = i;
            }
        }
        throw new MalformedServerReplyException("Could not parse newsgroup info.\nServer reply: " + reply);
    }

    static NewsgroupInfo __parseNewsgroupListEntry(String entry) {
        String[] tokens = entry.split(" ");
        if (tokens.length < 4) {
            return null;
        }
        NewsgroupInfo result = new NewsgroupInfo();
        int i = 0 + 1;
        result._setNewsgroup(tokens[0]);
        int i2 = i + 1;
        try {
            long lastNum = Long.parseLong(tokens[i]);
            i = i2 + 1;
            try {
                long firstNum = Long.parseLong(tokens[i2]);
                result._setFirstArticle(firstNum);
                result._setLastArticle(lastNum);
                if (firstNum == 0 && lastNum == 0) {
                    result._setArticleCount(0);
                } else {
                    result._setArticleCount((lastNum - firstNum) + 1);
                }
                i2 = i + 1;
                switch (tokens[i].charAt(0)) {
                    case JumpInfo.REQUEST_MODEL_TO_MAIN /*77*/:
                    case 'm':
                        result._setPostingPermission(1);
                        return result;
                    case JumpInfo.REQUEST_TO_INDEX_PRINT /*78*/:
                    case POP3.DEFAULT_PORT /*110*/:
                        result._setPostingPermission(3);
                        return result;
                    case 'Y':
                    case 'y':
                        result._setPostingPermission(2);
                        return result;
                    default:
                        result._setPostingPermission(0);
                        return result;
                }
            } catch (NumberFormatException e) {
                i2 = i;
                return null;
            }
        } catch (NumberFormatException e2) {
            return null;
        }
    }

    static Article __parseArticleEntry(String line) {
        Article article = new Article();
        article.setSubject(line);
        String[] parts = line.split("\t");
        if (parts.length > 6) {
            int i = 0 + 1;
            int i2;
            try {
                article.setArticleNumber(Long.parseLong(parts[0]));
                i2 = i + 1;
                try {
                    article.setSubject(parts[i]);
                    i = i2 + 1;
                    article.setFrom(parts[i2]);
                    i2 = i + 1;
                    article.setDate(parts[i]);
                    i = i2 + 1;
                    article.setArticleId(parts[i2]);
                    i2 = i + 1;
                    article.addReference(parts[i]);
                } catch (NumberFormatException e) {
                }
            } catch (NumberFormatException e2) {
                i2 = i;
            }
        }
        return article;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.apache.commons.net.nntp.NewsgroupInfo[] __readNewsgroupListing() throws java.io.IOException {
        /*
        r7 = this;
        r3 = new org.apache.commons.net.io.DotTerminatedMessageReader;
        r6 = r7._reader_;
        r3.<init>(r6);
        r2 = new java.util.Vector;
        r6 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
        r2.<init>(r6);
    L_0x000e:
        r1 = r3.readLine();	 Catch:{ all -> 0x001e }
        if (r1 == 0) goto L_0x0029;
    L_0x0014:
        r5 = __parseNewsgroupListEntry(r1);	 Catch:{ all -> 0x001e }
        if (r5 == 0) goto L_0x0023;
    L_0x001a:
        r2.addElement(r5);	 Catch:{ all -> 0x001e }
        goto L_0x000e;
    L_0x001e:
        r6 = move-exception;
        r3.close();
        throw r6;
    L_0x0023:
        r6 = new org.apache.commons.net.MalformedServerReplyException;	 Catch:{ all -> 0x001e }
        r6.<init>(r1);	 Catch:{ all -> 0x001e }
        throw r6;	 Catch:{ all -> 0x001e }
    L_0x0029:
        r3.close();
        r4 = r2.size();
        r6 = 1;
        if (r4 >= r6) goto L_0x0037;
    L_0x0033:
        r6 = 0;
        r0 = new org.apache.commons.net.nntp.NewsgroupInfo[r6];
    L_0x0036:
        return r0;
    L_0x0037:
        r0 = new org.apache.commons.net.nntp.NewsgroupInfo[r4];
        r2.copyInto(r0);
        goto L_0x0036;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.net.nntp.NNTPClient.__readNewsgroupListing():org.apache.commons.net.nntp.NewsgroupInfo[]");
    }

    private BufferedReader __retrieve(int command, String articleId, ArticleInfo pointer) throws IOException {
        if (articleId != null) {
            if (!NNTPReply.isPositiveCompletion(sendCommand(command, articleId))) {
                return null;
            }
        } else if (!NNTPReply.isPositiveCompletion(sendCommand(command))) {
            return null;
        }
        if (pointer != null) {
            __parseArticlePointer(getReplyString(), pointer);
        }
        return new DotTerminatedMessageReader(this._reader_);
    }

    private BufferedReader __retrieve(int command, long articleNumber, ArticleInfo pointer) throws IOException {
        if (!NNTPReply.isPositiveCompletion(sendCommand(command, Long.toString(articleNumber)))) {
            return null;
        }
        if (pointer != null) {
            __parseArticlePointer(getReplyString(), pointer);
        }
        return new DotTerminatedMessageReader(this._reader_);
    }

    public BufferedReader retrieveArticle(String articleId, ArticleInfo pointer) throws IOException {
        return __retrieve(0, articleId, pointer);
    }

    public Reader retrieveArticle(String articleId) throws IOException {
        return retrieveArticle(articleId, (ArticleInfo) null);
    }

    public Reader retrieveArticle() throws IOException {
        return retrieveArticle((String) null);
    }

    public BufferedReader retrieveArticle(long articleNumber, ArticleInfo pointer) throws IOException {
        return __retrieve(0, articleNumber, pointer);
    }

    public BufferedReader retrieveArticle(long articleNumber) throws IOException {
        return retrieveArticle(articleNumber, null);
    }

    public BufferedReader retrieveArticleHeader(String articleId, ArticleInfo pointer) throws IOException {
        return __retrieve(3, articleId, pointer);
    }

    public Reader retrieveArticleHeader(String articleId) throws IOException {
        return retrieveArticleHeader(articleId, (ArticleInfo) null);
    }

    public Reader retrieveArticleHeader() throws IOException {
        return retrieveArticleHeader((String) null);
    }

    public BufferedReader retrieveArticleHeader(long articleNumber, ArticleInfo pointer) throws IOException {
        return __retrieve(3, articleNumber, pointer);
    }

    public BufferedReader retrieveArticleHeader(long articleNumber) throws IOException {
        return retrieveArticleHeader(articleNumber, null);
    }

    public BufferedReader retrieveArticleBody(String articleId, ArticleInfo pointer) throws IOException {
        return __retrieve(1, articleId, pointer);
    }

    public Reader retrieveArticleBody(String articleId) throws IOException {
        return retrieveArticleBody(articleId, (ArticleInfo) null);
    }

    public Reader retrieveArticleBody() throws IOException {
        return retrieveArticleBody(null);
    }

    public BufferedReader retrieveArticleBody(long articleNumber, ArticleInfo pointer) throws IOException {
        return __retrieve(1, articleNumber, pointer);
    }

    public BufferedReader retrieveArticleBody(long articleNumber) throws IOException {
        return retrieveArticleBody(articleNumber, null);
    }

    public boolean selectNewsgroup(String newsgroup, NewsgroupInfo info) throws IOException {
        if (!NNTPReply.isPositiveCompletion(group(newsgroup))) {
            return false;
        }
        if (info != null) {
            __parseGroupReply(getReplyString(), info);
        }
        return true;
    }

    public boolean selectNewsgroup(String newsgroup) throws IOException {
        return selectNewsgroup(newsgroup, null);
    }

    public String listHelp() throws IOException {
        if (!NNTPReply.isInformational(help())) {
            return null;
        }
        StringWriter help = new StringWriter();
        BufferedReader reader = new DotTerminatedMessageReader(this._reader_);
        Util.copyReader(reader, help);
        reader.close();
        help.close();
        return help.toString();
    }

    public String[] listOverviewFmt() throws IOException {
        if (!NNTPReply.isPositiveCompletion(sendCommand("LIST", "OVERVIEW.FMT"))) {
            return null;
        }
        BufferedReader reader = new DotTerminatedMessageReader(this._reader_);
        ArrayList<String> list = new ArrayList();
        while (true) {
            String line = reader.readLine();
            if (line != null) {
                list.add(line);
            } else {
                reader.close();
                return (String[]) list.toArray(new String[list.size()]);
            }
        }
    }

    public boolean selectArticle(String articleId, ArticleInfo pointer) throws IOException {
        if (articleId != null) {
            if (!NNTPReply.isPositiveCompletion(stat(articleId))) {
                return false;
            }
        } else if (!NNTPReply.isPositiveCompletion(stat())) {
            return false;
        }
        if (pointer != null) {
            __parseArticlePointer(getReplyString(), pointer);
        }
        return true;
    }

    public boolean selectArticle(String articleId) throws IOException {
        return selectArticle(articleId, (ArticleInfo) null);
    }

    public boolean selectArticle(ArticleInfo pointer) throws IOException {
        return selectArticle(null, pointer);
    }

    public boolean selectArticle(long articleNumber, ArticleInfo pointer) throws IOException {
        if (!NNTPReply.isPositiveCompletion(stat(articleNumber))) {
            return false;
        }
        if (pointer != null) {
            __parseArticlePointer(getReplyString(), pointer);
        }
        return true;
    }

    public boolean selectArticle(long articleNumber) throws IOException {
        return selectArticle(articleNumber, null);
    }

    public boolean selectPreviousArticle(ArticleInfo pointer) throws IOException {
        if (!NNTPReply.isPositiveCompletion(last())) {
            return false;
        }
        if (pointer != null) {
            __parseArticlePointer(getReplyString(), pointer);
        }
        return true;
    }

    public boolean selectPreviousArticle() throws IOException {
        return selectPreviousArticle((ArticleInfo) null);
    }

    public boolean selectNextArticle(ArticleInfo pointer) throws IOException {
        if (!NNTPReply.isPositiveCompletion(next())) {
            return false;
        }
        if (pointer != null) {
            __parseArticlePointer(getReplyString(), pointer);
        }
        return true;
    }

    public boolean selectNextArticle() throws IOException {
        return selectNextArticle((ArticleInfo) null);
    }

    public NewsgroupInfo[] listNewsgroups() throws IOException {
        if (NNTPReply.isPositiveCompletion(list())) {
            return __readNewsgroupListing();
        }
        return null;
    }

    public Iterable<String> iterateNewsgroupListing() throws IOException {
        if (NNTPReply.isPositiveCompletion(list())) {
            return new ReplyIterator(this._reader_);
        }
        throw new IOException("LIST command failed: " + getReplyString());
    }

    public Iterable<NewsgroupInfo> iterateNewsgroups() throws IOException {
        return new NewsgroupIterator(iterateNewsgroupListing());
    }

    public NewsgroupInfo[] listNewsgroups(String wildmat) throws IOException {
        if (NNTPReply.isPositiveCompletion(listActive(wildmat))) {
            return __readNewsgroupListing();
        }
        return null;
    }

    public Iterable<String> iterateNewsgroupListing(String wildmat) throws IOException {
        if (NNTPReply.isPositiveCompletion(listActive(wildmat))) {
            return new ReplyIterator(this._reader_);
        }
        throw new IOException("LIST ACTIVE " + wildmat + " command failed: " + getReplyString());
    }

    public Iterable<NewsgroupInfo> iterateNewsgroups(String wildmat) throws IOException {
        return new NewsgroupIterator(iterateNewsgroupListing(wildmat));
    }

    public NewsgroupInfo[] listNewNewsgroups(NewGroupsOrNewsQuery query) throws IOException {
        if (NNTPReply.isPositiveCompletion(newgroups(query.getDate(), query.getTime(), query.isGMT(), query.getDistributions()))) {
            return __readNewsgroupListing();
        }
        return null;
    }

    public Iterable<String> iterateNewNewsgroupListing(NewGroupsOrNewsQuery query) throws IOException {
        if (NNTPReply.isPositiveCompletion(newgroups(query.getDate(), query.getTime(), query.isGMT(), query.getDistributions()))) {
            return new ReplyIterator(this._reader_);
        }
        throw new IOException("NEWGROUPS command failed: " + getReplyString());
    }

    public Iterable<NewsgroupInfo> iterateNewNewsgroups(NewGroupsOrNewsQuery query) throws IOException {
        return new NewsgroupIterator(iterateNewNewsgroupListing(query));
    }

    public String[] listNewNews(NewGroupsOrNewsQuery query) throws IOException {
        if (!NNTPReply.isPositiveCompletion(newnews(query.getNewsgroups(), query.getDate(), query.getTime(), query.isGMT(), query.getDistributions()))) {
            return null;
        }
        Vector<String> list = new Vector();
        BufferedReader reader = new DotTerminatedMessageReader(this._reader_);
        while (true) {
            try {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                list.addElement(line);
            } finally {
                reader.close();
            }
        }
        int size = list.size();
        if (size < 1) {
            return new String[0];
        }
        String[] result = new String[size];
        list.copyInto(result);
        return result;
    }

    public Iterable<String> iterateNewNews(NewGroupsOrNewsQuery query) throws IOException {
        if (NNTPReply.isPositiveCompletion(newnews(query.getNewsgroups(), query.getDate(), query.getTime(), query.isGMT(), query.getDistributions()))) {
            return new ReplyIterator(this._reader_);
        }
        throw new IOException("NEWNEWS command failed: " + getReplyString());
    }

    public boolean completePendingCommand() throws IOException {
        return NNTPReply.isPositiveCompletion(getReply());
    }

    public Writer postArticle() throws IOException {
        if (NNTPReply.isPositiveIntermediate(post())) {
            return new DotTerminatedMessageWriter(this._writer_);
        }
        return null;
    }

    public Writer forwardArticle(String articleId) throws IOException {
        if (NNTPReply.isPositiveIntermediate(ihave(articleId))) {
            return new DotTerminatedMessageWriter(this._writer_);
        }
        return null;
    }

    public boolean logout() throws IOException {
        return NNTPReply.isPositiveCompletion(quit());
    }

    public boolean authenticate(String username, String password) throws IOException {
        if (authinfoUser(username) != NNTPReply.MORE_AUTH_INFO_REQUIRED || authinfoPass(password) != NNTPReply.AUTHENTICATION_ACCEPTED) {
            return false;
        }
        this._isAllowedToPost = true;
        return true;
    }

    private BufferedReader __retrieveArticleInfo(String articleRange) throws IOException {
        if (NNTPReply.isPositiveCompletion(xover(articleRange))) {
            return new DotTerminatedMessageReader(this._reader_);
        }
        return null;
    }

    public BufferedReader retrieveArticleInfo(long articleNumber) throws IOException {
        return __retrieveArticleInfo(Long.toString(articleNumber));
    }

    public BufferedReader retrieveArticleInfo(long lowArticleNumber, long highArticleNumber) throws IOException {
        return __retrieveArticleInfo(lowArticleNumber + "-" + highArticleNumber);
    }

    public Iterable<Article> iterateArticleInfo(long lowArticleNumber, long highArticleNumber) throws IOException {
        BufferedReader info = retrieveArticleInfo(lowArticleNumber, highArticleNumber);
        if (info != null) {
            return new ArticleIterator(new ReplyIterator(info, false));
        }
        throw new IOException("XOVER command failed: " + getReplyString());
    }

    private BufferedReader __retrieveHeader(String header, String articleRange) throws IOException {
        if (NNTPReply.isPositiveCompletion(xhdr(header, articleRange))) {
            return new DotTerminatedMessageReader(this._reader_);
        }
        return null;
    }

    public BufferedReader retrieveHeader(String header, long articleNumber) throws IOException {
        return __retrieveHeader(header, Long.toString(articleNumber));
    }

    public BufferedReader retrieveHeader(String header, long lowArticleNumber, long highArticleNumber) throws IOException {
        return __retrieveHeader(header, lowArticleNumber + "-" + highArticleNumber);
    }

    @Deprecated
    public Reader retrieveHeader(String s, int l, int h) throws IOException {
        return retrieveHeader(s, (long) l, (long) h);
    }

    @Deprecated
    public Reader retrieveArticleInfo(int a, int b) throws IOException {
        return retrieveArticleInfo((long) a, (long) b);
    }

    @Deprecated
    public Reader retrieveHeader(String a, int b) throws IOException {
        return retrieveHeader(a, (long) b);
    }

    @Deprecated
    public boolean selectArticle(int a, ArticlePointer ap) throws IOException {
        ArticleInfo ai = __ap2ai(ap);
        boolean b = selectArticle((long) a, ai);
        __ai2ap(ai, ap);
        return b;
    }

    @Deprecated
    public Reader retrieveArticleInfo(int a) throws IOException {
        return retrieveArticleInfo((long) a);
    }

    @Deprecated
    public boolean selectArticle(int a) throws IOException {
        return selectArticle((long) a);
    }

    @Deprecated
    public Reader retrieveArticleHeader(int a) throws IOException {
        return retrieveArticleHeader((long) a);
    }

    @Deprecated
    public Reader retrieveArticleHeader(int a, ArticlePointer ap) throws IOException {
        ArticleInfo ai = __ap2ai(ap);
        Reader rdr = retrieveArticleHeader((long) a, ai);
        __ai2ap(ai, ap);
        return rdr;
    }

    @Deprecated
    public Reader retrieveArticleBody(int a) throws IOException {
        return retrieveArticleBody((long) a);
    }

    @Deprecated
    public Reader retrieveArticle(int a, ArticlePointer ap) throws IOException {
        ArticleInfo ai = __ap2ai(ap);
        Reader rdr = retrieveArticle((long) a, ai);
        __ai2ap(ai, ap);
        return rdr;
    }

    @Deprecated
    public Reader retrieveArticle(int a) throws IOException {
        return retrieveArticle((long) a);
    }

    @Deprecated
    public Reader retrieveArticleBody(int a, ArticlePointer ap) throws IOException {
        ArticleInfo ai = __ap2ai(ap);
        Reader rdr = retrieveArticleBody((long) a, ai);
        __ai2ap(ai, ap);
        return rdr;
    }

    @Deprecated
    public Reader retrieveArticle(String a, ArticlePointer ap) throws IOException {
        ArticleInfo ai = __ap2ai(ap);
        Reader rdr = retrieveArticle(a, ai);
        __ai2ap(ai, ap);
        return rdr;
    }

    @Deprecated
    public Reader retrieveArticleBody(String a, ArticlePointer ap) throws IOException {
        ArticleInfo ai = __ap2ai(ap);
        Reader rdr = retrieveArticleBody(a, ai);
        __ai2ap(ai, ap);
        return rdr;
    }

    @Deprecated
    public Reader retrieveArticleHeader(String a, ArticlePointer ap) throws IOException {
        ArticleInfo ai = __ap2ai(ap);
        Reader rdr = retrieveArticleHeader(a, ai);
        __ai2ap(ai, ap);
        return rdr;
    }

    @Deprecated
    public boolean selectArticle(String a, ArticlePointer ap) throws IOException {
        ArticleInfo ai = __ap2ai(ap);
        boolean b = selectArticle(a, ai);
        __ai2ap(ai, ap);
        return b;
    }

    @Deprecated
    public boolean selectArticle(ArticlePointer ap) throws IOException {
        ArticleInfo ai = __ap2ai(ap);
        boolean b = selectArticle(ai);
        __ai2ap(ai, ap);
        return b;
    }

    @Deprecated
    public boolean selectNextArticle(ArticlePointer ap) throws IOException {
        ArticleInfo ai = __ap2ai(ap);
        boolean b = selectNextArticle(ai);
        __ai2ap(ai, ap);
        return b;
    }

    @Deprecated
    public boolean selectPreviousArticle(ArticlePointer ap) throws IOException {
        ArticleInfo ai = __ap2ai(ap);
        boolean b = selectPreviousArticle(ai);
        __ai2ap(ai, ap);
        return b;
    }

    private ArticleInfo __ap2ai(ArticlePointer ap) {
        if (ap == null) {
            return null;
        }
        return new ArticleInfo();
    }

    private void __ai2ap(ArticleInfo ai, ArticlePointer ap) {
        if (ap != null) {
            ap.articleId = ai.articleId;
            ap.articleNumber = (int) ai.articleNumber;
        }
    }
}
