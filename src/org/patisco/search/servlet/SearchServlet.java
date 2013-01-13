package org.patisco.search.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.patisco.search.model.SearchModel;

public class SearchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public SearchServlet() {
		super();

	}


	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		String query = URLDecoder.decode(request.getParameter("query"),"UTF-8");
		String fq1 = URLDecoder.decode(request.getParameter("fq_PName"),"UTF-8");
		String fq2 = request.getParameter("fq_PubDate");
		String sort = request.getParameter("sort");
		int range = Integer.parseInt(request.getParameter("range"));
		int start = Integer.parseInt(request.getParameter("start"));
		SearchModel searchModel = new SearchModel(start,range);
		//set filter query
		searchModel.setFilterQuery(fq1,fq2);
		////////////////////////////////
		/*可在solrconfig.xml中，設定request handler，即可不指定欄位，跨欄搜尋，並指定權重*/
		if(query != ""){
		JSONArray Result = searchModel.Search("Specification:" + query, sort);
		response.setContentType("application/json");
		PrintWriter pw = response.getWriter();  
		pw.println(Result);  
        pw.close();  
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//一開始用submit進來的時，是用這個
		request.setCharacterEncoding("UTF-8");
		String query = request.getParameter("input");
		SearchModel searchModel = new SearchModel(0,20000);
		JSONArray Result = searchModel.Search("Specification:" + query, "id");
		String resultNum = searchModel.getResultFoundNumber();
		request.setAttribute("Result", Result);
		request.setAttribute("ResultFound", resultNum);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
}
