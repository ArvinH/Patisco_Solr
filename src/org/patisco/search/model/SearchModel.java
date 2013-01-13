package org.patisco.search.model;

import java.util.Iterator;
import java.util.Map.Entry;

import net.sf.json.JSONArray;

import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class SearchModel {
	private static int _start = 0; // Specifies the number of rows to skip.
										// Useful in pagination of results.
	private static int _nbDocuments = 20000;
	private static QueryResponse response;
	private static String url = "http://192.168.11.27:8983/solr";
	private long _numFound;
	private int _numReturn;
	private String _fq1 = null;
	private String _fq2 = null;

	public SearchModel(int start, int range) {
		_start = start;
		_nbDocuments = range;
	}

	public JSONArray Search(String query, String sort) {
		SolrServer server;
		SolrDocumentList docs = null;
		String Date_format = null;	//for Date�榡�ഫ
		JSONArray jsonArray = new JSONArray();
		try {
			server = new HttpSolrServer(url);
			// add

			SolrQuery solrQuery = new SolrQuery();
			solrQuery.setQuery(query);
			solrQuery.setStart(_start);
			solrQuery.setRows(_nbDocuments);
			solrQuery.setRequestHandler("query");
			solrQuery.set("fl", "*,score");   //�]�wfl�ѼơA���w�n�Ǧ^����field����ơA�o�̳]�w�Ҧ�field�Pscore
			if (_fq1 != "") {
				solrQuery.addFilterQuery("ProductName:" + _fq1);
			}
			if (_fq2 != "") {
				solrQuery.addFilterQuery("publishedDate:" + _fq2);
			}
			if (sort != null) {
				solrQuery.addSortField(sort, ORDER.asc);
			}
			solrQuery.setRequestHandler("/browse");
			response = server.query(solrQuery);
			docs = response.getResults();
			if (docs != null) {
				System.out.println(docs.getNumFound() + " documents found, "
						+ docs.size() + " returned : ");
				setResultNumber(docs.getNumFound(), docs.size()); //�]�w�ثe�^�ǴX����Ƶ��e��
				for (int i = 0; i < docs.size(); i++) {
					SolrDocument doc = docs.get(i);
					JSONObject jsonObject = new JSONObject();
					for (Iterator<Entry<String, Object>> it2 = doc.iterator(); it2
							.hasNext();) {
						Entry<String, Object> entry = it2.next();
						if (entry.getKey().equals("publishedDate")) {  // �N�Ǧ^��date�榡�ର�¦r��A��K�e�ݧe�{
							Date_format = entry.getValue().toString();
							jsonObject.put(entry.getKey(), Date_format);
						} else {
							//�@�뱡�p
							jsonObject.put(entry.getKey(), entry.getValue());
						}
					}
					System.out.print("\n");
					//�N�`�@���X����Ʀs�bjsonarray���̫᭱�ǵ��e��
					jsonObject.put("TotalResultFound", docs.getNumFound());
					jsonArray.add(jsonObject);
				}
			}

		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonArray;

	}

	public void setFilterQuery(String fq1, String fq2) {
		_fq1 = fq1;
		_fq2 = fq2;
	}

	public void setResultNumber(long numFound, int numReturn) {
		_numFound = numFound;
		_numReturn = numReturn;
	}

	public String getResultNumber() {
		return Integer.toString(_numReturn);
	}

	public String getResultFoundNumber() {
		return Long.toString(_numFound);
	}
}
