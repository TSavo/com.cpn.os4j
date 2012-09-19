package com.cpn.os4j.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

public class SerializedFile implements Serializable {

	private static final long serialVersionUID = 2904773678458586926L;
	String path;
	String contents;
	String postxfer;

	public SerializedFile() {
	}

	public SerializedFile(final String aPath, final byte[] someBytes) {
		path = aPath;
		contents = Base64.encodeBase64String(someBytes);
	}

	public SerializedFile(final String aPath, final byte[] someBytes, final String aPostxfer) {
		this(aPath, someBytes);
		postxfer = aPostxfer;
	}

	public SerializedFile(final String aPath, final File aFile) throws FileNotFoundException, IOException {
		this(aPath, new FileInputStream(aFile));
	}

	public SerializedFile(final String aPath, final InputStream aStream) throws IOException {
		this(aPath, IOUtils.toByteArray(aStream));
	}

	public SerializedFile(final String aPath, final InputStream aStream, final String aPostXfer) throws IOException {
		this(aPath, IOUtils.toByteArray(aStream), aPostXfer);
	}

	public SerializedFile(final String aPath, final String someData) {
		this(aPath, someData.getBytes());
	}

	public String getContents() {
		return contents;
	}

	public String getPath() {
		return path;
	}

	public String getPostxfer() {
		return postxfer;
	}

	public void setContents(final String contents) {
		this.contents = contents;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public void setPostxfer(final String postxfer) {
		this.postxfer = postxfer;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("path", path).append("contents", contents).append("postxfer", postxfer);
		return builder.toString();
	}

}
