package org.eirinncraft.CreatureCatcher.Creatures;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Rabbit.Type;

import lombok.Getter;

public class CaughtRabbit extends CaughtCreature {

	@Getter
	private Type rabbittype;

	public CaughtRabbit(Creature creature) {
		super(creature);		
		this.rabbittype = ((Rabbit) creature).getRabbitType();
	}

	@Override
	public void additionalSets(Entity entity) {
		((Rabbit) entity).setRabbitType( rabbittype );
	}

}
