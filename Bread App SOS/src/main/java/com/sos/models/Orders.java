package com.sos.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="Orders")
public class Orders {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	private String name;
	
	private String des;
	
	private String price;
	
	private String stocks;
        
        private String customer;
        
        private String totalprice;
        
        private String quantity;
        
        private String location;
        
        private String phone;
         
        private String status;
	
	private String email;
	
	private String password;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
        
        public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}
        
        public String getTotal() {
		return totalprice;
	}

	public void setTotal(String totalprice) {
		this.totalprice = totalprice;
	}
        
        public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
        public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
        public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
        
         public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
	
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getStocks() {
		return stocks;
	}

	public void setStocks(String stocks) {
		this.stocks = stocks;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Orders [id=" + id + ", ProductName=" + name + ", Customer=" + customer + ", Quantity=" + quantity + ", TotalPrice="
				+ totalprice + ", Address="
				+ location + ", Phone="
				+ phone + ", Status="
				+ status + "]";
	}

	
}
