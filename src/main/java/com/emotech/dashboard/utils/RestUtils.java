/**
 * Utility class for dealing with REST calls to other hosts
 * Currently only deals with instances where we expect a JSON response 
 * but may be extended for other uses.
 * 
 * @author Tony Noble
 * 
 */
package com.emotech.dashboard.utils;

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
  
  private JsonParser parser = new JsonParser() ;
      
  /**
   *  Return parsed JSON content of a URL
   * @param urlString - the URL to retrieve
   * @return a JsonObject containing the result
   */
  public JsonObject getJsonObjectFromUrl( String urlString ) {
    return this.getJsonObjectFromUrl( urlString , null ) ;
  }
    
    
  /**
   *  Return parsed JSON content of a URL
   * @param urlString - the URL to retrieve
   * @param basicAuth - a string containing basicAuth details
   * @return a JsonObject containing the result
   */
  public JsonObject getJsonObjectFromUrl( String urlString , String basicAuth ) {
    try {
      JsonElement ele = this.getJsonElementFromUrl( urlString , basicAuth ) ;
      if( ele == null ) {
        return new JsonObject() ;
      } else {
        return ele.getAsJsonObject() ;
      }
    } catch( Exception e ) { e.printStackTrace() ; } 
    return new JsonObject() ;
  }
    
    
  /**
   *  Return parsed JSON content of a URL
   * @param urlString - the URL to retrieve
   * @return a JsonArray object containing the result
   */
  public JsonArray getJsonArrayFromUrl( String urlString ) {
    return this.getJsonArrayFromUrl( urlString , null ) ;
  }
      
    
  /**
   *  Return parsed JSON content of a URL
   * @param urlString - the URL to retrieve
   * @param basicAuth - a string containing basicAuth details
   * @return a JsonArray containing the result
   */
  public JsonArray getJsonArrayFromUrl( String urlString , String basicAuth ) {
    try {
      JsonElement ele = this.getJsonElementFromUrl( urlString , basicAuth ) ;
      if( ele == null ) {
        return new JsonArray() ;
      } else {
        return ele.getAsJsonArray() ;
      }
    } catch( Exception e ) { e.printStackTrace() ; } 
    return new JsonArray() ;
  }
    
    
  /**
   *  Return parsed JSON content of a URL
   * @param urlString - the URL to retrieve
   * @param basicAuth - a string containing basicAuth details (may be null)
   * @return a JsonElement containing the result
   */
  private JsonElement getJsonElementFromUrl( String urlString , String basicAuth ) {
    try {
      URL thisUrl = new URL( urlString ) ;
      URLConnection uc = thisUrl.openConnection();
      if( basicAuth != null ) {
        uc.setRequestProperty("Authorization", basicAuth);
      }
      uc.setRequestProperty("Accept", "application/json");

      InputStream in = uc.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(in));

      JsonElement ele = this.parser.parse(br) ;

      br.close();
      in.close();
            
      return ele ;
            
    } catch( Exception e ) { 
      e.printStackTrace() ;  
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
  	System.out.println( "POSTing to destination URL:" + destinationUrl );
      URL url = new URL( destinationUrl ) ;

      StringBuilder postData = new StringBuilder();
      for (Map.Entry<String,String> param : parameters.entrySet()) {
          if (postData.length() != 0) postData.append('&') ;
          postData.append(URLEncoder.encode(param.getKey(), "UTF-8")) ;
          postData.append('=') ;
          postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
      }
      byte[] postDataBytes = postData.toString().getBytes("UTF-8");
    
      System.out.println( "REQUEST BODY: " + postData.toString() ) ;
      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
      conn.setRequestMethod("POST");
      conn.setDoOutput(true);
      conn.getOutputStream().write(postDataBytes);
      
      Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

      for (int c; (c = in.read()) >= 0;)
          System.out.print((char)c);
  }
  
}
