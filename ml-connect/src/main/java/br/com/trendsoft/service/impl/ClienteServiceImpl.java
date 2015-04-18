package br.com.trendsoft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.trendsoft.dao.ClienteDao;
import br.com.trendsoft.exception.DaoException;
import br.com.trendsoft.model.Cliente;
import br.com.trendsoft.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Resource
	public ClienteDao clienteDao;
	
	@Override
	public List<Cliente> getTodosClientes() throws DaoException {
		
		return clienteDao.recuperaTodos();
	}

	public ClienteDao getClienteDao() {
		return clienteDao;
	}

	public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}
	
	

}
