package com.mrbysco.seedlessplace;

import com.mojang.logging.LogUtils;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(SeedlessPlaceMod.MOD_ID)
public class SeedlessPlaceMod {
	public static final String MOD_ID = "seedlessplace";
	public static final Logger LOGGER = LogUtils.getLogger();

	public SeedlessPlaceMod() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SeedlessConfig.commonSpec, "aseedlessplace.toml");
		eventBus.register(SeedlessConfig.class);

		MinecraftForge.EVENT_BUS.addListener(this::onRightClick);
	}

	private void onRightClick(PlayerInteractEvent.RightClickBlock event) {
		final ItemStack itemStack = event.getItemStack();
		final ItemStack replica = SeedlessConfig.getReplica(itemStack);
		if (replica.isEmpty()) {
			return;
		}
		UseOnContext context = new UseOnContext(event.getLevel(), event.getEntity(), event.getHand(), replica, event.getHitVec());
		InteractionResult result = replica.useOn(context);
		if (result.consumesAction()) {
			itemStack.shrink(1);
			event.setCanceled(true);
			event.setCancellationResult(result);
		}
	}
}
