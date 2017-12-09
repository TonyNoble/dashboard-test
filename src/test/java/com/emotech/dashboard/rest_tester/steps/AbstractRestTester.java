/**
 * Abstract base class for testing REST interfaces.
 * 
 * @author Tony Noble
 */
package com.emotech.dashboard.rest_tester.steps ;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.restassured.RestAssured ;
import io.restassured.http.ContentType ;
import io.restassured.response.Response ; 

public abstract class AbstractRestTester {
  private String baseUrl ;
  private String path ;
  private Response restResponse ;
  private String restResponseString ;
  private int restResponseStatus ;
  
  private Log log = LogFactory.getLog(AbstractRestTester.class) ;
    
  /**
   * Pre-call setup steps
   */
  private void setupRestCall() {
    log.debug( "Setting up RestAssured paths" ) ;
    RestAssured.baseURI = this.baseUrl ;
    RestAssured.basePath = "/rest/" ;
    log.debug( "RestAssured.baseURI: " + RestAssured.baseURI ) ;
    log.debug( "RestAssured.basePath: " + RestAssured.basePath ) ;
  }
  
  
  /**
   * Post-call steps
   */
  private void postRestCall() {
    log.debug( "Setting response string and status") ;
    this.restResponseString = this.restResponse.asString() ;
    this.restResponseStatus = this.restResponse.getStatusCode() ;
    log.debug( "restResponseString: " + this.restResponseString ) ;
    log.debug( "restResponseStatus: " + this.restResponseStatus ) ;
  }
  
  
  /**
   * GET a URL and store the response
   */
  public void getUrl() {
    log.debug( "GETing path: " + this.path ) ;
    setupRestCall() ;
    this.restResponse = RestAssured.when().get( this.path ).then().extract().response() ;
    postRestCall() ;
  }
  
  
  /**
   * GET a URL, expecting a JSON response and store it
   */
  public void getJsonUrl() {
    log.debug( "GETing path (with JSON response expected): " + this.path );
    setupRestCall() ;
    this.restResponse = RestAssured.when().get( this.path ).then().contentType( ContentType.JSON ).extract().response() ;
    postRestCall() ;
  }

  
  /**
   * POST a URL and store the response
   */
  public void postUrl() {
    log.debug( "POSTing path: " + this.path ) ;
    setupRestCall() ;
    this.restResponse = RestAssured.when().post( this.path ).then().extract().response() ;
    postRestCall() ;
  }
  
  
  /**
   * PUT a URL and store the response
   */
  public void putUrl() {
    log.debug( "PUTing path: " + this.path ) ;
    setupRestCall() ;
    this.restResponse = RestAssured.when().put( this.path ).then().extract().response() ;
    postRestCall() ;
  }
  
  
  /**
   * DELETE a URL and store the response
   */
  public void deleteUrl() {
    log.debug( "DELETEing path: " + this.path ) ;
    setupRestCall() ;
    this.restResponse = RestAssured.when().delete( this.path ).then().extract().response() ;
    postRestCall() ;
  }

  
  /**
   * Getter method for host URL
   * @return the host URL
   */
  public String getBaseUrl() {
    log.debug( "getBaseUrl(): " + baseUrl ) ;
    return baseUrl;
  }

  
  /**
   * Setter method for the host URL
   * @param baseUrl
   */
  public void setBaseUrl(String baseUrl) {
    log.debug( "setBaseUrl(): " + baseUrl ) ;
    this.baseUrl = baseUrl;
  }

  
  /**
   * Getter method for the REST endpoint path
   * @return the REST endpoint path
   */
  public String getPath() {
    log.debug( "getPath(): " + path ) ;
    return path;
  }

  
  /**
   * Setter method for the REST endpoint path
   * @param path
   */
  public void setPath(String path) {
    log.debug( "setPath(): " + path ) ;
    this.path = path;
  }

  
  /**
   * Getter method for the most recent REST call response
   * @return the Response object
   */
  public Response getRestResponse() {
    log.debug( "getRestResponse(): " + restResponse ) ;
    return restResponse;
  }

  
  /**
   * Getter method for the reponse code from the most recent REST call
   * @return the HTTP response code from the most recent REST call
   */
  public int getRestResponseStatus() {
    log.debug( "getRestResponseStatus(): " + restResponseStatus ) ;
    return restResponseStatus;
  }

  
  /**
   * Getter method for the string representation of the most recent 
   * REST call response
   * @return the string representation of the most recent REST call response
   */
  public String getRestResponseString() {
    log.debug( "getRestResponseString(): " + restResponseString ) ;
    return restResponseString;
  }

}