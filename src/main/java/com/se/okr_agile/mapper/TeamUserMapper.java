package com.se.okr_agile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.okr_agile.entity.Team;
import com.se.okr_agile.entity.TeamUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeamUserMapper extends BaseMapper<TeamUser> {
    @Select("select t.id, t.name " +
            "from team_user as tu inner join team as t " +
            "on tu.team_id = t.id " +
            "where tu.user_id = #{userId}")
    List<Team> getTeamsByUserId(Long userId);
}
