package com.cpn.os4j;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.TreeMap;

import org.junit.Ignore;
import org.junit.Test;

import com.cpn.os4j.command.OpenStackCommand;
import com.cpn.os4j.command.ServerErrorException;
import com.cpn.os4j.model.Image;
import com.cpn.os4j.model.KeyPair;
import com.cpn.os4j.model.SecurityGroup;
import com.cpn.os4j.model.Volume;

public class OpenStackTest {

	private static EndPoint ep;

	static {
		try {
			//ep = new OpenStackEndPoint(new URI("http://10.101.100.100:8773/services/Cloud"), new Credentials() {
				ep = new OpenStackEndPoint(new URI("http://10.1.10.249:8773/services/Cloud"), new Credentials() {

				@Override
				public String getAccessKey() {
//					return "2c52532c-94b5-4298-89a5-002b4cc82a32%3Avsp";
						return "1cf01825-1303-4fad-a49e-2776a5077bd1%3Avsp";
				}

				@Override
				public String getSecretKey() {
					//return "70d45ff2-6958-4d25-96d0-1e79bd5a5dec";
					return "666a4776-19be-4d0e-b2a8-1735dd151606";
				}
			});
		} catch (final URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (final ServerErrorException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (final IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Test
	public void testDescribeAvailabilityZones() throws Exception{
		System.out.println(ep.getAvailabilityZones());
	}
	@Test
	public void testCreateVolume() throws Exception {
		System.out.println(ep.createVolume("nova", 18).waitUntilAvailable(120000).delete().waitUntilDeleted(120000));
	}

	@Test
	public void testDescribeSnapshots() throws Exception {
		System.out.println(ep.getSnapshots());
	}

	@Test
	@Ignore
	public void testGetConsoleOutput() throws Exception {
		System.out.println(ep.getInstances().get(0).getConsoleOutput());
	}

	@Test
	public void testGetImages() throws Exception {
		System.out.println(ep.getImages());
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
	public void testGetRegions() throws Exception {
		System.out.println(ep.getRegions());
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
	public void testHmacSHA256SignatureStrategy() throws Exception {

		final TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("AWSAccessKeyId", "ed391003-88b9-4408-9e87-1a87ba460121%3Avsp");
		map.put("Action", "DescribeInstances");
		map.put("SignatureMethod", "HmacSHA256");
		map.put("SignatureVersion", "2");
		map.put("Timestamp", "2011-11-03T16%3A41%3A58");
		map.put("Version", "2009-11-30");

		final OpenStackEndPoint os = mock(OpenStackEndPoint.class);
		final URI uri = new URI("http://10.1.10.249:8773/services/Cloud/");
		final Credentials creds = mock(Credentials.class);

		when(os.getURI()).thenReturn(uri);
		when(os.getCredentials()).thenReturn(creds);
		when(creds.getSecretKey()).thenReturn("e23517e4-f511-47f3-8b93-3e595a0607ca");

		final OpenStackCommand<?> command = mock(OpenStackCommand.class);
		when(command.getQueryString()).thenReturn(map);
		when(command.getVerb()).thenReturn("GET");
		when(command.getEndPoint()).thenReturn(os);

		final SignatureStrategy strategy = new HmacSHA256SignatureStrategy();

		assertEquals("U449qSt/iwFseMq1OTpiLXokU51OEflGegNIHl4dx4Q=", strategy.getSignature(command));
	}

	@Test
	public void testKeyPairs() throws Exception {
		System.out.println(ep.getKeyPairs());
	}

	@Test
	public void testRebootInstances() throws Exception {
		final Image image = ep.getImages().get(0);
		final KeyPair key = ep.getKeyPairs().get(0);
		final SecurityGroup sg = ep.getSecurityGroups().get(0);
		image.runInstance(key, "m1.large", "public", 1, 1, null, null, sg).waitUntilRunning(120000).reboot().waitUntilRunning(120000).terminate();
	}

	@Test
	public void testReleaseAndAllocateAddress() throws Exception {
		System.out.println(ep.allocateIPAddress().release());
	}

	@Test
	@Ignore /* Snapshots break volumes... */
	public void testSnapshotFromImage() throws Exception {
		final Volume v = ep.getVolumes().get(0);
		System.out.println(v.createSnapshot().waitUntilAvailable().delete());
	}

	@Test
	public void testWaitUntilRunningThenTerminate() throws Exception {
		final Image image = ep.getImages().get(0);
		final KeyPair key = ep.getKeyPairs().get(0);
		final SecurityGroup sg = ep.getSecurityGroups().get(0);
		System.out.println("Starting: " + new Date());
		image.runInstance(key, "m1.large", "public", 1, 1, null, null, sg).waitUntilRunning(120000).terminate().waitUntilTerminated(120000);
		System.out.println("Finished: " + new Date());
	}

}
