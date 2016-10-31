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

public class testMedia {
 
	@Test(dataProvider="data-provider",dataProviderClass=readData.class)
	public void testMedia(String term, String country,String media,String limit,String testcase) throws URISyntaxException, IOException, JSONException {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("term", term);
		params.put("media", media);
		
		if(!country.equals("null"))
			params.put("country", country);
		else if(!limit.equals("null"))
			params.put("limit", limit);
	
		
		apiCall apiCall = new apiCall();
		HttpResponse httpResponse = apiCall.calliTunesAPI(params);
		String fullResponse = apiCall.parseResponse(httpResponse);
		
		JSONObject obj = new JSONObject(fullResponse);
		
		Map<String,String> map = new HashMap<String,String>();

		ObjectMapper mapper = new ObjectMapper();

		Map<?, ?> response= mapper.readValue(fullResponse, map.getClass());
		


		if(testcase.equalsIgnoreCase("Positive"))
		{
			
		Assert.assertTrue(response.containsKey("resultCount"));
		Assert.assertTrue(response.containsKey("results"));
		double resultCount = Double.valueOf(String.valueOf(response.get("resultCount")));
		Assert.assertEquals(resultCount, (double) ((Collection) response.get("results")).size());
		if(resultCount!=0)
		{
			//chk that the search term is present in the search results			
		Assert.assertTrue(fullResponse.contains(term));
		ArrayList a = (ArrayList) response.get("results");
		HashMap h = (HashMap) a.get(0);
		//Check correct media type is present in the search results
		if(media.equals("movie"))
		Assert.assertEquals(h.get("kind"),"feature-movie");
		else if(media.equals("podcast"))
			Assert.assertEquals(h.get("kind"),"podcast");
		else if(media.equals("music"))
			Assert.assertEquals( h.get("kind"),"song");
		else if(media.equals("tvShow"))
			Assert.assertEquals(h.get("kind"),"tv-episode");
		else if(media.equals("musicVideo"))
			Assert.assertEquals(h.get("kind"),"music-video");
		}
		}
		else
		{
			//Check if error message is returned
			String errorMessage = (String) response.get("errorMessage");
			
			Assert.assertTrue(errorMessage.contains("Invalid value(s)"));
		}
		
		
		
	}
	
	
	

	
	

}

