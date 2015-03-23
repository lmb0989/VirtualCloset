package com.virtualcloset.dbdao;

import java.sql.ResultSet;

public interface ObjectMapper {
	public ObjectMapper mapping(ResultSet rs);
	
	public ObjectMapper clone();
}
