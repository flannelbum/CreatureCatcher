package org.eirinncraft.CreatureCatcher.Creatures;

import org.bukkit.DyeColor;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Sheep;

import lombok.Getter;

public class CaughtSheep extends CaughtCreature {

	@Getter
	private DyeColor color;
	@Getter
	private boolean sheared;
	
	public CaughtSheep(Creature creature) {
		super(creature);
		this.color = ((Sheep) creature).getColor();
		this.sheared = ((Sheep) creature).isSheared();
	}

	@Override
	public void additionalSets(Entity entity) {
		((Sheep) entity).setColor(color);
		((Sheep) entity).setSheared(sheared);
	}

}
