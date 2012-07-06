package com.cpn.os4j.model;

import java.io.Serializable;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.w3c.dom.Node;

import com.cpn.os4j.EndPoint;
import com.cpn.xml.XMLUtil;

@SuppressWarnings("serial")
@Immutable
public class ServerError implements Serializable {

	public static ServerError unmarshall(final Node aNode, final EndPoint anOpenStack) {
		final XMLUtil x = new XMLUtil(aNode);
		try {
			return new ServerError(anOpenStack, x.get("Code"), x.get("Message"));
		} catch (final XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private final String code;
	@SuppressWarnings("unused")
	private transient final EndPoint endPoint;

	private final String message;

	public ServerError(final EndPoint anEndPoint, final String code, final String message) {
		endPoint = anEndPoint;
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("code", code).append("message", message);
		return builder.toString();
	}

}
