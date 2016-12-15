package g3.yeepay.com.yeepaysumang.unionPay;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import g3.yeepay.com.yeepaysumang.mobileInfo.MobileInformation;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * title: <br/>
 * description:描述<br/>
 * Copyright: Copyright (c)2016<br/>
 * Company: 易宝支付(YeePay)<br/>
 *
 * @author guoliang.li
 * @version 1.0.0
 * @since 16/11/10 下午2:48
 */
public class UnionPay {


//    private static final String submit_url_qa="http://124.250.37.125:8002/app-merchant-proxy/command.action";

    private static final String submit_url_production="http://www.yeepay.com/app-merchant-proxy/command.action";

    private static final String submit_url_qa="http://qa.yeepay.com/app-merchant-proxy/command.action";

    private static final String LOG_TAG="UNIONPAY";

    private static final String encode="application/x-www-form-urlencoded;charset=GBK";

    private static final int REQUEST_TIMEOUT = 5*1000;//设置请求超时5秒钟

    private static final int SO_TIMEOUT = 5*1000;  //设置等待数据超时时间5秒钟


    private static  String errorMsg="";


    private static String mapToString(Map<String,String>map){
        Iterator<Map.Entry<String,String>>it=map.entrySet().iterator();
        StringBuilder content=new StringBuilder();
        while(it.hasNext()){
            Map.Entry<String,String>entry=it.next();
            content.append(entry.getKey()).append("=").append(entry.getValue());
            if(it.hasNext()){
                content.append("&");
            }
        }
        return content.toString();
    }

    public static Boolean IsSimState(Context mcontext){
        boolean result=true;
        TelephonyManager manager=(TelephonyManager) mcontext.getSystemService(TELEPHONY_SERVICE);
        switch (manager.getSimState()){
            case TelephonyManager.SIM_STATE_ABSENT:
                result=false;
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                result=false;
                break;
            case TelephonyManager.SIM_STATE_READY:
                result=true;
                break;
        }
        return result;
    }
    /**
     * 确认支付接口
     * @param activity
     * @param map
     */
    public static void payment(Activity activity, final Map<String,String>map,final String mode){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<String> future =new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String tn=getTn(map,mode);
                return tn;
            }
        });
        executor.execute(future);
        String tnCode= null;
        try {
            tnCode = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(tnCode==null||"".equals(tnCode)){
            throw new RuntimeException(errorMsg);
        }else {
//            UPPayAssistEx.startPay(activity,null,null,tnCode,mode);
//            if(MobileInformation.hasNFC(activity)){
                UPPayAssistEx.startSamsungPay(activity, PayActivity.class,null,null,tnCode,mode);
//            }else {
//                errorMsg="未开启NFC功能，请打开NFC功能！";
//                throw new RuntimeException(errorMsg);
//            }
//            UPPayAssistEx.startSamsungPay(activity, PayActivity.class,null,null,tnCode,mode);
        }
    }

