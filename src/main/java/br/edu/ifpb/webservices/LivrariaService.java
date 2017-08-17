package br.edu.ifpb.webservices;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

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
	@WebMethod(operationName = "cadastrar")
	public boolean create(@WebParam(name = "titulo") String title, @WebParam(name = "editora") String editor,
			@WebParam(name = "isbn") String isbn, @WebParam(name = "edicao") Integer edition,
			@WebParam(name = "autor") String author) {

		if (title.isEmpty() || editor.isEmpty() || isbn.isEmpty() || edition == null || author.isEmpty()) {
			return false;
		}

		Livro livro = new Livro(title, isbn, editor, edition, author);
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

	// This method find book by ISBN
	@WebMethod(operationName = "procurarPorISBN")
	public Livro findByISBN(@WebParam(name = "isbn") String isbn) {
		if (isbn == null || isbn.isEmpty()) {
			return null;
		}

		LivroDAO dao = new LivroDAO();
		Livro livro = dao.findByISBN(isbn);

		return livro;
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
	public String destroyBook(@WebParam(name = "livro") Livro livro) {
		LivroDAO dao = new LivroDAO();

		livro = dao.localizar(livro.getId());
		if (livro == null) {
			return "Livro não localizado";
		}
		dao.begin();
		dao.apagar(livro);
		dao.commit();

		return livro.toString();
	}
}
