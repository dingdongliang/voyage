package com.dyenigma.store;

import com.mysql.jdbc.ReplicationDriver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * topic mysql读写分离示例
 * author: dyenigma
 * create: 2016/3/1 14:58
 */
public class Replication {

    public static void main(String... args) {
        try {
            ReplicationDriver driver = new ReplicationDriver();

            Properties props = new Properties();

            //故障处理，自动重连
            props.put("autoReconnect", "true");

            //负载均衡
            props.put("roundRobinLoadBalance", "true");

            props.put("user", "foo");
            props.put("password", "bar");

            Connection conn = driver.connect("jdbc:mysql:replication://master,slave1,slave2,slave3/test", props);

            conn.setReadOnly(false);
            conn.setAutoCommit(false);
            conn.createStatement().executeUpdate("UPDATE some_table ....");
            conn.commit();

            conn.setReadOnly(true);
            ResultSet rs = conn.createStatement().executeQuery("SELECT a,b FROM alt_table");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
