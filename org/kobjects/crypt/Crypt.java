package org.kobjects.crypt;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.media.TransportMediator;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.ExploreByTouchHelper;
import com.google.android.gms.gcm.Task;
import com.hiti.printerprotocol.request.HitiPPR_BurnFirmware;
import com.hiti.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import java.io.IOException;
import java.util.Random;
import javax.jmdns.impl.constants.DNSConstants;
import javax.jmdns.impl.constants.DNSRecordClass;
import org.apache.commons.net.bsd.RCommandClient;
import org.apache.commons.net.bsd.RLoginClient;
import org.apache.commons.net.io.Util;
import org.apache.commons.net.nntp.NNTP;
import org.apache.commons.net.pop3.POP3;
import org.apache.commons.net.telnet.TelnetOption;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.transport.ServiceConnection;
import org.kxml2.wap.Wbxml;

public class Crypt {
    private static final int ITERATIONS = 16;
    private static final int[][] SPtrans;
    private static final int[] con_salt;
    private static final int[] cov_2char;
    private static final boolean[] shifts2;
    private static final int[][] skb;

    private Crypt() {
    }

    static {
        con_salt = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, ITERATIONS, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 0, 0, 0, 0, 0};
        shifts2 = new boolean[]{false, false, true, true, true, true, true, true, false, true, true, true, true, true, true, false};
        skb = new int[][]{new int[]{0, ITERATIONS, 536870912, 536870928, AccessibilityNodeInfoCompat.ACTION_CUT, 65552, 536936448, 536936464, AccessibilityNodeInfoCompat.ACTION_PREVIOUS_HTML_ELEMENT, 2064, 536872960, 536872976, 67584, 67600, 536938496, 536938512, 32, 48, 536870944, 536870960, 65568, 65584, 536936480, 536936496, 2080, 2096, 536872992, 536873008, 67616, 67632, 536938528, 536938544, AccessibilityNodeInfoCompat.ACTION_COLLAPSE, 524304, 537395200, 537395216, 589824, 589840, 537460736, 537460752, 526336, 526352, 537397248, 537397264, 591872, 591888, 537462784, 537462800, 524320, 524336, 537395232, 537395248, 589856, 589872, 537460768, 537460784, 526368, 526384, 537397280, 537397296, 591904, 591920, 537462816, 537462832}, new int[]{0, 33554432, AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD, 33562624, AccessibilityNodeInfoCompat.ACTION_SET_TEXT, 35651584, 2105344, 35659776, 4, 33554436, 8196, 33562628, 2097156, 35651588, 2105348, 35659780, Util.DEFAULT_COPY_BUFFER_SIZE, 33555456, 9216, 33563648, 2098176, 35652608, 2106368, 35660800, 1028, 33555460, 9220, 33563652, 2098180, 35652612, 2106372, 35660804, 268435456, 301989888, 268443648, 301998080, 270532608, 304087040, 270540800, 304095232, 268435460, 301989892, 268443652, 301998084, 270532612, 304087044, 270540804, 304095236, 268436480, 301990912, 268444672, 301999104, 270533632, 304088064, 270541824, 304096256, 268436484, 301990916, 268444676, 301999108, 270533636, 304088068, 270541828, 304096260}, new int[]{0, 1, ServiceConnection.DEFAULT_BUFFER_SIZE, 262145, ViewCompat.MEASURED_STATE_TOO_SMALL, 16777217, 17039360, 17039361, 2, 3, 262146, 262147, 16777218, 16777219, 17039362, 17039363, RCommandClient.MIN_CLIENT_PORT, RLoginClient.DEFAULT_PORT, 262656, 262657, 16777728, 16777729, 17039872, 17039873, RCommandClient.DEFAULT_PORT, 515, 262658, 262659, 16777730, 16777731, 17039874, 17039875, 134217728, 134217729, 134479872, 134479873, 150994944, 150994945, 151257088, 151257089, 134217730, 134217731, 134479874, 134479875, 150994946, 150994947, 151257090, 151257091, 134218240, 134218241, 134480384, 134480385, 150995456, 150995457, 151257600, 151257601, 134218242, 134218243, 134480386, 134480387, 150995458, 150995459, 151257602, 151257603}, new int[]{0, AccessibilityNodeInfoCompat.ACTION_DISMISS, DNSConstants.FLAGS_RD, 1048832, 8, InputDeviceCompat.SOURCE_TOUCHPAD, 264, 1048840, AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD, 1052672, 4352, 1052928, 4104, 1052680, 4360, 1052936, 67108864, 68157440, 67109120, 68157696, 67108872, 68157448, 67109128, 68157704, 67112960, 68161536, 67113216, 68161792, 67112968, 68161544, 67113224, 68161800, AccessibilityNodeInfoCompat.ACTION_SET_SELECTION, 1179648, 131328, 1179904, 131080, 1179656, 131336, 1179912, 135168, 1183744, 135424, 1184000, 135176, 1183752, 135432, 1184008, 67239936, 68288512, 67240192, 68288768, 67239944, 68288520, 67240200, 68288776, 67244032, 68292608, 67244288, 68292864, 67244040, 68292616, 67244296, 68292872}, new int[]{0, 268435456, AccessibilityNodeInfoCompat.ACTION_CUT, 268500992, 4, 268435460, InputDeviceCompat.SOURCE_TRACKBALL, 268500996, 536870912, 805306368, 536936448, 805371904, 536870916, 805306372, 536936452, 805371908, AccessibilityNodeInfoCompat.ACTION_DISMISS, 269484032, 1114112, 269549568, 1048580, 269484036, 1114116, 269549572, 537919488, 806354944, 537985024, 806420480, 537919492, 806354948, 537985028, 806420484, AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD, 268439552, 69632, 268505088, 4100, 268439556, 69636, 268505092, 536875008, 805310464, 536940544, 805376000, 536875012, 805310468, 536940548, 805376004, 1052672, 269488128, 1118208, 269553664, 1052676, 269488132, 1118212, 269553668, 537923584, 806359040, 537989120, 806424576, 537923588, 806359044, 537989124, 806424580}, new int[]{0, 134217728, 8, 134217736, Util.DEFAULT_COPY_BUFFER_SIZE, 134218752, 1032, 134218760, AccessibilityNodeInfoCompat.ACTION_SET_SELECTION, 134348800, 131080, 134348808, 132096, 134349824, 132104, 134349832, 1, 134217729, 9, 134217737, InputDeviceCompat.SOURCE_GAMEPAD, 134218753, 1033, 134218761, 131073, 134348801, 131081, 134348809, 132097, 134349825, 132105, 134349833, 33554432, 167772160, 33554440, 167772168, 33555456, 167773184, 33555464, 167773192, 33685504, 167903232, 33685512, 167903240, 33686528, 167904256, 33686536, 167904264, InputDeviceCompat.SOURCE_HDMI, 167772161, 33554441, 167772169, 33555457, 167773185, 33555465, 167773193, 33685505, 167903233, 33685513, 167903241, 33686529, 167904257, 33686537, 167904265}, new int[]{0, DNSConstants.FLAGS_RD, AccessibilityNodeInfoCompat.ACTION_COLLAPSE, 524544, ViewCompat.MEASURED_STATE_TOO_SMALL, 16777472, 17301504, 17301760, ITERATIONS, 272, 524304, 524560, InputDeviceCompat.SOURCE_JOYSTICK, 16777488, 17301520, 17301776, AccessibilityNodeInfoCompat.ACTION_SET_TEXT, 2097408, 2621440, 2621696, 18874368, 18874624, 19398656, 19398912, 2097168, 2097424, 2621456, 2621712, 18874384, 18874640, 19398672, 19398928, RCommandClient.MIN_CLIENT_PORT, 768, 524800, 525056, 16777728, 16777984, 17302016, 17302272, 528, 784, 524816, 525072, 16777744, 16778000, 17302032, 17302288, 2097664, 2097920, 2621952, 2622208, 18874880, 18875136, 19399168, 19399424, 2097680, 2097936, 2621968, 2622224, 18874896, 18875152, 19399184, 19399440}, new int[]{0, 67108864, ServiceConnection.DEFAULT_BUFFER_SIZE, 67371008, 2, 67108866, 262146, 67371010, AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD, 67117056, 270336, 67379200, InputDeviceCompat.SOURCE_MOUSE, 67117058, 270338, 67379202, 32, 67108896, 262176, 67371040, 34, 67108898, 262178, 67371042, 8224, 67117088, 270368, 67379232, 8226, 67117090, 270370, 67379234, AccessibilityNodeInfoCompat.ACTION_PREVIOUS_HTML_ELEMENT, 67110912, 264192, 67373056, 2050, 67110914, 264194, 67373058, Task.EXTRAS_LIMIT_BYTES, 67119104, 272384, 67381248, 10242, 67119106, 272386, 67381250, 2080, 67110944, 264224, 67373088, 2082, 67110946, 264226, 67373090, 10272, 67119136, 272416, 67381280, 10274, 67119138, 272418, 67381282}};
        SPtrans = new int[][]{new int[]{8520192, AccessibilityNodeInfoCompat.ACTION_SET_SELECTION, -2139095040, -2138963456, HitiPPR_BurnFirmware.BURN_FIRMWARE_SIZE, -2147352064, -2147352576, -2139095040, -2147352064, 8520192, 8519680, -2147483136, -2139094528, HitiPPR_BurnFirmware.BURN_FIRMWARE_SIZE, 0, -2147352576, AccessibilityNodeInfoCompat.ACTION_SET_SELECTION, ExploreByTouchHelper.INVALID_ID, 8389120, 131584, -2138963456, 8519680, -2147483136, 8389120, ExploreByTouchHelper.INVALID_ID, RCommandClient.MIN_CLIENT_PORT, 131584, -2138963968, RCommandClient.MIN_CLIENT_PORT, -2139094528, -2138963968, 0, 0, -2138963456, 8389120, -2147352576, 8520192, AccessibilityNodeInfoCompat.ACTION_SET_SELECTION, -2147483136, 8389120, -2138963968, RCommandClient.MIN_CLIENT_PORT, 131584, -2139095040, -2147352064, ExploreByTouchHelper.INVALID_ID, -2139095040, 8519680, -2138963456, 131584, 8519680, -2139094528, HitiPPR_BurnFirmware.BURN_FIRMWARE_SIZE, -2147483136, -2147352576, 0, AccessibilityNodeInfoCompat.ACTION_SET_SELECTION, HitiPPR_BurnFirmware.BURN_FIRMWARE_SIZE, -2139094528, 8520192, ExploreByTouchHelper.INVALID_ID, -2138963968, RCommandClient.MIN_CLIENT_PORT, -2147352064}, new int[]{268705796, 0, 270336, 268697600, 268435460, 8196, 268443648, 270336, AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD, 268697604, 4, 268443648, 262148, 268705792, 268697600, 4, ServiceConnection.DEFAULT_BUFFER_SIZE, 268443652, 268697604, AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD, 270340, 268435456, 0, 262148, 268443652, 270340, 268705792, 268435460, 268435456, ServiceConnection.DEFAULT_BUFFER_SIZE, 8196, 268705796, 262148, 268705792, 268443648, 270340, 268705796, 262148, 268435460, 0, 268435456, 8196, ServiceConnection.DEFAULT_BUFFER_SIZE, 268697604, AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD, 268435456, 270340, 268443652, 268705792, AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD, 0, 268435460, 4, 268705796, 270336, 268697600, 268697604, ServiceConnection.DEFAULT_BUFFER_SIZE, 8196, 268443648, 268443652, 4, 268697600, 270336}, new int[]{1090519040, 16842816, 64, 1090519104, 1073807360, ViewCompat.MEASURED_STATE_TOO_SMALL, 1090519104, 65600, 16777280, AccessibilityNodeInfoCompat.ACTION_CUT, 16842752, 1073741824, 1090584640, 1073741888, 1073741824, 1090584576, 0, 1073807360, 16842816, 64, 1073741888, 1090584640, AccessibilityNodeInfoCompat.ACTION_CUT, 1090519040, 1090584576, 16777280, 1073807424, 16842752, 65600, 0, ViewCompat.MEASURED_STATE_TOO_SMALL, 1073807424, 16842816, 64, 1073741824, AccessibilityNodeInfoCompat.ACTION_CUT, 1073741888, 1073807360, 16842752, 1090519104, 0, 16842816, 65600, 1090584576, 1073807360, ViewCompat.MEASURED_STATE_TOO_SMALL, 1090584640, 1073741824, 1073807424, 1090519040, ViewCompat.MEASURED_STATE_TOO_SMALL, 1090584640, AccessibilityNodeInfoCompat.ACTION_CUT, 16777280, 1090519104, 65600, 16777280, 0, 1090584576, 1073741888, 1090519040, 1073807424, 64, 16842752}, new int[]{1049602, 67109888, 2, 68158466, 0, 68157440, 67109890, 1048578, 68158464, 67108866, 67108864, 1026, 67108866, 1049602, AccessibilityNodeInfoCompat.ACTION_DISMISS, 67108864, 68157442, 1049600, Util.DEFAULT_COPY_BUFFER_SIZE, 2, 1049600, 67109890, 68157440, Util.DEFAULT_COPY_BUFFER_SIZE, 1026, 0, 1048578, 68158464, 67109888, 68157442, 68158466, AccessibilityNodeInfoCompat.ACTION_DISMISS, 68157442, 1026, AccessibilityNodeInfoCompat.ACTION_DISMISS, 67108866, 1049600, 67109888, 2, 68157440, 67109890, 0, Util.DEFAULT_COPY_BUFFER_SIZE, 1048578, 0, 68157442, 68158464, Util.DEFAULT_COPY_BUFFER_SIZE, 67108864, 68158466, 1049602, AccessibilityNodeInfoCompat.ACTION_DISMISS, 68158466, 2, 67109888, 1049602, 1048578, 1049600, 68157440, 67109890, 1026, 67108864, 67108866, 68158464}, new int[]{33554432, AccessibilityNodeInfoCompat.ACTION_COPY, DNSConstants.FLAGS_RD, 33571080, 33570824, 33554688, 16648, 33570816, AccessibilityNodeInfoCompat.ACTION_COPY, 8, 33554440, 16640, 33554696, 33570824, 33571072, 0, 16640, 33554432, 16392, 264, 33554688, 16648, 0, 33554440, 8, 33554696, 33571080, 16392, 33570816, DNSConstants.FLAGS_RD, 264, 33571072, 33571072, 33554696, 16392, 33570816, AccessibilityNodeInfoCompat.ACTION_COPY, 8, 33554440, 33554688, 33554432, 16640, 33571080, 0, 16648, 33554432, DNSConstants.FLAGS_RD, 16392, 33554696, DNSConstants.FLAGS_RD, 0, 33571080, 33570824, 33571072, 264, AccessibilityNodeInfoCompat.ACTION_COPY, 16640, 33570824, 33554688, 264, 8, 16648, 33570816, 33554440}, new int[]{536870928, 524304, 0, 537397248, 524304, AccessibilityNodeInfoCompat.ACTION_PREVIOUS_HTML_ELEMENT, 536872976, AccessibilityNodeInfoCompat.ACTION_COLLAPSE, 2064, 537397264, 526336, 536870912, 536872960, 536870928, 537395200, 526352, AccessibilityNodeInfoCompat.ACTION_COLLAPSE, 536872976, 537395216, 0, AccessibilityNodeInfoCompat.ACTION_PREVIOUS_HTML_ELEMENT, ITERATIONS, 537397248, 537395216, 537397264, 537395200, 536870912, 2064, ITERATIONS, 526336, 526352, 536872960, 2064, 536870912, 536872960, 526352, 537397248, 524304, 0, 536872960, 536870912, AccessibilityNodeInfoCompat.ACTION_PREVIOUS_HTML_ELEMENT, 537395216, AccessibilityNodeInfoCompat.ACTION_COLLAPSE, 524304, 537397264, 526336, ITERATIONS, 537397264, 526336, AccessibilityNodeInfoCompat.ACTION_COLLAPSE, 536872976, 536870928, 537395200, 526352, 0, AccessibilityNodeInfoCompat.ACTION_PREVIOUS_HTML_ELEMENT, 536870928, 536872976, 537397248, 537395200, 2064, ITERATIONS, 537395216}, new int[]{AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD, TransportMediator.FLAG_KEY_MEDIA_NEXT, 4194432, 4194305, 4198529, FragmentTransaction.TRANSIT_FRAGMENT_OPEN, 4224, 0, 4194304, 4194433, Wbxml.EXT_T_1, 4198400, 1, 4198528, 4198400, Wbxml.EXT_T_1, 4194433, AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD, FragmentTransaction.TRANSIT_FRAGMENT_OPEN, 4198529, 0, 4194432, 4194305, 4224, 4198401, 4225, 4198528, 1, 4225, 4198401, TransportMediator.FLAG_KEY_MEDIA_NEXT, 4194304, 4225, 4198400, 4198401, Wbxml.EXT_T_1, AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD, TransportMediator.FLAG_KEY_MEDIA_NEXT, 4194304, 4198401, 4194433, 4225, 4224, 0, TransportMediator.FLAG_KEY_MEDIA_NEXT, 4194305, 1, 4194432, 0, 4194433, 4194432, 4224, Wbxml.EXT_T_1, AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD, 4198529, 4194304, 4198528, 1, FragmentTransaction.TRANSIT_FRAGMENT_OPEN, 4198529, 4194305, 4198528, 4198400, FragmentTransaction.TRANSIT_FRAGMENT_OPEN}, new int[]{136314912, 136347648, 32800, 0, 134250496, 2097184, 136314880, 136347680, 32, 134217728, 2129920, 32800, 2129952, 134250528, 134217760, 136314880, DNSRecordClass.CLASS_UNIQUE, 2129952, 2097184, 134250496, 136347680, 134217760, 0, 2129920, 134217728, AccessibilityNodeInfoCompat.ACTION_SET_TEXT, 134250528, 136314912, AccessibilityNodeInfoCompat.ACTION_SET_TEXT, DNSRecordClass.CLASS_UNIQUE, 136347648, 32, AccessibilityNodeInfoCompat.ACTION_SET_TEXT, DNSRecordClass.CLASS_UNIQUE, 134217760, 136347680, 32800, 134217728, 0, 2129920, 136314912, 134250528, 134250496, 2097184, 136347648, 32, 2097184, 134250496, 136347680, AccessibilityNodeInfoCompat.ACTION_SET_TEXT, 136314880, 134217760, 2129920, 32800, 134250528, 136314880, 32, 136347648, 2129952, 0, 134217728, 136314912, DNSRecordClass.CLASS_UNIQUE, 2129952}};
        cov_2char = new int[]{46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, ControllerState.PLAY_PHOTO, ControllerState.NO_PLAY_ITEM, ControllerState.PLAY_COUNT_STATE, 104, 105, 106, 107, 108, 109, POP3.DEFAULT_PORT, 111, 112, 113, 114, DNSConstants.RESPONSE_MAX_WAIT_INTERVAL, 116, 117, 118, NNTP.DEFAULT_PORT, SoapEnvelope.VER12, 121, 122};
    }

    private static final int byteToUnsigned(byte b) {
        byte value = b;
        return value >= null ? value : value + DNSConstants.FLAGS_RD;
    }

    private static int fourBytesToInt(byte[] b, int offset) {
        int offset2 = offset + 1;
        offset = offset2 + 1;
        offset2 = offset + 1;
        offset = offset2 + 1;
        return ((byteToUnsigned(b[offset]) | (byteToUnsigned(b[offset2]) << 8)) | (byteToUnsigned(b[offset]) << ITERATIONS)) | (byteToUnsigned(b[offset2]) << 24);
    }

    private static final void intToFourBytes(int iValue, byte[] b, int offset) {
        int i = offset + 1;
        b[offset] = (byte) (iValue & TelnetOption.MAX_OPTION_VALUE);
        offset = i + 1;
        b[i] = (byte) ((iValue >>> 8) & TelnetOption.MAX_OPTION_VALUE);
        i = offset + 1;
        b[offset] = (byte) ((iValue >>> ITERATIONS) & TelnetOption.MAX_OPTION_VALUE);
        offset = i + 1;
        b[i] = (byte) ((iValue >>> 24) & TelnetOption.MAX_OPTION_VALUE);
    }

    private static final void PERM_OP(int a, int b, int n, int m, int[] results) {
        int t = ((a >>> n) ^ b) & m;
        b ^= t;
        results[0] = a ^ (t << n);
        results[1] = b;
    }

    private static final int HPERM_OP(int a, int n, int m) {
        int t = ((a << (16 - n)) ^ a) & m;
        return (a ^ t) ^ (t >>> (16 - n));
    }

    private static int[] des_set_key(byte[] key) {
        int[] schedule = new int[32];
        int[] results = new int[2];
        PERM_OP(fourBytesToInt(key, 4), fourBytesToInt(key, 0), 4, 252645135, results);
        int d = results[0];
        PERM_OP(HPERM_OP(d, -2, -859045888), HPERM_OP(results[1], -2, -859045888), 1, 1431655765, results);
        PERM_OP(results[1], results[0], 8, 16711935, results);
        PERM_OP(results[1], results[0], 1, 1431655765, results);
        d = results[0];
        int c = results[1];
        d = ((((d & TelnetOption.MAX_OPTION_VALUE) << ITERATIONS) | (MotionEventCompat.ACTION_POINTER_INDEX_MASK & d)) | ((16711680 & d) >>> ITERATIONS)) | ((-268435456 & c) >>> 4);
        c &= 268435455;
        int j = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            if (shifts2[i]) {
                c = (c >>> 2) | (c << 26);
                d = (d >>> 2) | (d << 26);
            } else {
                c = (c >>> 1) | (c << 27);
                d = (d >>> 1) | (d << 27);
            }
            c &= 268435455;
            d &= 268435455;
            int s = ((skb[0][c & 63] | skb[1][((c >>> 6) & 3) | ((c >>> 7) & 60)]) | skb[2][((c >>> 13) & 15) | ((c >>> 14) & 48)]) | skb[3][(((c >>> 20) & 1) | ((c >>> 21) & 6)) | ((c >>> 22) & 56)];
            int t = ((skb[4][d & 63] | skb[5][((d >>> 7) & 3) | ((d >>> 8) & 60)]) | skb[6][(d >>> 15) & 63]) | skb[7][((d >>> 21) & 15) | ((d >>> 22) & 48)];
            int i2 = j + 1;
            schedule[j] = ((t << ITERATIONS) | (SupportMenu.USER_MASK & s)) & -1;
            s = (s >>> ITERATIONS) | (SupportMenu.CATEGORY_MASK & t);
            j = i2 + 1;
            schedule[i2] = ((s << 4) | (s >>> 28)) & -1;
        }
        return schedule;
    }

    private static final int D_ENCRYPT(int L, int R, int S, int E0, int E1, int[] s) {
        int v = R ^ (R >>> ITERATIONS);
        int u = v & E0;
        v &= E1;
        u = (((u << ITERATIONS) ^ u) ^ R) ^ s[S];
        int t = (((v << ITERATIONS) ^ v) ^ R) ^ s[S + 1];
        t = (t >>> 4) | (t << 28);
        return L ^ (((((((SPtrans[1][t & 63] | SPtrans[3][(t >>> 8) & 63]) | SPtrans[5][(t >>> ITERATIONS) & 63]) | SPtrans[7][(t >>> 24) & 63]) | SPtrans[0][u & 63]) | SPtrans[2][(u >>> 8) & 63]) | SPtrans[4][(u >>> ITERATIONS) & 63]) | SPtrans[6][(u >>> 24) & 63]);
    }

    private static final int[] body(int[] schedule, int Eswap0, int Eswap1) {
        int t;
        int left = 0;
        int right = 0;
        for (int j = 0; j < 25; j++) {
            for (int i = 0; i < 32; i += 4) {
                left = D_ENCRYPT(left, right, i, Eswap0, Eswap1, schedule);
                right = D_ENCRYPT(right, left, i + 2, Eswap0, Eswap1, schedule);
            }
            t = left;
            left = right;
            right = t;
        }
        t = right;
        int[] results = new int[2];
        PERM_OP(((left >>> 1) | (left << 31)) & -1, ((t >>> 1) | (t << 31)) & -1, 1, 1431655765, results);
        PERM_OP(results[1], results[0], 8, 16711935, results);
        PERM_OP(results[1], results[0], 2, 858993459, results);
        PERM_OP(results[1], results[0], ITERATIONS, SupportMenu.USER_MASK, results);
        PERM_OP(results[1], results[0], 4, 252645135, results);
        right = results[0];
        return new int[]{results[1], right};
    }

    public static final String generate(String original) {
        return crypt(Integer.toHexString(new Random().nextInt() & SupportMenu.USER_MASK), original);
    }

    public static final boolean match(String test, String full) {
        if (full == null || full.length() < 3) {
            return false;
        }
        return full.equals(crypt(full.substring(0, 2), test));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.String crypt(java.lang.String r19, java.lang.String r20) {
        /*
    L_0x0000:
        r17 = r19.length();
        r18 = 2;
        r0 = r17;
        r1 = r18;
        if (r0 >= r1) goto L_0x0024;
    L_0x000c:
        r17 = new java.lang.StringBuilder;
        r17.<init>();
        r0 = r17;
        r1 = r19;
        r17 = r0.append(r1);
        r18 = "A";
        r17 = r17.append(r18);
        r19 = r17.toString();
        goto L_0x0000;
    L_0x0024:
        r5 = new java.lang.StringBuffer;
        r17 = "             ";
        r0 = r17;
        r5.<init>(r0);
        r17 = 0;
        r0 = r19;
        r1 = r17;
        r8 = r0.charAt(r1);
        r17 = 1;
        r0 = r19;
        r1 = r17;
        r7 = r0.charAt(r1);
        r17 = 0;
        r0 = r17;
        r5.setCharAt(r0, r8);
        r17 = 1;
        r0 = r17;
        r5.setCharAt(r0, r7);
        r17 = con_salt;
        r2 = r17[r8];
        r17 = con_salt;
        r17 = r17[r7];
        r3 = r17 << 4;
        r17 = 8;
        r0 = r17;
        r12 = new byte[r0];
        r9 = 0;
    L_0x0060:
        r0 = r12.length;
        r17 = r0;
        r0 = r17;
        if (r9 >= r0) goto L_0x006e;
    L_0x0067:
        r17 = 0;
        r12[r9] = r17;
        r9 = r9 + 1;
        goto L_0x0060;
    L_0x006e:
        r9 = 0;
    L_0x006f:
        r0 = r12.length;
        r17 = r0;
        r0 = r17;
        if (r9 >= r0) goto L_0x0090;
    L_0x0076:
        r17 = r20.length();
        r0 = r17;
        if (r9 >= r0) goto L_0x0090;
    L_0x007e:
        r0 = r20;
        r10 = r0.charAt(r9);
        r17 = r10 << 1;
        r0 = r17;
        r0 = (byte) r0;
        r17 = r0;
        r12[r9] = r17;
        r9 = r9 + 1;
        goto L_0x006f;
    L_0x0090:
        r14 = des_set_key(r12);
        r13 = body(r14, r2, r3);
        r17 = 9;
        r0 = r17;
        r4 = new byte[r0];
        r17 = 0;
        r17 = r13[r17];
        r18 = 0;
        r0 = r17;
        r1 = r18;
        intToFourBytes(r0, r4, r1);
        r17 = 1;
        r17 = r13[r17];
        r18 = 4;
        r0 = r17;
        r1 = r18;
        intToFourBytes(r0, r4, r1);
        r17 = 8;
        r18 = 0;
        r4[r17] = r18;
        r9 = 2;
        r16 = 0;
        r15 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
    L_0x00c3:
        r17 = 13;
        r0 = r17;
        if (r9 >= r0) goto L_0x00f7;
    L_0x00c9:
        r11 = 0;
        r6 = 0;
    L_0x00cb:
        r17 = 6;
        r0 = r17;
        if (r11 >= r0) goto L_0x00f4;
    L_0x00d1:
        r6 = r6 << 1;
        r17 = r4[r16];
        r17 = r17 & r15;
        if (r17 == 0) goto L_0x00db;
    L_0x00d9:
        r6 = r6 | 1;
    L_0x00db:
        r15 = r15 >>> 1;
        if (r15 != 0) goto L_0x00e3;
    L_0x00df:
        r16 = r16 + 1;
        r15 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
    L_0x00e3:
        r17 = cov_2char;
        r17 = r17[r6];
        r0 = r17;
        r0 = (char) r0;
        r17 = r0;
        r0 = r17;
        r5.setCharAt(r9, r0);
        r11 = r11 + 1;
        goto L_0x00cb;
    L_0x00f4:
        r9 = r9 + 1;
        goto L_0x00c3;
    L_0x00f7:
        r17 = r5.toString();
        return r17;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.kobjects.crypt.Crypt.crypt(java.lang.String, java.lang.String):java.lang.String");
    }

    public static void main(String[] args) throws IOException {
        StringBuffer buf = new StringBuffer();
        while (true) {
            int i = System.in.read();
            if (i < 32) {
                System.out.println(generate(buf.toString()));
                return;
            }
            buf.append((char) i);
        }
    }
}
