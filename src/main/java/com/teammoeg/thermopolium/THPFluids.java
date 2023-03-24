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

package com.teammoeg.thermopolium;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;

import com.teammoeg.thermopolium.fluid.SoupFluid;
import com.teammoeg.thermopolium.fluid.SoupFluid.SoupAttributes;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class THPFluids {
	private static class TAC {
		ResourceLocation t;
		int c;

		public TAC(ResourceLocation t, int c) {
			super();
			this.t = t;
			this.c = c;
		}
	}

	private static final ResourceLocation STILL_WATER_TEXTURE = new ResourceLocation("block/water_still");
	private static final ResourceLocation STILL_SOUP_TEXTURE = new ResourceLocation(Main.MODID, "fluid/soup_fluid");
	private static final ResourceLocation STILL_MILK_TEXTURE = new ResourceLocation("forge", "block/milk_still");
	static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Main.MODID);
	private static final Map<String, TAC> soupfluids = new HashMap<>();

	public static TAC soup(int c) {
		return new TAC(STILL_SOUP_TEXTURE, c);
	}

	public static TAC water(int c) {
		return new TAC(STILL_WATER_TEXTURE, c);
	}

	public static TAC milk(int c) {
		return new TAC(STILL_MILK_TEXTURE, c);
	}
	public static Stream<Fluid> getAll(){
		return soupfluids.keySet().stream().map(e->new ResourceLocation(Main.MODID,e)).map(ForgeRegistries.FLUIDS::getValue);
	}
	public static void init() {
		soupfluids.put("acquacotta", soup(0xffdcb259));
		soupfluids.put("bisque", soup(0xffb87246));
		soupfluids.put("bone_gelatin", soup(0xffe3a14a));
		soupfluids.put("borscht", soup(0xff802629));
		soupfluids.put("borscht_cream", soup(0xffcf938e));
		soupfluids.put("congee", soup(0xffd6cbb3));
		soupfluids.put("cream_of_meat_soup", soup(0xffb98c60));
		soupfluids.put("cream_of_mushroom_soup", soup(0xffa7815f));
		soupfluids.put("custard", soup(0xffecda6e));
		soupfluids.put("dilute_soup", soup(0xffc2b598));
		soupfluids.put("egg_drop_soup", soup(0xffd9b773));
		soupfluids.put("egg_tongsui", soup(0xffc9b885));
		soupfluids.put("fish_chowder", soup(0xffd7c68e));
		soupfluids.put("fish_soup", soup(0xffa18441));
		soupfluids.put("fricassee", soup(0xffd2a85f));
		soupfluids.put("goji_tongsui", soup(0xffa97744));
		soupfluids.put("goulash", soup(0xff9e4a2a));
		soupfluids.put("gruel", soup(0xffd3ba9a));
		soupfluids.put("hodgepodge", soup(0xffb59d64));
		soupfluids.put("meat_soup", soup(0xff895e2d));
		soupfluids.put("mushroom_soup", soup(0xff97664c));
		soupfluids.put("nail_soup", water(0xFF3ABDFF));
		soupfluids.put("nettle_soup", soup(0xff467b32));
		soupfluids.put("okroshka", soup(0xffd0c776));
		// soupfluids.put("plain_milk",milk(0xffffffff));
		// soupfluids.put("plain_water",water(0xff374780));
		soupfluids.put("porridge", soup(0xffc6b177));
		soupfluids.put("poultry_soup", soup(0xffbc9857));
		soupfluids.put("pumpkin_soup", soup(0xffd88f31));
		soupfluids.put("pumpkin_soup_cream", soup(0xffe5c58b));
		soupfluids.put("rice_pudding", soup(0xffd8d2bc));
		soupfluids.put("scalded_milk", milk(0xfff3f0e3));
		soupfluids.put("seaweed_soup", soup(0xff576835));
		soupfluids.put("stock", soup(0xffc1a242));
		soupfluids.put("stracciatella", soup(0xffbfbe5c));
		soupfluids.put("ukha", soup(0xffb78533));
		soupfluids.put("vegetable_chowder", soup(0xffa39a42));
		soupfluids.put("vegetable_soup", soup(0xff848929));
		soupfluids.put("walnut_soup", soup(0xffdcb072));
		for (Entry<String, TAC> i : soupfluids.entrySet()) {
			FLUIDS.register(i.getKey(),
					() -> new SoupFluid(new ForgeFlowingFluid.Properties(null, null,
							SoupAttributes.builder(THPStewTexture.texture.getOrDefault(i.getKey(),i.getValue().t),THPStewTexture.texture.getOrDefault(i.getKey(),i.getValue().t)).viscosity(1200).color(THPStewTexture.texture.containsKey(i.getKey())?0xffffffff:i.getValue().c)
									.temperature(333).rarity(Rarity.UNCOMMON)).slopeFindDistance(1)
											.explosionResistance(100F)));
		}
	}

	public static Set<String> getSoupfluids() {
		return soupfluids.keySet();
	}
}
