# 虚拟现实后台文档

## 开发文档

- 下载

  `git clone https://github.com/linjinbao666/vrmanager`

## 部署文档

- 编译

  `mvn clean package -Dmaven.test.skip=true`

- 数据库导入

  `source vrmanager.sql`

- 运行

  `java -jar target/*.jar -Dspring.datasource.url="jdbc:mysql://121.5.240.206:3306/vrmanager"`
