package com.cpn.os4j.command;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.http.client.ClientProtocolException;
import org.xml.sax.SAXException;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.UnmarshallerHelper;

public interface OpenStackCommand<T> {

	
	public abstract String getVerb();
	public abstract TreeMap<String, String> getQueryString();
	public abstract OpenStack getEndPoint();
	public abstract String getAction();
	public abstract List<T> execute() throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException, ClientProtocolException, IOException, ParserConfigurationException, URISyntaxException, SAXException, XPathExpressionException;
	public abstract UnmarshallerHelper<T> getUnmarshallerHelper();

}