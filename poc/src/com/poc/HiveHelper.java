package com.poc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.m2.model.formula.parser.FormulaParser.returnvalue_return;

import com.ibm.avatar.aog.ConstFuncNode.Str;

public class HiveHelper {
	private static String driverName = "org.apache.hive.jdbc.HiveDriver";

	static {
		try {
			Class.forName(driverName);
			
			setup();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static Connection con;
	static Statement stmt;

	static void setup() throws SQLException {
		con = DriverManager
				.getConnection("jdbc:hive2://bivm.ibm.com:10000/default",
						"biadmin", "biadmin");
		stmt = con.createStatement();
	}

	static void describe(String table) throws SQLException {
		String sql = "describe " + table;
		System.out.println("Running: " + sql);
		ResultSet res = stmt.executeQuery(sql);
		while (res.next()) {
			System.out.println(res.getString(1) + "\t" + res.getString(2));
		}
	}

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {

		String tableName = "poc";
		// describe table

		describe(tableName);

		selectFirstRow(tableName);
	}

	static String noOfRows(String table) throws SQLException {
		// regular hive query
		String sql = "select count(1) from " + table;
		System.out.println("Running: " + sql);
		ResultSet res = stmt.executeQuery(sql);
		String count = "0";
		while (res.next()) {
			System.out.println(res.getString(1));
			count = res.getString(1);
		}
		return count;
	}
	
	static void selectFirstRow(String table) throws SQLException {
		String sql = "select * from " + table +" LIMIT 1";
		System.out.println("Running: " + sql);
		ResultSet res = stmt.executeQuery(sql);
		while (res.next()) {
			System.out.println(res.getString(1)+" - "+res.getString(2));
		}
		
	}

	public static List<String> listAllTables() throws SQLException {
		// show tables
		String sql = "show tables";
		System.out.println("Running: " + sql);
		
		List<String> resultsArrayList = new ArrayList<String>();
		ResultSet r = stmt.executeQuery(sql);
		if (r.next()) {
			System.out.println(r.getString(1));
			resultsArrayList.add(r.getString(1));
		}
		return resultsArrayList;
	}
	
	
}
