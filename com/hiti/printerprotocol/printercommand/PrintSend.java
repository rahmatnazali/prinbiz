package com.hiti.printerprotocol.printercommand;

import android.content.Context;
import com.hiti.jumpinfo.JumpPreferenceKey;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.printerprotocol.request.HitiPPR_PrinterCommandNew;
import com.hiti.printerprotocol.utility.IMobile;
import com.hiti.printerprotocol.utility.ISDcard;
import com.hiti.printerprotocol.utility.MobileUtility;
import com.hiti.printerprotocol.utility.SDcardUtility;
import com.hiti.utility.FileUtility;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class PrintSend {
    String IP;
    Context mContext;
    MobileUtility mMobileUtility;
    ArrayList<Integer> mPhotoCopiesList;
    ArrayList<Integer> mPhotoIdList;
    ArrayList<String> mPhotoPathList;
    ArrayList<Integer> mPhotoSidList;
    int mPort;
    SDcardUtility mSDcardUtility;
    Type mType;

    public interface CallBack {
        void showError(Step step, String str);

        void showProgressBar(boolean z);

        void showStatus(String str);
    }

    public enum Step {
        PrintChecking,
        PhotoSentDone,
        PaperSizeUnMatch,
        Initial,
        ImagePreparing,
        Printing,
        Sending,
        PrintDone,
        recoveryDone,
        BurnFirmware,
        PrinterError,
        Timeout,
        ConnectFail
    }

    public enum Type {
        mobile,
        sdCard
    }

    class MobileUtil extends MobileUtility {
        public MobileUtil(Context context, ArrayList<String> strPhotoPathList, ArrayList<Integer> iPhotoCopiesList, boolean bNeedUnSharpen) {
            super(context, strPhotoPathList, iPhotoCopiesList, bNeedUnSharpen);
        }

        public int[][] SetPrintoutSize(int iPaperType) {
            int[][] length = (int[][]) Array.newInstance(Integer.TYPE, new int[]{2, 2});
            length[0][0] = 1818;
            length[0][1] = 1212;
            length[1][0] = 1280;
            length[1][1] = 1818;
            return length;
        }
    }

    public PrintSend(Context context, Type type) {
        this.mContext = null;
        this.mMobileUtility = null;
        this.mSDcardUtility = null;
        this.IP = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP;
        this.mPort = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT;
        this.mPhotoPathList = null;
        this.mPhotoCopiesList = null;
        this.mPhotoIdList = null;
        this.mPhotoSidList = null;
        this.mType = Type.mobile;
        this.mContext = context;
        this.mType = type;
        if (type == Type.mobile) {
            this.mPhotoPathList = new ArrayList();
        } else {
            this.mPhotoIdList = new ArrayList();
            this.mPhotoSidList = new ArrayList();
        }
        this.mPhotoCopiesList = new ArrayList();
        new JumpPreferenceKey(context).SetPreference(JumpPreferenceKey.PRINTER_MODEL, WirelessType.TYPE_P461);
    }

    public void prepare(String photoPath, int copies, IMobile listener) {
        if (FileUtility.FileExist(photoPath)) {
            this.mPhotoPathList.add(photoPath);
            this.mPhotoCopiesList.add(Integer.valueOf(copies));
            prepare(this.mPhotoPathList, this.mPhotoCopiesList, listener);
        }
    }

    public void prepare(ArrayList<String> photoPathList, ArrayList<Integer> copiesList, IMobile listener) {
        this.mMobileUtility = new MobileUtil(this.mContext, photoPathList, copiesList, false);
        this.mMobileUtility.SetMobileListener(listener);
    }

    public void prepare(Job job, int copies, ISDcard listener) {
        if (job != null) {
            this.mPhotoIdList.add(Integer.valueOf(job.getThumbnailId()));
            this.mPhotoCopiesList.add(Integer.valueOf(copies));
            prepare(job, this.mPhotoIdList, this.mPhotoCopiesList, listener);
        }
    }

    public void prepare(Job job, ArrayList<Integer> photoIdList, ArrayList<Integer> photoCopiesList, ISDcard listener) {
        if (job != null) {
            this.mPhotoSidList.add(Integer.valueOf(job.getStorageId()));
            this.mSDcardUtility = new SDcardUtility(this.mContext, photoIdList, this.mPhotoSidList, photoCopiesList);
            this.mSDcardUtility.SetPrintingPhotoListener(listener);
        }
    }

    public void setPrintSetting(boolean isTexture, boolean isFineMode) {
        if (this.mType == Type.mobile) {
            if (this.mMobileUtility != null) {
                this.mMobileUtility.SetPrinterInfo(2, isTexture, false, isFineMode ? 2 : 1, 1);
            }
        } else if (this.mSDcardUtility != null) {
            this.mSDcardUtility.SetPrinterInfo(2, false, false, isFineMode ? 2 : 1, 1);
        }
    }

    public void send(Job job) {
        if (this.mType == Type.mobile) {
            this.mMobileUtility.SendPhoto(job == null ? null : job.getSocket(), this.IP, this.mPort);
        } else {
            this.mSDcardUtility.SendPhoto();
        }
    }
}
