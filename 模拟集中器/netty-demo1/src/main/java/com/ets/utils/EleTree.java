package com.ets.utils;

import java.util.List;

public class EleTree {
	private String id;
	private String name;
	private boolean isLeaf;
	private List<EleTree> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean leaf) {
		isLeaf = leaf;
	}

	public List<EleTree> getChildren() {
		return children;
	}

	public void setChildren(List<EleTree> children) {
		this.children = children;
	}
}
