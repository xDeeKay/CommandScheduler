package net.dkcraft.commandscheduler;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public Main main;
	
	public Config config;
	
	public Map<String, Schedule> schedules = new HashMap<String, Schedule>();

	public void onEnable() {

		this.main = this;

		config = new Config(this);

		config.loadSchedules();
	}
	
	public void onDisable() {

	}
}
