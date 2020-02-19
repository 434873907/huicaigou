package com.apiunion.procurement.admin.test;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * commons-httpclient是一个遗留版本，现在官方已经不推荐使用了。 它已取代由Apache
 * HttpComponents项目HttpClient和的HttpCore模组，提供更好的性能和更大的灵活性。
 * 
 * @author HJC
 *
 */
public class HttpClientUtil {
//	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	private HttpClientUtil() {
	}
	private static CloseableHttpClient httpClient;
	/*private static RequestConfig requestConfig = RequestConfig.custom()
			.setConnectTimeout(2000)
			.setSocketTimeout(5000)
			.build();*/
	static {
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(2000)
				.setSocketTimeout(10000)
				.build();
		httpClient = HttpClients.createDefault();
		HttpClientBuilder httpClientBuilder = HttpClients.custom();
		httpClient = httpClientBuilder.setDefaultRequestConfig(requestConfig)
				.build();

	}



	public static String doGet(String url, Map<String, String> param, Map<String, String> reqHeader) {

		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();

			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);

			for (String key : reqHeader.keySet()) {
//				httpPost.setHeader(key, reqHeader.get(key));
				httpGet.setHeader(key, reqHeader.get(key));
			}

			// 执行请求
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}

	/*public static String doGet(String url) {
		return doGet(url, null);
	}*/

	public static String doPost(String url, Map<String, String> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
				httpPost.setEntity(entity);
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		logger.info(resultString);
		System.out.println(resultString);
		return resultString;
	}

	public static String doPost(String url) {
		return doPost(url, null);
	}

	public static String doPostJson(String url, String json) {
		// 创建Httpclient对象
//		CloseableHttpClient httpClient = HttpClients.createDefault();
        /*RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(2000)
                .setSocketTimeout(5000)
                .build();*/
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
//			httpPost.setConfig(requestConfig);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		logger.info(resultString);
		System.out.println(resultString);
		return resultString;
	}


	public static String doPostJson(String url, String json,Map<String, String> reqHeader) {
		// 创建Httpclient对象
//		CloseableHttpClient httpClient = HttpClients.createDefault();
        /*RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(2000)
                .setSocketTimeout(5000)
                .build();*/
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);

			for (String key : reqHeader.keySet()) {
				httpPost.setHeader(key, reqHeader.get(key));
			}
//			httpPost.setConfig(requestConfig);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		logger.info(resultString);
		System.out.println(resultString);
		return resultString;
	}

	/**
	 * •普通http post的contentType为 •application/x-www-form-urlencoded
	 * •WebService的contentType为－即在Http的基础上发SOAP协议 •text/xml 这是基于soap1.1协议。
	 * •application/soap+xml 这是基于soap1.2协议。
	 * 
	 * @param url
	 * @param soapxml
	 * @return
	 */
	public static String doPostWebService(String url, String soapxml) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			StringEntity entity = new StringEntity(soapxml);
			httpPost.setHeader("Content-Type", "application/soap+xml; charset=utf-8");
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return resultString;
	}


}
