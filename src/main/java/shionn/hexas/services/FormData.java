package shionn.hexas.services;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class FormData {

	private byte[] data;
	private String name;

	public byte[] getData() {
		return data;
	}

	public String getName() {
		return name;
	}

	@FormParam("file")
	@PartType("application/octet-stream")
	public void setData(byte[] data) {
		this.data = data;
	}

	@FormParam("name")
	public void setName(String name) {
		this.name = name;
	}

}
