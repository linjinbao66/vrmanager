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

```
