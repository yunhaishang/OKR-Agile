package com.se.okr_agile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.okr_agile.entity.Token;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenMapper extends BaseMapper<Token> {
    @Delete("delete from token where token=#{token}")
    int deleteByToken(String token);
}
