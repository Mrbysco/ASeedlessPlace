package com.mrbysco.seedlessplace.datagen;

import com.mrbysco.seedlessplace.datamap.SeedlessDatamaps;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class SeedlessMapProvider extends DataMapProvider {
	public SeedlessMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, lookupProvider);
	}

	@Override
	protected void gather(HolderLookup.Provider provider) {
		final Builder<ItemStack, Item> replicaMap = builder(SeedlessDatamaps.SEED_REPLICAS);
		replicaMap.add(Items.WHEAT.builtInRegistryHolder(), new ItemStack(Items.WHEAT_SEEDS), false);
		replicaMap.add(Items.BEETROOT.builtInRegistryHolder(), new ItemStack(Items.BEETROOT_SEEDS), false);
		replicaMap.add(Items.MELON_SLICE.builtInRegistryHolder(), new ItemStack(Items.MELON_SEEDS), false);
	}
}
