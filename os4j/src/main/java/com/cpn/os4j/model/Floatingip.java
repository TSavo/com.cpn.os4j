
package com.cpn.os4j.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang.builder.ToStringBuilder;


@JsonInclude(Include.NON_NULL)
public class Floatingip
    implements Serializable
{
	@JsonProperty("floating_network_id")
    private String floatingNetworkId;
	@JsonProperty("tenant_id")
    private String tenantId;
	@JsonProperty("fixed_ip_address")
    private String fixedIpAddress;
	@JsonProperty("floating_ip_address")
    private String floatingIpAddress;
    private String id;
    @JsonProperty("port_id")
    private String portId;
    @JsonProperty("router_id")
    private String routerId;
    private final static long serialVersionUID = 7799287859101738205L;

    public Floatingip() {
    }
    public String getFloatingNetworkId() {
        return floatingNetworkId;
    }
   
    public void setFloatingNetworkId(final String aFloatingNetworkId) {
        this.floatingNetworkId = aFloatingNetworkId;
    }
    public String getTenantId() {
        return tenantId;
    }
  
    public void setTenantId(final String aTenantId) {
        this.tenantId = aTenantId;
    }
    public String getFixedIpAddress() {
        return fixedIpAddress;
    }
  
    public void setFixedIpAddress(final String aFixedIpAddress) {
        this.fixedIpAddress = aFixedIpAddress;
    }
    public String getFloatingIpAddress() {
        return floatingIpAddress;
    }
  
    public void setFloatingIpAddress(final String aFloatingIpAddress) {
        this.floatingIpAddress = aFloatingIpAddress;
    }
    public String getId() {
        return id;
    }
    public void setId(final String anId) {
        this.id = anId;
    }
    public String getPortId() {
        return portId;
    }
    public void setPortId(final String aPortId) {
        this.portId = aPortId;
    }
    public String getRouterId() {
        return routerId;
    }
   
    public void setRouterId(final String aRouterId) {
        this.routerId = aRouterId;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this.getClass()).append("floatingNetworkId", floatingNetworkId).append("tenantId", tenantId).append("fixedIpAddress", fixedIpAddress).append("floatingIpAddress", floatingIpAddress).append("id", id).append("portId", portId).append("routerId", routerId).toString();
    }

}
