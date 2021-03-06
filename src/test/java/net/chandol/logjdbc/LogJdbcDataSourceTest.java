package net.chandol.logjdbc;


import net.chandol.logjdbc._testhelper.AutoLogInitializer;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.*;

import static net.chandol.logjdbc._fixture.DummyDataSource.getDummyH2DataSource;


public class LogJdbcDataSourceTest extends AutoLogInitializer {

    @Test
    public void core() throws SQLException {
        DataSource h2DataSource = getDummyH2DataSource();
        DataSource dataSource = new LogJdbcDataSource(h2DataSource);

        Connection connection = dataSource.getConnection();

        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE CUSTOMERS (ID INT NOT NULL, NAME VARCHAR (20) NOT NULL, AGE INT NOT NULL, ADDRESS CHAR (25), SALARY DECIMAL(18, 2), PRIMARY KEY (ID));");
        statement.execute("INSERT INTO CUSTOMERS (ID, NAME, AGE, ADDRESS, SALARY) VALUES (2, 'Khilan', 25, 'Delhi', 1500.00 );");
        statement.execute("INSERT INTO CUSTOMERS (ID, NAME, AGE, ADDRESS, SALARY) VALUES (3, 'kaushik', 23, 'Kota', 2000.00 );");
        statement.execute("INSERT INTO CUSTOMERS (ID, NAME, AGE, ADDRESS, SALARY) VALUES (4, 'Chaitali', 25, 'Mumbai', 6500.00 );");
        statement.execute("INSERT INTO CUSTOMERS (ID, NAME, AGE, ADDRESS, SALARY) VALUES (5, 'Hardik', 27, 'Bhopal', 8500.00 );");
        statement.execute("INSERT INTO CUSTOMERS (ID, NAME, AGE, ADDRESS, SALARY) VALUES (6, 'Komal', 22, 'MP', 4500.00 );");

        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM CUSTOMERS WHERE ID=?");
        pstmt.setLong(1, 3);
        ResultSet resultSet = pstmt.executeQuery();

        while (resultSet.next()) {
        }

        PreparedStatement pstmt2 = connection.prepareStatement("SELECT * FROM CUSTOMERS");
        ResultSet resultSet2 = pstmt2.executeQuery();

        while (resultSet2.next()) {
        }

        PreparedStatement pstmt3 = connection.prepareStatement("SELECT * FROM CUSTOMERS WHERE  NAME=?");
        pstmt3.setString(1, "Hardik");
        ResultSet resultSet3 = pstmt3.executeQuery();

        while (resultSet3.next()) {
        }

        resultSet.close();
        resultSet2.close();
        resultSet3.close();

        connection.rollback();
        connection.close();
    }

}