package com.cpn.os4j.glance;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class GlanceImage {

	private String id;
	private String uri;
	private String name;
	private String diskFormat;
	private String containerFormat;
	private long size;
	private String checksum;
	private Date createdAt;
	private Date updatedAt;
	private Date deletedAt;
	private String status;
	private boolean deleted;
	private boolean isPublic;
	private boolean isProtected;
	private long minRam;
	private long minDisk;
	private String owner;
	private Map<String, String> properties = new HashMap<>();

	@JsonIgnore
	private transient HttpClient client = new HttpClient();

	public GlanceImage() {

	}

	public GlanceImage(final String aUri) {
		uri = aUri;
	}

	public void download(final OutputStream stream) {
		final GetMethod method = new GetMethod(getUri());
		try {
			client.executeMethod(method);
			final byte[] buffer = new byte[4096];
			int n = 0;
			final InputStream input = method.getResponseBodyAsStream();
			while (-1 != (n = input.read(buffer))) {
				stream.write(buffer, 0, n);
			}
		} catch (final Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			method.releaseConnection();
		}
	}

	public String getChecksum() {
		return checksum;
	}

	@JsonIgnore
	public HttpClient getClient() {
		return client;
	}

	@JsonProperty(value = "container_format")
	public String getContainerFormat() {
		return containerFormat;
	}

	@JsonProperty(value = "created_at")
	public Date getCreatedAt() {
		return createdAt;
	}

	@JsonProperty(value = "deleted_at")
	public Date getDeletedAt() {
		return deletedAt;
	}

	@JsonProperty(value = "disk_format")
	public String getDiskFormat() {
		return diskFormat;
	}

	public String getId() {
		return id;
	}

	@JsonProperty(value = "min_disk")
	public long getMinDisk() {
		return minDisk;
	}

	@JsonProperty(value = "min_ram")
	public long getMinRam() {
		return minRam;
	}

	public String getName() {
		return name;
	}

	public String getOwner() {
		return owner;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public long getSize() {
		return size;
	}

	public String getStatus() {
		return status;
	}

	@JsonProperty(value = "updated_at")
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public String getUri() {
		return uri;
	}

	@JsonProperty(value = "deleted")
	public boolean isDeleted() {
		return deleted;
	}

	@JsonProperty(value = "protected")
	public boolean isProtected() {
		return isProtected;
	}

	@JsonProperty(value = "is_public")
	public boolean isPublic() {
		return isPublic;
	}

	public void populate() throws HttpException, IOException {
		final HeadMethod method = new HeadMethod(uri);
		client.executeMethod(method);
		populateFromHeaders(method.getResponseHeaders());
	}

	public void populateFromHeaders(final Header[] someHeaders) {
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		for (final Header h : someHeaders) {
			if (h.getName().equals("x-image-meta-uri")) {
				setUri(h.getValue());
			}
			if (h.getName().equals("x-image-meta-name")) {
				setName(h.getValue());
			}
			if (h.getName().equals("x-image-meta-disk_format")) {
				setDiskFormat(h.getValue());
			}
			if (h.getName().equals("x-image-meta-container_format")) {
				setContainerFormat(h.getValue());
			}
			if (h.getName().equals("x-image-meta-size")) {
				setSize(Long.parseLong(h.getValue()));
			}
			if (h.getName().equals("x-image-meta-checksum")) {
				setChecksum(h.getValue());
			}
			if (h.getName().equals("x-image-meta-created_at")) {
				try {
					setCreatedAt(dateFormat.parse(h.getValue()));
				} catch (final ParseException e) {
				}
			}
			if (h.getName().equals("x-image-meta-updated_at")) {
				try {
					setUpdatedAt(dateFormat.parse(h.getValue()));
				} catch (final ParseException e) {
				}
			}
			if (h.getName().equals("x-image-meta-status")) {
				setStatus(h.getValue());
			}
			if (h.getName().equals("x-image-meta-is-public")) {
				setPublic(Boolean.parseBoolean(h.getValue()));
			}
			if (h.getName().equals("x-image-meta-min-ram")) {
				setMinRam(Long.parseLong(h.getValue()));
			}
			if (h.getName().equals("x-image-meta-min-disk")) {
				setMinDisk(Long.parseLong(h.getValue()));
			}
			if (h.getName().equals("x-image-meta-owner")) {
				setOwner(h.getValue());
			}
			if (h.getName().contains("x-image-meta-property-")) {
				getProperties().put(h.getName().replaceAll("x-image-meta-property-", ""), h.getValue());
			}
		}
	}

	public void setChecksum(final String checksum) {
		this.checksum = checksum;
	}

	@JsonIgnore
	public void setClient(final HttpClient client) {
		this.client = client;
	}

	@JsonProperty(value = "container_format")
	public void setContainerFormat(final String containerFormat) {
		this.containerFormat = containerFormat;
	}

	@JsonProperty(value = "created_at")
	public void setCreatedAt(final Date createdAt) {
		this.createdAt = createdAt;
	}

	@JsonProperty(value = "deleted")
	public void setDeleted(final boolean deleted) {
		this.deleted = deleted;
	}

	@JsonProperty(value = "deleted_at")
	public void setDeletedAt(final Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	@JsonProperty(value = "disk_format")
	public void setDiskFormat(final String diskFormat) {
		this.diskFormat = diskFormat;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public GlanceImage setMetadata() {
		final PutMethod method = new PutMethod(uri);
		try {
			method.addRequestHeader("x-image-meta-name", getName());
			if (getDiskFormat() != null) {
				method.addRequestHeader("x-image-meta-disk_format", getDiskFormat());
			}
			if (getDiskFormat() != null) {
				method.addRequestHeader("x-image-meta-disk_format", getDiskFormat());
			}
			if (getContainerFormat() != null) {
				method.addRequestHeader("x-image-meta-container_format", getContainerFormat());
			}
			if (getSize() > 0l) {
				method.addRequestHeader("x-image-meta-size", new Long(getSize()).toString());
			}
			if (getChecksum() != null) {
				method.addRequestHeader("x-image-meta-checksum", getChecksum());
			}
			method.addRequestHeader("x-image-meta-checksum", new Boolean(isPublic()).toString());
			if (getMinRam() > 0) {
				method.addRequestHeader("x-image-meta-min-ram", new Long(getMinRam()).toString());
			}
			if (getMinDisk() > 0) {
				method.addRequestHeader("x-image-meta-min-disk", new Long(getMinDisk()).toString());
			}
			if (getOwner() != null) {
				method.addRequestHeader("x-image-meta-owner", getOwner());
			}
			for (final Entry<String, String> kv : getProperties().entrySet()) {
				method.addRequestHeader("x-image-meta-property-" + kv.getKey(), kv.getValue());
			}
			client.executeMethod(method);
			populateFromHeaders(method.getResponseHeaders());
			return this;
		} catch (final IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			method.releaseConnection();
		}
	}

	@JsonProperty(value = "min_disk")
	public void setMinDisk(final long minDisk) {
		this.minDisk = minDisk;
	}

	@JsonProperty(value = "min_ram")
	public void setMinRam(final long minRam) {
		this.minRam = minRam;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setOwner(final String owner) {
		this.owner = owner;
	}

	public void setProperties(final Map<String, String> properties) {
		this.properties = properties;
	}

	@JsonProperty(value = "protected")
	public void setProtected(final boolean isProtected) {
		this.isProtected = isProtected;
	}

	@JsonProperty(value = "is_public")
	public void setPublic(final boolean isPublic) {
		this.isPublic = isPublic;
	}

	public void setSize(final long size) {
		this.size = size;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@JsonProperty(value = "updated_at")
	public void setUpdatedAt(final Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setUri(final String uri) {
		this.uri = uri;
	}

}
