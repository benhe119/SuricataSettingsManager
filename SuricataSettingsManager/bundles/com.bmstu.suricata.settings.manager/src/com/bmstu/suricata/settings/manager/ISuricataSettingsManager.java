package com.bmstu.suricata.settings.manager;

import com.bmstu.suricata.settings.manager.model.Rule;
import com.bmstu.suricata.settings.manager.model.RuleSet;

/**
 *
 * Instance of this class can change suricata settings.
 *
 * @author Nikolaev
 *
 */
public interface ISuricataSettingsManager {

	/**
	 *
	 * Adds rule set to suricata.
	 *
	 * @param ruleSet - rule to add. Can't be <code>null</code>.
	 */
	void addRuleSet(RuleSet ruleSet);

	/**
	 *
	 * Removes rule set from suricata.
	 *
	 * @param ruleSet - rule to remove. Can't be <code>null</code>.
	 */
	void removeRuleSet(RuleSet ruleSet);

	/**
	 *
	 * Adds rule to rule set that can be found using given <code>path</code>.
	 *
	 * @param path - suricata root folder relative path to rule set. Can't be <code>null</code>.
	 * @param rule - rule to add. Can't be <code>null</code>.
	 */
	void addRule(String path, Rule rule);

	/**
	 *
	 * Removes rule from rule set that can be found using given <code>path</code>.
	 *
	 * @param path - suricata root folder relative path to rule set. Can't be <code>null</code>.
	 * @param rule - rule to remove. Can't be <code>null</code>.
	 */
	void removeRule(String path, Rule rule);
}
