package com.cpn.os4j.command;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.HttpResponseException;

import com.cpn.os4j.model.ServerError;

@SuppressWarnings("serial")
@Immutable
public class ServerErrorException extends HttpResponseException {
	private final OpenStackCommand<?> command;
	private final List<ServerError> errors;
	private final String rawBody;
	private final int statusCode;

	public ServerErrorException(final int statusCode, final List<ServerError> errors, final String rawBody, final OpenStackCommand<?> command) {
		super(statusCode, rawBody);
		this.errors = errors;
		this.statusCode = statusCode;
		this.rawBody = rawBody;
		this.command = command;
	}

	public OpenStackCommand<?> getCommand() {
		return command;
	}

	public List<ServerError> getErrors() {
		return errors;
	}

	public String getRawBody() {
		return rawBody;
	}

	@Override
	public int getStatusCode() {
		return statusCode;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("errors", errors).append("statusCode", statusCode).append("command", command);
		return builder.toString();
	}
}
