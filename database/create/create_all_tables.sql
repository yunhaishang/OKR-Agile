
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
                                 create_user_id BIGINT COMMENT '创建者ID',
                                 created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 INDEX idx_team_id (team_id),
                                 INDEX idx_sprint_id (sprint_id),
                                 INDEX idx_snapshot_date (snapshot_date),
                                 FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE,
                                 FOREIGN KEY (sprint_id) REFERENCES sprint(id) ON DELETE SET NULL
) COMMENT = '指标快照表';

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
) COMMENT = '复盘报告表';

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
) COMMENT = '目标表（OKR中的Objective）';

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
                            create_user_id BIGINT COMMENT '创建者ID',
                            created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            INDEX idx_objective_id (objective_id),
                            FOREIGN KEY (objective_id) REFERENCES objective(id) ON DELETE CASCADE
) COMMENT = '关键结果表（OKR中的Key Result）';

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
                      due_date DATETIME COMMENT '截止时间',
                      estimated_hours INT COMMENT '预估工时',
                      actual_hours INT COMMENT '实际工时',
                      code_contribution_score DECIMAL(5,2) COMMENT '代码贡献度',
                      create_user_id BIGINT COMMENT '创建者ID',
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
) COMMENT = '任务表';

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