package org.eirinncraft.CreatureCatcher.Creatures;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Panda;

public class CaughtPanda extends CaughtCreature {

    private Panda.Gene mainGene;
    private Panda.Gene hiddenGene;

    public CaughtPanda(Creature creature) {
        super(creature);
        this.mainGene = ((Panda) creature).getMainGene();
        this.hiddenGene = ((Panda) creature).getHiddenGene();
    }

    @Override
    public void additionalSets(Entity entity) {
        ((Panda) entity).setMainGene( this.mainGene );
        ((Panda) entity).setHiddenGene( this.hiddenGene );
    }
}
