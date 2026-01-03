-- 删除指标快照表（如果存在）
DROP TABLE IF EXISTS metric_snapshot;

-- 创建指标快照表
CREATE TABLE metric_snapshot (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '快照 ID',
    team_id BIGINT NOT NULL COMMENT '团队ID',
    sprint_id BIGINT COMMENT '周期ID',
    snapshot_date DATE NOT NULL COMMENT '快照日期',
    okr_progress DECIMAL(5,2) COMMENT 'OKR 总体进度',
    total_tasks INT DEFAULT 0 COMMENT '任务总数',
    completed_tasks INT DEFAULT 0 COMMENT '已完成任务数',
    blocked_tasks INT DEFAULT 0 COMMENT '阻塞任务数',
    velocity DECIMAL(10,2) COMMENT '团队速度指标',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_team_id (team_id),
    INDEX idx_sprint_id (sprint_id),
    INDEX idx_snapshot_date (snapshot_date),
    FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE,
    FOREIGN KEY (sprint_id) REFERENCES sprint(id) ON DELETE SET NULL
) COMMENT = '指标快照表';