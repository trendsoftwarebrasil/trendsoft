package br.com.trendsoft.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.trendsoft.model.Venda;

@ManagedBean(name="dtBasicView")
@ViewScoped
public class BasicView implements Serializable {
     
    private List<Venda> vendas;
     
    @ManagedProperty("#{carService}")
    private CarService service;
 
    @PostConstruct
    public void init() {
        vendas = service.createCars(10);
    }
     
    public List<Venda> getCars() {
        return vendas;
    }
 
    public void setService(CarService service) {
        this.service = service;
    }
}
