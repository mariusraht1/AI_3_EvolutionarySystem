package application;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

public class Utilities {
	private static Utilities instance;

	public static Utilities getInstance() {
		if (instance == null) {
			instance = new Utilities();
		}

		return instance;
	}

	private Random random = new Random();

	protected Utilities() {
	}

	public int generateRandom(int min, int max) {
		return random.nextInt((max - min) + 1) + min;
	}

	public enum OSType {
		Windows, MacOS, Unix, Other
	};

	protected static OSType osType;

	public OSType getOperatingSystemType() {
		if (osType == null) {
			String osName = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);

			if (osName.contains("mac") || osName.contains("darwin")) {
				osType = OSType.MacOS;
			} else if (osName.contains("win")) {
				osType = OSType.Windows;
			} else if (osName.contains("nux")) {
				osType = OSType.Unix;
			} else {
				osType = OSType.Other;
			}
		}

		return osType;
	}
	
	public double divide(double dividend, double divisor) {
		if (divisor == 0) {
			return 0;
		} else {
			return dividend / divisor;
		}
	}

	public int parseInt(String s) {
		int result = 0;

		try {
			result = Integer.parseInt(s);
		} catch (Exception e) {
		}

		return result;
	}

	public double formatDouble(String format, double number) {
		DecimalFormat decimalFormat = new DecimalFormat(format);
		DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
		decimalFormatSymbols.setDecimalSeparator('.');
		decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
		number = Double.valueOf(decimalFormat.format(number));
		
		return number;
	}
}
