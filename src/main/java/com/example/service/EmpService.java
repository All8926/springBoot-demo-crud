package com.example.service;

import com.example.pojo.PageBean;

public interface EmpService {
    PageBean page(Integer page, Integer pageSize);
}
