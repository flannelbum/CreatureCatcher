package org.eirinncraft.CreatureCatcher.Creatures;

import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fox;

public class CaughtFox extends CaughtCreature {

    private Fox.Type type;
    private boolean isCrouching;
    private boolean isSleeping;
    private AnimalTamer firstTrustedPlayer;
    private AnimalTamer secondTrustedPlayer;

    public CaughtFox(Creature creature) {
        super(creature);
        this.type = ((Fox) creature).getFoxType();
        this.isCrouching = ((Fox) creature).isCrouching();
        this.isSleeping = ((Fox) creature).isSleeping();
        this.firstTrustedPlayer = ((Fox) creature).getFirstTrustedPlayer();
        this.secondTrustedPlayer = ((Fox) creature).getSecondTrustedPlayer();

    }

    @Override
    public void additionalSets(Entity entity) {
        ((Fox) entity).setFoxType( this.type );
        ((Fox) entity).setCrouching( this.isCrouching );
        ((Fox) entity).setSleeping( this.isSleeping );
        ((Fox) entity).setFirstTrustedPlayer( this.firstTrustedPlayer );
        ((Fox) entity).setSecondTrustedPlayer( this.secondTrustedPlayer );
    }
}
