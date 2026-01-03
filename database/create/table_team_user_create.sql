-- 删除Team User关联表（如果存在）
DROP TABLE IF EXISTS team_user;

-- 创建Team User关联表（团队成员关系）
CREATE TABLE team_user (
    team_id BIGINT NOT NULL COMMENT '团队ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role VARCHAR(50) DEFAULT 'member' COMMENT '角色（owner / admin / member）',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (team_id, user_id),
    INDEX idx_user_id (user_id),
    INDEX idx_role (role),
    FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) COMMENT = '团队用户关联表（团队成员关系）';