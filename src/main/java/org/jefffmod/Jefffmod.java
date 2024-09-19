package org.jefffmod;

import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.plugin.Plugin;

public class Jefffmod extends Plugin {
	
	@Override
	public void onLoad() {



		//creating and registering a new module
		final AFKBoostFly afkBoostFly = new AFKBoostFly();
		RusherHackAPI.getModuleManager().registerFeature(afkBoostFly);
		final MapCopy mapCopy = new MapCopy();
		RusherHackAPI.getModuleManager().registerFeature(mapCopy);
	}

	@Override
	public void onUnload() {
		this.getLogger().info("Jefffmod unloaded!");

	}
	
}