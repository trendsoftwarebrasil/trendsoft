package br.com.trendsoft.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mercadolibre.sdk.AuthorizationFailure;
import com.mercadolibre.sdk.Meli;
import com.mercadolibre.sdk.MeliException;
import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;

@ManagedBean
@ViewScoped
public class Bean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4497743154677441756L;

	private String word;

	private String anagram;
	
	String code = null;
	String token;
	String refrashToken;

	@PostConstruct
	public void init() {

		// Meli.apiUrl = "https://api.mercadolibre.com";

		Meli m = new Meli(4013368235398167L,
				"fcn1Xo99nYX3vHf7q4MRFA7K6Q7HeCm5");
		
		code = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("code");

		try{
			if (code != null) {
				try {
					m.authorize(code, "http://localhost:8080/ml-connect");
					token = m.getAccessToken();
					refrashToken = m.getRefreshToken();

					FluentStringsMap params = new FluentStringsMap();
					params.add("access_token", m.getAccessToken());
					params.add("seller", "146216892");
					Response r = m.get("/orders/search/recent",params);
					JsonParser parser = new JsonParser();
					JsonElement element =  parser.parse(r.getResponseBody());
					JsonObject jsonObject = element.getAsJsonObject();
					JsonArray vendas = (JsonArray) jsonObject.get("results");
					Iterator<JsonElement> iterator = vendas.iterator();
					while (iterator.hasNext()){
						JsonObject venda =  iterator.next().getAsJsonObject();
						System.out.println(venda.get("id"));
					}
				 
				
					System.out.println(r.getResponseBody());
					
		


					System.out.println();

				} catch (AuthorizationFailure e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MeliException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

			} else 		
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect(m.getAuthUrl("http://localhost:8080/ml-connect"));
		}
		catch(IOException e){
			e.printStackTrace();
		}


	}

	public void generateAnagram() {
		Random rand = new Random();

		List<Character> characters = new ArrayList<Character>();
		for (char c : word.toCharArray()) {
			characters.add(c);
		}
		StringBuilder output = new StringBuilder(word.length());
		while (characters.size() != 0) {
			int randPicker = (int) (Math.random() * characters.size());
			output.append(characters.remove(randPicker));
		}
		this.anagram = output.toString();
	}

	public String getAnagram() {
		return anagram;
	}

	public void setAnagram(String anagram) {
		this.anagram = anagram;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
}