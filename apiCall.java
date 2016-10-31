package abc;
import org.testng.annotations.Test;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.databind.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
public class apiCall {
	
	//build the string for the calling the iTunes API
	public HttpResponse calliTunesAPI(Map<String, ?> params) throws URISyntaxException, IOException {
		
		HttpClient client = HttpClients.createDefault();
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost("itunes.apple.com").setPath("/search");
		for (String s : params.keySet()) {
			builder.setParameter(s, String.valueOf(params.get(s)));
		}

		URI uri = builder.build();
		HttpGet get = new HttpGet(uri);
		
		return client.execute(get);
	}
	
	
	public String parseResponse(HttpResponse httpResponse) throws IOException, JSONException {
			
		Scanner scan = new Scanner(httpResponse.getEntity().getContent());
		String fullRespString = new String();
		while (scan.hasNext())
			fullRespString += scan.nextLine();
		scan.close();
	
		return fullRespString;
	
	
		
	}

}
