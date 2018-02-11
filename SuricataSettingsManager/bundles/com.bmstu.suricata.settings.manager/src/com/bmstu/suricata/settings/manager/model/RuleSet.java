package com.bmstu.suricata.settings.manager.model;

/**
 *
 * Instance of this class represents suricata rule set.
 *
 * @author Nikolaev
 *
 */
public class RuleSet {
	private String name;

	/**
	 *
	 * Constructor.
	 *
	 * @param name - rule set name. Can't be <code>null</code>.
	 */
	public RuleSet(String name) {
		this.name = name;
	}

	/**
	 *
	 * Returns rule name.
	 *
	 * @return rule name. Can't return <code>null</code>.
	 */
	public String getName() {
		return name;
	}
}
