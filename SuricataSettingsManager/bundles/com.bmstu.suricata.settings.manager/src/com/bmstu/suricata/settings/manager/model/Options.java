package com.bmstu.suricata.settings.manager.model;

import java.util.Collection;

/**
 *
 * Instance of this class represents rule options.
 *
 * @author Nikolaev
 *
 */
public class Options {

	private Collection<String> values;

	/**
	 *
	 * Constructor.
	 *
	 * @param values - options values. Can't be <code>null</code>.
	 */
	public Options(Collection<String> values) {
		this.values = values;
	}

	/**
	 *
	 * Returns options values.
	 *
	 * @return options values. Can't return <code>null</code>.
	 */
	public Collection<String> getValue() {
		return values;
	}
}
