package com.teammoeg.thermopolium.recipes;

import java.util.function.Function;

import com.google.gson.JsonElement;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public interface StewNumber extends Function<StewPendingContext,Float>{
	public boolean fits(ItemStack stack);
	public boolean fits(ResourceLocation type);
	public JsonElement serialize();
	public void write(PacketBuffer buffer);
	public String getType();
}