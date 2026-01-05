-- 删除Task表（如果存在）
DROP TABLE IF EXISTS task;

-- 创建Task表（任务）
CREATE TABLE task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务 ID',
    team_id BIGINT NOT NULL COMMENT '所属团队ID',
    sprint_id BIGINT COMMENT '所属周期ID',
    kr_id BIGINT NOT NULL COMMENT '关联的KR（强制）ID',
    title VARCHAR(200) NOT NULL COMMENT '任务标题',
    description TEXT COMMENT '任务描述',
    status VARCHAR(20) COMMENT '任务状态（todo / doing / done / blocked）',
    priority VARCHAR(20) COMMENT '优先级（low / medium / high）',
    type VARCHAR(20) COMMENT '类型（feature / bug / chore 等）',
    story_points INT COMMENT '故事点',
    assignee_id BIGINT COMMENT '负责人ID',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_team_id (team_id),
    INDEX idx_sprint_id (sprint_id),
    INDEX idx_kr_id (kr_id),
    INDEX idx_assignee_id (assignee_id),
    FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE,
    FOREIGN KEY (sprint_id) REFERENCES sprint(id) ON DELETE SET NULL,
    FOREIGN KEY (kr_id) REFERENCES key_result(id) ON DELETE CASCADE,
    FOREIGN KEY (assignee_id) REFERENCES user(id) ON DELETE SET NULL
) COMMENT = '任务表' AUTO_INCREMENT = 1;