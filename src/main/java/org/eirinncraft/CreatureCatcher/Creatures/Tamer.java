package org.eirinncraft.CreatureCatcher.Creatures;

import java.util.UUID;

import org.bukkit.entity.AnimalTamer;

import lombok.Data;

@Data
public class Tamer implements AnimalTamer {

	private String name;
	private UUID uniqueId;
	
	public Tamer(String name, UUID uniqueId){
		this.name = name;
		this.uniqueId = uniqueId;
	}
	
}
