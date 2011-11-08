package com.cpn.os4j.command;

import com.cpn.os4j.Image;
import com.cpn.os4j.OpenStack;

public class DescribeImagesCommand extends AbstractOpenStackCommand<Image> {
	
	public DescribeImagesCommand(OpenStack anEndPoint) {
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
