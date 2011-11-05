package com.cpn.os4j;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.TreeMap;

import org.junit.Test;

import com.cpn.os4j.command.DescribeRegionsCommand;
import com.cpn.os4j.command.OpenStackCommand;
import com.cpn.os4j.signature.HmacSHA256SignatureStrategy;
import com.cpn.os4j.signature.SignatureStrategy;

public class OpenStackTest {

	private static OpenStack ep;

	static {
		try {
			ep = new OpenStack(new URI("http://10.1.10.249:8773/services/Cloud/"), "ed391003-88b9-4408-9e87-1a87ba460121%3Avsp", "e23517e4-f511-47f3-8b93-3e595a0607ca");
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Test
	public void testHmacSHA256SignatureStrategy() throws Exception {

		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("AWSAccessKeyId", "ed391003-88b9-4408-9e87-1a87ba460121%3Avsp");
		map.put("Action", "DescribeInstances");
		map.put("SignatureMethod", "HmacSHA256");
		map.put("SignatureVersion", "2");
		map.put("Timestamp", "2011-11-03T16%3A41%3A58");
		map.put("Version", "2009-11-30");

		OpenStack os = mock(OpenStack.class);
		URI uri = new URI("http://10.1.10.249:8773/services/Cloud/");
		when(os.getURI()).thenReturn(uri);
		when(os.getSecretKey()).thenReturn("e23517e4-f511-47f3-8b93-3e595a0607ca");

		OpenStackCommand<?> command = mock(OpenStackCommand.class);
		when(command.getQueryString()).thenReturn(map);
		when(command.getVerb()).thenReturn("GET");
		when(command.getEndPoint()).thenReturn(os);

		SignatureStrategy strategy = new HmacSHA256SignatureStrategy();

		assertEquals("U449qSt/iwFseMq1OTpiLXokU51OEflGegNIHl4dx4Q=", strategy.getSignature(command));
	}

	@Test
	public void testDescribeRegionsCommand() throws Exception {
		DescribeRegionsCommand command = new DescribeRegionsCommand(ep);
		System.out.println(command.execute());
	}

	@Test
	public void testGetRegions() throws Exception {
		System.out.println(ep.getRegions());
	}

	@Test
	public void testGetInstances() throws Exception {
		System.out.println(ep.getInstances());
	}

}
