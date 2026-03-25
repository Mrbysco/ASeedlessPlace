package com.mrbysco.seedlessplace.datamap;

import com.mrbysco.seedlessplace.SeedlessPlaceMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class SeedlessDatamaps {
	public static final DataMapType<Item, ItemStack> SEED_REPLICAS = DataMapType.builder(
			SeedlessPlaceMod.modLoc("seed_replicas"), Registries.ITEM, ItemStack.CODEC).synced(ItemStack.CODEC, false).build();


	@SubscribeEvent
	public static void register(final RegisterDataMapTypesEvent event) {
		event.register(SEED_REPLICAS);
	}
}
