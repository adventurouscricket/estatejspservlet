package com.mrhenry.repository;

import java.util.List;

public interface GenericJDBC<T> {
	List<T> query(String sql, Object ... parameters);
	void update(Object entity, Long id);
	Long insert(Object entity);
}
