package org.eirinncraft.CreatureCatcher.Creatures;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Parrot.Variant;

import lombok.Getter;

public class CaughtParrot extends CaughtCreature {

	@Getter
	private Variant variant;
	
	public CaughtParrot(Creature creature) {
		super(creature);

		this.variant = ((Parrot) creature).getVariant();
	}

	@Override
	public void additionalSets(Entity entity) {
		((Parrot) entity).setVariant(variant);
	}

}
