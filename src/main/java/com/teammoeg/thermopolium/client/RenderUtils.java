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

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

/**
 * Fluid render codes adapted from Immersive Engineering and modified.
 * Related codes fall under their license and open-sourced.
 * 
 * @author BluSunrize
 * @author khjxiaogu
 */
public class RenderUtils {

	private RenderUtils() {
	}

	@SuppressWarnings("deprecation")
	public static void handleGuiTank(MatrixStack transform, IFluidTank tank, int x, int y, int w, int h) {
		FluidStack fluid = tank.getFluid();
		transform.pushPose();

		IRenderTypeBuffer.Impl buffer = IRenderTypeBuffer.immediate(Tessellator.getInstance().getBuilder());
		if (fluid != null && fluid.getFluid() != null) {
			int fluidHeight = (int) (h * (fluid.getAmount() / (float) tank.getCapacity()));
			drawRepeatedFluidSpriteGui(buffer, transform, fluid, x, y + h - fluidHeight, w, fluidHeight);
			RenderSystem.color3f(1, 1, 1);
		}
		buffer.endBatch();
		transform.popPose();
	}

	private static RenderType getGui(ResourceLocation texture) {
		return RenderType.create("gui_" + texture, DefaultVertexFormats.POSITION_COLOR_TEX, GL11.GL_QUADS, 256,
				RenderType.State.builder().setTextureState(new RenderState.TextureState(texture, false, false))
						.setAlphaState(new RenderState.AlphaState(0.5F)).createCompositeState(false));
	}

	private static void buildVertex(IVertexBuilder bu, MatrixStack transform, float r, float g, float b, float a,
			float p1, float p2, float u0, float u1, int light, int overlay) {
		bu.vertex(transform.last().pose(), p1, p2, 0).color(r, g, b, a).uv(u0, u1).overlayCoords(overlay)
				.uv2(light).normal(transform.last().normal(), 1, 1, 1).endVertex();
	}

	public static void drawRepeatedFluidSpriteGui(IRenderTypeBuffer.Impl buffer, MatrixStack transform,
			FluidStack fluid, float x, float y, float w, float h) {
		RenderType renderType = getGui(PlayerContainer.BLOCK_ATLAS);
		IVertexBuilder builder = buffer.getBuffer(renderType);
		TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager()
				.getAtlas(PlayerContainer.BLOCK_ATLAS)
				.getSprite(fluid.getFluid().getAttributes().getStillTexture(fluid));
		int col = fluid.getFluid().getAttributes().getColor(fluid);
		int iW = sprite.getWidth();
		int iH = sprite.getHeight();
		if (iW > 0 && iH > 0)
			drawRepeatedSprite(builder, transform, x, y, w, h, iW, iH, sprite.getU0(), sprite.getU1(),
					sprite.getV0(), sprite.getV1(), (col >> 16 & 255) / 255.0f, (col >> 8 & 255) / 255.0f,
					(col & 255) / 255.0f, 0.8f, LightTexture.pack(15, 15), OverlayTexture.NO_OVERLAY);
		buffer.endBatch(renderType);
	}

	public static void drawRepeatedSprite(IVertexBuilder builder, MatrixStack transform, float x, float y, float w,
			float h, int iconWidth, int iconHeight, float uMin, float uMax, float vMin, float vMax, float r, float g,
			float b, float alpha, int light, int overlay) {
		int iterMaxW = (int) (w / iconWidth);
		int iterMaxH = (int) (h / iconHeight);
		float leftoverW = w % iconWidth;
		float leftoverH = h % iconHeight;
		float leftoverWf = leftoverW / iconWidth;
		float leftoverHf = leftoverH / iconHeight;
		float iconUDif = uMax - uMin;
		float iconVDif = vMax - vMin;
		for (int ww = 0; ww < iterMaxW; ww++) {
			for (int hh = 0; hh < iterMaxH; hh++)
				drawTexturedColoredRect(builder, transform, x + ww * iconWidth, y + hh * iconHeight, iconWidth,
						iconHeight, r, g, b, alpha, uMin, uMax, vMin, vMax, light, overlay);
			drawTexturedColoredRect(builder, transform, x + ww * iconWidth, y + iterMaxH * iconHeight, iconWidth,
					leftoverH, r, g, b, alpha, uMin, uMax, vMin, (vMin + iconVDif * leftoverHf), light, overlay);
		}
		if (leftoverW > 0) {
			for (int hh = 0; hh < iterMaxH; hh++)
				drawTexturedColoredRect(builder, transform, x + iterMaxW * iconWidth, y + hh * iconHeight, leftoverW,
						iconHeight, r, g, b, alpha, uMin, (uMin + iconUDif * leftoverWf), vMin, vMax, light, overlay);
			drawTexturedColoredRect(builder, transform, x + iterMaxW * iconWidth, y + iterMaxH * iconHeight, leftoverW,
					leftoverH, r, g, b, alpha, uMin, (uMin + iconUDif * leftoverWf), vMin,
					(vMin + iconVDif * leftoverHf), light, overlay);
		}
	}

	public static void drawTexturedColoredRect(IVertexBuilder builder, MatrixStack transform, float x, float y, float w,
			float h, float r, float g, float b, float alpha, float u0, float u1, float v0, float v1, int light,
			int overlay) {
		buildVertex(builder, transform, r, g, b, alpha, x, y + h, u0, v1, light, overlay);
		buildVertex(builder, transform, r, g, b, alpha, x + w, y + h, u1, v1, light, overlay);
		buildVertex(builder, transform, r, g, b, alpha, x + w, y, u1, v0, light, overlay);
		buildVertex(builder, transform, r, g, b, alpha, x, y, u0, v0, light, overlay);
	}
}
