package com.simibubi.create.modules.contraptions.relays.gauge;

import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.modules.contraptions.base.KineticTileEntity;

import com.simibubi.create.modules.contraptions.goggle.IHaveGoggleInformation;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import java.util.List;

public class GaugeTileEntity extends KineticTileEntity implements IHaveGoggleInformation {

	public float dialTarget;
	public float dialState;
	public float prevDialState;
	public int color;

	public GaugeTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.putFloat("Value", dialTarget);
		compound.putInt("Color", color);
		return super.write(compound);
	}

	@Override
	public void read(CompoundNBT compound) {
		dialTarget = compound.getFloat("Value");
		color = compound.getInt("Color");
		super.read(compound);
	}

	@Override
	public void tick() {
		super.tick();
		prevDialState = dialState;
		dialState += (dialTarget - dialState) * .125f;
		if (dialState > 1 && world.rand.nextFloat() < 1 / 2f)
			dialState -= (dialState - 1) * world.rand.nextFloat();
	}

	@Override
	public boolean addToGoggleTooltip(List<String> tooltip, boolean isPlayerSneaking) {
		tooltip.add(spacing + Lang.translate("gui.gauge.info_header"));

		return true;
	}

}
