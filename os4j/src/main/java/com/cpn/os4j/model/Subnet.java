
package com.cpn.os4j.model;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(Include.NON_NULL)
public class Subnet
    implements Serializable
{

    private String name;
    @JsonProperty("enable_dhcp")
    private Boolean enableDhcp = Boolean.FALSE;
    @JsonProperty("network_id")
    private String networkId;
    @JsonProperty("tenant_id")
    private String tenantId;
    @JsonProperty("dns_nameservers")
    private List<String> dnsNameservers;
    @JsonProperty("allocation_pools")
    private List<IPRange> allocationPools;
    @JsonProperty("host_routes")
    private List<String> hostRoutes;
    @JsonProperty("ip_version")
    private Integer ipVersion;
    @JsonProperty("gateway_ip")
    private String gatewayIp;
    private String cidr;
    private String id;
    private final static long serialVersionUID = 7712246454468308580L;

    public Subnet() {
    }
    public String getName() {
        return name;
    }
   
    public void setName(final String aName) {
        this.name = aName;
    }

    public Boolean getEnableDhcp() {
        return enableDhcp;
    }

    public void setEnableDhcp(final Boolean anEnableDhcp) {
        this.enableDhcp = anEnableDhcp;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(final String aNetworkId) {
        this.networkId = aNetworkId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(final String aTenantId) {
        this.tenantId = aTenantId;
    }
    public List<String> getDnsNameservers() {
        return dnsNameservers;
    }

    public void setDnsNameservers(final List<String> someDnsNameservers) {
        this.dnsNameservers = someDnsNameservers;
    }
    public List<IPRange> getAllocationPools() {
        return allocationPools;
    }
   
    public void setAllocationPools(final List<IPRange> someAllocationPools) {
        this.allocationPools = someAllocationPools;
    }
    public List<String> getHostRoutes() {
        return hostRoutes;
    }
 
    public void setHostRoutes(final List<String> someHostRoutes) {
        this.hostRoutes = someHostRoutes;
    }

    public Integer getIpVersion() {
        return ipVersion;
    }

    public void setIpVersion(final Integer anIpVersion) {
        this.ipVersion = anIpVersion;
    }

    public String getGatewayIp() {
        return gatewayIp;
    }

    public void setGatewayIp(final String aGatewayIp) {
        this.gatewayIp = aGatewayIp;
    }

    public String getCidr() {
        return cidr;
    }

    public void setCidr(final String aCidr) {
        this.cidr = aCidr;
    }

    public String getId() {
        return id;
    }
    public void setId(final String anId) {
        this.id = anId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this.getClass()).append("name", name).append("enableDhcp", enableDhcp).append("networkId", networkId).append("tenantId", tenantId).append("dnsNameservers", dnsNameservers).append("allocationPools", allocationPools).append("hostRoutes", hostRoutes).append("ipVersion", ipVersion).append("gatewayIp", gatewayIp).append("cidr", cidr).append("id", id).toString();
    }

}
