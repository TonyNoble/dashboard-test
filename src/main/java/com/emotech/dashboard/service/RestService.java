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

import org.springframework.stereotype.Repository;
import javax.ejb.Singleton;


@Repository("restService")
@Singleton
public class RestService {

  public long duckCount = 0 ;

  /**
   * Add a duck.
   * @throws Exception - if we're about to exceed the max long value (that's a lot of ducks)
   */
  public void addDuck() throws Exception {
	if( duckCount < Long.MAX_VALUE ) {
      duckCount++ ;
	} else {
      throw new Exception( "Too many ducks!  Beware the duckpocalypse!" ) ;
	}
  }
  
  /**
   * Delete a duck
   * @throws Exception - If there are already zero ducks, we don't want to have a negative amount
   */
  public void deleteDuck() throws Exception {
    if( duckCount > 0 ) {
      duckCount-- ;
    } else {
      throw new Exception( "Cannot have less than zero ducks!" ) ;
    }
  }

  /**
   * Get the number of ducks
   * @return a JsonObject containing a number of ducks
   */
  public JsonObject getDucks() {
    JsonObject duckCountObject = new JsonObject() ;
    duckCountObject.addProperty( "duckCount" , duckCount ) ;
    return duckCountObject ;
  }
}
