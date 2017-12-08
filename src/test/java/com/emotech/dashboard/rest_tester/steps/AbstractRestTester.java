/**
 * Abstract base class for testing REST interfaces.
 * 
 * @author Tony Noble
 */
package com.emotech.dashboard.rest_tester.steps ;

import io.restassured.RestAssured ;
import io.restassured.http.ContentType ;
import io.restassured.response.Response ; 

public abstract class AbstractRestTester {
	private String baseUrl ;
	private String path ;
	private Response restResponse ;
	private String restResponseString ;
	private int restResponseStatus ;
	
		
	private void setupRestCall() {
		RestAssured.baseURI = this.baseUrl ;
		RestAssured.basePath = "/rest/" ;
	}
	
	private void postRestCall() {
		this.restResponseString = this.restResponse.asString() ;
		this.restResponseStatus = this.restResponse.getStatusCode() ;
	}
	
	public void getUrl() {
		setupRestCall() ;
		this.restResponse = RestAssured.when().get( this.path ).then().extract().response() ;
		postRestCall() ;
	}
	
	public void getJsonUrl() {
		setupRestCall() ;
		this.restResponse = RestAssured.when().get( this.path ).then().contentType( ContentType.JSON ).extract().response() ;
		postRestCall() ;
	}

	public void postUrl() {
		setupRestCall() ;
		this.restResponse = RestAssured.when().post( this.path ).then().extract().response() ;
		postRestCall() ;
	}
	
	public void putUrl() {
		setupRestCall() ;
		this.restResponse = RestAssured.when().put( this.path ).then().extract().response() ;
		postRestCall() ;
	}
	
	public void deleteUrl() {
		setupRestCall() ;
		this.restResponse = RestAssured.when().delete( this.path ).then().extract().response() ;
		postRestCall() ;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Response getRestResponse() {
		return restResponse;
	}

	public int getRestResponseStatus() {
		return restResponseStatus;
	}

	public String getRestResponseString() {
		return restResponseString;
	}

}