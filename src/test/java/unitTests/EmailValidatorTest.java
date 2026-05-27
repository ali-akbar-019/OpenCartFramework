package unitTests;

import org.testng.Assert;
import org.testng.annotations.Test;

import utilities.EmailValidator;

public class EmailValidatorTest {

	// ========== NORMAL CASES ==========

	@Test
	public void testValidEmail_StandardFormat() {
		boolean result = EmailValidator.isValid("user@example.com");
		Assert.assertTrue(result, "Standard email format should be valid");
	}

	@Test
	public void testValidEmail_WithNumbers() {
		boolean result = EmailValidator.isValid("user123@domain.co.uk");
		Assert.assertTrue(result, "Email with numbers and subdomain should be valid");
	}

	@Test
	public void testValidEmail_WithDotsInLocalPart() {
		boolean result = EmailValidator.isValid("first.last@example.com");
		Assert.assertTrue(result, "Email with dots in local part should be valid");
	}

	// ========== NEGATIVE CASES ==========

	@Test
	public void testNullEmail_ReturnsFalse() {
		boolean result = EmailValidator.isValid(null);
		Assert.assertFalse(result, "Null email should be rejected");
	}

	@Test
	public void testEmptyEmail_ReturnsFalse() {
		boolean result = EmailValidator.isValid("");
		Assert.assertFalse(result, "Empty email should be rejected");
	}

	@Test
	public void testBlankEmail_ReturnsFalse() {
		boolean result = EmailValidator.isValid("   ");
		Assert.assertFalse(result, "Blank email (spaces only) should be rejected");
	}

	@Test
	public void testEmailNoAtSymbol_ReturnsFalse() {
		boolean result = EmailValidator.isValid("userexample.com");
		Assert.assertFalse(result, "Email without @ should be rejected");
	}

	@Test
	public void testEmailNoDot_ReturnsFalse() {
		boolean result = EmailValidator.isValid("user@examplecom");
		Assert.assertFalse(result, "Email without dot should be rejected");
	}

	@Test
	public void testEmailWithSpaces_ReturnsFalse() {
		boolean result = EmailValidator.isValid("user@example .com");
		Assert.assertFalse(result, "Email with spaces should be rejected");
	}

	// ========== BOUNDARY CASES ==========

	@Test
	public void testEmail_MaxLength_Valid() {
		// 96 characters (OpenCart's limit)
		String local = "a".repeat(50);
		String domain = "b".repeat(40);
		String email = local + "@" + domain + ".com";
		// Ensure length is exactly 96 or less
		if (email.length() <= 96) {
			boolean result = EmailValidator.isValid(email);
			// Valid if format is correct, regardless of length within limit
			Assert.assertTrue(result || !email.contains("@"));
		}
	}

	@Test
	public void testEmail_ExceedsMaxLength_ReturnsFalse() {
		String longEmail = "a".repeat(100) + "@example.com";
		boolean result = EmailValidator.isValid(longEmail);
		Assert.assertFalse(result, "Email exceeding 96 chars should be rejected");
	}

	// ========== VALIDATION MESSAGE TESTS (ORACLE) ==========

	@Test
	public void testValidationMessage_ForNullEmail() {
		String message = EmailValidator.getValidationMessage(null);
		Assert.assertEquals(message, "Email is required");
	}

	@Test
	public void testValidationMessage_ForMissingAtSymbol() {
		String message = EmailValidator.getValidationMessage("testexample.com");
		Assert.assertEquals(message, "Email must contain @ symbol");
	}

	@Test
	public void testValidationMessage_ForValidEmail() {
		String message = EmailValidator.getValidationMessage("test@example.com");
		Assert.assertEquals(message, "Valid");
	}

	// ========== PROPERTY-BASED TEST (Invariant) ==========

	@Test
	public void testValidationMessage_ConsistentWithIsValid() {
		// Property: If email is valid, message should be "Valid"
		// If email is invalid, message should NOT be "Valid"

		String validEmail = "test@example.com";
		Assert.assertTrue(EmailValidator.isValid(validEmail));
		Assert.assertEquals(EmailValidator.getValidationMessage(validEmail), "Valid");

		String invalidEmail = "invalid";
		Assert.assertFalse(EmailValidator.isValid(invalidEmail));
		Assert.assertNotEquals(EmailValidator.getValidationMessage(invalidEmail), "Valid");
	}
}