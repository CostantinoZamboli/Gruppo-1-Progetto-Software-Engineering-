package main;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class PostgresDbConnectionTest {

    @Test
    void connect() {
        PostgresDbConnection dbConnection = new PostgresDbConnection();
        Connection connection = dbConnection.connect();
        assertNotNull(connection, "L'oggetto dbConnection è null");
    }
}