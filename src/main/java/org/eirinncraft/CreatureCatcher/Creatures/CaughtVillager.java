package org.eirinncraft.CreatureCatcher.Creatures;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.eirinncraft.CreatureCatcher.Creatures.StoreMerchantRecipe.StoreMerchantRecipeBuilder;
import org.eirinncraft.CreatureCatcher.Util.B64Util;

import lombok.Getter;

public class CaughtVillager extends CaughtCreature {

	@Getter 
	private Villager.Profession profession;
	@Getter
	private Villager.Type villagerType;
	@Getter
	private int villagerLevel;
	@Getter
	private int villagerExperience;
	@Getter
	private String inventory;
	@Getter
	private List<StoreMerchantRecipe> storedRecipes = new ArrayList<StoreMerchantRecipe>();

	
	public CaughtVillager(Creature creature) {
		super(creature);

		this.profession = ((Villager) creature).getProfession();
		this.villagerType = ((Villager) creature).getVillagerType();
		this.villagerLevel = ((Villager) creature).getVillagerLevel();
		this.villagerExperience = ((Villager) creature).getVillagerExperience();

		this.inventory = B64Util.itemStackArrayToBase64(((Villager) creature).getInventory().getContents());
		
		// This merchant stuff is kinda nuts.  Consider revising
		List<MerchantRecipe> recipes = ((Villager) creature).getRecipes();
		for( MerchantRecipe recipe : recipes ){
			ItemStack[] result = new ItemStack[]{ recipe.getResult() };
			storedRecipes.add( 
					new StoreMerchantRecipeBuilder()
					.result( B64Util.itemStackArrayToBase64( result ) )
					.ingredients( B64Util.itemListToBase64( recipe.getIngredients() ) )
					.uses( recipe.getUses() )
					.maxUses( recipe.getMaxUses() )
					.experienceReward( recipe.hasExperienceReward() )
					.villagerExperience( recipe.getVillagerExperience() )
					.priceMultiplier( recipe.getPriceMultiplier() )
					.build()
					);
		}
		
	}


	@Override
	public void additionalSets(Entity entity) {

		((Villager) entity).setProfession(profession);
		((Villager) entity).setVillagerType(villagerType);
		((Villager) entity).setVillagerLevel(villagerLevel);
		((Villager) entity).setVillagerExperience(villagerExperience);

		// Again, consider revising this rats-nest
		try {
			((Villager) entity).getInventory().setContents( B64Util.itemStackArrayFromBase64( inventory ));
	
			List<MerchantRecipe> recipes = new ArrayList<MerchantRecipe>();
			for( StoreMerchantRecipe recipe : storedRecipes ){
				ItemStack[] result;
					result = B64Util.itemStackArrayFromBase64( recipe.getResult() );
					MerchantRecipe mr = new MerchantRecipe( 
							result[0],
							recipe.getUses(), 
							recipe.getMaxUses(), 
							recipe.experienceReward(),
							recipe.getVillagerExperience(),
							recipe.getPriceMultiplier() );
					mr.setIngredients( B64Util.itemListFromBase64( recipe.getIngredients() ));
					recipes.add( mr );
			}		
			
		((Villager) entity).setRecipes(recipes);
		
		} catch (IOException e) {
				e.printStackTrace();
		}
		
	}

}
