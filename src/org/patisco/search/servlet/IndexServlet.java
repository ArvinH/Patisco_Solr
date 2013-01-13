package org.patisco.search.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.patisco.search.model.IndexModel;

public class IndexServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public IndexServlet() {
		super();
	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//request.getRequestDispatcher("/index.jsp").forward(request, response);
		IndexModel indexmodelFromDB = new IndexModel();
		indexmodelFromDB.SelectTable();
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
}
