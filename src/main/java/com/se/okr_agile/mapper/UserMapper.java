package com.se.okr_agile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.okr_agile.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
