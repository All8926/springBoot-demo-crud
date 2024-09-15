package com.example.service.impl;

import com.example.mapper.EmpMapper;
import com.example.pojo.Emp;
import com.example.pojo.PageBean;
import com.example.service.EmpService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize) {
        // 1.设置分页参数
        PageHelper.startPage(page,pageSize);

        // 2.执行查询
        List<Emp> list = empMapper.list();
        Page<Emp> p = (Page<Emp>) list;

        // 3.封装PageBaen对象
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());

        return pageBean;
    }
}
