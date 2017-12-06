/**
 * REST controller class.  Contains the rest endpoints for the application.  These will, in turn
 * call the relevant service methods to deal with the request.
 * 
 * @author Tony Noble
 * 
 */
package com.emotech.dashboard.controllers;

import com.emotech.dashboard.service.* ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson ;

@RestController
public class DashboardRestController {

  @Autowired
  @Qualifier(value = "restService")
  private RestService restService ;

  private Gson json = new Gson() ;   

  /**
   * REST endpoint to post a duck.
   * @return ResponseEntity - either 200, or a 500 containing any exception message
   */
  @RequestMapping( path="/duck" , method = RequestMethod.POST , produces = "application/json; charset=utf-8" )
  @ResponseBody
  public ResponseEntity< String > postDuck() {
    try {
      restService.addDuck() ;
      return new ResponseEntity< String >( HttpStatus.OK ) ;
    } catch( Exception e ) {
      e.printStackTrace() ;
      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.setContentType(MediaType.APPLICATION_JSON);
      return new ResponseEntity< String >( json.toJson( e.getMessage() ) , responseHeaders , HttpStatus.INTERNAL_SERVER_ERROR ) ;
    }
  }  

  /**
   * REST endpoint to get the current count of ducks.
   * @return ResponseEntity containing a JSON object (with the count), or a 500 containing any exception message
   */
  @RequestMapping( path="/duck" , method = RequestMethod.GET , produces = "application/json; charset=utf-8" )
  @ResponseBody
  public ResponseEntity< String > getDucks() {
	  
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setContentType(MediaType.APPLICATION_JSON);
    
    try {
      return new ResponseEntity< String >( json.toJson( restService.getDucks() ) , responseHeaders , HttpStatus.OK ) ;
    } catch( Exception e ) {
      e.printStackTrace() ;
      return new ResponseEntity< String >( json.toJson( e.getMessage() ) , responseHeaders , HttpStatus.INTERNAL_SERVER_ERROR ) ;
    }
  }  

  /**
   * REST endpoint to delete a duck.
   * @return ResponseEntity - either 200, or a 500 containing any exception message
   */
  @RequestMapping( path="/duck" , method = RequestMethod.DELETE , produces = "application/json; charset=utf-8" )
  @ResponseBody
  public ResponseEntity< String > deleteDuck() {

    try {
      restService.deleteDuck() ;
      return new ResponseEntity< String >( HttpStatus.OK ) ;
    } catch( Exception e ) {
      e.printStackTrace() ;
      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.setContentType(MediaType.APPLICATION_JSON);
      return new ResponseEntity< String >( json.toJson( e.getMessage() ) , responseHeaders , HttpStatus.INTERNAL_SERVER_ERROR ) ;
    }
  }  

}
