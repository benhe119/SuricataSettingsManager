package com.bmstu.suricata.settings.manager.path.service;

import org.osgi.service.component.annotations.Component;

import com.bmstu.suricata.settings.manager.ISuricataPathFinder;

/**
 *
 * Returns path to my Suricata install folder.
 *
 * @author Nikolaev
 *
 */
@Component
public class CourseworkSuricataPathFinder implements ISuricataPathFinder {
	private static final String SURICATA_ROOT = "C:\\Suricata\\";

	@Override
	public String getSuricataPath() {
		return SURICATA_ROOT;
	}
}
