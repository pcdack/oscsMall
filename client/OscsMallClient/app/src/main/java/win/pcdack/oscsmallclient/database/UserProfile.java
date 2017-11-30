package win.pcdack.oscsmallclient.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by pcdack on 17-11-19.
 *
 */

@Entity(nameInDb = "user_profile")
public class UserProfile {
    @Id
    private long userId=0;
    private String userName=null;
    private String email=null;
    private String phone=null;
    private String avatar=null;
    @Generated(hash = 2136450388)
    public UserProfile(long userId, String userName, String email, String phone,
            String avatar) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
    }
    @Generated(hash = 968487393)
    public UserProfile() {
    }
    public long getUserId() {
        return this.userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
  

}
