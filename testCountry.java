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
import abc.readData;

import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class testCountry {
 
	@Test(dataProvider="data-provider",dataProviderClass=readData.class)
	public void testCountry(String term, String country,String media,String limit,String testcase) throws URISyntaxException, IOException, JSONException {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("term", term);
		params.put("country", country);
		
		if(!media.equals("null"))
			params.put("media", media);
		else if(!limit.equals("null"))
		params.put("limit", limit);
	
		//call the iTunes API
		apiCall apiCall = new apiCall();
		HttpResponse httpResponse = apiCall.calliTunesAPI(params);
		String fullResponse = apiCall.parseResponse(httpResponse);
		
		JSONObject obj = new JSONObject(fullResponse);
		
		Map<String,String> map = new HashMap<String,String>();

		ObjectMapper mapper = new ObjectMapper();

		Map<?, ?> response= mapper.readValue(fullResponse, map.getClass());
		


		if(testcase.equalsIgnoreCase("Positive"))
		{
		//Check if there are any results	
		Assert.assertTrue(response.containsKey("resultCount"));
		Assert.assertTrue(response.containsKey("results"));
		double resultCount = Double.valueOf(String.valueOf(response.get("resultCount")));
		Assert.assertEquals(resultCount, (double) ((Collection) response.get("results")).size());
		if(resultCount!=0)
		{
			//chk that the search term is present in the search results
		Assert.assertTrue(fullResponse.contains(term));
		ArrayList respList = (ArrayList) response.get("results");
		HashMap res = (HashMap) respList.get(0);
		//Check the country names are correct in the search results
		if(country.equals("US"))
		Assert.assertEquals(res.get("country"), "USA");
		else if (country.equals("AU"))
		Assert.assertEquals(res.get("country"), "AUS");
		else if (country.equals("GB"))
			Assert.assertEquals(res.get("country"),"GBR");
		else if (country.equals("DE"))
			Assert.assertEquals(res.get("country"), "DEU");
		}
		}
		else
		{
			//Check if error message is thrown if wrong country code is entered
			String errorMessage = (String) response.get("errorMessage");		
			Assert.assertTrue(errorMessage.contains("Invalid value(s)"));
		}			
		
	}
		

}


