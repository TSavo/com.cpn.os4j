package com.cpn.os4j.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
	private static final XPath xPath= XPathFactory.newInstance().newXPath();

	static {
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private Node node;
	
	public XMLUtil(Node aNode){
		node = aNode;
	}
	
	public String get(String anXPath) throws XPathExpressionException{
		return xPathString(node, anXPath);
	}
	
	@SuppressWarnings("unchecked")
	public static final <T> T xPath(Node aNode, String anXPath, QName aQName) throws XPathExpressionException {
		return (T) xPath.compile(anXPath).evaluate(aNode, aQName);
	}
	
	public static final List<Node> toList(NodeList aList){
		List<Node> list = new ArrayList<Node>();
		for(int x = 0; x < aList.getLength(); ++x){
			list.add(aList.item(x));
		}
		return list;
	}
	
	public static final List<Node> xPathList(Node aNode, String anXPath) throws XPathExpressionException {
		return toList(XMLUtil.<NodeList>xPath(aNode, anXPath, XPathConstants.NODESET));
	}

	public static final String xPathString(Node aNode, String anXPath) throws XPathExpressionException {
		return (String) xPath.compile(anXPath).evaluate(aNode, XPathConstants.STRING);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> unmarshall(List<Node> aList, Class<T> anUnmarshaller){
		ArrayList<T> list = new ArrayList<T>();
		for(Node n : aList){
			try {
				list.add((T)anUnmarshaller.getDeclaredMethod("unmarshall", Node.class).invoke(null, n));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return list;
	}
	
	public static final Document toXML(String anXMLDoc) {
		try {
			return builder.parse(new ByteArrayInputStream(anXMLDoc.getBytes()));
		} catch (SAXException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
