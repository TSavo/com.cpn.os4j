package com.cpn.os4j;

import org.junit.Test;

import com.cpn.os4j.model.Server;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServerResponseTest {

	@Test
	public void test() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Server server = mapper.readValue(ServerResponseTest.class.getResourceAsStream("/server.json"), Server.class);
		
		mapper.writeValue(System.out, server);
	}

}
