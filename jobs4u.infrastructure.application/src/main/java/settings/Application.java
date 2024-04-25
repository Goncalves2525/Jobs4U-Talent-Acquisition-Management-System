package settings;

import eapli.framework.util.Utility;

/**
 * A "global" static class with the application registry of well known objects.
 * Adapted from:
 * @author Paulo Gandra Sousa
 */
@Utility
public class Application {

	public static final String VERSION = "v1";
	public static final String COPYRIGHT = "(C) 2023-2024, ISEP's 2NB1";

	private static final AppSettings SETTINGS = new AppSettings();

	public static AppSettings settings() {
		return SETTINGS;
	}

	private Application() {
		// private visibility to ensure singleton & utility
	}
}
