package com.njit.zang.controller;

import com.njit.zang.dto.Result;
import com.njit.zang.model.SendContent;
import com.njit.zang.model.User;
import com.njit.zang.service.AdminService;
import com.njit.zang.utils.MD5Utils;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Administrator on 2019/5/27.
 */
@RequestMapping("/admin")
@RestController()
@Slf4j
public class AdminController {
    final static String ADMIN_VERITY = "2bb792e34f69bddeb8878641bacd1cf2";
    @Autowired
    private AdminService adminService;

    @GetMapping("selectAllUser")
    public Result selectAllUser(){
        return Result.builder().code(Result.SUCCESS_CODE).res(adminService.selectUserManagerList()).build();
    }

    @GetMapping("selectAllFeed")
    public Result selectAllFeed(){
        return Result.builder().code(Result.SUCCESS_CODE).res(adminService.selectFeedManagerList()).build();
    }

    @DeleteMapping("deleteUser")
    public Result deleteUser(@RequestBody User user){
        adminService.deleteUser(user);
        return Result.builder().code(Result.SUCCESS_CODE).res(user.getUid()).build();
    }

    @DeleteMapping("deleteFeed")
    public Result deleteFeed(@RequestBody SendContent sendContent){
        adminService.deleteFeed(sendContent);
        return Result.builder().code(Result.SUCCESS_CODE).res(sendContent.getFeedId()).build();
    }

    @PostMapping("verity")
    public Result verity(@RequestBody Map m){
        String password = (String) m.get("password");
        password = MD5Utils.Encode(password);
        if(password.equals(ADMIN_VERITY)){
            return Result.builder().code(Result.SUCCESS_CODE).res(true).build();
        }
        return Result.builder().code(Result.SUCCESS_CODE).res(false).build();
    }
}
