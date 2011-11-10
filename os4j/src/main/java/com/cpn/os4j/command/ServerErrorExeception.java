package com.cpn.os4j.command;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.HttpResponseException;

import com.cpn.os4j.model.ServerError;

@SuppressWarnings("serial")
@Immutable
public class ServerErrorExeception extends HttpResponseException {
	private List<ServerError> errors;
	private int statusCode;
	private String rawBody;
	private OpenStackCommand<?> command;

	public ServerErrorExeception(int statusCode, List<ServerError> errors, String rawBody, OpenStackCommand<?> command) {
		super(statusCode, rawBody);
		this.errors = errors;
		this.statusCode = statusCode;
		this.rawBody = rawBody;
		this.command = command;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("errors", errors).append("statusCode", statusCode).append("command", command);
		return builder.toString();
	}

	public List<ServerError> getErrors() {
		return errors;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getRawBody() {
		return rawBody;
	}

	public OpenStackCommand<?> getCommand() {
		return command;
	}
}
