package me.noahvdaa.deathspectator;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import me.noahvdaa.bstats.Metrics;

public class DeathSpectator extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		new Metrics(this);
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void deathEvent(PlayerDeathEvent e) {
		BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                Player p = (Player) e.getEntity();
                if(!p.isDead()) return;
                p.spigot().respawn();
                p.setGameMode(GameMode.SPECTATOR);
                
            }
        }, 1L);
	}
	
}