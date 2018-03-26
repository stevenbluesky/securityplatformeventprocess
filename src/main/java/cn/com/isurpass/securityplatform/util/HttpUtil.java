package cn.com.isurpass.securityplatform.util;

import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class HttpUtil {

	private static Log log = LogFactory.getLog(HttpUtil.class);
	
	public static String httpGet(String url , JSONObject parameter , JSONObject header)
	{
		try 
		{
			log.info(url);
			if ( log.isInfoEnabled() && parameter != null )
				log.info(parameter.toJSONString());
			
			StringBuffer sb = new StringBuffer();
			if ( parameter != null )
				for ( String key : parameter.keySet())
				{
					if ( sb.length() != 0 )
						sb.append("&");
					sb.append(key).append("=").append(URLEncoder.encode(parameter.getString(key) , "UTF-8"));
				}
			
			if ( url.contains("?"))
				url += "&" + sb.toString();
			else 
				url += "?" + sb.toString();
			
			HttpClient httpclient = createHttpClient(url);
			HttpGet httpget = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
			httpget.setConfig(requestConfig);
			
			if ( header != null )
				for ( String key : header.keySet())
					httpget.addHeader(key, header.getString(key));
						
			HttpResponse response = httpclient.execute(httpget);
						
			HttpEntity entity = response.getEntity();

			String rst = EntityUtils.toString(entity , "UTF-8");
			log.info(rst);
			
			return rst;
		}
		catch(SocketTimeoutException e)
		{
			log.warn(e.getMessage());
		}
		catch(Throwable t)
		{
			log.error(t.getMessage(), t);
		}
		return "";
	}
	
	public static String httprequest(String url , String parameter)
	{
		HttpClient httpclient = createHttpClient(url);
		try 
		{
			log.info(url);
			if ( log.isInfoEnabled() )
				log.info(parameter);

			HttpPost httpost = new HttpPost(url);
						
			httpost.setEntity(new StringEntity(parameter, "UTF-8"));
			
			HttpResponse response = httpclient.execute(httpost);
						
			HttpEntity entity = response.getEntity();

			String rst = EntityUtils.toString(entity , "UTF-8");
			log.info(rst);
			
			return rst;
		}
		catch(Throwable t)
		{
			log.error(t.getMessage(), t);
		}
		finally 
		{
			
		}
		return "";
	}
	
	public static String httprequest(String url , JSONObject parameter)
	{
		HttpClient httpclient = createHttpClient(url);
		try 
		{
			log.info(url);
			if ( log.isInfoEnabled() )
				log.info(parameter.toJSONString());

			HttpPost httpost = new HttpPost(url);
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			
			for ( String key : parameter.keySet())
				nvps.add(new BasicNameValuePair(key, parameter.getString(key)));
						
			httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			
			HttpResponse response = httpclient.execute(httpost);
						
			HttpEntity entity = response.getEntity();

			String rst = EntityUtils.toString(entity , "UTF-8");
			log.info(rst);
			
			return rst;
		}
		catch(Throwable t)
		{
			log.error(t.getMessage(), t);
		}
		finally 
		{
			
		}
		return "";
	}
	
	public static HttpClient createHttpClient(String url)
	{
		if ( url.startsWith("https"))
		{
			RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();  
			try {  
  			    SSLContext sslContext = SSLContext.getInstance("TLS"); 
  			    sslContext.init(null, new TrustManager[] { new AnyTrustManager() }, null);
			    LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext);  
			    registryBuilder.register("https", sslSF);  
			} catch (NoSuchAlgorithmException e) {  
			    throw new RuntimeException(e);  
			} catch (KeyManagementException e) {
				 throw new RuntimeException(e);  
			}  
			Registry<ConnectionSocketFactory> registry = registryBuilder.build();  
			
			PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);  

			return HttpClientBuilder.create().setConnectionManager(connManager).build();
		}
		else 
			return HttpClientBuilder.create().build();
	}

}
