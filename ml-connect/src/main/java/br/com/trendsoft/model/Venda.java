package br.com.trendsoft.model;

import java.util.ArrayList;
import java.util.Set;

import com.google.gson.JsonObject;

public class Venda {

	private Long id;
	private String status;
	private String date_created;
	private String date_closed;
	private String last_updated;
	private double total_amount;
	private Long shipping_id;
	private Long buyer_id;
	private ArrayList<String> lstVendasId;
	
	private ArrayList<Pagamento> pagamento;
	private ArrayList<VendaItens> vendaItens;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate_created() {
		return date_created;
	}

	public void setDate_created(String date_created) {
		this.date_created = date_created;
	}

	public String getDate_closed() {
		return date_closed;
	}

	public void setDate_closed(String date_closed) {
		this.date_closed = date_closed;
	}

	public String getLast_updated() {
		return last_updated;
	}

	public void setLast_updated(String last_updated) {
		this.last_updated = last_updated;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	public Long getShipping_id() {
		return shipping_id;
	}

	public void setShipping_id(Long shipping_id) {
		this.shipping_id = shipping_id;
	}

	public Long getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(Long buyer_id) {
		this.buyer_id = buyer_id;
	}

	public ArrayList<String> getLstVendasId() {
		return lstVendasId;
	}

	public void setLstVendasId(ArrayList<String> lstVendasId) {
		this.lstVendasId = lstVendasId;
	}

	public ArrayList<Pagamento> getPagamento() {
		return pagamento;
	}

	public void setPagamento(ArrayList<Pagamento> pagamento) {
		this.pagamento = pagamento;
	}

	public ArrayList<VendaItens> getVendaItens() {
		return vendaItens;
	}

	public void setVendaItens(ArrayList<VendaItens> vendaItens) {
		this.vendaItens = vendaItens;
	}
	
	
}
