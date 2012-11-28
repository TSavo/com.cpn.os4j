
package com.cpn.os4j.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Autogenerated by APIOMatic.
 * 
 */
public class Port
    implements Serializable
{

    @JsonProperty("network_id")
    private String networkId;
    @JsonProperty("fixed_ips")
    private List<Fixedips> fixedIps;
    @JsonProperty("admin_state_up")
    private boolean adminStateUp;
    private String id;
    private final static long serialVersionUID = 3838636331252864045L;

    public Port() {
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(final String aNetworkId) {
        this.networkId = aNetworkId;
    }

    public List<Fixedips> getFixedIps() {
        return fixedIps;
    }

    public void setFixedIps(final List<Fixedips> someFixedIps) {
        this.fixedIps = someFixedIps;
    }

    public boolean getAdminStateUp() {
        return adminStateUp;
    }

    public void setAdminStateUp(final boolean anAdminStateUp) {
        this.adminStateUp = anAdminStateUp;
    }

    @JsonIgnore
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this.getClass()).append("id", id).append("networkId", networkId).append("fixedIps", fixedIps).append("adminStateUp", adminStateUp).toString();
    }

}
