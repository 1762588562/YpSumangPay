package g3.yeepay.com.yeepaySamsungPay;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * title: <br/>
 * description:描述<br/>
 * Copyright: Copyright (c)2016<br/>
 * Company: 易宝支付(YeePay)<br/>
 *
 * @author guoliang.li
 * @version 1.0.0
 * @since 16/11/24 下午6:43
 */
public class SamsungPayApplication extends Application {
    @Override
    public void onCreate() {
        CrashReport.initCrashReport(getApplicationContext(),"900059557",true);
        super.onCreate();
    }

    public SamsungPayApplication() {
        super();
    }
}
