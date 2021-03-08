package ru.youcrafts.gender.database;

import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import ru.youcrafts.gender.Config;
import ru.youcrafts.gender.GenderPlugin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractDataSource implements DataSourceInterface
{


    protected Config config;
    protected String driverClassName;
    protected HikariDataSource dataSource;


    public AbstractDataSource(Config config)
    {
        this.config = config;
    }


    public void close()
    {
        this.dataSource.close();
    }


    public Connection getConnection() throws SQLException
    {
        return this.dataSource.getConnection();
    }


    public void execute(final PreparedStatement preparedStatement)
    {
        Bukkit.getScheduler().runTaskAsynchronously(GenderPlugin.getInstance(), () -> {
            try {
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
