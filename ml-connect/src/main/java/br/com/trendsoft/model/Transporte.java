package br.com.trendsoft.model;

public class Transporte {

	private Long id;
	private String status;
	private String date_ready_to_ship;
	private String date_shipped;
	private String date_delivered;
	private String date_not_delivered;
	private String date_returned;
	private String date_cancelled;
	private String date_first_visit;
	private String tracking_number;
	private String tracking_method;
	private double latitude;
	private double longitude;
	private double list_cost;
	private String estimated_delivery;
	
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
	public String getDate_ready_to_ship() {
		return date_ready_to_ship;
	}
	public void setDate_ready_to_ship(String date_ready_to_ship) {
		this.date_ready_to_ship = date_ready_to_ship;
	}
	public String getDate_shipped() {
		return date_shipped;
	}
	public void setDate_shipped(String date_shipped) {
		this.date_shipped = date_shipped;
	}
	public String getDate_delivered() {
		return date_delivered;
	}
	public void setDate_delivered(String date_delivered) {
		this.date_delivered = date_delivered;
	}
	public String getDate_not_delivered() {
		return date_not_delivered;
	}
	public void setDate_not_delivered(String date_not_delivered) {
		this.date_not_delivered = date_not_delivered;
	}
	public String getDate_returned() {
		return date_returned;
	}
	public void setDate_returned(String date_returned) {
		this.date_returned = date_returned;
	}
	public String getDate_cancelled() {
		return date_cancelled;
	}
	public void setDate_cancelled(String date_cancelled) {
		this.date_cancelled = date_cancelled;
	}
	public String getDate_first_visit() {
		return date_first_visit;
	}
	public void setDate_first_visit(String date_first_visit) {
		this.date_first_visit = date_first_visit;
	}
	public String getTracking_number() {
		return tracking_number;
	}
	public void setTracking_number(String tracking_number) {
		this.tracking_number = tracking_number;
	}
	public String getTracking_method() {
		return tracking_method;
	}
	public void setTracking_method(String tracking_method) {
		this.tracking_method = tracking_method;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getList_cost() {
		return list_cost;
	}
	public void setList_cost(double list_cost) {
		this.list_cost = list_cost;
	}
	public String getEstimated_delivery() {
		return estimated_delivery;
	}
	public void setEstimated_delivery(String estimated_delivery) {
		this.estimated_delivery = estimated_delivery;
	}
	
}
