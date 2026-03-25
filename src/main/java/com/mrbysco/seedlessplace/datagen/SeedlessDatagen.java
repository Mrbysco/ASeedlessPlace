package com.mrbysco.seedlessplace.datagen;

import com.mrbysco.seedlessplace.SeedlessPlaceMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = SeedlessPlaceMod.MOD_ID)
public class SeedlessDatagen {
	@SubscribeEvent
	public static void dataGenEvent(GatherDataEvent event) {
		DataGenerator gen = event.getGenerator();
		PackOutput output = gen.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		gen.addProvider(true, new SeedlessMapProvider(output, lookupProvider));
	}
}
