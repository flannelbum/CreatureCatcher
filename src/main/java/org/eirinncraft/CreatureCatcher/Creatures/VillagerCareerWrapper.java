package org.eirinncraft.CreatureCatcher.Creatures;

import org.bukkit.Bukkit;
import org.bukkit.entity.Villager;

public final class VillagerCareerWrapper  {
	
	/**
	 *  Some helpful reading regarding Villager OBC/NMS:
	 *  https://www.spigotmc.org/threads/get-villager-career.216494/
	 *  
	 *  In short, MC 1.8 introduced Professions to villagers.  Bukkit/Spigot doesn't 
	 *  currently handle this with the API.  NMS reflection is used here.
	 *  
	 *  The constructor takes the villager we're checking, and the NMS Field names.
	 *  Obtaining the NMS field names can be done by inspecting the spigot/bukkit jar.
	 *  
	 *    net.minecraft.server.v??.EntityVillager.class
	 *   
	 *  Inspect the class - look for void a(NBTTagCompound) or b(NBTTagCompound) methods
	 *  as, I believe, they describe the serialization of the minecraft villager entity.  
	 *  There should be fields here related to "Career" and "CareerLevel".  
	 *  
	 *  Specify these fields as strings when evoking the VillagerCareer_GenericNMS object below.
	 *   
	 */
	
	public final static String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	
	public static VillagerCareerInterface Wrap(Villager villager) {
		try {
			
			switch(version) {
			
			case "v1_8_R1": return new VillagerCareer_GenericNMS(villager, "bv", "bw");
			case "v1_8_R2": return new VillagerCareer_GenericNMS(villager, "bx", "by");
			case "v1_8_R3": return new VillagerCareer_GenericNMS(villager, "bx", "by");
			case "v1_9_R1": return new VillagerCareer_GenericNMS(villager, "bH", "bI");
			case "v1_9_R2": return new VillagerCareer_GenericNMS(villager, "bI", "bJ");
			case "v1_10_R1": return new VillagerCareer_GenericNMS(villager, "bJ", "bK");
			case "v1_11_R1": return new VillagerCareer_GenericNMS(villager, "bJ", "bK");

			// the latest
			case "v1_12_R1": return new VillagerCareer_GenericNMS(villager, "bK", "bL");
			default: return new VillagerCareer_GenericNMS(villager, "bK", "bL");
			
			}
		} catch (VillagerCareerException e) {
			e.printStackTrace();
		}
		return null;
	}

}
