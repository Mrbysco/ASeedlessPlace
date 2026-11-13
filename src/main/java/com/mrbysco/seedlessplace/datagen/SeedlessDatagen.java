package com.mrbysco.seedlessplace.datagen;

import com.mrbysco.seedlessplace.SeedlessPlaceMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = SeedlessPlaceMod.MOD_ID)
public class SeedlessDatagen {
	@SubscribeEvent
	public static void dataGenEvent(GatherDataEvent.Client event) {
		event.createProvider(SeedlessMapProvider::new);
	}
}
