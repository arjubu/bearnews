package com.bu.softwareengineering.bearnews.twittercrawler;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootApplication
public class TwitterCrawlerApplication {

	public static void main(String[] args) throws IOException, URISyntaxException {
		SpringApplication.run(TwitterCrawlerApplication.class, args);
		String bearerToken = "AAAAAAAAAAAAAAAAAAAAAJLKiQEAAAAA362AF3td5GbXmTRaUsYsZ%2BoVqVk%3DBWoa88Ext7FV6FAad7QgqaSpn0ew0d2s4l8muLaylFXxzndyNF";
		if (null != bearerToken) {
			Map<String, String> rules = new HashMap<>();
			rules.put("java", "java");
			//rules.put("dogs has:images", "dog images");
			setupRules(bearerToken, rules);
			connectStream(bearerToken);
		} else {
			System.out.println("Unable to get Bearer Token");
		}
	}


	private static void connectStream(String bearerToken) throws IOException, URISyntaxException {

		HttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(RequestConfig.custom()
						.setCookieSpec(CookieSpecs.STANDARD).build())
				.build();

		URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets/search/stream");

		HttpGet httpGet = new HttpGet(uriBuilder.build());
		httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));

		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (null != entity) {
			BufferedReader reader = new BufferedReader(new InputStreamReader((entity.getContent())));
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				line = reader.readLine();
			}
		}

	}

	private static void setupRules(String bearerToken, Map<String, String> rules) throws IOException, URISyntaxException {
		List<String> existingRules = getRules(bearerToken);
		if (existingRules.size() > 0) {
			deleteRules(bearerToken, existingRules);
		}
		createRules(bearerToken, rules);
	}

	private static void createRules(String bearerToken, Map<String, String> rules) throws URISyntaxException, IOException {
		HttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(RequestConfig.custom()
						.setCookieSpec(CookieSpecs.STANDARD).build())
				.build();

		URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets/search/stream/rules");

		HttpPost httpPost = new HttpPost(uriBuilder.build());
		httpPost.setHeader("Authorization", String.format("Bearer %s", bearerToken));
		httpPost.setHeader("content-type", "application/json");
		StringEntity body = new StringEntity(getFormattedString("{\"add\": [%s]}", rules));
		httpPost.setEntity(body);
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		if (null != entity) {
			System.out.println(EntityUtils.toString(entity, "UTF-8"));
		}
	}

	private static List<String> getRules(String bearerToken) throws URISyntaxException, IOException {
		List<String> rules = new ArrayList<>();
		HttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(RequestConfig.custom()
						.setCookieSpec(CookieSpecs.STANDARD).build())
				.build();

		URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets/search/stream/rules");

		HttpGet httpGet = new HttpGet(uriBuilder.build());
		httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
		httpGet.setHeader("content-type", "application/json");
		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (null != entity) {
			JSONObject json = new JSONObject(EntityUtils.toString(entity, "UTF-8"));
			if (json.length() > 1) {
				JSONArray array = (JSONArray) json.get("data");
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject = (JSONObject) array.get(i);
					rules.add(jsonObject.getString("id"));
				}
			}
		}
		return rules;
	}

	private static void deleteRules(String bearerToken, List<String> existingRules) throws URISyntaxException, IOException {
		HttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(RequestConfig.custom()
						.setCookieSpec(CookieSpecs.STANDARD).build())
				.build();

		URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets/search/stream/rules");

		HttpPost httpPost = new HttpPost(uriBuilder.build());
		httpPost.setHeader("Authorization", String.format("Bearer %s", bearerToken));
		httpPost.setHeader("content-type", "application/json");
		StringEntity body = new StringEntity(getFormattedString("{ \"delete\": { \"ids\": [%s]}}", existingRules));
		httpPost.setEntity(body);
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		if (null != entity) {
			System.out.println(EntityUtils.toString(entity, "UTF-8"));
		}
	}

	private static String getFormattedString(String string, List<String> ids) {
		StringBuilder sb = new StringBuilder();
		if (ids.size() == 1) {
			return String.format(string, "\"" + ids.get(0) + "\"");
		} else {
			for (String id : ids) {
				sb.append("\"" + id + "\"" + ",");
			}
			String result = sb.toString();
			return String.format(string, result.substring(0, result.length() - 1));
		}
	}

	private static String getFormattedString(String string, Map<String, String> rules) {
		StringBuilder sb = new StringBuilder();
		if (rules.size() == 1) {
			String key = rules.keySet().iterator().next();
			return String.format(string, "{\"value\": \"" + key + "\", \"tag\": \"" + rules.get(key) + "\"}");
		} else {
			for (Map.Entry<String, String> entry : rules.entrySet()) {
				String value = entry.getKey();
				String tag = entry.getValue();
				sb.append("{\"value\": \"" + value + "\", \"tag\": \"" + tag + "\"}" + ",");
			}
			String result = sb.toString();
			return String.format(string, result.substring(0, result.length() - 1));
		}
	}


}
