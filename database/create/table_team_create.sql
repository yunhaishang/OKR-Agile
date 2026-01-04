-- 删除Team表（如果存在）
DROP TABLE IF EXISTS team;

-- 创建Team表（团队）
CREATE TABLE team (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '团队ID',
    name VARCHAR(100) NOT NULL COMMENT '团队名称',
    description TEXT COMMENT '团队描述',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_name (name)
) COMMENT = '团队表';