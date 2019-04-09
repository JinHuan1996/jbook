package com.jh.jbook;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SqlTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void sqlTest(){

        try {
            // 获取数据库jdbc连接
            Connection connection = dataSource.getConnection();
            int id = 3;
            String sql = "SELECT * FROM user WHERE id = ?";
            // 调用PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Object object = resultSet.getObject("username");
                System.out.println(object);
                int anInt = resultSet.getInt(1);
                System.out.println(anInt);
            }
            StringBuilder sb = new StringBuilder("SELECT * FROM user WHERE id = ");
            sb.append(3).append(" and").append(" username = ").append("'lisi4'");
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sb.toString());
            while (resultSet.next()) {
                Object object = resultSet.getObject("username");
                System.out.println(object);
                int anInt = resultSet.getInt(1);
                System.out.println(anInt);
            }

            statement.close();
            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
