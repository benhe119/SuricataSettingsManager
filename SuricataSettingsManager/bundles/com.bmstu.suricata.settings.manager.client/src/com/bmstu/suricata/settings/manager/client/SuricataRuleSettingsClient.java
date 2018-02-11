package com.bmstu.suricata.settings.manager.client;

import java.util.Arrays;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.bmstu.suricata.settings.manager.ISuricataSettingsManager;
import com.bmstu.suricata.settings.manager.model.Action;
import com.bmstu.suricata.settings.manager.model.Header;
import com.bmstu.suricata.settings.manager.model.Options;
import com.bmstu.suricata.settings.manager.model.Rule;

/**
*
* Client for suricata settings. Adds rule to suricata on startup.
*
* @author Nikolaev
*
*/
@Component(immediate = true)
public class SuricataRuleSettingsClient {
	private static final String RULE_PATH = "rules\\custom.rules";

	private ISuricataSettingsManager suricataSettingsManager;

	@Activate
	public void activate(Map<String, Object> properties) {
		suricataSettingsManager.addRule(RULE_PATH, createRule());
		//suricataSettingsManager.removeRule(RULE_PATH, createRule());
	}

	@Reference(unbind = "-")
	public void bindSuricataSettingsManager(ISuricataSettingsManager suricataSettingsManager) {
		this.suricataSettingsManager = suricataSettingsManager;
	}

	private Rule createRule() {
		return new Rule(new Action("alert"), new Header("http", "any", "any", "->", "any", "any"), new Options(Arrays.asList("msg:\"cutom message\"")));
	}
}
