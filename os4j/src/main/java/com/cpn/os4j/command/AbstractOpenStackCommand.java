package com.cpn.os4j.command;

import static com.cpn.os4j.util.XMLUtil.toXML;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.util.XMLUtil;

public abstract class AbstractOpenStackCommand<T> implements OpenStackCommand<T> {

	private OpenStack endPoint;
	protected TreeMap<String, String> queryString = new TreeMap<String, String>();

	private static final String CHAR_ENCODING = Charset.forName("UTF-8").name();

	public AbstractOpenStackCommand(OpenStack anEndPoint) {
		endPoint = anEndPoint;
		queryString.put("AWSAccessKeyId", endPoint.getCredentials().getAccessKey());
		queryString.put("SignatureMethod", endPoint.getSignatureStrategy().getSignatureMethod());
		queryString.put("SignatureVersion", new Integer(endPoint.getSignatureStrategy().getSignatureVersion()).toString());
		queryString.put("Action", getAction());
		queryString.put("Version", "2010-08-31");
		try {
			queryString.put("Timestamp", URLEncoder.encode(new Date().toString(), CHAR_ENCODING).replaceAll("\\+", "%20"));
			queryString.put("GUID", URLEncoder.encode(UUID.randomUUID().toString(), CHAR_ENCODING));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public OpenStack getEndPoint() {
		return endPoint;
	}

	@Override
	public TreeMap<String, String> getQueryString() {
		return queryString;
	}

	public OpenStackCommand<T> put(String aKey, String aValue) {
		queryString.put(aKey, aValue);
		return this;
	}

	@Override
	public String getVerb() {
		return "GET";
	}

	public abstract Class<T> getUnmarshallingClass();

	public abstract String getUnmarshallingXPath();

	@SuppressWarnings("unchecked")
	public List<T> unmarshall(List<Node> aList, Class<T> anUnmarshaller) {
		ArrayList<T> list = new ArrayList<T>();
		for (Node n : aList) {
			try {
				list.add((T) anUnmarshaller.getDeclaredMethod("unmarshall", Node.class, OpenStack.class).invoke(null, n, endPoint));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return list;
	}

	public List<T> unmarshall(Node aDocument) {
		try {
			if (getUnmarshallingClass() != null && getUnmarshallingXPath() != null) {
				return unmarshall(XMLUtil.xPathList(aDocument, getUnmarshallingXPath()), getUnmarshallingClass());
			} else {
				LoggerFactory.getLogger(AbstractOpenStackCommand.class).warn(XMLUtil.prettyPrint(aDocument));
				return null;
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public List<T> execute() {

		HttpClient client = new DefaultHttpClient();

		StringBuffer sb = new StringBuffer();
		for (String s : queryString.keySet()) {
			if (sb.length() > 0) {
				sb.append("&");
			}
			sb.append(s + "=" + queryString.get(s));
		}
		try {
			String signature = URLEncoder.encode(endPoint.getSignatureStrategy().getSignature(this), CHAR_ENCODING);
			sb.append("&Signature=" + signature);

			HttpRequestBase request;
			if (getVerb() == "GET") {
				request = new HttpGet(endPoint.getURI() + "?" + sb.toString());
			} else {
				StringEntity entity = new StringEntity(sb.toString());
				entity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
				HttpPost post = new HttpPost(endPoint.getURI());
				post.setEntity(entity);
				request = post;
			}

			return unmarshall(toXML(client.execute(request, new BasicResponseHandler())));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
