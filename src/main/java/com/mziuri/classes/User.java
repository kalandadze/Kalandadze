package com.mziuri.classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class User {
    Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/messenger","root","password");

    private String username;
    private String password;

    public User(String username, String password) throws SQLException {
        this.username = username;
        this.password = password;
    }

    public List<Message> findMessages() throws SQLException {
        List<Message> messages=new ArrayList<>();
        PreparedStatement ps= conn.prepareStatement("select message from messages where user=?");
        ps.setString(1,username);
        ResultSet rs=ps.executeQuery();
        while (rs.next()){
            messages.add(new Message(this,rs.getString("message")));
        }
        ps.close();
        return messages;
    }

    public void saveUser() throws SQLException {
        if (!exists()){
            PreparedStatement ps=conn.prepareStatement("insert into users(username,password) values(?,?)");
            ps.setString(1,username);
            ps.setString(2,password);
            ps.executeUpdate();
            ps.close();
        }
    }
    public boolean exists() throws SQLException {
        PreparedStatement ps= conn.prepareStatement("select*from users where username=?");
        ps.setString(1,username);
        ResultSet rs=ps.executeQuery();
        return rs.next();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
