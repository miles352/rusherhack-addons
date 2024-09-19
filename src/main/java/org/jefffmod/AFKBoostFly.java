package org.jefffmod;

import org.rusherhack.client.api.events.client.EventUpdate;
import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.core.event.subscribe.Subscribe;
import org.rusherhack.core.setting.NumberSetting;

public class AFKBoostFly extends ToggleableModule {


	private long lastTime = System.currentTimeMillis();
	private boolean goingUp = false;

	private final NumberSetting<Double> upTime = new NumberSetting<>("Millis Up", 1000.0, 100.0, 10000.0);
	private final NumberSetting<Double> downTime = new NumberSetting<>("Millis Down", 3000.0, 100.0, 10000.0);

	private final NumberSetting<Double> upPitch = new NumberSetting<>("Pitch Up", -25.0, -90.0, 0.0);
	private final NumberSetting<Double> downPitch = new NumberSetting<>("Pitch Down", 20.0, 0.0, 90.0);

	public AFKBoostFly() {
		super("AFKBoostFly", "AFKBoostFly", ModuleCategory.MISC);
		this.registerSettings(
				this.upTime,
				this.downTime,
				this.upPitch,
				this.downPitch
		);
	}

	@Subscribe
	private void onUpdate(EventUpdate event){
		if (goingUp && System.currentTimeMillis() - lastTime > upTime.getValue())
		{
			lastTime = System.currentTimeMillis();
			goingUp = false;
			mc.player.setXRot(downPitch.getValue().floatValue());
		} else if (!goingUp && System.currentTimeMillis() - lastTime > downTime.getValue())
		{
			lastTime = System.currentTimeMillis();
			goingUp = true;
			mc.player.setXRot(upPitch.getValue().floatValue());
		}
	}
}
