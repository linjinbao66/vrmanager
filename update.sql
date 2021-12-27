USE vrmanager;

-- 修改admin密码
update user set password='toor' where username='admin' and sn='admin';

-- clazz表增加clazz_no列
ALTER TABLE clazz ADD COLUMN clazz_no BIGINT(20) NULL;
