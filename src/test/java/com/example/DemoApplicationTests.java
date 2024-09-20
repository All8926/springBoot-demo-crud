package com.example;

import com.example.mapper.EmpMapper;
import com.example.pojo.Emp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

//@SpringBootTest
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
//        List<Emp> empList = empMapper.getList("张", (short) 1, LocalDate.of(2000, 1, 12), LocalDate.of(2025, 1, 1));

        // 动态sql语句
        List<Emp> empList = empMapper.getList(null, (short) 1, null, null);
        System.out.println(empList);
    }

    @Test
    void testUpdate2(){
        Emp emp = new Emp();
        emp.setId(23);
        emp.setUsername("zhuxiaoming444");
        emp.setName("祝小明22");
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.update2(emp);
    }

    @Test
    void TestDeleteByIds(){
        List<Integer> ids = Arrays.asList(16, 18);

        empMapper.deleteByIds(ids);

    }

    /**
     * 生成jwt
     */
    @Test
    void TestGenJwt(){
        HashMap<String,Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name","abc");
        objectObjectHashMap.put("pwd",123456);
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, "test")    // 签名算法
                .setClaims(objectObjectHashMap)     // 自定义内容（在载荷）
                .setExpiration(new Date(System.currentTimeMillis() + 600 * 1000))    // 过期时间
                .compact();
        System.out.println(token);
    }

    /**
     * 解析jwt
     */
    @Test
    void TestParseJwt(){
        Claims test = Jwts.parser()
                .setSigningKey("test")    // 指定签名密钥
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi5p2O5ZubIiwiaWQiOjI2LCJleHAiOjE3MjY3OTUxNjEsInVzZXJuYW1lIjoibGlzaSJ9.b5e8iLKX3jT2OXDq78AQEBW8fKMhXrbIIg6gmezvOx0")
                .getBody();     // 解析令牌
        System.out.println(test);
    }

}
