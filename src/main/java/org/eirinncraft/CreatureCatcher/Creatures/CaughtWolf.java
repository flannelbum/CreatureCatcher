package org.eirinncraft.CreatureCatcher.Creatures;

import org.bukkit.DyeColor;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Wolf;

import lombok.Getter;

public class CaughtWolf extends CaughtCreature {

	@Getter
	private DyeColor colorColor;
	@Getter
	private boolean angry;
	
	
	public CaughtWolf(Creature creature) {
		super(creature);
		
		this.colorColor = ((Wolf) creature).getCollarColor();
		this.angry = ((Wolf) creature).isAngry();
		
	}

	@Override
	public void additionalSets(Entity entity) {
		
		((Wolf) entity).setCollarColor(colorColor);
		((Wolf) entity).setAngry(angry);

	}

}
