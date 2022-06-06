package com.deinsoft.puntoventa.framework.repository;

import java.util.List;
import java.util.Map;

import com.deinsoft.puntoventa.framework.model.MetaData;


public interface IJdbcRepository {
	List<MetaData> findAll(String tableName);
}
