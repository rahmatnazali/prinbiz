package org.apache.commons.net.telnet;

import android.support.v4.media.TransportMediator;
import com.hiti.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.imap.IMAP;
import org.apache.commons.net.nntp.NNTP;
import org.apache.commons.net.nntp.NNTPReply;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.pop3.POP3;
import org.apache.commons.net.smtp.SMTPReply;
import org.ksoap2.SoapEnvelope;
import org.kxml2.wap.Wbxml;
import org.xmlpull.v1.XmlPullParser;

public class TelnetOption {
    public static final int APPROXIMATE_MESSAGE_SIZE = 4;
    public static final int AUTHENTICATION = 37;
    public static final int BINARY = 0;
    public static final int BYTE_MACRO = 19;
    public static final int DATA_ENTRY_TERMINAL = 20;
    public static final int ECHO = 1;
    public static final int ENCRYPTION = 38;
    public static final int END_OF_RECORD = 25;
    public static final int EXTENDED_ASCII = 17;
    public static final int EXTENDED_OPTIONS_LIST = 255;
    public static final int FORCE_LOGOUT = 18;
    public static final int LINEMODE = 34;
    public static final int MAX_OPTION_VALUE = 255;
    public static final int NEGOTIATE_CARRIAGE_RETURN = 10;
    public static final int NEGOTIATE_FORMFEED = 13;
    public static final int NEGOTIATE_HORIZONTAL_TAB = 12;
    public static final int NEGOTIATE_HORIZONTAL_TAB_STOP = 11;
    public static final int NEGOTIATE_LINEFEED = 16;
    public static final int NEGOTIATE_OUTPUT_LINE_WIDTH = 8;
    public static final int NEGOTIATE_OUTPUT_PAGE_SIZE = 9;
    public static final int NEGOTIATE_VERTICAL_TAB = 15;
    public static final int NEGOTIATE_VERTICAL_TAB_STOP = 14;
    public static final int NEW_ENVIRONMENT_VARIABLES = 39;
    public static final int OLD_ENVIRONMENT_VARIABLES = 36;
    public static final int OUTPUT_MARKING = 27;
    public static final int PREPARE_TO_RECONNECT = 2;
    public static final int REGIME_3270 = 29;
    public static final int REMOTE_CONTROLLED_TRANSMISSION = 7;
    public static final int REMOTE_FLOW_CONTROL = 33;
    public static final int SEND_LOCATION = 23;
    public static final int STATUS = 5;
    public static final int SUPDUP = 21;
    public static final int SUPDUP_OUTPUT = 22;
    public static final int SUPPRESS_GO_AHEAD = 3;
    public static final int TACACS_USER_IDENTIFICATION = 26;
    public static final int TERMINAL_LOCATION_NUMBER = 28;
    public static final int TERMINAL_SPEED = 32;
    public static final int TERMINAL_TYPE = 24;
    public static final int TIMING_MARK = 6;
    public static final int WINDOW_SIZE = 31;
    public static final int X3_PAD = 30;
    public static final int X_DISPLAY_LOCATION = 35;
    private static final int __FIRST_OPTION = 0;
    private static final int __LAST_OPTION = 255;
    private static final String[] __optionString;

