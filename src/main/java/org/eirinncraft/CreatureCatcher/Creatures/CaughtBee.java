package org.eirinncraft.CreatureCatcher.Creatures;

import org.bukkit.entity.Bee;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;

public class CaughtBee extends CaughtCreature {

    private boolean hasNectar;
    private boolean hasStung;
    private int anger;
    private int cannotEnterHiveTicks;

    public CaughtBee(Creature creature) {
        super(creature);
        this.hasNectar = ((Bee) creature).hasNectar();
        this.hasStung = ((Bee) creature).hasStung();
        this.anger = ((Bee) creature).getAnger();
        this.cannotEnterHiveTicks = ((Bee) creature).getCannotEnterHiveTicks();
    }

    @Override
    public void additionalSets(Entity entity) {
        ((Bee) entity).setHasNectar( this.hasNectar );
        ((Bee) entity).setHasStung( this.hasStung );
        ((Bee) entity).setAnger( this.anger );
        ((Bee) entity).setCannotEnterHiveTicks( this.cannotEnterHiveTicks );
    }
}
