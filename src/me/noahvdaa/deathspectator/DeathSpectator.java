package me.noahvdaa.deathspectator;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import me.noahvdaa.bstats.Metrics;

public class DeathSpectator extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		new Metrics(this);
		getServer().getPluginManager().registerEvents(this, this);
		
		saveDefaultConfig();
	}
	
	@EventHandler
	public void deathEvent(PlayerDeathEvent e) {
        Player p = (Player) e.getEntity();
		if(p.hasPermission("deathspectator.bypass")) return;
		if(getConfig().getString("method").equalsIgnoreCase("respawn")) {
			BukkitScheduler scheduler = getServer().getScheduler();
			scheduler.scheduleSyncDelayedTask(this, new Runnable() {
	        		@Override
	            		public void run() {
	                		if(p.isDead()) {
	                			p.spigot().respawn();
	                		}
	                		p.setGameMode(GameMode.SPECTATOR);
	            		}
	        	}, 1L);
		}
	}
	
	@EventHandler
	public void damage(EntityDamageEvent e) {
		Player p = (Player) e.getEntity();
		if(p.hasPermission("deathspectator.bypass")) return;
		if(e.getDamage() < p.getHealth()) return;
		if(getConfig().getString("method").equalsIgnoreCase("instant")) {
			e.setCancelled(true);
            		p.setHealth(20.0);
            		p.setGameMode(GameMode.SPECTATOR);
		}
	}
	
}
