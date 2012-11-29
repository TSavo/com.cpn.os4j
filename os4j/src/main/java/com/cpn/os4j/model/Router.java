
package com.cpn.os4j.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(Include.NON_NULL)
public class Router
    implements Serializable
{

    @JsonIgnore
	private String status;
    private String name;
    @JsonProperty("admin_state_up")
    private Boolean adminStateUp;
    @JsonProperty("tenant_id")
    private String tenantId;
    private String id;
    @JsonProperty("external_gateway_info")
    private ExternalGatewayInfo externalGatewayInfo;
    private final static long serialVersionUID = 377025078575909614L;

    public Router() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String aStatus) {
        this.status = aStatus;
    }
    public String getName() {
        return name;
    }
    public void setName(final String aName) {
        this.name = aName;
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
    public String getId() {
        return id;
    }
    public void setId(final String anId) {
        this.id = anId;
    }

    public ExternalGatewayInfo getExternalGatewayInfo() {
        return externalGatewayInfo;
    }

    public void setExternalGatewayInfo(final ExternalGatewayInfo anExternalGatewayInfo) {
        this.externalGatewayInfo = anExternalGatewayInfo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this.getClass()).append("status", status).append("name", name).append("adminStateUp", adminStateUp).append("tenantId", tenantId).append("id", id).append("externalGatewayInfo", externalGatewayInfo).toString();
    }

}
