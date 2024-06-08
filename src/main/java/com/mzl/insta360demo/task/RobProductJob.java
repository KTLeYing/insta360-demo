package com.mzl.insta360demo.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mzl.insta360demo.util.DateUtil;
import com.mzl.insta360demo.util.EmailUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

/**
 * @Description: 抢公司商品的脚本任务
 * @Author: mzl
 */
@Component
public class RobProductJob {

    private static final Logger log = LoggerFactory.getLogger(RobProductJob.class);

    /**
     * 抢X3任务，每隔一分钟执行一次
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void robProductX3(){
        log.info("抢X3任务开始执行...");

        OkHttpClient client = new OkHttpClient();

        String url = "http://121.15.203.178:9080/api/public/browser/data/161?pageSize=10&current=1&min=1&max=10&companyId=1&con11160_value=%20X3&isFromAdvanceSearch=1&type=browser.wpmc&fielddbtype=browser.wpmc&currenttime=1717640918657&nodataloading=0&requestid=-1&workflowid=78&wfid=78&billid=-44&isbill=1&f_weaver_belongto_userid=8108&f_weaver_belongto_usertype=0&wf_isagent=0&wf_beagenter=0&wfTestStr&fieldid=9016&viewtype=1&fromModule=workflow&wfCreater=8108&disabledConditionCache=true&__random__=1717641425851";
        String cookie = "__clusterSessionCookieName=B7D9FE880BBC329A40B69CCF119FED21; ecology_JSessionid=aaaquFbjkjCfF7fEbmX-y; JSESSIONID=aaaquFbjkjCfF7fEbmX-y; loginidweaver=8108; languageidweaver=7; __randcode__=a4370f87-765b-4423-b19a-8edf849db2fd";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", cookie)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("抢X3任务请求失败, 响应体=" + response);
            }

            String responseStr = response.body().string();
            log.info("抢X3任务请求响应结果, 响应体={}", responseStr);

            // 请求响应结果的处理
            handleResponseData(responseStr);
        }catch (IOException | MessagingException e) {
            log.error("抢X3任务执行处理失败...", e);
        }

        log.info("抢X3任务执行完成...");
    }

    private void handleResponseData(String responseStr) throws MessagingException, IOException {
        JSONObject responseObj = JSON.parseObject(responseStr);
        List<JSONObject> dataObjs = (List<JSONObject>) responseObj.get("datas");
        for (JSONObject data : dataObjs){
            String x3NameFlag = "P.AQA00101";
            String curProductNameFlag = (String) data.get("gylh");
            if (x3NameFlag.equals(curProductNameFlag)){
                String restNum = (String) data.get("xcsl");
                log.info("抢X3任务执行结果, X3现存数restNum={}, x3NameFlag={}", restNum, x3NameFlag);

                // 每天在指定时间范围内发送邮件通知【10:00~10:02】
                if (DateUtil.isAtTimeScope(10, 0, 10, 2)){
                    String resultMessage = String.format("抢X3任务执行结果, X3现存数restNum=%s, x3NameFlag=%s", restNum, x3NameFlag);
                    EmailUtil.sendEmail(resultMessage);
                }

                if (Integer.parseInt(restNum) > 0){
                    // X3现存数 > 0, 则发邮件通知
                    String message = String.format("X3现存数大于0, 可以开始抢了...restNum=%s, x3NameFlag=%s", restNum, x3NameFlag);
                    EmailUtil.sendEmail(message);
                    log.info("X3现存数大于0, 可以开始抢了...restNum={}, x3NameFlag={}", restNum, x3NameFlag);
                }
            }
        }
    }

}