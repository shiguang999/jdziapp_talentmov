# jdziapp_talentmov APP说明书
  用到的开发工具android studio<br>
  
  后端配合jdziapp_cms使用 https://github.com/shiguang999/jdziapp_cms.git<br>

# APP 修改:
## 1、前端源码要改的地方：
+ 搜索：金豆子影视  替换成自己的app名
+ 搜索http://111.67.193.41:6061/  替换成自己的域名（后面必须带/）
+ 其他修改项自己搜索着找就好了

+ 分享的图片：\talentmov\app\src\main\res\drawable-xxhdpi\share.png
+ Logo地址：\talentmov\app\src\main\res\mipmap-xxhdpi\ticon2.png  自己替换
+ 对接网站 以及分享接口之类：<br>
\talentmov\app\src\main\java\com\movtalent\app\App_Config.java<br>
72行  对接的网站<br>
90行  对接的源码的地址<br>
121-123行  电视直播  小说  漫画的对应网址更改<br>
\talentmov\modules\playerlib\src\main\res\layout\layout_auth_cover.xml<br>
第59行  游客观看时间文字  <br>
\talentmov\modules\playerlib\src\main\java\com\media\playerlib\cover\AuthCover.java<br>
第103行  60*1000  就是60秒   时间自己改<br>
+ 修改顶部导航颜色：<br>
~app\src\main\res\values\colors.xml<br>

## 2、推荐位设置：
+ 品质好剧，必看榜单  推荐1 修改为：WEB端热播推荐 推荐1<br>
+ 正在上映  推荐7<br>
+ 火热更新，好剧不断  推荐2<br>
+ 最新电影  推荐3<br>
+ 最新电视剧  推荐4<br>
+ 最新动漫  推荐5<br>
+ 最新综艺  推荐6  （名字源码我改了幻灯片推荐   名字你们自己搜索改）<br>
+ 幻灯片  推荐9<br>

## 3、广告位设置：
+ ad_splash     开屏广告<br>
+ ad_home_1到ad_home_4   推荐页广告<br>
+ ad_detail   播放页广告<br>
+ ad_player   播放器广告<br>
+ ad_user_center   个人中心广告<br>

## 4、相关配置：
> 在 com.movjdzi.app.view 下JAVA类
+ HomeTabFragment 首页<br>
+ SelfTabFragment 个人中心<br>
+ OnlineDetailPageActivity 播放页<br>
+ PlayerPresenter 播放器<br>
+ ShareTabFragment 分享<br>
+ TopicTabFragment 专题推荐<br>

## 5、编译：
+ 右边点击 gradle --> app --> Tasks --> build --> assembleRelease
+ \talentmov\app\build\outputs\apk\  编译了在这个文件夹里
