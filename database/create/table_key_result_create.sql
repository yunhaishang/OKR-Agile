-- 删除Key Result表（如果存在）
DROP TABLE IF EXISTS key_result;

-- 创建Key Result表（关键结果 KR）
CREATE TABLE key_result (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'KR ID',
    objective_id BIGINT NOT NULL COMMENT '所属Objective ID',
    description VARCHAR(255) NOT NULL COMMENT 'KR 描述',
    metric_type VARCHAR(20) NOT NULL COMMENT '度量模型（TASK_DRIVEN / RATIO / MANUAL）',
    target_value DECIMAL(10,2) COMMENT '目标值',
    current_value DECIMAL(10,2) DEFAULT 0.00 COMMENT '当前值',
    display_unit VARCHAR(20) COMMENT '展示单位（%、次、个等）',
    weight DECIMAL(5,2) DEFAULT 1.00 COMMENT 'KR 权重',
    progress DECIMAL(5,2) DEFAULT 0.00 COMMENT 'KR 完成度（系统计算 / 评估结果）',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_objective_id (objective_id),
    FOREIGN KEY (objective_id) REFERENCES objective(id) ON DELETE CASCADE
) COMMENT = '关键结果表（OKR中的Key Result）' AUTO_INCREMENT = 1;