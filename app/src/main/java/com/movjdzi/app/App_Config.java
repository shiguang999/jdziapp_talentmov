package com.movjdzi.app;

/**
 * @author huangyong
 * createTime 2019-10-13
 * 全局的自定义配置
 * 根据自己实际情况修改
 */
public class App_Config {


    /**
     * 服务器所有请求依赖的主域名，为cms接口系统安装的地址
     */
    public static final String BASE_URL = "http://111.67.193.41:6061/";//金豆子视频
//    public static final String BASE_URL = "http://160.19.51.137/";//海鸥影视

    /**
     * 主分类配置，不建议修改
     */
    public static final String[] TAB_ARR = {"推荐", "电影", "电视剧", "动漫", "综艺"};


    /**
     * 友盟统计key
     */
//    public static final String UMENKEY = "5d904dcb3fc195c774000b0e";//追剧达人
    public static final String UMENKEY = "5e2306e4cb23d2a435000295";//金豆子视频
    /**
     * 友盟统计app渠道名
     */
    public static final String UMEN_APP_NAME = "movjdzi";
    /**
     * 友盟推送app key
     */
//    public static final String UMEN_PUSH_KEY = "4b3893e95500500c2c2f88069e56da0c";
    public static final String UMEN_PUSH_KEY = "1aea0bd74a1510657a6061344a70a4b4";


    /**
     * qq分享 appId
     */
//    public static final String QQ_APP_ID = "1109823571";//追剧达人
//    public static final String QQ_APP_ID = "1105923666";//金豆子视频
    public static final String QQ_APP_ID = "1105923666";//金豆子视频
    public static final String QQ_APP_SCOP = "get_user_info,"
            + "get_simple_userinfo,"
            + "add_share,"
            + "add_topic,"
            + "add_pic_t";
    /**
     * 微信分享、朋友圈分享 appId
     * 替换为自己申请的账户信息
     */
    /*public static final String WEICHAT_APP_ID = "wxd2069b96357bdfe7";//追剧达人
    public static final String WEICHAT_APP_SECRET = "89bc6b30bb7c750798296a7897bf8235";*/
    /*public static final String WEICHAT_APP_ID = "wx0ba98111e3acf48c";//金豆子视频
    public static final String WEICHAT_APP_SECRET = "9d04e4e393c668c1ce8542c075b1721b";*/
    public static final String WEICHAT_APP_ID = "wx0ba98111e3acf48c";//金豆子视频
    public static final String WEICHAT_APP_SECRET = "9d04e4e393c668c1ce8542c075b1721b";

    /**
     * 微博分享 配置
     */
    public static final String WEIBO_APP_KEY = "3881135066";//金豆子视频
    public static final String WEIBO_APP_SCOP = "friendships_groups_read,"
            + "friendships_groups_write,"
            + "statuses_to_me_read,"
            + "follow_app_official_microblog";
    public static final String WEIBO_REDIRECT_URL = "xxxxxxxxxxxx";
    
    
    /**
     * 横条广告配置，将html文件放到assets目录里
     */
    public static final String AD_BANNER = "file:///android_asset/ad_banner.html";


    /**
     * 开屏页地址
     */
    public static final String SPLAH_URL = BASE_URL;

    /**
     * 内置头像，可自己替换
     */
    public static final int[] ICON_GROUP = {
            R.drawable.ic_head_1,
            R.drawable.ic_head_2,
            R.drawable.ic_head_3,
            R.drawable.ic_head_4,
            R.drawable.ic_head_5,
            R.drawable.ic_head_6,
            R.drawable.ic_head_7,
            R.drawable.ic_head_8
    };
    /**
     * 服务器里这个接口系统的安装目录
     */
    public static final String SERVER_PATH = "app/cms/";
    /**
     * 会员显示配置，不建议修改，如果要改，需要同时改后台接口
     */
    public static final String VIP_1 = "VIP会员1天";
    public static final String VIP_2 = "VIP会员1周";
    public static final String VIP_3 = "VIP会员1月";
    public static final String VIP_4 = "VIP会员1年";


    /**
     * 分类筛选页面的形式配置，这个就自己写吧，读服务器没意义，看下影片都有哪些形式
     */
    public static final String[] MAIN_CONFIG = {
            "全部形式",
            "电影",
            "电视剧",
            "动漫",
            "综艺"
    };

    /**
     * 分类筛选页面的年代配置，这个就自己写吧，读服务器没意义
     */
    public static final String[] YEAR_CONFIG = {
            "全部年代",
            "2020",
            "2019",
            "2018",
            "2017",
            "2016",
            "2016以前"
    };

    /**
     * 分类筛选页面的地区配置，这个就自己写吧，读服务器没意义，看下影片都有哪些地区
     */
    public static final String[] AREA_CONFIG = {
            "全部地区",
            "大陆",
            "美国",
            "法国",
            "印度",
            "英国",
            "日本",
            "泰国",
            "韩国",
            "香港",
            "台湾",
            "西班牙",
            "加拿大",
            "其他",
            "海外"
    };

    public static final String JUMP_URL_3 = BASE_URL;
    public static final String JUMP_URL_2 = BASE_URL + "app/fzlc/index.html";//成长历程
    public static final String JUMP_URL_1 = BASE_URL;//官网
    public static String JUMP_URL_TEXT = "详情";//详情
}
