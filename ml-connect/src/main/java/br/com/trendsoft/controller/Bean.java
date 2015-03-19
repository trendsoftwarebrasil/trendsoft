package br.com.trendsoft.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.trendsoft.model.Venda;

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

//					1º - Vendas IDs
					
//					Pega os últimos 50 IDs e insere na base os novos IDs (Tabela: orders_id).
					/*
					FluentStringsMap params = new FluentStringsMap();
					params.add("access_token", m.getAccessToken());
					params.add("seller", "146216892");
					params.add("sort", "date_desc");
					
					Response r = m.get("/orders/search",params);
					
					JsonParser parser = new JsonParser();
					JsonElement element =  parser.parse(r.getResponseBody());
					JsonObject jsonObject = element.getAsJsonObject();
					JsonArray vendas = (JsonArray) jsonObject.get("results");
					Iterator<JsonElement> iterator = vendas.iterator();

					ArrayList<String> lstVendasId = new ArrayList<String>();
					while (iterator.hasNext()){
						JsonObject venda =  iterator.next().getAsJsonObject();
						lstVendasId.add(venda.get("id").toString());
						//System.out.println(venda.get("id"));
					}
					
					Venda objVenda = new Venda();
					objVendas.setLstVendasId(lstVendasId);  */
				
//					2º - Seleciona na Base as ordens de venda a serem inseridas ou atualizadas:
						
//					SELECT oi.id
//				    FROM orders_id oi
//				        LEFT JOIN orders o ON (oi.id = o.id)
//				        LEFT JOIN shipping s ON (o.shipping_id = s.id)
//				    WHERE o.id is null
//				           OR (
//								(o.status = 'paid')
//								AND ( 
//									s.id is null OR (s.status <> 'delivered')
//								)
//							)
//							OR (o.status <> 'paid')

//					Carrega as tabelas: 
//					- order
//					- buyer
//					- payment
//					- order_items
				
//					ID de exemplo!!!  
//					futuramente deverá varrer o resultado do select acima e executar o código abaixo para cada ID.
					String id = "936495018";
					
					FluentStringsMap params = new FluentStringsMap();
					params.add("access_token", m.getAccessToken());
					params.add("seller", "146216892");
					Response r = m.get("/orders/" + id, params);
					
					JsonParser parser = new JsonParser();
					JsonElement element =  parser.parse(r.getResponseBody());
					JsonObject jsonObject = element.getAsJsonObject();
					
					Venda objVendas = new Venda();
					
					objVendas.setId(jsonObject.get("id").getAsLong());
					objVendas.setStatus(jsonObject.get("status").toString());
					objVendas.setDate_created(jsonObject.get("date_created").toString());
					objVendas.setDate_closed(jsonObject.get("date_closed").toString());
					objVendas.setLast_updated(jsonObject.get("last_updated").toString());
					objVendas.setTotal_amount(jsonObject.get("total_amount").getAsDouble());
					objVendas.setShipping_id(jsonObject.getAsJsonObject("shipping"));
					objVendas.setBuyer_id(jsonObject.getAsJsonObject("buyer"));
					
					System.out.println(objVendas);
					
					
					
					
					
					
					
					
					
					
					
					System.out.println(objVendas.getLstVendasId());
					
		


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