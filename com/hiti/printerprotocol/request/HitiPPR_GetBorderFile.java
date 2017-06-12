package com.hiti.printerprotocol.request;

import android.content.Context;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.SettingStep;
import com.hiti.utility.FileUtility;
import com.hiti.utility.PringoConvenientConst;
import java.util.ArrayList;
import java.util.Iterator;

public class HitiPPR_GetBorderFile extends HitiPPR_PrinterCommandNew {
    private static final int CHECK_NEXT_FOLDER = 2;
    private static final int CHECK_NEXT_RANGE = 1;
    private static final int H_FILENAME_FOLDER = 4;
    private static final int H_THUMB_FOLDER = 3;
    private static final int RANGE_BORDER = 3;
    private static final int RANGE_COLLAGE = 5;
    private static final int RANGE_FILENAME = 4;
    private static final int RANGE_PRINTOUT = 2;
    private static final int RANGE_ROOT = 1;
    private static final int V_FILENAME_FOLDER = 2;
    private static final int V_THUMB_FOLDER = 1;
    byte m_AttrFolderNameEncode;
    private int m_AttrStorageIdNum;
    private ArrayList<byte[]> m_AttrStorageIdsList;
    String m_EncodeType;
    byte[] m_FolderID;
    int m_FolderIdNum;
    ArrayList<byte[]> m_FolderIdsList;
    byte[] m_FolderSID;
    private ArrayList<byte[]> m_FolderSIDsList;
    int m_LenOfFolderName;
    byte[] m_NowSID;
    byte[] m_PrintOutFolderId;
    byte[] m_RootFolderId;
    private int m_SIDNum;
    boolean m_bOnceGetDone;
    private boolean m_boSilent;
    ArrayList<byte[]> m_byBorderIdsList;
    ArrayList<byte[]> m_byThumbBdIdsList;
    int m_cnt;
    int m_cntPhoto;
    float m_fRatioF;
    byte[] m_fileNameFolderId;
    byte[] m_hBorderFolderId;
    byte[] m_hThumbId;
    String m_hThumbPath;
    int m_iDetectStep;
    int m_iFolderRange;
    int m_iLastRead;
    int m_iRead;
    int m_iSumOfCheckDoneBorder;
    byte[] m_objFormat;
    byte[] m_parentObjH;
    int m_readNum;
    ArrayList<String> m_strBorderCheckDoneList;
    ArrayList<String> m_strBorderFolerList;
    String m_strBorderName;
    ArrayList<String> m_strBorderNameList;
    ArrayList<String> m_strFileFolderList;
    String m_strFileNameFolder;
    String m_strFolderName;
    ArrayList<String> m_strFolderPathList;
    ArrayList<String> m_strImportedBorderPath;
    ArrayList<String> m_strPrintOutScaleList;
    String m_strRootPath;
    ArrayList<String> m_strThumbBdPathList;
    float m_tbCnt;
    int m_thumbLen;
    String m_vBorderFileName;
    byte[] m_vBorderFolderId;
    byte[] m_vFileId;
    String m_vParentPath;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_GetBorderFile.1 */
    static /* synthetic */ class C03991 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$printerprotocol$SettingStep;

