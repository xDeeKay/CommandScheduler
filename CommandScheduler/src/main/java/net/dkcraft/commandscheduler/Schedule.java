package net.dkcraft.commandscheduler;

import java.util.List;

public class Schedule {

	private String time;

	private List<String> commands;
	
	public Schedule(String time, List<String> commands) {
		this.setTime(time);
		this.setCommands(commands);
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<String> getCommands() {
		return commands;
	}

	public void setCommands(List<String> commands) {
		this.commands = commands;
	}
}
