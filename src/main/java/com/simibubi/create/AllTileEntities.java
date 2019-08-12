package com.simibubi.create;

import java.util.function.Supplier;

import com.simibubi.create.modules.kinetics.base.KineticTileEntityRenderer;
import com.simibubi.create.modules.kinetics.generators.MotorTileEntity;
import com.simibubi.create.modules.kinetics.generators.MotorTileEntityRenderer;
import com.simibubi.create.modules.kinetics.receivers.TurntableTileEntity;
import com.simibubi.create.modules.kinetics.relays.AxisTileEntity;
import com.simibubi.create.modules.kinetics.relays.AxisTunnelTileEntity;
import com.simibubi.create.modules.kinetics.relays.AxisTunnelTileEntityRenderer;
import com.simibubi.create.modules.kinetics.relays.BeltTileEntity;
import com.simibubi.create.modules.kinetics.relays.BeltTileEntityRenderer;
import com.simibubi.create.modules.kinetics.relays.GearboxTileEntity;
import com.simibubi.create.modules.kinetics.relays.GearboxTileEntityRenderer;
import com.simibubi.create.modules.kinetics.relays.GearshifterTileEntity;
import com.simibubi.create.modules.kinetics.relays.GearshifterTileEntityRenderer;
import com.simibubi.create.modules.schematics.block.SchematicTableTileEntity;
import com.simibubi.create.modules.schematics.block.SchematicannonRenderer;
import com.simibubi.create.modules.schematics.block.SchematicannonTileEntity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public enum AllTileEntities {

	// Schematics
	SCHEMATICANNON(SchematicannonTileEntity::new, AllBlocks.SCHEMATICANNON),
	SCHEMATICTABLE(SchematicTableTileEntity::new, AllBlocks.SCHEMATIC_TABLE),

	// Kinetics
	AXIS(AxisTileEntity::new, AllBlocks.AXIS, AllBlocks.GEAR, AllBlocks.LARGE_GEAR, AllBlocks.AXIS_TUNNEL),
	MOTOR(MotorTileEntity::new, AllBlocks.MOTOR),
	GEARBOX(GearboxTileEntity::new, AllBlocks.GEARBOX),
	TURNTABLE(TurntableTileEntity::new, AllBlocks.TURNTABLE),
	AXIS_TUNNEL(AxisTunnelTileEntity::new, AllBlocks.AXIS_TUNNEL),
	GEARSHIFTER(GearshifterTileEntity::new, AllBlocks.GEARSHIFTER),
	BELT(BeltTileEntity::new, AllBlocks.BELT),
	
	;
	
	private Supplier<? extends TileEntity> supplier;
	public TileEntityType<?> type;
	private AllBlocks[] blocks;

	private AllTileEntities(Supplier<? extends TileEntity> supplier, AllBlocks... blocks) {
		this.supplier = supplier;
		this.blocks = blocks;
	}

	@SubscribeEvent
	public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {

		for (AllTileEntities tileEntity : values()) {
			Block[] blocks = new Block[tileEntity.blocks.length];
			for (int i = 0; i < blocks.length; i++)
				blocks[i] = tileEntity.blocks[i].block;
			
			ResourceLocation resourceLocation = new ResourceLocation(Create.ID, tileEntity.name().toLowerCase());
			tileEntity.type = TileEntityType.Builder.create(tileEntity.supplier, blocks).build(null)
					.setRegistryName(resourceLocation);
			event.getRegistry().register(tileEntity.type);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerRenderers() {
		bind(SchematicannonTileEntity.class, new SchematicannonRenderer());
		bind(AxisTileEntity.class, new KineticTileEntityRenderer());
		bind(TurntableTileEntity.class, new KineticTileEntityRenderer());
		bind(MotorTileEntity.class, new MotorTileEntityRenderer());
		bind(AxisTunnelTileEntity.class, new AxisTunnelTileEntityRenderer());
		bind(GearboxTileEntity.class, new GearboxTileEntityRenderer());
		bind(GearshifterTileEntity.class, new GearshifterTileEntityRenderer());
		bind(BeltTileEntity.class, new BeltTileEntityRenderer());
	}

	@OnlyIn(Dist.CLIENT)
	private static <T extends TileEntity> void bind(Class<T> clazz, TileEntityRenderer<? super T> renderer) {
		ClientRegistry.bindTileEntitySpecialRenderer(clazz, renderer);
	}

}