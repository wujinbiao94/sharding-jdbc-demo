# sharding-jdbc-demo
当当开源框架分库分表简单demo

本项目使用水平划分两个库两个表，外加一个分布式主键id库，其中，分布式主键id的解决方法为：每次读取一段id保存在本地，以避免读写比1：1的问题。代码逻辑比较简单，其中分库分表的方案参考https://blog.csdn.net/bntX2jSQfEHy7/article/details/82836694该方案设计，代码比较简单，只需要配置简单的数据库连接，如需更改表结构，请自行更改表的映射和切片字段。
