package com.virtualcloset.dbdao;

import java.sql.ResultSet;

public interface ObjectMapper {
	public Object mapping(ResultSet rs);
}
