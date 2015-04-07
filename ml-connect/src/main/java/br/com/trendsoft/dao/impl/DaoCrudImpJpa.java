package br.com.trendsoft.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.trendsoft.dao.DaoCrud;
import br.com.trendsoft.exception.DaoException;
import br.com.trendsoft.model.Persistente;

@SuppressWarnings("unchecked")
public class DaoCrudImpJpa<P extends Persistente> implements DaoCrud<P> {

	@PersistenceContext
	private EntityManager entityManager;

	private final Class<P> oClass;// object class

	public DaoCrudImpJpa() {
		this.oClass = (Class<P>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	protected EntityManager getEntityManager() {
		if (entityManager == null)
			throw new IllegalStateException(
					"Contexto de persistencia está nulo !!!");
		return entityManager;
	}

	@Override
	public Session getSession() {
		return (Session) this.getEntityManager().getDelegate();
	}

	@Override
	public void alterar(P persistente) throws DaoException {
		String msg = new String("Erro ao realizar o merge no DAO.");
		try {
			getEntityManager().merge(persistente);
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e.getCause(), null, msg);
		}
	}

	@Override
	public void deletar(P persistente) throws DaoException {
		String msg = new String("Erro ao realizar o delete no DAO.");
		try {
			persistente = getEntityManager().merge(persistente);
			getEntityManager().remove(persistente);
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e.getCause(), null, msg);
		}
	}
	
