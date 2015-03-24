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

import br.com.trendsoft.model.Cliente;
import br.com.trendsoft.model.Pagamento;
import br.com.trendsoft.model.Transporte;
import br.com.trendsoft.model.Venda;
import br.com.trendsoft.model.VendaItens;

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

	private String word = "helo world";
	private String content;
	private String anagram;

	String code = null;
	String token;
	String refrashToken;

	@PostConstruct
	public void init() {
		
		this.content = "dashboard";

		// Meli.apiUrl = "https://api.mercadolibre.com";

		Meli m = new Meli(4013368235398167L, "fcn1Xo99nYX3vHf7q4MRFA7K6Q7HeCm5");

		code = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("code");

		try {
			if (code != null) {

				try {
					m.authorize(code, "http://localhost:8080/ml-connect");

					// 1º - Vendas IDs

					// Pega os últimos 50 IDs e insere na base os novos IDs
					// (Tabela: orders_id).
					/*
					 * FluentStringsMap params = new FluentStringsMap();
					 * params.add("access_token", m.getAccessToken());
					 * params.add("seller", "146216892"); params.add("sort",
					 * "date_desc");
					 * 
					 * Response r = m.get("/orders/search",params);
					 * 
					 * JsonParser parser = new JsonParser(); JsonElement element
					 * = parser.parse(r.getResponseBody()); JsonObject
					 * jsonObject = element.getAsJsonObject(); JsonArray vendas
					 * = (JsonArray) jsonObject.get("results");
					 * Iterator<JsonElement> iterator = vendas.iterator();
					 * 
					 * ArrayList<String> lstVendasId = new ArrayList<String>();
					 * while (iterator.hasNext()){ JsonObject venda =
					 * iterator.next().getAsJsonObject();
					 * lstVendasId.add(venda.get("id").toString());
					 * //System.out.println(venda.get("id")); }
					 * 
					 * Venda objVenda = new Venda();
					 * objVendas.setLstVendasId(lstVendasId);
					 */

					// 2º - Seleciona na Base as ordens de venda a serem
					// inseridas ou atualizadas:

					// SELECT oi.id
					// FROM orders_id oi
					// LEFT JOIN orders o ON (oi.id = o.id)
					// LEFT JOIN shipping s ON (o.shipping_id = s.id)
					// WHERE o.id is null
					// OR (
					// (o.status = 'paid')
					// AND (
					// s.id is null OR (s.status <> 'delivered')
					// )
					// )
					// OR (o.status <> 'paid')

					// Carrega as tabelas:
					// - order
					// - buyer
					// - payment
					// - order_items

					// ID de exemplo!!!
					// futuramente deverá varrer o resultado do select acima e
					// executar o código abaixo para cada ID.
					String id = "936495018";

					FluentStringsMap params = new FluentStringsMap();
					params.add("access_token", m.getAccessToken());
					params.add("seller", "146216892");
					Response r = m.get("/orders/" + id, params);

					// Dados para a tabela Order
					JsonParser parser = new JsonParser();
					JsonElement element = parser.parse(r.getResponseBody());
					JsonObject joOrder = element.getAsJsonObject();

					Venda objVenda = new Venda();

					objVenda.setId(joOrder.get("id").getAsLong());
					objVenda.setStatus(joOrder.get("status").toString());
					objVenda.setDate_created(joOrder.get("date_created")
							.toString());
					objVenda.setDate_closed(joOrder.get("date_closed")
							.toString());
					objVenda.setLast_updated(joOrder.get("last_updated")
							.toString());
					objVenda.setTotal_amount(joOrder.get("total_amount")
							.getAsDouble());
					objVenda.setShipping_id(joOrder.getAsJsonObject("shipping")
							.get("id").getAsLong());
					objVenda.setBuyer_id(joOrder.getAsJsonObject("buyer")
							.get("id").getAsLong());

					// Dados para a tabela Buyer
					JsonObject joBuyer = joOrder.getAsJsonObject("buyer");
					Cliente objCliente = new Cliente();

					objCliente.setId(joBuyer.get("id").getAsLong());
					objCliente.setNickname(joBuyer.get("nickname")
							.getAsString());
					objCliente.setEmail(joBuyer.get("email").getAsString());
					objCliente.setArea_code(joBuyer.getAsJsonObject("phone")
							.get("area_code").getAsString());
					objCliente.setNumber(joBuyer.getAsJsonObject("phone")
							.get("number").getAsString());
					if (!joBuyer.getAsJsonObject("phone").get("extension")
							.isJsonNull())
						objCliente.setExtension(joBuyer
								.getAsJsonObject("phone").get("extension")
								.getAsString());
					objCliente.setFirst_name(joBuyer.get("first_name")
							.getAsString());
					objCliente.setLast_name(joBuyer.get("last_name")
							.getAsString());
					objCliente.setDoc_type(joBuyer
							.getAsJsonObject("billing_info").get("doc_type")
							.getAsString());
					objCliente.setDoc_number(joBuyer
							.getAsJsonObject("billing_info").get("doc_number")
							.getAsString());

					// Dados para a tabela Payment
					JsonArray pagamentos = (JsonArray) joOrder.get("payments");
					Iterator<JsonElement> iteratorPaym = pagamentos.iterator();

					ArrayList<Pagamento> lstPagamentos = new ArrayList<Pagamento>();
					Pagamento objPagamento;

					while (iteratorPaym.hasNext()) {
						objPagamento = new Pagamento();

						JsonObject pagamento = iteratorPaym.next()
								.getAsJsonObject();

						objPagamento.setId(pagamento.get("id").getAsLong());
						objPagamento.setOrder_id(pagamento.get("order_id")
								.getAsLong());
						objPagamento.setPayment_method_id(pagamento.get(
								"payment_method_id").getAsString());
						objPagamento.setCurrency_id(pagamento
								.get("currency_id").getAsString());
						objPagamento.setInstallments(pagamento.get(
								"installments").getAsInt());
						objPagamento.setPayment_type(pagamento.get(
								"payment_type").getAsString());
						objPagamento.setStatus(pagamento.get("status")
								.getAsString());
						objPagamento.setStatus_code(pagamento
								.get("status_code").getAsString());
						objPagamento.setStatus_detail(pagamento.get(
								"status_detail").getAsString());
						objPagamento.setTransaction_amount(pagamento.get(
								"transaction_amount").getAsDouble());
						objPagamento.setTotal_paid_amount(pagamento.get(
								"total_paid_amount").getAsDouble());
						objPagamento.setDate_created(pagamento.get(
								"date_created").getAsString());
						objPagamento.setDate_last_modified(pagamento.get(
								"date_last_modified").getAsString());

						lstPagamentos.add(objPagamento);
						// System.out.println(pagamento.get("id"));
					}

					objVenda.setPagamento(lstPagamentos);

					// Dados para a tabela order_items
					JsonArray vendaItens = (JsonArray) joOrder
							.get("order_items");
					Iterator<JsonElement> iteratorItens = vendaItens.iterator();

					ArrayList<VendaItens> lstVendaItens = new ArrayList<VendaItens>();
					VendaItens objVendaItens = new VendaItens();

					while (iteratorItens.hasNext()) {
						objVendaItens = new VendaItens();

						JsonObject item = iteratorItens.next()
								.getAsJsonObject();

						objVendaItens.setOrders_id(objVenda.getId());
						objVendaItens.setProduct_id(item
								.getAsJsonObject("item").get("id")
								.getAsString());
						objVendaItens.setQuantity(item.get("quantity")
								.getAsInt());
						objVendaItens.setUnit_price(item.get("unit_price")
								.getAsDouble());
						objVendaItens.setCurrency_id(item.get("currency_id")
								.getAsString());

						lstVendaItens.add(objVendaItens);
						// System.out.println(pagamento.get("id"));
					}

					objVenda.setPagamento(lstPagamentos);

					// Carga da tabela Shipping - importando dados do Mercado
					// Livre.
					// ID de exemplo!!!
					// futuramente deverá varrer o resultado do select acima e
					// executar o código abaixo para cada ID.
					String idShip = "21404711983";

					FluentStringsMap params2 = new FluentStringsMap();
					params2.add("access_token", m.getAccessToken());
					params2.add("seller", "146216892");
					Response r2 = m.get("/shipments/" + idShip, params2);

					// Dados para a tabela Shippment
					JsonParser parser2 = new JsonParser();
					JsonElement element2 = parser2.parse(r2.getResponseBody());
					JsonObject joShip = element2.getAsJsonObject();

					Transporte objTransporte = new Transporte();

					objTransporte.setId(joShip.get("id").getAsLong());
					objTransporte.setStatus(joShip.get("status").getAsString());
					if (!joShip.getAsJsonObject("status_history")
							.get("date_ready_to_ship").isJsonNull())
						objTransporte.setDate_ready_to_ship(joShip
								.getAsJsonObject("status_history")
								.get("date_ready_to_ship").getAsString());
					if (!joShip.getAsJsonObject("status_history")
							.get("date_shipped").isJsonNull())
						objTransporte.setDate_shipped(joShip
								.getAsJsonObject("status_history")
								.get("date_shipped").getAsString());
					if (!joShip.getAsJsonObject("status_history")
							.get("date_delivered").isJsonNull())
						objTransporte.setDate_delivered(joShip
								.getAsJsonObject("status_history")
								.get("date_delivered").getAsString());
					if (!joShip.getAsJsonObject("status_history")
							.get("date_not_delivered").isJsonNull())
						objTransporte.setDate_not_delivered(joShip
								.getAsJsonObject("status_history")
								.get("date_not_delivered").getAsString());
					if (!joShip.getAsJsonObject("status_history")
							.get("date_returned").isJsonNull())
						objTransporte.setDate_returned(joShip
								.getAsJsonObject("status_history")
								.get("date_returned").getAsString());
					if (!joShip.getAsJsonObject("status_history")
							.get("date_cancelled").isJsonNull())
						objTransporte.setDate_cancelled(joShip
								.getAsJsonObject("status_history")
								.get("date_cancelled").getAsString());
					if (!joShip.getAsJsonObject("status_history")
							.get("date_first_visit").isJsonNull())
						objTransporte.setDate_first_visit(joShip
								.getAsJsonObject("status_history")
								.get("date_first_visit").getAsString());
					if (!joShip.get("tracking_number").isJsonNull())
						objTransporte.setTracking_number(joShip.get(
								"tracking_number").getAsString());
					if (!joShip.get("tracking_method").isJsonNull())
						objTransporte.setTracking_method(joShip.get(
								"tracking_method").getAsString());
					if (!joShip.getAsJsonObject("receiver_address")
							.get("latitude").isJsonNull())
						objTransporte.setLatitude(joShip
								.getAsJsonObject("receiver_address")
								.get("latitude").getAsDouble());
					if (!joShip.getAsJsonObject("receiver_address")
							.get("longitude").isJsonNull())
						objTransporte.setLongitude(joShip
								.getAsJsonObject("receiver_address")
								.get("longitude").getAsDouble());
					if (!joShip.getAsJsonObject("shipping_option")
							.get("list_cost").isJsonNull())
						objTransporte.setList_cost(joShip
								.getAsJsonObject("shipping_option")
								.get("list_cost").getAsDouble());
					if (!joShip.getAsJsonObject("shipping_option")
							.getAsJsonObject("estimated_delivery").isJsonNull())
						objTransporte.setEstimated_delivery(joShip
								.getAsJsonObject("shipping_option")
								.getAsJsonObject("estimated_delivery")
								.get("date").getAsString());

				} catch (AuthorizationFailure e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MeliException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} 
//			else 
//				FacesContext
//						.getCurrentInstance()
//						.getExternalContext()
//						.redirect(
//								m.getAuthUrl("http://localhost:8080/ml-connect"));
		} catch (IOException e) {
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}