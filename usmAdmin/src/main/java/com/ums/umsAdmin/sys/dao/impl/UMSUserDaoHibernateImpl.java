package com.ums.umsAdmin.sys.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ums.umsAdmin.common.dao.impl.BaseDaoHibernateImpl;
import com.ums.umsAdmin.sys.dao.UMSUserDao;
import com.ums.umsAdmin.sys.form.QueryUserForm;
import com.ums.umsAdmin.sys.model.UMSUser;

@Repository("umsUserDao")
public class UMSUserDaoHibernateImpl extends BaseDaoHibernateImpl<UMSUser>
		implements UMSUserDao {

	@Override
	public UMSUser findByName(String name) {
		String hql = "from UMSUser u where u.userName='" + name + "'";
		return (UMSUser) getSession().createQuery(hql).uniqueResult();
	}
	
	@Override
	public UMSUser findByUUID(String UUID){
		String hql = "from UMSUser u where u.resetPassUUID='" + UUID + "'";
		return (UMSUser) getSession().createQuery(hql).uniqueResult();
	}

	@Override
	public boolean isExisted(String name) {
		Criteria criteria = getSession().createCriteria(UMSUser.class);
		criteria.add(Restrictions.eq("userName", name));
		List<UMSUser> list = criteria.list();
		if (list.size() > 0)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean isExisted(Long id) {
		Criteria criteria = getSession().createCriteria(UMSUser.class);
		criteria.add(Restrictions.eq("userId", id));
		List<UMSUser> list = criteria.list();
		if (list.size() > 0)
			return true;
		else
			return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UMSUser> pageQuery(int start, int pageSize, QueryUserForm form) {
		Criteria criteria = getCriteria(form);
		criteria.setFirstResult(start);
		criteria.setMaxResults(pageSize);
		return criteria.list();
	}

	@Override
	public long queryCount(QueryUserForm form) {
		return count(getCriteria(form));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UMSUser> blurSearch(String hints) {
		Criteria criteria = getSession().createCriteria(UMSUser.class);
		initCriteria(criteria,hints);
		criteria.addOrder(Order.asc("userName"));
		return criteria.list();
	}

	private Criteria initCriteria(Criteria criteria,String hints) {
		if (hints != null && !"".equals(hints)) {
			Disjunction dis = Restrictions.disjunction();
			dis.add(Restrictions.like("userName", hints, MatchMode.ANYWHERE));
			dis.add(Restrictions.like("email", hints, MatchMode.ANYWHERE));
			criteria.add(dis);
		}
		return criteria;
	}

	private Criteria getCriteria(QueryUserForm form) {
		Criteria criteria = getSession().createCriteria(UMSUser.class);
		if (form.getHints() != null && !"".equals(form.getHints())) {
			initCriteria(criteria,form.getHints());
		}
		if (form.getOrderField() != null && !"".equals(form.getOrderField())) {
			if ("asc".equals(form.getOrderDirection())) {
				criteria.addOrder(Order.asc(form.getOrderField()));
			} else {
				criteria.addOrder(Order.desc(form.getOrderField()));
			}
		}

		return criteria;
	}
}
