/**
 * Singleton service, providing functionality purely for the REST controller
 * Other services may be called by the REST controller, but functions that are 
 * not used anywhere else reside here.
 * 
 * @author Tony Noble
 * 
 */
package com.emotech.dashboard.service;

import com.google.gson.JsonObject ;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Repository;
import javax.ejb.Singleton;


@Repository("restService")
@Singleton
public class RestService {

  private Log log = LogFactory.getLog(RestService.class) ;

  public long duckCount = 0 ;

  /**
   * Add a duck.
   * @throws Exception - if we're about to exceed the max long value (that's a lot of ducks)
   */
  public void addDuck() throws Exception {
	log.debug( "addDuck method invoked" ) ;
	log.debug( "duckCount is " + duckCount ) ;
	if( duckCount < Long.MAX_VALUE ) {
      duckCount++ ;
	} else {
      log.error( "duckCount aready is max value of long.  Cannot increment" ) ;
      throw new Exception( "Too many ducks!  Beware the duckpocalypse!" ) ;
	}
	log.debug( "duckCount is now " + duckCount ) ;
	log.debug( "Exiting addDuck method" ) ;
  }
  
  /**
   * Delete a duck
   * @throws Exception - If there are already zero ducks, we don't want to have a negative amount
   */
  public void deleteDuck() throws Exception {
	log.debug( "deleteDuck method invoked" ) ;
	log.debug( "duckCount is " + duckCount ) ;
    if( duckCount > 0 ) {
      duckCount-- ;
    } else {
      log.error( "duckCount aready zero.  We can't go negative" ) ;
      throw new Exception( "Cannot have less than zero ducks!" ) ;
    }
	log.debug( "duckCount is now " + duckCount ) ;
	log.debug( "Exiting deleteDuck method" ) ;
  }

  /**
   * Get the number of ducks
   * @return a JsonObject containing a number of ducks
   */
  public JsonObject getDucks() {
	log.debug( "getDucks method invoked" ) ;
	log.debug( "duckCount is " + duckCount ) ;
    JsonObject duckCountObject = new JsonObject() ;
    duckCountObject.addProperty( "duckCount" , duckCount ) ;
    log.debug( "Exiting getDucks method" ) ;
    return duckCountObject ;
  }
}
