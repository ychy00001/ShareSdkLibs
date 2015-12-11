package cn.sharesdk.onekeyshare.model;

/**
 * 第三方登录信息类
 * Created by yangchunyu
 * on 2015/12/7 17:50
 */
public class LoginInfo {
    private String token;//令牌
    private String uid;//用户id
    private long exprestime;//授权时间
    private String name;//用户名
    private String profileImg;//用户头像

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getExprestime() {
        return exprestime;
    }

    public void setExprestime(long exprestime) {
        this.exprestime = exprestime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "token='" + token + '\'' +
                ", uid='" + uid + '\'' +
                ", exprestime=" + exprestime +
                ", name='" + name + '\'' +
                ", profileImg='" + profileImg + '\'' +
                '}';
    }
}
