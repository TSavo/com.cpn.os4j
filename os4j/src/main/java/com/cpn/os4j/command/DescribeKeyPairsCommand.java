package com.cpn.os4j.command;

import com.cpn.os4j.KeyPair;
import com.cpn.os4j.OpenStack;

public class DescribeKeyPairsCommand extends AbstractOpenStackCommand<KeyPair> {

	public DescribeKeyPairsCommand(OpenStack anEndPoint) {
		super(anEndPoint);
	}

	@Override
	public String getAction() {
		return "DescribeKeyPairs";
	}

	@Override
	public Class<KeyPair> getUnmarshallingClass() {
		return KeyPair.class;
	}

	@Override
	public String getUnmarshallingXPath() {
		return "//keySet/item";
	}

}
