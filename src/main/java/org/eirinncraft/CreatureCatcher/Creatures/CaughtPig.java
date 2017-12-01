package org.eirinncraft.CreatureCatcher.Creatures;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;

import lombok.Getter;

public class CaughtPig extends CaughtCreature {

	@Getter
	private boolean saddled;
	
	public CaughtPig(Creature creature) {
		super(creature);
		
		this.saddled = ((Pig) creature).hasSaddle();
	}
		
	@Override
	public void additionalSets(Entity entity) {
		((Pig) entity).setSaddle(saddled);	
	}
	
}
