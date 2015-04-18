package br.com.trendsoft.dao.impl;

import org.springframework.stereotype.Repository;

import br.com.trendsoft.dao.ClienteDao;
import br.com.trendsoft.model.Cliente;

@Repository
public class ClienteDaoImpl extends DaoCrudImpJpa<Cliente> implements ClienteDao{

}
