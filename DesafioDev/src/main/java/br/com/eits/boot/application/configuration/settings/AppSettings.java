package br.com.eits.boot.application.configuration.settings;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("app")
public class AppSettings
{
	/**
	 * URL externa da aplicação.
	 */
	private String externalUrl;

	/**
	 * Remetente padrão para envio de emails.
	 */
	private String mailFrom;

	/**
	 * Tempo de expiração do token de redefinição de senha.
	 */
	private int passwordTokenExpirationHours;

	public String getExternalUrl() {
		return externalUrl;
	}

	public void setExternalUrl(String externalUrl) {
		this.externalUrl = externalUrl;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public int getPasswordTokenExpirationHours() {
		return passwordTokenExpirationHours;
	}

	public void setPasswordTokenExpirationHours(int passwordTokenExpirationHours) {
		this.passwordTokenExpirationHours = passwordTokenExpirationHours;
	}
}
