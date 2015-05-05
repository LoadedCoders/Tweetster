package com.poc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;


/**
 * Servlet implementation class APIServlet
 */
public class APIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public APIServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Map<String, String[]>paramMap = request.getParameterMap();
		String[]qStrings = paramMap.get("q");
		String command = qStrings[0];
		
		JSONObject jsonObject = new JSONObject();
		if (command.equalsIgnoreCase("count")) {
			try {
				String rows = HiveHelper.noOfRows();
				jsonObject.put("count", rows);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				jsonObject.put("error", e.getLocalizedMessage());
			}
		}
		
		PrintWriter writer = response.getWriter();
		writer.println(jsonObject.toJSONString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
