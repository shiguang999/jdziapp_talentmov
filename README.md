# jdziapp_talentmov
talentmov

# cms：<br>
\app\app\cms\config\dbs.php  17行开始 填你的苹果cms数据库账号密码<br>

注册金币、分享获得金币 ： <br>
/app/cms/src/app/Model/Model_User.php     第72行 $pass, 'user_points' => 改成你想要的注册金币数,<br>
/app/cms/src/app/Model/Model_User.php     第105行(int)$points + 改成你想要的分享金币数<br>

/app/cms/src/app/Api/Mov.php   版本更新 公告 广告位   470行开始<br>

# APP:
广告位：<br>
ad_splash     开屏广告<br>
ad_home_1到ad_home_4   推荐页广告<br>
ad_detail   播放页广告<br>
ad_player   播放器广告<br>
ad_user_center   个人中心广告<br>

HomeTabFragment 首页<br>
SelfTabFragment 个人中心<br>
OnlineDetailPageActivity 播放页<br>
PlayerPresenter 播放器<br>
ShareTabFragment 分享<br>
TopicTabFragment 专题推荐<br>
