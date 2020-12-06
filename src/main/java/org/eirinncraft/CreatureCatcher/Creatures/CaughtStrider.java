package org.eirinncraft.CreatureCatcher.Creatures;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Strider;

public class CaughtStrider extends CaughtCreature {

    private boolean isShivering;

    public CaughtStrider(Creature creature) {
        super(creature);
        this.isShivering = ((Strider) creature).isShivering();
    }

    @Override
    public void additionalSets(Entity entity) {
        ((Strider) entity).setShivering( this.isShivering );
    }
}
