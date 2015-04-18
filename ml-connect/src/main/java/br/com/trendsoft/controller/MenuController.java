package br.com.trendsoft.controller;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.springframework.stereotype.Component;

import br.com.trendsoft.exception.DaoException;
import br.com.trendsoft.service.ClienteService;

@Component
@ManagedBean
@SessionScoped
public class MenuController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String PATH_CONTEUDO = "/paginas/view/";
	
	private String content;
	
	@Resource
	private ClienteService clienteService;
	
	public MenuController(){
		
		
		this.content = "dashboard";
		
	}
	
	@PostConstruct
	public void init(){
		
		try {
			clienteService.getTodosClientes();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
	
	
	public void atualizaConteudo(ActionEvent event){

		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		String content = params.get("content");
		
		setContent(content);

	}


	public ClienteService getClienteService() {
		return clienteService;
	}


	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	
	

}
