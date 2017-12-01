package org.eirinncraft.CreatureCatcher.Creatures;

import java.io.IOException;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.InventoryHolder;
import org.eirinncraft.CreatureCatcher.Util.B64Util;

import lombok.Getter;

public class CaughtGeneric extends CaughtCreature {

	@Getter
	private String inventory;
	
	public CaughtGeneric(Creature creature) { 
		super(creature);
		
		if( creature instanceof InventoryHolder )
			this.inventory = B64Util.itemStackArrayToBase64( ((InventoryHolder) creature).getInventory().getContents() );
	}

	@Override
	public void additionalSets(Entity entity) {
		
		if( entity instanceof InventoryHolder )
			try {
				((InventoryHolder) entity).getInventory().setContents( B64Util.itemStackArrayFromBase64( inventory ));
			} catch (IllegalArgumentException | IOException e) {
				e.printStackTrace();
			}
	}

}
