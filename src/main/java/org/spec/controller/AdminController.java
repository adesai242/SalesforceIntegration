package org.spec.controller;

import org.spec.business.StudentHandler;
import org.spec.enums.ResponseStatusCode;
import org.spec.model.SalesforceResponse;
import org.spec.model.StudentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {
	
	@Autowired
	StudentHandler studentHandler;
	
	@RequestMapping(value = "/getStudentDetails", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public SalesforceResponse<String> getStudentDetails () {
				
		SalesforceResponse<String> requestJson = new SalesforceResponse<String>();
	
		StudentDetails studentDetails = studentHandler.getStudentDetail();
		try {
				requestJson.setDataObject(studentDetails);
				requestJson.setStatusCode(ResponseStatusCode.T200.name());
				requestJson.setStatusDescription(ResponseStatusCode.T200.getResponseDescription());

		} catch (Exception e) {
			requestJson.setStatusCode(ResponseStatusCode.S001.name());
			requestJson.setStatusDescription(ResponseStatusCode.S001.getResponseDescription());
			e.printStackTrace();
		}
		return requestJson;
	}
	
	@RequestMapping(value = "/setStudentDetails", method = RequestMethod.POST ,consumes = "application/json", produces = "application/json")
	@ResponseBody
	public SalesforceResponse<String> setStudentDetails (@RequestBody String reqParam) {
				
		
		System.out.println("Request from Test:" + reqParam);
		SalesforceResponse<String> requestJson = new SalesforceResponse<String>();
	
		studentHandler.setStudentDetail(reqParam);
		try {
				
				requestJson.setStatusCode(ResponseStatusCode.T200.name());
				requestJson.setStatusDescription(ResponseStatusCode.T200.getResponseDescription());

		} catch (Exception e) {
			requestJson.setStatusCode(ResponseStatusCode.S001.name());
			requestJson.setStatusDescription(ResponseStatusCode.S001.getResponseDescription());
			e.printStackTrace();
		}
		return requestJson;
	}
	
	@RequestMapping(value = "/saveStudentDetails", method = RequestMethod.POST ,consumes = "application/json", produces = "application/json")
	@ResponseBody
	public SalesforceResponse<String> saveStudentDetails (@RequestBody StudentDetails studentDetails) {
				
		SalesforceResponse<String> requestJson = new SalesforceResponse<String>();
	
		studentHandler.saveStudenDetails(studentDetails);
		try {
				
				requestJson.setStatusCode(ResponseStatusCode.T200.name());
				requestJson.setStatusDescription(ResponseStatusCode.T200.getResponseDescription());

		} catch (Exception e) {
			requestJson.setStatusCode(ResponseStatusCode.S001.name());
			requestJson.setStatusDescription(ResponseStatusCode.S001.getResponseDescription());
			e.printStackTrace();
		}
		return requestJson;
	}
}
