package usi.weixinpush.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * 描述: HTTP 请求工具类
 * @Author wangxianlin
 * @Date 2:27 2022/8/20
 * @param: null
 * @Return
 */
public class HttpClientUtil {

    private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * get 请求
     * @param url
     * @param charset
     * @return
     */
    public static String doGet(String url, String charset){
        /**
         * 1.生成HttpClient对象并设置参数
         */
        HttpClient httpClient = new HttpClient();
        //设置Http连接超时为5秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10000);

        /**
         * 2.生成GetMethod对象并设置参数
         */
        GetMethod getMethod = new GetMethod(url);
        //设置get请求超时为5秒
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 10000);
        //设置请求重试处理，用的是默认的重试处理：请求三次
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

        String response = "";

        /**
         * 3.执行HTTP GET 请求
         */
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            /**
             * 4.判断访问的状态码
             */
            if (statusCode != HttpStatus.SC_OK){
                log.error("请求出错：" + getMethod.getStatusLine());
            }

            /**
             * 5.处理HTTP响应内容
             */
            //HTTP响应头部信息，这里简单打印
            Header[] headers = getMethod.getResponseHeaders();
            for (Header h: headers){
                log.info(h.getName() + "---------------" + h.getValue());
            }
            //读取HTTP响应内容，这里简单打印网页内容
            System.out.println(getMethod.getResponseCharSet());
            //读取为字节数组
            byte[] responseBody =  getMethod.getResponseBody();
            response = new String(responseBody, charset);
            log.info("-----------response:" + response);
            //读取为InputStream，在网页内容数据量大时候推荐使用
            //InputStream response = getMethod.getResponseBodyAsStream();

        } catch (HttpException e) {
            //发生致命的异常，可能是协议不对或者返回的内容有问题
            log.error("请检查输入的URL!");
            e.printStackTrace();
        } catch (IOException e){
            //发生网络异常
            log.error("发生网络异常!");
        }finally {
            /**
             * 6.释放连接
             */
            getMethod.releaseConnection();
        }
        return response;
    }

    /**
     * post请求
     * @param url
     * @param json
     * @return
     */
    public static String doPost(String url, JSONObject json){
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);

        postMethod.addRequestHeader("accept", "*/*");
        postMethod.addRequestHeader("connection", "Keep-Alive");
        //设置json格式传送
        postMethod.addRequestHeader("Content-Type", "application/json;charset=utf-8");
        //必须设置下面这个Header
        postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
        //添加请求参数
        //postMethod.addParameter("param", json.getString("param"));
        StringRequestEntity param = new StringRequestEntity(json.toJSONString());
        postMethod.setRequestEntity(param);
        String res = "";
        try {
            int code = httpClient.executeMethod(postMethod);
            if (code == 200){
                byte[] responseBody = postMethod.getResponseBody();
                res = new String(responseBody, "UTF-8");
                //res = postMethod.getResponseBodyAsString();
                log.info(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
