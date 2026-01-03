-- 删除Sprint表（如果存在）
DROP TABLE IF EXISTS sprint;

-- 创建Sprint表（迭代周期）
CREATE TABLE sprint (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'Sprint ID',
    team_id BIGINT NOT NULL COMMENT '所属团队ID',
    name VARCHAR(100) NOT NULL COMMENT 'Sprint名称',
    start_date DATETIME COMMENT '开始时间',
    end_date DATETIME COMMENT '结束时间',
    status VARCHAR(20) DEFAULT 'active' COMMENT '状态（active / closed）',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_team_id (team_id),
    INDEX idx_name (name),
    INDEX idx_status (status),
    FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE
) COMMENT = 'Sprint表（迭代周期）';