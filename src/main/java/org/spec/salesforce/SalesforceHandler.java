package org.spec.salesforce;

import org.spec.model.StudentDetails;

public interface SalesforceHandler {
	public boolean loginSalesforce(String userName,String token);
	public void logoutSaleforce();
	public boolean updateStudentDataIntoSalesforce(StudentDetails studentDetails);
	
}
