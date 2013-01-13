package org.patisco.search.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TimeZone;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

public class DBIndexTest {

	private static String url = "http://192.168.11.27:8983/solr/";
	private static HttpSolrServer solrCore;

	public DBIndexTest() throws MalformedURLException {
		solrCore = new HttpSolrServer(url);
	}

	public long addResultSet(ResultSet rs) throws SQLException,
			SolrServerException, IOException {
		long count = 0;
		int innerCount = 0;
		Timestamp DTF = null;
		// solr 內的 Date格式 一定要是 yyyy-MM-dd'T'HH:mm:ss'Z' -like: "2010-03-09T15:38:29Z"
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		solrCore.deleteByQuery("*:*");
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		ResultSetMetaData rsm = rs.getMetaData();
		int numColumns = rsm.getColumnCount();
		String[] colNames = new String[numColumns + 1];
		/**
		 * JDBC numbers the columns starting at 1, so the normal java convention
		 * of starting at zero won't work.
		 */
		for (int i = 1; i < (numColumns + 1); i++) {
			colNames[i] = rsm.getColumnName(i);
		}

		while (rs.next()) {
			count++;
			innerCount++;

			SolrInputDocument doc = new SolrInputDocument();
			Object f = null;
			for (int j = 1; j < (numColumns + 1); j++) {
				if (colNames[j] != null) {
					// 將資料庫中讀出的時間格式改為solr可讀的（UTC）
					if ( colNames[j].equals("publishedDate")){
					/* 看時區是哪個，在這裡作更改*/
					   	TimeZone utc = TimeZone.getTimeZone( "UTC" );
						dateFormat.setTimeZone( utc );				
						DTF = rs.getTimestamp(j);
						f = dateFormat.format(DTF);
					}
					else {
					f = rs.getString(j); 
					}
					if (f == null) {
						f = " ";
					}
					doc.addField(colNames[j], f);
				}
			}
			System.out.print("\n");
			docs.add(doc);

		}
		solrCore.add(docs);
		solrCore.commit();
		docs.clear();
		innerCount = 0;
		return count;
	}
}