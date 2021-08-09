package org.spec.business;

import java.util.List;

import org.json.JSONObject;
import org.spec.dataaccess.StudentDetailsDao;
import org.spec.model.StudentDetails;
import org.spec.salesforce.SalesforceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("studentHandler")
public class StudentHandlerImpl implements StudentHandler{
	
	@Autowired
	StudentDetailsDao<StudentDetails> studentDetailsDao;
	
	@Autowired
	SalesforceHandler salesforceHandler;

	@Override
	public List<StudentDetails> getStudentDetails() {
		return studentDetailsDao.loadAll();
		
	}

	@Override
	public StudentDetails getStudentDetail() {
		return studentDetailsDao.getStudentDetail();
	}

	@Override
	public void setStudentDetail(String reqParam) {
		JSONObject requestJSON = new JSONObject(reqParam);
		String firstName = null;
		String lastName = null;
		String address = null;
		String city = null;
		String state = null;
		String zip= null;
		String studentSalesforceId = null;
		int studentDetailsId = 0;
		
		if(requestJSON.getJSONArray("StudentDetails").getJSONObject(0).has("Id")){
			studentSalesforceId = requestJSON.getJSONArray("StudentDetails").getJSONObject(0).getString("Id");
		}
		
		if(requestJSON.getJSONArray("StudentDetails").getJSONObject(0).has("StudentDetailsId__c")){
			studentDetailsId = requestJSON.getJSONArray("StudentDetails").getJSONObject(0).getInt("StudentDetailsId__c");
		}
		
		if(requestJSON.getJSONArray("StudentDetails").getJSONObject(0).has("First_Name__c")){
			firstName = requestJSON.getJSONArray("StudentDetails").getJSONObject(0).getString("First_Name__c");
		}
		if(requestJSON.getJSONArray("StudentDetails").getJSONObject(0).has("Last_Name__c")){
			lastName = requestJSON.getJSONArray("StudentDetails").getJSONObject(0).getString("Last_Name__c");
		}
		if(requestJSON.getJSONArray("StudentDetails").getJSONObject(0).has("Address__c")){
			address = requestJSON.getJSONArray("StudentDetails").getJSONObject(0).getString("Address__c");
		}
		if(requestJSON.getJSONArray("StudentDetails").getJSONObject(0).has("City__c")){
			city = requestJSON.getJSONArray("StudentDetails").getJSONObject(0).getString("City__c");
		}
		if(requestJSON.getJSONArray("StudentDetails").getJSONObject(0).has("State__c")){
			state = requestJSON.getJSONArray("StudentDetails").getJSONObject(0).getString("State__c");
		}
		if(requestJSON.getJSONArray("StudentDetails").getJSONObject(0).has("Zip__c")){
			zip = requestJSON.getJSONArray("StudentDetails").getJSONObject(0).getString("Zip__c");
		}
		
		StudentDetails studentDetails = new StudentDetails();
		studentDetails.setStudentDetailsId(studentDetailsId);
		studentDetails.setFirstName(firstName);
		studentDetails.setLastName(lastName);
		studentDetails.setAddress(address);
		studentDetails.setCity(city);
		studentDetails.setState(state);
		studentDetails.setZip(zip);
		studentDetails.setStudentSalesforceId(studentSalesforceId);
		studentDetailsDao.update(studentDetails);
		
	}

	@Override
	public void saveStudenDetails(StudentDetails studentDetails) {
		
		studentDetailsDao.update(studentDetails);
		
		if(salesforceHandler.loginSalesforce("ankitdesai242@gmail.com", "specTrinity@123axPtV41uJiCIpY0iCe7IJoezt")) {
			salesforceHandler.updateStudentDataIntoSalesforce(studentDetails);
			salesforceHandler.logoutSaleforce();
		}
		
	} 
	
}
