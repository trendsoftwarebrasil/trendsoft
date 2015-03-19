package br.com.trendsoft.model;

public class VendaItens {

	private Long orders_id;
	private String product_id;
	private int quantity;
	private double unit_price;
	private String currency_id;
	
	public Long getOrders_id() {
		return orders_id;
	}
	public void setOrders_id(Long orders_id) {
		this.orders_id = orders_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}
	public String getCurrency_id() {
		return currency_id;
	}
	public void setCurrency_id(String currency_id) {
		this.currency_id = currency_id;
	}
	
	
}
