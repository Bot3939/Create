package com.simibubi.create.modules.contraptions.relays.elementary;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.utility.AllShapes;
import com.simibubi.create.modules.contraptions.base.RotatedPillarKineticBlock;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class ShaftBlock extends RotatedPillarKineticBlock {

	public ShaftBlock(Properties properties) {
		super(properties);
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ShaftTileEntity();
	}

	@Override
	protected boolean hasStaticPart() {
		return false;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return AllShapes.SIX_VOXEL_POLE.get(state.get(AXIS));
	}

	@Override
	public float getParticleTargetRadius() {
		return .25f;
	}

	@Override
	public float getParticleInitialRadius() {
		return 0f;
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		super.fillItemGroup(group, items);
		AllItems.BELT_CONNECTOR.get().fillItemGroup(group, items);
	}

	// IRotate:

	@Override
	public boolean hasShaftTowards(IWorldReader world, BlockPos pos, BlockState state, Direction face) {
		return face.getAxis() == state.get(AXIS);
	}

	@Override
	public Axis getRotationAxis(BlockState state) {
		return state.get(AXIS);
	}

}
