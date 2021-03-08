package ru.youcrafts.gender;

import de.leonhard.storage.Json;
import de.leonhard.storage.internal.FlatFile;
import ru.youcrafts.gender.types.ConfigType;

public class Config
{


    private final FlatFile config;


    public Config(GenderPlugin plugin)
    {
        this.config = new Json("config", plugin.getDataFolder().getPath());
        this.config.setDefault(ConfigType.DB_HOST.getName(), "127.0.0.1");
        this.config.setDefault(ConfigType.DB_PORT.getName(), 3306);
        this.config.setDefault(ConfigType.DB_BASE.getName(), "servers");
        this.config.setDefault(ConfigType.DB_USER.getName(), "user");
        this.config.setDefault(ConfigType.DB_PASS.getName(), "secret");
        this.config.setDefault(ConfigType.DB_TABLES_PREFIX.getName(), "gender");
        this.config.setDefault(ConfigType.GENDER_MAN_SYMBOL.getName(), "§b♂ ");
        this.config.setDefault(ConfigType.GENDER_WOMAN_SYMBOL.getName(), "§d♀ ");
        this.config.setDefault(ConfigType.GENDER_MAN_MESSAGE.getName(), "§2╔\n§2║ §aВаш гендер успешно изменен на §bмужской!\n§2╚");
        this.config.setDefault(ConfigType.GENDER_WOMAN_MESSAGE.getName(), "§2╔\n§2║ §aВаш гендер успешно изменен на §dженский!\n§2╚");
        this.config.setDefault(ConfigType.GENDER_ALREADY_MESSAGE.getName(), " §l§c\u2022 §cДанный гендер уже выбран!");
        this.config.setDefault(ConfigType.GENDER_HELP_MESSAGE.getName(), "§6╔\n§6║ §rВыберите гендер для своего персонажа:\n§6║ §f/sex §7м - для §bпарней\n§6║ §f/sex §7ж - для §dдевушек\n§6╚");
    }


    public FlatFile getConfig()
    {
        return this.config;
    }
}
