package g3.yeepay.com.yeepaySamsungPay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import g3.yeepay.com.yeepaysumang.unionPay.UnionPay;

/**
 * title: <br/>
 * description:描述<br/>
 * Copyright: Copyright (c)2016<br/>
 * Company: 易宝支付(YeePay)<br/>
 *
 * @author guoliang.li
 * @version 1.0.0
 * @since 16/12/1 下午4:16
 */
public class PaymentActivity extends Activity {
    private EditText et_tn;
    private EditText et_mode_tnpayment;
    private Button btn_samsungPay;
    private Button btn_unionPay;
    private Button btn_back3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tn_payment);
        et_tn=(EditText) findViewById(R.id.et_tn);
        et_mode_tnpayment=(EditText) findViewById(R.id.et_mode_tnpayment);
        btn_samsungPay=(Button) findViewById(R.id.btn_samsungPay);
        btn_unionPay=(Button)findViewById(R.id.btn_unionPay);
        btn_back3=(Button) findViewById(R.id.btn_back3);
        btn_back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PaymentActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btn_unionPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UnionPay.unionPay(PaymentActivity.this,et_tn.getText().toString(),et_mode_tnpayment.getText().toString());
            }
        });
        btn_samsungPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UnionPay.samsungPay(PaymentActivity.this,et_tn.getText().toString(),et_mode_tnpayment.getText().toString());
            }
        });
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
                    Toast.makeText(PaymentActivity.this, "支付成功", Toast.LENGTH_LONG).show();
                    break;
                case 0x12:
                    Toast.makeText(PaymentActivity.this,"支付失败",Toast.LENGTH_LONG).show();
                    break;
                case 0x13:
                    Toast.makeText(PaymentActivity.this,"支付取消",Toast.LENGTH_LONG).show();
                    break;

            }
            super.handleMessage(msg);
        }
    };
}
