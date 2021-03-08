package ru.youcrafts.gender.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.youcrafts.gender.Config;
import ru.youcrafts.gender.GenderPlugin;
import ru.youcrafts.gender.api.GenderAPI;
import ru.youcrafts.gender.types.ConfigType;

public class GenderPlaceholder extends PlaceholderExpansion
{


    private final static String IDENTIFIER = "gender";

    private final GenderPlugin plugin;
    private final Config config;



    public GenderPlaceholder(GenderPlugin plugin, Config config)
    {
        this.plugin = plugin;
        this.config = config;
    }


    @Override
    public boolean persist()
    {
        return true;
    }


    @Override
    public boolean canRegister()
    {
        return true;
    }


    @Override
    public @NotNull String getAuthor()
    {
        return this.plugin.getDescription().getAuthors().toString();
    }


    @Override
    public @NotNull String getIdentifier()
    {
        return GenderPlaceholder.IDENTIFIER;
    }


    @Override
    public @NotNull String getVersion()
    {
        return this.plugin.getDescription().getVersion();
    }


    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier)
    {
        if (player == null) {
            return "";
        }

        if (!identifier.equals(GenderPlaceholder.IDENTIFIER)) {
            return "";
        }

        if (!GenderAPI.hasGender(player)) {
            return "";
        }

        return GenderAPI.isMan(player)
                ? this.config.getConfig().getString(ConfigType.GENDER_MAN_SYMBOL.getName())
                : this.config.getConfig().getString(ConfigType.GENDER_WOMAN_SYMBOL.getName());
    }
}