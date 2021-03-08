package ru.youcrafts.gender;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import ru.youcrafts.gender.commands.GenderCommand;
import ru.youcrafts.gender.database.AbstractDataSource;
import ru.youcrafts.gender.database.MySQLDataSource;
import ru.youcrafts.gender.listeners.PlayerListener;
import ru.youcrafts.gender.managers.GenderManager;
import ru.youcrafts.gender.placeholders.GenderPlaceholder;

@Plugin(
        name = "GenderPlugin",
        version = "1.0.0"
)
@Author(value = "oDD1 / Alexander Repin")
@Description(value = "Player gender plugin with support PlaceholderAPI")
@Dependency(value = "PlaceholderAPI")
public class GenderPlugin extends JavaPlugin
{


    private static GenderPlugin instance;
    private static PaperCommandManager commandManager;

    private GenderManager genderManager;
    private Config config;
    private AbstractDataSource dataSource;


    @Override
    public void onEnable()
    {
        GenderPlugin.instance = this;
        GenderPlugin.commandManager = new PaperCommandManager(this);

        this.config = new Config(this);
        this.dataSource = new MySQLDataSource(this.config);
        this.genderManager = new GenderManager(this.config, this.dataSource);

        this.registerPlaceholders();
        this.registerCommands();
        this.registerListeners();
    }


    public static GenderPlugin getInstance()
    {
        return GenderPlugin.instance;
    }


    private void registerCommands()
    {
        GenderPlugin.commandManager.registerCommand(new GenderCommand(this.config, this.genderManager));
    }


    private void registerListeners()
    {
        Bukkit.getPluginManager().registerEvents(
                new PlayerListener(this.genderManager), this
        );
    }


    private void registerPlaceholders()
    {
        new GenderPlaceholder(this, this.config).register();
    }


    @Override
    public void onDisable()
    {
        this.dataSource.close();
    }
}
