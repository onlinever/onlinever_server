package com.onlinever.commons.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		String key = DataSourceContextHolder.getDataSourceKey();
		return key != null ? key : "master";
	}

}
