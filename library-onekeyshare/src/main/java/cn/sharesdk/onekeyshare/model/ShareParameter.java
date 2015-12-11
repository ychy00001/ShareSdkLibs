package cn.sharesdk.onekeyshare.model;

import android.text.TextUtils;
import cn.sharesdk.tencent.qzone.QZone;

import java.util.HashMap;

/**
 * 分享参数对象:
 * 用于记录要分享的数据内容
 * Created by yangchunyu
 * on 2015/12/4 15:20
 */
public class ShareParameter {
    /**
     * title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
     * titleUrl是标题的网络链接，仅在人人网和QQ空间使用
     * text是分享文本，所有平台都需要这个字段
     * imageUrl是网络图片的路径，Linked-In以外的平台都支持此参数
     * 注:(imageUrl 新浪微博需要高级权限)
     * url仅在微信（包括好友和朋友圈）中使用 点击后的链接网址
     * comment是我对这条分享的评论，仅在人人网和QQ空间使用
     * site是分享此内容的网站名称，仅在QQ空间使用
     * siteUrl是分享此内容的网站地址，仅在QQ空间使用
     */
    public static final String KEY_TITLE = "title";
    public static final String KEY_TITLE_URL = "titleUrl";
    public static final String KEY_TEXT = "text";
    public static final String KEY_IMG_URL = "imgUrl";
    public static final String KEY_URL = "url";
    public static final String KEY_COMMENT = "comment";
    public static final String KEY_SITE = "site";
    public static final String KEY_SITE_URL = "siteUrl";

    public static final String KEY_IS_SLIENT = "isSlient";//是否直接分享 默认为True
    public static final String KEY_PLATFORM = "platform";//分享的平台 例如：SinaWeibo.NAME

    private HashMap<String, Object> shareParamsMap;

    public ShareParameter() {
        shareParamsMap = new HashMap<>();
    }

    public void setTitle(String title) {
        shareParamsMap.put(KEY_TITLE, title);
    }

    public String getTitle(){
        return shareParamsMap.containsKey(KEY_TEXT) ? String.valueOf(shareParamsMap.get(KEY_TEXT)) : null;
    }

    public void setTitleUrl(String titleUrl) {
        shareParamsMap.put(KEY_TITLE_URL, titleUrl);
    }

    public void setText(String text) {
        shareParamsMap.put(KEY_TEXT, text);
    }

    public String getText() {
        return shareParamsMap.containsKey(KEY_TEXT) ? String.valueOf(shareParamsMap.get(KEY_TEXT)) : null;
    }

    public void setImageUrl(String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl))
            shareParamsMap.put(KEY_IMG_URL, imageUrl);
    }

    public void setUrl(String url) {
        if (!TextUtils.isEmpty(url))
            shareParamsMap.put(KEY_URL, url);
    }

    public void setComment(String comment) {
        if (!TextUtils.isEmpty(comment))
            shareParamsMap.put(KEY_COMMENT, comment);
    }

    @SuppressWarnings("unused")
    public void setSite(String site) {
        if (!TextUtils.isEmpty(site))
            shareParamsMap.put(KEY_SITE, site);
    }

    @SuppressWarnings("unused")
    public void setSiteUrl(String siteUrl) {
        if (!TextUtils.isEmpty(siteUrl))
            shareParamsMap.put(KEY_SITE_URL, siteUrl);
    }

    /**
     * 设置是否直接分享
     * @param isSlite true 直接分享，不弹出编辑页 false 弹出编辑页进行分享
     */
    public void setIsSlite(boolean isSlite){
        shareParamsMap.put(KEY_IS_SLIENT, isSlite);
    }

    /**
     * 根据平台的分享名称设置分享平台
     * @param platform 平台名称
     */
    public void setPlatform(String platform){
        shareParamsMap.put(KEY_PLATFORM, platform);
    }

    /**
     * 在获取参数时候返回对应平台所拥有的参数
     * @return 分享参数
     */
    public HashMap<String, Object> getShareParamsMap(){
        //QQ空间没有title和titleUrl无法分享
        if(shareParamsMap.get(KEY_PLATFORM).equals(QZone.NAME)){
            if(!shareParamsMap.containsKey(KEY_TITLE)
                    ||!shareParamsMap.containsKey(KEY_TITLE_URL)){
                return null;
            }
        }
        return shareParamsMap;
    }

}
