package com.bmstu.suricata.settings.manager.client;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.bmstu.suricata.settings.manager.ISuricataSettingsManager;
import com.bmstu.suricata.settings.manager.model.RuleSet;

/**
 *
 * Client for suricata settings. Adds rule set to suricata on startup.
 *
 * @author Nikolaev
 *
 */
@Component(immediate = true)
public class SuricataRuleSetSettingsClient {

	private static final String RULE_NAME = "http-events.rules";

	private ISuricataSettingsManager suricataSettingsManager;

	@Activate
	public void activate(Map<String, Object> properties) {
		suricataSettingsManager.addRuleSet(new RuleSet(RULE_NAME));
	}

	@Reference(unbind = "-")
	public void bindSuricataSettingsManager(ISuricataSettingsManager suricataSettingsManager)
	{
		this.suricataSettingsManager = suricataSettingsManager;
	}
}
