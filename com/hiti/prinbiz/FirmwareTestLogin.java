package com.hiti.prinbiz;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.hiti.jni.hello.Hello;
import com.hiti.utility.UserInfo;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

public class FirmwareTestLogin extends Activity {
    private static final int CHECK_MODE = 1;
    private static final int LOGIN_MODE = 2;
    private OnClickListener CommitListener;
    private EditText m_AccountEditText;
    private ImageButton m_BackImageButton;
    private EditText m_CheckEditText;
    private RelativeLayout m_CheckRLayout;
    private RelativeLayout m_LoginLayout;
    private Button m_OKButton;
    private EditText m_PwdEditText;
    private int m_iMode;

    /* renamed from: com.hiti.prinbiz.FirmwareTestLogin.1 */
    class C02891 implements OnClickListener {
        C02891() {
        }

        public void onClick(View v) {
            FirmwareTestLogin.this.onBackPressed();
        }
    }

    /* renamed from: com.hiti.prinbiz.FirmwareTestLogin.2 */
    class C02902 implements OnClickListener {
        C02902() {
        }

        public void onClick(View v) {
            if (FirmwareTestLogin.this.m_iMode == FirmwareTestLogin.CHECK_MODE) {
                FirmwareTestLogin.this.ChangeToLoginMode();
            } else {
                FirmwareTestLogin.this.LoginToUpdateFirmware();
            }
        }
    }

    public FirmwareTestLogin() {
        this.m_LoginLayout = null;
        this.m_CheckRLayout = null;
        this.m_CheckEditText = null;
        this.m_AccountEditText = null;
        this.m_PwdEditText = null;
        this.m_OKButton = null;
        this.m_BackImageButton = null;
        this.m_iMode = CHECK_MODE;
        this.CommitListener = new C02902();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0349R.layout.activity_login);
        this.m_LoginLayout = (RelativeLayout) findViewById(C0349R.id.m_LoginLayout);
        this.m_CheckRLayout = (RelativeLayout) findViewById(C0349R.id.m_CheckRLayout);
        this.m_CheckEditText = (EditText) findViewById(C0349R.id.m_CheckEditText);
        this.m_AccountEditText = (EditText) findViewById(C0349R.id.m_AccountEditText);
        this.m_PwdEditText = (EditText) findViewById(C0349R.id.m_PwdEditText);
        this.m_BackImageButton = (ImageButton) findViewById(C0349R.id.m_BackImageButton);
        this.m_OKButton = (Button) findViewById(C0349R.id.m_OKButton);
        try {
            this.m_OKButton.setTextColor(ColorStateList.createFromXml(getResources(), getResources().getXml(C0349R.color.setting_orange_to_white)));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        this.m_OKButton.setOnClickListener(this.CommitListener);
        this.m_BackImageButton.setOnClickListener(new C02891());
    }

    private void ChangeToLoginMode() {
        String strPassword = null;
        if (this.m_CheckEditText.getText() != null) {
            strPassword = this.m_CheckEditText.getText().toString();
        }
        if (strPassword.equals(getString(C0349R.string.update_pwd))) {
            this.m_iMode = LOGIN_MODE;
            this.m_CheckRLayout.setVisibility(8);
            this.m_LoginLayout.setVisibility(0);
            this.m_OKButton.setText(getString(C0349R.string.LOGIN));
            return;
        }
        Toast.makeText(this, getString(C0349R.string.MSG_PWD_WRONG), 0).show();
    }

    private void LoginToUpdateFirmware() {
        String strU = Hello.SayHello(this, 10014);
        String strP = Hello.SayGoodBye(this, 10014);
        String strUserU = this.m_AccountEditText.getText().toString();
        String strUserP = this.m_PwdEditText.getText().toString();
        if (strU.equals(strUserU) && strP.equals(strUserP)) {
            UserInfo.UserLogout(this);
            UserInfo.FakeUserLogin(this, strU, strP);
            Toast.makeText(this, getString(C0349R.string.LOGIN_OK), 0).show();
            finish();
            return;
        }
        Toast.makeText(this, getString(C0349R.string.MSG_ACCOUNT_PWD_WRONG), 0).show();
    }
}
