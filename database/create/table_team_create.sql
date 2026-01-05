-- 删除Team表（如果存在）
DROP TABLE IF EXISTS team;

-- 创建Team表（团队）
CREATE TABLE team (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '团队ID',
    name VARCHAR(100) NOT NULL COMMENT '团队名称',
    description TEXT COMMENT '团队描述',
    cycle_days INT DEFAULT 14 COMMENT '迭代周期（天）',
    status INT DEFAULT 1 COMMENT '团队状态（1-活跃，0-暂停）',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    member_count INT DEFAULT 0 COMMENT '成员数量',
    okr_count INT DEFAULT 0 COMMENT 'OKR数量',
    task_count INT DEFAULT 0 COMMENT '任务数量',
    completed_task_count INT DEFAULT 0 COMMENT '已完成任务数量',
    INDEX idx_name (name),
    INDEX idx_status (status)
) COMMENT = '团队表';