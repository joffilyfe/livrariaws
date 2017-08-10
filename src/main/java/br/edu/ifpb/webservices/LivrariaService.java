package br.edu.ifpb.webservices;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "LivrariaService")
public class LivrariaService {

	// This methos will return the book id
	@WebMethod(operationName = "cadastrar")
	public int create(@WebParam(name = "titulo") String title, @WebParam(name = "editora") String editor,
			@WebParam(name = "isbn") String isbn, @WebParam(name = "edicao") String edition,
			@WebParam(name = "autor") String author) {

		if (title == null || editor == null || isbn == null || edition == null || author == null) {
			return 0;
		}

		return 1;
	}

	// This method find book by id
	@WebMethod(operationName = "procurarPorId")
	public String findById(@WebParam(name = "id") int id) {

		if (id == 0) {
			return "Not found";
		}

		return String.format("Book by id %d", id);
	}

	// This method find book by ISBN
	@WebMethod(operationName = "procurarPorISBN")
	public String findByISBN(@WebParam(name = "isbn") String isbn) {
		if (isbn == null || isbn.equals("")) {
			return "Please enter a valid ISBN";
		}

		return String.format("Book by ISBN %s", isbn);
	}

	// This method update a books title
	@WebMethod(operationName = "atualizarTitulo")
	public boolean updateTitle(@WebParam(name = "id") int id, @WebParam(name = "titulo") String title) {
		if (id == 0) {
			return false;
		}

		return true;
	}

	// This method update a books editor
	@WebMethod(operationName = "atualizaEditora")
	public boolean updateEditor(@WebParam(name = "id") int id, @WebParam(name = "editor") String editor) {
		if (id == 0) {
			return false;
		}

		return true;
	}

	// This method update a books eidtion
	@WebMethod(operationName = "atualizaEdicao")
	public boolean updateEdition(@WebParam(name = "id") int id, @WebParam(name = "edition") String edition) {
		if (id == 0) {
			return false;
		}

		return true;
	}

	// This method update a books author
	@WebMethod(operationName = "atualizaAutor")
	public boolean updateAuthor(@WebParam(name = "id") int id, @WebParam(name = "autor") String author) {
		if (id == 0) {
			return false;
		}

		return true;
	}
}
