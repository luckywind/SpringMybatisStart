package com.mybatis3.mappers;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface AggMapper {

    @Select("select name ,count(1) from students group by name")
    List<Map<String, Integer>> countByName();

}
