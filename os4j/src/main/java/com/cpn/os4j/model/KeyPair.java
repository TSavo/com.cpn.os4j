package com.cpn.os4j.model;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.w3c.dom.Node;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.cache.Cacheable;
import com.cpn.os4j.util.XMLUtil;

@SuppressWarnings("serial")
public class KeyPair implements Cacheable<String> {

	public static KeyPair unmarshall(final Node aNode, final OpenStack anOpenStack) {
		final KeyPair p = new KeyPair();
		final XMLUtil x = new XMLUtil(aNode);

		try {
			p.name = x.get("keyName");
			p.fingerprint = x.get("keyFingerprint");
		} catch (final XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return p;

	}

	private String name, fingerprint;

	public String getFingerprint() {
		return fingerprint;
	}

	@Override
	public String getKey() {
		return getName();
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("name", name).append("fingerprint", fingerprint);
		return builder.toString();
	}
}
