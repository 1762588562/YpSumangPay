package g3.yeepay.com.yeepaysumang.mobileInfo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * title: <br/>
 * description:描述<br/>
 * Copyright: Copyright (c)2016<br/>
 * Company: 易宝支付(YeePay)<br/>
 *
 * @author guoliang.li
 * @version 1.0.0
 * @since 16/11/9 上午10:16
 */
public class MobileInformation {

    /**
     * 获取手机型号
     * @return
     */
    public static String getPhoneType(){
        String phoneType=android.os.Build.MODEL;
        return phoneType;
    }

    /**
     * 获取手机MAC地址
     * @param mContext
     * @return
     */
    public static String getPhoneMAC(Context mContext){
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null) {
            return wifiInfo.getMacAddress();
        } else {
            return null;
        }
    }

    /**
     * 获取手机IMEI
     * @param mContext
     * @return
     */
    public static String getImei(Context mContext){
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    /**
     * 获取手机IMSI
     * @param mContext
     * @return
     */
    public static String getImsi(Context mContext){
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
        return telephonyManager.getSubscriberId();
    }

    /**
     * 判断手机是安装SPay包含的三个APK
     * @param mContext
     * @return
     */
    public static Boolean IsSpayInstalled(Context mContext){
        boolean result=true;
        List<String>list=new ArrayList<String>();
        list.add(0,"com.samsung.android.spay");
        list.add(1,"com.samsung.android.spayfw");
        list.add(2,"com.unionpay.tsmservice");
        android.content.pm.ApplicationInfo info = null;
        for(int i=0;i<list.size();i++){
            try {
                info=mContext.getPackageManager().getApplicationInfo(list.get(i),0);
                if(info==null){
                    Log.e("Samsung APP","Samsung APP is not installed");
                    return false;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                result=false;
                return false;
            }
        }
        return result;
    }

    /**
     * 检测是否开启NFC
     * @param mContext
     * @return
     */
    public static Boolean hasNFC(Context mContext){
        boolean bRet=false;
        if(mContext==null)
            return bRet;
        NfcManager manager = (NfcManager) mContext.getSystemService(Context.NFC_SERVICE);
        NfcAdapter adapter = manager.getDefaultAdapter();
        if (adapter != null && adapter.isEnabled()) {
            bRet=true;
        }
        return bRet;
    }

}
