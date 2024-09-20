package com.example.controller;

import com.example.pojo.Emp;
import com.example.pojo.Result;
import com.example.service.EmpService;
import com.example.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private EmpService empService;

    @PostMapping("/login")
    public Result login(@RequestBody Emp emp){
       Emp user = empService.login(emp);

       if(user != null){
           Map claims = new HashMap<>();
           claims.put("username",user.getUsername());
           claims.put("id",user.getId());
           claims.put("name",user.getName());
           String token = JwtUtils.GenerateJwt(claims);
           return Result.success(token);
       }else {
           return Result.error("用户名或密码错误");
       }

    }
}
