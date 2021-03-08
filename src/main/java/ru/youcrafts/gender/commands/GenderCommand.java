package ru.youcrafts.gender.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.entity.Player;
import ru.youcrafts.gender.Config;
import ru.youcrafts.gender.api.GenderAPI;
import ru.youcrafts.gender.managers.GenderManager;
import ru.youcrafts.gender.types.ConfigType;
import ru.youcrafts.gender.types.GenderType;

import java.util.*;

public class GenderCommand extends BaseCommand
{


    private final Config config;
    private final GenderManager genderManager;
    private final HashMap<Integer, List<String>> genderTypes = new HashMap<>();


    public GenderCommand(Config config, GenderManager genderManager)
    {
        this.config = config;
        this.genderManager = genderManager;
        this.genderTypes.put(GenderType.MAN.getType(), Arrays.asList("0", "м", "m"));
        this.genderTypes.put(GenderType.WOMAN.getType(), Arrays.asList("1", "w", "ж"));
    }


    @CommandAlias("gender|sex")
    @Syntax("<sex>")
    public void onCommand(Player player, String[] args)
    {
        if (args.length < 1) {
            return;
        }

        Integer gender = GenderManager.getGender(player.getUniqueId());
        String selected = args[0];

        for (Map.Entry<Integer, List<String>> genderType : this.genderTypes.entrySet()) {
            if (!genderType.getValue().contains(selected)) {
                continue;
            }

            if (gender != null && gender.equals(genderType.getKey())) {
                player.sendMessage(this.config.getConfig().getString(ConfigType.GENDER_ALREADY_MESSAGE.getName()));
                return;
            }

            try {
                if (GenderAPI.hasGender(player)) {
                    this.genderManager.change(player.getUniqueId(), genderType.getKey());
                } else {
                    this.genderManager.set(player.getUniqueId(), genderType.getKey());
                }

                player.sendMessage(
                        genderType.getKey().equals(GenderType.MAN.getType())
                                ? this.config.getConfig().getString(ConfigType.GENDER_MAN_MESSAGE.getName())
                                : this.config.getConfig().getString(ConfigType.GENDER_WOMAN_MESSAGE.getName())
                );

                return;
            } catch (RuntimeException e) {
                ;
            }
        }

        player.sendMessage(this.config.getConfig().getString(ConfigType.GENDER_HELP_MESSAGE.getName()));
    }
}
