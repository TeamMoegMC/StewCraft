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

package com.teammoeg.thermopolium.data.recipes.numbers;

import java.util.stream.Stream;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teammoeg.thermopolium.data.TranslationProvider;
import com.teammoeg.thermopolium.data.recipes.StewNumber;
import com.teammoeg.thermopolium.data.recipes.StewPendingContext;
import com.teammoeg.thermopolium.util.FloatemTagStack;

import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemType implements StewNumber {
	Item type;
	ResourceLocation loc;

	public ItemType(JsonElement jo) {
		type = ForgeRegistries.ITEMS
				.getValue(loc = new ResourceLocation(jo.getAsJsonObject().get("item").getAsString()));
	}

	public ItemType(Item type) {
		super();
		this.type = type;
		this.loc = type.getRegistryName();
	}

	@Override
	public Float apply(StewPendingContext t) {
		if (type == null)
			return 0F;
		return t.getOfItem(i -> i.getItem().equals(type));
	}

	@Override
	public boolean fits(FloatemTagStack stack) {
		return stack.getItem().equals(type);
	}

	@Override
	public JsonElement serialize() {
		JsonObject th = new JsonObject();
		th.addProperty("item", loc.toString());
		return th;
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeResourceLocation(loc);
	}

	public ItemType(PacketBuffer buffer) {
		loc = buffer.readResourceLocation();
		type = ForgeRegistries.ITEMS.getValue(loc);
	}

	@Override
	public String getType() {
		return "item";
	}

	@Override
	public Stream<StewNumber> getItemRelated() {
		return Stream.of(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((loc == null) ? 0 : loc.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ItemType))
			return false;
		ItemType other = (ItemType) obj;
		if (loc == null) {
			if (other.loc != null)
				return false;
		} else if (!loc.equals(other.loc))
			return false;
		return true;
	}

	@Override
	public Stream<ResourceLocation> getTags() {
		return Stream.empty();
	}

	@Override
	public String getTranslation(TranslationProvider p) {
		return p.getTranslation(type.getDescriptionId());
	}
}
