package com.mrbysco.seedlessplace;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeedlessConfig {
	private static final Map<Item, Item> SEED_REPLICA_MAP = new HashMap<>();

	public static class Common {
		// List option that dictates which crop item behaves like what seed
		public final ForgeConfigSpec.ConfigValue<List<? extends String>> seedReplicas;

		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("Seedless settings")
					.push("seedless");

			String[] conversions = new String[]
					{
							"minecraft:wheat,minecraft:wheat_seeds",
							"minecraft:beetroot,minecraft:beetroot_seeds",
							"minecraft:melon_slice,minecraft:melon_seeds",
					};

			seedReplicas = builder
					.comment("A list of crop item to seed item conversions in the format 'crop_item,seed_item'.",
							"Example: 'minecraft:wheat,minecraft:wheat_seeds' makes wheat behave like wheat seeds when placed.",
							"Add your own conversions as needed. Default is wheat to wheat seeds.",
							"Note: Use the item registry names.")
					.defineListAllowEmpty("seedReplicas", () -> List.of(conversions), o -> (o instanceof String));

			builder.pop();
		}
	}

	public static final ForgeConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfigEvent configEvent) {
		ModConfig config = configEvent.getConfig();
		refreshCache(config.getType());
	}

	private static void refreshCache(ModConfig.Type type) {
		if (type == ModConfig.Type.COMMON) {
			for (String entry : COMMON.seedReplicas.get()) {
				String[] parts = entry.split(",");
				if (parts.length == 2) {
					try {
						ResourceLocation crop = ResourceLocation.tryParse(parts[0]);
						if (crop == null) {
							SeedlessPlaceMod.LOGGER.error("Invalid crop item in seed replica entry: {}", entry);
							continue;
						}
						Item cropItem = ForgeRegistries.ITEMS.getValue(crop);

						ResourceLocation seed = ResourceLocation.tryParse(parts[1]);
						if (seed == null) {
							SeedlessPlaceMod.LOGGER.error("Invalid seed item in seed replica entry: {}", entry);
							continue;
						}
						Item seedItem = ForgeRegistries.ITEMS.getValue(seed);

						if (cropItem != null && seedItem != null) {
							SEED_REPLICA_MAP.put(cropItem, seedItem);
						}
					} catch (Exception e) {
						SeedlessPlaceMod.LOGGER.error("Error parsing seed replica entry: {}", entry, e);
					}
				} else {
					SeedlessPlaceMod.LOGGER.error("Invalid seed replica entry: {}", entry);
				}
			}
		}
	}

	@NotNull
	public static ItemStack getReplica(ItemStack stack) {
		if (SEED_REPLICA_MAP.containsKey(stack.getItem()))
			return SEED_REPLICA_MAP.get(stack.getItem()).getDefaultInstance();
		return ItemStack.EMPTY;
	}

	@SubscribeEvent
	public static void onLoad(final ModConfigEvent.Loading configEvent) {
		SeedlessPlaceMod.LOGGER.debug("Loaded A Seedless Place's config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
		SeedlessPlaceMod.LOGGER.warn("Seedless A Place's config just got changed on the file system!");
	}
}
