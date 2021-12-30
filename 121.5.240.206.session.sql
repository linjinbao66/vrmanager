USE vrmanager;
SELECT
	clazz.id AS '班级编号',
	clazz.`name` AS '班级名称',
	`user`.sn AS '学号',
	`user`.username AS '学生姓名',
	SUM(IF(score.type=0,score.score,0)) AS '理论成绩',
	SUM(IF(score.type=1,score.score,0)) AS '实操成绩',
	SUM(IF(score.student_sn=`user`.sn,1,0)) AS '考试次数'
FROM
	clazz
	LEFT JOIN `user` ON clazz.id = `user`.clazz_id 
	LEFT JOIN score ON score.student_sn = `user`.sn
WHERE
	`user`.role_id = 1 
	AND clazz_id = 50 
ORDER BY
	`user`.sn;

