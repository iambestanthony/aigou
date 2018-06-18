package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by JayJ on 2018/4/21.
 **/
@Controller
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ICategoryService categoryService;

    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session,String categoryName,@RequestParam(value = "parentId",defaultValue = "0") Integer parentId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录后再试");
        }
        //校验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            //是管理员，处理逻辑
            return categoryService.addCategory(categoryName,parentId);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }
    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session,Integer categoryId,String categoryName){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录后再试");
        }
        if (userService.checkAdminRole(user).isSuccess()){
            //更新categoryName
            return categoryService.updateCategoryName(categoryId,categoryName);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录后再试");
        }
        if (userService.checkAdminRole(user).isSuccess()){
            //查询子节点的category信息，并且不递归，保持平级
            return categoryService.getChildrenParallelCategory(categoryId);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping(value = "get_deep_category.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录后再试");
        }
        if (userService.checkAdminRole(user).isSuccess()){
            //查询当前节点的id和递归子节点的id
            return categoryService.selectCategoryAndChildrenById(categoryId);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }
}
