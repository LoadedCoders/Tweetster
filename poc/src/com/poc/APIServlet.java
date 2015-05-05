package com.poc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.json.simple.JSONArray;
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
		
		String hdfs_base_uri = "hdfs://bivm.ibm.com:9000/biginsights/hive/warehouse";
		Path pt = null;
		
		if (command.equalsIgnoreCase("count")) {
			pt = new Path(hdfs_base_uri+"/total_tweets/000000_0");
		} else if (command.equalsIgnoreCase("langs")) {
			pt = new Path(hdfs_base_uri+"/total_languages/000000_0");
		} else if (command.equalsIgnoreCase("users")) {
			pt = new Path(hdfs_base_uri+"/total_users/000000_0");
		} else if (command.equalsIgnoreCase("words")) {
			pt = new Path(hdfs_base_uri+"/total_words/000000_0");
		} else if (command.equalsIgnoreCase("top_langs")) {
			pt = new Path(hdfs_base_uri+"/top_languages/000000_0");
		} else if (command.equalsIgnoreCase("top_pl")) {
			pt = new Path(hdfs_base_uri+"/top_places/000000_0");
		} else if (command.equalsIgnoreCase("top_tags")) {
			pt = new Path(hdfs_base_uri+"/top_hashtags/000000_0");
		} else if (command.equalsIgnoreCase("top_sources")) {
			pt = new Path(hdfs_base_uri+"/top_sources/000000_0");
		}
		
		fs.exists(pt);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				fs.open(pt)));
		String line;
		line = br.readLine();
		StringBuffer stringBuffer = new StringBuffer();
		
		JSONArray jsonObject = new JSONArray();

		while (line != null) {
			jsonObject.add(line);
			stringBuffer.append(line);
			System.out.println(line);
			line = br.readLine();
		}
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
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
