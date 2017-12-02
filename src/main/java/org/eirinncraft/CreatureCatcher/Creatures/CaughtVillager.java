package org.eirinncraft.CreatureCatcher.Creatures;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.eirinncraft.CreatureCatcher.Creatures.StoreMerchantRecipe.StoreMerchantRecipeBuilder;
import org.eirinncraft.CreatureCatcher.Util.B64Util;

import lombok.Getter;

public class CaughtVillager extends CaughtCreature {

	@Getter 
	private Profession profession;
	@Getter
	private int riches;
	@Getter
	private String inventory;
	@Getter
	private List<StoreMerchantRecipe> storedRecipes = new ArrayList<StoreMerchantRecipe>();
	@Getter
	private int careerID;
	@Getter
	private int careerLevel;
	
	public CaughtVillager(Creature creature) {
		super(creature);
		
		VillagerCareerInterface vci = VillagerCareerWrapper.Wrap( ((Villager) creature) );
		this.careerID = vci.getCareerID();
		this.careerLevel = vci.getCareerLevel();
		this.setDisplayName( vci.getAdjustedProfessionName() );
		this.profession = ((Villager) creature).getProfession();
				
		this.riches = ((Villager) creature).getRiches();
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
					.build()
					);
		}
		
	}


	@Override
	public void additionalSets(Entity entity) {
		VillagerCareerInterface vci = VillagerCareerWrapper.Wrap( ((Villager) entity) );
		vci.setCareerID( careerID );
		vci.setCareerLevel( careerLevel );
		
		((Villager) entity).setProfession(profession);
		((Villager) entity).setRiches(riches);
		
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
							recipe.experienceReward() );
					mr.setIngredients( B64Util.itemListFromBase64( recipe.getIngredients() ));
					recipes.add( mr );
			}		
			
		((Villager) entity).setRecipes(recipes);
		
		} catch (IOException e) {
				e.printStackTrace();
		}
		
	}

}


















