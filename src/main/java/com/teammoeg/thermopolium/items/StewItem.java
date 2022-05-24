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

package com.teammoeg.thermopolium.items;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.teammoeg.thermopolium.Contents;
import com.teammoeg.thermopolium.Contents.THPItems;
import com.teammoeg.thermopolium.Main;
import com.teammoeg.thermopolium.data.recipes.BowlContainingRecipe;
import com.teammoeg.thermopolium.util.FloatemStack;
import com.teammoeg.thermopolium.util.SoupInfo;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class StewItem extends Item {
	ItemStack capturedStack;
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		return super.getContainerItem(itemStack);
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return super.getItemStackLimit(stack);
	}

	public ItemStack finishUsingItem(ItemStack itemstack, World worldIn, LivingEntity entityLiving) {

		SoupInfo si = getInfo(itemstack);
		if (!worldIn.isClientSide) {
			for (EffectInstance eff : si.effects) {
				if (eff != null) {
					entityLiving.addEffect(eff);
				}
			}
			Random r = entityLiving.getRandom();
			for (Pair<EffectInstance, Float> ef : si.foodeffect) {
				if (r.nextFloat() < ef.getSecond())
					entityLiving.addEffect(ef.getFirst());
			}
		}
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entityLiving;
			if (!worldIn.isClientSide)
				player.getFoodData().eat(si.healing, si.saturation);
			if (player.abilities.instabuild)
				return itemstack;

		}
		return new ItemStack(Items.BOWL);
	}

	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);

		playerIn.startUsingItem(handIn);
		return ActionResult.consume(itemstack);
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 16;
	}

	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		SoupInfo info=StewItem.getInfo(stack);
		FloatemStack fs=info.stacks.stream().max((t1,t2)->t1.getCount()>t2.getCount()?1:(t1.getCount()==t2.getCount()?0:-1)).orElse(null);
		if(fs!=null)
			tooltip.add(new TranslationTextComponent("tooltip.thermopolium.main_ingredient",fs.getStack().getDisplayName()));
		addPotionTooltip(info.effects, tooltip, 1);
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	public static SoupInfo getInfo(ItemStack stack) {
		if (stack.hasTag()) {
			CompoundNBT soupTag = stack.getTagElement("soup");
			return soupTag == null ? new SoupInfo(new ResourceLocation(stack.getTag().getString("type")))
					: new SoupInfo(soupTag);
		}
		return new SoupInfo();
	}

	public static void addPotionTooltip(List<EffectInstance> list, List<ITextComponent> lores, float durationFactor) {
		List<Pair<Attribute, AttributeModifier>> list1 = Lists.newArrayList();
		if (!list.isEmpty()) {
			for (EffectInstance effectinstance : list) {
				IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent(
						effectinstance.getDescriptionId());
				Effect effect = effectinstance.getEffect();
				Map<Attribute, AttributeModifier> map = effect.getAttributeModifiers();
				if (!map.isEmpty()) {
					for (Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
						AttributeModifier attributemodifier = entry.getValue();
						AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(),
								effect.getAttributeModifierValue(effectinstance.getAmplifier(), attributemodifier),
								attributemodifier.getOperation());
						list1.add(new Pair<>(entry.getKey(), attributemodifier1));
					}
				}

				if (effectinstance.getAmplifier() > 0) {
					iformattabletextcomponent = new TranslationTextComponent("potion.withAmplifier",
							iformattabletextcomponent,
							new TranslationTextComponent("potion.potency." + effectinstance.getAmplifier()));
				}

				if (effectinstance.getDuration() > 20) {
					iformattabletextcomponent = new TranslationTextComponent("potion.withDuration",
							iformattabletextcomponent,
							EffectUtils.formatDuration(effectinstance, durationFactor));
				}

				lores.add(iformattabletextcomponent.withStyle(effect.getCategory().getTooltipFormatting()));
			}
		}

		if (!list1.isEmpty()) {
			lores.add(StringTextComponent.EMPTY);
			lores.add((new TranslationTextComponent("potion.whenDrank")).withStyle(TextFormatting.DARK_PURPLE));

			for (Pair<Attribute, AttributeModifier> pair : list1) {
				AttributeModifier attributemodifier2 = pair.getSecond();
				double d0 = attributemodifier2.getAmount();
				double d1;
				if (attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE
						&& attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
					d1 = attributemodifier2.getAmount();
				} else {
					d1 = attributemodifier2.getAmount() * 100.0D;
				}

				if (d0 > 0.0D) {
					lores.add((new TranslationTextComponent(
							"attribute.modifier.plus." + attributemodifier2.getOperation().toValue(),
							ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1),
							new TranslationTextComponent(pair.getFirst().getDescriptionId())))
									.withStyle(TextFormatting.BLUE));
				} else if (d0 < 0.0D) {
					d1 = d1 * -1.0D;
					lores.add((new TranslationTextComponent(
							"attribute.modifier.take." + attributemodifier2.getOperation().toValue(),
							ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1),
							new TranslationTextComponent(pair.getFirst().getDescriptionId())))
									.withStyle(TextFormatting.RED));
				}
			}
		}

	}

	public static void setInfo(ItemStack stack, SoupInfo si) {
		if (!si.isEmpty())
			stack.getOrCreateTag().put("soup", si.save());
	}

	public static List<FloatemStack> getItems(ItemStack stack) {
		if (stack.hasTag()) {
			CompoundNBT nbt = stack.getTagElement("soup");
			if (nbt != null)
				return SoupInfo.getStacks(nbt);
		}
		return Lists.newArrayList();
	}
	public static ResourceLocation getBase(ItemStack stack) {
		if (stack.hasTag()) {
			CompoundNBT nbt = stack.getTagElement("soup");
			if (nbt != null)
				return new ResourceLocation(SoupInfo.getRegName(nbt));
		}
		return BowlContainingRecipe.extractFluid(stack).getFluid().getRegistryName();
	}
	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.allowdedIn(group)) {
			ItemStack is = new ItemStack(this);
			is.getOrCreateTag().putString("type", fluid.toString());
			items.add(is);
		}
	}

	public UseAction getUseAnimation(ItemStack stack) {
		return UseAction.DRINK;
	}

	ResourceLocation fluid;
	// fake food to trick mechanics
	public static final Food fakefood = new Food.Builder().nutrition(4).saturationMod(0.2f).fast()
			.meat().build();

	public StewItem(String name, ResourceLocation fluid, Properties properties) {
		super(properties.food(fakefood));
		setRegistryName(Main.MODID, name);
		Contents.registeredItems.add(this);
		THPItems.stews.add(this);
		this.fluid = fluid;
	}

	@Override
	public Food getFoodProperties() {
		return super.getFoodProperties();
	}
}
