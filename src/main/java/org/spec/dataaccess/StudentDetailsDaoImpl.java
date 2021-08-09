package org.spec.dataaccess;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.spec.model.StudentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("studentDetailsDao")
public class StudentDetailsDaoImpl<T extends StudentDetails> extends GenericDaoHibernateImpl<T> implements StudentDetailsDao<T> {
	@Autowired
	public StudentDetailsDaoImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public StudentDetails getStudentDetail() {
		
		List<StudentDetails> studentDetailsList = getSession().createSQLQuery("select  top 1 * from StudentDetails order by 1 desc")
				.setResultTransformer(new AliasToBeanResultTransformer(StudentDetails.class))
				.list();
		
		return studentDetailsList.get(0);
	}
}
