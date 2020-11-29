package org.eirinncraft.CreatureCatcher.Creatures;

import lombok.Getter;
import org.bukkit.DyeColor;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;

public class CaughtCat extends CaughtCreature {

    @Getter
    private final Cat.Type catType;
    @Getter
    private final DyeColor collarColor;

    public CaughtCat(Creature creature) {
        super(creature);
        this.catType = ((Cat) creature).getCatType();
        this.collarColor = ((Cat) creature).getCollarColor();

    }

    @Override
    public void additionalSets(Entity entity) {
        ((Cat) entity).setCatType(catType);
        ((Cat) entity).setCollarColor(collarColor);
    }


}
