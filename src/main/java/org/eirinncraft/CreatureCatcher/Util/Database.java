package org.eirinncraft.CreatureCatcher.Util;

import org.eirinncraft.CreatureCatcher.Creatures.CaughtCreature;


/**
 * Basic CRUD abstraction for a CaughtCreature
 */
public abstract class Database {
    
	
	/**
	 * Return a CaughtCreature object given a specific token
	 * 
	 * @param token
	 * @return CaughtCreature
	 */
    public abstract CaughtCreature getCreature(String token);
    
    
    /**
     * Creates or Updates a CaughtCreature object
     * 
     * @param creature
     */
    public abstract void updateCreature(CaughtCreature creature);
    
    
    /**
     * Deletes a CaughtCreature object
     * @param token
     */
    public abstract void deleteCreature(String token);
    
}
