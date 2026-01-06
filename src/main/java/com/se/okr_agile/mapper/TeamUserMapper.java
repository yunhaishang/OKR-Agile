package com.se.okr_agile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.okr_agile.entity.Team;
import com.se.okr_agile.entity.TeamUser;
import com.se.okr_agile.vo.UserTeamInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeamUserMapper extends BaseMapper<TeamUser> {
    @Select("select t.id, t.name, t.description, t.cycle_days as cycleDays, t.status, t.created_at as createdAt, t.updated_at as updatedAt " +
            "from team_user as tu inner join team as t " +
            "on tu.team_id = t.id " +
            "where tu.user_id = #{userId}")
    List<Team> getTeamsByUserId(Long userId);
    
    @Select("select count(*) from team_user where team_id = #{teamId}")
    Integer getMemberCountByTeamId(Long teamId);
    
    @Select("delete from team_user where team_id = #{teamId}")
    void deleteByTeamId(Long teamId);
    
    @Select("delete from team_user where team_id = #{teamId} and user_id = #{userId}")
    void deleteByTeamIdAndUserId(Long teamId, Long userId);
    
    @Select("select u.id, u.username, u.email, u.nickname, u.avatar " +
            "from team_user tu " +
            "inner join user u on tu.user_id = u.id " +
            "where tu.team_id = #{teamId}")
    List<UserTeamInfo> getTeamMembersByTeamId(Long teamId);
}
