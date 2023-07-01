package cn.ivfzhou.ssmexample.util;

import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Map;

import cn.ivfzhou.ssmexample.constant.SsmConstant;
import cn.ivfzhou.ssmexample.vo.ResultVO;

@Component
public class SendSMSUtil {

    @Value("${yunpian.apikey}")
    private String apikey;


    /**
     * 发送短信工具类方法
     *
     * @param phone   手机号
     * @param session session对象
     * @return
     */
    public ResultVO sendSMS(String phone, HttpSession session) {
        //初始化clnt,使用单例方式
        YunpianClient clnt = new YunpianClient(apikey).init();
        // 生成验证码
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        code = 888888;
        session.setAttribute(SsmConstant.USER_CODE, code + "");
        //发送短信API
        Map<String, String> param = clnt.newParam(2);
        param.put(YunpianClient.MOBILE, phone);
        param.put(YunpianClient.TEXT, "【云片网】您的验证码是" + code);
        //账户:clnt.user().* 签名:clnt.sign().* 模版:clnt.tpl().* 短信:clnt.sms().* 语音:clnt.voice().* 流量:clnt.flow().* 隐私通话:clnt.call().*
        Result<SmsSingleSend> r = clnt.sms().single_send(param);
        //获取返回结果，返回码:r.getCode(),返回码描述:r.getMsg(),API结果:r.getData(),其他说明:r.getDetail(),调用异常:r.getThrowable()
        Integer c = r.getCode();
        String msg = r.getMsg();
        ResultVO vo = new ResultVO(c, msg, null);
        clnt.close();
        return vo;
    }

}
