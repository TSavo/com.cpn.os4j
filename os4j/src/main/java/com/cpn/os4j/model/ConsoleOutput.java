package com.cpn.os4j.model;

import java.io.Serializable;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.w3c.dom.Node;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.util.XMLUtil;

@SuppressWarnings("serial")
@Immutable
public class ConsoleOutput implements Serializable {

	public static ConsoleOutput unmarshall(final Node aNode, final OpenStack anEndPoint) {
		final ConsoleOutput o = new ConsoleOutput(anEndPoint);
		final XMLUtil x = new XMLUtil(aNode);
		try {
			o.output = new String(Base64.decodeBase64(x.get("output")));
			o.timestamp = x.get("timestamp");
			o.instanceId = x.get("instanceId");
		} catch (final XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return o;
	}

	private final OpenStack endPoint;

	private String output, timestamp, instanceId;

	private ConsoleOutput(final OpenStack anEndPoint) {
		endPoint = anEndPoint;
	}

	public Instance getInstance() {
		return endPoint.getInstanceCache().get(getInstanceId());
	}

	public String getInstanceId() {
		return instanceId;
	}

	public String getOutput() {
		return output;
	}

	public String getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("output", output).append("timestamp", timestamp).append("instanceId", instanceId);
		return builder.toString();
	}

}
