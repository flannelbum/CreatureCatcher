package org.eirinncraft.CreatureCatcher.Util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eirinncraft.CreatureCatcher.CreatureCatcher;
import org.eirinncraft.CreatureCatcher.Creatures.CaughtCreature;
import org.eirinncraft.CreatureCatcher.Creatures.CaughtCreatureFactory;

import com.google.gson.Gson;


public class SQLite extends Database{
    
	private CreatureCatcher plugin;
	private Connection connection;
	private File databaseFile;
	
	private String sqlCreateTable = "CREATE TABLE IF NOT EXISTS `CaughtCreature` ("
			+ "`token` TEXT NOT NULL PRIMARY KEY UNIQUE,"
			+ "`entityType` TEXT NOT NULL,"
			+ "`creatureJSON` TEXT NOT NULL)";
	
	
	public SQLite(CreatureCatcher plugin){
        super();
        this.plugin = plugin;
        this.databaseFile = new File(plugin.getDataFolder(), "captured.db");
        
        try {
        	Connection conn = getSQLConnection();
            Statement s = conn.createStatement();
            s.executeUpdate(sqlCreateTable);
            s.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
	}

    
    public Connection getSQLConnection() {
        if (!databaseFile.exists()){
            try {
            	databaseFile.createNewFile();
            } catch (IOException e) {
                plugin.log("File write error: " + databaseFile.toString());
            }
        }
    	
    	
        try {
        	
            if(connection!=null && !connection.isClosed()){
                return connection;
            }
            
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile);
            return connection;
            
        } catch (SQLException ex) {
        	plugin.log("SQLite exception on initialize");
        	ex.printStackTrace();   
        	
        } catch (ClassNotFoundException ex) {
            plugin.log("org.sqlite.JDBC class not found!!");
            ex.printStackTrace();
        }
        
        return null;
    }

    
    
    

	@Override
	public CaughtCreature getCreature(String token) {
		CaughtCreature creature = null;
		
		String sql = "SELECT entityType, creatureJSON FROM CaughtCreature WHERE token = ? ";
		
		Gson gson = new Gson();
		Connection conn;
		PreparedStatement ps;

		try {
			conn = getSQLConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, token);
			ResultSet rs = ps.executeQuery();
			
			if(!rs.isClosed())
				creature = (CaughtCreature) gson.fromJson(rs.getString("creatureJSON"), CaughtCreatureFactory.getClass( rs.getString("entityType") ));
			else
				plugin.log("No SQL ResultSet for token: " + token);
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return creature;
	}
    
    
	@Override
	public void updateCreature(CaughtCreature creature) {
		Gson gson = new Gson();
		String json = gson.toJson(creature);
		String sql = "INSERT OR REPLACE INTO CaughtCreature (token, entityType, creatureJSON) VALUES(?,?,?)";
		
		Connection conn;
		PreparedStatement ps;
		
		try {
			conn = getSQLConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, creature.getToken());
			ps.setString(2, creature.getType().toString());
			ps.setString(3, json);
			
			ps.executeUpdate();
			
			ps.close();
			conn.close();
			
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}


	@Override
	public void deleteCreature(String token) {
		String sql = "DELETE FROM CaughtCreature WHERE token = ? ";
		
		Connection conn;
		PreparedStatement ps;
		
		try {
			conn = getSQLConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, token);
			
			ps.executeUpdate();
			
			ps.close();
			conn.close();
			
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}
    
    
}

