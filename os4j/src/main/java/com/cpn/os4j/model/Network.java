
package com.cpn.os4j.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Network
    implements Serializable
{
    private String status;
    private List<String> subnets;
    private String name;
    @JsonProperty("provider:physical_network")
    private String physicalNetwork;
    @JsonProperty("admin_state_up")
    private Boolean adminStateUp=Boolean.FALSE;
    @JsonProperty("tenant_id")
    private String tenantId;
    @JsonProperty("provider:network_type")
    private String networkType;
    @JsonProperty("router:external")
    private Boolean external=Boolean.FALSE;
    private Boolean shared=Boolean.FALSE;
    private String id;
    @JsonProperty("provider:segmentation_id")
    private Integer segmentationId;
    
    private final static long serialVersionUID = 7606961362173971513L;

    public Network() {
    }
  
    public String getStatus() {
        return status;
    }

    public void setStatus(final String aStatus) {
        this.status = aStatus;
    }
    
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
    public String getPhysicalNetwork() {
        return physicalNetwork;
    }
    public void setPhysicalNetwork(final String aPhysicalNetwork) {
        this.physicalNetwork = aPhysicalNetwork;
    }

    public Boolean getAdminStateUp() {
        return adminStateUp;
    }
    public void setAdminStateUp(final Boolean anAdminStateUp) {
        this.adminStateUp = anAdminStateUp;
    }

    public String getTenantId() {
        return tenantId;
    }
    public void setTenantId(final String aTenantId) {
        this.tenantId = aTenantId;
    }

    public String getNetworkType() {
        return networkType;
    }
    public void setNetworkType(final String aNetworkType) {
        this.networkType = aNetworkType;
    }

    public Boolean getExternal() {
        return external;
    }
    public void setExternal(final Boolean anExternal) {
        this.external = anExternal;
    }
    public Boolean getShared() {
        return shared;
    }

    public void setShared(final Boolean aShared) {
        this.shared = aShared;
    }
    public String getId() {
        return id;
    }
    public void setId(final String anId) {
        this.id = anId;
    }
    public Integer getSegmentationId() {
        return segmentationId;
    }
    public void setSegmentationId(final Integer aSegmentationId) {
        this.segmentationId = aSegmentationId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this.getClass()).append("status", status).append("subnets", subnets).append("name", name).append("physicalNetwork", physicalNetwork).append("adminStateUp", adminStateUp).append("tenantId", tenantId).append("networkType", networkType).append("external", external).append("shared", shared).append("id", id).append("segmentationId", segmentationId).toString();
    }

}
