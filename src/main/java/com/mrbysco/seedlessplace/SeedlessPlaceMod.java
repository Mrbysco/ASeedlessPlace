package com.mrbysco.seedlessplace;

import com.mojang.logging.LogUtils;
import com.mrbysco.seedlessplace.datamap.SeedlessDatamaps;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.slf4j.Logger;

@Mod(SeedlessPlaceMod.MOD_ID)
public class SeedlessPlaceMod {
	public static final String MOD_ID = "seedlessplace";
	public static final Logger LOGGER = LogUtils.getLogger();

	public SeedlessPlaceMod() {
		NeoForge.EVENT_BUS.addListener(this::onRightClick);
	}

	public static Identifier modLoc(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	private void onRightClick(PlayerInteractEvent.RightClickBlock event) {
		final ItemStack itemStack = event.getItemStack();
		if (itemStack.isEmpty()) return;
		final ItemStackTemplate replicaTemplate = itemStack.getItem().builtInRegistryHolder().getData(SeedlessDatamaps.SEED_REPLICAS);
		if (replicaTemplate == null) return;
		ItemStack replica = replicaTemplate.create();
		if (replica.isEmpty()) return;

		UseOnContext context = new UseOnContext(event.getLevel(), event.getEntity(), event.getHand(), replica, event.getHitVec());
		InteractionResult result = replica.useOn(context);
		if (result.consumesAction()) {
			itemStack.shrink(1);
			event.setCanceled(true);
			event.setCancellationResult(result);
		}
	}
}
