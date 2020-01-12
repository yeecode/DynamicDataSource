package com.github.yeecode.dynamicdatasourcedemo.business;

import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from sampletable")
    String select();
}
