package cn.sharesdk.onekeyshare.utils;

import android.content.Context;
import android.text.TextUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.model.LoginInfo;
import cn.sharesdk.sina.weibo.SinaWeibo;

import java.util.HashMap;

/**
 * 一键登陆工具 用于登陆新浪微博和QQ空间
 * Created by yangchunyu
 * on 2015/12/6.
 */
public class LoginUtil {

    private Context mContext;
    private PlatformActionListener mListener;

    public LoginUtil(Context context) {
        ShareSDK.initSDK(context);
        this.mContext = context;
    }

    /**
     * 注意对于不同的操作 actionListener返回的action是不同的
     * 用户信息：Platform.ACTION_USER_INFOR
     */
    public void setPlatformActionListener(PlatformActionListener listener){
        this.mListener = listener;
    }

    /**
     * 移除授权信息
     */
    public void removeAuthorize(Platform platform){
        //isValid和removeAccount不开启线程，会直接返回。
        if(platform.isValid ()){
            platform.removeAccount(true);
        }
    }

    /**
     * 登陆操作
     * @param platform 所操作平台
     */
    public void login(Platform platform){
        //先移除授权信息
        removeAuthorize(platform);
        if(mListener != null){
            platform.setPlatformActionListener(mListener);
            platform.showUser(null);
        }
    }

    /**
     * 通过平台在客户端的数据来获取用户信息
     * @param platform 所用平台
     * @return 返回用户信息类
     */
    public LoginInfo getUserInfoByDB(Platform platform){
        if(platform.isAuthValid()){
            LoginInfo loginInfo = new LoginInfo();
            PlatformDb platDB = platform.getDb();//获取数平台数据DB
            String id,name,profileImg,token;
            long expiresTime;
            //通过DB获取各种数据
            expiresTime = platDB.getExpiresTime();
            token = platDB.getToken();
            id = platDB.getUserId();
            name = platDB.getUserName();
            profileImg = platDB.getUserIcon();
            setDataToLgoinInfo(loginInfo, id, name, profileImg, token, expiresTime);
            if(TextUtils.isEmpty(id)){
                return null;
            }
            return loginInfo;
        }
        return null;
    }

    /**
     * 通过登录成功的回调函数中的HashMap获取用户的信息
     * @param res 当一个类实现了PlatformActionListener接口后
     *            在onComplete(Platform,action,HashMap<String,Object>)方法中调用
     * @return 返回用户信息类 {@link LoginInfo}
     */
    public LoginInfo getUserInfoByRes(HashMap<String, Object> res){
        LoginInfo loginInfo = new LoginInfo();
        String id,name,profileImg,token;
        long expiresTime;
        //解析部分用户资料字段
        id=res.get("id").toString();//ID
        name=res.get("name").toString();//用户名
        profileImg=res.get("profile_image_url").toString();//头像链接
        token=res.get("token").toString();
        expiresTime = (Long)res.get("expres_time");
        setDataToLgoinInfo(loginInfo, id, name, profileImg, token, expiresTime);
        if(TextUtils.isEmpty(id)){
            return null;
        }
        return loginInfo;
    }

    /**
     * 将参数信息设置给LoginInfo对象
     */
    private void setDataToLgoinInfo(LoginInfo loginInfo, String id, String name, String profileImg, String token, long expiresTime) {
        loginInfo.setExprestime(expiresTime);
        loginInfo.setToken(token);
        loginInfo.setUid(id);
        loginInfo.setName(name);
        loginInfo.setProfileImg(profileImg);
    }

    /**
     * 判断是否安装新浪微博客户端
     * @return true 安装新浪微博客户端 false 未安装
     */
    @SuppressWarnings("unused")
    public boolean isWeiboInstalled(){
        Platform platform = ShareSDK.getPlatform(mContext, SinaWeibo.NAME);
        return platform.isClientValid();
    }

    /**
     * 取消新浪微博授权
     */
    public void logoutSina(){
        //TODO 目前无法移除服务器授权  ShareSDK不支持。。。等待他们出新版本
        Platform platform = ShareSDK.getPlatform(mContext, SinaWeibo.NAME);
        platform.removeAccount();
    }
}
