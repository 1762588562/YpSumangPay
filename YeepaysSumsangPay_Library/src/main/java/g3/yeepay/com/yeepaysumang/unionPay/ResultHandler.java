package g3.yeepay.com.yeepaysumang.unionPay;

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
 * @since 16/11/21 上午11:52
 */
public class ResultHandler {
    /**
     * 解析优惠结果
     * @param promotionResult
     * @return
     */
    public static Map<String,String>parsePromotionResult(String promotionResult){
        Map<String,String>map=new HashMap<String, String>();
        if(null==promotionResult||"".equals(promotionResult)){
            return map;
        }else {
            String promotionSplit[]=promotionResult.split("\n");
            for(int i=0;i<promotionSplit.length;i++){
                String promotionParams[]=promotionSplit[i].split("=");
                if(promotionParams.length>1){
                    map.put(promotionParams[0],promotionParams[1]);
                }else {
                    map.put(promotionParams[0],"");
                }

            }
            return map;
        }

    }

    /**
     * 解析订单号
     * @param tnResult
     * @return
     */
    public static String parseTnResult(String tnResult){
        String reslut=null;
        if(null==tnResult||"".equals(tnResult)){
            return reslut;
        }else {
            String tnSplit[]=tnResult.split("\n");
            for(int i=0;i<tnSplit.length;i++){
                if(tnSplit[i].startsWith("p5_ypTn")){
                    String tnParams[]=tnSplit[i].split("=");
                    if(tnParams.length>1){
                        reslut=tnParams[1];
                        return reslut;
                    }else {
                        reslut="";
                        return reslut;
                    }

                }
            }
            return reslut;
        }
    }

    /**
     * 解析错误信息
     * @param result
     * @return
     */
    public static String parseErrorMsgResult(String result){
        String errorResult=null;
        if(null==result||"".equals(result)){
            return errorResult;
        }else {
            String tnSplit[]=result.split("\n");
            for(int i=0;i<tnSplit.length;i++){
                if(tnSplit[i].startsWith("errorMsg")){
                    String tnParams[]=tnSplit[i].split("=");
                    if(tnParams.length>1){
                        errorResult=tnParams[1];
                        return errorResult;
                    }else {
                        errorResult="";
                        return errorResult;
                    }

                }
            }
        }
        return errorResult;
    }
}
