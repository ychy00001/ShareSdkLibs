package com.smalltown.sharesdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.model.LoginInfo;
import cn.sharesdk.onekeyshare.model.ShareParameter;
import cn.sharesdk.onekeyshare.utils.LoginUtil;
import cn.sharesdk.onekeyshare.utils.ShareUtil;
import cn.sharesdk.sina.weibo.SinaWeibo;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private LoginUtil loginUtil;
    private TextView tv_authority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShareSDK.initSDK(this);
        tv_authority = ( TextView) findViewById(R.id.tv_authority);
    }

    public void shareWB(View view) {
        /**
         * 1.创建分享工具类
         */
        ShareUtil shareUtil = new ShareUtil(this);
        /**
         * 2.设置分享参数
         */
        ShareParameter shareParameter = new ShareParameter();
        shareParameter.setTitle("新浪微博分享");
        shareParameter.setText("集成分享测试哈哈");
        shareParameter.setPlatform(SinaWeibo.NAME);
        shareParameter.setIsSlite(false);
        /**
         * 3.开始分享
         */
        shareUtil.showShare(shareParameter);
    }

    public void loginWB(View view) {
        loginUtil = new LoginUtil(this);
        Platform weibo = ShareSDK.getPlatform(this,SinaWeibo.NAME);
        loginUtil.setPlatformActionListener(new LoginListener());
        loginUtil.login(weibo);
    }

    public void removeAuthority(View view) {
        Platform platform = ShareSDK.getPlatform(this, SinaWeibo.NAME);
        platform.removeAccount(true);
        //        LoginInfo info = loginUtil.getUserInfoByDB(platform);
        //        if(info != null){
        //            System.out.println("数据库获取:"+info);
        //        }
    }

    class LoginListener implements PlatformActionListener {
        @Override
        public void onComplete(final Platform platform, int action, HashMap<String, Object> res) {
            if(action == Platform.ACTION_USER_INFOR){
                System.out.println(platform.getName() + ":" + action);
                LoginInfo info = loginUtil.getUserInfoByDB(platform);
                if(info != null){
                    //tv_userinfo.setText(info);
                    System.out.println("数据库获取:"+info.toString());
                }else{
                    info = loginUtil.getUserInfoByRes(res);
                    System.out.println("登陆获取:"+info.toString());
                }

                if(info == null){
                    //用户信息获取失败 请重试
                    System.out.println("用户信息获取失败");
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(platform.isAuthValid()){
                        tv_authority.setText("授权");
                    }else{
                        tv_authority.setText("未授权");
                    }
                }
            });
        }

        @Override
        public void onCancel(Platform platform, int i) {
            System.out.println("登陆取消");
        }
        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            System.out.println("登陆错误"+" 错误码:"+i+"/n"+throwable.getMessage());
        }
    }
}
