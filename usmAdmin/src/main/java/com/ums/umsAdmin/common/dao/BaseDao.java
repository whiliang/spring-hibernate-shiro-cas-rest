package com.ums.umsAdmin.common.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;

public interface BaseDao<M extends Serializable> {
	public void save(M entity);
	public void delete(M entity);
	public void delete(Long id);
	public void update(M entity);
	public M getById(Long id);
	public List<M> findByIds(Long[] ids);
	public List<M> findAll();
	public long count();
	public long count(Criteria criteria);
}
