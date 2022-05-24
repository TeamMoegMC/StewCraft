/*
 * Copyright (c) 2022 TeamMoeg
 *
 * This file is part of Thermopolium.
 *
 * Thermopolium is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * Thermopolium is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Thermopolium. If not, see <https://www.gnu.org/licenses/>.
 */

package com.teammoeg.thermopolium.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.teammoeg.thermopolium.Contents;
import com.teammoeg.thermopolium.blocks.StewPotTileEntity;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fluids.FluidStack;

public class StewPotRenderer extends TileEntityRenderer<StewPotTileEntity> {

	public StewPotRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
		System.out.println("constructed");
	}

	private static Vector3f clr(int fromcol, int tocol, float proc) {
		float fcolr = (fromcol >> 16 & 255) / 255.0f, fcolg = (fromcol >> 8 & 255) / 255.0f,
				fcolb = (fromcol & 255) / 255.0f, tcolr = (tocol >> 16 & 255) / 255.0f,
				tcolg = (tocol >> 8 & 255) / 255.0f, tcolb = (tocol & 255) / 255.0f;
		return new Vector3f(fcolr + (tcolr - fcolr) * proc, fcolg + (tcolg - fcolg) * proc,
				fcolb + (tcolb - fcolb) * proc);
	}

	private static Vector3f clr(int col) {
		return new Vector3f((col >> 16 & 255) / 255.0f, (col >> 8 & 255) / 255.0f, (col & 255) / 255.0f);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(StewPotTileEntity te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer,
			int combinedLightIn, int combinedOverlayIn) {
		if (!te.getLevel().hasChunkAt(te.getBlockPos()))
			return;
		BlockState state = te.getBlockState();
		if (state.getBlock() != Contents.THPBlocks.stew_pot)
			return;
		matrixStack.pushPose();
		FluidStack fs = te.getTank().getFluid();
		if (fs != null && !fs.isEmpty() && fs.getFluid() != null) {
			float rr=fs.getAmount();
			if(te.proctype==2)//just animate fluid reduction
				rr+=250f*(1-te.process*1f/te.processMax);
			float yy = Math.min(1,rr / te.getTank().getCapacity()) * .5f + .3125f;
			matrixStack.translate(0, yy, 0);
			matrixStack.mulPose(new Quaternion(90, 0, 0, true));
			matrixStack.pushPose();
			IVertexBuilder builder = buffer.getBuffer(RenderType.translucent());
			TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager()
					.getAtlas(PlayerContainer.BLOCK_ATLAS)
					.getSprite(fs.getFluid().getAttributes().getStillTexture(fs));
			int col = fs.getFluid().getAttributes().getColor(fs);
			int iW = sprite.getWidth();
			int iH = sprite.getHeight();
			if (iW > 0 && iH > 0) {
				Vector3f clr;
				float alp = 1f;
				if (te.become != null && te.processMax > 0) {
					TextureAtlasSprite sprite2 = Minecraft.getInstance().getModelManager()
							.getAtlas(PlayerContainer.BLOCK_ATLAS)
							.getSprite(te.become.getFluid().getAttributes().getStillTexture(fs));
					float proc = te.process * 1f / te.processMax;
					clr = clr(col, te.become.getAttributes().getColor(fs), proc);
					if (sprite2.getWidth() > 0 && sprite2.getHeight() > 0) {
						alp = 1 - proc;
						RenderUtils.drawTexturedColoredRect(builder, matrixStack, .125f, .125f, .75f, .75f, clr.x(),
								clr.y(), clr.z(), proc, sprite2.getU0(), sprite2.getU1(), sprite2.getV0(),
								sprite2.getV1(), combinedLightIn, combinedOverlayIn);
					}
				} else {
					clr = clr(col);

				}
				RenderUtils.drawTexturedColoredRect(builder, matrixStack, .125f, .125f, .75f, .75f, clr.x(),
						clr.y(), clr.z(), alp, sprite.getU0(), sprite.getU1(), sprite.getV0(),
						sprite.getV1(), combinedLightIn, combinedOverlayIn);

			}
			matrixStack.popPose();
		}
		
		matrixStack.popPose();
	}

}