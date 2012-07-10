package com.cpn.os4j.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public abstract class AbstractOpenStackModel {
	
	List<Link> links;
	
	@JsonIgnore
	public String getSelfRef(){
		for(Link l : links){
			if("self".equals(l.getRel())){
				return l.getHref();
			}
		}
		return null;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
}
