package com.cpn.os4j.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLUtil {
	private static final DocumentBuilder builder;
	private static final XPath xPath = XPathFactory.newInstance().newXPath();

	static {
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private Node node;

	public XMLUtil(Node aNode) {
		node = aNode;
	}

	public String get(String anXPath) throws XPathExpressionException {
		return xPathString(node, anXPath);
	}
	
	public Calendar getCalendar(String anXPath) throws XPathExpressionException{
		Calendar c = new GregorianCalendar();
		Date d;
		try {
			d = DateFormat.getDateInstance().parse((get(anXPath)));
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		c.setTime(d);
		return c;
		
	}

	public String getString(String anXPath) throws XPathExpressionException {
		return get(anXPath);
	}

	public int getInteger(String anXPath) throws XPathExpressionException {
		return xPathInteger(node, anXPath);
	}

	public List<Node> getList(String anXPath) throws XPathExpressionException {
		return xPathList(node, anXPath);
	}

	public List<String> getStringList(String anXPath) throws XPathExpressionException {
		return xPathStringList(node, anXPath);
	}

	@SuppressWarnings("unchecked")
	public static final <T> T xPath(Node aNode, String anXPath, QName aQName) throws XPathExpressionException {
		return (T) xPath.compile(anXPath).evaluate(aNode, aQName);
	}

	@SuppressWarnings("unchecked")
	public static final <T> List<T> toList(NodeList aList) {
		List<T> list = new ArrayList<T>();
		for (int x = 0; x < aList.getLength(); ++x) {
			list.add((T) aList.item(x));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static final <T> List<T> toStringList(NodeList aList) {
		List<T> list = new ArrayList<T>();
		for (int x = 0; x < aList.getLength(); ++x) {
			list.add((T) aList.item(x).getNodeValue());
		}
		return list;
	}

	public static final List<Node> xPathList(Node aNode, String anXPath) throws XPathExpressionException {
		return toList(XMLUtil.<NodeList> xPath(aNode, anXPath, XPathConstants.NODESET));
	}

	public static final List<String> xPathStringList(Node aNode, String anXPath) throws XPathExpressionException {
		return toStringList(XMLUtil.<NodeList> xPath(aNode, anXPath, XPathConstants.NODESET));
	}

	public static final String xPathString(Node aNode, String anXPath) throws XPathExpressionException {
		return (String) xPath.compile(anXPath).evaluate(aNode, XPathConstants.STRING);
	}

	public static final int xPathInteger(Node aNode, String anXPath) throws XPathExpressionException {
		return Integer.parseInt((String) xPath.compile(anXPath).evaluate(aNode, XPathConstants.STRING));
	}

	public static final Document toXML(String anXMLDoc) {
		try {
			return builder.parse(new ByteArrayInputStream(anXMLDoc.getBytes()));
		} catch (SAXException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
