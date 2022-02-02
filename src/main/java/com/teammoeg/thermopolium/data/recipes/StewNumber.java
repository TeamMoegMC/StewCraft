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

package com.teammoeg.thermopolium.data.recipes;

import java.util.function.Function;
import java.util.stream.Stream;

import com.google.gson.JsonElement;
import com.teammoeg.thermopolium.util.FloatemTagStack;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public interface StewNumber extends Function<StewPendingContext, Float>,Writeable {
	public boolean fits(FloatemTagStack stack);

	public String getType();

	public Stream<StewNumber> getItemRelated();

	public Stream<ResourceLocation> getTags();
}
