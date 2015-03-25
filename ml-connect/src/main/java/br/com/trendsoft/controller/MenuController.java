package br.com.trendsoft.controller;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
@SessionScoped
public class MenuController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String PATH_CONTEUDO = "/paginas/view/";
	
	private String content;
	
	public MenuController(){
		
		this.content = "dashboard";
		
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
	

}
