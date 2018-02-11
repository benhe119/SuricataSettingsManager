package com.bmstu.suricata.settings.manager.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import com.bmstu.suricata.settings.manager.ISuricataSettingsManager;
import com.bmstu.suricata.settings.manager.model.RuleSet;

/**
 *
 * Tests for suricata settings manager.
 *
 * @author Nikolaev
 *
 */
public class MainTests {

	private static final String RULE_NAME = "http-events.rules";

	@Test
	public void addRuleTest() {
		ISuricataSettingsManager manager = getService(ISuricataSettingsManager.class);
		assertNotNull("Log managet not found", manager);

		manager.addRuleSet(new RuleSet(RULE_NAME));
	}

	@Test
	public void removeRuleTest() {
		ISuricataSettingsManager manager = getService(ISuricataSettingsManager.class);
		assertNotNull("Log managet not found", manager);

		manager.removeRuleSet(new RuleSet(RULE_NAME));
	}

	static <T> T getService(Class<T> clazz) {
		Bundle bundle = FrameworkUtil.getBundle(MainTests.class);
		if (bundle != null) {
			ServiceTracker<T, T> st = new ServiceTracker<T, T>(bundle.getBundleContext(), clazz, null);
			st.open();

			try {
				// give the runtime some time to startup
				return st.waitForService(500);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
