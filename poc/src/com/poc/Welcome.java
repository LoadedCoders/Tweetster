package com.poc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * Servlet implementation class Welcome
 */
public class Welcome extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public Welcome() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		Path pt = new Path(
				"hdfs://bivm.ibm.com:9000/user/biadmin/wc15/count.csv");
		String HADOOP_HOME = "/opt/ibm/biginsights/IHC";
		Path path = new Path(HADOOP_HOME+"/etc/hadoop/core-site.xml");
		Configuration configuration = new Configuration();
		configuration.getResource(HADOOP_HOME+"/etc/hadoop/core-site.xml");
		FileSystem fs = FileSystem.get(configuration);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				fs.open(pt)));
		String line;
		line = br.readLine();
		StringBuffer stringBuffer = new StringBuffer();
		while (line != null) {
			stringBuffer.append(line);
			System.out.println(line);
			line = br.readLine();
		}

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("index.jsp");
		request.setAttribute("count", stringBuffer.toString());
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void name() {

	}

}
