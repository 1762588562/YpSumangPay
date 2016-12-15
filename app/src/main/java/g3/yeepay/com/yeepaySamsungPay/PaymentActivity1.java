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

/**
 * title: <br/>
 * description:描述<br/>
 * Copyright: Copyright (c)2016<br/>
 * Company: 易宝支付(YeePay)<br/>
 *
 * @author guoliang.li
 * @version 1.0.0
 * @since 16/11/22 下午4:08
 */
public class PaymentActivity1 extends Activity{
    private EditText et_businessType;
    private EditText et_mermId;
    private EditText et_orderId;
    private EditText et_amount;
    private EditText et_promotionId;
    private EditText et_cur;
    private EditText et_pid;
    private EditText et_url;
    private EditText et_desc;
    private TextView tv_tnCodeCode;
    public static Map<String,String>payParamsMap1=new HashMap<String,String>();

    public Button btn_back1;
    public Button btn_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.payment1);
        et_businessType=(EditText) findViewById(R.id.et_sBusinessType);
        et_mermId=(EditText) findViewById(R.id.et_sMerchantNum);
        et_orderId=(EditText) findViewById(R.id.et_sOrderId);
        et_amount=(EditText) findViewById(R.id.et_sAmount);
        et_promotionId=(EditText) findViewById(R.id.et_sPromotionId);
        et_cur=(EditText) findViewById(R.id.et_sCurrency);
        et_pid=(EditText) findViewById(R.id.et_sPid);
        et_url=(EditText) findViewById(R.id.et_sUrl);
        et_desc=(EditText) findViewById(R.id.et_sDes);
        tv_tnCodeCode=(TextView) findViewById(R.id.tv_tnCodeCode);
        et_businessType.setText("SpayOrder");
        et_cur.setText("CNY");
        et_mermId.setText("10040007799");
        btn_back1=(Button) findViewById(R.id.btn_back1);
        btn_next=(Button) findViewById(R.id.btn_next);
        btn_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PaymentActivity1.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payParamsMap1.put("p0_Cmd",et_businessType.getText().toString().trim());
                payParamsMap1.put("p1_MerId",et_mermId.getText().toString().trim());
                payParamsMap1.put("p2_Order",et_orderId.getText().toString().trim());
                payParamsMap1.put("p3_Amt",et_amount.getText().toString().trim());
                payParamsMap1.put("pe_promotionId",et_promotionId.getText().toString().trim());
                payParamsMap1.put("p4_Cur",et_cur.getText().toString().trim());
                payParamsMap1.put("p5_Pid",et_pid.getText().toString().trim());
                payParamsMap1.put("p8_Url",et_url.getText().toString().trim());
                payParamsMap1.put("pe_desc",et_desc.getText().toString().trim());
                Intent intent=new Intent(PaymentActivity1.this,PaymentActivity2.class);
                startActivity(intent);
            }
        });
        super.onCreate(savedInstanceState);
    }
}
