package utilities;

public class PasswordValidator {

	// OpenCart password rules: 4-20 characters
	public static boolean isValid(String password) {
		if (password == null) {
			return false;
		}

		if (password.length() < 4) {
			return false;
		}

		if (password.length() > 20) {
			return false;
		}

		return true;
	}

	// Returns password strength (bonus for property-based testing)
	public static String getStrength(String password) {
		if (!isValid(password)) {
			return "INVALID";
		}

		int score = 0;

		if (password.matches(".*[A-Z].*"))
			score++; // Has uppercase
		if (password.matches(".*[a-z].*"))
			score++; // Has lowercase
		if (password.matches(".*\\d.*"))
			score++; // Has digit
		if (password.matches(".*[@#$%^&+=].*"))
			score++; // Has special char
		if (password.length() >= 8)
			score++; // Length bonus

		if (score >= 4)
			return "STRONG";
		if (score >= 3)
			return "MEDIUM";
		return "WEAK";
	}

	// Property: password strength should be consistent across multiple checks
	public static boolean isStrengthConsistent(String password) {
		String strength1 = getStrength(password);
		String strength2 = getStrength(password);
		return strength1.equals(strength2);
	}
}