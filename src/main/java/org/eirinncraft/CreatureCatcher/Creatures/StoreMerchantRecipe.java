package org.eirinncraft.CreatureCatcher.Creatures;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Builder 
@Data
class StoreMerchantRecipe implements Serializable {

	private static final long serialVersionUID = 4571728477670824261L;
	
	private String result;
	private String ingredients;
	private int uses;
	private int maxUses;
	@Accessors(fluent = true)
	private boolean experienceReward;
}