//    public static void samsungPay(Activity activity,String tnCode,String mode){
//        UPPayAssistEx.startSamsungPay(activity, PayActivity.class,null,null,tnCode,mode);
//    }
//
//    public static void unionPay(Activity activity,String tnCode,String mode){
//        UPPayAssistEx.startPay(activity,null,null,tnCode,mode);
//    }


  public static  Map<String,String>getPromotion(final Context context, Map<String,String>map,final String mode){
      Map<String,String>promotionParams=new HashMap<String, String>();
      map.put("pe_PhoneModel",MobileInformation.getPhoneType());

      if(IsSimState(context)){
          map.put("pe_PhoneIMSI",MobileInformation.getImsi(context));
      }
      map.put("pe_PhoneIMEI",MobileInformation.getImei(context));
      map.put("pe_PhoneMac",MobileInformation.getPhoneMAC(context));
      promotionParams.putAll(map);
      final Map<String,String>promotionParamsMap=promotionParams;
      ExecutorService executor = Executors.newSingleThreadExecutor();
      FutureTask<Map> future =new FutureTask<Map>(new Callable<Map>(){
          @Override
          public Map call() throws Exception {
              Map<String,String>getPromotionResult=new HashMap<String, String>();
              getPromotionResult=getPromotionResult(context,promotionParamsMap,mode);
              return getPromotionResult;
          }
      });
      executor.execute(future);
      Map<String,String>promotionResult=new HashMap<String, String>();
      try {
          promotionResult=future.get();
      } catch (InterruptedException e) {
          e.printStackTrace();
      } catch (ExecutionException e) {
          e.printStackTrace();
      }
      return promotionResult;
  }

    /**
     * 获取优惠接口
     * @param mcontext
     * @param map
     * @return
     */
    public static Map<String,String> getPromotionResult(Context mcontext,Map<String,String> map,String mode){
        Map<String,String>promotionResult=new HashMap<String, String>();
        HttpPost request=null;
        if(MobileInformation.IsSpayInstalled(mcontext)) {
            if (map.isEmpty()) {
                Log.i("LOG_TAG", "请输入参数");
                promotionResult.put("r1_Code", "3");
                promotionResult.put("errorMsg", "Params Incomplete");
                return promotionResult;
            }
            if("".equals(mode)||null==mode){
                promotionResult.put("r1_Code", "3");
                promotionResult.put("errorMsg", "Params Incomplete");
                return promotionResult;
            }else if(mode.equals("01")){
                request=new HttpPost(submit_url_qa);
            }else if(mode.equals("00")){
                request=new HttpPost(submit_url_production);
            }else {
                promotionResult.put("r1_Code", "3");
                promotionResult.put("errorMsg", "Params Incomplete");
                return promotionResult;
            }
            List<NameValuePair> params=new ArrayList<NameValuePair>();
            for(String key:map.keySet()){
                params.add(new BasicNameValuePair(key,map.get(key)));
            }
            try {
                HttpEntity entity = new UrlEncodedFormEntity(
                        params,"gbk");
                request.setEntity(entity);
                HttpClient client=getHttpClient(REQUEST_TIMEOUT,SO_TIMEOUT);
                try {
                    HttpResponse response=client.execute(request);
                    if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                        String result= EntityUtils.toString(response.getEntity(),"gbk");
                        promotionResult= ResultHandler.parsePromotionResult(result);
                        return promotionResult;
                    }else {
                        promotionResult.put("r1_Code","4");
                        promotionResult.put("errorMsg","Server Exception");
                    }
                } catch (IOException e) {
                    Log.i(LOG_TAG,e.getMessage());
                    promotionResult.put("r1_Code","5");
                    promotionResult.put("errorMsg","IO Exception");
                    return promotionResult;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else {
            promotionResult.put("r1_Code","2");
            promotionResult.put("errorMsg","SamsungPay Plugin Not Found");
            return promotionResult;
        }
        return promotionResult;
    }

    public static String getTn(Map<String,String>map,String mode){
        String tnResult="";
        HttpPost request=null;
        if(null==mode||"".equals(mode)){
            errorMsg="mode is null";
            return null;
        }else if(mode.equals("00")){
            request=new HttpPost(submit_url_production);
        }else if(mode.equals("01")){
            request=new HttpPost(submit_url_qa);
        }else {
            errorMsg="mode error";
            return null;
        }
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        for(String key:map.keySet()){
            params.add(new BasicNameValuePair(key,map.get(key)));
        }
        try {
            HttpEntity entity = new UrlEncodedFormEntity(
                    params,"gbk");
            request.setEntity(entity);
            HttpClient client=getHttpClient(REQUEST_TIMEOUT,SO_TIMEOUT);
            try {
                HttpResponse response=client.execute(request);
                if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                    String result= EntityUtils.toString(response.getEntity(),"gbk");
                    tnResult=ResultHandler.parseTnResult(result);
                    errorMsg=ResultHandler.parseErrorMsgResult(result);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return tnResult;
    }

    private static DefaultHttpClient getHttpClient(int rTimeOut,int sTimeOut){
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, rTimeOut);
        HttpConnectionParams.setSoTimeout(httpParams, sTimeOut);
        DefaultHttpClient client = new DefaultHttpClient(httpParams);
        return client;
    }

}
