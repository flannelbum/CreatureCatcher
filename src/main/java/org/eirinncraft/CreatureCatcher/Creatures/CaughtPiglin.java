package org.eirinncraft.CreatureCatcher.Creatures;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Piglin;

public class CaughtPiglin extends CaughtCreature {

    private boolean isAbleToHunt;

    public CaughtPiglin(Creature creature) {
        super(creature);
        this.isAbleToHunt = ((Piglin) creature).isAbleToHunt();
    }

    @Override
    public void additionalSets(Entity entity) {
        ((Piglin) entity).setIsAbleToHunt( this.isAbleToHunt );
    }
}
