package ru.youcrafts.gender.api;

import org.bukkit.entity.Player;
import ru.youcrafts.gender.managers.GenderManager;
import ru.youcrafts.gender.types.GenderType;

public class GenderAPI
{


    public static boolean hasGender(Player player)
    {
        return GenderManager.getGender(player.getUniqueId()) != null;
    }


    public static Integer getGender(Player player)
    {
        return GenderManager.getGender(player.getUniqueId());
    }


    public static boolean isMan(Player player)
    {
        if (!GenderAPI.hasGender(player)) {
            return false;
        }

        return GenderAPI.getGender(player).equals(GenderType.MAN.getType());
    }


    public static boolean isWoman(Player player)
    {
        if (!GenderAPI.hasGender(player)) {
            return false;
        }

        return GenderAPI.getGender(player).equals(GenderType.WOMAN.getType());
    }
}
