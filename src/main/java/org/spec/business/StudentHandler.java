package org.spec.business;

import java.util.List;

import org.spec.model.StudentDetails;

public interface StudentHandler {
	public List<StudentDetails> getStudentDetails();
	public StudentDetails getStudentDetail();
	public void setStudentDetail(String reqParam);
	public void saveStudenDetails(StudentDetails studentDetails);
}
