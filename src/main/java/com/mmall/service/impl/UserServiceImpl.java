package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by JayJ on 2018/4/15.
 **/
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User>  login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        //todo 密码登录md5
        String md5password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username, md5password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);
    }
    @Override
    public ServerResponse<String> register(User user){
        //重用检查是否有效的方法
        ServerResponse<String> valiedResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!valiedResponse.isSuccess()){
            return valiedResponse;
        }
        valiedResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!valiedResponse.isSuccess()){
            return valiedResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        //md5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        int resultCount = userMapper.insert(user);
        if (resultCount == 0){
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }
    @Override
    public ServerResponse<String> checkValid(String str ,String type){
        if (org.apache.commons.lang3.StringUtils.isNotBlank(str)){
            //开始校验
            if (Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0){
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if (Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0){
                    return ServerResponse.createByErrorMessage("邮箱已存在");
                }
            }

        }else{
            return  ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }
    @Override
    public ServerResponse selectQuestion(String username){
        ServerResponse<String> valied = this.checkValid(username, Const.USERNAME);
        if (valied.isSuccess()){
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(question)){
            return  ServerResponse.createBySuccess(question);
        }

        return ServerResponse.createByErrorMessage("找回密码的问题是空的");
    }
    @Override
    public ServerResponse<String> checkAnswer(String username,String question,String answer){
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if (resultCount>0){
            //说明问题验证正确
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题的答案错误");
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        if (org.apache.commons.lang3.StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("需要参数forgetToken");
        }
        ServerResponse<String> valiedResponse = this.checkValid(username, Const.USERNAME);
        if (valiedResponse.isSuccess()){
            //没有用户名
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (org.apache.commons.lang3.StringUtils.isBlank(token)){
            return  ServerResponse.createByErrorMessage("Token无效或者已过期");
        }
        if (org.apache.commons.lang3.StringUtils.equals(forgetToken,token)){
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int resultCount = userMapper.updatePasswordByUsername(username, md5Password);
            if (resultCount>0){
                return ServerResponse.createBySuccess("密码重置成功");
            }

        }else {
            return  ServerResponse.createByErrorMessage("Token错误，请重新获取Token");
        }

        return ServerResponse.createByErrorMessage("密码重置失败");
    }
    @Override
    public ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user){
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("旧密码错误");
        }

        //设置密码
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        resultCount = userMapper.updateByPrimaryKeySelective(user);
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("密码更新成功");
        }
        return ServerResponse.createByErrorMessage("密码更新失败");
    }

    @Override
    public ServerResponse<User> update_information(User user) {
        //用户名不能被修改
        //校验email
        ServerResponse<String> emailValied = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!emailValied.isSuccess()){
            return ServerResponse.createByErrorMessage("email已存在，请更换email进行重新输入");
        }
        User upUser = new User();
        upUser.setId(user.getId());
        upUser.setEmail(user.getEmail());
        upUser.setPhone(user.getPhone());
        upUser.setQuestion(user.getQuestion());
        upUser.setAnswer(user.getAnswer());


        int resultCount = userMapper.updateByPrimaryKeySelective(upUser);

        if (resultCount > 0) {
            return ServerResponse.createBySuccess("更新个人信息成功",upUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }

    public ServerResponse<User> getInformation(Integer userId){
        User user= userMapper.selectByPrimaryKey(userId);
        if (user==null){
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);


        return  ServerResponse.createBySuccess(user);
    }

    //校验是否是管理员
    public ServerResponse checkAdminRole(User user){
        if (user!=null&&Const.Role.ROLE_ADMIN==user.getRole().intValue()){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }


}
