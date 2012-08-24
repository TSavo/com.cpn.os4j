package com.cpn.os4j.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class VolumeAttachmentResponse {

	VolumeAttachment volumeAttachment;
	List<VolumeAttachment> volumeAttachments;
	public VolumeAttachment getVolumeAttachment() {
		return volumeAttachment;
	}
	public void setVolumeAttachment(VolumeAttachment volumeAttachment) {
		this.volumeAttachment = volumeAttachment;
	}
	public List<VolumeAttachment> getVolumeAttachments() {
		return volumeAttachments;
	}
	public void setVolumeAttachments(List<VolumeAttachment> volumeAttachments) {
		this.volumeAttachments = volumeAttachments;
	}
	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("volumeAttachment", volumeAttachment).append("volumeAttachments", volumeAttachments);
		return builder.toString();
	}
}