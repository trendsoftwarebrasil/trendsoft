package br.com.trendsoft.model;

public class Pagamento {

	private Long id;
	private Long order_id;
	private String payment_method_id;
	private String currency_id;
	private int installments;
	private String payment_type;
	private String status;
	private String status_code;
	private String status_detail;
	private double transaction_amount;
	private double total_paid_amount;
	private double shipping_cost;
	private double net_received_amount;
	private double installment_amount;
	private String date_created;
	private String date_last_modified;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	public String getPayment_method_id() {
		return payment_method_id;
	}
	public void setPayment_method_id(String payment_method_id) {
		this.payment_method_id = payment_method_id;
	}
	public String getCurrency_id() {
		return currency_id;
	}
	public void setCurrency_id(String currency_id) {
		this.currency_id = currency_id;
	}
	public int getInstallments() {
		return installments;
	}
	public void setInstallments(int installments) {
		this.installments = installments;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus_code() {
		return status_code;
	}
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	public String getStatus_detail() {
		return status_detail;
	}
	public void setStatus_detail(String status_detail) {
		this.status_detail = status_detail;
	}
	public double getTransaction_amount() {
		return transaction_amount;
	}
	public void setTransaction_amount(double transaction_amount) {
		this.transaction_amount = transaction_amount;
	}
	public double getTotal_paid_amount() {
		return total_paid_amount;
	}
	public void setTotal_paid_amount(double total_paid_amount) {
		this.total_paid_amount = total_paid_amount;
	}
	public double getShipping_cost() {
		return shipping_cost;
	}
	public void setShipping_cost(double shipping_cost) {
		this.shipping_cost = shipping_cost;
	}
	public double getNet_received_amount() {
		return net_received_amount;
	}
	public void setNet_received_amount(double net_received_amount) {
		this.net_received_amount = net_received_amount;
	}
	public double getInstallment_amount() {
		return installment_amount;
	}
	public void setInstallment_amount(double installment_amount) {
		this.installment_amount = installment_amount;
	}
	public String getDate_created() {
		return date_created;
	}
	public void setDate_created(String date_created) {
		this.date_created = date_created;
	}
	public String getDate_last_modified() {
		return date_last_modified;
	}
	public void setDate_last_modified(String date_last_modified) {
		this.date_last_modified = date_last_modified;
	}
	
}
