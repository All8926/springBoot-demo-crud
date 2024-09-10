package com.example.pojo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Emp {
    private Integer id;
    private String username;
    private String name;
    private String password;
    private Short gender;
    private String image;
    private Short job;
    private LocalDate entrydate;
    private  Integer deptId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
