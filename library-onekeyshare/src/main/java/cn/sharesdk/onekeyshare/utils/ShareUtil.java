package cn.sharesdk.onekeyshare.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.model.ShareParameter;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import java.util.Map;

/**
 * 分享工具类
 *  Created by yangchunyu
 *  2015/12/4 15:15
 */
public class ShareUtil {
    public static final int TYPE_SINAWB = 1;//新浪微博分享类型
    public static final int TYPE_WECHAT = 2;//微信分享类型
    public static final int TYPE_WECHAT_MOMENT = 3;//朋友圈分享类型
    public static final int TYPE_QQ = 4;//QQ分享类型
    public static final int TYPE_QZONE = 5;//QQ空间分享类型

    @SuppressWarnings("FieldCanBeLocal")
    private final boolean MODE_DIALOG = true; // 设置是否为对话框显示
    @SuppressWarnings("FieldCanBeLocal")
    private final boolean MODE_SSO_AUTHORIZE = false;// 设置是否为SSO授权

    private Context mContext;
    private OnekeyShare.ShareInsertListener mShareCallBack;

    public ShareUtil(Context context) {
        this.mContext = context;
    }

    /**
     * 分享关键入口，用于外界调用分享的方法
     * @param shareParameter 分享参数
     */
    public void showShare(ShareParameter shareParameter){
        final OnekeyShare oks = new OnekeyShare();
        if(shareParameter.getShareParamsMap() == null){
            Toast.makeText(mContext, "分享参数错误,请重新分享!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mShareCallBack!=null){
            oks.setShareInsertListener(mShareCallBack);
        }
        setShareData(shareParameter, oks);
        oks.show(mContext);
    }

    /**
     * 开始分享
     * @param platform 分享平台
     * @param title 分享标题
     * @param text  分享内容
     * @param imageUrl  分享图片
     * @param url   分享链接
     */
    public void showShare(String platform,String title,String text,String imageUrl,String url){
        ShareParameter shareParameter = new ShareParameter();
        if(!TextUtils.isEmpty(platform)){
            shareParameter.setPlatform(platform);
        }
        if(!TextUtils.isEmpty(title)){
            shareParameter.setTitle(title);
        }
        if(!TextUtils.isEmpty(text)){
            shareParameter.setText(text);
        }
        if(!TextUtils.isEmpty(imageUrl)){
            shareParameter.setImageUrl(imageUrl);
        }
        if(!TextUtils.isEmpty(url)){
            if(platform!=null && platform.equals(QZone.NAME) | platform.equals(QQ.NAME)){
                shareParameter.setTitleUrl(url);
            }
            shareParameter.setUrl(url);
        }
        shareParameter.setIsSlite(false);//是否直接分享 false弹出编辑页
        showShare(shareParameter);
    }

    /**
     * 开始分享
     * @param shareType 分享类型
     * @param title 分享标题
     * @param text  分享内容
     * @param imageUrl  分享图片
     * @param url   分享链接
     */
    public void showShare(int shareType,String title,String text,String imageUrl,String url){
        String platform = getPlatformByShareType(shareType);
        if(platform != null){
            showShare(platform,title,text,imageUrl,url);
        }
    }

    /**
     * 根据平台类型获取平台名称
     * @param shareType 平台类型
     * @return 平台名称
     */
    private String getPlatformByShareType(int shareType) {
        switch (shareType) {
            case TYPE_SINAWB:
                return SinaWeibo.NAME;
            case TYPE_WECHAT:
                return Wechat.NAME;
            case TYPE_WECHAT_MOMENT:
                return WechatMoments.NAME;
            case TYPE_QQ:
                return QQ.NAME;
            case TYPE_QZONE:
                return QZone.NAME;
        }
        return null;
    }

    /**
     * 设置分享的参数
     * @param shareParameter 参数集合
     * @param oks   执行分享集成对象
     */
    private void setShareData(ShareParameter shareParameter, OnekeyShare oks) {
        for(Map.Entry<String,Object> entry:
                shareParameter.getShareParamsMap().entrySet()){
            if(entry.getKey().equals(ShareParameter.KEY_TITLE)){
                oks.setTitle(entry.getValue().toString());
            }
            if(entry.getKey().equals(ShareParameter.KEY_TITLE_URL)){
                oks.setTitleUrl(entry.getValue().toString());
            }
            if(entry.getKey().equals(ShareParameter.KEY_TEXT)){
                oks.setText(entry.getValue().toString());
            }
            if(entry.getKey().equals(ShareParameter.KEY_IMG_URL)){
                oks.setImageUrl(entry.getValue().toString());
            }
            if(entry.getKey().equals(ShareParameter.KEY_URL)){
                oks.setUrl(entry.getValue().toString());
            }
            if(entry.getKey().equals(ShareParameter.KEY_COMMENT)){
                oks.setComment(entry.getValue().toString());
            }
            if(entry.getKey().equals(ShareParameter.KEY_SITE)){
                oks.setSite(entry.getValue().toString());
            }
            if(entry.getKey().equals(ShareParameter.KEY_SITE_URL)){
                oks.setSiteUrl(entry.getValue().toString());
            }
            if(entry.getKey().equals(ShareParameter.KEY_IS_SLIENT)){
                oks.setSilent((boolean) entry.getValue());
            }
            if(entry.getKey().equals(ShareParameter.KEY_PLATFORM)){
                oks.setPlatform(entry.getValue().toString());
            }
        }
        //noinspection ConstantConditions
        if(MODE_DIALOG){
            //对话框模式
            oks.setDialogMode();
        }
        //noinspection PointlessBooleanExpression,ConstantConditions
        if(!MODE_SSO_AUTHORIZE){
            //不适用SSO授权
            oks.disableSSOWhenAuthorize();
        }
        // 去自定义不同平台的字段内容
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeUtil());
    }

    /**
     * 设置分享回调监听
     */
    public void setShareCallBack(OnekeyShare.ShareInsertListener shareCallBack) {
        this.mShareCallBack = shareCallBack;
    }

    /**
     * 根据平台获取分享ID
     * @param platform 分享平台
     * @return 分享平台的ID
     */
    public static int getShareTypeByPlatform(Platform platform) {
        if(platform.getName().equals(SinaWeibo.NAME))
            return TYPE_SINAWB;
        if(platform.getName().equals(Wechat.NAME))
            return TYPE_WECHAT;
        if(platform.getName().equals(WechatMoments.NAME))
            return TYPE_WECHAT_MOMENT;
        if(platform.getName().equals(QQ.NAME))
            return TYPE_QQ;
        if(platform.getName().equals(QZone.NAME))
            return TYPE_QZONE;
        return -1;
    }

}
