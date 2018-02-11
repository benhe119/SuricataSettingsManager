package com.bmstu.suricata.settings.manager.model;

/**
 *
 * Instance of this class represents rule action.
 *
 * @author Nikolaev
 *
 */
public class Action {

	private String name;

	/**
	 *
	 * Constructor.
	 *
	 * @param name - rule name. Can't be <code>null</code>.
	 */
	public Action(String name) {
		this.name = name;
	}

	/**
	 *
	 * Returns action name.
	 *
	 * @return action name. Can't return <code>null</code>.
	 */
	public String getName() {
		return name;
	}
}
