package utilities;

public class EmailValidator {

	// Validates email based on OpenCart rules
	public static boolean isValid(String email) {
		if (email == null || email.trim().isEmpty()) {
			return false;
		}

		email = email.trim();

		// OpenCart maximum length is 96 characters
		if (email.length() > 96) {
			return false;
		}

		// Must contain @ and .
		if (!email.contains("@") || !email.contains(".")) {
			return false;
		}

		// Should not contain spaces
		if (email.contains(" ")) {
			return false;
		}

		return true;
	}

	// Returns specific validation message (for oracle demonstration)
	public static String getValidationMessage(String email) {
		if (email == null || email.trim().isEmpty()) {
			return "Email is required";
		}

		email = email.trim();

		if (email.length() > 96) {
			return "Email exceeds maximum length of 96 characters";
		}

		if (!email.contains("@")) {
			return "Email must contain @ symbol";
		}

		if (!email.contains(".")) {
			return "Email must contain dot (.)";
		}

		if (email.contains(" ")) {
			return "Email cannot contain spaces";
		}

		return "Valid";
	}
}