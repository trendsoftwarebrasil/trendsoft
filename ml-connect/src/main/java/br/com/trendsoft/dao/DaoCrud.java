package br.com.trendsoft.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import br.com.trendsoft.exception.DaoException;
import br.com.trendsoft.model.Persistente;

public interface DaoCrud<P extends Persistente> {

	public Long recuperaPorParamsQteReg(String query,
			Map<String, Object> params) throws DaoException;

	public P merge(P persistente) throws DaoException;

	public void gravar(P persistente) throws DaoException;
	
	public void gravar(List<P> listaPersistente) throws DaoException;

	public void alterar(P persistente) throws DaoException;

	public void deletar(P persistente) throws DaoException;
	
	public void excluirTodos() throws DaoException;

	public P recuperaUm(Serializable pk) throws DaoException;

	public List<P> recuperaTodos() throws DaoException;

	public List<P> recuperaTodosOrderBy(String query) throws DaoException;
	
	public List<P> recuperaTodosOrderByCriteria(String orderBy) throws DaoException;

	public List<P> recuperaPorParams(String query, Map<String, Object> params)
			throws DaoException;

	public List<P> recuperaPorParams(String query, Map<String, Object> params,
			int maxResults) throws DaoException;

	public List<P> recuperaPorParams(String query, Map<String, Object> params,
			int maximo, int atual) throws DaoException;

	public List<P> recuperaPorQuery(String query) throws DaoException;

	public Object recuperaUmPorQuery(String query) throws DaoException;

	public P recuperaUmPorParams(String query, Map<String, Object> params)
			throws DaoException;

	public List<P> paginacaoBaseSerializableDataModel(String strQuery,
			Map<String, Object> params) throws DaoException;

	public P recuperaUmPorParamsTrataNull(String query,
			Map<String, Object> params) throws DaoException;

	public Object recuperaUmCampoPorParams(String query,
			Map<String, Object> params) throws DaoException;

	public int executaUpdateOuDelete(String query) throws DaoException;

	public int executaUpdateOuDelete(String query, Map<String, Object> params)
			throws DaoException;

	public List<P> recuperaPorParamsSql(String strQuery,
			Map<String, Object> params, Class<P> classe) throws DaoException;
	
	public List<P> recuperaPorParamsSql(String strQuery,
			Map<String, Object> params, Class<P> classe,
			int maximo, int atual) throws DaoException;

	public Session getSession();

	public P refresh(P persistente) throws DaoException;

	public void refreshRefresh(P persistente) throws DaoException;

	public void flush() throws DaoException;
}
