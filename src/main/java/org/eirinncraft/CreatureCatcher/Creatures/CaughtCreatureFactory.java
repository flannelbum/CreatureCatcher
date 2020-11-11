package org.eirinncraft.CreatureCatcher.Creatures;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class CaughtCreatureFactory {
	
	public static final CaughtCreature catchEntity(Entity entity){
		
		Creature creature = (Creature) entity;
				
		switch(creature.getType()){
		
		case HORSE: return new CaughtHorse(creature);
		case MULE: return new CaughtHorse(creature);
		case DONKEY: return new CaughtHorse(creature);
		case ZOMBIE_HORSE: return new CaughtHorse(creature);
		case SKELETON_HORSE: return new CaughtHorse(creature);
		
		case PIG: return new CaughtPig(creature);
		case SHEEP: return new CaughtSheep(creature);
		
		case WOLF: return new CaughtWolf(creature);
		case LLAMA: return new CaughtLlama(creature);
		case PARROT: return new CaughtParrot(creature);
		case VILLAGER: return new CaughtVillager(creature);
		case RABBIT: return new CaughtRabbit(creature);
		
		default: return new CaughtGeneric(creature);
		}

		
	}
	

	public static final Class<?> getClass(String type){
		return getClass(EntityType.valueOf(type));
	}
	
	public static final Class<?> getClass(EntityType type){
						
		switch(type){
		
		case HORSE: return CaughtHorse.class;
		case MULE: return CaughtHorse.class;
		case DONKEY: return CaughtHorse.class;
		case ZOMBIE_HORSE: return CaughtHorse.class;
		case SKELETON_HORSE: return CaughtHorse.class;
		
		case PIG: return CaughtPig.class;
		case SHEEP: return CaughtSheep.class;
		
		case WOLF: return CaughtWolf.class;
		case LLAMA: return CaughtLlama.class;
		case PARROT: return CaughtParrot.class;
		case VILLAGER: return CaughtVillager.class;
		case RABBIT: return CaughtRabbit.class;
		
		default: return CaughtGeneric.class;
		}
		
	}
	
	
	public static boolean validEntityType(Entity entity){
		switch(entity.getType()){
			
			// Ones that need special handling
			case HORSE: return true;
			case MULE: return true;
			case DONKEY: return true;
			case ZOMBIE_HORSE: return true;
			case SKELETON_HORSE: return true;
			
			case LLAMA: return true;
			case PARROT: return true;
			case PIG: return true;
			case SHEEP: return true;
			case VILLAGER: return true;
			case WOLF: return true;
			case RABBIT: return true;
			
			// Ones that are generic
			case BAT: return true;
			case BLAZE: return true;
			case CAVE_SPIDER: return true;
			case CHICKEN: return true;
			case COW: return true;
			case CREEPER: return true;
			case ELDER_GUARDIAN: return true;
			case ENDERMAN: return true;
			case ENDERMITE: return true;
			case GHAST: return true;
			case MUSHROOM_COW: return true;
			case OCELOT: return true;
//			case PIG_ZOMBIE: return true;
			case POLAR_BEAR: return true;
			case SHULKER: return true;
			case SILVERFISH: return true;
			case SKELETON: return true;
			case SNOWMAN: return true;
			case SPIDER: return true;
			case SQUID: return true;
			case WITCH: return true;
			case WITHER_SKELETON: return true;
			case ZOMBIE: return true;
			default: return false;
		}
	}
	
}
