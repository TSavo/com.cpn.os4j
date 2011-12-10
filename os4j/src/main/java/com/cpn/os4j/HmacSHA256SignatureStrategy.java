package com.cpn.os4j;

import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cpn.os4j.command.OpenStackCommand;

public class HmacSHA256SignatureStrategy implements SignatureStrategy {
	private static final String CARRIGE_RETURN = "\n";

	@Override
	public String getSignature(final OpenStackCommand<?> aCommand) {
		Mac mac;
		try {
			mac = Mac.getInstance("HmacSHA256");
			mac.init(new SecretKeySpec(aCommand.getEndPoint().getCredentials().getSecretKey().getBytes(), "HmacSHA256"));
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		mac.update(aCommand.getVerb().getBytes());
		mac.update(CARRIGE_RETURN.getBytes());
		mac.update(aCommand.getEndPoint().getURI().getHost().getBytes());
		mac.update(":".getBytes());
		mac.update(new Integer(aCommand.getEndPoint().getURI().getPort()).toString().getBytes());
		mac.update(CARRIGE_RETURN.getBytes());
		mac.update(aCommand.getEndPoint().getURI().getPath().getBytes());
		mac.update(CARRIGE_RETURN.getBytes());
		final StringBuffer sb = new StringBuffer();
		for (final String s : aCommand.getQueryString().keySet()) {
			if (sb.length() > 0) {
				sb.append("&");
			}
			sb.append(s + "=" + aCommand.getQueryString().get(s));
		}
		mac.update(sb.toString().getBytes());

		return Base64.encodeBase64String(mac.doFinal()).trim();
	}

	@Override
	public String getSignatureMethod() {
		return "HmacSHA256";
	}

	@Override
	public int getSignatureVersion() {
		return 2;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("getSignatureMethod()", getSignatureMethod()).append("getSignatureVersion()", getSignatureVersion());
		return builder.toString();
	}

}
