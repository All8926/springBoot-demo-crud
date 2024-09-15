package com.example.controller;

import com.example.pojo.Dept;
import com.example.pojo.Emp;
import com.example.pojo.Result;
import com.example.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/depts")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 查询全部部门
     * @return
     */
    @GetMapping
    public Result list(){
        List<Dept> list = deptService.list();
        return Result.success(list);
    }

    /**
     * 根据id查询部门
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("删除的部门id: {}", id);
       Dept dept =  deptService.getById(id);
        return Result.success(dept);
    }

    /**
     * 根据id删除部门
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("删除的部门id: {}", id);
        deptService.delete(id);
        return  Result.success();
    }

    /**
     * 新增部门
     * @param dept
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Dept dept){
        log.info("新增的部门：{}", dept);
        deptService.add(dept);
        return Result.success();
    }

    /**
     * 修改部门
     * @param dept
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Dept dept){
        log.info("修改的信息：{}", dept);
        deptService.update(dept);
        return Result.success();
    }
}
