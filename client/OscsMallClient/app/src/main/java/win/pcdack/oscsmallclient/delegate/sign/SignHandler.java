package win.pcdack.oscsmallclient.delegate.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import win.pcdack.oscsmallclient.database.DatabaseManager;
import win.pcdack.oscsmallclient.database.UserProfile;

/**
 * Created by pcdack on 17-11-19.
 *
 */

public class SignHandler {
    public static void signIn(String response){
        final JSONObject profileJson= JSON.parseObject(response).getJSONObject("data");
        final long userId=(long) profileJson.getInteger("id");
        final String userName=profileJson.getString("username");
        final String email=profileJson.getString("email");
        final String phone=profileJson.getString("phone");
        final String avatar=profileJson.getString("avatar");
        final UserProfile userProfile=new UserProfile(userId,userName,phone,email,avatar);
        DatabaseManager.getInstance().getDao().insert(userProfile);
    }
}
