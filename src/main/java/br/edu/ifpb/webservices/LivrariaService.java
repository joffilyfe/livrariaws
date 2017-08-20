package br.edu.ifpb.webservices;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import br.edu.ifpb.dao.DAO;
import br.edu.ifpb.dao.LivroDAO;
import br.edu.ifpb.entity.Livro;

@WebService(serviceName = "LivrariaService")
public class LivrariaService {

	@PostConstruct
	@WebMethod(exclude = true)
	public void initializer() {
		DAO.abrir();
	}

	@PreDestroy
	@WebMethod(exclude = true)
	public void finalizer() {
		DAO.fechar();
	}

	// This methos will return the book id
	@WebMethod(operationName = "cadastrarLivro")
	public boolean create(@WebParam(name = "livro") Livro livro) {
		
		if (livro == null || livro.getTitulo().isEmpty() ||
			livro.getEditora().isEmpty() || livro.getIsbn().isEmpty() ||
			livro.getEdicao() == null || livro.getAutor().isEmpty()) {
			return false;
		}
		//evita um erro no SQLite
		livro.setId(null);
		
		LivroDAO dao = new LivroDAO();
		dao.begin();
		dao.persistir(livro);
		dao.commit();

		return true;
	}

	// This method find book by id
	@WebMethod(operationName = "procurarPorId")
	public Livro findById(@WebParam(name = "id") int id) {

		if (id == 0) {
			return null;
		}

		LivroDAO dao = new LivroDAO();
		Livro livro = dao.localizar(id);

		return livro;
	}
	
	// This method search book by ISBN
	@WebMethod(operationName = "pesquisarPorISBN")
	public List<Livro> searchByISBN(@WebParam(name = "isbn") String isbn) {
		List<Livro> livros = new ArrayList<Livro>();
		
		if (isbn == null || isbn.isEmpty()) {
			return livros;
		}

		LivroDAO dao = new LivroDAO();
		livros = dao.searchByISBN(isbn); 
		
		return livros;
	}

	// This method find book by ISBN
	@WebMethod(operationName = "procurarPorISBN")
	public Livro findByISBN(@WebParam(name = "isbn") String isbn) {
		if (isbn == null || isbn.isEmpty()) {
			return null;
		}

		LivroDAO dao = new LivroDAO();

		return dao.findByISBN(isbn);
	}

	// This method update a books
	@WebMethod(operationName = "atualizarLivro")
	public boolean updateBook(@WebParam(name = "livro") Livro livro) {
		if (livro == null || livro.getId() == 0) {
			return false;
		}

		LivroDAO dao = new LivroDAO();

		if (dao.localizar(livro.getId()) == null) {
			return false;
		}

		dao.begin();
		dao.atualizar(livro);
		dao.commit();
		return true;
	}

	@WebMethod(operationName = "excluirLivro")
	public Livro destroyBook(@WebParam(name = "id") int id) {
		LivroDAO dao = new LivroDAO();
		Livro livro = null;
		
		livro = this.findById(id);
		if (livro == null) {
			return null;
		}
		dao.begin();
		dao.apagar(livro);
		dao.commit();

		return livro;
	}
	
    @WebMethod(operationName = "listarLivros")
	public List<Livro> listBooks() {
		List<Livro> books = null;
		LivroDAO dao = new LivroDAO();
		books = dao.listar();
		return books;
	}
	
}
