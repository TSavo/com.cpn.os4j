package com.cpn.os4j.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class VolumeAttachmentResponse {

	VolumeAttachment volumeAttachment;
	List<VolumeAttachment> volumeAttachments;

	public VolumeAttachment getVolumeAttachment() {
		return volumeAttachment;
	}

	public List<VolumeAttachment> getVolumeAttachments() {
		return volumeAttachments;
	}

	public void setVolumeAttachment(final VolumeAttachment volumeAttachment) {
		this.volumeAttachment = volumeAttachment;
	}

	public void setVolumeAttachments(final List<VolumeAttachment> volumeAttachments) {
		this.volumeAttachments = volumeAttachments;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("volumeAttachment", volumeAttachment).append("volumeAttachments", volumeAttachments);
		return builder.toString();
	}
}