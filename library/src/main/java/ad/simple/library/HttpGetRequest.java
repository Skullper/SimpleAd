package ad.simple.library;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by pugman on 01.02.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

/**
 * Class designed for sending simple GET request to get ad url
 */
public class HttpGetRequest extends AsyncTask<String, Void, String>{

	private static final String REQUEST_METHOD = "GET";
	private static final int    TIMEOUT        = 20000;
	private static final String END_POINT      = "https://clicker.ddeapp.com/api/v1/adv/url?";
	private static final String REQUEST_PARAM  = "app=";
	private static final String FIELD_URL      = "url";

	@Override
	protected String doInBackground(String... params){
		String packageName = params[0];
		//Add params to url
		String request = addParams(packageName);
		String url = null;
		String inputLine;
		try{
			//Create a URL object holding our url
			URL myUrl = new URL(request);
			//Create a connection
			HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
			//Set methods and timeouts
			connection.setRequestMethod(REQUEST_METHOD);
			connection.setReadTimeout(TIMEOUT);
			connection.setConnectTimeout(TIMEOUT);
			//Connect to created url
			connection.connect();
			//Start reading result
			InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
			BufferedReader reader = new BufferedReader(streamReader);
			StringBuilder stringBuilder = new StringBuilder();
			//Check if the line we are reading is not null
			while((inputLine = reader.readLine()) != null){
				stringBuilder.append(inputLine);
			}
			//Close our InputStream and Buffered reader
			reader.close();
			streamReader.close();
			//Parse response
			url = getUrl(stringBuilder.toString());
		} catch(MalformedURLException e){
			Log.e("HTTP", "MalformedException: " + e.getMessage());
		} catch(IOException e){
			Log.e("HTTP", "IOException: " + e.getMessage());
		}
		return url;
	}

	private String addParams(String packageName){
		return END_POINT + REQUEST_PARAM + packageName;
	}

	private String getUrl(String response){
		String url = null;
		try{
			JSONObject object = new JSONObject(response);
			url = object.getString(FIELD_URL);
		} catch(JSONException e){
			Log.e("JSON", "ParsingException: " + e.getMessage());
		}
		return url;
	}

}
