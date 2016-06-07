package com.ums.umsRestService.common.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ums.umsRestService.common.dao.BaseDao;

public class BaseDaoHibernateImpl<M extends Serializable> implements BaseDao<M> {
	protected static final Logger log = LoggerFactory
			.getLogger(BaseDaoHibernateImpl.class);
	private final Class<M> entityClass;

	@SuppressWarnings("unchecked")
	public BaseDaoHibernateImpl() {
		entityClass = (Class<M>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(M entity) {
		getSession().save(entity);
	}

	@Override
	public void delete(M entity) {
		getSession().delete(entity);
	}

	@Override
	public void delete(Long id) {
		getSession().delete(getById(id));
	}

	@Override
	public void update(M entity) {
		getSession().update(entity);
	}

	@Override
	public M getById(Long id) {
		return (M) getSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<M> findByIds(Long[] ids) {
		return getSession()
				.createQuery(
						"from " + entityClass.getSimpleName()
								+ " where id in(ids)").setParameter("ids", ids)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<M> findAll() {
		return getSession().createQuery("from " + entityClass.getSimpleName())
				.list();
	}

	@Override
	public Long count() {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
}
