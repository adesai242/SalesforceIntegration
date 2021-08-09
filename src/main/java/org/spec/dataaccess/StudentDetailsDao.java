package org.spec.dataaccess;

import org.spec.model.StudentDetails;

public interface StudentDetailsDao<T extends StudentDetails> extends GenericDao<T> {
	public StudentDetails getStudentDetail();
}
