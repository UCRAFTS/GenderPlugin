package ru.youcrafts.gender.managers;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import ru.youcrafts.gender.Config;
import ru.youcrafts.gender.GenderPlugin;
import ru.youcrafts.gender.database.AbstractDataSource;
import ru.youcrafts.gender.types.ConfigType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.UUID;

public class GenderManager
{


    private static final HashMap<UUID, Integer> genders = new HashMap<>();

    private final Config config;
    private final AbstractDataSource dataSource;


    public GenderManager(Config config, AbstractDataSource dataSource)
    {
        this.config = config;
        this.dataSource = dataSource;
    }


    public void load(@NotNull UUID uuid)
    {
        String query = String.format(
                "SELECT sex FROM %s_sex WHERE uuid = '%s'",
                this.config.getConfig().getString(ConfigType.DB_TABLES_PREFIX.getName()),
                uuid.toString()
        );

        Bukkit.getScheduler().runTaskAsynchronously(GenderPlugin.getInstance(), () -> {
            try (Connection connection = this.dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet result = preparedStatement.executeQuery();

                if (result.next()) {
                    GenderManager.genders.put(uuid, result.getInt(1));
                }
            } catch (Exception e) {
                throw new RuntimeException("Cant load player data!");
            }
        });
    }


    public void set(@NotNull UUID uuid, Integer gender)
    {
        String query = String.format(
                "INSERT INTO %s_sex VALUES('%s', %s)",
                this.config.getConfig().getString(ConfigType.DB_TABLES_PREFIX.getName()),
                uuid.toString(),
                gender
        );

        Bukkit.getScheduler().runTaskAsynchronously(GenderPlugin.getInstance(), () -> {
            try (Connection connection = this.dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.execute();

                GenderManager.genders.put(uuid, gender);
            } catch (Exception e) {
                throw new RuntimeException("Cant set player data!");
            }
        });
    }


    public void change(@NotNull UUID uuid, Integer gender)
    {

        String query = String.format(
                "UPDATE %s_sex SET sex = %s WHERE uuid = '%s'",
                this.config.getConfig().getString(ConfigType.DB_TABLES_PREFIX.getName()),
                gender,
                uuid.toString()
        );

        Bukkit.getScheduler().runTaskAsynchronously(GenderPlugin.getInstance(), () -> {
            try (Connection connection = this.dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.execute();

                GenderManager.genders.put(uuid, gender);
            } catch (Exception e) {
                throw new RuntimeException("Cant change player data!");
            }
        });
    }


    public static Integer getGender(@NotNull UUID uuid)
    {
        return GenderManager.genders.getOrDefault(uuid, null);
    }
}
