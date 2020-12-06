package org.eirinncraft.CreatureCatcher.Creatures;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;

public class CaughtIronGolem extends CaughtCreature {

    boolean isPlayerCreated;

    public CaughtIronGolem(Creature creature) {
        super(creature);
        this.isPlayerCreated = ((IronGolem) creature).isPlayerCreated();
    }

    @Override
    public void additionalSets(Entity entity) {
        ((IronGolem) entity).setPlayerCreated( this.isPlayerCreated );
    }
}
