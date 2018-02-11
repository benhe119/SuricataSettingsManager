package com.bmstu.suricata.settings.manager.model;

/**
 *
 * Instance of this class represents rule header.
 *
 * @author Nikolaev
 *
 */
public class Header {

	private String protocol;
	private String destination;
	private String destinationPort;
	private String direction;
	private String source;
	private String sourcePort;

	/**
	 *
	 * Constuctor.
	 *
	 * @param protocol - rule protocol. Can't be <code>null</code>.
	 * @param destination - rule destination. Can't be <code>null</code>.
	 * @param destinationPort - destination port. Can't be <code>null</code>.
	 * @param direction - direction. (-> , <>) Can't be <code>null</code>.
	 * @param source - rule source. Can't be <code>null</code>.
	 * @param sourcePort - source port. Can't be <code>null</code>.
	 */
	public Header(String protocol, String destination, String destinationPort, String direction, String source, String sourcePort) {
		this.protocol = protocol;
		this.destination = destination;
		this.destinationPort = destinationPort;
		this.direction = direction;
		this.source = source;
		this.sourcePort = sourcePort;
	}

	/**
	 *
	 * Returns rule protocol.
	 *
	 * @return rule protocol. Can't return <code>null</code>.
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 *
	 * Returns rule destination.
	 *
	 * @return rule destination. Can't return <code>null</code>.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 *
	 * Returns destination port.
	 *
	 * @return destination port. Can't return <code>null</code>.
	 */
	public String getDestinationPort() {
		return destinationPort;
	}

	/**
	 *
	 * Returns direction.
	 *
	 * @return direction. Can't return <code>null</code>.
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 *
	 * Returns rule source.
	 *
	 * @return rule source. Can't return <code>null</code>.
	 */
	public String getSource() {
		return source;
	}

	/**
	 *
	 * Returns source port.
	 *
	 * @return source port. Can't return <code>null</code>.
	 */
	public String getSourcePort() {
		return sourcePort;
	}
}
