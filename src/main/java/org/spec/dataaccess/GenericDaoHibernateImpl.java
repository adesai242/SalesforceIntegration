package org.spec.dataaccess;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spec.model.UI_Utilities;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;




/**
 * Dec 29, 2015 
 * abstarct class for GenericDaoHibernateImpl
 * @author devi.singh
 * @version v1.0
 * @param <T>
 */
public abstract class GenericDaoHibernateImpl<T> extends HibernateDaoSupport implements GenericDao<T>
{
	
	/* 
	* evictAll: Clear all session
	* @param:@see org.tls.dataaccess.GenericDao#evictAll() 
	* @return
	*/
	public void evictAll()
	{
		
		getSession().clear();
	}
	
	
	/* 
	* loadById: Load entity by id 
	* @param:@see org.tls.dataaccess.GenericDao#loadById(int) 
	* @return
	*/
	@SuppressWarnings("unchecked")
	public T loadById(int id) 
	{
		
		getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_EAGER);
		T t = (T)this.getHibernateTemplate().get(getDaoClass(), id);
		return t;
	}

	/* 
	* delete:
	* @param:@see org.tls.dataaccess.GenericDao#delete(java.lang.Object) 
	* @return
	*/
	public void delete(T t) {
		
		getHibernateTemplate().delete(t);
	}

	
	/* 
	* update:
	* @param:@see org.tls.dataaccess.GenericDao#update(java.lang.Object) 
	* @return
	*/
	public void update(T t) {
		
		getHibernateTemplate().saveOrUpdate(t);
	}
	
	
	/* 
	* insert:
	* @param:@see org.tls.dataaccess.GenericDao#insert(java.lang.Object) 
	* @return
	*/
	public int insert(T t) 
	{
	
			Serializable id = getHibernateTemplate().save(t);
			return new Integer(id.toString()).intValue();

	}
	
	
	/**
	 * DO NOT USE THIS FUNCTION
	 * 
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<?> loadEntity(Class<?> type)
	{
		getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_EAGER);
		Collection<T> result = new LinkedHashSet<T>((Collection<? extends T>) getHibernateTemplate().loadAll(type));
		List<T> filteredResult = new ArrayList<T>(result);
		return filteredResult;
	}
	
	/* 
	* loadAll: Load  all object of passed entity
	* @param:@see org.tls.dataaccess.GenericDao#loadAll() 
	* @return
	*/
	@SuppressWarnings("unchecked")
	public List<T> loadAll() {
		
		getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_EAGER);
		Collection<T> result = new LinkedHashSet<T>(getHibernateTemplate().loadAll(getDaoClass()));
		List<T> filteredResult = new ArrayList<T>(result);
		return filteredResult;
	}
	
	/* 
	* loadByAttribute: Loading entity by attributeName
	* @param:@see org.tls.dataaccess.GenericDao#loadByAttribute(java.lang.String, java.lang.Object) 
	* @return
	*/
	@SuppressWarnings("unchecked")
	public List<T> loadByAttribute(String attributeName, Object attributeValue)
	{
		DetachedCriteria crit = DetachedCriteria.forClass(this.getDaoClass());
		crit.add(Restrictions.eq(attributeName, attributeValue));
		crit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return getHibernateTemplate().findByCriteria(crit);
	}
	
	/* 
	* loadByAttribute: Loading entity by attributeName,attributeValue with order by
	* @param:@see org.tls.dataaccess.GenericDao#loadByAttribute(java.lang.String, java.lang.Object, java.lang.String) 
	* @return
	*/
	@SuppressWarnings("unchecked")
	public List<T> loadByAttribute(String attributeName, Object attributeValue, String orderBy)
	{
		
		DetachedCriteria crit = DetachedCriteria.forClass(this.getDaoClass());
		crit.add(Restrictions.eq(attributeName, attributeValue));
		crit.addOrder(Order.desc(orderBy));
		crit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return getHibernateTemplate().findByCriteria(crit);
	}
	
	/* 
	* loadByAttribute: : Loading entity by attribute list
	* @param:@see org.tls.dataaccess.GenericDao#loadByAttribute(java.util.Map) 
	* @return
	*/
	@SuppressWarnings("unchecked")
	public List<T> loadByAttribute(Map<String, Object> attributes)
	{
		
		Set<String> attributeNameList = attributes.keySet();
		DetachedCriteria crit = DetachedCriteria.forClass(getDaoClass());
		for(String attributeName : attributeNameList)
		{
			Object attr = attributes.get(attributeName);
			
			crit.add(Restrictions.eq(attributeName, 
					attributes.get(attributeName)));
		}
		crit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return getHibernateTemplate().findByCriteria(crit);
	}
	
	/* 
	* loadByAttributeConditions:
	* @param:@see org.tls.dataaccess.GenericDao#loadByAttributeConditions(java.util.Map) 
	* @return
	*/
	@SuppressWarnings("unchecked")
	public List<T> loadByAttributeConditions(Map<String, Object> attributesConditions)
	{
		
		Set<String> attributeNameListCond = attributesConditions.keySet();
		DetachedCriteria crit = DetachedCriteria.forClass(getDaoClass());
		for(String conditionName : attributeNameListCond)
		{
			Object attr = attributesConditions.get(conditionName);
			Map<String, Object> attributes=(Map<String, Object>)attr;
			Set<String> attributeNameList = attributes.keySet();
			for(String attributeName : attributeNameList)
			{
				if(conditionName.equals("eq")){
					crit.add(Restrictions.eq(attributeName, 
							attributes.get(attributeName)));
				}else if(conditionName.equals("ne")){
					crit.add(Restrictions.ne(attributeName, 
							attributes.get(attributeName)));
				} 
			}
		}
		crit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return getHibernateTemplate().findByCriteria(crit);
	}
	//GenDAOIMPL#1#91753604 - end
	
	
	/**
	 * Method for :GenericDaoHibernateImpl
	 * getDaoClass :DAO class finder
	 */
	@SuppressWarnings("unchecked")
	public Class<? extends T> getDaoClass()
	{
		
		
		Class clazz = getClass();
		Type[] param = clazz.getTypeParameters();
		if(param.length > 0)
		{
			Type[] types = ((TypeVariable<?>)param[0]).getBounds();
			if(types.length > 0)
				return (Class<T>)types[0];
		}
		
		while(clazz.getSuperclass() != GenericDaoHibernateImpl.class)
		{
			clazz = clazz.getSuperclass();
		}
		return (Class<T>)(((ParameterizedType)clazz.getGenericSuperclass()).
					getActualTypeArguments()[0]);
	}
	
	/* 
	* evictObject: evict object from session
	* @param:@see org.tls.dataaccess.GenericDao#evictObject(java.lang.Object) 
	* @return
	*/
	public void evictObject(T t)
	{
		this.getSession().evict(t);
	}
	
	/* 
	* loadByFieldList: Load by field list
	* @param:@see org.tls.dataaccess.GenericDao#loadByFieldList(java.lang.String, java.util.List) 
	* @return
	*/
	@SuppressWarnings("unchecked")
	public List<T> loadByFieldList(String fieldName, List<?> fieldList)
	{
		
		if(fieldList.size() > 0)
		{
			DetachedCriteria crit = DetachedCriteria.forClass(getDaoClass()).add(Restrictions.in(fieldName, fieldList));
			crit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			return getHibernateTemplate().findByCriteria(crit);			
		}
		return new ArrayList<T>();
	}
	
	/* 
	* loadByFieldList: Load by field list with field name
	* @param:@see org.tls.dataaccess.GenericDao#loadByFieldList(java.lang.String, java.util.Set) 
	* @return
	*/
	public List<T> loadByFieldList(String fieldName, Set<?> fieldList)
	{
		if(fieldList.size() > 0)
		{
			DetachedCriteria crit = DetachedCriteria.forClass(getDaoClass()).add(Restrictions.in(fieldName, fieldList));
			crit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			return getHibernateTemplate().findByCriteria(crit);			
		}
		return new ArrayList<T>();
	}

	/**
	 * Method for :GenericDaoHibernateImpl
	 * getIdList
	 */
	protected List<Integer> getIdList(String idField, List<?> objectList)
	{
		List<Integer> idList = new ArrayList<Integer>();
		for(Object obj : objectList)
		{
		
				idList.add(Integer.parseInt(UI_Utilities.getField(idField, obj).toString()));								
		
		}
		return idList;
	}
	
	/* 
	* loadByFieldList: Load by field name ,object id and list
	* @param:@see org.tls.dataaccess.GenericDao#loadByFieldList(java.lang.String, java.lang.String, java.util.List) 
	* @return
	*/
	public List<T> loadByFieldList(String fieldName, String objectIdField, List<?> objectList)
	{
		
		DetachedCriteria crit = DetachedCriteria.forClass(this.getDaoClass());
		
		List<Integer> idList = getIdList(objectIdField, objectList);
		if(idList.size() == 0)
		{
			return new ArrayList<T>();
		}
		crit.add(Restrictions.in(fieldName, idList));
		crit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return getHibernateTemplate().findByCriteria(crit);
	}
	
	
	/* 
	* commit: committing session
	* @param:@see org.tls.dataaccess.GenericDao#commit() 
	* @return
	*/
	public void commit()
	{
		getSession().flush();
	}
	
	
	/**
	 * Method for :GenericDaoHibernateImpl
	 * getDaoId
	 */
	public int getDaoId(T t) {
		
		return (Integer) getSession().getIdentifier(t);
	}
	
	
	/* 
	* loadByAttribute: : Loading entity by attribute list and sort order, Created for Meeting form fields requirements by Devisingh. 
	* @param:@see org.tls.dataaccess.GenericDao#loadByAttribute(java.util.Map) 
	* @return
	*/
	@SuppressWarnings("unchecked")
	public List<T> loadByAttributeWithOrder(Map<String, Object> attributes,String orderby)
	{
		
		Set<String> attributeNameList = attributes.keySet();
		DetachedCriteria crit = DetachedCriteria.forClass(getDaoClass());
		for(String attributeName : attributeNameList)
		{
			Object attr = attributes.get(attributeName);
			
			crit.add(Restrictions.eq(attributeName, 
					attributes.get(attributeName)));
		}
		crit.addOrder(Order.asc(orderby));
		crit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return getHibernateTemplate().findByCriteria(crit);
	}
}