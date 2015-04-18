package br.com.trendsoft.service;

import java.util.List;

import br.com.trendsoft.exception.DaoException;
import br.com.trendsoft.model.Cliente;


public interface ClienteService {
	
	public List<Cliente> getTodosClientes() throws DaoException;

}
