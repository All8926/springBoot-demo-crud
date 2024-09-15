package com.example.mapper;

import com.example.pojo.Emp;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpMapper {

    // 删除
    @Delete("delete from emp where id = #{id}")
    public int delete(Integer id);

    // 新增
    @Options(keyProperty = "id", useGeneratedKeys = true)   // 获取返回的主键
    @Insert("insert into emp(username, name, gender, image, job, entrydate, dept_id, create_time, update_time)" +
            "values (#{username},#{name},#{gender},#{image},#{job},#{entrydate},#{deptId},#{createTime},#{updateTime})")
    public void insert(Emp emp);

    //更新
    @Update("update emp set username=#{username},name=#{name},gender=#{gender},image=#{image},job=#{job},entrydate=#{entrydate}," +
            "dept_id=#{deptId},update_time=#{updateTime} where id = #{id}")
    public void update(Emp emp);

    // 查询全部
    @Select("select * from emp")
    public List<Emp> list();
    
    // 根据id查询
    @Select("select id, username, password, name, gender, image, job, entrydate, dept_id, create_time, update_time from emp where id = #{id}")
    public Emp getById(Integer id);

    //条件查询  (concat: 字符串拼接函数)
//    @Select("select * from emp where name like concat('%',#{name},'%') and gender = #{gender} and " +
//            "entrydate between #{begin} and #{end} order by update_time desc")
    public List<Emp> getList(@Param("name") String name, @Param("gender") Short gender, @Param("begin") LocalDate begin, @Param("end") LocalDate end);

    //动态sql 更新
    public void update2(Emp emp);

    // 批量删除
    public void deleteByIds(@Param("ids") List<Integer> ids);
}
