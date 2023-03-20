package me.wizzo.gummymc.database;

import com.zaxxer.hikari.HikariDataSource;
import me.wizzo.gummymc.GummyMC;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class HikariCPSettings {

    public String className, url, host, port, database, username, password, vanishTable, mentionTable;
    private HikariDataSource dataSource;
    private final GummyMC main;

    public HikariCPSettings(GummyMC main) {
        this.main = main;
    }

    public void initSource() throws SQLException {
        this.className = main.getDbConfig("ClassName");
        this.host = main.getDbConfig("Host");
        this.port = main.getDbConfig("Port");
        this.database = main.getDbConfig("Database");
        this.username = main.getDbConfig("Username");
        this.password = main.getDbConfig("Password");
        this.vanishTable = main.getDbConfig("Table.Vanish");
        this.mentionTable = main.getDbConfig("Table.Mention");
        this.url = main.getDbConfig("Url")
                .replace("{ip}", host)
                .replace("{port}", port)
                .replace("{database}", database);

        dataSource = new HikariDataSource();
        dataSource.setMaximumPoolSize(Integer.parseInt(main.getDbConfig("Max-pool-size")));
        dataSource.setDriverClassName(className);
        dataSource.setJdbcUrl(url);
        dataSource.addDataSourceProperty("user", username);
        dataSource.addDataSourceProperty("password", password);
        dataSource.addDataSourceProperty("database", database);

        testDataSource(dataSource);
    }

    public void close(HikariDataSource source) {
        source.close();
    }

    public HikariDataSource getSource() {
        return dataSource;
    }

    private void testDataSource(DataSource source) throws SQLException {
        try (Connection connection = source.getConnection()) {
            if (!connection.isValid(1000)) {
                throw new SQLException("Impossibile eseguire la connessione al database");
            }
        }
    }
}
