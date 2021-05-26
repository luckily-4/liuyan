package com.jdbc.common;


import java.sql.*;

//数据库操作公共类
public class ConnectUtil {
    private ConnectUtil(){}

    private static  String url="jdbc:mysql://localhost:3306/jdbcdemo?serverTimezone=UTC";
    private static  String user="root";
    private static  String password="123456";


    //       1：加载驱动
    static {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("找不到驱动类");
            e.printStackTrace();
        }
    }

    //        2：连接
    public static Connection getConnection(){

        Connection conn= null;
        try {
            conn = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }


    //       3：释放资源
    public static void  release(ResultSet rs, Statement stmt ,Connection conn){

            try {
                if(rs!=null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(stmt!=null) {
                        stmt.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        if(conn!=null) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
               }
            }

    }



}
