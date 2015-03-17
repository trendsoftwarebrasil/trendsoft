package br.com.trendsoft.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.mercadolibre.sdk.AuthorizationFailure;
import com.mercadolibre.sdk.Meli;
import com.mercadolibre.sdk.MeliException;
import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;

@ManagedBean
@SessionScoped
public class Bean {
	private String word;

	private String anagram;

	String redirectUrl;
	
	String code = null;
	String token;
	String refrashToken;

	@PostConstruct
	public void init() {

		// Meli.apiUrl = "https://api.mercadolibre.com";

		Meli m = new Meli(4013368235398167L,
				"fcn1Xo99nYX3vHf7q4MRFA7K6Q7HeCm5", null,
				"TG-5507725ae4b054b3f45822f2-146216892");

		code = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("code");

		if (!(code == null)) {

			try {
				// m.authorize(redirectUrl, "http://localhost:8080/ml-connect");
				m.authorize(code, "http://localhost:8080/ml-connect");
				token = m.getAccessToken();
				refrashToken = m.getRefreshToken();
				
				FluentStringsMap params = new FluentStringsMap();
				params.add("access_token", m.getAccessToken());
				params.add("seller", "146216892");
				Response r = m.get("/orders/search/recent",params);
				String resp = r.getResponseBody();
				
				System.out.println();

			} catch (AuthorizationFailure e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MeliException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			redirectUrl = m.getAuthUrl("http://localhost:8080/ml-connect");
		}

		System.out.println();

		// String refreshToken = m.getRefreshToken();

		// token = m.getAccessToken();

		// String code = origRequest.getRequestURI();
		/*
		 * try { //m.authorize(redirectUrl, "http://localhost:8080/ml-connect");
		 * m.authorize("TG-55047bf4e4b005d8524cb2c5-146216892",
		 * "http://localhost:8080/ml-connect"); } catch (AuthorizationFailure e)
		 * { // TODO Auto-generated catch block e.printStackTrace(); } String
		 * token = m.getAccessToken();
		 */

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

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
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