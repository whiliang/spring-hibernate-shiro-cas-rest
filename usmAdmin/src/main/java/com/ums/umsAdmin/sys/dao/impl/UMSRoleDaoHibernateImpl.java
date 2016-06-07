package com.ums.umsAdmin.sys.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ums.umsAdmin.common.dao.impl.BaseDaoHibernateImpl;
import com.ums.umsAdmin.sys.dao.UMSRoleDao;
import com.ums.umsAdmin.sys.form.QueryRoleForm;
import com.ums.umsAdmin.sys.model.UMSRole;

@Repository("umsRoleDao")
public class UMSRoleDaoHibernateImpl extends BaseDaoHibernateImpl<UMSRole> implements
		UMSRoleDao {
	private static final char IS_DEFAULT='N';
	@Override
	public UMSRole findByName(String name){
		String hql = "from UMSRole r where r.roleName='" + name + "'";
		return (UMSRole) getSession().createQuery(hql).uniqueResult();
	}

	@Override
	public List<UMSRole>findAllNoDefault(){
		String hql="from UMSRole r where r.isDefault!='Y'";
		return (List<UMSRole>) getSession().createQuery(hql).list();
	}
	
	@Override
	public long queryCount(QueryRoleForm form){
		return count(getCriteria(form));
	}
	
	@Override
	public List<UMSRole> pageQuery(int start, int pageSize, QueryRoleForm form) {
		Criteria criteria = getCriteria(form);
		criteria.setFirstResult(start);
		criteria.setMaxResults(pageSize);
		return criteria.list();
	}
	
	private Criteria initCriteria(Criteria criteria,String hints) {
		if (hints != null && !"".equals(hints)) {
			Disjunction dis = Restrictions.disjunction();
			dis.add(Restrictions.like("roleName", hints, MatchMode.ANYWHERE));
			dis.add(Restrictions.like("umsApp.appName", hints, MatchMode.ANYWHERE));
			criteria.add(dis);
		}
		return criteria;
	}
	private Criteria getCriteria(QueryRoleForm form) {
		Criteria criteria = getSession().createCriteria(UMSRole.class);	
		criteria.add(Restrictions.eq("isDefault", form.getIsDefault()));
		criteria.createAlias("umsApp", "umsApp");
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UMSRole> blurSearch(String hints){
		Criteria criteria = getSession().createCriteria(UMSRole.class);
		criteria.createAlias("umsApp", "umsApp");
		criteria.add(Restrictions.eq("isDefault", IS_DEFAULT));
		initCriteria(criteria,hints);
		criteria.addOrder(Order.asc("roleName"));
		return criteria.list();
	}
	
	@Override
	public boolean isExisted(Long id){
		Criteria criteria = getSession().createCriteria(UMSRole.class);
		criteria.add(Restrictions.eq("roleId", id));
		List<UMSRole>list = criteria.list();
		if (list.size() > 0)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean checkRoleName(String roleName,Long appId ){
		Criteria criteria = getSession().createCriteria(UMSRole.class);
		criteria.createAlias("umsApp", "umsApp");
		criteria.add(Restrictions.eq("roleName", roleName));
		criteria.add(Restrictions.eq("umsApp.appId", appId));
		List<UMSRole>list = criteria.list();
		if (list.size() > 0)
			return true;
		else
			return false;
	}
}
