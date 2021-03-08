package ru.youcrafts.gender.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.youcrafts.gender.Config;
import ru.youcrafts.gender.types.ConfigType;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class MySQLDataSource extends AbstractDataSource implements DataSourceInterface
{


    protected String driverClassName = "com.mysql.jdbc.Driver";


    public MySQLDataSource(Config config)
    {
        super(config);

        if (this.dataSource != null) {
            this.close();
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(this.driverClassName);
        hikariConfig.setUsername(this.config.getConfig().getString(ConfigType.DB_USER.getName()));
        hikariConfig.setPassword(this.config.getConfig().getString(ConfigType.DB_PASS.getName()));
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setPoolName("GenderPool");
        hikariConfig.setJdbcUrl(
                String.format(
                        "jdbc:mysql://%s:%s/%s?useSSL=true&verifyServerCertificate=false",
                        this.config.getConfig().getString(ConfigType.DB_HOST.getName()),
                        this.config.getConfig().getInt(ConfigType.DB_PORT.getName()),
                        this.config.getConfig().getString(ConfigType.DB_BASE.getName())
                )
        );

        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("logWriter", new PrintWriter(System.out));

        this.dataSource = new HikariDataSource(hikariConfig);
        this.createTables();
    }


    private void createTables()
    {
        String balanceTable = String.format(
            "CREATE TABLE IF NOT EXISTS `%s_sex` (`uuid` varchar(36) NOT NULL,`sex` bigint NOT NULL DEFAULT '0',PRIMARY KEY (`uuid`),KEY `%s_sex_uuid` (`uuid`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;",
            this.config.getConfig().getString(ConfigType.DB_TABLES_PREFIX.getName()),
            this.config.getConfig().getString(ConfigType.DB_TABLES_PREFIX.getName())
        );

        try (Connection connection = this.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(balanceTable);
            preparedStatement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Cant create plugin table!");
        }
    }
}
