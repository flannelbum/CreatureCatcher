package org.eirinncraft.CreatureCatcher.Creatures;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hoglin;

public class CaughtHoglin extends CaughtCreature {

    private boolean isImmuneToZombification;
    private boolean isAbleToBeHunted;
    private int conversionTime;

    public CaughtHoglin(Creature creature) {
        super(creature);
        this.isImmuneToZombification = ((Hoglin) creature).isImmuneToZombification();
        this.isAbleToBeHunted = ((Hoglin) creature).isAbleToBeHunted();
        this.conversionTime = ((Hoglin) creature).getConversionTime();
    }

    @Override
    public void additionalSets(Entity entity) {
        ((Hoglin) entity).setImmuneToZombification( this.isImmuneToZombification );
        ((Hoglin) entity).setIsAbleToBeHunted( this.isAbleToBeHunted );
        ((Hoglin) entity).setConversionTime( this.conversionTime );
    }
}
