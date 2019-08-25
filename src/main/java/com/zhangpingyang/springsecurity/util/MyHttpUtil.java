package com.zhangpingyang.springsecurity.util;


import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.*;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * 使用 HttpClient 来发送 post 和 get 请求
 * 
 * @author P.L
 */
public class MyHttpUtil {
	
	public static final String ERROR_RETURN = null; //http 请求, 返回错误
	private static final String UTF8 = "utf8";
	private static final String GBK = "GBK";
	private static final int HTTP_TIMEOUT=3000;	//设置 http 连接超时为 3秒

	
	public static String clientPostJsonUtf8(String url, String body){
		return clientPost(url, body, ContentTypeAndCharset.JSON_UTF8);
	}
	
	public static String clientPostJsonUtf8(String url, String body, int timeoutMilliSecond){
		return clientPost(url, body, ContentTypeAndCharset.JSON_UTF8, timeoutMilliSecond);
	}


	/**
	 * 用于服务端通过getParameter接收参数
	 * @param url
	 * @param packageParams
	 * @return
	 */
	public static String clientPostLowJsonUtf8(String url, SortedMap<String, Object> packageParams, Integer connectionTimeout){
		return clientPostLow(url, packageParams, ContentTypeAndCharset.JSON_UTF8, connectionTimeout);
	}
	/**
	 * 用于服务端通过getParameter接收参数
	 * @param url
	 * @param packageParams
	 * @return
	 */
	public static String clientPostLowJsonUtf8(String url, SortedMap<String, Object> packageParams){
		return clientPostLow(url, packageParams, ContentTypeAndCharset.JSON_UTF8, null);
	}
	/**
	 * 默认的resUniCode 响应的数据解析编码GBK
	 * @param url
	 * @param body
	 * @return
	 */
	public static String clientPostXmlUtf8(String url, String body){
		return clientPost(url, body, ContentTypeAndCharset.XML_UTF8);
	}
	public static String clientPostXmlGBK(String url, String body){
		return clientPost(url, body, ContentTypeAndCharset.XML_GBK);
	}
	
	/**
	 * post请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String clientPostParam(String url, Map<String, String> params){
		return clientPost(url,params);
	}
	
    /**
     * 发送  http post 请求
     * @param url
     * @param body
     * @return
     */
    public static String clientPost(String url, String body,ContentTypeAndCharset contentTypeAndCharset) {
        return MyHttpUtil.clientPost(url, body, contentTypeAndCharset, HTTP_TIMEOUT);
    }
    
    private static String clientPost(String url, String body, ContentTypeAndCharset contentTypeAndCharset, int timeoutMilliSecond) {
    	HttpClient httpClient = new HttpClient();
    	PostMethod method = new PostMethod(url);
    	String rs = null;
    	
    	try {
    		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
    		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeoutMilliSecond); //设置 post 方法请求超时为 3 秒
    		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeoutMilliSecond); //设置 http 连接超时为 3 秒
    		method.setRequestEntity(new StringRequestEntity(body, contentTypeAndCharset.getContentType(), contentTypeAndCharset.getCharsetName()));
    		httpClient.executeMethod(method);
//            rs = method.getResponseBodyAsString();//不推荐用这个方法
    		rs = getResponseFromHttpMethod(method,contentTypeAndCharset.getCharsetName());
    	} catch(ConnectException e) {
    		e.printStackTrace();
    		return ERROR_RETURN;
    	} catch(ConnectTimeoutException e) {
    		e.printStackTrace();
    		return ERROR_RETURN;
    	} catch(SocketTimeoutException e) {
    		e.printStackTrace();
    		return ERROR_RETURN;
    	} catch (UnsupportedEncodingException e) {
    		e.printStackTrace();
    		return ERROR_RETURN;
    	} catch (IOException e) {
    		e.printStackTrace();
    		return ERROR_RETURN;
    	} finally {
    		method.releaseConnection();
    	}
    	
    	return rs;
    }
    
    
    /**
     * 发送  http post 请求 
     * @param url
     * @return
     */
    public static String clientPostLow(String url, SortedMap<String, Object> packageParams,ContentTypeAndCharset contentTypeAndCharset,Integer connectionTimeout) {
        HttpClient httpClient = new HttpClient();
        PostMethod method = new PostMethod(url);
        String rs = null;

        try {
        	if(connectionTimeout==null || connectionTimeout<1000){
        		connectionTimeout = HTTP_TIMEOUT;
        	}
        	method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, HTTP_TIMEOUT); //设置 post 方法请求超时为 30 秒
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout); //设置 http 连接超时为 30 秒
            createRequestParam(packageParams,method);//添加参数
            httpClient.executeMethod(method);
