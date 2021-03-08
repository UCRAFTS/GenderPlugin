package ru.youcrafts.gender.types;

import org.jetbrains.annotations.NotNull;

public enum ConfigType
{


    DB_HOST("db.host"),
    DB_PORT("db.port"),
    DB_USER("db.user"),
    DB_PASS("db.pass"),
    DB_BASE("db.base"),
    DB_TABLES_PREFIX("db.tablesPrefix"),
    GENDER_MAN_SYMBOL("gender.man.symbol"),
    GENDER_WOMAN_SYMBOL("gender.woman.symbol"),
    GENDER_MAN_MESSAGE("gender.man.message"),
    GENDER_WOMAN_MESSAGE("gender.woman.message"),
    GENDER_HELP_MESSAGE("gender.help.message"),
    GENDER_ALREADY_MESSAGE("gender.already.message");


    private final String name;


    ConfigType(@NotNull final String name)
    {
        this.name = name;
    }


    public String getName()
    {
        return this.name;
    }
}
