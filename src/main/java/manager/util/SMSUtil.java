package manager.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.HashMap;
import java.util.Map;

public class SMSUtil {

    private static final String ak = "";
    private static final String sk = "";
    private static final String tc = "";

    public static final Map<String, String> CODEMAP = new HashMap<>();

    public static CommonResponse SendSMS(String code, String phone){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                ak, sk);//自己账号的AccessKey信息
        IAcsClient client = new DefaultAcsClient(profile);
        CommonResponse response = null;
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");  //短信服务的服务接入地址
        request.setSysVersion("2017-05-25");            //API的版本号
        request.setSysAction("SendSms");//API的名称
        request.putQueryParameter("PhoneNumbers", phone);//接收短信的手机号码
        request.putQueryParameter("SignName", "牛曳数字");//短信签名名称
        request.putQueryParameter("TemplateCode", tc);//短信模板ID
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");//短信模板变量对应的实际值
        try {
            response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static void main(String[] args_) throws Exception {
        CommonResponse commonResponse = SendSMS("12132", "15195355289");
        String data = commonResponse.getData();
        JSONObject jsonObject = JSONUtil.parseObj(data);
        String code = jsonObject.getStr("Code");
        System.out.println(code);


    }
}
