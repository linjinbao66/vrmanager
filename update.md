# 更新文档

## 2022-03-19

```sql

-- 1.删除唯一健
USE vrmanager;
SHOW INDEX FROM `user`;
ALTER TABLE `user` DROP INDEX `user_sn_uindex`;
SHOW INDEX FROM `user`;
-- 2.调整大小
ALTER TABLE `user` MODIFY sn VARCHAR(50);
ALTER TABLE `user` MODIFY clazz_no VARCHAR(50);
-- 3. 增加score表字段
ALTER TABLE score ADD COLUMN clazz_no VARCHAR(50);
-- 4. 修正数据库
update user set clazz_no="" where clazz_no is null;
alter table user modify column clazz_no varchar(50) not null;
alter table user modify column sn varchar(50) not null;
ALTER TABLE `user` MODIFY COLUMN clazz_no VARCHAR(50) NOT NULL DEFAULT "";
ALTER TABLE `user` MODIFY COLUMN sn VARCHAR(50) NOT NULL DEFAULT "";

```
