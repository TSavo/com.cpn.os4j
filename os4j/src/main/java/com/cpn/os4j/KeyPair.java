package com.cpn.os4j;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.w3c.dom.Node;

import com.cpn.os4j.cache.Cacheable;
import com.cpn.os4j.util.XMLUtil;

@SuppressWarnings("serial")
public class KeyPair implements Cacheable<String> {

	private String name, fingerprint;

	public String getName() {
		return name;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	@Override
	public String getKey() {
		return getName();
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("name", name).append("fingerprint", fingerprint);
		return builder.toString();
	}

	public static KeyPair unmarshall(Node aNode, OpenStack anOpenStack) {
		KeyPair p = new KeyPair();
		XMLUtil x = new XMLUtil(aNode);

		try {
			p.name = x.get("keyName");
			p.fingerprint = x.get("keyFingerprint");
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return p;

	}
}
