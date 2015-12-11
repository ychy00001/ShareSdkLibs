package cn.sharesdk.onekeyshare.utils;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.onekeyshare.model.ShareParameter;

/**
 * 自定义各个平台分享内容工具类
 * 以后使用
 * 会在{@link ShareUtil#showShare(ShareParameter)}中调用
 * Created by yangchunyu
 * on 2015/12/4 15:34
 */
public class ShareContentCustomizeUtil  implements ShareContentCustomizeCallback {
    public void onShare(Platform platform, ShareParams paramsToShare) {
//        String text = platform.getContext().getString(com.xcar.library_sharesdk.R.string.share_title);
        if ("WechatMoments".equals(platform.getName())) {
            // 改写twitter分享内容中的text字段，否则会超长，
            // 因为twitter会将图片地址当作文本的一部分去计算长度
//            text += platform.getContext().getString(cn.sharesdk.onekeyshare.R.string.share_to_wechatmoment);
//            paramsToShare.setText(text);
        }else if("SinaWeibo".equals(platform.getName())){
//            text += platform.getContext().getString(cn.sharesdk.onekeyshare.R.string.share_to_sina);
//            text += "by Rain";
//            paramsToShare.setText(text);
        }else if("TencentWeibo".equals(platform.getName())){
//            text += platform.getContext().getString(cn.sharesdk.onekeyshare.R.string.share_to_tencent);
//            paramsToShare.setText(text);
        }
    }
}
