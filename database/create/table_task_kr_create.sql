-- 删除Task KR关联表（如果存在）
DROP TABLE IF EXISTS task_kr;

-- 创建Task KR关联表（任务 - KR 支撑关系）
CREATE TABLE task_kr (
    task_id BIGINT NOT NULL COMMENT '任务 ID',
    kr_id BIGINT NOT NULL COMMENT 'KR ID',
    weight DECIMAL(5,2) DEFAULT 1.00 COMMENT '任务对KR的支撑权重',
    PRIMARY KEY (task_id, kr_id),
    INDEX idx_task_id (task_id),
    INDEX idx_kr_id (kr_id),
    FOREIGN KEY (task_id) REFERENCES task(id) ON DELETE CASCADE,
    FOREIGN KEY (kr_id) REFERENCES key_result(id) ON DELETE CASCADE
) COMMENT = '任务与关键结果关联表';