	@Override
	public void excluirTodos() throws DaoException {
		String msg = new String("Erro ao realizar o deleteTodos no DAO.");
		try {
			List<P> listaTodos = this.recuperaTodos();
			for (P persistente : listaTodos) {
				persistente = getEntityManager().merge(persistente);
				getEntityManager().remove(persistente);
			}
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e.getCause(), null, msg);
		}
	}

	@Override
	public void gravar(P persistente) throws DaoException {
		String msg = new String("Erro ao realizar o gravar no DAO.");
		try {
			getEntityManager().persist(persistente);	
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e.getCause(), null, msg);
		}
	}
	
	@Override
	public void gravar(List<P> listaPersistente) throws DaoException {
		for (P p : listaPersistente) {
			gravar(p);
		}
	}

	@Override
	public int executaUpdateOuDelete(String queryString) throws DaoException {
		String msg = new String(
				"Erro ao realizar o executa update ou delete no DAO.");
		try {
			Query query = getEntityManager().createQuery(queryString);
			return query.executeUpdate();
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e.getCause(), null, msg);
		}
	}

	@Override
	public int executaUpdateOuDelete(String query, Map<String, Object> params)
			throws DaoException {

		String msg = new String(
				"Erro ao realizar o executa update ou delete no DAO.");

		try {
			Query q = getEntityManager().createQuery(query);

			// Separa os parâmetros transmitidos
			for (String chave : params.keySet()) {
				q.setParameter(chave, params.get(chave));
			}

			return q.executeUpdate();

		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e.getCause(), null, msg);
		}
	}

	@Override
	public List<P> recuperaTodos() throws DaoException {
		String msg = new String("Erro ao realizar o recuperar todos no DAO.");
		try {
			String queryString = "SELECT obj FROM " + oClass.getSimpleName()
					+ " obj";
			Query query = getEntityManager().createQuery(queryString);
			return query.getResultList();
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e.getCause(), null, msg);
		}
	}

	@Override
	public List<P> recuperaTodosOrderBy(String query) throws DaoException {
		String msg = new String(
				"Erro ao realizar o recuperar todos com ordenação no DAO.");
		try {
			Query q = getEntityManager().createQuery(query);
			return q.getResultList();
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e.getCause(), null, msg);
		}
	}
	
	public List<P> recuperaTodosOrderByCriteria(String orderBy) throws DaoException {
		String msg = new String(
				"Erro ao realizar o recuperar todos com ordenação via Criteria no DAO.");
		try {
		  Criteria criteria = getSession().createCriteria(oClass)
				  .addOrder(Order.asc(orderBy));
		  
		  return criteria.list();
			
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e.getCause(), null, msg);
		}
	}

	@Override
	public P recuperaUm(Serializable pk) throws DaoException {
		String msg = new String("Erro ao realizar o recuperar um no DAO.");
		try {
			// return (P) getEntityManager().find(Persistente.class, pk);

			return getEntityManager().find(oClass, pk);
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e.getCause(), null, msg);
		}
	}

	@Override
	public List<P> recuperaPorParams(String query, Map<String, Object> params)
			throws DaoException {

		String msg = new String(
				"Erro ao realizar o recuperar por parametros no DAO.");

		try {
			Query q = getEntityManager().createQuery(query);

			// Separa os parâmetros transmitidos
			for (String chave : params.keySet()) {
				q.setParameter(chave, params.get(chave));
			}

			return q.getResultList();

		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e.getCause(), null, msg);
		}
	}
	
	@Override
	public List<P> recuperaPorParamsSql(String strQuery,
			Map<String, Object> params, Class<P> classe,
			int maximo, int atual) throws DaoException {

		String msg = new String(
				"Erro ao realizar o recuperar por parametros tipo Sql no DAO.");

		try {
			Query query = this.getEntityManager().createNativeQuery(
					strQuery.toString(), classe);

			// Separa os parâmetros transmitidos
			for (String chave : params.keySet()) {
				query.setParameter(chave, params.get(chave));
			}
			
			query.setFirstResult(atual);
			query.setMaxResults(maximo);

			return query.getResultList();

		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e.getCause(), null, msg);
		}
	}

	@Override
	public List<P> recuperaPorParamsSql(String strQuery,
			Map<String, Object> params, Class<P> classe) throws DaoException {

		String msg = new String(
				"Erro ao realizar o recuperar por parametros tipo Sql no DAO.");

		try {
			Query query = this.getEntityManager().createNativeQuery(
					strQuery.toString(), classe);

			// Separa os parâmetros transmitidos
			for (String chave : params.keySet()) {
				query.setParameter(chave, params.get(chave));
			}

			return query.getResultList();

		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e.getCause(), null, msg);
		}
	}

	@Override
	public List<P> recuperaPorParams(String query, Map<String, Object> params,
			int maxResults) throws DaoException {

		String msg = new String(
				"Erro ao realizar o recuperar por parametros com maxResults no DAO.");

		try {
			Query q = getEntityManager().createQuery(query);

			// Separa os parâmetros transmitidos
			for (String chave : params.keySet()) {
				q.setParameter(chave, params.get(chave));
			}

			q = q.setMaxResults(maxResults);

			return q.getResultList();

		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e.getCause(), null, msg);
		}
	}

	@Override
	public List<P> recuperaPorParams(String query, Map<String, Object> params,
			int maximo, int atual) throws DaoException {

		String msg = new String(
				"Erro ao realizar o recuperar por parametros no DAO.");

		try {
			Query q = getEntityManager().createQuery(query);

			// Separa os parâmetros transmitidos
			for (String chave : params.keySet()) {
				q.setParameter(chave, params.get(chave));
			}

			// Query q =
			// getEntityManager().createQuery(queryString).setMaxResults(
			// maximo).setFirstResult(atual);

			// Query q = getEntityManager().createQuery(queryString);

			q = q.setFirstResult(atual);

			q = q.setMaxResults(maximo);

			return q.getResultList();

		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e.getCause(), null, msg);
		}
	}
	
	@Override
	public Long recuperaPorParamsQteReg(String query, Map<String, Object> params)
			throws DaoException {

		String msg = new String(
				"Erro ao realizar o recuperar por parametros no DAO.");

		try {
			Query q = getEntityManager().createQuery(query);

			// Separa os parâmetros transmitidos
			for (String chave : params.keySet()) {
				q.setParameter(chave, params.get(chave));
			}

			return (Long) q.getSingleResult();

		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e.getCause(), null, msg);
		}
	}

	@Override
	public List<P> recuperaPorQuery(String query) throws DaoException {

		String msg = new String(
				"Erro ao realizar o recuperar por query no DAO.");

		try {
			Query q = getEntityManager().createQuery(query);

			return q.getResultList();

		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e.getCause(), null, msg);
		}
	}

	@Override
	public Object recuperaUmPorQuery(String query) throws DaoException {

		String msg = new String(
				"Erro ao realizar o recuperar um Object por query no DAO.");

		try {
			Query q = getEntityManager().createQuery(query);

			return q.getSingleResult();

		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e.getCause(), null, msg);
		}
	}

	@Override
	public P recuperaUmPorParams(String query, Map<String, Object> params)
			throws DaoException {

		String msg = new String(
				"Erro ao realizar o recuperar um por parametros no DAO.");

		try {
			Query q = getEntityManager().createQuery(query);

			// Separa os parâmetros transmitidos
			for (String chave : params.keySet()) {
				q.setParameter(chave, params.get(chave));
			}

			return (P) q.getSingleResult();

		} catch (Exception e) {
			DaoException daoEx = new DaoException(e.getMessage(), e.getCause(),
					null, msg);
			// daoEx.setTransacao(getEntityManager().getTransaction());
			// throw new DaoException(e.getMessage(), e.getCause(), null, msg);
			throw daoEx;
		}
	}

	/**
	 * Variação do recuperaUmPoParams onde o NoResultException retorna null no
	 * objeto
	 * 
	 * @param query
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	@Override
	public P recuperaUmPorParamsTrataNull(String query,
			Map<String, Object> params) throws DaoException {

		String msg = new String(
				"Erro ao realizar o recuperar um por parametros no DAO.");

		try {
			Query q = getEntityManager().createQuery(query);

			// Separa os parâmetros transmitidos
			for (String chave : params.keySet()) {
				q.setParameter(chave, params.get(chave));
			}
			try {
				return (P) q.getSingleResult();
			} catch (NoResultException e) {
				return null;
			}

		} catch (Exception e) {
			DaoException daoEx = new DaoException(e.getMessage(), e.getCause(),
					null, msg);
			// daoEx.setTransacao(getEntityManager().getTransaction());
			// throw new DaoException(e.getMessage(), e.getCause(), null, msg);
			throw daoEx;
		}
	}

	public Object recuperaUmCampoPorParams(String query,
			Map<String, Object> params) throws DaoException {
		String msg = new String(
				"Erro ao realizar o recuperar um campo por parametros no DAO.");

		try {
			Query q = getEntityManager().createQuery(query);

			// Separa os parâmetros transmitidos
			for (String chave : params.keySet()) {
				q.setParameter(chave, params.get(chave));
			}

			return q.getSingleResult();

		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e.getCause(), null, msg);
		}
	}

	@Override
	// @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public P merge(P persistente) throws DaoException {
		return entityManager.merge(persistente);
	}

	@Override
	// @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public P refresh(P persistente) throws DaoException {
		return entityManager.merge(persistente);
	}

	public void refreshRefresh(P persistente) throws DaoException {
		entityManager.refresh(persistente);
	}

	public void flush() throws DaoException {
		entityManager.flush();
	}

	@Override
	public List<P> paginacaoBaseSerializableDataModel(String strQuery,
			Map<String, Object> params) throws DaoException {
		String msg = new String(
				"Erro ao executar o método de paginação do DaoCrudImpJpa.");
		try {
			Query q = getEntityManager().createQuery(strQuery);

			// Separa os parâmetros transmitidos
			for (String chave : params.keySet()) {
				q.setParameter(chave, params.get(chave));
			}

			return q.getResultList();

		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e.getCause(), null, msg);
		}
	}

}
