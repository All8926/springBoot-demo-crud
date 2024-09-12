package com.example;

import com.example.mapper.EmpMapper;
import com.example.pojo.Emp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private EmpMapper empMapper;

    @Test
    void testDelete() {
      int num =  empMapper.delete(22);
        System.out.println(num);
    }

    @Test
    void testInsert(){
        Emp emp = new Emp();
        emp.setUsername("zhuxiaoming3");
        emp.setName("祝小明");
        emp.setGender((short)1);
        emp.setImage("22.png");
        emp.setJob((short)3);
        emp.setDeptId(3);
        emp.setEntrydate(LocalDate.of(2024  ,9,12));
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);

        System.out.println(emp.getId().toString());
    }

    @Test
    void testUpdate(){
        Emp emp = new Emp();
        emp.setId(23);
        emp.setUsername("zhuxiaoming4");
        emp.setName("祝小明");
        emp.setGender((short)0);
        emp.setImage("44.png");
        emp.setJob((short)3);
        emp.setDeptId(3);
        emp.setEntrydate(LocalDate.of(2024  ,9,12));
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.update(emp);
    }

    @Test
    void testGetById(){
        Emp emp = empMapper.getById(5);
        System.out.println(emp);
    }

    @Test
   void testGetList(){
        List<Emp> empList = empMapper.getList("小", (short) 1, LocalDate.of(2000, 1, 12), LocalDate.of(2025, 1, 1));
        System.out.println(empList);
    }

}
