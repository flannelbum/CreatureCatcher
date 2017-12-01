package org.eirinncraft.CreatureCatcher.Creatures;

import java.io.IOException;

import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.ChestedHorse;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.eirinncraft.CreatureCatcher.Util.B64Util;
import org.bukkit.entity.Mule;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.entity.ZombieHorse;

import lombok.Getter;

public class CaughtHorse extends CaughtCreature {

	@Getter
	private double jumpStrength;
	@Getter
	private int domestication;
	@Getter
	private int maxDomestication;
	@Getter
	private double maxhealth;
	@Getter
	private String inventory;
	
	// Horse
	@Getter
	private Color color;
	@Getter
	private Style style;
	
	// ChestedHorse
	@Getter
	private boolean carryingChest;	

	
	public CaughtHorse(Creature creature) {
		super(creature);
		
		this.jumpStrength = ((AbstractHorse) creature).getJumpStrength();
		this.domestication = ((AbstractHorse) creature).getDomestication();
		this.maxDomestication = ((AbstractHorse) creature).getMaxDomestication();

		if( creature instanceof ChestedHorse ){
			this.carryingChest = ((ChestedHorse) creature).isCarryingChest();
		}
		
		if( creature instanceof Horse ){
			this.color = ((Horse) creature).getColor();
			this.style = ((Horse) creature).getStyle();
			this.inventory = B64Util.itemStackArrayToBase64( ((Horse) creature).getInventory().getContents() );
		}
				
		if( creature instanceof Donkey )
			this.inventory = B64Util.itemStackArrayToBase64( ((Donkey) creature).getInventory().getContents() );
		
		if( creature instanceof Mule )
			this.inventory = B64Util.itemStackArrayToBase64( ((Mule) creature).getInventory().getContents() );
		
		if( creature instanceof SkeletonHorse )
			this.inventory = B64Util.itemStackArrayToBase64( ((SkeletonHorse) creature).getInventory().getContents() );
		
		if( creature instanceof ZombieHorse )
			this.inventory = B64Util.itemStackArrayToBase64( ((ZombieHorse) creature).getInventory().getContents() );
		
	}

	@Override
	public void additionalSets(Entity entity) {

		((AbstractHorse) entity).setJumpStrength(jumpStrength);
		((AbstractHorse) entity).setDomestication(domestication);
		((AbstractHorse) entity).setMaxDomestication(maxDomestication);
		
		if( entity instanceof ChestedHorse ){
			((ChestedHorse) entity).setCarryingChest(carryingChest);
		}
		
		
		try {
			if( entity instanceof Horse ){
				((Horse) entity).setColor(color);
				((Horse) entity).setStyle(style);	
				((Horse) entity).getInventory().setContents( B64Util.itemStackArrayFromBase64( inventory ));
			}
			
			if( entity instanceof Donkey )
				((Donkey) entity).getInventory().setContents( B64Util.itemStackArrayFromBase64( inventory ));
			
			if( entity instanceof Mule ) 
				((Mule) entity).getInventory().setContents( B64Util.itemStackArrayFromBase64( inventory ));
			
			if( entity instanceof SkeletonHorse )
				((SkeletonHorse) entity).getInventory().setContents( B64Util.itemStackArrayFromBase64( inventory ));
			
			if( entity instanceof ZombieHorse )
				((ZombieHorse) entity).getInventory().setContents( B64Util.itemStackArrayFromBase64( inventory ));
			
			
		} catch (IllegalArgumentException | IOException e) {
				e.printStackTrace();
		}
	}
		
}


