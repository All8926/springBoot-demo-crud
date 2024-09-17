package com.example.service;

import com.example.pojo.Emp;
import com.example.pojo.PageBean;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {
    PageBean page(Integer page, Integer pageSize, String name, Short gender,
                  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end);

    void delete(List<Integer> ids);

    void save(Emp emp);
}
