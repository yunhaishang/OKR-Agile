-- 删除复盘报告表（如果存在）
DROP TABLE IF EXISTS retrospective_report;

-- 创建复盘报告表
CREATE TABLE retrospective_report (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '报告 ID',
    team_id BIGINT NOT NULL COMMENT '所属团队ID',
    sprint_id BIGINT COMMENT '所属周期ID',
    title VARCHAR(200) COMMENT '报告标题',
    highlights TEXT COMMENT '亮点总结',
    bottlenecks TEXT COMMENT '瓶颈分析',
    suggestions TEXT COMMENT '改进建议',
    metrics_json JSON COMMENT '进度趋势 / KR 完成度等结构化数据',
    status INT DEFAULT 1 COMMENT '报告状态（1-已完成，0-草稿）',
    generated_by_user_id BIGINT COMMENT '生成报告的用户ID',
    generated_at DATETIME COMMENT '生成时间',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_team_id (team_id),
    INDEX idx_sprint_id (sprint_id),
    FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE,
    FOREIGN KEY (sprint_id) REFERENCES sprint(id) ON DELETE SET NULL
) COMMENT = '复盘报告表' AUTO_INCREMENT = 1;