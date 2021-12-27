USE vrmanager;

-- 修改admin密码
UPDATE user SET PASSWORD = toor WHERE USER.username = 'admin' AND USER.sn = 'admin';

-- clazz表增加clazz_no列

ALTER TABLE clazz ADD COLUMN clazz_no BIGINT(20) NULL;