    static {
        String[] strArr = new String[DNSConstants.FLAGS_RD];
        strArr[__FIRST_OPTION] = "BINARY";
        strArr[ECHO] = "ECHO";
        strArr[PREPARE_TO_RECONNECT] = "RCP";
        strArr[SUPPRESS_GO_AHEAD] = "SUPPRESS GO AHEAD";
        strArr[APPROXIMATE_MESSAGE_SIZE] = "NAME";
        strArr[STATUS] = "STATUS";
        strArr[TIMING_MARK] = "TIMING MARK";
        strArr[REMOTE_CONTROLLED_TRANSMISSION] = "RCTE";
        strArr[NEGOTIATE_OUTPUT_LINE_WIDTH] = "NAOL";
        strArr[NEGOTIATE_OUTPUT_PAGE_SIZE] = "NAOP";
        strArr[NEGOTIATE_CARRIAGE_RETURN] = "NAOCRD";
        strArr[NEGOTIATE_HORIZONTAL_TAB_STOP] = "NAOHTS";
        strArr[NEGOTIATE_HORIZONTAL_TAB] = "NAOHTD";
        strArr[NEGOTIATE_FORMFEED] = "NAOFFD";
        strArr[NEGOTIATE_VERTICAL_TAB_STOP] = "NAOVTS";
        strArr[NEGOTIATE_VERTICAL_TAB] = "NAOVTD";
        strArr[NEGOTIATE_LINEFEED] = "NAOLFD";
        strArr[EXTENDED_ASCII] = "EXTEND ASCII";
        strArr[FORCE_LOGOUT] = "LOGOUT";
        strArr[BYTE_MACRO] = "BYTE MACRO";
        strArr[DATA_ENTRY_TERMINAL] = "DATA ENTRY TERMINAL";
        strArr[SUPDUP] = "SUPDUP";
        strArr[SUPDUP_OUTPUT] = "SUPDUP OUTPUT";
        strArr[SEND_LOCATION] = "SEND LOCATION";
        strArr[TERMINAL_TYPE] = "TERMINAL TYPE";
        strArr[END_OF_RECORD] = "END OF RECORD";
        strArr[TACACS_USER_IDENTIFICATION] = "TACACS UID";
        strArr[OUTPUT_MARKING] = "OUTPUT MARKING";
        strArr[TERMINAL_LOCATION_NUMBER] = "TTYLOC";
        strArr[REGIME_3270] = "3270 REGIME";
        strArr[X3_PAD] = "X.3 PAD";
        strArr[WINDOW_SIZE] = "NAWS";
        strArr[TERMINAL_SPEED] = "TSPEED";
        strArr[REMOTE_FLOW_CONTROL] = "LFLOW";
        strArr[LINEMODE] = "LINEMODE";
        strArr[X_DISPLAY_LOCATION] = "XDISPLOC";
        strArr[OLD_ENVIRONMENT_VARIABLES] = "OLD-ENVIRON";
        strArr[AUTHENTICATION] = "AUTHENTICATION";
        strArr[ENCRYPTION] = "ENCRYPT";
        strArr[NEW_ENVIRONMENT_VARIABLES] = "NEW-ENVIRON";
        strArr[40] = "TN3270E";
        strArr[41] = "XAUTH";
        strArr[42] = "CHARSET";
        strArr[43] = "RSP";
        strArr[44] = "Com Port Control";
        strArr[45] = "Suppress Local Echo";
        strArr[46] = "Start TLS";
        strArr[47] = "KERMIT";
        strArr[48] = "SEND-URL";
        strArr[49] = "FORWARD_X";
        strArr[50] = XmlPullParser.NO_NAMESPACE;
        strArr[51] = XmlPullParser.NO_NAMESPACE;
        strArr[52] = XmlPullParser.NO_NAMESPACE;
        strArr[53] = XmlPullParser.NO_NAMESPACE;
        strArr[54] = XmlPullParser.NO_NAMESPACE;
        strArr[55] = XmlPullParser.NO_NAMESPACE;
        strArr[56] = XmlPullParser.NO_NAMESPACE;
        strArr[57] = XmlPullParser.NO_NAMESPACE;
        strArr[58] = XmlPullParser.NO_NAMESPACE;
        strArr[59] = XmlPullParser.NO_NAMESPACE;
        strArr[60] = XmlPullParser.NO_NAMESPACE;
        strArr[61] = XmlPullParser.NO_NAMESPACE;
        strArr[62] = XmlPullParser.NO_NAMESPACE;
        strArr[63] = XmlPullParser.NO_NAMESPACE;
        strArr[64] = XmlPullParser.NO_NAMESPACE;
        strArr[65] = XmlPullParser.NO_NAMESPACE;
        strArr[66] = XmlPullParser.NO_NAMESPACE;
        strArr[67] = XmlPullParser.NO_NAMESPACE;
        strArr[68] = XmlPullParser.NO_NAMESPACE;
        strArr[69] = XmlPullParser.NO_NAMESPACE;
        strArr[70] = XmlPullParser.NO_NAMESPACE;
        strArr[71] = XmlPullParser.NO_NAMESPACE;
        strArr[72] = XmlPullParser.NO_NAMESPACE;
        strArr[73] = XmlPullParser.NO_NAMESPACE;
        strArr[74] = XmlPullParser.NO_NAMESPACE;
        strArr[75] = XmlPullParser.NO_NAMESPACE;
        strArr[76] = XmlPullParser.NO_NAMESPACE;
        strArr[77] = XmlPullParser.NO_NAMESPACE;
        strArr[78] = XmlPullParser.NO_NAMESPACE;
        strArr[79] = XmlPullParser.NO_NAMESPACE;
        strArr[80] = XmlPullParser.NO_NAMESPACE;
        strArr[81] = XmlPullParser.NO_NAMESPACE;
        strArr[82] = XmlPullParser.NO_NAMESPACE;
        strArr[83] = XmlPullParser.NO_NAMESPACE;
        strArr[84] = XmlPullParser.NO_NAMESPACE;
        strArr[85] = XmlPullParser.NO_NAMESPACE;
        strArr[86] = XmlPullParser.NO_NAMESPACE;
        strArr[87] = XmlPullParser.NO_NAMESPACE;
        strArr[88] = XmlPullParser.NO_NAMESPACE;
        strArr[89] = XmlPullParser.NO_NAMESPACE;
        strArr[90] = XmlPullParser.NO_NAMESPACE;
        strArr[91] = XmlPullParser.NO_NAMESPACE;
        strArr[92] = XmlPullParser.NO_NAMESPACE;
        strArr[93] = XmlPullParser.NO_NAMESPACE;
        strArr[94] = XmlPullParser.NO_NAMESPACE;
        strArr[95] = XmlPullParser.NO_NAMESPACE;
        strArr[96] = XmlPullParser.NO_NAMESPACE;
        strArr[97] = XmlPullParser.NO_NAMESPACE;
        strArr[98] = XmlPullParser.NO_NAMESPACE;
        strArr[99] = XmlPullParser.NO_NAMESPACE;
        strArr[100] = XmlPullParser.NO_NAMESPACE;
        strArr[ControllerState.PLAY_PHOTO] = XmlPullParser.NO_NAMESPACE;
        strArr[ControllerState.NO_PLAY_ITEM] = XmlPullParser.NO_NAMESPACE;
        strArr[ControllerState.PLAY_COUNT_STATE] = XmlPullParser.NO_NAMESPACE;
        strArr[104] = XmlPullParser.NO_NAMESPACE;
        strArr[105] = XmlPullParser.NO_NAMESPACE;
        strArr[106] = XmlPullParser.NO_NAMESPACE;
        strArr[107] = XmlPullParser.NO_NAMESPACE;
        strArr[108] = XmlPullParser.NO_NAMESPACE;
        strArr[109] = XmlPullParser.NO_NAMESPACE;
        strArr[POP3.DEFAULT_PORT] = XmlPullParser.NO_NAMESPACE;
        strArr[111] = XmlPullParser.NO_NAMESPACE;
        strArr[112] = XmlPullParser.NO_NAMESPACE;
        strArr[113] = XmlPullParser.NO_NAMESPACE;
        strArr[114] = XmlPullParser.NO_NAMESPACE;
        strArr[DNSConstants.RESPONSE_MAX_WAIT_INTERVAL] = XmlPullParser.NO_NAMESPACE;
        strArr[116] = XmlPullParser.NO_NAMESPACE;
        strArr[117] = XmlPullParser.NO_NAMESPACE;
        strArr[118] = XmlPullParser.NO_NAMESPACE;
        strArr[NNTP.DEFAULT_PORT] = XmlPullParser.NO_NAMESPACE;
        strArr[SoapEnvelope.VER12] = XmlPullParser.NO_NAMESPACE;
        strArr[121] = XmlPullParser.NO_NAMESPACE;
        strArr[122] = XmlPullParser.NO_NAMESPACE;
        strArr[NTPUDPClient.DEFAULT_PORT] = XmlPullParser.NO_NAMESPACE;
        strArr[124] = XmlPullParser.NO_NAMESPACE;
        strArr[FTPReply.DATA_CONNECTION_ALREADY_OPEN] = XmlPullParser.NO_NAMESPACE;
        strArr[TransportMediator.KEYCODE_MEDIA_PLAY] = XmlPullParser.NO_NAMESPACE;
        strArr[TransportMediator.KEYCODE_MEDIA_PAUSE] = XmlPullParser.NO_NAMESPACE;
        strArr[TransportMediator.FLAG_KEY_MEDIA_NEXT] = XmlPullParser.NO_NAMESPACE;
        strArr[Wbxml.EXT_T_1] = XmlPullParser.NO_NAMESPACE;
        strArr[TransportMediator.KEYCODE_MEDIA_RECORD] = XmlPullParser.NO_NAMESPACE;
        strArr[Wbxml.STR_T] = XmlPullParser.NO_NAMESPACE;
        strArr[Wbxml.LITERAL_A] = XmlPullParser.NO_NAMESPACE;
        strArr[133] = XmlPullParser.NO_NAMESPACE;
        strArr[134] = XmlPullParser.NO_NAMESPACE;
        strArr[135] = XmlPullParser.NO_NAMESPACE;
        strArr[136] = XmlPullParser.NO_NAMESPACE;
        strArr[137] = XmlPullParser.NO_NAMESPACE;
        strArr[138] = "TELOPT PRAGMA LOGON";
        strArr[139] = "TELOPT SSPI LOGON";
        strArr[140] = "TELOPT PRAGMA HEARTBEAT";
        strArr[141] = XmlPullParser.NO_NAMESPACE;
        strArr[142] = XmlPullParser.NO_NAMESPACE;
        strArr[IMAP.DEFAULT_PORT] = XmlPullParser.NO_NAMESPACE;
        strArr[144] = XmlPullParser.NO_NAMESPACE;
        strArr[145] = XmlPullParser.NO_NAMESPACE;
        strArr[146] = XmlPullParser.NO_NAMESPACE;
        strArr[147] = XmlPullParser.NO_NAMESPACE;
        strArr[148] = XmlPullParser.NO_NAMESPACE;
        strArr[149] = XmlPullParser.NO_NAMESPACE;
        strArr[FTPReply.FILE_STATUS_OK] = XmlPullParser.NO_NAMESPACE;
        strArr[151] = XmlPullParser.NO_NAMESPACE;
        strArr[152] = XmlPullParser.NO_NAMESPACE;
        strArr[153] = XmlPullParser.NO_NAMESPACE;
        strArr[154] = XmlPullParser.NO_NAMESPACE;
        strArr[155] = XmlPullParser.NO_NAMESPACE;
        strArr[156] = XmlPullParser.NO_NAMESPACE;
        strArr[157] = XmlPullParser.NO_NAMESPACE;
        strArr[158] = XmlPullParser.NO_NAMESPACE;
        strArr[159] = XmlPullParser.NO_NAMESPACE;
        strArr[160] = XmlPullParser.NO_NAMESPACE;
        strArr[161] = XmlPullParser.NO_NAMESPACE;
        strArr[162] = XmlPullParser.NO_NAMESPACE;
        strArr[163] = XmlPullParser.NO_NAMESPACE;
        strArr[164] = XmlPullParser.NO_NAMESPACE;
        strArr[165] = XmlPullParser.NO_NAMESPACE;
        strArr[166] = XmlPullParser.NO_NAMESPACE;
        strArr[167] = XmlPullParser.NO_NAMESPACE;
        strArr[168] = XmlPullParser.NO_NAMESPACE;
        strArr[169] = XmlPullParser.NO_NAMESPACE;
        strArr[170] = XmlPullParser.NO_NAMESPACE;
        strArr[171] = XmlPullParser.NO_NAMESPACE;
        strArr[172] = XmlPullParser.NO_NAMESPACE;
        strArr[173] = XmlPullParser.NO_NAMESPACE;
        strArr[174] = XmlPullParser.NO_NAMESPACE;
        strArr[175] = XmlPullParser.NO_NAMESPACE;
        strArr[176] = XmlPullParser.NO_NAMESPACE;
        strArr[177] = XmlPullParser.NO_NAMESPACE;
        strArr[178] = XmlPullParser.NO_NAMESPACE;
        strArr[179] = XmlPullParser.NO_NAMESPACE;
        strArr[180] = XmlPullParser.NO_NAMESPACE;
        strArr[181] = XmlPullParser.NO_NAMESPACE;
        strArr[182] = XmlPullParser.NO_NAMESPACE;
        strArr[183] = XmlPullParser.NO_NAMESPACE;
        strArr[184] = XmlPullParser.NO_NAMESPACE;
        strArr[185] = XmlPullParser.NO_NAMESPACE;
        strArr[186] = XmlPullParser.NO_NAMESPACE;
        strArr[187] = XmlPullParser.NO_NAMESPACE;
        strArr[188] = XmlPullParser.NO_NAMESPACE;
        strArr[189] = XmlPullParser.NO_NAMESPACE;
        strArr[190] = XmlPullParser.NO_NAMESPACE;
        strArr[191] = XmlPullParser.NO_NAMESPACE;
        strArr[Wbxml.EXT_0] = XmlPullParser.NO_NAMESPACE;
        strArr[Wbxml.EXT_1] = XmlPullParser.NO_NAMESPACE;
        strArr[Wbxml.EXT_2] = XmlPullParser.NO_NAMESPACE;
        strArr[Wbxml.OPAQUE] = XmlPullParser.NO_NAMESPACE;
        strArr[Wbxml.LITERAL_AC] = XmlPullParser.NO_NAMESPACE;
        strArr[197] = XmlPullParser.NO_NAMESPACE;
        strArr[198] = XmlPullParser.NO_NAMESPACE;
        strArr[NNTPReply.DEBUG_OUTPUT] = XmlPullParser.NO_NAMESPACE;
        strArr[NNTPReply.SERVER_READY_POSTING_ALLOWED] = XmlPullParser.NO_NAMESPACE;
        strArr[NNTPReply.SERVER_READY_POSTING_NOT_ALLOWED] = XmlPullParser.NO_NAMESPACE;
        strArr[NNTPReply.SLAVE_STATUS_NOTED] = XmlPullParser.NO_NAMESPACE;
        strArr[203] = XmlPullParser.NO_NAMESPACE;
        strArr[204] = XmlPullParser.NO_NAMESPACE;
        strArr[NNTPReply.CLOSING_CONNECTION] = XmlPullParser.NO_NAMESPACE;
        strArr[206] = XmlPullParser.NO_NAMESPACE;
        strArr[207] = XmlPullParser.NO_NAMESPACE;
        strArr[208] = XmlPullParser.NO_NAMESPACE;
        strArr[209] = XmlPullParser.NO_NAMESPACE;
        strArr[210] = XmlPullParser.NO_NAMESPACE;
        strArr[SMTPReply.SYSTEM_STATUS] = XmlPullParser.NO_NAMESPACE;
        strArr[FTPReply.DIRECTORY_STATUS] = XmlPullParser.NO_NAMESPACE;
        strArr[FTPReply.FILE_STATUS] = XmlPullParser.NO_NAMESPACE;
        strArr[SMTPReply.HELP_MESSAGE] = XmlPullParser.NO_NAMESPACE;
        strArr[FTPReply.NAME_SYSTEM_TYPE] = XmlPullParser.NO_NAMESPACE;
        strArr[216] = XmlPullParser.NO_NAMESPACE;
        strArr[217] = XmlPullParser.NO_NAMESPACE;
        strArr[218] = XmlPullParser.NO_NAMESPACE;
        strArr[219] = XmlPullParser.NO_NAMESPACE;
        strArr[SMTPReply.SERVICE_READY] = XmlPullParser.NO_NAMESPACE;
        strArr[SMTPReply.SERVICE_CLOSING_TRANSMISSION_CHANNEL] = XmlPullParser.NO_NAMESPACE;
        strArr[NNTPReply.ARTICLE_RETRIEVED_BODY_FOLLOWS] = XmlPullParser.NO_NAMESPACE;
        strArr[NNTPReply.ARTICLE_RETRIEVED_REQUEST_TEXT_SEPARATELY] = XmlPullParser.NO_NAMESPACE;
        strArr[224] = XmlPullParser.NO_NAMESPACE;
        strArr[FTPReply.DATA_CONNECTION_OPEN] = XmlPullParser.NO_NAMESPACE;
        strArr[FTPReply.CLOSING_DATA_CONNECTION] = XmlPullParser.NO_NAMESPACE;
        strArr[FTPReply.ENTERING_PASSIVE_MODE] = XmlPullParser.NO_NAMESPACE;
        strArr[228] = XmlPullParser.NO_NAMESPACE;
        strArr[FTPReply.ENTERING_EPSV_MODE] = XmlPullParser.NO_NAMESPACE;
        strArr[NNTPReply.ARTICLE_LIST_BY_MESSAGE_ID_FOLLOWS] = XmlPullParser.NO_NAMESPACE;
        strArr[NNTPReply.NEW_NEWSGROUP_LIST_FOLLOWS] = XmlPullParser.NO_NAMESPACE;
        strArr[232] = XmlPullParser.NO_NAMESPACE;
        strArr[233] = XmlPullParser.NO_NAMESPACE;
        strArr[FTPReply.SECURITY_DATA_EXCHANGE_COMPLETE] = XmlPullParser.NO_NAMESPACE;
        strArr[NNTPReply.ARTICLE_TRANSFERRED_OK] = XmlPullParser.NO_NAMESPACE;
        strArr[TelnetCommand.EOF] = XmlPullParser.NO_NAMESPACE;
        strArr[TelnetCommand.SUSP] = XmlPullParser.NO_NAMESPACE;
        strArr[TelnetCommand.ABORT] = XmlPullParser.NO_NAMESPACE;
        strArr[TelnetCommand.EOR] = XmlPullParser.NO_NAMESPACE;
        strArr[TelnetCommand.SE] = XmlPullParser.NO_NAMESPACE;
        strArr[TelnetCommand.NOP] = XmlPullParser.NO_NAMESPACE;
        strArr[TelnetCommand.SYNCH] = XmlPullParser.NO_NAMESPACE;
        strArr[TelnetCommand.BREAK] = XmlPullParser.NO_NAMESPACE;
        strArr[TelnetCommand.IP] = XmlPullParser.NO_NAMESPACE;
        strArr[TelnetCommand.AO] = XmlPullParser.NO_NAMESPACE;
        strArr[TelnetCommand.AYT] = XmlPullParser.NO_NAMESPACE;
        strArr[TelnetCommand.EC] = XmlPullParser.NO_NAMESPACE;
        strArr[TelnetCommand.EL] = XmlPullParser.NO_NAMESPACE;
        strArr[TelnetCommand.GA] = XmlPullParser.NO_NAMESPACE;
        strArr[TelnetCommand.SB] = XmlPullParser.NO_NAMESPACE;
        strArr[TelnetCommand.WILL] = XmlPullParser.NO_NAMESPACE;
        strArr[TelnetCommand.WONT] = XmlPullParser.NO_NAMESPACE;
        strArr[TelnetCommand.DO] = XmlPullParser.NO_NAMESPACE;
        strArr[TelnetCommand.DONT] = XmlPullParser.NO_NAMESPACE;
        strArr[__LAST_OPTION] = "Extended-Options-List";
        __optionString = strArr;
    }

    public static final String getOption(int code) {
        if (__optionString[code].length() == 0) {
            return "UNASSIGNED";
        }
        return __optionString[code];
    }

    public static final boolean isValidOption(int code) {
        return code <= __LAST_OPTION;
    }

    private TelnetOption() {
    }
}