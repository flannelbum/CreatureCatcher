package org.eirinncraft.CreatureCatcher.Creatures;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
//import org.bukkit.loot.LootTable;
//import org.bukkit.loot.Lootable;


public abstract class CaughtCreature {
	
	@Getter
	private String token;
	
	//core attributes
	@Getter
	private HashMap<Attribute,Double> attributeMap = new HashMap<Attribute,Double>();

//	//Lootable
//	@Getter
//	private LootTable loottable;
//	@Getter
//	private long lootseed;
	
	//Ageable
	@Getter 
	private int age;
	@Getter
	private boolean adult;

	//Breedable
	@Getter
	private boolean agelock;
	@Getter
	private boolean canbreed;
	
	//Tameable
	@Getter
	private boolean tamed;
	@Getter
	private UUID ownerUUID;
	@Getter
	private String ownerName;
	
	@Getter
	private EntityType type;
	@Getter
	private String name;
	@Getter
	private String customName;
	@Getter
	private boolean customNameVisible;
	@Getter @Setter
	private String displayName;
	@Getter @Accessors(fluent = true)
	private boolean canPickupItems;
	@Getter
	private double health;
	@Getter @Accessors(fluent = true)
	private boolean hasAI;
	@Getter @Accessors(fluent = true)
	private boolean removeWhenFarAway;
	
	
	public CaughtCreature(Creature creature){
		this.token = Long.toHexString(Double.doubleToLongBits(Math.random()));

		//core attributes
		for( Attribute attribute : Attribute.values() )
			if( creature.getAttribute( attribute ) != null ){
				attributeMap.put( attribute, creature.getAttribute( attribute ).getBaseValue() );
			}

//		//Lootable
//		if( creature instanceof Lootable){
//			this.loottable = ((Lootable) creature).getLootTable();
//			this.lootseed = ((Lootable) creature).getSeed();
//		}
		
		//Ageable
		if( creature instanceof Ageable ){
			this.age = ((Ageable) creature).getAge();
			this.adult = ((Ageable) creature).isAdult();
		}

		//Breedable
		if( creature instanceof Breedable ){
			this.agelock = ((Breedable) creature).getAgeLock();
			this.canbreed = ((Breedable) creature).canBreed();
		}
		
		//Tameable
		if( creature instanceof Tameable ){
			this.tamed = ((Tameable) creature).isTamed();
			if( this.tamed && ((Tameable) creature).getOwner() != null ){
				this.ownerUUID = ((Tameable) creature).getOwner().getUniqueId();
				this.ownerName = ((Tameable) creature).getOwner().getName();
			}
		}
		
		
		this.type = creature.getType();
		this.name = creature.getName();
		this.customName = creature.getCustomName();
		this.customNameVisible = creature.isCustomNameVisible();
		
		if( customNameVisible )
			this.displayName = customName;
		else
			this.displayName = name;
				
		this.canPickupItems = creature.getCanPickupItems();
		this.health = creature.getHealth();
		this.hasAI = creature.hasAI();
		this.removeWhenFarAway = creature.getRemoveWhenFarAway();
		
		
	}
		
	
	public Entity spawn(Location location){

		
		// tweak initial spawn location
		location.setX( location.getX() +.5);
		location.setY( location.getY() - 2);
		location.setZ( location.getZ() +.5);
		
		Entity entity = location.getWorld().spawnEntity(location, type);

//		//Lootable
//		if( entity instanceof Lootable ){
//			((Lootable) entity).setLootTable(loottable);
//			((Lootable) entity).setSeed(lootseed);
//		}

		//Ageable
		if( entity instanceof Ageable ){
			((Ageable) entity).setAge(age);
			if( adult )
				((Ageable) entity).setAdult();
			else
				((Ageable) entity).setBaby();
		}

		//Breedable
		if( entity instanceof Breedable ){
			((Breedable) entity).setBreed(canbreed);
			((Breedable) entity).setAgeLock(agelock);
		}
		
		//Tameable
		if( entity instanceof Tameable ){
			
			((Tameable) entity).setTamed(tamed);
			((Tameable) entity).setOwner( new Tamer(ownerName, ownerUUID) );
		}
		
		//Sittable - make them all sit if they can
		if( entity instanceof Sittable ){
			((Sittable) entity).setSitting(true);
		}
		
		additionalSets(entity);
		
		Creature creature = (Creature) entity;
		
		//core attributes
		for( Entry<Attribute,Double> entry : attributeMap.entrySet() )
			creature.getAttribute( entry.getKey() ).setBaseValue( entry.getValue() );
		
		creature.setCustomName(customName);
		creature.setCustomNameVisible(customNameVisible);
		creature.setCanPickupItems(canPickupItems);
		creature.setHealth(health);
		creature.setAI(hasAI);
		creature.setRemoveWhenFarAway(removeWhenFarAway);
		
		// tweak location again to raise entity
		location.setY( location.getY() + 3 );
		entity.teleport(location);
		
		return entity;
	}
	
	public abstract void additionalSets(Entity entity);

}

