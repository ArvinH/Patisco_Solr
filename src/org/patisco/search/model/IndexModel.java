package org.patisco.search.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.solr.client.solrj.SolrServerException;

import com.mysql.jdbc.ResultSetMetaData;

public class IndexModel {
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private ResultSetMetaData md = null;
	private PreparedStatement pst = null;
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://Localhost:3306/TestDatas";
	private String user = "root";
	private String passwd = "123456";
	private String selectSQL = "select * from Products";
	public Date date = new Date();

	public IndexModel() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, passwd);
		} catch (ClassNotFoundException e) {
			System.out.println("DriverClassNotFound :" + e.toString());
		} catch (SQLException x) {
			System.out.println("Exception :" + x.toString());
		} finally {
			System.out.println((new Date().getTime() - date.getTime()));
		}
	}

	public void SelectTable() {
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectSQL);
			md = (ResultSetMetaData) rs.getMetaData();
			for (int i = 1; i <= md.getColumnCount(); i++) {
				System.out.print(md.getTableName(i) + ".");
				System.out.print(md.getColumnName(i) + "\t\t");
			}
			System.out.println();
			DBIndexTest DBIndex = new DBIndexTest();
			DBIndex.addResultSet(rs);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			Close();
		}
	}

	private void Close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		} catch (SQLException e) {
			System.out.println("Close Exception :" + e.toString());
		}
	}
}