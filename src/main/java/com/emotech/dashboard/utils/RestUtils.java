/**
 * Utility class for dealing with REST calls to other hosts
 * Currently only deals with instances where we expect a JSON response 
 * but may be extended for other uses.
 * 
 * @author Tony Noble
 * 
 */
package com.emotech.dashboard.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class RestUtils {
  
  private Log log = LogFactory.getLog(RestUtils.class) ;
  private JsonParser parser = new JsonParser() ;
      
  /**
   *  Return parsed JSON content of a URL
   * @param urlString - the URL to retrieve
   * @return a JsonObject containing the result
   */
  public JsonObject getJsonObjectFromUrl( String urlString ) {
  log.debug( "getJsonObjectFromUrl method invoked with single parameter" ) ;
  log.debug( "urlString: " + urlString ) ;
    return this.getJsonObjectFromUrl( urlString , null ) ;
  }
    
    
  /**
   *  Return parsed JSON content of a URL
   * @param urlString - the URL to retrieve
   * @param basicAuth - a string containing basicAuth details
   * @return a JsonObject containing the result
   */
  public JsonObject getJsonObjectFromUrl( String urlString , String basicAuth ) {
  log.debug( "getJsonObjectFromUrl method invoked with two parameters" ) ;
  log.debug( "urlString: " + urlString ) ;
  log.debug( "basicAuth: " + basicAuth ) ;
    try {
      log.debug( "Invoking getJsonElementFromUrl with supplied parameters" ) ;
      JsonElement ele = this.getJsonElementFromUrl( urlString , basicAuth ) ;
      if( ele == null ) {
      log.debug( "null element returned from getJsonElementFromUrl.  Returning empty JsonObject" ) ;
        return new JsonObject() ;
      } else {
      log.debug( "JsonElement returned successfully" ) ;
        return ele.getAsJsonObject() ;
      }
    } catch( Exception e ) { log.error( "Exception thrown!" , e ) ; } 
    return new JsonObject() ;
  }
    
    
  /**
   *  Return parsed JSON content of a URL
   * @param urlString - the URL to retrieve
   * @return a JsonArray object containing the result
   */
  public JsonArray getJsonArrayFromUrl( String urlString ) {
  log.debug( "getJsonArrayFromUrl method invoked with single parameter" ) ;
  log.debug( "urlString: " + urlString ) ;
    return this.getJsonArrayFromUrl( urlString , null ) ;
  }
      
    
  /**
   *  Return parsed JSON content of a URL
   * @param urlString - the URL to retrieve
   * @param basicAuth - a string containing basicAuth details
   * @return a JsonArray containing the result
   */
  public JsonArray getJsonArrayFromUrl( String urlString , String basicAuth ) {
  log.debug( "getJsonArrayFromUrl method invoked with two parameters" ) ;
  log.debug( "urlString: " + urlString ) ;
  log.debug( "basicAuth: " + basicAuth ) ;
    try {
      log.debug( "Invoking getJsonElementFromUrl with supplied parameters" ) ;
      JsonElement ele = this.getJsonElementFromUrl( urlString , basicAuth ) ;
      if( ele == null ) {
      log.debug( "null element returned from getJsonElementFromUrl.  Returning empty JsonArray" ) ;
        return new JsonArray() ;
      } else {
      log.debug( "JsonElement returned successfully" ) ;
        return ele.getAsJsonArray() ;
      }
    } catch( Exception e ) { log.error( "Exception thrown!" , e ) ; } 
    return new JsonArray() ;
  }
    
    
  /**
   *  Return parsed JSON content of a URL
   * @param urlString - the URL to retrieve
   * @param basicAuth - a string containing basicAuth details (may be null)
   * @return a JsonElement containing the result
   */
  private JsonElement getJsonElementFromUrl( String urlString , String basicAuth ) {
  log.debug( "getJsonElementFromUrl method invoked with two parameters" ) ;
  log.debug( "urlString: " + urlString ) ;
  log.debug( "basicAuth: " + basicAuth ) ;
    try {
      URL thisUrl = new URL( urlString ) ;
      URLConnection uc = thisUrl.openConnection();
      if( basicAuth != null ) {
        uc.setRequestProperty("Authorization", basicAuth);
      }
      uc.setRequestProperty("Accept", "application/json");

      InputStream in = uc.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(in));

      log.debug( "Parsing response from url" ) ;
      JsonElement ele = this.parser.parse(br) ;

      br.close();
      in.close();
            
      log.debug( "Response parsed.  Returning JsonElement" ) ;
      return ele ;
            
    } catch( Exception e ) { 
      log.error( "Exception thrown!" , e ) ;
    }
    return null ;
  }
  
  /**
   *  Post a URL.  Throw an exception if it fails.
   * @param destinationUrl - the URL to POST to
   * @param parameters - a Map containing the parameters to be POSTed
   * @throws Exception - if a failure occurs at any point, an exception is thrown
   */
  public void postUrl( String destinationUrl , HashMap< String , String > parameters ) throws Exception {
    log.debug( "getJsonElementFromUrl method invoked" ) ;
    log.debug( "destinationUrl: " + destinationUrl ) ;

    try {
      URL url = new URL( destinationUrl ) ;

      StringBuilder postData = new StringBuilder();
    
      log.debug( "POST data:" ) ;
      for (Map.Entry<String,String> param : parameters.entrySet()) {
        log.debug( param.getKey() + ": " + param.getValue() ) ;
        if ( postData.length() != 0 ) {
          postData.append( '&' ) ;
        }
        postData.append(URLEncoder.encode(param.getKey(), "UTF-8")) ;
        postData.append('=') ;
        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
      }
      byte[] postDataBytes = postData.toString().getBytes("UTF-8");
    
      log.debug( "Sending request: " + postData.toString() ) ;
      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
      conn.setRequestMethod("POST");
      conn.setDoOutput(true);
      conn.getOutputStream().write(postDataBytes);
      
      Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

      String response = "" ;
      for (int c ; (c = in.read()) >= 0 ; ) {
        response.concat( Character.toString( (char) c ) ) ;
      }
      log.debug( "Received response: " + response ) ;
    } catch( Exception e ) {
    	log.error( "POST to url failed!" , e ) ;
    	throw e ;
    }
  }
}
