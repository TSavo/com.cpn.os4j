package com.cpn.os4j.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class ExternalNetwork  implements Serializable{

	private static final long serialVersionUID = 7818000619955853265L;
    private String status;
    private List<String> subnets;
    private String name;
    private String physicalNetwork;
    private Boolean adminStateUp=Boolean.FALSE;
    private String tenantId;
    private String networkType;
    private Boolean external=Boolean.FALSE;
    private Boolean shared=Boolean.FALSE;
    private String id;
    private Integer segmentationId;
    

    public ExternalNetwork() {
    }
    @JsonIgnore
    public String getStatus() {
        return status;
    }

    public void setStatus(final String aStatus) {
        this.status = aStatus;
    }
    @JsonIgnore
    public List<String> getSubnets() {
        return subnets;
    }

    public void setSubnets(final List<String> someSubnets) {
        this.subnets = someSubnets;
    }

    public String getName() {
        return name;
    }

    public void setName(final String aName) {
        this.name = aName;
    }
    @JsonIgnore
    public String getPhysicalNetwork() {
        return physicalNetwork;
    }
    @JsonProperty("provider:physical_network")
    public void setPhysicalNetwork(final String aPhysicalNetwork) {
        this.physicalNetwork = aPhysicalNetwork;
    }

    public Boolean getAdminStateUp() {
        return adminStateUp;
    }
    @JsonProperty("admin_state_up")
    public void setAdminStateUp(final Boolean anAdminStateUp) {
        this.adminStateUp = anAdminStateUp;
    }

    public String getTenantId() {
        return tenantId;
    }
    @JsonProperty("tenant_id")
    public void setTenantId(final String aTenantId) {
        this.tenantId = aTenantId;
    }
    @JsonIgnore
    public String getNetworkType() {
        return networkType;
    }
    @JsonProperty("provider:network_type")
    public void setNetworkType(final String aNetworkType) {
        this.networkType = aNetworkType;
    }

    public Boolean getExternal() {
        return external;
    }
    @JsonProperty("router:external")
    public void setExternal(final Boolean anExternal) {
        this.external = anExternal;
    }
    @JsonIgnore
    public Boolean getShared() {
        return shared;
    }

    public void setShared(final Boolean aShared) {
        this.shared = aShared;
    }
    @JsonIgnore
    public String getId() {
        return id;
    }
    @JsonProperty("id")
    public void setId(final String anId) {
        this.id = anId;
    }
    @JsonIgnore
    public Integer getSegmentationId() {
        return segmentationId;
    }
    @JsonProperty("provider:segmentation_id")
    public void setSegmentationId(final Integer aSegmentationId) {
        this.segmentationId = aSegmentationId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this.getClass()).append("status", status).append("subnets", subnets).append("name", name).append("physicalNetwork", physicalNetwork).append("adminStateUp", adminStateUp).append("tenantId", tenantId).append("networkType", networkType).append("external", external).append("shared", shared).append("id", id).append("segmentationId", segmentationId).toString();
    }

}
