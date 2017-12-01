package org.eirinncraft.CreatureCatcher.Creatures;

import java.lang.reflect.Field;
import java.text.MessageFormat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

/**
 *  Some helpful reading regarding Villager OBC/NMS:
 *  https://www.spigotmc.org/threads/get-villager-career.216494/
 *  
 *  In short, MC 1.8 introduced Professions to villagers.  Bukkit/Spigot doesn't 
 *  currently handle this with the API.  NMS reflection is used here.
 *  
 *  The constructor takes the villager we're checking, and the NMS Field names
 *  Obtaining the NMS field names can be done by inspecting the spigot/bukkit jar.
 *  
 *    net.minecraft.server.v??.EntityVillager.class
 *   
 *  Inspect the class - look for void a(NBTTagCompound) or b(NBTTagCompound) methods
 *  as, I believe, they describe the serialization of the minecraft villager entity.  
 *  There should be fields here related to "Career" and "CareerLevel".  
 *  
 *  Specify these fields as strings when evoking this object.
 *  
 */


public class VillagerCareer_GenericNMS implements VillagerCareerInterface {

	
	private String reflectedVersion;
	private Villager villager;
	private String carrerIDNMSField;
	private String carreerLevelNMSField;

	
	public VillagerCareer_GenericNMS(Villager villager, String carrerIDNMSField, String carreerLevelNMSField) throws VillagerCareerException {
		this.reflectedVersion = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		this.villager = villager;
		this.carrerIDNMSField = carrerIDNMSField;
		this.carreerLevelNMSField = carreerLevelNMSField;
	}
	
	
	
	private Class<?> getClass(String className) {
		try {
			Class<?> c = Class.forName(MessageFormat.format(className, reflectedVersion));
			return c;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	
	@Override
	public int getCareerID() {
		int id = -1;
		try {
			Class<?> craftVillagerClass = getClass("org.bukkit.craftbukkit.{0}.entity.CraftVillager");
			Object craftVillager = craftVillagerClass.cast(this.villager);
			java.lang.reflect.Method method = craftVillagerClass.getMethod("getHandle");
			Object nmsVillager = method.invoke(craftVillager);
			Class<?> entityVillagerClass = getClass("net.minecraft.server.{0}.EntityVillager");

			Field careerField = entityVillagerClass.getDeclaredField(carrerIDNMSField);
			
			careerField.setAccessible(true);
			id = careerField.getInt(nmsVillager);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return id;
	}

	
	@Override
	public void setCareerID(int id) {
		try {
			villager.setProfession(Profession.LIBRARIAN);
			Class<?> craftVillagerClass = getClass("org.bukkit.craftbukkit.{0}.entity.CraftVillager");
			Object craftVillager = craftVillagerClass.cast(this.villager);
			java.lang.reflect.Method method = craftVillagerClass.getMethod("getHandle");
			Object nmsVillager = method.invoke(craftVillager);
			Class<?> entityVillagerClass = getClass("net.minecraft.server.{0}.EntityVillager");

			Field careerID = entityVillagerClass.getDeclaredField(carrerIDNMSField);

			careerID.setAccessible(true);
			careerID.setInt(nmsVillager, id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	@Override
	public int getCareerLevel() {
		int level = -1;
		try {
			Class<?> craftVillagerClass = getClass("org.bukkit.craftbukkit.{0}.entity.CraftVillager");
			Object craftVillager = craftVillagerClass.cast(this.villager);
			java.lang.reflect.Method method = craftVillagerClass.getMethod("getHandle");
			Object nmsVillager = method.invoke(craftVillager);
			Class<?> entityVillagerClass = getClass("net.minecraft.server.{0}.EntityVillager");

			Field careerLevel = entityVillagerClass.getDeclaredField(carreerLevelNMSField);

			careerLevel.setAccessible(true);
			level = careerLevel.getInt(nmsVillager); 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return level;
	}
	
	
	@Override
	public void setCareerLevel(int level) {
		try {
			Class<?> craftVillagerClass = getClass("org.bukkit.craftbukkit.{0}.entity.CraftVillager");
			Object craftVillager = craftVillagerClass.cast(this.villager);
			java.lang.reflect.Method method = craftVillagerClass.getMethod("getHandle");
			Object nmsVillager = method.invoke(craftVillager);
			Class<?> entityVillagerClass = getClass("net.minecraft.server.{0}.EntityVillager");

			Field careerLevel = entityVillagerClass.getDeclaredField(carreerLevelNMSField);

			careerLevel.setAccessible(true);
			careerLevel.setInt(nmsVillager, level); 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	@Override
	public String getAdjustedProfessionName() {
		String displayName = null;
		int careerID = this.getCareerID();
		switch( this.villager.getProfession() ) {
		case FARMER:
			switch( careerID ) {
			default: displayName = "Farmer"; break;
			case 2:	displayName = "Fisherman"; break;
			case 3:	displayName = "Shepherd"; break;
			case 4:	displayName = "Fletcher"; break;
			} break;
		case LIBRARIAN:
			switch( careerID ) {
			default: displayName = "Librarian"; break;
			case 2: displayName = "Cartographer"; break;
			} break;
		case PRIEST:
			displayName = "Cleric"; break;
		case BLACKSMITH:
			switch( careerID ) {
			default: displayName = "Armorer"; break;
			case 2: displayName = "Weapon Smith"; break;
			case 3: displayName = "Tool Smith"; break;
			} break;
		case BUTCHER:
			switch( careerID ) {
			default: displayName = "Butcher"; break;
			case 2: displayName = "Leatherworker"; break;
			} break;
		case NITWIT:
			displayName = "NitWit"; break;
		default: displayName = "Villager";
		}
		
		return displayName;
	}

	
}
