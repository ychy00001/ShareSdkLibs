#ShareSDKDemo 
##用于外部调用的库 直接集成可以选择第三方分享或登录

1. library-onekeyshare 依赖 library-sharesdk
2. app 依赖 library-onekeyshare
3. onkeyshare库中主要在utils包下LoginUtil  ShareUtil
4. 要分享可以调用ShareUtil 中showShare方法
5. 要登录可以调用LoginUtil 中login方法
6. AndroidManifest.xml中需要添加权限
7. <uses-permission android:name="android.permission.GET_TASKS"/>
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    <uses-permission android:name="android.permission.MANAGE_ACCOUNTS"/>
    <uses-permission android:name="android.permission.GET_ACCOUNTS"/>
8. 注意如果导入库的时候出现R文件找不到 查找项目所有的资源文件 看是否有错误
9. 目前只支持新浪微博、微信、朋友圈、QQ、QQ空间分享 
10. 注意本项目在library-onekeyshare中assets文件下添加的ShareSDK为本机签名的配置信息，需要在里面配置ShareSDK的appkey
11. 各种平台的Key id secret都需要配置成自己的
12. 