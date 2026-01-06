-- 插入测试数据

-- 插入测试用户
INSERT INTO user (username, email, password, nickname) VALUES
('admin1', 'admin@example.com', '$2a$10$NPEt3MvHd7y6y3z8Q5h8hOZ7Q5h8hOZ7Q5h8hOZ7Q5h8hOZ7Q5h8h', 'Admin User'),
('user3', 'user1@example.com', '$2a$10$NPEt3MvHd7y6y3z8Q5h8hOZ7Q5h8hOZ7Q5h8hOZ7Q5h8hOZ7Q5h8h', 'User One'),
('user4', 'user2@example.com', '$2a$10$NPEt3MvHd7y6y3z8Q5h8hOZ7Q5h8hOZ7Q5h8hOZ7Q5h8hOZ7Q5h8h', 'User Two');

-- 插入测试团队
INSERT INTO team (name, description, created_at) VALUES
('开发团队', '负责产品开发的团队', NOW()),
('测试团队', '负责产品质量保证的团队', NOW()),
('产品团队', '负责产品规划和设计的团队', NOW());

-- 插入测试Sprint
INSERT INTO sprint (team_id, name, start_date, end_date, status) VALUES
(1, 'Sprint 1', '2024-01-01 00:00:00', '2024-01-14 23:59:59', 'active'),
(1, 'Sprint 2', '2024-01-15 00:00:00', '2024-01-28 23:59:59', 'active'),
(2, 'Test Sprint 1', '2024-01-01 00:00:00', '2024-01-14 23:59:59', 'closed');

-- 插入测试Objective
INSERT INTO objective (team_id, sprint_id, title, description, progress) VALUES
(1, 1, '提升系统性能', '优化系统性能，提高响应速度', 75.00),
(1, 1, '完善用户功能', '开发和完善用户相关功能', 50.00),
(2, 3, '提高测试覆盖率', '提升代码测试覆盖率至80%以上', 90.00);

-- 插入测试Key Result
INSERT INTO key_result (objective_id, description, metric_type, target_value, display_unit, weight) VALUES
(1, '将API响应时间降低到200ms以下', 'response_time', 200, 'ms', 0.5),
(1, '将数据库查询时间降低到50ms以下', 'query_time', 50, 'ms', 0.3),
(1, '将系统并发处理能力提升至1000TPS', 'throughput', 1000, 'TPS', 0.2),
(2, '实现用户注册功能', 'feature_completion', 1, 'count', 0.4),
(2, '实现用户登录功能', 'feature_completion', 1, 'count', 0.3),
(2, '实现用户资料管理功能', 'feature_completion', 1, 'count', 0.3);

-- 插入测试任务
INSERT INTO task (team_id, sprint_id, kr_id, title, description, status, priority, type, story_points, assignee_id) VALUES
(1, 1, 1, '优化API缓存机制', '实现Redis缓存以提升API响应速度', 'done', 'high', 'feature', 8, 1),
(1, 1, 1, '数据库索引优化', '为关键查询添加和优化数据库索引', 'doing', 'high', 'feature', 5, 2),
(1, 1, 2, '优化数据库查询语句', '重写慢查询，提升查询效率', 'todo', 'medium', 'optimization', 3, 2),
(1, 1, 3, '负载均衡配置', '配置负载均衡以提升并发处理能力', 'blocked', 'medium', 'infrastructure', 5, 3),
(1, 1, 4, '实现用户注册界面', '开发用户注册的前端界面', 'done', 'high', 'feature', 3, 2),
(1, 1, 5, '实现用户登录认证', '开发用户登录认证逻辑', 'doing', 'high', 'feature', 5, 3),
(2, 3, 6, '编写单元测试', '为用户资料管理功能编写单元测试', 'done', 'medium', 'testing', 3, 2);

-- 插入团队用户关系
INSERT INTO team_user (team_id, user_id) VALUES
(1, 1), (1, 2), (1, 3),
(2, 2), (2, 3),
(3, 1), (3, 2);

-- 插入测试复盘报告
INSERT INTO retrospective_report (team_id, sprint_id, title, highlights, bottlenecks, suggestions, metrics_json) VALUES
(1, 1, 'Sprint 1 复盘报告', '完成了大部分核心功能开发，团队协作良好', '部分任务延期，需求变更较多', '加强需求管理，提前规划风险', '{"completedTasks": 15, "totalTasks": 18, "completionRate": 83.3}'),
(2, 3, 'Test Sprint 1 复盘报告', '测试覆盖率显著提升，发现多个潜在问题', '自动化测试脚本编写耗时较长', '引入更多测试工具，提升自动化水平', '{"completedTasks": 8, "totalTasks": 8, "completionRate": 100.0}');

-- 插入测试任务KR关联
INSERT INTO task_kr (task_id, kr_id) VALUES
(1, 1), (2, 1), (3, 2), (4, 3), (5, 4), (6, 5), (7, 6);

-- 插入测试指标快照
INSERT INTO metric_snapshot (team_id, sprint_id, snapshot_date, okr_progress, total_tasks, completed_tasks, blocked_tasks, velocity) VALUES
(1, 1, '2024-01-07 00:00:00', 65.00, 18, 12, 1, 42.5),
(1, 1, '2024-01-14 00:00:00', 75.00, 18, 15, 0, 45.2);