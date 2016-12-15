package g3.yeepay.com.yeepaysumang;

import android.content.Context;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import g3.yeepay.com.yeepaysumang.unionPay.UnionPay;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
   Context mcontext;

   @Test
    public void testPay(){
//       UnionPay.getPreferential("SpayPromotion","10040001738","14793900981430","TEST","CNY1","1","ccabc7837eaabce7f4d01a9926967963");
//       UnionPay.getPreferential("SpayPromotion","10040001738","14793900300400","TEST","CNY1","100","f21cc5d5961c0860baab15c89e9a0eaa");

       Map<String,String>paramsMap=new HashMap<String, String>();
       paramsMap.put("p0_Cmd","SpayPromotion");
       paramsMap.put("p1_MerId","10040007799");
       paramsMap.put("p2_Order","14798810045550");
       paramsMap.put("p3_Amt","5");
       paramsMap.put("p4_Cur","CNY");
       paramsMap.put("pe_Phone","TEST");
       paramsMap.put("pe_MemberId","优惠");
       paramsMap.put("hmac","3f0932dd14c96d180e4362d8ee197791");

       UnionPay.getPreferential(paramsMap);
   }
}