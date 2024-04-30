package com.example.demo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author chris
 * @className demo
 * @date 2023/12/13 10:56
 * @description:
 */
@Service
public class SqlServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlServer.class);

    public void sqlUpdate(String msgKey, Connection conn,String sql) throws Exception{
        PreparedStatement statement=null;
        try {

            statement=conn.prepareStatement(sql);
            //int result=statement.executeUpdate();
            statement.execute();
            int result=statement.getUpdateCount();
            LOGGER.info("更新完成条数:{}",result);

        } finally {
            if(null!= statement){
                try {
                    statement.close();
                }catch (SQLException throwables){
                    throwables.printStackTrace();
                }
            }
        }
    }
}
