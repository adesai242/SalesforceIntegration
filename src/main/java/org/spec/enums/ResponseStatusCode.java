package org.spec.enums;

public enum ResponseStatusCode {
	T200("OK"), T500("INTERNAL_SERVER_ERROR"),U501("Already User Exits"),T502("Regstration email is not sent due to not active semester for this program"),
	U502("User not found"),L503("Already LDPA Name"),S001("Student is not created into SIS");
	
	private final String responseDescription;
	private String exceptionClassName;
	
	ResponseStatusCode(String responseDescription ) {
		this.responseDescription = responseDescription;
	}
	
	ResponseStatusCode(String responseDescription, String exceptionClassName) {
        this.responseDescription = responseDescription;
        this.exceptionClassName = exceptionClassName;
    }

	public String getResponseDescription() {
		return responseDescription;
	}
}
