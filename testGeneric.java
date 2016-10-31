package appleiTunes;
import org.testng.annotations.Test;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;


import java.io.IOException;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.databind.*;

import abc.apiCall;

import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class testGeneric {
	//verify that error message is thrown when term is not there in request
	@Test
	public void noTerm() throws IOException, URISyntaxException, JSONException {
		Map<String, Object> params = new HashMap<String, Object>();
		apiCall apiCall = new apiCall();
		HttpResponse httpResponse = apiCall.calliTunesAPI(params);
		String fullResponse = apiCall.parseResponse(httpResponse);
		
		JSONObject obj = new JSONObject(fullResponse);
		
		Map<String,String> map = new HashMap<String,String>();

		ObjectMapper mapper = new ObjectMapper();

		Map<?, ?> response= mapper.readValue(fullResponse, map.getClass());
		//Check if proper error message is being thrown indicating that term is mandatory
	String errorMessage = (String) response.get("errorMessage");
	Assert.assertTrue(errorMessage.contains("Invalid value"));
		
	}
	@Test
	public void wrongParams() throws IOException, URISyntaxException, JSONException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("txt", "Shakira");
		apiCall apiCall = new apiCall();
		HttpResponse httpResponse = apiCall.calliTunesAPI(params);
		String fullResponse = apiCall.parseResponse(httpResponse);
		
		JSONObject obj = new JSONObject(fullResponse);
		
		Map<String,String> map = new HashMap<String,String>();

		ObjectMapper mapper = new ObjectMapper();

		Map<?, ?> response= mapper.readValue(fullResponse, map.getClass());
		//Check if proper error message is being thrown indicating that wrong parameters are passed
	String errorMessage = (String) response.get("errorMessage");
	Assert.assertTrue(errorMessage.contains("Invalid value"));
		
	}
	
	@Test
	public void chkMaxLimit() throws IOException, URISyntaxException, JSONException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("term", "Shakira");
		params.put("country", "US");
		params.put("limit", "230");
		apiCall apiCall = new apiCall();
		
		HttpResponse httpResponse = apiCall.calliTunesAPI(params);
		String fullResponse = apiCall.parseResponse(httpResponse);
		
		JSONObject obj = new JSONObject(fullResponse);
		
		Map<String,String> map = new HashMap<String,String>();

		ObjectMapper mapper = new ObjectMapper();

		Map<?, ?> response= mapper.readValue(fullResponse, map.getClass());

		Assert.assertTrue(response.containsKey("resultCount"));
		Assert.assertTrue(response.containsKey("results"));
		double resultCount = Double.valueOf(String.valueOf(response.get("resultCount")));
		Assert.assertEquals((double) 200, resultCount);
		Assert.assertEquals(resultCount, (double) ((Collection) response.get("results")).size());
	}

	@Test
	public void chkDefaultLimit() throws IOException, URISyntaxException, JSONException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("term", "Shakira");
		
		apiCall apiCall = new apiCall();
		
		HttpResponse httpResponse = apiCall.calliTunesAPI(params);
		String fullResponse = apiCall.parseResponse(httpResponse);

		JSONObject obj = new JSONObject(fullResponse);
		
		Map<String,String> map = new HashMap<String,String>();

		ObjectMapper mapper = new ObjectMapper();

		Map<?, ?> response= mapper.readValue(fullResponse, map.getClass());

		Assert.assertTrue(response.containsKey("resultCount"));
		Assert.assertTrue(response.containsKey("results"));
		double resultCount = Double.valueOf(String.valueOf(response.get("resultCount")));
		Assert.assertEquals((double) 50, resultCount);
		Assert.assertEquals(resultCount, (double) ((Collection) response.get("results")).size());
		ArrayList res = (ArrayList) response.get("results");
		//Check that default country for all rows 
		for(int i=0;i<resultCount;i++)
		{
		
		HashMap individualEntry = (HashMap) res.get(i);
		//Assert that default country is US when no country is entered
		Assert.assertEquals( individualEntry.get("country"),"USA");
		}
		
	}

}
