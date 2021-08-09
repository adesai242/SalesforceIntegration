package org.spec.dataaccess;

import java.util.List;
import java.util.Map;
import java.util.Set;



/**
 * Dec 29, 2015 
 * Class for GenericDao
 * @author devi.singh
 * @version v1.0
 * @param <T>
 */
public interface GenericDao <T>
{
   public void evictAll();
	public int insert(T t);
	public void delete(T t);
	public void update(T t);
	public T loadById(int id);
	public List<T> loadByAttribute(String attributeName, Object attributeValue);
	public List<T> loadByAttribute(Map<String, Object> attributes);
	public List<T> loadByAttributeConditions(Map<String, Object> attributes);
	public List<T> loadAll();
	public List<T> loadByFieldList(String fieldName, String objectIdField, List<?> objectList);
	public List<T> loadByFieldList(String fieldName, List<?> fieldList);
	public List<T> loadByFieldList(String fieldName, Set<?> fieldSet);
	public void evictObject(T t);
	public List<T> loadByAttribute(String attributeName, Object attributeValue, String orderBy);
	public void commit();
	public List<T> loadByAttributeWithOrder(Map<String, Object> attributes,String orderby);
}
