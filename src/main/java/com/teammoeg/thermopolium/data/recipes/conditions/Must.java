package com.teammoeg.thermopolium.data.recipes.conditions;

import com.google.gson.JsonObject;
import com.teammoeg.thermopolium.data.recipes.StewNumber;
import com.teammoeg.thermopolium.data.recipes.StewPendingContext;

import net.minecraft.network.PacketBuffer;

public class Must extends NumberedStewCondition {

	public Must(JsonObject obj) {
		super(obj);
	}

	public Must(StewNumber number) {
		super(number);
	}

	@Override
	public boolean test(StewPendingContext t, float n) {
		return n>0;
	}
	@Override
	public JsonObject serialize() {
		JsonObject jo=super.serialize();
		return jo;
	}

	public Must(PacketBuffer buffer) {
		super(buffer);
	}
	@Override
	public String getType() {
		return "contains";
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if(obj instanceof Must)
			return false;
		if (!super.equals(obj))
			return false;
		return true;
	}
}