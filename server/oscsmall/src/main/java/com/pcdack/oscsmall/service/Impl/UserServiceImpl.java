package com.pcdack.oscsmall.service.Impl;
import com.pcdack.oscsmall.common.Const;
import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.common.TokenCache;
import com.pcdack.oscsmall.dao.UserMapper;
import com.pcdack.oscsmall.pojo.User;
import com.pcdack.oscsmall.service.IUserService;
import com.pcdack.oscsmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
@Service("iUserService")
public class UserServiceImpl implements IUserService{
    @Autowired
    private UserMapper userMapper;
    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCode=userMapper.checkUsername(username);
//        这里确保用户存在
        if (resultCode == 0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String md5Password= MD5Util.MD5EncodeUtf8(password);
        User user=userMapper.selectLogin(username, md5Password);
        if (user==null){
            return  ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);
    }
    @Override
    public ServerResponse<String> register(User user) {
        ServerResponse validResponse=this.checkValid(user.getUsername(),Const.USERNAME);
        if (!validResponse.isSuccess()){
            return validResponse;
        }
        validResponse=this.checkValid(user.getEmail(),Const.EMAIL);
        if (!validResponse.isSuccess()){
            return validResponse;
        }
        user.setRole(Const.ROLE.ROLE_CUSTOM);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCode=userMapper.insert(user);
        if (resultCode == 0){
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccess("注册成功");
    }
    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(type)) {
            if (Const.USERNAME.equals(type)) {
                int resultCode = userMapper.checkUsername(str);
                if (resultCode > 0) {
                    return ServerResponse.createByErrorMessage("用户已存在");
                }
            }
            if (Const.EMAIL.equals(type)) {
                int resultCode = userMapper.checkEmail(str);
                if (resultCode > 0) {
                    return ServerResponse.createByErrorMessage("Email已存在");
                }
            }
        }else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccess("校验成功");
    }
    @Override
    public ServerResponse<String> selectQuestion(String username) {
        ServerResponse validResponse=this.checkValid(username,Const.USERNAME);
        if (validResponse.isSuccess()){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String question=userMapper.selectQuestionByUserName(username);
        if (StringUtils.isNotBlank(question)){
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("用户问题为空！");
    }

    @Override
    public ServerResponse<String> checkAnswer(String username,
                                              String question,
                                              String answer) {
        int resultCode=userMapper.checkAnswer(username, question, answer);
        if (resultCode>0){
            String forgetToken= UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("答案错误！");
    }
    @Override
    public ServerResponse<String> forgetResetPassword(String username,
                                                      String newPassword,
                                                      String forgetToken) {
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createByErrorMessage("参数错误，token为空!");
        }
        ServerResponse validResponse=this.checkValid(username,Const.USERNAME);
        if (validResponse.isSuccess()){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String token=TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);
        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMessage("token无效或者过期");
        }
        if (StringUtils.equals(forgetToken,token)) {
            String md5Password=MD5Util.MD5EncodeUtf8(newPassword);
            int resultCode=userMapper.updatePasswordByUsername(username, md5Password);
            if (resultCode>0){
                return ServerResponse.createBySuccess("修改密码成功");
            }
        }else {
            return ServerResponse.createByErrorMessage("Token错误或者过期");
        }
        return ServerResponse.createByErrorMessage("修改密码失败");
    }
    @Override
    public ServerResponse<String> resetPassword(String passwordOld,
                                                String passwordNew,
                                                User user) {
        //防止横向越权,要校验一下这个用户的旧密码,一定要指定是这个用户.因为我们会查询一个count(1),如果不指定id,那么结果就是true啦count>0;
        int resultCode=userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if (resultCode==0){
            return ServerResponse.createByErrorMessage("旧密码错误！");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCode=userMapper.updateByPrimaryKey(user);
        if (updateCode>0) {
            return ServerResponse.createBySuccess("密码更新成功");
        }
        return ServerResponse.createByErrorMessage("密码更新失败");
    }
    @Override
    public ServerResponse<User> updateInformation(User user) {
        int resultCode=userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if (resultCode > 0){
            return ServerResponse.createByErrorMessage("该email已经注册，请使用新的email");
        }
        User updateUser=new User();
        updateUser.setId(user.getId());
        updateUser.setPhone(user.getPhone());
        updateUser.setAvatar(user.getAvatar());
        updateUser.setEmail(user.getEmail());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        int updateCode=userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCode>0){
            return ServerResponse.createBySuccess("个人信息更新成功",updateUser);
        }
        return ServerResponse.createByErrorMessage("个人信息更新失败");
    }
    @Override
    public ServerResponse<User> getInformation(int userId) {
        User user=userMapper.selectByPrimaryKey(userId);
        if (user==null){
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
//        这里我们不希望返回用户的密码，所以要至空
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }
    /**
     * 验证身份
     */
    @Override
    public ServerResponse checkAdmin(User user) {
        if (user==null){
            return ServerResponse.createByErrorMessage("请登录");
        }
        if (user.getRole().intValue()== Const.ROLE.ROLE_ADMIN) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }
}