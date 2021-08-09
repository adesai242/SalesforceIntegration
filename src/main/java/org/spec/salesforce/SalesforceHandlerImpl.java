package org.spec.salesforce;

import org.spec.model.StudentDetails;
import org.springframework.stereotype.Service;

import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.Error;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

@Service("salesforceHandler")
public class SalesforceHandlerImpl implements SalesforceHandler {

	private static PartnerConnection connection;
	public static int connectionCount = 2;

	@Override
	public boolean loginSalesforce(String userName, String token) {
		ConnectorConfig config = new ConnectorConfig();
		config.setUsername(userName);
		config.setPassword(token);
		try {
			connection = Connector.newConnection(config);
			LoginResult loginResult = connection.login(userName, token);

			System.out.println("Sevlet URL:" + loginResult.getServerUrl());
			System.out.println("Session ID:" + loginResult.getSessionId());

			return true;

		} catch (ConnectionException e1) {
			System.out
					.println("Connection falied in loginSalesforce function--------");
			connectionCount--;
			e1.printStackTrace();
			try {
				if (connectionCount > 0) {
					Thread.sleep(20000);
					loginSalesforce(userName, token);
				} else {
					return false;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public void logoutSaleforce() {
		try {
			connection.logout();
		} catch (ConnectionException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean updateStudentDataIntoSalesforce(StudentDetails studentDetails) {
		try {
				String Id = studentDetails.getStudentSalesforceId();
				SObject updateStudent = new SObject();
				updateStudent.setType("Student__c");
				updateStudent.setId(Id);
				
				updateStudent.setField("First_Name__c", studentDetails.getFirstName());
				updateStudent.setField("Last_Name__c", studentDetails.getLastName());
				updateStudent.setField("Address__c", studentDetails.getAddress());
				updateStudent.setField("City__c", studentDetails.getCity());
				updateStudent.setField("State__c", studentDetails.getState());
				updateStudent.setField("Zip__c", studentDetails.getZip());
				updateStudent.setField("StudentDetailsId__c", studentDetails.getStudentDetailsId());

				// Make the update call by passing an array containing
				// the two objects.
				SaveResult[] saveResults = connection
						.update(new SObject[] { updateStudent });
				for (int j = 0; j < saveResults.length; j++) {
					if (saveResults[j].isSuccess()) {
						System.out.println("Contact with an ID of "
								+ saveResults[j].getId() + " was updated.");
					} else {
						for (int i = 0; i < saveResults[j].getErrors().length; i++) {
							com.sforce.soap.partner.Error err = saveResults[j]
									.getErrors()[i];
							System.out
									.println("Errors were found on item " + j);
							System.out.println("Error code: "
									+ err.getStatusCode().toString());
							System.out.println("Error message: "
									+ err.getMessage());
						}
						//failInsertApplication(saveResults[j].getErrors(),student);
						return false;
					}
				}
			
			return true;

		} catch (Exception ce) {
			ce.printStackTrace();
		}

		return false;
	}
	
	public void failInsertApplication(Error[] resultsErrors,StudentDetails studentDetails) {
		String errorCode = "";
		String errorMessage="";
		
		for (int i = 0; i < resultsErrors.length; i++) {
			com.sforce.soap.partner.Error err = resultsErrors[i];
			errorCode = errorCode + ' ' + err.getStatusCode().toString();
			errorMessage = errorMessage + ' ' + err.getMessage();
		}
	}

}
