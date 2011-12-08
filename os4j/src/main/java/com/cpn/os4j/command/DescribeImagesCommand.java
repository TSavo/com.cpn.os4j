package com.cpn.os4j.command;

import com.cpn.os4j.EndPoint;
import com.cpn.os4j.model.Image;

public class DescribeImagesCommand extends AbstractOpenStackCommand<Image> {

	public DescribeImagesCommand(final EndPoint anEndPoint) {
		super(anEndPoint);
	}

	@Override
	public String getAction() {
		return "DescribeImages";
	}

	@Override
	public Class<Image> getUnmarshallingClass() {
		return Image.class;
	}

	@Override
	public String getUnmarshallingXPath() {
		return "//imagesSet/item";
	}

}
