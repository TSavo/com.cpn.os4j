package com.cpn.os4j.command;

import com.cpn.os4j.EndPoint;
import com.cpn.os4j.model.KeyPair;

public class DescribeKeyPairsCommand extends AbstractOpenStackCommand<KeyPair> {

	public DescribeKeyPairsCommand(final EndPoint anEndPoint) {
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
