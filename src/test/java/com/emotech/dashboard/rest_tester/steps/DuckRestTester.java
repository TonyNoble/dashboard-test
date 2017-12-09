/**
 * Concrete class for testing the DuckDashboard REST interface
 * 
 * @author Tony Noble
 */
package com.emotech.dashboard.rest_tester.steps ;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

public class DuckRestTester extends AbstractRestTester {
  
  private long duckCount ;
  
  private void setCurrentDuckCount() {
    this.setPath( "duck" ) ;
    this.getUrl() ;
    try {
      duckCount = Long.parseLong( this.getRestResponse().path("duckCount").toString() ) ;
    } catch( Exception e ) {
      duckCount = 0 ;
    }    
  }

  @Given( "the duck counting API" )
  public void givenDuckApi() {
    this.setBaseUrl( System.getenv( "baseDuckUrl" ) ) ;
    if( null == this.getBaseUrl() || "".equals( this.getBaseUrl() ) ) {
      this.setBaseUrl( "http://localhost:8080" ) ;
    }
    this.setCurrentDuckCount() ;
  }
  
  @When( "the duck service is called with a GET request" )
  public void getDuck() {
    this.setPath( "duck" ) ;
    this.getJsonUrl() ;
  }
  
  @When( "the duck service is called with a POST request" )
  public void postDuck() {
    this.setCurrentDuckCount() ;
    this.setPath( "duck" ) ;
    this.postUrl() ;
  }

  @When( "the duck service is called with a DELETE request" )
  public void deleteDuck() {
    this.setCurrentDuckCount() ;
    this.setPath( "duck" ) ;
    this.deleteUrl() ;
  }
  
  @When( "the duck service is called with a request that is not GET, POST or DELETE" )
  public void otherDuck() {
    this.setPath( "duck" ) ;
    this.putUrl() ;
  }
  
  @When( "the current number of ducks is zero" )
  public void checkZeroDucks() {
    this.setPath( "duck" ) ;
    this.getUrl() ;
    while( Long.parseLong( this.getRestResponse().path("duckCount").toString() ) > 0 && this.getRestResponseStatus() == 200 ) {
      this.deleteUrl() ;
      if( this.getRestResponseStatus() == 200 ) {
        this.getUrl() ;
      }
    }
  }
  
  @When( "the current number of ducks is non-zero" )
  public void checkNonZeroDucks() {
    this.setPath( "duck" ) ;
    this.postUrl() ;
  }
  
  @When( "the current number of ducks is less than the maximum longint value" )
  public void checkDucksLessThanMaxLong() {
    this.setCurrentDuckCount() ;
    if( duckCount == Long.MAX_VALUE ) {
      this.setPath( "duck" ) ;
      this.deleteUrl() ;
    }
  }
  
  @When( "a page is requested that is not the duck REST endpoint" )
  public void getNonDuckPage() {
    this.setPath( "nonDuck" ) ;
    this.getUrl();
  }
  
  @Then( "a 200 response is returned" )
  public void check200Response() {
    Assert.assertEquals( 200 , this.getRestResponseStatus() ) ;
  }
  
  @Then( "a 500 response is returned" )
  public void check500Response() {
    Assert.assertEquals( 500 , this.getRestResponseStatus() ) ;
  }
  
  @Then( "a 404 response is returned" )
  public void check404Response() {
    Assert.assertEquals( 404 , this.getRestResponseStatus() ) ;
  }
  
  @Then( "a 405 response is returned" )
  public void check405Response() {
    Assert.assertEquals( 405 , this.getRestResponseStatus() ) ;
  }
  
  @Then( "the number of ducks is returned" ) 
  public void checkDucksReturned() {
    Assert.assertNotNull( this.getRestResponse().path( "duckCount" ) ) ;  
  }
  
  @Then( "the number of ducks is incremented" )
  public void checkCurrentDuckCountIncremented() {
    this.setPath( "duck" ) ;
    this.getUrl() ;
    Assert.assertTrue( Long.parseLong( this.getRestResponse().path("duckCount").toString() ) > this.duckCount ) ;
  }
  
  @Then( "the number of ducks is decremented" )
  public void checkCurrentDuckCountDecremented() {
    this.setPath( "duck" ) ;
    this.getUrl() ;
    Assert.assertTrue( Long.parseLong( this.getRestResponse().path("duckCount").toString() ) < this.duckCount ) ;
  }
  
}