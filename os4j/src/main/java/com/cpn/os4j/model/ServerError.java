package com.cpn.os4j.model;

import java.io.Serializable;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.w3c.dom.Node;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.util.XMLUtil;

@SuppressWarnings("serial")
@Immutable
public class ServerError implements Serializable{

	private String code;
	private String message;
	@SuppressWarnings("unused")
	private OpenStack endPoint;
	
	public ServerError(OpenStack anEndPoint, String code, String message){
		this.endPoint = anEndPoint;
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	public static ServerError unmarshall(Node aNode, OpenStack anOpenStack){
		XMLUtil x = new XMLUtil(aNode);
		try {
			return new ServerError(anOpenStack, x.get("Code"), x.get("Message"));
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}	
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("code", code).append("message", message);
		return builder.toString();
	}
	
	
}
