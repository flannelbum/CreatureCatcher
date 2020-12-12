package org.eirinncraft.CreatureCatcher.Creatures;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;

public class CaughtZombieVillager extends CaughtCreature {

    private Villager.Profession profession;
    private Villager.Type villagerType;

    public CaughtZombieVillager(Creature creature){
        super(creature);
        this.profession = ((ZombieVillager) creature).getVillagerProfession();
        this.villagerType = ((ZombieVillager) creature).getVillagerType();
    }

    @Override
    public void additionalSets(Entity entity) {
        ((ZombieVillager) entity).setVillagerProfession( this.profession );
        ((ZombieVillager) entity).setVillagerType( this.villagerType );
    }
}