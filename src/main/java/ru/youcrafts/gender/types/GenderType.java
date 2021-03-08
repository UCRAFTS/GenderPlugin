package ru.youcrafts.gender.types;

import org.jetbrains.annotations.NotNull;

public enum GenderType
{


    MAN(0),
    WOMAN(1);


    private final Integer type;


    GenderType(@NotNull final Integer type)
    {
        this.type = type;
    }


    public Integer getType()
    {
        return this.type;
    }
}
