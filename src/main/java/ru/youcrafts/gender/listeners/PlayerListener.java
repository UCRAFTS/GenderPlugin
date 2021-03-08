package ru.youcrafts.gender.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import ru.youcrafts.gender.GenderPlugin;
import ru.youcrafts.gender.managers.GenderManager;

public class PlayerListener implements Listener
{


    private final GenderManager genderManager;


    public PlayerListener(GenderManager genderManager)
    {
        this.genderManager = genderManager;
    }


    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerLogin(final PlayerLoginEvent event)
    {
        Bukkit.getScheduler().runTaskLaterAsynchronously(
                GenderPlugin.getInstance(), () -> this.genderManager.load(event.getPlayer().getUniqueId()), 100
        );
    }
}
