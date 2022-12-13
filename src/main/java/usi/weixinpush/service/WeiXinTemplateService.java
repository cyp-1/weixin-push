package usi.weixinpush.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usi.weixinpush.util.CommonnUtil;
import usi.weixinpush.util.ConfigUtil;
import usi.weixinpush.util.HttpClientUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;

public class WeiXinTemplateService {

    private static Logger log = LoggerFactory.getLogger(WeiXinTemplateService.class);

    /** 获取TOKEN URL */
    public static final String GET_ACCESS_TOKEN_URL = " https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    /** 推送模板 URL */
    public static final String PUSH_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
    /** 推送模板 URL */
    public static final   String APP_ID = ConfigUtil.getValue("APP_ID");
    public static final  String APP_SECRET = ConfigUtil.getValue("APP_SECRET");
    /** 发送给谁 OPENID */
    public static final String TO_USER = ConfigUtil.getValue("TO_USER");
    /** 推送的模板ID */
    public static final String TEMPLATE_ID = ConfigUtil.getValue("TEMPLATE_ID");

    /**
     * 描述: 获取用户Token
     * @Author wangxianlin
     * @Date 2:31 2022/8/20
     * @param:
     * @Return void
     */
    public static String getAccessToken(){
        String accessTokenUrl = GET_ACCESS_TOKEN_URL + "&appid=" + APP_ID + "&secret=" + APP_SECRET;
        String res = HttpClientUtil.doGet(accessTokenUrl,"utf-8");
        JSONObject json = JSON.parseObject(res);
        log.info("token 返回数据：[{}]",json.toString());
        return json.getString("access_token");
    }

    /**
     * 描述: 推送模板消息
     * @Author wangxianlin
     * @Date 2:32 2022/8/20
     * @param:
     * @Return void
     */
    public static void pushWeiXinTemplate(){
        String accessTokenUrl = PUSH_TEMPLATE_URL + getAccessToken();
        JSONObject json = new JSONObject();
        // 接收用户的OPENID
        json.put("touser",TO_USER);
        // 模板ID
        json.put("template_id",TEMPLATE_ID);
        // 消息唯一标识
        json.put("client_msg_id",System.currentTimeMillis()+"");
        // 内容
        JSONObject data = new JSONObject();
        data.put("today",getJSON(CommonnUtil.getNow(),"#E9FB8A"));
        data.put("city",getJSON("湛江","#0080FF"));
        // 距离我的生日
        try {
//            data.put("a",getJSON(CommonnUtil.calculatePastDays("2022-08-16"),"#A3C6FC"));
            data.put("withU",getJSON(CommonnUtil.calculatePastDays("2021-11-20"),"#A3C6FC"));
            data.put("sheBir",getJSON(CommonnUtil.calculateBirth("2000-01-22"),"#FB2413"));
            data.put("myBir",getJSON(CommonnUtil.calculateBirth("1998-11-15"),"#DEFDAB "));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 土味情话
        data.put("tuQ",getJSON(getEarthyLoveStory3(),"#FDE050"));
        // 笑话
        data.put("joK",getJSON(getJokes(),"#44b41f"));
        // 获取每日天气
        try {
            JSONObject weatherJson = getEveryWeather();
            // 星期几
            data.put("week",getJSON((String)(weatherJson.getJSONObject("info").getString("week")),"#E9FB8A"));
            // 天气
            data.put("type",getJSON((String)(weatherJson.getJSONObject("info").getString("type")),"#AC58FA"));
            // 最高温度
            data.put("height",getJSON((String)(weatherJson.getJSONObject("info").getString("high")),"#F94E29"));
            // 最低温度
            data.put("low",getJSON((String)(weatherJson.getJSONObject("info").getString("low")),"#6698FF"));
            // 风向
            data.put("fengxiang",getJSON((String)(weatherJson.getJSONObject("info").getString("fengxiang")),"#A7FFF8"));
            // 建议
            data.put("tip",getJSON((String)(weatherJson.getJSONObject("info").getString("tip")),"#969C9D"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        json.put("data",data);
        log.info("请求数据：[{}]",json.toString());
        String res = HttpClientUtil.doPost(accessTokenUrl,json);
        JSONObject jsonRes = JSON.parseObject(res);
        log.info("获取模板列表 返回数据：[{}]",jsonRes.toString());
    }

    /**
     * 描述:  获取笑话
     */
    public static String getJokes(){
        String tu_url = "https://api.vvhan.com/api/joke?type=json";
        String res = HttpClientUtil.doGet(tu_url,"UTF-8");
        String onlyJok = res.replaceAll(".*\"joke\":\"(.*)\".*","$1");
//        log.info("获取笑话 返回数据：[{}]",onlyJok);
        return onlyJok;
    }

    public static void main(String[] args) {
        getJokes();
        getEarthyLoveStory3();
    }

    /**
     * 描述:  获取情话  TODO 无法在腾讯云服务器获取到
     * @Author wangxianlin
     * @Date 0:52 2022/8/23
     * @Return java.lang.String
     */
    public static String getEarthyLoveStory(){
        String tu_url = "https://api.lovelive.tools/api/SweetNothings";
        String res = HttpClientUtil.doGet(tu_url,"UTF-8");
        log.info("获取土味情话 返回数据：[{}]",res);
        return res;
    }


    /**
     * 描述: 获取情话  TODO 无法在腾讯云服务器获取到
     * @Author wangxianlin
     * @Date 2:32 2022/8/20
     * @Return void
     */
    public static String getEarthyLoveStory2(){
        String tu_url = "https://api.uomg.com/api/rand.qinghua?format=text";
        String res = HttpClientUtil.doGet(tu_url,"UTF-8");
        log.info("获取土味情话 返回数据：[{}]",res);
        return res;
    }


    /**
     * 描述: 获取情话 TODO 可以再腾讯云服务器获取到
     * @Author wangxianlin
     * @Date 2:32 2022/8/20
     * @Return void
     */
    public static String getEarthyLoveStory3(){
        String tu_url = "https://api.vvhan.com/api/love";
        String res = HttpClientUtil.doGet(tu_url,"UTF-8");
//        log.info("获取土味情话 返回数据：[{}]",res);
        return res;
    }


    /**
     * 描述: 获取每日天气
     * @Author wangxianlin
     * @Date 2:32 2022/8/20
     * @param:
     * @Return void
     */
    public static JSONObject getEveryWeather() throws UnsupportedEncodingException {
        String city = URLEncoder.encode("湛江", "UTF-8");
        String url  = "https://api.vvhan.com/api/weather?city="+city;
        String res = HttpClientUtil.doGet((url),"UTF-8");
        JSONObject jsonRes = JSON.parseObject(res);
        log.info("获取每日天气：[{}]",jsonRes.toString());
        return jsonRes;
    }

    public static JSONObject getJSON(String value,String color){
        JSONObject data = new JSONObject();
        data.put("value",value);
        data.put("color",color);
        return data;
    }

}
