package org.spec.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="StudentDetails")
public class StudentDetails {
	
	@Id
	@Column( name = "studentDetailsId", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int studentDetailsId;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String studentSalesforceId;
	
	public int getStudentDetailsId() {
		return studentDetailsId;
	}
	public void setStudentDetailsId(int studentDetailsId) {
		this.studentDetailsId = studentDetailsId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getStudentSalesforceId() {
		return studentSalesforceId;
	}
	public void setStudentSalesforceId(String studentSalesforceId) {
		this.studentSalesforceId = studentSalesforceId;
	}
	
	
}
