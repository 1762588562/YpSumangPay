package g3.yeepay.com.yeepaySamsungPay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import g3.yeepay.com.yeepaysumang.unionPay.UnionPay;
import g3.yeepay.com.yeepaySamsungPay.digest.Digest;

/**
 * title: <br/>
 * description:描述<br/>
 * Copyright: Copyright (c)2016<br/>
 * Company: 易宝支付(YeePay)<br/>
 *
 * @author guoliang.li
 * @version 1.0.0
 * @since 16/11/22 上午10:59
 */
public class PrefrentialActivity extends Activity {
    private EditText et_businessType;
    private EditText et_merchantNum;
    private EditText et_orderId;
    private EditText et_amount;
    private EditText et_currency;
    private EditText et_phoneNum;
    private EditText et_memberId;
    private EditText et_hmac;
    private EditText et_pEnviromentParam;
    private Button btn_getPrefential;
    private Button btn_prefentialSign;
    private Map<String,String>prefrentialResultMap=new HashMap<String,String>();
    private TextView tv_prefentialResult;
    private Button btn_prefentialBack;
    private String aKey="1oC3L9516894J0jX2k5X5Uh405G9ER39760gXw8P10YCIs2888W3tnI63pTP";
//    private String aKey="7To543x6Z2u87E9zc28qD63okJ171N3c8lJb47D489237993Kyo17M865itg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion);
        et_businessType=(EditText) findViewById(R.id.et_pBusinessType);
        et_merchantNum=(EditText) findViewById(R.id.et_pMerchantNum);
        et_orderId=(EditText) findViewById(R.id.et_pOrderId);
        et_amount=(EditText) findViewById(R.id.et_pAmt);
        et_currency=(EditText) findViewById(R.id.et_pCurrency);
        et_phoneNum=(EditText) findViewById(R.id.et_pPhone);
        et_memberId=(EditText) findViewById(R.id.et_pMerchantId);
        et_hmac=(EditText) findViewById(R.id.et_pHmac);
        et_pEnviromentParam=(EditText) findViewById(R.id.et_pEnviromentParam);
        btn_getPrefential=(Button) findViewById(R.id.btn_getPrefential);
        btn_prefentialSign=(Button) findViewById(R.id.btn_prefentialSign);
        btn_prefentialSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sign=Digest.hmacSign((et_businessType.getText().toString().trim()+et_merchantNum.getText().toString().trim()
                +et_orderId.getText().toString().trim()+et_amount.getText().toString().trim()+et_currency.getText().toString().trim()
                +et_phoneNum.getText().toString().trim()+et_memberId.getText().toString().trim()),aKey);
                et_hmac.setText(sign);
            }
        });
        tv_prefentialResult=(TextView) findViewById(R.id.tv_prefentialResult);
        btn_prefentialBack=(Button) findViewById(R.id.btn_prefentialBack);
        et_businessType.setText("SpayPromotion");
        et_currency.setText("CNY");
        et_merchantNum.setText("10040007799");
        et_pEnviromentParam.setText("01");
        btn_prefentialBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PrefrentialActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btn_getPrefential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefrentialResultMap=UnionPay.getPromotion(PrefrentialActivity.this,getPrefentialParams(),et_pEnviromentParam.getText().toString());
                String rs_PromotionDesc=prefrentialResultMap.get("rs_PromotionDesc");
                String r3_Amt=prefrentialResultMap.get("r3_Amt");
                String r2_Amt=prefrentialResultMap.get("r2_Amt");
                String errorMsg=prefrentialResultMap.get("errorMsg");
                String r1_Code=prefrentialResultMap.get("r1_Code");
                String result="r1_Code="+r1_Code+"\n"+"r2_Amt="+r2_Amt+"\n"+"rs_PromotionDesc="+rs_PromotionDesc+"\n"+"r3_Amt="+r3_Amt+"\n"+"errorMsg="+errorMsg;
                tv_prefentialResult.setText(result);
            }
        });
    }

    public Map<String,String>getPrefentialParams(){
        Map<String,String> params=new HashMap<String,String>();
        params.put("p0_Cmd",et_businessType.getText().toString().trim());
        params.put("p1_MerId",et_merchantNum.getText().toString().trim());
        params.put("p2_Order",et_orderId.getText().toString().trim());
        params.put("p3_Amt",et_amount.getText().toString().trim());
        params.put("p4_Cur",et_currency.getText().toString().trim());
        params.put("pe_Phone",et_phoneNum.getText().toString().trim());
        params.put("pe_MemberId",et_memberId.getText().toString().trim());
        params.put("hmac",et_hmac.getText().toString().trim());
        return params;
    }

//    Handler myHandler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
////            if(msg!=null){
////                HashMap<String,String>map=(HashMap<String, String>) (msg.obj);
////                String prefentialInfo=null;
////                for(String key:map.keySet()){
////                    prefentialInfo=key+"="+map.get(key)+"\n";
////                }
////                tv_prefentialResult.setText(prefentialInfo);
//            }
////            super.handleMessage(msg);
//        }
//    };
}

