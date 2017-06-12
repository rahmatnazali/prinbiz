package com.hiti.ui.collageview;

import android.content.Context;
import android.content.res.AssetManager;
import com.hiti.utility.ByteConvertUtility;
import com.hiti.utility.FileUtility;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;

public class CollageParser {
    public static final String ATTR_Add_Photo_Button_Height = "Add_Photo_Button_Height";
    public static final String ATTR_Add_Photo_Button_Left = "Add_Photo_Button_Left";
    public static final String ATTR_Add_Photo_Button_Top = "Add_Photo_Button_Top";
    public static final String ATTR_Add_Photo_Button_Width = "Add_Photo_Button_Width";
    public static final String ATTR_Background_Photo_Path = "Background_Photo_Path";
    public static final String ATTR_Biometric_Line_Photo_Path = "Biometric_Line_Photo_Path";
    public static final String ATTR_Collage_Numbers = "Collage_Numbers";
    public static final String ATTR_Color_Select = "Color_Select";
    public static final String ATTR_Company_Align = "Company_Align";
    public static final String ATTR_Company_Font_Color = "Company_Font_Color";
    public static final String ATTR_Company_Font_Size = "Company_Font_Size";
    public static final String ATTR_Company_Left = "Company_Left";
    public static final String ATTR_Company_Top = "Company_Top";
    public static final String ATTR_ContentType_Align = "ContentType_Align";
    public static final String ATTR_ContentType_Font_Color = "ContentType_Font_Color";
    public static final String ATTR_ContentType_Font_Size = "ContentType_Font_Size";
    public static final String ATTR_ContentType_Left = "ContentType_Left";
    public static final String ATTR_ContentType_Top = "ContentType_Top";
    public static final String ATTR_Demo_Background_Photo_Path = "Demo_Background_Photo_Path";
    public static final String ATTR_Email_Align = "Email_Align";
    public static final String ATTR_Email_Font_Color = "Email_Font_Color";
    public static final String ATTR_Email_Font_Size = "Email_Font_Size";
    public static final String ATTR_Email_Left = "Email_Left";
    public static final String ATTR_Email_Top = "Email_Top";
    public static final String ATTR_Foreground_Photo_Path = "Foreground_Photo_Path";
    public static final String ATTR_FromSomeone_Align = "FromSomeone_Align";
    public static final String ATTR_FromSomeone_Font_Color = "FromSomeone_Font_Color";
    public static final String ATTR_FromSomeone_Font_Size = "FromSomeone_Font_Size";
    public static final String ATTR_FromSomeone_Left = "FromSomeone_Left";
    public static final String ATTR_FromSomeone_Top = "FromSomeone_Top";
    public static final String ATTR_Group = "Group";
    public static final String ATTR_Height = "Height";
    public static final String ATTR_Left = "Left";
    public static final String ATTR_MISC_Align = "MISC_Align";
    public static final String ATTR_MISC_Font_Color = "MISC_Font_Color";
    public static final String ATTR_MISC_Font_Size = "MISC_Font_Size";
    public static final String ATTR_MISC_Left = "MISC_Left";
    public static final String ATTR_MISC_Top = "MISC_Top";
    public static final String ATTR_Mask_Path = "Mask_Path";
    public static final String ATTR_Metal_Photo_Path = "Metal_Photo_Path";
    public static final String ATTR_Mobile_phone_Align = "Mobile_phone_Align";
    public static final String ATTR_Mobile_phone_Font_Color = "Mobile_phone_Font_Color";
    public static final String ATTR_Mobile_phone_Font_Size = "Mobile_phone_Font_Size";
    public static final String ATTR_Mobile_phone_Left = "Mobile_phone_Left";
    public static final String ATTR_Mobile_phone_Top = "Mobile_phone_Top";
    public static final String ATTR_Name_Align = "Name_Align";
    public static final String ATTR_Name_Font_Color = "Name_Font_Color";
    public static final String ATTR_Name_Font_Size = "Name_Font_Size";
    public static final String ATTR_Name_Left = "Name_Left";
    public static final String ATTR_Name_Top = "Name_Top";
    public static final String ATTR_Output_Photo_Height = "Output_Photo_Height";
    public static final String ATTR_Output_Photo_Width = "Output_Photo_Width";
    public static final String ATTR_QRCode_Align = "QRCode_Align";
    public static final String ATTR_QRCode_Height = "QRCode_Height";
    public static final String ATTR_QRCode_Left = "QRCode_Left";
    public static final String ATTR_QRCode_Top = "QRCode_Top";
    public static final String ATTR_QRCode_Width = "QRCode_Width";
    public static final String ATTR_Region_Table_Path = "Region_Table_Path";
    public static final String ATTR_Telephone_Align = "Telephone_Align";
    public static final String ATTR_Telephone_Font_Color = "Telephone_Font_Color";
    public static final String ATTR_Telephone_Font_Size = "Telephone_Font_Size";
    public static final String ATTR_Telephone_Left = "Telephone_Left";
    public static final String ATTR_Telephone_Top = "Telephone_Top";
    public static final String ATTR_Title_Align = "Title_Align";
    public static final String ATTR_Title_Font_Color = "Title_Font_Color";
    public static final String ATTR_Title_Font_Size = "Title_Font_Size";
    public static final String ATTR_Title_Left = "Title_Left";
    public static final String ATTR_Title_Top = "Title_Top";
    public static final String ATTR_ToSomeone_Align = "ToSomeone_Align";
    public static final String ATTR_ToSomeone_Font_Color = "ToSomeone_Font_Color";
    public static final String ATTR_ToSomeone_Font_Size = "ToSomeone_Font_Size";
    public static final String ATTR_ToSomeone_Left = "ToSomeone_Left";
    public static final String ATTR_ToSomeone_Top = "ToSomeone_Top";
    public static final String ATTR_Top = "Top";
    public static final String ATTR_Website_Align = "Website_Align";
    public static final String ATTR_Website_Font_Color = "Website_Font_Color";
    public static final String ATTR_Website_Font_Size = "Website_Font_Size";
    public static final String ATTR_Website_Left = "Website_Left";
    public static final String ATTR_Website_Top = "Website_Top";
    public static final String ATTR_Width = "Width";
    public static final String NODE_Business_Card_Info = "Business_Card_Info";
    public static final String NODE_Collage_Group_Info = "Collage_Group_Info";
    public static final String NODE_Collage_Info = "Collage_Info";

