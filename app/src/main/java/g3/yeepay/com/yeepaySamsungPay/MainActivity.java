package g3.yeepay.com.yeepaySamsungPay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    public Button btn_pay;
    public Button btn_prefential;
    private Button btn_tnCode;
    public TextView tv_showPrefential;
    private TextView tv_version;
    Context mContext;


//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case 0x11:
//                    Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_LONG).show();
//                    break;
//                case 0x12:
//                    Toast.makeText(MainActivity.this,"支付失败",Toast.LENGTH_LO NG).show();
//                    break;
//            }
//
//            super.handleMessage(msg);
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        btn_pay=(Button)findViewById(R.id.btn_pay);
        btn_prefential=(Button)findViewById(R.id.btn_prefential);
        btn_tnCode=(Button)findViewById(R.id.btn_tnCode);
        btn_tnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PaymentActivity.class);
                startActivity(intent);
            }
        });
        tv_showPrefential=(TextView) findViewById(R.id.tv_prefentialAmt);
        tv_version=(TextView) findViewById(R.id.tv_version);
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PaymentActivity1.class);
                startActivity(intent);
//                UnionPay.payment1(MainActivity.this,getTnParams());
            }
        });
        btn_prefential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PrefrentialActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            finish();
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
    }

    private Map<String,String> getTnParams(){
        Map<String,String>tnMap=new HashMap<String, String>();
        tnMap.put("p0_Cmd","SpayOrder");
        tnMap.put("p1_MerId","10040007799");
        tnMap.put("p2_Order","14797131101150");
        tnMap.put("p3_Amt","100");
        tnMap.put("p3_PromotionAmt","10");
        tnMap.put("p4_Cur","CNY");
        tnMap.put("p8_Url","http://qa.yeepay.com/");
        tnMap.put("pr_NeedResponse","1");
        tnMap.put("p5_Pid","TEST");
        tnMap.put("hmac","2460c4c64fef8a6a4c17c4bf630fb384");
        return tnMap;
    }
}
