package com.cpn.os4j;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.TreeMap;

import org.junit.Test;

import com.cpn.os4j.command.OpenStackCommand;
import com.cpn.os4j.command.ServerErrorExeception;
import com.cpn.os4j.model.Image;
import com.cpn.os4j.model.KeyPair;
import com.cpn.os4j.model.SecurityGroup;
import com.cpn.os4j.model.Volume;

public class OpenStackTest {

	private static OpenStack ep;

	static {
		try {
			ep = new OpenStack(new URI("http://10.1.10.249:8773/services/Cloud/"), new OpenStackCredentials() {

				@Override
				public String getSecretKey() {
					return "e23517e4-f511-47f3-8b93-3e595a0607ca";
				}

				@Override
				public String getAccessKey() {
					return "ed391003-88b9-4408-9e87-1a87ba460121%3Avsp";
				}
			});
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (ServerErrorExeception e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
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
		OpenStackCredentials creds = mock(OpenStackCredentials.class);

		when(os.getURI()).thenReturn(uri);
		when(os.getCredentials()).thenReturn(creds);
		when(creds.getSecretKey()).thenReturn("e23517e4-f511-47f3-8b93-3e595a0607ca");

		OpenStackCommand<?> command = mock(OpenStackCommand.class);
		when(command.getQueryString()).thenReturn(map);
		when(command.getVerb()).thenReturn("GET");
		when(command.getEndPoint()).thenReturn(os);

		SignatureStrategy strategy = new HmacSHA256SignatureStrategy();

		assertEquals("U449qSt/iwFseMq1OTpiLXokU51OEflGegNIHl4dx4Q=", strategy.getSignature(command));
	}

	@Test
	public void testGetRegions() throws Exception {
		System.out.println(ep.getRegions());
	}

	@Test
	public void testGetInstances() throws Exception {
		System.out.println(ep.getInstances());
	}

	@Test
	public void testGetIpAddresses() throws Exception {
		System.out.println(ep.getIPAddresses());
	}

	@Test
	public void testGetSecurityGroups() throws Exception {
		System.out.println(ep.getSecurityGroups());
	}

	@Test
	public void testGetVolumes() throws Exception {
		System.out.println(ep.getVolumes());
	}

	@Test
	public void testGetImages() throws Exception {
		System.out.println(ep.getImages());
	}

	@Test
	public void testKeyPairs() throws Exception {
		System.out.println(ep.getKeyPairs());
	}

	@Test
	public void testRebootInstances() throws Exception {
		ep.getInstances().get(0).reboot();
	}

	@Test
	public void testGetConsoleOutput() throws Exception {
		System.out.println(ep.getInstances().get(0).getConsoleOutput());
	}

	@Test
	public void testWaitUntilRunningThenTerminate() throws Exception {
		Image image = ep.getImages().get(0);
		KeyPair key = ep.getKeyPairs().get(0);
		SecurityGroup sg = ep.getSecurityGroups().get(0);
		System.out.println("Starting: " + new Date());
		image.runInstance(key, "m1.large", "public", "1", "1", sg).waitUntilRunning().terminate().waitUntilTerminated();
		System.out.println("Finished: " + new Date());
	}

	@Test
	public void testReleaseAndAllocateAddress() throws Exception {
		System.out.println(ep.allocateIPAddress().release());
	}

	@Test
	public void testCreateVolume() throws Exception {
		System.out.println(ep.createVolume("nova", 18).waitUntilAvailable().delete().waitUntilDeleted());
	}

	@Test
	public void testDescribeSnapshots() throws Exception {
		System.out.println(ep.getSnapshots());
	}

	@Test
	public void testSnapshotFromImage() throws Exception {
		Volume v = ep.getVolumes().get(0);
		System.out.println(v.createSnapshot().waitUntilAvailable().delete());
	}

}
