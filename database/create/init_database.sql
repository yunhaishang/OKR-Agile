USE okr_agile_db;
-- 初始化数据库表结构
-- 该脚本按依赖关系顺序创建所有表

-- 首先确保删除现有表（按依赖关系逆序）
DROP TABLE IF EXISTS retrospective_report;
DROP TABLE IF EXISTS metric_snapshot;
DROP TABLE IF EXISTS task_kr;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS key_result;
DROP TABLE IF EXISTS objective;
DROP TABLE IF EXISTS sprint;
DROP TABLE IF EXISTS team_user;
DROP TABLE IF EXISTS team;
DROP TABLE IF EXISTS user;

-- 创建表结构（按依赖关系顺序）
SOURCE ./table_user_create.sql;
SOURCE ./table_team_create.sql;
SOURCE ./table_team_user_create.sql;
SOURCE ./table_sprint_create.sql;
SOURCE ./table_objective_create.sql;
SOURCE ./table_key_result_create.sql;
SOURCE ./table_task_create.sql;
SOURCE ./table_task_kr_create.sql;
SOURCE ./table_metric_snapshot_create.sql;
SOURCE ./table_retrospective_report_create.sql;

-- 重置自增主键为1
SOURCE reset_auto_increment.sql;

-- 初始化测试数据
SOURCE test_data.sql;

-- 提示信息
SELECT 'Database schema initialization completed successfully!' AS message;