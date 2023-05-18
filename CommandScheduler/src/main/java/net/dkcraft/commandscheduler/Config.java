package net.dkcraft.commandscheduler;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class Config {

	public Main plugin;

	public Config(Main plugin) {
		this.plugin = plugin;
	}

	public void loadSchedules() {

		plugin.getConfig().options().copyDefaults(true);
		plugin.saveDefaultConfig();

		if (plugin.getConfig().isSet("schedules")) {

			Set<String> scheduleSet = plugin.getConfig().getConfigurationSection("schedules").getKeys(false);

			if (!scheduleSet.isEmpty()) {

				for (String schedule : scheduleSet) {

					String time = plugin.getConfig().getString("schedules." + schedule + ".time");
					List<String> commands = plugin.getConfig().getStringList("schedules." + schedule + ".commands");

					plugin.schedules.put(schedule, new Schedule(time, commands));
					plugin.getLogger().log(Level.INFO, "Added schedule " + schedule + " for " + time + " executing " + commands.size() + " command(s)");

					repeatSchedule(schedule, new Runnable() {
						public void run() {
							plugin.getLogger().log(Level.INFO, "Running schedule " + schedule + " and executing " + commands.size() + " command(s)");
							for (String command : commands) {
								plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
							}
						}
					});
				}
			}
		}
	}

	public int repeatSchedule(String schedule, Runnable task) {

		String time = plugin.schedules.get(schedule).getTime();

		Calendar calendar = Calendar.getInstance();

		long currentTimeInMillis = calendar.getTimeInMillis();

		String[] timeParts = time.split(":");
		int hour = Integer.parseInt(timeParts[0]);
		int minute = Integer.parseInt(timeParts[1]);

		if (calendar.get(Calendar.HOUR_OF_DAY) >= hour && calendar.get(Calendar.MINUTE) >= minute) {
			calendar.add(Calendar.DATE, 1);
		}

		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		long offsetMillis = calendar.getTimeInMillis() - currentTimeInMillis;
		long ticks = offsetMillis / 50L;

		return plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, task, ticks, 1728000L);
	}
}
