package com.bmstu.suricata.settings.manager.model;

/**
*
* Instance of this class represents suricata rule.
*
* @author Nikolaev
*
*/
public class Rule {
	private static final String SPACE = " ";
	private Action action;
	private Header header;
	private Options options;

	/**
	 *
	 * Constructor.
	 *
	 * @param action - rule action. Can't be <code>null</code>.
	 * @param header - rule header. Can't be <code>null</code>.
	 * @param options - rule options. Can't be <code>null</code>.
	 */
	public Rule(Action action, Header header, Options options) {
		this.action = action;
		this.header = header;
		this.options = options;
	}

	/**
	 *
	 * Returns rule action.
	 *
	 * @return rule action. Can't return <code>null</code>.
	 */
	public Action getAction() {
		return action;
	}

	/**
	 *
	 * Returns rule header.
	 *
	 * @return rule header. Can't return <code>null</code>.
	 */
	public Header getHeader() {
		return header;
	}

	/**
	 *
	 * Returns rule options.
	 *
	 * @return rule options. Can't return <code>null</code>.
	 */
	public Options getOptions() {
		return options;
	}

	@Override
	public String toString() {
		String string = getAction().getName();
		string += SPACE + getHeader().getProtocol();
		string += SPACE + getHeader().getDestination();
		string += SPACE + getHeader().getDestinationPort();
		string += SPACE + getHeader().getDirection();
		string += SPACE + getHeader().getSource();
		string += SPACE + getHeader().getSourcePort();
		string += SPACE + '(';
		for (String option : getOptions().getValue()) {
			string += option + "; ";
		}
		if (!getOptions().getValue().isEmpty()) {
			string = string.substring(0, string.length() - 1);
		}
		string += ')';

		return string;
	}
}
