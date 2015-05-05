package com.poc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

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
		Path path2 = new Path(HADOOP_HOME+"/etc/hadoop/hdfs-site.xml");

		Configuration configuration = new Configuration();
		configuration.addResource(path);
		configuration.addResource(path2);

		FileSystem fs = null;
		try {
			fs = FileSystem.get(new URI("hdfs://bivm.ibm.com:9000"), configuration);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fs.exists(pt);
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