        static {
            $SwitchMap$com$hiti$printerprotocol$SettingStep = new int[SettingStep.values().length];
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_Complete.ordinal()] = HitiPPR_GetBorderFile.V_THUMB_FOLDER;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_AuthenticationRequest.ordinal()] = HitiPPR_GetBorderFile.V_FILENAME_FOLDER;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_AuthenticationResponse.ordinal()] = HitiPPR_GetBorderFile.RANGE_BORDER;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetStorageIdRequest.ordinal()] = HitiPPR_GetBorderFile.RANGE_FILENAME;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetStorageIdResponse.ordinal()] = HitiPPR_GetBorderFile.RANGE_COLLAGE;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetStorageIdResponseSuccess.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetObjectHandleRequest.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetObjectHandleResponse.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetObjectHandleResponseSuccess.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetObjectInfoRequest.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetObjectInfoResponse.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetObjectInfoResponseSuccess.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetImageRequest.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetImageResponse.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetImageResponseSuccess.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
        }
    }

    public HitiPPR_GetBorderFile(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_boSilent = false;
        this.m_SIDNum = -1;
        this.m_AttrStorageIdNum = -1;
        this.m_AttrStorageIdsList = null;
        this.m_FolderSIDsList = null;
        this.m_parentObjH = new byte[]{(byte) -1, (byte) -1, (byte) -1, (byte) -1};
        this.m_objFormat = new byte[]{(byte) 48, (byte) 1};
        this.m_FolderIdNum = -1;
        this.m_NowSID = null;
        this.m_FolderIdsList = null;
        this.m_cntPhoto = V_THUMB_FOLDER;
        this.m_iRead = -1;
        this.m_iLastRead = -1;
        this.m_readNum = 0;
        this.m_FolderID = null;
        this.m_FolderSID = null;
        this.m_LenOfFolderName = -1;
        this.m_strFolderName = null;
        this.m_AttrFolderNameEncode = (byte) -1;
        this.m_EncodeType = null;
        this.m_iFolderRange = V_THUMB_FOLDER;
        this.m_iDetectStep = V_THUMB_FOLDER;
        this.m_strBorderName = null;
        this.m_vBorderFileName = null;
        this.m_strFileNameFolder = null;
        this.m_RootFolderId = null;
        this.m_PrintOutFolderId = null;
        this.m_vBorderFolderId = null;
        this.m_hBorderFolderId = null;
        this.m_fileNameFolderId = null;
        this.m_vFileId = null;
        this.m_hThumbId = null;
        this.m_hThumbPath = null;
        this.m_byBorderIdsList = null;
        this.m_byThumbBdIdsList = null;
        this.m_strBorderNameList = null;
        this.m_strThumbBdPathList = null;
        this.m_strBorderCheckDoneList = null;
        this.m_strPrintOutScaleList = null;
        this.m_strBorderFolerList = null;
        this.m_strFileFolderList = null;
        this.m_strFolderPathList = null;
        this.m_strImportedBorderPath = null;
        this.m_strRootPath = null;
        this.m_vParentPath = null;
        this.m_iSumOfCheckDoneBorder = -1;
        this.m_cnt = -1;
        this.m_thumbLen = -1;
        this.m_tbCnt = 0.0f;
        this.m_bOnceGetDone = false;
        this.m_byBorderIdsList = new ArrayList();
        this.m_strBorderNameList = new ArrayList();
        this.m_strBorderCheckDoneList = new ArrayList();
        this.m_strPrintOutScaleList = new ArrayList();
        this.m_strBorderFolerList = new ArrayList();
        this.m_strFileFolderList = new ArrayList();
        this.m_strThumbBdPathList = new ArrayList();
        this.m_byThumbBdIdsList = new ArrayList();
        this.m_strPrintOutScaleList.add("4x6");
        this.m_strPrintOutScaleList.add("5x7");
        this.m_strPrintOutScaleList.add("6x8");
        this.m_strBorderFolerList.add(PringoConvenientConst.V_BORDER_PATH);
        this.m_strBorderFolerList.add(PringoConvenientConst.H_BORDER_PATH);
        this.m_strFileFolderList.add(PringoConvenientConst.THUMB);
        this.LOG.m385i("HitiPPR_GetBorderFile", "HitiPPR_GetBorderFile");
    }

    public void SetSilentMode() {
        this.m_boSilent = true;
    }

    public void StartRequest() {
        GetBorderFile();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void GetBorderFile() {
        /*
        r33 = this;
        r27 = r33.IsRunning();
        if (r27 == 0) goto L_0x004c;
    L_0x0006:
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "GetBorderFile";
        r29 = r33.GetCurrentStep();
        r29 = java.lang.String.valueOf(r29);
        r27.m385i(r28, r29);
        r27 = com.hiti.printerprotocol.request.HitiPPR_GetBorderFile.C03991.$SwitchMap$com$hiti$printerprotocol$SettingStep;
        r28 = r33.GetCurrentStep();
        r28 = r28.ordinal();
        r27 = r27[r28];
        switch(r27) {
            case 1: goto L_0x004d;
            case 2: goto L_0x005e;
            case 3: goto L_0x0080;
            case 4: goto L_0x00b2;
            case 5: goto L_0x00d6;
            case 6: goto L_0x01a5;
            case 7: goto L_0x029b;
            case 8: goto L_0x0456;
            case 9: goto L_0x04b9;
            case 10: goto L_0x077b;
            case 11: goto L_0x095a;
            case 12: goto L_0x0995;
            case 13: goto L_0x0ae7;
            case 14: goto L_0x0b75;
            case 15: goto L_0x0bf5;
            default: goto L_0x0028;
        };
    L_0x0028:
        r27 = r33.IsConnectError();
        if (r27 == 0) goto L_0x0e88;
    L_0x002e:
        r0 = r33;
        r0 = r0.m_boSilent;
        r27 = r0;
        r28 = 1;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x0e63;
    L_0x003c:
        r27 = 365; // 0x16d float:5.11E-43 double:1.803E-321;
        r28 = 0;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r0.SendMessage(r1, r2);
        r33.Stop();
    L_0x004c:
        return;
    L_0x004d:
        r27 = 364; // 0x16c float:5.1E-43 double:1.8E-321;
        r28 = 0;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r0.SendMessage(r1, r2);
        r33.Stop();
        goto L_0x0028;
    L_0x005e:
        r27 = r33.Authentication_Request();
        if (r27 == 0) goto L_0x0076;
    L_0x0064:
        r27 = 7;
        r28 = 0;
        r29 = com.hiti.printerprotocol.SettingStep.Step_AuthenticationResponse;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r3 = r29;
        r0.DecideNextStepAndPrepareReadBuff(r1, r2, r3);
        goto L_0x0028;
    L_0x0076:
        r27 = com.hiti.printerprotocol.SettingStep.Step_Error;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x0080:
        r27 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r0 = r33;
        r1 = r27;
        r27 = r0.ReadResponse(r1);
        if (r27 == 0) goto L_0x00ad;
    L_0x008c:
        r27 = r33.IsReadComplete();
        if (r27 == 0) goto L_0x0028;
    L_0x0092:
        r27 = r33.CheckCommandSuccess();
        if (r27 == 0) goto L_0x00a2;
    L_0x0098:
        r27 = com.hiti.printerprotocol.SettingStep.Step_GetStorageIdRequest;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x00a2:
        r27 = com.hiti.printerprotocol.SettingStep.Step_Error;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x00ad:
        r33.DecideErrorStatus();
        goto L_0x0028;
    L_0x00b2:
        r27 = r33.Get_Storage_Id_Request();
        if (r27 == 0) goto L_0x00cb;
    L_0x00b8:
        r27 = 7;
        r28 = 0;
        r29 = com.hiti.printerprotocol.SettingStep.Step_GetStorageIdResponse;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r3 = r29;
        r0.DecideNextStepAndPrepareReadBuff(r1, r2, r3);
        goto L_0x0028;
    L_0x00cb:
        r27 = com.hiti.printerprotocol.SettingStep.Step_Error;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x00d6:
        r27 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r0 = r33;
        r1 = r27;
        r27 = r0.ReadResponse(r1);
        if (r27 == 0) goto L_0x01a0;
    L_0x00e2:
        r27 = r33.IsReadComplete();
        if (r27 == 0) goto L_0x0028;
    L_0x00e8:
        r27 = r33.CheckCommandSuccess();
        if (r27 == 0) goto L_0x0101;
    L_0x00ee:
        r27 = 4;
        r28 = 0;
        r29 = com.hiti.printerprotocol.SettingStep.Step_GetStorageIdResponseSuccess;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r3 = r29;
        r0.DecideNextStepAndPrepareReadBuff(r1, r2, r3);
        goto L_0x0028;
    L_0x0101:
        r0 = r33;
        r0 = r0.m_lpReadData;
        r27 = r0;
        r28 = 2;
        r27 = r27[r28];
        r28 = 70;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x0125;
    L_0x0113:
        r0 = r33;
        r0 = r0.m_lpReadData;
        r27 = r0;
        r28 = 3;
        r27 = r27[r28];
        r28 = 5;
        r0 = r27;
        r1 = r28;
        if (r0 == r1) goto L_0x0149;
    L_0x0125:
        r0 = r33;
        r0 = r0.m_lpReadData;
        r27 = r0;
        r28 = 2;
        r27 = r27[r28];
        r28 = 71;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x015d;
    L_0x0137:
        r0 = r33;
        r0 = r0.m_lpReadData;
        r27 = r0;
        r28 = 3;
        r27 = r27[r28];
        r28 = 1;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x015d;
    L_0x0149:
        r0 = r33;
        r0 = r0.m_Context;
        r27 = r0;
        r28 = 2131099748; // 0x7f060064 float:1.7811858E38 double:1.0529031733E-314;
        r18 = r27.getString(r28);
        r0 = r33;
        r1 = r18;
        r0.SetErrorMSG(r1);
    L_0x015d:
        r0 = r33;
        r0 = r0.m_lpReadData;
        r27 = r0;
        r28 = 2;
        r27 = r27[r28];
        r28 = 64;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x0195;
    L_0x016f:
        r0 = r33;
        r0 = r0.m_lpReadData;
        r27 = r0;
        r28 = 3;
        r27 = r27[r28];
        r28 = 4;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x0195;
    L_0x0181:
        r0 = r33;
        r0 = r0.m_Context;
        r27 = r0;
        r28 = 2131099956; // 0x7f060134 float:1.781228E38 double:1.052903276E-314;
        r18 = r27.getString(r28);
        r0 = r33;
        r1 = r18;
        r0.SetErrorMSG(r1);
    L_0x0195:
        r27 = com.hiti.printerprotocol.SettingStep.Step_Error;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x01a0:
        r33.DecideErrorStatus();
        goto L_0x0028;
    L_0x01a5:
        r27 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r0 = r33;
        r1 = r27;
        r27 = r0.ReadResponse(r1);
        if (r27 == 0) goto L_0x0296;
    L_0x01b1:
        r27 = r33.IsReadComplete();
        if (r27 == 0) goto L_0x028b;
    L_0x01b7:
        r0 = r33;
        r0 = r0.m_AttrStorageIdNum;
        r27 = r0;
        r28 = -1;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x0207;
    L_0x01c5:
        r0 = r33;
        r0 = r0.m_lpReadData;
        r27 = r0;
        r28 = 0;
        r29 = 4;
        r27 = com.hiti.utility.ByteConvertUtility.ByteToInt(r27, r28, r29);
        r0 = r27;
        r1 = r33;
        r1.m_AttrStorageIdNum = r0;
        r0 = r33;
        r0 = r0.m_AttrStorageIdNum;
        r27 = r0;
        r27 = r27 * 4;
        r28 = 0;
        r29 = 0;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r3 = r29;
        r0.DecideNextStepAndPrepareReadBuff(r1, r2, r3);
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "m_AttrStorageIdNum";
        r0 = r33;
        r0 = r0.m_AttrStorageIdNum;
        r29 = r0;
        r29 = java.lang.String.valueOf(r29);
        r27.m385i(r28, r29);
        goto L_0x0028;
    L_0x0207:
        r0 = r33;
        r0 = r0.m_AttrStorageIdsList;
        r27 = r0;
        if (r27 != 0) goto L_0x0028;
    L_0x020f:
        r27 = new java.util.ArrayList;
        r27.<init>();
        r0 = r27;
        r1 = r33;
        r1.m_AttrStorageIdsList = r0;
        r27 = new java.util.ArrayList;
        r27.<init>();
        r0 = r27;
        r1 = r33;
        r1.m_FolderSIDsList = r0;
        r12 = 0;
    L_0x0226:
        r0 = r33;
        r0 = r0.m_AttrStorageIdNum;
        r27 = r0;
        r0 = r27;
        if (r12 >= r0) goto L_0x0280;
    L_0x0230:
        r27 = 4;
        r0 = r27;
        r0 = new byte[r0];
        r23 = r0;
        r17 = 0;
    L_0x023a:
        r27 = 4;
        r0 = r17;
        r1 = r27;
        if (r0 >= r1) goto L_0x0253;
    L_0x0242:
        r0 = r33;
        r0 = r0.m_lpReadData;
        r27 = r0;
        r28 = r12 * 4;
        r28 = r28 + r17;
        r27 = r27[r28];
        r23[r17] = r27;
        r17 = r17 + 1;
        goto L_0x023a;
    L_0x0253:
        r0 = r33;
        r0 = r0.m_AttrStorageIdsList;
        r27 = r0;
        r0 = r27;
        r1 = r23;
        r0.add(r1);
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "storageID";
        r29 = 0;
        r30 = 4;
        r0 = r23;
        r1 = r29;
        r2 = r30;
        r29 = com.hiti.utility.ByteConvertUtility.ByteToInt(r0, r1, r2);
        r29 = java.lang.String.valueOf(r29);
        r27.m385i(r28, r29);
        r12 = r12 + 1;
        goto L_0x0226;
    L_0x0280:
        r27 = com.hiti.printerprotocol.SettingStep.Step_GetObjectHandleRequest;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x028b:
        r27 = com.hiti.printerprotocol.SettingStep.Step_Error;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x0296:
        r33.DecideErrorStatus();
        goto L_0x0028;
    L_0x029b:
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "Step_GetObjectHandleRequest";
        r29 = new java.lang.StringBuilder;
        r29.<init>();
        r30 = "SIDNum = ";
        r29 = r29.append(r30);
        r0 = r33;
        r0 = r0.m_SIDNum;
        r30 = r0;
        r29 = r29.append(r30);
        r29 = r29.toString();
        r27.m385i(r28, r29);
        r0 = r33;
        r0 = r0.m_SIDNum;
        r27 = r0;
        if (r27 != 0) goto L_0x02da;
    L_0x02c7:
        r27 = com.hiti.printerprotocol.SettingStep.Step_GetObjectInfoRequest;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        r27 = -1;
        r0 = r27;
        r1 = r33;
        r1.m_FolderIdNum = r0;
        goto L_0x0028;
    L_0x02da:
        r0 = r33;
        r0 = r0.m_SIDNum;
        r27 = r0;
        r28 = -2;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x02f0;
    L_0x02e8:
        r27 = -1;
        r0 = r27;
        r1 = r33;
        r1.m_SIDNum = r0;
    L_0x02f0:
        r0 = r33;
        r0 = r0.m_SIDNum;
        r27 = r0;
        r28 = -1;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x0348;
    L_0x02fe:
        r0 = r33;
        r0 = r0.m_AttrStorageIdsList;
        r27 = r0;
        r27 = r27.size();
        r0 = r27;
        r1 = r33;
        r1.m_SIDNum = r0;
        r27 = new java.util.ArrayList;
        r27.<init>();
        r0 = r27;
        r1 = r33;
        r1.m_FolderIdsList = r0;
        r27 = new java.util.ArrayList;
        r27.<init>();
        r0 = r27;
        r1 = r33;
        r1.m_FolderSIDsList = r0;
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "Step_GetObjectHandleRequest";
        r29 = new java.lang.StringBuilder;
        r29.<init>();
        r30 = "SIDNum = ";
        r29 = r29.append(r30);
        r0 = r33;
        r0 = r0.m_SIDNum;
        r30 = r0;
        r29 = r29.append(r30);
        r29 = r29.toString();
        r27.m385i(r28, r29);
    L_0x0348:
        r0 = r33;
        r0 = r0.m_objFormat;
        r27 = r0;
        r28 = 0;
        r29 = 48;
        r27[r28] = r29;
        r0 = r33;
        r0 = r0.m_objFormat;
        r27 = r0;
        r28 = 1;
        r29 = 1;
        r27[r28] = r29;
        r0 = r33;
        r0 = r0.m_iFolderRange;
        r27 = r0;
        r28 = 1;
        r0 = r27;
        r1 = r28;
        if (r0 <= r1) goto L_0x03f7;
    L_0x036e:
        r0 = r33;
        r0 = r0.m_FolderIdsList;
        r27 = r0;
        r27.clear();
        r0 = r33;
        r0 = r0.m_FolderSIDsList;
        r27 = r0;
        r27.clear();
        r0 = r33;
        r0 = r0.m_iFolderRange;
        r27 = r0;
        r28 = 5;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x03a6;
    L_0x038e:
        r0 = r33;
        r0 = r0.m_objFormat;
        r27 = r0;
        r28 = 0;
        r29 = 56;
        r27[r28] = r29;
        r0 = r33;
        r0 = r0.m_objFormat;
        r27 = r0;
        r28 = 1;
        r29 = 11;
        r27[r28] = r29;
    L_0x03a6:
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "m_SIDNum";
        r0 = r33;
        r0 = r0.m_SIDNum;
        r29 = r0;
        r29 = java.lang.String.valueOf(r29);
        r27.m385i(r28, r29);
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "m_parentObjH";
        r0 = r33;
        r0 = r0.m_parentObjH;
        r29 = r0;
        r29 = java.lang.String.valueOf(r29);
        r27.m385i(r28, r29);
        r0 = r33;
        r0 = r0.m_parentObjH;
        r27 = r0;
        if (r27 != 0) goto L_0x0418;
    L_0x03d8:
        r0 = r33;
        r0 = r0.m_Context;
        r27 = r0;
        r28 = 2131099868; // 0x7f0600dc float:1.7812101E38 double:1.0529032326E-314;
        r27 = r27.getString(r28);
        r0 = r33;
        r1 = r27;
        r0.SetErrorMSG(r1);
        r27 = com.hiti.printerprotocol.SettingStep.Step_Error;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x03f7:
        r0 = r33;
        r0 = r0.m_AttrStorageIdsList;
        r27 = r0;
        r0 = r33;
        r0 = r0.m_SIDNum;
        r28 = r0;
        r28 = r28 + -1;
        r0 = r28;
        r1 = r33;
        r1.m_SIDNum = r0;
        r27 = r27.get(r28);
        r27 = (byte[]) r27;
        r0 = r27;
        r1 = r33;
        r1.m_NowSID = r0;
        goto L_0x03a6;
    L_0x0418:
        r0 = r33;
        r0 = r0.m_NowSID;
        r27 = r0;
        r0 = r33;
        r0 = r0.m_parentObjH;
        r28 = r0;
        r0 = r33;
        r0 = r0.m_objFormat;
        r29 = r0;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r3 = r29;
        r27 = r0.Get_Object_Handle_Request(r1, r2, r3);
        if (r27 == 0) goto L_0x044b;
    L_0x0438:
        r27 = 7;
        r28 = 0;
        r29 = com.hiti.printerprotocol.SettingStep.Step_GetObjectHandleResponse;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r3 = r29;
        r0.DecideNextStepAndPrepareReadBuff(r1, r2, r3);
        goto L_0x0028;
    L_0x044b:
        r27 = com.hiti.printerprotocol.SettingStep.Step_Error;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x0456:
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "Step_GetObjectHandleResponse";
        r29 = new java.lang.StringBuilder;
        r29.<init>();
        r30 = "m_objFormat = ";
        r29 = r29.append(r30);
        r0 = r33;
        r0 = r0.m_objFormat;
        r30 = r0;
        r31 = 1;
        r30 = r30[r31];
        r29 = r29.append(r30);
        r29 = r29.toString();
        r27.m385i(r28, r29);
        r27 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r0 = r33;
        r1 = r27;
        r27 = r0.ReadResponse(r1);
        if (r27 == 0) goto L_0x04b4;
    L_0x048a:
        r27 = r33.IsReadComplete();
        if (r27 == 0) goto L_0x0028;
    L_0x0490:
        r27 = r33.CheckCommandSuccess();
        if (r27 == 0) goto L_0x04a9;
    L_0x0496:
        r27 = 4;
        r28 = 0;
        r29 = com.hiti.printerprotocol.SettingStep.Step_GetObjectHandleResponseSuccess;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r3 = r29;
        r0.DecideNextStepAndPrepareReadBuff(r1, r2, r3);
        goto L_0x0028;
    L_0x04a9:
        r27 = com.hiti.printerprotocol.SettingStep.Step_Error;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x04b4:
        r33.DecideErrorStatus();
        goto L_0x0028;
    L_0x04b9:
        r27 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r0 = r33;
        r1 = r27;
        r27 = r0.ReadResponse(r1);
        if (r27 == 0) goto L_0x0776;
    L_0x04c5:
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "Step_GetObjectHandleResponseSuccess";
        r29 = new java.lang.StringBuilder;
        r29.<init>();
        r30 = "m_FolderIdNum = ";
        r29 = r29.append(r30);
        r0 = r33;
        r0 = r0.m_FolderIdNum;
        r30 = r0;
        r29 = r29.append(r30);
        r29 = r29.toString();
        r27.m385i(r28, r29);
        r27 = r33.IsReadComplete();
        if (r27 == 0) goto L_0x0028;
    L_0x04ef:
        r0 = r33;
        r0 = r0.m_FolderIdNum;
        r27 = r0;
        r28 = -1;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x05b7;
    L_0x04fd:
        r0 = r33;
        r0 = r0.m_lpReadData;
        r27 = r0;
        r28 = 0;
        r29 = 4;
        r27 = com.hiti.utility.ByteConvertUtility.ByteToInt(r27, r28, r29);
        r0 = r27;
        r1 = r33;
        r1.m_FolderIdNum = r0;
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "=>m_FolderIdNum";
        r29 = new java.lang.StringBuilder;
        r29.<init>();
        r30 = "m_FolderIdNum = ";
        r29 = r29.append(r30);
        r0 = r33;
        r0 = r0.m_FolderIdNum;
        r30 = r0;
        r29 = r29.append(r30);
        r29 = r29.toString();
        r27.m385i(r28, r29);
        r0 = r33;
        r0 = r0.m_FolderIdNum;
        r27 = r0;
        if (r27 != 0) goto L_0x0558;
    L_0x053d:
        r27 = 0;
        r0 = r27;
        r1 = r33;
        r1.m_SIDNum = r0;
        r27 = -1;
        r0 = r27;
        r1 = r33;
        r1.m_FolderIdNum = r0;
        r27 = com.hiti.printerprotocol.SettingStep.Step_GetObjectHandleRequest;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x0558:
        r0 = r33;
        r0 = r0.m_FolderIdNum;
        r27 = r0;
        r26 = r27 * 4;
        r0 = r33;
        r0 = r0.m_lpReadData;
        r27 = r0;
        r0 = r27;
        r0 = r0.length;
        r27 = r0;
        r11 = r27 / 2;
        r0 = r26;
        if (r0 > r11) goto L_0x0596;
    L_0x0571:
        r0 = r26;
        r1 = r33;
        r1.m_iRead = r0;
        r27 = 0;
        r0 = r27;
        r1 = r33;
        r1.m_readNum = r0;
    L_0x057f:
        r0 = r33;
        r0 = r0.m_iRead;
        r27 = r0;
        r28 = 0;
        r29 = 0;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r3 = r29;
        r0.DecideNextStepAndPrepareReadBuff(r1, r2, r3);
        goto L_0x0028;
    L_0x0596:
        r0 = r33;
        r0.m_iRead = r11;
        r0 = r33;
        r0 = r0.m_iRead;
        r27 = r0;
        r27 = r26 / r27;
        r0 = r27;
        r1 = r33;
        r1.m_readNum = r0;
        r0 = r33;
        r0 = r0.m_iRead;
        r27 = r0;
        r27 = r26 % r27;
        r0 = r27;
        r1 = r33;
        r1.m_iLastRead = r0;
        goto L_0x057f;
    L_0x05b7:
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "FolderId num=";
        r0 = r33;
        r0 = r0.m_FolderIdNum;
        r29 = r0;
        r29 = java.lang.String.valueOf(r29);
        r27.m385i(r28, r29);
        r12 = 0;
    L_0x05cd:
        r0 = r33;
        r0 = r0.m_FolderIdNum;
        r27 = r0;
        r0 = r27;
        if (r12 >= r0) goto L_0x066c;
    L_0x05d7:
        r27 = 4;
        r0 = r27;
        r4 = new byte[r0];
        r17 = 0;
    L_0x05df:
        r27 = 4;
        r0 = r17;
        r1 = r27;
        if (r0 >= r1) goto L_0x05f8;
    L_0x05e7:
        r0 = r33;
        r0 = r0.m_lpReadData;
        r27 = r0;
        r28 = r12 * 4;
        r28 = r28 + r17;
        r27 = r27[r28];
        r4[r17] = r27;
        r17 = r17 + 1;
        goto L_0x05df;
    L_0x05f8:
        r0 = r33;
        r0 = r0.m_FolderIdsList;
        r27 = r0;
        r0 = r27;
        r0.add(r4);
        r0 = r33;
        r0 = r0.m_FolderSIDsList;
        r27 = r0;
        r0 = r33;
        r0 = r0.m_NowSID;
        r28 = r0;
        r27.add(r28);
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "FolderId =";
        r29 = new java.lang.StringBuilder;
        r29.<init>();
        r30 = "";
        r29 = r29.append(r30);
        r30 = 0;
        r31 = 4;
        r0 = r30;
        r1 = r31;
        r30 = com.hiti.utility.ByteConvertUtility.ByteToInt(r4, r0, r1);
        r29 = r29.append(r30);
        r29 = r29.toString();
        r27.m385i(r28, r29);
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "m_NowSID=";
        r29 = new java.lang.StringBuilder;
        r29.<init>();
        r30 = "";
        r29 = r29.append(r30);
        r0 = r33;
        r0 = r0.m_NowSID;
        r30 = r0;
        r31 = 0;
        r32 = 4;
        r30 = com.hiti.utility.ByteConvertUtility.ByteToInt(r30, r31, r32);
        r29 = r29.append(r30);
        r29 = r29.toString();
        r27.m385i(r28, r29);
        r12 = r12 + 1;
        goto L_0x05cd;
    L_0x066c:
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "m_FolderIdsList=";
        r0 = r33;
        r0 = r0.m_FolderIdsList;
        r29 = r0;
        r29 = java.lang.String.valueOf(r29);
        r27.m385i(r28, r29);
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "m_readNum=";
        r0 = r33;
        r0 = r0.m_readNum;
        r29 = r0;
        r29 = java.lang.String.valueOf(r29);
        r27.m385i(r28, r29);
        r0 = r33;
        r0 = r0.m_readNum;
        r27 = r0;
        if (r27 != 0) goto L_0x06d9;
    L_0x069e:
        r27 = com.hiti.printerprotocol.SettingStep.Step_GetObjectHandleRequest;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        r27 = 0;
        r0 = r27;
        r1 = r33;
        r1.m_SIDNum = r0;
        r27 = -1;
        r0 = r27;
        r1 = r33;
        r1.m_FolderIdNum = r0;
        r27 = 1;
        r0 = r27;
        r1 = r33;
        r1.m_cntPhoto = r0;
        r27 = -1;
        r0 = r27;
        r1 = r33;
        r1.m_iRead = r0;
        r27 = -1;
        r0 = r27;
        r1 = r33;
        r1.m_iLastRead = r0;
        r27 = 0;
        r0 = r27;
        r1 = r33;
        r1.m_readNum = r0;
        goto L_0x0028;
    L_0x06d9:
        r0 = r33;
        r0 = r0.m_readNum;
        r27 = r0;
        r28 = 1;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x0712;
    L_0x06e7:
        r0 = r33;
        r0 = r0.m_iLastRead;
        r27 = r0;
        r0 = r27;
        r1 = r33;
        r1.m_iRead = r0;
        r0 = r33;
        r0 = r0.m_iRead;
        r27 = r0;
        r28 = 0;
        r29 = 0;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r3 = r29;
        r0.DecideNextStepAndPrepareReadBuff(r1, r2, r3);
        r27 = 0;
        r0 = r27;
        r1 = r33;
        r1.m_readNum = r0;
        goto L_0x0028;
    L_0x0712:
        r0 = r33;
        r0 = r0.m_cntPhoto;
        r27 = r0;
        r27 = r27 + 1;
        r0 = r27;
        r1 = r33;
        r1.m_cntPhoto = r0;
        r0 = r33;
        r0 = r0.m_cntPhoto;
        r27 = r0;
        r0 = r33;
        r0 = r0.m_readNum;
        r28 = r0;
        r28 = r28 + 1;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x075f;
    L_0x0734:
        r0 = r33;
        r0 = r0.m_iLastRead;
        r27 = r0;
        r0 = r27;
        r1 = r33;
        r1.m_iRead = r0;
        r0 = r33;
        r0 = r0.m_iRead;
        r27 = r0;
        r28 = 0;
        r29 = 0;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r3 = r29;
        r0.DecideNextStepAndPrepareReadBuff(r1, r2, r3);
        r27 = 0;
        r0 = r27;
        r1 = r33;
        r1.m_readNum = r0;
        goto L_0x0028;
    L_0x075f:
        r0 = r33;
        r0 = r0.m_iRead;
        r27 = r0;
        r28 = 0;
        r29 = 0;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r3 = r29;
        r0.DecideNextStepAndPrepareReadBuff(r1, r2, r3);
        goto L_0x0028;
    L_0x0776:
        r33.DecideErrorStatus();
        goto L_0x0028;
    L_0x077b:
        r0 = r33;
        r0 = r0.m_FolderIdNum;
        r27 = r0;
        r0 = r33;
        r0 = r0.m_FolderIdsList;
        r28 = r0;
        r28 = r28.size();
        r28 = r28 + -1;
        r0 = r27;
        r1 = r28;
        if (r0 >= r1) goto L_0x084b;
    L_0x0793:
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "Check";
        r0 = r33;
        r0 = r0.m_strPrintOutScaleList;
        r29 = r0;
        r30 = 0;
        r29 = r29.get(r30);
        r29 = java.lang.String.valueOf(r29);
        r27.m385i(r28, r29);
        r0 = r33;
        r0 = r0.m_FolderIdsList;
        r27 = r0;
        r0 = r33;
        r0 = r0.m_FolderIdNum;
        r28 = r0;
        r28 = r28 + 1;
        r0 = r28;
        r1 = r33;
        r1.m_FolderIdNum = r0;
        r27 = r27.get(r28);
        r27 = (byte[]) r27;
        r0 = r27;
        r1 = r33;
        r1.m_FolderID = r0;
        r0 = r33;
        r0 = r0.m_FolderSIDsList;
        r27 = r0;
        r0 = r33;
        r0 = r0.m_FolderIdNum;
        r28 = r0;
        r27 = r27.get(r28);
        r27 = (byte[]) r27;
        r0 = r27;
        r1 = r33;
        r1.m_FolderSID = r0;
        r0 = r33;
        r0 = r0.m_FolderID;
        r27 = r0;
        r28 = 0;
        r29 = 4;
        r13 = com.hiti.utility.ByteConvertUtility.ByteToInt(r27, r28, r29);
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "Step_iFolderId";
        r29 = java.lang.String.valueOf(r13);
        r27.m385i(r28, r29);
        r27 = -1;
        r0 = r27;
        if (r13 != r0) goto L_0x0924;
    L_0x0809:
        r0 = r33;
        r0 = r0.m_Context;
        r27 = r0;
        r28 = 2131099924; // 0x7f060114 float:1.7812215E38 double:1.0529032603E-314;
        r27 = r27.getString(r28);
        r0 = r27;
        r1 = r33;
        r1.m_strFolderName = r0;
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "Step_id";
        r29 = java.lang.String.valueOf(r13);
        r27.m385i(r28, r29);
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "Step_folder_name";
        r0 = r33;
        r0 = r0.m_strFolderName;
        r29 = r0;
        r29 = java.lang.String.valueOf(r29);
        r27.m385i(r28, r29);
        r27 = com.hiti.printerprotocol.SettingStep.Step_GetObjectInfoRequest;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x084b:
        r0 = r33;
        r0 = r0.m_strBorderCheckDoneList;
        r27 = r0;
        r27 = r27.size();
        if (r27 == 0) goto L_0x086b;
    L_0x0857:
        r27 = 1;
        r0 = r33;
        r1 = r27;
        r0.InitialParamsOfFolder(r1);
        r27 = com.hiti.printerprotocol.SettingStep.Step_GetImageRequest;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x086b:
        r0 = r33;
        r0 = r0.LOG;
        r28 = r0;
        r27 = new java.lang.StringBuilder;
        r27.<init>();
        r29 = "Pass_";
        r0 = r27;
        r1 = r29;
        r29 = r0.append(r1);
        r0 = r33;
        r0 = r0.m_strPrintOutScaleList;
        r27 = r0;
        r30 = 0;
        r0 = r27;
        r1 = r30;
        r27 = r0.get(r1);
        r27 = (java.lang.String) r27;
        r0 = r29;
        r1 = r27;
        r27 = r0.append(r1);
        r27 = r27.toString();
        r29 = "Next~";
        r0 = r28;
        r1 = r27;
        r2 = r29;
        r0.m385i(r1, r2);
        r0 = r33;
        r0 = r0.m_strPrintOutScaleList;
        r27 = r0;
        r28 = 0;
        r27.remove(r28);
        r0 = r33;
        r0 = r0.m_strPrintOutScaleList;
        r27 = r0;
        r27 = r27.isEmpty();
        if (r27 == 0) goto L_0x08ff;
    L_0x08c0:
        r0 = r33;
        r0 = r0.m_bOnceGetDone;
        r27 = r0;
        if (r27 != 0) goto L_0x08f4;
    L_0x08c8:
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "Step_GetObjectInfoRequest";
        r29 = "NO_MATCH_BORDER_NAME";
        r27.m385i(r28, r29);
        r0 = r33;
        r0 = r0.m_Context;
        r27 = r0;
        r28 = 2131099868; // 0x7f0600dc float:1.7812101E38 double:1.0529032326E-314;
        r27 = r27.getString(r28);
        r0 = r33;
        r1 = r27;
        r0.SetErrorMSG(r1);
        r27 = com.hiti.printerprotocol.SettingStep.Step_Error;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x08f4:
        r27 = com.hiti.printerprotocol.SettingStep.Step_Complete;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x08ff:
        r27 = 0;
        r0 = r27;
        r1 = r33;
        r1.m_SIDNum = r0;
        r0 = r33;
        r0 = r0.m_RootFolderId;
        r27 = r0;
        r0 = r27;
        r1 = r33;
        r1.m_parentObjH = r0;
        r27 = r33.InitialData();
        if (r27 == 0) goto L_0x0028;
    L_0x0919:
        r27 = com.hiti.printerprotocol.SettingStep.Step_GetObjectHandleRequest;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x0924:
        r0 = r33;
        r0 = r0.m_FolderSID;
        r27 = r0;
        r0 = r33;
        r0 = r0.m_FolderID;
        r28 = r0;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r27 = r0.Get_Object_Info_Request(r1, r2);
        if (r27 == 0) goto L_0x094f;
    L_0x093c:
        r27 = 7;
        r28 = 0;
        r29 = com.hiti.printerprotocol.SettingStep.Step_GetObjectInfoResponse;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r3 = r29;
        r0.DecideNextStepAndPrepareReadBuff(r1, r2, r3);
        goto L_0x0028;
    L_0x094f:
        r27 = com.hiti.printerprotocol.SettingStep.Step_Error;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x095a:
        r27 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r0 = r33;
        r1 = r27;
        r27 = r0.ReadResponse(r1);
        if (r27 == 0) goto L_0x0990;
    L_0x0966:
        r27 = r33.IsReadComplete();
        if (r27 == 0) goto L_0x0028;
    L_0x096c:
        r27 = r33.CheckCommandSuccess();
        if (r27 == 0) goto L_0x0985;
    L_0x0972:
        r27 = 4;
        r28 = 0;
        r29 = com.hiti.printerprotocol.SettingStep.Step_GetObjectInfoResponseSuccess;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r3 = r29;
        r0.DecideNextStepAndPrepareReadBuff(r1, r2, r3);
        goto L_0x0028;
    L_0x0985:
        r27 = com.hiti.printerprotocol.SettingStep.Step_Error;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x0990:
        r33.DecideErrorStatus();
        goto L_0x0028;
    L_0x0995:
        r27 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r0 = r33;
        r1 = r27;
        r27 = r0.ReadResponse(r1);
        if (r27 == 0) goto L_0x0ae2;
    L_0x09a1:
        r27 = r33.IsReadComplete();
        if (r27 == 0) goto L_0x0028;
    L_0x09a7:
        r0 = r33;
        r0 = r0.m_LenOfFolderName;
        r27 = r0;
        r28 = -1;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x09e2;
    L_0x09b5:
        r0 = r33;
        r0 = r0.m_lpReadData;
        r27 = r0;
        r28 = 0;
        r29 = 4;
        r27 = com.hiti.utility.ByteConvertUtility.ByteToInt(r27, r28, r29);
        r27 = r27 + -4;
        r0 = r27;
        r1 = r33;
        r1.m_LenOfFolderName = r0;
        r0 = r33;
        r0 = r0.m_LenOfFolderName;
        r27 = r0;
        r28 = 0;
        r29 = 0;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r3 = r29;
        r0.DecideNextStepAndPrepareReadBuff(r1, r2, r3);
        goto L_0x0028;
    L_0x09e2:
        r0 = r33;
        r0 = r0.m_strFolderName;
        r27 = r0;
        if (r27 != 0) goto L_0x0028;
    L_0x09ea:
        r0 = r33;
        r0 = r0.m_lpReadData;
        r27 = r0;
        r28 = 2;
        r27 = r27[r28];
        r0 = r27;
        r1 = r33;
        r1.m_AttrFolderNameEncode = r0;
        r0 = r33;
        r0 = r0.m_AttrFolderNameEncode;
        r27 = r0;
        r28 = 16;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x0aab;
    L_0x0a08:
        r27 = "UNICODE";
        r0 = r27;
        r1 = r33;
        r1.m_EncodeType = r0;
    L_0x0a10:
        r0 = r33;
        r0 = r0.m_LenOfFolderName;	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r27 = r0;
        r27 = r27 + -32;
        r0 = r27;
        r6 = new byte[r0];	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r0 = r33;
        r0 = r0.m_EncodeType;	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r27 = r0;
        r28 = "UNICODE";
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x0ab5;
    L_0x0a2a:
        r0 = r33;
        r0 = r0.m_lpReadData;	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r27 = r0;
        r28 = 32;
        r0 = r33;
        r0 = r0.m_LenOfFolderName;	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r29 = r0;
        r29 = r29 + -32;
        r0 = r27;
        r1 = r28;
        r2 = r29;
        com.hiti.utility.ByteConvertUtility.EdienChange(r0, r1, r2, r6);	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r27 = new java.lang.String;	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r0 = r33;
        r0 = r0.m_EncodeType;	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r28 = r0;
        r0 = r27;
        r1 = r28;
        r0.<init>(r6, r1);	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r0 = r27;
        r1 = r33;
        r1.m_strFolderName = r0;	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r0 = r33;
        r0 = r0.m_strFolderName;	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r27 = r0;
        r28 = 0;
        r0 = r33;
        r0 = r0.m_strFolderName;	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r29 = r0;
        r29 = r29.length();	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r29 = r29 + -1;
        r27 = r27.substring(r28, r29);	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r0 = r27;
        r1 = r33;
        r1.m_strFolderName = r0;	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
    L_0x0a76:
        r0 = r33;
        r0 = r0.LOG;	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r27 = r0;
        r28 = "Get SD card FolderName";
        r0 = r33;
        r0 = r0.m_strFolderName;	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r29 = r0;
        r29 = java.lang.String.valueOf(r29);	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r27.m385i(r28, r29);	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r0 = r33;
        r0 = r0.m_strPrintOutScaleList;	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r27 = r0;
        r28 = 0;
        r27 = r27.get(r28);	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r27 = (java.lang.String) r27;	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r0 = r33;
        r1 = r27;
        r0.DetectFolderName(r1);	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        goto L_0x0028;
    L_0x0aa2:
        r8 = move-exception;
        r8.printStackTrace();
        r33.DecideErrorStatus();
        goto L_0x0028;
    L_0x0aab:
        r27 = "ASCII";
        r0 = r27;
        r1 = r33;
        r1.m_EncodeType = r0;
        goto L_0x0a10;
    L_0x0ab5:
        r12 = 0;
    L_0x0ab6:
        r0 = r6.length;	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r27 = r0;
        r0 = r27;
        if (r12 >= r0) goto L_0x0acc;
    L_0x0abd:
        r0 = r33;
        r0 = r0.m_lpReadData;	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r27 = r0;
        r28 = r12 + 32;
        r27 = r27[r28];	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r6[r12] = r27;	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r12 = r12 + 1;
        goto L_0x0ab6;
    L_0x0acc:
        r27 = new java.lang.String;	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r0 = r33;
        r0 = r0.m_EncodeType;	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r28 = r0;
        r0 = r27;
        r1 = r28;
        r0.<init>(r6, r1);	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        r0 = r27;
        r1 = r33;
        r1.m_strFolderName = r0;	 Catch:{ UnsupportedEncodingException -> 0x0aa2 }
        goto L_0x0a76;
    L_0x0ae2:
        r33.DecideErrorStatus();
        goto L_0x0028;
    L_0x0ae7:
        r0 = r33;
        r0 = r0.LOG;
        r28 = r0;
        r29 = "Step_GetImageRequest";
        r27 = new java.lang.StringBuilder;
        r27.<init>();
        r30 = "borderId=";
        r0 = r27;
        r1 = r30;
        r30 = r0.append(r1);
        r0 = r33;
        r0 = r0.m_byBorderIdsList;
        r27 = r0;
        r31 = 0;
        r0 = r27;
        r1 = r31;
        r27 = r0.get(r1);
        r27 = (byte[]) r27;
        r31 = 0;
        r32 = 4;
        r0 = r27;
        r1 = r31;
        r2 = r32;
        r27 = com.hiti.utility.ByteConvertUtility.ByteToInt(r0, r1, r2);
        r0 = r30;
        r1 = r27;
        r27 = r0.append(r1);
        r27 = r27.toString();
        r0 = r28;
        r1 = r29;
        r2 = r27;
        r0.m385i(r1, r2);
        r0 = r33;
        r0 = r0.m_NowSID;
        r28 = r0;
        r0 = r33;
        r0 = r0.m_byBorderIdsList;
        r27 = r0;
        r29 = 0;
        r0 = r27;
        r1 = r29;
        r27 = r0.get(r1);
        r27 = (byte[]) r27;
        r0 = r33;
        r1 = r28;
        r2 = r27;
        r27 = r0.Get_Object_Request(r1, r2);
        if (r27 == 0) goto L_0x0b6a;
    L_0x0b57:
        r27 = 7;
        r28 = 0;
        r29 = com.hiti.printerprotocol.SettingStep.Step_GetImageResponse;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r3 = r29;
        r0.DecideNextStepAndPrepareReadBuff(r1, r2, r3);
        goto L_0x0028;
    L_0x0b6a:
        r27 = com.hiti.printerprotocol.SettingStep.Step_Error;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x0b75:
        r27 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r0 = r33;
        r1 = r27;
        r27 = r0.ReadResponse(r1);
        if (r27 == 0) goto L_0x0bf0;
    L_0x0b81:
        r27 = r33.IsReadComplete();
        if (r27 == 0) goto L_0x0028;
    L_0x0b87:
        r27 = r33.CheckCommandSuccess();
        if (r27 == 0) goto L_0x0ba0;
    L_0x0b8d:
        r27 = 4;
        r28 = 0;
        r29 = com.hiti.printerprotocol.SettingStep.Step_GetImageResponseSuccess;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r3 = r29;
        r0.DecideNextStepAndPrepareReadBuff(r1, r2, r3);
        goto L_0x0028;
    L_0x0ba0:
        r0 = r33;
        r0 = r0.m_lpReadData;
        r27 = r0;
        r28 = 2;
        r27 = r27[r28];
        r28 = 70;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x0be5;
    L_0x0bb2:
        r0 = r33;
        r0 = r0.m_lpReadData;
        r27 = r0;
        r28 = 3;
        r27 = r27[r28];
        r28 = 4;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x0be5;
    L_0x0bc4:
        r0 = r33;
        r0 = r0.m_Context;
        r27 = r0;
        r28 = 2131099864; // 0x7f0600d8 float:1.7812093E38 double:1.0529032307E-314;
        r27 = r27.getString(r28);
        r0 = r33;
        r1 = r27;
        r0.SetErrorMSG(r1);
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "Step_GetImageResponse";
        r29 = "No Border Image";
        r27.m385i(r28, r29);
    L_0x0be5:
        r27 = com.hiti.printerprotocol.SettingStep.Step_Error;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x0bf0:
        r33.DecideErrorStatus();
        goto L_0x0028;
    L_0x0bf5:
        r27 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r0 = r27;
        r0 = new byte[r0];
        r27 = r0;
        r0 = r27;
        r1 = r33;
        r1.m_lpReadData = r0;
        r27 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r0 = r33;
        r1 = r27;
        r27 = r0.ReadResponse(r1);
        if (r27 == 0) goto L_0x0e5e;
    L_0x0c0f:
        r27 = r33.IsReadComplete();
        if (r27 == 0) goto L_0x0028;
    L_0x0c15:
        r9 = 0;
        r0 = r33;
        r0 = r0.m_lpReadData;
        r27 = r0;
        r28 = 0;
        r29 = 4;
        r22 = com.hiti.utility.ByteConvertUtility.ByteToInt(r27, r28, r29);
        r0 = r33;
        r0 = r0.m_strRootPath;
        r27 = r0;
        if (r27 != 0) goto L_0x0c6b;
    L_0x0c2c:
        r27 = new java.lang.StringBuilder;
        r27.<init>();
        r0 = r33;
        r0 = r0.m_Context;
        r28 = r0;
        r29 = 0;
        r28 = r28.getExternalFilesDir(r29);
        r28 = r28.getAbsolutePath();
        r27 = r27.append(r28);
        r28 = "/";
        r27 = r27.append(r28);
        r28 = "IMG_PRINGO";
        r27 = r27.append(r28);
        r27 = r27.toString();
        r0 = r27;
        r1 = r33;
        r1.m_strRootPath = r0;
    L_0x0c5b:
        r0 = r33;
        r0 = r0.m_strRootPath;
        r27 = r0;
        r0 = r33;
        r1 = r27;
        r27 = r0.CreateFolderForBorder(r1);
        if (r27 == 0) goto L_0x0c5b;
    L_0x0c6b:
        r28 = new java.lang.StringBuilder;
        r28.<init>();
        r0 = r33;
        r0 = r0.m_strBorderCheckDoneList;
        r27 = r0;
        r29 = 0;
        r0 = r27;
        r1 = r29;
        r27 = r0.get(r1);
        r27 = (java.lang.String) r27;
        r0 = r28;
        r1 = r27;
        r27 = r0.append(r1);
        r28 = ".png";
        r27 = r27.append(r28);
        r19 = r27.toString();
        r27 = new java.lang.StringBuilder;
        r27.<init>();
        r0 = r33;
        r0 = r0.m_strRootPath;
        r28 = r0;
        r27 = r27.append(r28);
        r28 = "/";
        r27 = r27.append(r28);
        r0 = r27;
        r1 = r19;
        r27 = r0.append(r1);
        r24 = r27.toString();
        r0 = r33;
        r0 = r0.m_strImportedBorderPath;
        r27 = r0;
        if (r27 != 0) goto L_0x0cc8;
    L_0x0cbd:
        r27 = new java.util.ArrayList;
        r27.<init>();
        r0 = r27;
        r1 = r33;
        r1.m_strImportedBorderPath = r0;
    L_0x0cc8:
        r0 = r33;
        r0 = r0.m_strImportedBorderPath;
        r27 = r0;
        r0 = r27;
        r1 = r24;
        r0.add(r1);
        r10 = new java.io.FileOutputStream;	 Catch:{ FileNotFoundException -> 0x0ec5, IOException -> 0x0ec2 }
        r27 = 0;
        r0 = r24;
        r1 = r27;
        r10.<init>(r0, r1);	 Catch:{ FileNotFoundException -> 0x0ec5, IOException -> 0x0ec2 }
        r15 = 0;
        r16 = 0;
        r5 = 1;
    L_0x0ce4:
        if (r5 != 0) goto L_0x0cec;
    L_0x0ce6:
        r27 = r33.IsReadComplete();	 Catch:{ FileNotFoundException -> 0x0de2, IOException -> 0x0df3 }
        if (r27 != 0) goto L_0x0cf2;
    L_0x0cec:
        r27 = r33.IsRunning();	 Catch:{ FileNotFoundException -> 0x0de2, IOException -> 0x0df3 }
        if (r27 != 0) goto L_0x0d8d;
    L_0x0cf2:
        r10.flush();	 Catch:{ FileNotFoundException -> 0x0de2, IOException -> 0x0df3 }
        r10.close();	 Catch:{ FileNotFoundException -> 0x0de2, IOException -> 0x0df3 }
        r9 = r10;
    L_0x0cf9:
        r0 = r33;
        r0 = r0.m_iSumOfCheckDoneBorder;
        r27 = r0;
        r28 = -1;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x0d17;
    L_0x0d07:
        r0 = r33;
        r0 = r0.m_byBorderIdsList;
        r27 = r0;
        r27 = r27.size();
        r0 = r27;
        r1 = r33;
        r1.m_iSumOfCheckDoneBorder = r0;
    L_0x0d17:
        r0 = r33;
        r0 = r0.m_byBorderIdsList;
        r27 = r0;
        r28 = 0;
        r27.remove(r28);
        r0 = r33;
        r0 = r0.m_strBorderCheckDoneList;
        r27 = r0;
        r28 = 0;
        r27.remove(r28);
        r0 = r33;
        r0 = r0.m_iSumOfCheckDoneBorder;
        r27 = r0;
        if (r27 <= 0) goto L_0x0d76;
    L_0x0d35:
        r0 = r33;
        r0 = r0.m_iSumOfCheckDoneBorder;
        r27 = r0;
        r0 = r33;
        r0 = r0.m_byBorderIdsList;
        r28 = r0;
        r28 = r28.size();
        r27 = r27 - r28;
        r0 = r27;
        r7 = (float) r0;
        r20 = 1117126656; // 0x42960000 float:75.0 double:5.51933903E-315;
        r27 = 0;
        r27 = (r7 > r27 ? 1 : (r7 == r27 ? 0 : -1));
        if (r27 <= 0) goto L_0x0d67;
    L_0x0d52:
        r0 = r33;
        r0 = r0.m_iSumOfCheckDoneBorder;
        r27 = r0;
        r0 = r27;
        r0 = (float) r0;
        r27 = r0;
        r27 = r7 / r27;
        r28 = 1103626240; // 0x41c80000 float:25.0 double:5.45263811E-315;
        r27 = r27 * r28;
        r28 = 1117126656; // 0x42960000 float:75.0 double:5.51933903E-315;
        r20 = r27 + r28;
    L_0x0d67:
        r27 = 369; // 0x171 float:5.17E-43 double:1.823E-321;
        r28 = java.lang.String.valueOf(r20);
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r0.SendMessage(r1, r2);
    L_0x0d76:
        r0 = r33;
        r0 = r0.m_byBorderIdsList;
        r27 = r0;
        r27 = r27.isEmpty();
        if (r27 != 0) goto L_0x0dfa;
    L_0x0d82:
        r27 = com.hiti.printerprotocol.SettingStep.Step_GetImageRequest;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x0d8d:
        r27 = r33.IsReadComplete();	 Catch:{ FileNotFoundException -> 0x0de2, IOException -> 0x0df3 }
        if (r27 == 0) goto L_0x0de9;
    L_0x0d93:
        r27 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        r28 = 0;
        r29 = 0;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r3 = r29;
        r0.DecideNextStepAndPrepareReadBuff(r1, r2, r3);	 Catch:{ FileNotFoundException -> 0x0de2, IOException -> 0x0df3 }
        r15 = r15 + 500;
        r0 = r22;
        if (r15 < r0) goto L_0x0dbd;
    L_0x0daa:
        r15 = r15 + -500;
        r14 = r22 - r15;
        r27 = 0;
        r28 = 0;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r0.DecideNextStepAndPrepareReadBuff(r14, r1, r2);	 Catch:{ FileNotFoundException -> 0x0de2, IOException -> 0x0df3 }
        r5 = 0;
        r15 = r15 + r14;
    L_0x0dbd:
        r16 = 0;
    L_0x0dbf:
        r27 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r0 = r33;
        r1 = r27;
        r27 = r0.ReadResponse(r1);	 Catch:{ FileNotFoundException -> 0x0de2, IOException -> 0x0df3 }
        if (r27 == 0) goto L_0x0dee;
    L_0x0dcb:
        r27 = r33.GetOffsetRead();	 Catch:{ FileNotFoundException -> 0x0de2, IOException -> 0x0df3 }
        r21 = r27 - r16;
        r0 = r33;
        r0 = r0.m_lpReadData;	 Catch:{ FileNotFoundException -> 0x0de2, IOException -> 0x0df3 }
        r27 = r0;
        r0 = r27;
        r1 = r16;
        r2 = r21;
        r10.write(r0, r1, r2);	 Catch:{ FileNotFoundException -> 0x0de2, IOException -> 0x0df3 }
        goto L_0x0ce4;
    L_0x0de2:
        r8 = move-exception;
        r9 = r10;
    L_0x0de4:
        r8.printStackTrace();
        goto L_0x0cf9;
    L_0x0de9:
        r16 = r33.GetOffsetRead();	 Catch:{ FileNotFoundException -> 0x0de2, IOException -> 0x0df3 }
        goto L_0x0dbf;
    L_0x0dee:
        r33.DecideErrorStatus();	 Catch:{ FileNotFoundException -> 0x0de2, IOException -> 0x0df3 }
        goto L_0x0ce4;
    L_0x0df3:
        r8 = move-exception;
        r9 = r10;
    L_0x0df5:
        r8.printStackTrace();
        goto L_0x0cf9;
    L_0x0dfa:
        r27 = 1;
        r0 = r27;
        r1 = r33;
        r1.m_bOnceGetDone = r0;
        r0 = r33;
        r0 = r0.LOG;
        r27 = r0;
        r28 = "GetImageDone";
        r0 = r33;
        r0 = r0.m_bOnceGetDone;
        r29 = r0;
        r29 = java.lang.String.valueOf(r29);
        r27.m385i(r28, r29);
        r0 = r33;
        r0 = r0.m_strPrintOutScaleList;
        r27 = r0;
        r28 = 0;
        r27.remove(r28);
        r0 = r33;
        r0 = r0.m_strPrintOutScaleList;
        r27 = r0;
        r27 = r27.isEmpty();
        if (r27 == 0) goto L_0x0e39;
    L_0x0e2e:
        r27 = com.hiti.printerprotocol.SettingStep.Step_Complete;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x0e39:
        r27 = 0;
        r0 = r27;
        r1 = r33;
        r1.m_SIDNum = r0;
        r0 = r33;
        r0 = r0.m_RootFolderId;
        r27 = r0;
        r0 = r27;
        r1 = r33;
        r1.m_parentObjH = r0;
        r27 = r33.InitialData();
        if (r27 == 0) goto L_0x0028;
    L_0x0e53:
        r27 = com.hiti.printerprotocol.SettingStep.Step_GetObjectHandleRequest;
        r0 = r33;
        r1 = r27;
        r0.DecideNextStep(r1);
        goto L_0x0028;
    L_0x0e5e:
        r33.DecideErrorStatus();
        goto L_0x0028;
    L_0x0e63:
        r33.StopTimerOut();
        r25 = r33.GetErrorMSGConnectFail();
        r0 = r33;
        r0 = r0.m_ErrorString;
        r27 = r0;
        if (r27 == 0) goto L_0x0e78;
    L_0x0e72:
        r0 = r33;
        r0 = r0.m_ErrorString;
        r25 = r0;
    L_0x0e78:
        r27 = 365; // 0x16d float:5.11E-43 double:1.803E-321;
        r0 = r33;
        r1 = r27;
        r2 = r25;
        r0.SendMessage(r1, r2);
        r33.Stop();
        goto L_0x004c;
    L_0x0e88:
        r27 = r33.IsTimeoutError();
        if (r27 == 0) goto L_0x004c;
    L_0x0e8e:
        r0 = r33;
        r0 = r0.m_boSilent;
        r27 = r0;
        r28 = 1;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x0eae;
    L_0x0e9c:
        r27 = 365; // 0x16d float:5.11E-43 double:1.803E-321;
        r28 = 0;
        r0 = r33;
        r1 = r27;
        r2 = r28;
        r0.SendMessage(r1, r2);
        r33.Stop();
        goto L_0x004c;
    L_0x0eae:
        r25 = r33.GetErrorMSGTimeOut();
        r27 = 312; // 0x138 float:4.37E-43 double:1.54E-321;
        r0 = r33;
        r1 = r27;
        r2 = r25;
        r0.SendMessage(r1, r2);
        r33.Stop();
        goto L_0x004c;
    L_0x0ec2:
        r8 = move-exception;
        goto L_0x0df5;
    L_0x0ec5:
        r8 = move-exception;
        goto L_0x0de4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hiti.printerprotocol.request.HitiPPR_GetBorderFile.GetBorderFile():void");
    }

    private boolean InitialData() {
        this.m_iDetectStep = V_THUMB_FOLDER;
        this.m_iFolderRange = V_FILENAME_FOLDER;
        InitialParamsOfFolder(V_THUMB_FOLDER);
        InitialParamsOfFolder(V_FILENAME_FOLDER);
        this.m_strThumbBdPathList.clear();
        this.m_byThumbBdIdsList.clear();
        this.m_strBorderNameList.clear();
        this.m_byBorderIdsList.clear();
        this.m_FolderIdsList.clear();
        this.m_strFolderName = null;
        this.m_strFileNameFolder = null;
        this.m_strFileFolderList.clear();
        this.m_strBorderCheckDoneList.clear();
        this.m_strBorderName = null;
        this.m_strRootPath = null;
        return true;
    }

    private void DetectFolderName(String printOutF) {
        this.LOG.m385i("DetectFolderName", "m_iDetectStep=" + this.m_iDetectStep);
        switch (this.m_iDetectStep) {
            case V_THUMB_FOLDER /*1*/:
                RangeStepByStep(printOutF, "vborder", PringoConvenientConst.THUMB, "tbc_");
            case V_FILENAME_FOLDER /*2*/:
                if (this.m_strBorderNameList.size() > 0 && this.m_thumbLen > 0) {
                    this.m_tbCnt = (float) ((this.m_thumbLen - this.m_strBorderNameList.size()) + V_THUMB_FOLDER);
                    this.m_fRatioF = (this.m_tbCnt - 1.0f) / ((float) this.m_thumbLen);
                    this.m_fRatioF = (this.m_fRatioF * 45.0f) + (((float) (45 / this.m_thumbLen)) * 0.0f);
                    SendMessage(RequestState.REQUEST_COMPARE_BORDER_NAME, String.valueOf(this.m_fRatioF + 30.0f));
                }
                RangeStepByStep(printOutF, "vborder", this.m_strBorderName, "pbc_");
            case RANGE_BORDER /*3*/:
                if (this.m_strBorderNameList.size() > 0 && this.m_thumbLen > 0) {
                    this.m_tbCnt = (float) ((this.m_thumbLen - this.m_strBorderNameList.size()) + V_THUMB_FOLDER);
                    this.m_fRatioF = (this.m_tbCnt - 1.0f) / ((float) this.m_thumbLen);
                    this.m_fRatioF = (this.m_fRatioF * 45.0f) + (((float) (45 / this.m_thumbLen)) * 0.0f);
                    SendMessage(RequestState.REQUEST_COMPARE_BORDER_NAME, String.valueOf(this.m_fRatioF + 30.0f));
                }
                RangeStepByStep(printOutF, "hborder", PringoConvenientConst.THUMB, "tbc_");
            case RANGE_FILENAME /*4*/:
                if (this.m_strBorderNameList.size() > 0 && this.m_thumbLen > 0) {
                    this.m_tbCnt = (float) ((this.m_thumbLen - this.m_strBorderNameList.size()) + V_THUMB_FOLDER);
                    this.m_fRatioF = (this.m_tbCnt - 1.0f) / ((float) this.m_thumbLen);
                    this.m_fRatioF = (this.m_fRatioF * 45.0f) + (((float) (45 / this.m_thumbLen)) * 1.0f);
                    SendMessage(RequestState.REQUEST_COMPARE_BORDER_NAME, String.valueOf(this.m_fRatioF + 30.0f));
                }
                RangeStepByStep(printOutF, "hborder", this.m_strBorderName, "pbc_");
            default:
        }
    }

    private void RangeStepByStep(String printOutF, String borderF, String fileF, String preFileName) {
        this.LOG.m385i("RangeStepByStep", "m_iFolderRange=" + this.m_iFolderRange);
        switch (this.m_iFolderRange) {
            case V_THUMB_FOLDER /*1*/:
                CompareFolderName(PringoConvenientConst.ROOT_BORDER_FOLDER);
            case V_FILENAME_FOLDER /*2*/:
                SendMessage(RequestState.REQUEST_SEARCH_THUMB_PRINTOUT, printOutF);
                CompareFolderName(printOutF);
            case RANGE_BORDER /*3*/:
                if (this.m_iDetectStep == V_THUMB_FOLDER) {
                    SendMessage(RequestState.REQUEST_SEARCH_THUMB_BORDER, "10");
                }
                CompareFolderName(borderF);
            case RANGE_FILENAME /*4*/:
                if (this.m_iDetectStep == V_THUMB_FOLDER) {
                    SendMessage(RequestState.REQUEST_SEARCH_THUMB_BORDER, "20");
                }
                CompareFolderName(fileF);
            case RANGE_COLLAGE /*5*/:
                if (this.m_iDetectStep == V_THUMB_FOLDER) {
                    SendMessage(RequestState.REQUEST_SEARCH_THUMB_BORDER, "30");
                }
                CompareFileName(preFileName, printOutF + "/Common/" + borderF + "/" + fileF);
            default:
        }
    }

    private void CompareFolderName(String folderName) {
        if (this.m_strFolderName.equals(folderName)) {
            CheckNextRange();
        } else {
            DecideNextStep(SettingStep.Step_GetObjectInfoRequest);
        }
        InitialParamsOfFolder(V_FILENAME_FOLDER);
    }

    private void CheckNextRange() {
        DecideNextStep(SettingStep.Step_GetObjectHandleRequest);
        this.m_parentObjH = (byte[]) this.m_FolderID.clone();
        switch (this.m_iFolderRange) {
            case V_THUMB_FOLDER /*1*/:
                this.m_RootFolderId = (byte[]) this.m_FolderID.clone();
                break;
            case V_FILENAME_FOLDER /*2*/:
                this.m_PrintOutFolderId = (byte[]) this.m_FolderID.clone();
                break;
            case RANGE_BORDER /*3*/:
                if (this.m_iDetectStep != V_THUMB_FOLDER) {
                    if (this.m_iDetectStep == RANGE_BORDER) {
                        this.m_hBorderFolderId = (byte[]) this.m_FolderID.clone();
                        break;
                    }
                }
                this.m_vBorderFolderId = (byte[]) this.m_FolderID.clone();
                break;
                break;
            case RANGE_FILENAME /*4*/:
                this.m_strFileNameFolder = this.m_strFolderName;
                break;
        }
        this.m_iFolderRange += V_THUMB_FOLDER;
        InitialParamsOfFolder(V_THUMB_FOLDER);
    }

    private void InitialParamsOfFolder(int iCheckStep) {
        switch (iCheckStep) {
            case V_THUMB_FOLDER /*1*/:
                this.m_FolderIdNum = -1;
                this.m_SIDNum = -2;
            case V_FILENAME_FOLDER /*2*/:
                this.m_LenOfFolderName = -1;
                this.m_strFolderName = null;
                this.m_cntPhoto = V_THUMB_FOLDER;
            default:
        }
    }

    private void CompareFileName(String preFileName, String parentPath) {
        this.LOG.m385i("CompareFileName_" + preFileName, "=" + this.m_strFolderName);
        if (preFileName.equals(this.m_strFolderName.substring(0, RANGE_FILENAME))) {
            String strScanBorderName = this.m_strFolderName.substring(RANGE_FILENAME, this.m_strFolderName.lastIndexOf("."));
            if (this.m_iDetectStep == V_THUMB_FOLDER && this.m_strBorderName == null) {
                this.m_strThumbBdPathList.add(parentPath + "/" + preFileName + strScanBorderName);
                this.m_byThumbBdIdsList.add(this.m_FolderID.clone());
                this.m_strFileFolderList.add(PringoConvenientConst.THUMB);
                this.m_strBorderNameList.add(strScanBorderName);
                if (this.m_FolderIdNum == this.m_FolderIdsList.size() - 1) {
                    this.m_thumbLen = this.m_strBorderNameList.size();
                    GoToCheckFileNameFolder((String) this.m_strBorderNameList.get(0));
                } else {
                    DecideNextStep(SettingStep.Step_GetObjectInfoRequest);
                }
            } else if (this.m_iDetectStep == V_FILENAME_FOLDER || this.m_iDetectStep == RANGE_FILENAME) {
                if (this.m_strBorderName.equals(strScanBorderName)) {
                    if (this.m_iDetectStep == V_FILENAME_FOLDER) {
                        this.m_vParentPath = parentPath;
                        this.m_vFileId = (byte[]) this.m_FolderID.clone();
                        GoToHborderFileNameFolder();
                    } else if (this.m_iDetectStep == RANGE_FILENAME) {
                        String vThumbPath = (String) this.m_strThumbBdPathList.get(0);
                        String vfilePath = this.m_vParentPath + "/" + preFileName + ((String) this.m_strBorderNameList.get(0));
                        String hfilePath = parentPath + "/" + preFileName + ((String) this.m_strBorderNameList.get(0));
                        this.m_strBorderCheckDoneList.add(vThumbPath);
                        this.m_strBorderCheckDoneList.add(vfilePath);
                        this.m_strBorderCheckDoneList.add(this.m_hThumbPath);
                        this.m_strBorderCheckDoneList.add(hfilePath);
                        this.m_byBorderIdsList.add((byte[]) this.m_byThumbBdIdsList.get(0));
                        this.m_byBorderIdsList.add(this.m_vFileId);
                        this.m_byBorderIdsList.add(this.m_hThumbId);
                        this.m_byBorderIdsList.add(this.m_FolderID.clone());
                        this.m_strBorderNameList.remove(0);
                        this.m_strFileFolderList.add(this.m_strFileNameFolder);
                        this.m_strThumbBdPathList.remove(0);
                        this.m_byThumbBdIdsList.remove(0);
                        if (this.m_strBorderNameList.size() != 0) {
                            GoToCheckFileNameFolder((String) this.m_strBorderNameList.get(0));
                        } else {
                            InitialParamsOfFolder(V_THUMB_FOLDER);
                            DecideNextStep(SettingStep.Step_GetImageRequest);
                        }
                    }
                } else if (this.m_FolderIdNum == this.m_FolderIdsList.size() - 1) {
                    this.m_strThumbBdPathList.remove(0);
                    this.m_byThumbBdIdsList.remove(0);
                    this.m_strBorderNameList.remove(0);
                    GoToCheckFileNameFolder((String) this.m_strBorderNameList.get(0));
                } else {
                    DecideNextStep(SettingStep.Step_GetObjectInfoRequest);
                }
            } else if (this.m_iDetectStep != RANGE_BORDER) {
                DecideNextStep(SettingStep.Step_Error);
            } else if (strScanBorderName.equals(this.m_vBorderFileName)) {
                this.m_hThumbId = (byte[]) this.m_FolderID.clone();
                this.m_hThumbPath = parentPath + "/" + preFileName + strScanBorderName;
                this.m_strFileFolderList.add(PringoConvenientConst.THUMB);
                GoToCheckFileNameFolder((String) this.m_strBorderNameList.get(0));
            } else if (this.m_FolderIdNum == this.m_FolderIdsList.size() - 1) {
                this.m_strThumbBdPathList.remove(0);
                this.m_byThumbBdIdsList.remove(0);
                this.m_strBorderNameList.remove(0);
                GoToCheckFileNameFolder((String) this.m_strBorderNameList.get(0));
            } else {
                DecideNextStep(SettingStep.Step_GetObjectInfoRequest);
            }
        } else if (this.m_FolderIdNum != this.m_FolderIdsList.size() - 1 || this.m_strBorderNameList.isEmpty()) {
            DecideNextStep(SettingStep.Step_GetObjectInfoRequest);
        } else {
            GoToCheckFileNameFolder((String) this.m_strBorderNameList.get(0));
        }
        InitialParamsOfFolder(V_FILENAME_FOLDER);
    }

    private void GoToCheckFileNameFolder(String strBorderName) {
        this.LOG.m385i("GoToCheckFileNameFolder", "~~" + strBorderName);
        this.m_strBorderName = strBorderName;
        DecideNextStep(SettingStep.Step_GetObjectHandleRequest);
        InitialParamsOfFolder(V_THUMB_FOLDER);
        this.m_iFolderRange = RANGE_FILENAME;
        if (this.m_iDetectStep == V_THUMB_FOLDER || this.m_iDetectStep == RANGE_FILENAME) {
            this.m_parentObjH = (byte[]) this.m_vBorderFolderId.clone();
            this.m_iDetectStep = V_FILENAME_FOLDER;
            this.m_vBorderFileName = strBorderName;
        } else if (this.m_iDetectStep == RANGE_BORDER) {
            this.m_parentObjH = (byte[]) this.m_hBorderFolderId.clone();
            this.m_iDetectStep = RANGE_FILENAME;
        }
    }

    private void GoToHborderFileNameFolder() {
        this.LOG.m385i("GoToHborderFileNameFolder", "GoToHborderFileNameFolder");
        this.m_strBorderName = null;
        InitialParamsOfFolder(V_THUMB_FOLDER);
        DecideNextStep(SettingStep.Step_GetObjectHandleRequest);
        this.m_parentObjH = (byte[]) this.m_PrintOutFolderId.clone();
        this.m_iFolderRange = RANGE_BORDER;
        this.m_iDetectStep = RANGE_BORDER;
    }

    private boolean CreateFolderForBorder(String strRootPath) {
        FileUtility.CreateFolder(strRootPath);
        String strFolderPath = strRootPath + "/" + ((String) this.m_strPrintOutScaleList.get(0));
        FileUtility.CreateFolder(strFolderPath);
        strFolderPath = strFolderPath + PringoConvenientConst.DOWNLOAD_FRAME_PRE_NAME;
        FileUtility.CreateFolder(strFolderPath);
        Iterator it = this.m_strBorderFolerList.iterator();
        while (it.hasNext()) {
            String strBorderPath = strFolderPath + "/" + ((String) it.next());
            FileUtility.CreateFolder(strBorderPath);
            Iterator it2 = this.m_strFileFolderList.iterator();
            while (it2.hasNext()) {
                String strFileNamePath = strBorderPath + "/" + ((String) it2.next());
                FileUtility.CreateFolder(strFileNamePath);
                this.LOG.m385i("strFileNamePath!!", "~=" + strFileNamePath);
            }
        }
        return true;
    }

    public void GetBorderPath(ArrayList<String> strBorderPathList) {
        if (this.m_strImportedBorderPath != null) {
            Iterator it = this.m_strImportedBorderPath.iterator();
            while (it.hasNext()) {
                strBorderPathList.add((String) it.next());
            }
        }
    }
}
