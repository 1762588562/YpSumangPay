package g3.yeepay.com.yeepaySamsungPay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import g3.yeepay.com.yeepaysumang.unionPay.UnionPay;
import g3.yeepay.com.yeepaySamsungPay.digest.Digest;

import static g3.yeepay.com.yeepaySamsungPay.PaymentActivity1.payParamsMap1;

/**
 * title: <br/>
 * description:描述<br/>
 * Copyright: Copyright (c)2016<br/>
 * Company: 易宝支付(YeePay)<br/>
 *
 * @author guoliang.li
 * @version 1.0.0
 * @since 16/11/22 下午4:55
 */
public class PaymentActivity2 extends Activity {
    private EditText et_mode;
    private EditText et_termimalCode;
    private EditText et_priskParam;
    private EditText et_needResponse;
    private EditText et_businessOrder;
    private EditText et_hmac;
    private EditText et_signParams;
    private EditText et_tnCode;
    private Button btn_confirmPay;
    private Button btn_back2;
    private Button btn_paySign;
    public static TextView tv_tnCode;
    public static String signValue="";
    private String aKey="1oC3L9516894J0jX2k5X5Uh405G9ER39760gXw8P10YCIs2888W3tnI63pTP";
//    private String aKey="7To543x6Z2u87E9zc28qD63okJ171N3c8lJb47D489237993Kyo17M865itg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.payment2);
        et_mode=(EditText) findViewById(R.id.et_mode);
        et_termimalCode=(EditText) findViewById(R.id.et_sTermimalCode);
        et_priskParam=(EditText) findViewById(R.id.et_sPriskParam);
        et_needResponse=(EditText) findViewById(R.id.et_sNeedResponse);
        et_businessOrder=(EditText) findViewById(R.id.et_sBussinessOrderId);
        et_hmac=(EditText) findViewById(R.id.et_sSign);
        et_signParams=(EditText) findViewById(R.id.et_signParams);
        et_needResponse.setText("1");
        et_mode.setText("01");
        btn_paySign=(Button) findViewById(R.id.btn_paySign);
        tv_tnCode=(TextView) findViewById(R.id.tv_tnCode);
        btn_paySign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 signValue=payParamsMap1.get("p0_Cmd")+payParamsMap1.get("p1_MerId")
                        +payParamsMap1.get("p2_Order")+payParamsMap1.get("p3_Amt")
                        +payParamsMap1.get("pe_promotionId")+payParamsMap1.get("p4_Cur")
                        +payParamsMap1.get("p5_Pid")+payParamsMap1.get("p8_Url")
                        +et_termimalCode.getText().toString().trim()
                        +et_priskParam.getText().toString().trim();
                et_signParams.setText(signValue);
                String sign=Digest.hmacSign((payParamsMap1.get("p0_Cmd")+payParamsMap1.get("p1_MerId")
                +payParamsMap1.get("p2_Order")+payParamsMap1.get("p3_Amt")
                +payParamsMap1.get("pe_promotionId")+payParamsMap1.get("p4_Cur")
                +payParamsMap1.get("p5_Pid")+payParamsMap1.get("p8_Url")
                +et_termimalCode.getText().toString().trim()
                +et_priskParam.getText().toString().trim()),aKey);
                et_hmac.setText(sign);
            }
        });
        btn_back2=(Button) findViewById(R.id.btn_back2);
        btn_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PaymentActivity2.this,PaymentActivity1.class);
                startActivity(intent);
            }
        });
        btn_confirmPay=(Button) findViewById(R.id.btn_confirmPay);
        btn_confirmPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    UnionPay.payment(PaymentActivity2.this,getPayParams(),et_mode.getText().toString());
                } catch (RuntimeException e) {
                    Message msg=new Message();
                    msg.what=0x14;
                    msg.obj=e.getMessage();
                    myHandler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        });
        super.onCreate(savedInstanceState);
    }

    private  Map<String,String>getPayParams(){
        Map<String,String>map=new HashMap<String, String>();
        map.put("prisk_TerminalCode",et_termimalCode.getText().toString().trim());
        map.put("prisk_Param",et_priskParam.getText().toString().trim());
        map.put("pr_NeedResponse",et_needResponse.getText().toString().trim());
        map.put("pc_BusinessOrder",et_businessOrder.getText().toString().trim());
        map.put("hmac",et_hmac.getText().toString().trim());
        map.putAll(payParamsMap1);
        return map;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data==null){
            return;
        }
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")){
            Message msg=new Message();
            msg.what=0x11;
            myHandler.sendMessage(msg);
        }else if(str.equalsIgnoreCase("fail")){
            Message msg=new Message();
            msg.what=0x12;
            myHandler.sendMessage(msg);
        }else if(str.equalsIgnoreCase("cancel")){
            Message msg=new Message();
            msg.what=0x13;
            myHandler.sendMessage(msg);
        }

    }

    Handler myHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x11:
                    Toast.makeText(PaymentActivity2.this, "支付成功", Toast.LENGTH_LONG).show();
                    break;
                case 0x12:
                    Toast.makeText(PaymentActivity2.this,"支付失败",Toast.LENGTH_LONG).show();
                    break;
                case 0x13:
                    Toast.makeText(PaymentActivity2.this,"支付取消",Toast.LENGTH_LONG).show();
                    break;
                case 0x14:
                    Toast.makeText(PaymentActivity2.this,msg.obj.toString(),Toast.LENGTH_LONG).show();
                    break;

            }
            super.handleMessage(msg);
        }
    };
}
