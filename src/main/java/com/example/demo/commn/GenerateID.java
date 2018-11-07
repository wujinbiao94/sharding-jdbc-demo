package com.example.demo.commn;

import java.sql.*;

/**
 * Created by 你敬爱的彪哥 on 2018/11/5.
 */
public class GenerateID {
    // JDBC 驱动名及数据库 URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/generate_Id";

    // 数据库的用户名与密码，需要根据自己的设置
    private static final String USER = "root";
    private static final String PASS = "746592";
    //当前id最小值
    public static Long minId = 0L;
    //当前id最大值
    public static Long maxId = 0L;
    /**
     * 一次取出多少id
     * @param num 数量
     */
    public static void queryId(int num) {
        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql= "SELECT nextId FROM generator ORDER BY id DESC ";
            ResultSet rs = stmt.executeQuery(sql);

            // 查询id
            while(rs.next()){
                // 通过字段检索
                Long id  = rs.getLong("nextId");
                // 输出数据
                System.out.println("当但前Id: " + id);
                minId = id+1;
                maxId = id + num;
                //只取最新的数据
                break;
            }
            //跟新库中id
            String insertSql = "INSERT INTO generator (nextId) VALUES ("+maxId+")";
            stmt.execute(insertSql);
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }



}
