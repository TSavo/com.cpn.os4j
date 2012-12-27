
package com.cpn.os4j.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(Include.NON_NULL)
public class Port
    implements Serializable
{

    @JsonProperty("network_id")
    private String networkId;
    @JsonProperty("fixed_ips")
    private List<FixedIp> fixedIps;
    @JsonProperty("admin_state_up")
    private boolean adminStateUp;
    private String id;
    private final static long serialVersionUID = 3838636331252864045L;
    private String status;
    private String name;
    @JsonProperty("tenant_id")
    private String tenantId;
    @JsonProperty("device_owner")
    private String deviceOwner;
    @JsonProperty("mac_address")
    private String macAddress;
    @JsonProperty("device_id")
    private String deviceId;

    public Port() {
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(final String aNetworkId) {
        this.networkId = aNetworkId;
    }

    public List<FixedIp> getFixedIps() {
        return fixedIps;
    }

    public void setFixedIps(final List<FixedIp> someFixedIps) {
        this.fixedIps = someFixedIps;
    }

    public boolean getAdminStateUp() {
        return adminStateUp;
    }

    public void setAdminStateUp(final boolean anAdminStateUp) {
        this.adminStateUp = anAdminStateUp;
    }

    public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(final String tenantId) {
		this.tenantId = tenantId;
	}

	public String getDeviceOwner() {
		return deviceOwner;
	}

	public void setDeviceOwner(final String deviceOwner) {
		this.deviceOwner = deviceOwner;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(final String macAddress) {
		this.macAddress = macAddress;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(final String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this.getClass()).append("id", id).append("networkId", networkId).append("fixedIps", fixedIps).append("adminStateUp", adminStateUp)
        .append("id", id).append("name", name).append("tenantId", tenantId).append("deviceOwner", deviceOwner).
        append("macAddress", macAddress).append("deviceId", deviceId).toString();
    }

}
