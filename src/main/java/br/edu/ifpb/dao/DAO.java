package br.edu.ifpb.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class DAO<T> {
	protected static EntityManager manager;

	public DAO() {
	}

	public static EntityManager abrir() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("livraria-sqlite");
		return manager = factory.createEntityManager();
	}

	public static void fechar() {
		manager = null;
	}

	public void persistir(T obj) {
		manager.persist(obj);
	}

	@SuppressWarnings("unchecked")
	public T localizar(Object chave) {
		Class<T> type = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		return manager.find(type, chave);
	}

	public T atualizar(T obj) {
		return manager.merge(obj);
	}

	public void apagar(T obj) {
		manager.remove(obj);
	}

	public void reler(T obj) {
		manager.refresh(obj);
	}

	@SuppressWarnings("unchecked")
	public List<T> listar() {
		Class<T> type = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		Query query = manager.createQuery("select x from " + type.getSimpleName() + " x");
		return query.getResultList();
	}

	public static void begin() {
		if (!manager.getTransaction().isActive())
			manager.getTransaction().begin();
	}

	public static void commit() {
		if (manager.getTransaction().isActive()) {
			manager.getTransaction().commit();
			manager.clear(); // esvaziar o cache de objetos
		}
	}

	public static void rollback() {
		if (manager.getTransaction().isActive())
			manager.getTransaction().rollback();
	}

	public static void flush() { // commit parcial
		manager.flush();
	}

}