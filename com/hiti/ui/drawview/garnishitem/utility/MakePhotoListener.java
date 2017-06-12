package com.hiti.ui.drawview.garnishitem.utility;

import java.net.Socket;

public interface MakePhotoListener {
    void MakeOneEditPhotoDone(Socket socket, int i, String str);

    int[] SetPrintout(int i);

    void onMakePhotoError();
}