//            rs = method.getResponseBodyAsString();//不推荐用这个方法
            rs = getResponseFromHttpMethod(method,contentTypeAndCharset.getCharsetName());
        } catch(ConnectException e) {
        	e.printStackTrace();
            return ERROR_RETURN;
        } catch(ConnectTimeoutException e) {
        	e.printStackTrace();
        	return ERROR_RETURN;
        } catch(SocketTimeoutException e) {
        	e.printStackTrace();
        	return ERROR_RETURN;
        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        	return ERROR_RETURN;
        } catch (IOException e) {
        	e.printStackTrace();
        	return ERROR_RETURN;
        } finally {
            method.releaseConnection();
        }

        return rs;
    }
	public static String clientPost(PostMethod method,ContentTypeAndCharset contentTypeAndCharset) {
		HttpClient httpClient = new HttpClient();
		String rs = null;
		try {
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, HTTP_TIMEOUT); //设置 post 方法请求超时为 30 秒
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(HTTP_TIMEOUT); //设置 http 连接超时为 30 秒
			httpClient.executeMethod(method);
//            rs = method.getResponseBodyAsString();//不推荐用这个方法
			rs = getResponseFromHttpMethod(method,contentTypeAndCharset.getCharsetName());
		} catch(ConnectException e) {
			e.printStackTrace();
			return ERROR_RETURN;
		} catch(ConnectTimeoutException e) {
			e.printStackTrace();
			return ERROR_RETURN;
		} catch(SocketTimeoutException e) {
			e.printStackTrace();
			return ERROR_RETURN;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return ERROR_RETURN;
		} catch (IOException e) {
			e.printStackTrace();
			return ERROR_RETURN;
		} finally {
			method.releaseConnection();
		}

		return rs;
	}
	
	/**
	 * post 请求
	 * @param url
	 * @param params 参数
	 * @return
	 */
	public static String clientPost(String url,Map<String,String> params) {
		if(url==null || params==null) return null;
		
		HttpClient httpClient = new HttpClient();
        PostMethod method = new PostMethod(url);
        String rs = null;
        try {
			for(String key : params.keySet()){
				method.setParameter(key,params.get(key));
			}
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, HTTP_TIMEOUT); //设置 post方法请求超时为 30 秒
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(HTTP_TIMEOUT); //设置 http 连接超时为 30 秒
            httpClient.executeMethod(method);
            rs = getResponseFromHttpMethod(method,UTF8);
        } catch(ConnectException e) {
        	e.printStackTrace();
            return ERROR_RETURN;
        } catch(ConnectTimeoutException e) {
        	e.printStackTrace();
        	return ERROR_RETURN;
        } catch(SocketTimeoutException e) {
        	e.printStackTrace();
        	return ERROR_RETURN;
        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        	return ERROR_RETURN;
        } catch (IOException e) {
        	e.printStackTrace();
        	return ERROR_RETURN;
        } finally {
            method.releaseConnection();
        }

        return rs;
	}
	
    /**
     * 发送  http get 请求
     */
	@SuppressWarnings("rawtypes")
	public static String clientGet(String url,Map<String,String> params, Integer timeout) {
        HttpClient httpClient = new HttpClient();
        GetMethod method = new GetMethod(url);
        String rs = null;
		if (timeout == null) {
			timeout = HTTP_TIMEOUT;
		}
        try {
			Set<Map.Entry<String,String>> entrySet=params.entrySet();
			for(Map.Entry entry:entrySet){
				HttpMethodParams httpMethodParams=new HttpMethodParams();
				httpMethodParams.setContentCharset(UTF8);
				httpMethodParams.setParameter(entry.getKey().toString(),entry.getValue());
				method.setParams(httpMethodParams);
			}
            method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout); //设置 post 方法请求超时为 30 秒
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout); //设置 http 连接超时为 30 秒
            httpClient.executeMethod(method);
            //rs = method.getResponseBodyAsString();//不推荐用这个方法
            rs = getResponseFromHttpMethod(method,UTF8);
        } catch(ConnectException e) {
        	e.printStackTrace();
        	return ERROR_RETURN;
        } catch(ConnectTimeoutException e) {
        	e.printStackTrace();
        	return ERROR_RETURN;
        } catch(SocketTimeoutException e) {
        	e.printStackTrace();
        	return ERROR_RETURN;
        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        	return ERROR_RETURN;
        } catch (IOException e) {
        	e.printStackTrace();
        	return ERROR_RETURN;
        } finally {
            method.releaseConnection();
        }

        return rs;
    }
    
	/**
	 * 发送  http get 请求
	 */
	public static String clientGet(String url,String queryStr, Integer timeout) {
		HttpClient httpClient = new HttpClient();
		GetMethod method = new GetMethod(url);
		String rs = null;
		if (timeout == null) {
			timeout = HTTP_TIMEOUT;
		}
		try {
			method.setQueryString(queryStr);
			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout); //设置 post 方法请求超时为 30 秒
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout); //设置 http 连接超时为 30 秒
			httpClient.executeMethod(method);
			//rs = method.getResponseBodyAsString();//不推荐用这个方法
			rs = getResponseFromHttpMethod(method,UTF8);
		} catch(ConnectException e) {
			e.printStackTrace();
			return ERROR_RETURN;
		} catch(ConnectTimeoutException e) {
			e.printStackTrace();
			return ERROR_RETURN;
		} catch(SocketTimeoutException e) {
			e.printStackTrace();
			return ERROR_RETURN;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return ERROR_RETURN;
		} catch (IOException e) {
			e.printStackTrace();
			return ERROR_RETURN;
		} finally {
			method.releaseConnection();
		}

		return rs;
	}
	 /**
     * 
     * @param packageParams
     * @param method
     */
	@SuppressWarnings("rawtypes")
	public static void createRequestParam(SortedMap<String, Object> packageParams,PostMethod method) {
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v)) {
				method.addParameter(k, v.toString());
			}
		}

	}
    /**
     * get Response From Http Method
     * @param method
     * @param resUniCode 返回数据的解析编码
     * @return
     * @throws IOException
     */
    private static String getResponseFromHttpMethod(HttpMethodBase method,String resUniCode) throws IOException {
    	InputStream resStream = method.getResponseBodyAsStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(resStream,resUniCode));//new GBK() //"utf8"
        StringBuilder sb = new StringBuilder();
        String resTemp = null;
        while((resTemp = br.readLine()) != null) {
            sb.append(resTemp);
        }
    	return sb.toString();
    }
    
    /**
     * http://blog.csdn.net/cdefg198/article/details/8235338
     * @author P.L
     *
     */
    public enum ContentTypeAndCharset {
    	TEXT_UTF8("text/plain;charset=UTF8",UTF8,"服务端需要返回一段普通文本给客户端"),
    	HTML_UTF8("text/html;charset=UTF8",UTF8,"服务端需要返回一段HTML代码给客户端"),
    	XML_UTF8("text/xml;charset=UTF8",UTF8,"服务端需要返回一段XML代码给客户端"),
    	JSON_UTF8("application/json;charset=UTF8",UTF8,"服务端需要返回一段json代码给客户端"),
    	JAVASCRIPT_UTF8("application/javascript;charset=UTF8",UTF8,"服务端需要返回一段javascript代码给客户端"),
    	TEXT_JAVASCRIPT_UTF8("text/javascript;charset=UTF8",UTF8,"服务端需要返回一段javascript代码给客户端"),
    	XML_GBK("text/xml;charset=UTF8",GBK,"服务端需要返回一段XML代码给客户端");
    	
    	private String contentType;
    	private String charsetName;
		private ContentTypeAndCharset(String contentType,String charsetName ,String remark) {
			this.contentType = contentType;
			this.charsetName = charsetName;
		}
		public String getContentType() {
			return contentType;
		}
		public String getCharsetName() {
			return charsetName;
		}
    }
}
