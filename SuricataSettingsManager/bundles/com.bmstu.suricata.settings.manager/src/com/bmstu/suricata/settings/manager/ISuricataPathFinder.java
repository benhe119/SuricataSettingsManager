package com.bmstu.suricata.settings.manager;

/**
 *
 * Path finder for suricata
 *
 * @author Nikolaev
 *
 */
public interface ISuricataPathFinder {

	/**
	 *
	 * Returns path to Suricata install folder.
	 *
	 * @return path to Suricata install folder. Can't return <code>null</code>.
	 */
	String getSuricataPath();

}
