-- 删除Objective表（如果存在）
DROP TABLE IF EXISTS objective;

-- 创建Objective表（目标 O / OKR）
CREATE TABLE objective (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'Objective / OKR ID',
    team_id BIGINT NOT NULL COMMENT '所属团队ID',
    sprint_id BIGINT COMMENT '所属周期（OKR Period）ID',
    title VARCHAR(200) NOT NULL COMMENT 'OKR 标题',
    description TEXT COMMENT 'OKR 描述',
    progress DECIMAL(5,2) DEFAULT 0.00 COMMENT 'OKR 当前进度（系统聚合计算）',
    cycle_start DATETIME COMMENT '周期开始时间',
    cycle_end DATETIME COMMENT '周期结束时间',
    status INT DEFAULT 1 COMMENT '状态（1-激活，0-关闭）',
    create_user_id BIGINT COMMENT '创建者ID',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_team_id (team_id),
    INDEX idx_sprint_id (sprint_id),
    FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE,
    FOREIGN KEY (sprint_id) REFERENCES sprint(id) ON DELETE SET NULL
) COMMENT = '目标表（OKR中的Objective）' AUTO_INCREMENT = 1;