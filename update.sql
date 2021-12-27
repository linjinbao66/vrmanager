USE vrmanager;

-- 修改admin密码
update user set password='toor' where username='admin' and sn='admin';

-- clazz表增加clazz_no列
ALTER TABLE clazz ADD COLUMN clazz_no BIGINT(20) NULL;

-- 增加表clazz_user

USE vrmanager;
CREATE TABLE clazz_user(id BIGINT(20), clazz_no BIGINT(20), user_sn BIGINT(20));