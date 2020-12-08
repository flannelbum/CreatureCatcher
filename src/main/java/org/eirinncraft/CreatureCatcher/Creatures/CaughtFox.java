package org.eirinncraft.CreatureCatcher.Creatures;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fox;

import java.util.UUID;

public class CaughtFox extends CaughtCreature {

    private Fox.Type foxType;
    private boolean isCrouching;
    private boolean isSleeping;

    private UUID firstTrustedUUID;
    private String firstTrustedName;
    private UUID secondTrustedUUID;
    private String secondTrustedName;

    public CaughtFox(Creature creature) {
        super(creature);
        Fox fox = ((Fox) creature);
        this.foxType = fox.getFoxType();
        this.isCrouching = fox.isCrouching();
        this.isSleeping = fox.isSleeping();
        if( ((Fox) creature).getFirstTrustedPlayer() != null ) {
            this.firstTrustedUUID = ((Fox) creature).getFirstTrustedPlayer().getUniqueId();
            this.firstTrustedName = ((Fox) creature).getFirstTrustedPlayer().getName();
        }
        if( ((Fox) creature).getSecondTrustedPlayer() != null ) {
            this.secondTrustedUUID = ((Fox) creature).getSecondTrustedPlayer().getUniqueId();
            this.secondTrustedName = ((Fox) creature).getFirstTrustedPlayer().getName();
        }
    }

    @Override
    public void additionalSets(Entity entity) {
        ((Fox) entity).setFoxType( this.foxType );
        ((Fox) entity).setCrouching( this.isCrouching );
        ((Fox) entity).setSleeping( this.isSleeping );
        if( this.firstTrustedName != null )
            ((Fox) entity).setFirstTrustedPlayer( new Tamer( this.firstTrustedName, this.firstTrustedUUID ));
        if( this.secondTrustedName != null )
            ((Fox) entity).setSecondTrustedPlayer( new Tamer( this.secondTrustedName, this.secondTrustedUUID ));
    }
}
