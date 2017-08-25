# EasyToUseVolley
[![](https://jitpack.io/v/molaeiali/easytousevolley.svg)](https://jitpack.io/#molaeiali/easytousevolley)

This module simplifyed using of volley

## Features
- GET requests
- POST requests
- Multipart requests

## Usage
Step 1. Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}


Step 2. Add the dependency

	dependencies {
	        compile 'com.github.molaeiali:easytousevolley:1.0.0-beta2'
	}

You need to create a singleton class named **EasyVolley** that extends **EasyVolleyManager**.
It has to Override **defaultHeaders** ,if you have a default header that should used on all connections, create it here and if not, return null.

It has to Override **getLoading** too, if you have a loading view for connections, it should implement **EasyVolleyLoading** that has two functions, **start** and **stop**, and you should return a new instance of it on getLoading, and if you don't have any loading view, return null.

	package org.molaei.easytousevolley.api;


	import android.app.Activity;
	import android.content.Context;

	import org.molaei.easyvolley.EasyVolleyLoading;
	import org.molaei.easyvolley.EasyVolleyManager;

	import java.util.HashMap;

	public class EasyVolley extends EasyVolleyManager {
	    private static EasyVolley instance;

	    public static EasyVolley getInstance(Activity activity) {
		if(instance == null){
		    instance = new EasyVolley(activity);
		}
		return instance;
	    }

	    private EasyVolley(Activity activity){
			super(activity);
	    }


	   @Override
	  protected HashMap<String, String> defaultHeaders() {
		//I didn't have any
	   return null;
	}

	  @Override
	  protected EasyVolleyLoading getLoading(Context context) {
		//I didn't have any
	      return null;
	  }
	}

## Sending Requests
### GET:
     EasyVolley.getInstance(Context context).GET(final String tag, final Context context, String url, final EasyVolleyWorks easyVolleyWorks, final boolean hasLoading, final HashMap<String, String> headers);
     
#### tag
Is used to identify the request on easyVolleyWorks to perform action on preExecute or postExecute or onFailure
#### easyVolleyWorks
Is an interface that should be implemented by the class, who needs to have a network request in it
#### hasLoading
Is a boolean, indicates that this request should show a loading view on screen or not
#### headers
Is optional, if you need to use a customized header for this specific request, here it is
#### parameters?
To send parameter by GET request, you can send them by appending them to url. 
URL?parameter1=value1&parameter2=value2
### POST:
    EasyVolley.getInstance(Context context).POST(final String tag, final Context context, String url, final HashMap<String, String> parameters, final EasyVolleyWorks easyVolleyWorks, final boolean hasLoading, final HashMap<String, String> headers);
#### parameters
Is an optional hashmap, that takes two strings for key and value

### Multipart:
    EasyVolley.getInstance(Context context).MultiPart(final String tag, final Context context, String url, final HashMap<String, String> parameters, final HashMap<String, File> files, final EasyVolleyWorks easyVolleyWorks, final boolean hasLoading, final HashMap<String, String> headers);
    
#### files
Is an optional hashmap, that takes a string for key and a file for value

## Handling Responses
Any class that wants to send request to server should implement **EasyVolleyWorks** interface, so it have to Override:

      void preExecute(String tag);

      void postExecute(String tag, String response);

      void onFailure(String tag, VolleyError error);

methods, you should decide which request's response you are getting here by **tag** .