    public static CollageInfoGroup GetCollageInfoGroup(Context context, String strCollageTemplatePath) {
        AssetManager assetManager = context.getAssets();
        CollageInfoGroup collageInfoGroup = new CollageInfoGroup(context);
        String strRootPath = strCollageTemplatePath.substring(0, strCollageTemplatePath.lastIndexOf("/"));
        InputStream inputStream = null;
        try {
            collageInfoGroup.SetCollageTemplatePath(strCollageTemplatePath);
            if (FileUtility.IsFromSDCard(context, strCollageTemplatePath)) {
                collageInfoGroup.SetResourceFromWhere(1);
                inputStream = new FileInputStream(strCollageTemplatePath);
            } else {
                collageInfoGroup.SetResourceFromWhere(0);
                inputStream = assetManager.open(strCollageTemplatePath);
            }
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
            if (inputStream != null) {
                inputStream.close();
            }
            Element root = document.getDocumentElement();
            NodeList nodes = root.getElementsByTagName(NODE_Collage_Group_Info);
            if (nodes.getLength() <= 0) {
                return null;
            }
            int i;
            Element CollageGroupInfoElement = (Element) nodes.item(0);
            collageInfoGroup.SetPhotoWidth(Integer.valueOf(CollageGroupInfoElement.getAttribute(ATTR_Output_Photo_Width)).intValue());
            collageInfoGroup.SetPhotoHeight(Integer.valueOf(CollageGroupInfoElement.getAttribute(ATTR_Output_Photo_Height)).intValue());
            collageInfoGroup.SetAddPhotoButtonWidth(Integer.valueOf(CollageGroupInfoElement.getAttribute(ATTR_Add_Photo_Button_Width)).intValue());
            collageInfoGroup.SetAddPhotoButtonHeight(Integer.valueOf(CollageGroupInfoElement.getAttribute(ATTR_Add_Photo_Button_Height)).intValue());
            collageInfoGroup.SetBackgroundPhotoPath(XmlPullParser.NO_NAMESPACE);
            if (!CollageGroupInfoElement.getAttribute(ATTR_Background_Photo_Path).equals(XmlPullParser.NO_NAMESPACE)) {
                collageInfoGroup.SetBackgroundPhotoPath(strRootPath + "/" + CollageGroupInfoElement.getAttribute(ATTR_Background_Photo_Path));
            }
            collageInfoGroup.SetDemoBackgroundPhotoPath(XmlPullParser.NO_NAMESPACE);
            if (!CollageGroupInfoElement.getAttribute(ATTR_Demo_Background_Photo_Path).equals(XmlPullParser.NO_NAMESPACE)) {
                collageInfoGroup.SetDemoBackgroundPhotoPath(strRootPath + "/" + CollageGroupInfoElement.getAttribute(ATTR_Demo_Background_Photo_Path));
            }
            collageInfoGroup.SetForegroundPhotoPath(XmlPullParser.NO_NAMESPACE);
            if (!CollageGroupInfoElement.getAttribute(ATTR_Foreground_Photo_Path).equals(XmlPullParser.NO_NAMESPACE)) {
                collageInfoGroup.SetForegroundPhotoPath(strRootPath + "/" + CollageGroupInfoElement.getAttribute(ATTR_Foreground_Photo_Path));
            }
            collageInfoGroup.SetMetalPhotoPath(XmlPullParser.NO_NAMESPACE);
            if (!CollageGroupInfoElement.getAttribute(ATTR_Metal_Photo_Path).equals(XmlPullParser.NO_NAMESPACE)) {
                collageInfoGroup.SetMetalPhotoPath(strRootPath + "/" + CollageGroupInfoElement.getAttribute(ATTR_Metal_Photo_Path));
            }
            collageInfoGroup.SetRegionTablePath(strRootPath + "/" + CollageGroupInfoElement.getAttribute(ATTR_Region_Table_Path));
            collageInfoGroup.SetCollageNumbers(Integer.valueOf(CollageGroupInfoElement.getAttribute(ATTR_Collage_Numbers)).intValue());
            NodeList CollageInfoNodes = root.getElementsByTagName(NODE_Collage_Info);
            for (i = 0; i < CollageInfoNodes.getLength(); i++) {
                Element CollageInfoElement = (Element) CollageInfoNodes.item(i);
                CollageInfo collageInfo = new CollageInfo();
                collageInfo.SetLeft(Float.valueOf(CollageInfoElement.getAttribute(ATTR_Left)).floatValue());
                collageInfo.SetTop(Float.valueOf(CollageInfoElement.getAttribute(ATTR_Top)).floatValue());
                collageInfo.SetWidth(Float.valueOf(CollageInfoElement.getAttribute(ATTR_Width)).floatValue());
                collageInfo.SetHeight(Float.valueOf(CollageInfoElement.getAttribute(ATTR_Height)).floatValue());
                collageInfo.SetMaskPath(strRootPath + "/" + CollageInfoElement.getAttribute(ATTR_Mask_Path));
                collageInfo.SetColorSelect(Integer.valueOf(CollageInfoElement.getAttribute(ATTR_Color_Select)).intValue());
                collageInfo.SetAddPhotoButtonLeft(CollageInfo.ATTR_VALUE_NO_BUTTON_POSITION);
                if (!CollageInfoElement.getAttribute(ATTR_Add_Photo_Button_Left).equals(XmlPullParser.NO_NAMESPACE)) {
                    collageInfo.SetAddPhotoButtonLeft(Float.valueOf(CollageInfoElement.getAttribute(ATTR_Add_Photo_Button_Left)).floatValue());
                }
                collageInfo.SetAddPhotoButtonTop(CollageInfo.ATTR_VALUE_NO_BUTTON_POSITION);
                if (!CollageInfoElement.getAttribute(ATTR_Add_Photo_Button_Top).equals(XmlPullParser.NO_NAMESPACE)) {
                    collageInfo.SetAddPhotoButtonTop(Float.valueOf(CollageInfoElement.getAttribute(ATTR_Add_Photo_Button_Top)).floatValue());
                }
                collageInfo.SetGroup(CollageInfo.ATTR_VALUE_NO_GROUP);
                if (!CollageInfoElement.getAttribute(ATTR_Group).equals(XmlPullParser.NO_NAMESPACE)) {
                    collageInfo.SetGroup(Integer.valueOf(CollageInfoElement.getAttribute(ATTR_Group)).intValue());
                }
                collageInfo.SetBiometricLinePhotoPath(XmlPullParser.NO_NAMESPACE);
                if (!CollageInfoElement.getAttribute(ATTR_Biometric_Line_Photo_Path).equals(XmlPullParser.NO_NAMESPACE)) {
                    collageInfo.SetBiometricLinePhotoPath(strRootPath + "/" + String.valueOf(CollageInfoElement.getAttribute(ATTR_Biometric_Line_Photo_Path)));
                }
                collageInfoGroup.AddCollageInfo(collageInfo);
            }
            NodeList BusinessCardNodes = root.getElementsByTagName(NODE_Business_Card_Info);
            for (i = 0; i < BusinessCardNodes.getLength(); i++) {
                Element BusinessCardElement = (Element) BusinessCardNodes.item(i);
                BusinessCardInfo businessCardInfo = new BusinessCardInfo();
                businessCardInfo.SetQRCodeAlign(Integer.valueOf(BusinessCardElement.getAttribute(ATTR_QRCode_Align)).intValue());
                businessCardInfo.SetQRCodeLeft(Float.valueOf(BusinessCardElement.getAttribute(ATTR_QRCode_Left)).floatValue());
                businessCardInfo.SetQRCodeTop(Float.valueOf(BusinessCardElement.getAttribute(ATTR_QRCode_Top)).floatValue());
                businessCardInfo.SetQRCodeWidth(Integer.valueOf(BusinessCardElement.getAttribute(ATTR_QRCode_Width)).intValue());
                businessCardInfo.SetQRCodeHeight(Integer.valueOf(BusinessCardElement.getAttribute(ATTR_QRCode_Height)).intValue());
                businessCardInfo.SetNameAlign(Integer.valueOf(BusinessCardElement.getAttribute(ATTR_Name_Align)).intValue());
                businessCardInfo.SetNameLeft(Float.valueOf(BusinessCardElement.getAttribute(ATTR_Name_Left)).floatValue());
                businessCardInfo.SetNameTop(Float.valueOf(BusinessCardElement.getAttribute(ATTR_Name_Top)).floatValue());
                businessCardInfo.SetNameFontSize(Integer.valueOf(BusinessCardElement.getAttribute(ATTR_Name_Font_Size)).intValue());
                businessCardInfo.SetNameFontColor(ByteConvertUtility.String10ToInt16(BusinessCardElement.getAttribute(ATTR_Name_Font_Color)));
                businessCardInfo.SetTelephoneAlign(Integer.valueOf(BusinessCardElement.getAttribute(ATTR_Telephone_Align)).intValue());
                businessCardInfo.SetTelephoneLeft(Float.valueOf(BusinessCardElement.getAttribute(ATTR_Telephone_Left)).floatValue());
                businessCardInfo.SetTelephoneTop(Float.valueOf(BusinessCardElement.getAttribute(ATTR_Telephone_Top)).floatValue());
                businessCardInfo.SetTelephoneFontSize(Integer.valueOf(BusinessCardElement.getAttribute(ATTR_Telephone_Font_Size)).intValue());
                businessCardInfo.SetTelephoneFontColor(ByteConvertUtility.String10ToInt16(BusinessCardElement.getAttribute(ATTR_Telephone_Font_Color)));
                businessCardInfo.SetMobilePhoneAlign(Integer.valueOf(BusinessCardElement.getAttribute(ATTR_Mobile_phone_Align)).intValue());
                businessCardInfo.SetMobilePhoneLeft(Float.valueOf(BusinessCardElement.getAttribute(ATTR_Mobile_phone_Left)).floatValue());
                businessCardInfo.SetMobilePhoneTop(Float.valueOf(BusinessCardElement.getAttribute(ATTR_Mobile_phone_Top)).floatValue());
                businessCardInfo.SetMobilePhoneFontSize(Integer.valueOf(BusinessCardElement.getAttribute(ATTR_Mobile_phone_Font_Size)).intValue());
                businessCardInfo.SetMobilePhoneFontColor(ByteConvertUtility.String10ToInt16(BusinessCardElement.getAttribute(ATTR_Mobile_phone_Font_Color)));
                businessCardInfo.SetEmailAlign(Integer.valueOf(BusinessCardElement.getAttribute(ATTR_Email_Align)).intValue());
                businessCardInfo.SetEmailLeft(Float.valueOf(BusinessCardElement.getAttribute(ATTR_Email_Left)).floatValue());
                businessCardInfo.SetEmailTop(Float.valueOf(BusinessCardElement.getAttribute(ATTR_Email_Top)).floatValue());
                businessCardInfo.SetEmailFontSize(Integer.valueOf(BusinessCardElement.getAttribute(ATTR_Email_Font_Size)).intValue());
                businessCardInfo.SetEmailFontColor(ByteConvertUtility.String10ToInt16(BusinessCardElement.getAttribute(ATTR_Email_Font_Color)));
                businessCardInfo.SetWebsiteAlign(Integer.valueOf(BusinessCardElement.getAttribute(ATTR_Website_Align)).intValue());
                businessCardInfo.SetWebsiteLeft(Float.valueOf(BusinessCardElement.getAttribute(ATTR_Website_Left)).floatValue());
                businessCardInfo.SetWebsiteTop(Float.valueOf(BusinessCardElement.getAttribute(ATTR_Website_Top)).floatValue());
                businessCardInfo.SetWebsiteFontSize(Integer.valueOf(BusinessCardElement.getAttribute(ATTR_Website_Font_Size)).intValue());
                businessCardInfo.SetWebsiteFontColor(ByteConvertUtility.String10ToInt16(BusinessCardElement.getAttribute(ATTR_Website_Font_Color)));
                businessCardInfo.SetCompanyAlign(Integer.valueOf(BusinessCardElement.getAttribute(ATTR_Company_Align)).intValue());
                businessCardInfo.SetCompanyLeft(Float.valueOf(BusinessCardElement.getAttribute(ATTR_Company_Left)).floatValue());
                businessCardInfo.SetCompanyTop(Float.valueOf(BusinessCardElement.getAttribute(ATTR_Company_Top)).floatValue());
                businessCardInfo.SetCompanyFontSize(Integer.valueOf(BusinessCardElement.getAttribute(ATTR_Company_Font_Size)).intValue());
                businessCardInfo.SetCompanyFontColor(ByteConvertUtility.String10ToInt16(BusinessCardElement.getAttribute(ATTR_Company_Font_Color)));
                businessCardInfo.SetTitleAlign(Integer.valueOf(BusinessCardElement.getAttribute(ATTR_Title_Align)).intValue());
                businessCardInfo.SetTitleLeft(Float.valueOf(BusinessCardElement.getAttribute(ATTR_Title_Left)).floatValue());
                businessCardInfo.SetTitleTop(Float.valueOf(BusinessCardElement.getAttribute(ATTR_Title_Top)).floatValue());
                businessCardInfo.SetTitleFontSize(Integer.valueOf(BusinessCardElement.getAttribute(ATTR_Title_Font_Size)).intValue());
                businessCardInfo.SetTitleFontColor(ByteConvertUtility.String10ToInt16(BusinessCardElement.getAttribute(ATTR_Title_Font_Color)));
                businessCardInfo.SetMISCAlign(Integer.valueOf(BusinessCardElement.getAttribute(ATTR_MISC_Align)).intValue());
                businessCardInfo.SetMISCLeft(Float.valueOf(BusinessCardElement.getAttribute(ATTR_MISC_Left)).floatValue());
                businessCardInfo.SetMISCTop(Float.valueOf(BusinessCardElement.getAttribute(ATTR_MISC_Top)).floatValue());
                businessCardInfo.SetMISCFontSize(Integer.valueOf(BusinessCardElement.getAttribute(ATTR_MISC_Font_Size)).intValue());
                businessCardInfo.SetMISCFontColor(ByteConvertUtility.String10ToInt16(BusinessCardElement.getAttribute(ATTR_MISC_Font_Color)));
                collageInfoGroup.SetBusinessCardInfo(businessCardInfo);
            }
            NodeList GreetingCardNodes = root.getElementsByTagName("Greeting_Card_Info");
            for (i = 0; i < GreetingCardNodes.getLength(); i++) {
                Element GreetingCardElement = (Element) GreetingCardNodes.item(i);
                GreetingCardInfo GreetingCardInfo = new GreetingCardInfo();
                GreetingCardInfo.SetToSomeoneAlign(Integer.valueOf(GreetingCardElement.getAttribute(ATTR_ToSomeone_Align)).intValue());
                GreetingCardInfo.SetToSomeoneLeft(Float.valueOf(GreetingCardElement.getAttribute(ATTR_ToSomeone_Left)).floatValue());
                GreetingCardInfo.SetToSomeoneTop(Float.valueOf(GreetingCardElement.getAttribute(ATTR_ToSomeone_Top)).floatValue());
                GreetingCardInfo.SetToSomeoneFontSize(Integer.valueOf(GreetingCardElement.getAttribute(ATTR_ToSomeone_Font_Size)).intValue());
                GreetingCardInfo.SetToSomeoneFontColor(ByteConvertUtility.String10ToInt16(GreetingCardElement.getAttribute(ATTR_ToSomeone_Font_Color)));
                GreetingCardInfo.SetContentTypeAlign(Integer.valueOf(GreetingCardElement.getAttribute(ATTR_ContentType_Align)).intValue());
                GreetingCardInfo.SetContentTypeLeft(Float.valueOf(GreetingCardElement.getAttribute(ATTR_ContentType_Left)).floatValue());
                GreetingCardInfo.SetContentTypeTop(Float.valueOf(GreetingCardElement.getAttribute(ATTR_ContentType_Top)).floatValue());
                GreetingCardInfo.SetContentTypeFontSize(Integer.valueOf(GreetingCardElement.getAttribute(ATTR_ContentType_Font_Size)).intValue());
                GreetingCardInfo.SetContentTypeFontColor(ByteConvertUtility.String10ToInt16(GreetingCardElement.getAttribute(ATTR_ContentType_Font_Color)));
                GreetingCardInfo.SetFromSomeoneAlign(Integer.valueOf(GreetingCardElement.getAttribute(ATTR_FromSomeone_Align)).intValue());
                GreetingCardInfo.SetFromSomeoneLeft(Float.valueOf(GreetingCardElement.getAttribute(ATTR_FromSomeone_Left)).floatValue());
                GreetingCardInfo.SetFromSomeoneTop(Float.valueOf(GreetingCardElement.getAttribute(ATTR_FromSomeone_Top)).floatValue());
                GreetingCardInfo.SetFromSomeoneFontSize(Integer.valueOf(GreetingCardElement.getAttribute(ATTR_FromSomeone_Font_Size)).intValue());
                GreetingCardInfo.SetFromSomeoneFontColor(ByteConvertUtility.String10ToInt16(GreetingCardElement.getAttribute(ATTR_FromSomeone_Font_Color)));
                collageInfoGroup.SetGreetingCardInfo(GreetingCardInfo);
            }
            return collageInfoGroup;
        } catch (SAXException e) {
            e.printStackTrace();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            return null;
        } catch (ParserConfigurationException e3) {
            e3.printStackTrace();
            if (inputStream != null) {
                inputStream.close();
            }
            return null;
        } catch (IOException e22) {
            e22.printStackTrace();
            if (inputStream != null) {
                inputStream.close();
            }
            return null;
        }
    }
}
