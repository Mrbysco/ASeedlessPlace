package com.mrbysco.seedlessplace.datagen;

import com.mrbysco.seedlessplace.datamap.SeedlessDatamaps;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class SeedlessMapProvider extends DataMapProvider {
	public SeedlessMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, lookupProvider);
	}

	@Override
	protected void gather(HolderLookup.Provider provider) {
		final Builder<ItemStackTemplate, Item> replicaMap = builder(SeedlessDatamaps.SEED_REPLICAS);
		replicaMap.add(Items.WHEAT.builtInRegistryHolder(), new ItemStackTemplate(Items.WHEAT_SEEDS), false);
		replicaMap.add(Items.BEETROOT.builtInRegistryHolder(), new ItemStackTemplate(Items.BEETROOT_SEEDS), false);
		replicaMap.add(Items.MELON_SLICE.builtInRegistryHolder(), new ItemStackTemplate(Items.MELON_SEEDS), false);
	}
}
