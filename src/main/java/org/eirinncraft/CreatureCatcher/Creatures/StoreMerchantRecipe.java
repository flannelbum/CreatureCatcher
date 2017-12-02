package org.eirinncraft.CreatureCatcher.Creatures;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Builder 
@Data
class StoreMerchantRecipe {

	private String result;
	private String ingredients;
	private int uses;
	private int maxUses;
	@Accessors(fluent = true)
	private boolean experienceReward;
}