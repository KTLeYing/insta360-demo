package com.mzl.insta360demo.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mzl.insta360demo.util.DateUtil;
import com.mzl.insta360demo.util.EmailUtil;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

/**
 * @Description: 抢公司商品的脚本任务
 * @Author: mzl
 */
@Component
public class RobProductJob {

    private static final Logger log = LoggerFactory.getLogger(RobProductJob.class);

    private static final OkHttpClient CLIENT = new OkHttpClient();

    private static String randCode;

    @PostConstruct
    public void init() throws Exception {
        getCookie4RandCode();
        // 启动后立即执行一次
        robProductX3();
        robProductX4();
    }

    /**
     * 抢X3任务，每隔一分钟执行一次
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void robProductX3(){
        log.info("抢X3任务开始执行...当前randCode={}", randCode);

        if (StringUtils.isEmpty(randCode)){
            log.warn("抢X3任务, 返回randCode为空,不做后续处理...");
        }

        try {
            // 请求获取产品信息
            String url = "http://121.15.203.178:9080/api/public/browser/data/161?pageSize=10&current=1&min=1&max=10&companyId=1&con11160_value=%20X3&isFromAdvanceSearch=1&type=browser.wpmc&fielddbtype=browser.wpmc&currenttime=1717640918657&nodataloading=0&requestid=-1&workflowid=78&wfid=78&billid=-44&isbill=1&f_weaver_belongto_userid=8108&f_weaver_belongto_usertype=0&wf_isagent=0&wf_beagenter=0&wfTestStr&fieldid=9016&viewtype=1&fromModule=workflow&wfCreater=8108&disabledConditionCache=true&__random__=1717641425851";
            String productResponseStr = getProductInfo(url);

            // 请求响应结果的处理
            String productFlag = "P.AQA00101";
            String productType = "Insta360 X3";
            handleResponseData(productResponseStr, productFlag, productType);
        } catch (Exception e) {
            log.error("抢X3任务执行处理失败...", e);
        }

        log.info("抢X3任务执行完成...");
    }

    /**
     * 抢X4任务，每隔一分钟执行一次
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void robProductX4(){
        log.info("抢X4任务开始执行...当前randCode={}", randCode);

        if (StringUtils.isEmpty(randCode)){
            log.warn("抢X4任务, 返回randCode为空,不做后续处理...");
        }

        try {
            // 请求获取产品信息
            String url = "http://121.15.203.178:9080/api/public/browser/data/161?pageSize=10&current=1&min=1&max=10&companyId=1&con11160_value=X4&isFromAdvanceSearch=1&type=browser.wpmc&fielddbtype=browser.wpmc&currenttime=1717876264647&nodataloading=0&requestid=-1&workflowid=78&wfid=78&billid=-44&isbill=1&f_weaver_belongto_userid=8108&f_weaver_belongto_usertype=0&wf_isagent=0&wf_beagenter=0&wfTestStr=&fieldid=9016&viewtype=1&fromModule=workflow&wfCreater=8108&disabledConditionCache=true&__random__=1717877208888";
            String productResponseStr = getProductInfo(url);

            // 请求响应结果的处理
            String productFlag = "P.ABM0000101";
            String productType = "Insta360 X4";
            handleResponseData(productResponseStr, productFlag, productType);
        } catch (Exception e) {
            log.error("抢X4任务执行处理失败...", e);
        }

        log.info("抢X4任务执行完成...");
    }

    private static String getProductInfo(String url) throws Exception {
        String cookie = "__clusterSessionCookieName=B7D9FE880BBC329A40B69CCF119FED21; ecology_JSessionid=aaaquFbjkjCfF7fEbmX-y; JSESSIONID=aaaquFbjkjCfF7fEbmX-y; loginidweaver=8108; languageidweaver=7; Systemlanguid=7; __randcode__=" + randCode;
        log.info("抢产品任务，请求获取商品信息, 当前cookie={}", cookie);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", cookie)
                .build();

        try(Response response = CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("抢产品任务，请求获取商品信息失败, 响应体=" + response);
            }

            String responseStr = response.body().string();
            log.info("抢产品任务，请求获取商品信息响应结果, 响应体={}", responseStr);

            JSONObject responseObj = JSON.parseObject(responseStr);
            String errorCode = responseObj.getString("errorCode");
            // 登录超时，则重新登录获取新的cookie(randCode)
            if ("002".equals(errorCode)){
                log.warn("抢产品任务, 请求获取商品信息登录信息超时, 重新获取新的cookie(randCode)...");
                getCookie4RandCode();
                throw new Exception("抢产品任务，请求获取商品信息登录信息超时, 重新获取新的cookie(randCode)");
            }

            return responseStr;
        }catch (IOException e) {
            log.error("抢产品任务，请求获取商品信息失败...");
            throw e;
        }
    }

    private static void getCookie4RandCode() throws Exception {
        String url = "http://121.15.203.178:9080/rsa/weaver.rsa.GetRsaInfo?ts=" + System.currentTimeMillis();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("抢产品任务，请求获取randCode失败, 响应体=" + response);
            }

            // 获取并存储 Cookie
            List<Cookie> cookies = Cookie.parseAll(request.url(), response.headers());
            // 查找 __randcode__ 的Cookie
            randCode = cookies.stream()
                    .filter(cookie -> "__randcode__".equals(cookie.name()))
                    .map(Cookie::value)
                    .findFirst()
                    .orElseThrow(() -> new Exception("__randcode__ 在新cookies找不到"));
        }catch (IOException e) {
            log.error("抢产品任务, 请求获取randCode失败...", e);
            throw e;
        }

        log.info("抢产品任务, 请求获取randCode, 更新randCode成功, randCode={}", randCode);
    }

    private void handleResponseData(String responseStr, String productFlag, String productType) throws Exception {
        JSONObject responseObj = JSON.parseObject(responseStr);
        List<JSONObject> dataObjs = (List<JSONObject>) responseObj.get("datas");

        for (JSONObject data : dataObjs){
            String curProductNameFlag = (String) data.get("gylh");
            if (productFlag.equals(curProductNameFlag)){
                String restNum = (String) data.get("xcsl");
                log.info("抢产品任务, 执行结果: 产品现存数restNum={}, productFlag={}, productType={}", restNum, productFlag, productType);

                // 每天在指定时间范围内发送邮件通知【10:00~10:02】
                if (DateUtil.isAtTimeScope(10, 0, 10, 2)){
                    String resultMessage = String.format("抢产品任务, 执行结果: productType=%s, restNum=%s, productFlag=%s", productType, restNum, productFlag);
                    EmailUtil.sendEmail(resultMessage);
                }

                if (Integer.parseInt(restNum) > 0){
                    // 产品现存数 > 0, 则发邮件通知
                    String message = String.format("产品现存数大于0, 可以开始抢了...productType=%s, restNum=%s, productFlag=%s", productType, restNum, productFlag);
                    EmailUtil.sendEmail(message);
                    log.info("产品现存数大于0, 可以开始抢了...restNum={}, productFlag={}, productType={}", restNum, productFlag, productType);
                }
            }
        }
    }

}