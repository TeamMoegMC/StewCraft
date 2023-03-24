/*
 * Copyright (c) 2022 TeamMoeg
 *
 * This file is part of Caupona.
 *
 * Caupona is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * Caupona is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Specially, we allow this software to be used alongside with closed source software Minecraft(R) and Forge or other modloader.
 * Any mods or plugins can also use apis provided by forge or com.teammoeg.caupona.api without using GPL or open source.
 *
 * You should have received a copy of the GNU General Public License
 * along with Caupona. If not, see <https://www.gnu.org/licenses/>.
 */

package com.teammoeg.thermopolium;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;

public class THPStewTexture{
	public static Map<String,ResourceLocation> texture=new HashMap<>();
	static{
		texture.put("acquacotta",new ResourceLocation(Main.MODID,"block/soups/acquacotta"));
		texture.put("bisque",new ResourceLocation(Main.MODID,"block/soups/bisque"));
		texture.put("borscht",new ResourceLocation(Main.MODID,"block/soups/borscht"));
		texture.put("borscht_cream",new ResourceLocation(Main.MODID,"block/soups/borscht_cream"));
		texture.put("congee",new ResourceLocation(Main.MODID,"block/soups/congee"));
		texture.put("cream_of_meat_soup",new ResourceLocation(Main.MODID,"block/soups/cream_of_meat_soup"));
		texture.put("cream_of_mushroom_soup",new ResourceLocation(Main.MODID,"block/soups/cream_of_mushroom_soup"));
		texture.put("custard",new ResourceLocation(Main.MODID,"block/soups/custard"));
		texture.put("dilute_soup",new ResourceLocation(Main.MODID,"block/soups/dilute_soup"));
		texture.put("egg_drop_soup",new ResourceLocation(Main.MODID,"block/soups/egg_drop_soup"));
		texture.put("egg_tongsui",new ResourceLocation(Main.MODID,"block/soups/egg_tongsui"));
		texture.put("fish_chowder",new ResourceLocation(Main.MODID,"block/soups/fish_chowder"));
		texture.put("fish_soup",new ResourceLocation(Main.MODID,"block/soups/fish_soup"));
		texture.put("fricassee",new ResourceLocation(Main.MODID,"block/soups/fricassee"));
		texture.put("goji_tongsui",new ResourceLocation(Main.MODID,"block/soups/goji_tongsui"));
		texture.put("goulash",new ResourceLocation(Main.MODID,"block/soups/goulash"));
		texture.put("gruel",new ResourceLocation(Main.MODID,"block/soups/gruel"));
		texture.put("hodgepodge",new ResourceLocation(Main.MODID,"block/soups/hodgepodge"));
		texture.put("meat_soup",new ResourceLocation(Main.MODID,"block/soups/meat_soup"));
		texture.put("mushroom_soup",new ResourceLocation(Main.MODID,"block/soups/mushroom_soup"));
		texture.put("nail_soup",new ResourceLocation(Main.MODID,"block/soups/nail_soup"));
		texture.put("nettle_soup",new ResourceLocation(Main.MODID,"block/soups/nettle_soup"));
		texture.put("okroshka",new ResourceLocation(Main.MODID,"block/soups/okroshka"));
		texture.put("porridge",new ResourceLocation(Main.MODID,"block/soups/porridge"));
		texture.put("poultry_soup",new ResourceLocation(Main.MODID,"block/soups/poultry_soup"));
		texture.put("pumpkin_soup",new ResourceLocation(Main.MODID,"block/soups/pumpkin_soup"));
		texture.put("pumpkin_soup_cream",new ResourceLocation(Main.MODID,"block/soups/pumpkin_soup_cream"));
		texture.put("rice_pudding",new ResourceLocation(Main.MODID,"block/soups/rice_pudding"));
		texture.put("scalded_milk",new ResourceLocation(Main.MODID,"block/soups/scalded_milk"));
		texture.put("seaweed_soup",new ResourceLocation(Main.MODID,"block/soups/seaweed_soup"));
		texture.put("stock",new ResourceLocation(Main.MODID,"block/soups/stock"));
		texture.put("stracciatella",new ResourceLocation(Main.MODID,"block/soups/stracciatella"));
		texture.put("ukha",new ResourceLocation(Main.MODID,"block/soups/ukha"));
		texture.put("vegetable_chowder",new ResourceLocation(Main.MODID,"block/soups/vegetable_chowder"));
		texture.put("vegetable_soup",new ResourceLocation(Main.MODID,"block/soups/vegetable_soup"));
		texture.put("walnut_soup",new ResourceLocation(Main.MODID,"block/soups/walnut_soup"));
	}
}
