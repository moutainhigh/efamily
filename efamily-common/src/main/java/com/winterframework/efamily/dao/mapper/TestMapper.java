package com.winterframework.efamily.dao.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.winterframework.efamily.entity.Test;

public interface TestMapper {  
   
    @Insert("insert into t_user(name, age) values(#{name}, #{age})")  
    public void insertUser(Test user);  
     
    @Update("update t_user set name=#{name}, age=#{age} where id=#{id}")  
    public void updateUser(Test user);  
     
    @Select("select * from t_user where id=#{id}")  
    public Test findById(int id);  
     
    @Delete("delete from t_user where id=#{id}")  
    public void deleteUser(int id);
}
   