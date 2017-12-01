package org.eirinncraft.CreatureCatcher.Creatures;

import java.io.IOException;

import org.bukkit.entity.ChestedHorse;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Llama.Color;
import org.eirinncraft.CreatureCatcher.Util.B64Util;

import lombok.Getter;

public class CaughtLlama extends CaughtCreature {

	@Getter
	private int strength;
	@Getter
	private Color color;
	@Getter
	private String inventory;
	@Getter
	private boolean carryingChest;
	
	public CaughtLlama(Creature creature) {
		super(creature);

		this.strength = ((Llama) creature).getStrength();
		this.color = ((Llama) creature).getColor();
		this.carryingChest = ((ChestedHorse) creature).isCarryingChest();
		
		this.inventory = B64Util.itemStackArrayToBase64( ((Llama) creature).getInventory().getContents() );
		
	}

	@Override
	public void additionalSets(Entity entity) {

		((Llama) entity).setStrength(strength);
		((Llama) entity).setColor(color);
		((Llama) entity).setCarryingChest(carryingChest);
		
		try {
			((Llama) entity).getInventory().setContents( B64Util.itemStackArrayFromBase64( inventory ));
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
	
	}

}
