package com.cpn.os4j.command;

import static com.cpn.os4j.util.XMLUtil.toXML;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.ServerError;
import com.cpn.os4j.model.UnmarshallerHelper;
import com.cpn.os4j.util.XMLUtil;

public abstract class AbstractOpenStackCommand<T> implements OpenStackCommand<T>, UnmarshallerHelper<T> {

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

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("action", getAction()).append("verb", getVerb()).append("queryString", queryString);
		return builder.toString();
	}

	public UnmarshallerHelper<T> getUnmarshallerHelper() {
		return this;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> unmarshall(List<Node> aList, Class<T> anUnmarshaller, OpenStack anEndPoint) {
		ArrayList<T> list = new ArrayList<T>();
		for (Node n : aList) {
			try {
				list.add((T) anUnmarshaller.getDeclaredMethod("unmarshall", Node.class, OpenStack.class).invoke(null, n, anEndPoint));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return list;
	}

	public static <T> List<T> unmarshall(Node aDocument, UnmarshallerHelper<T> aHelper, OpenStack anEndPoint) {
		try {
			if (aHelper != null && aHelper.getUnmarshallingClass() != null && aHelper.getUnmarshallingXPath() != null) {
				return unmarshall(XMLUtil.xPathList(aDocument, aHelper.getUnmarshallingXPath()), aHelper.getUnmarshallingClass(), anEndPoint);
			} else {
				LoggerFactory.getLogger(AbstractOpenStackCommand.class).warn("I don't have a way to unmarshall the following XML: " + XMLUtil.prettyPrint(aDocument));
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

			String signature;
			try {
				signature = URLEncoder.encode(endPoint.getSignatureStrategy().getSignature(this), CHAR_ENCODING);
			} catch (UnsupportedEncodingException e1) {
				throw new RuntimeException(e1.getMessage(), e1);
			}
			sb.append("&Signature=" + signature);

			HttpRequestBase request;
			if (getVerb() == "GET") {
				request = new HttpGet(endPoint.getURI() + "?" + sb.toString());
			} else {
				StringEntity entity;
				try {
					entity = new StringEntity(sb.toString());
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
				entity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
				HttpPost post = new HttpPost(endPoint.getURI());
				post.setEntity(entity);
				request = post;
			}
			final OpenStackCommand<T> ref = this;
			try {
				return unmarshall(toXML(client.execute(request, new ResponseHandler<String>() {
					/**
					 * Returns the response body as a String if the response was successful
					 * (a 2xx status code). If no response body exists, this returns null.
					 * If the response was unsuccessful (>= 300 status code), throws an
					 * {@link HttpResponseException}.
					 */
					public String handleResponse(final HttpResponse response) throws HttpResponseException, IOException {
						StatusLine statusLine = response.getStatusLine();
						HttpEntity entity = response.getEntity();
						if (statusLine.getStatusCode() >= 300) {
							if (entity != null) {
								String body = EntityUtils.toString(entity);
								Document doc = toXML(body);

								List<ServerError> errors = AbstractOpenStackCommand.unmarshall(doc, new UnmarshallerHelper<ServerError>() {
									@Override
									public Class<ServerError> getUnmarshallingClass() {
										return ServerError.class;
									}

									@Override
									public String getUnmarshallingXPath() {
										// TODO Auto-generated method stub
										return "//Response/Errors/Error";
									}
								}, endPoint);
								throw new ServerErrorExecption(statusLine.getStatusCode(), errors, body, ref);
							} else {
								throw new HttpResponseException(statusLine.getStatusCode(),  statusLine.getReasonPhrase());
							}
						}

						return entity == null ? null : EntityUtils.toString(entity);
					}
				})), this, endPoint);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		
			
	}

}
