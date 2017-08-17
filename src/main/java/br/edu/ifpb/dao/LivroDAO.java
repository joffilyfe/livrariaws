package br.edu.ifpb.dao;

import java.util.List;

import javax.persistence.Query;

import br.edu.ifpb.entity.Livro;

public class LivroDAO extends DAO<Livro> {
	public LivroDAO() {
		super();
	}

	public Livro findByISBN(String isbn) {
		Query q = manager.createQuery("select l from Livro l where l.isbn = :isbn");
		q.setParameter("isbn", isbn);

		@SuppressWarnings("unchecked")
		List<Livro> livros = q.getResultList();

		if (livros.isEmpty()) {
			return null;
		}

		return livros.get(0);
	}
}
