package org.jhe.ignite.spike.model;

public class SomeType {
	public String id;
	public String value;

	public SomeType() {
		super();
	}

	public SomeType(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
