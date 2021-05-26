package com.jdbc.dao;

import com.jdbc.bean.Message;
import com.jdbc.common.ConnectUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//消息DAO
public class MessageDAO {


    /**
     * 分页查询全部留言
     * @param page page当前页码
     * @param pageSize 每页记录数
     * @return
     * @throws Exception
     */

    public  List<Message> getMessageList(int page ,int pageSize)  {

        Connection conn=ConnectUtil.getConnection();

        String sql="select * from messages order by create_time desc limit ?,?";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Message> messages = new ArrayList<>();
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, (page - 1) * pageSize);
            stmt.setInt(2, pageSize);
            rs = stmt.executeQuery();
            while (rs.next()) {
                messages.add(new Message(rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("create_time")));

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectUtil.release(rs, stmt, conn);
        }
        return messages;
    }



    /**
     * 计算所有留言数量
     * @return
     * @throws Exception
     */
    public  int countMessages() {
        Connection conn = ConnectUtil.getConnection();
        String sql = "select count(*) total from messages";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectUtil.release(rs, stmt, conn);
        }
        return 0;
    }

    /**
     * 保存新建的留言
     * @return
     */
    public  boolean save(Message message){
        Connection conn = ConnectUtil.getConnection();
        String sql = "insert into messages(user_id,username,title,content,create_time) values(?,?,?,?,?)";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, message.getUserId());
            stmt.setString(2, message.getUserName());
            stmt.setString(3, message.getTitle());
            stmt.setString(4, message.getContent());
            stmt.setTimestamp(5, new Timestamp(message.getCreateTime().getTime()));
            stmt.execute();
            System.out.println(message.getTitle()+"---------------");
        } catch (Exception e) {
            System.out.println("保存留言信息失败");
            e.printStackTrace();
            return false;
        }finally {
            ConnectUtil.release(null,stmt,conn);
        }


        return true;
    }
}
