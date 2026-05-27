package unitTests;

import org.testng.Assert;
import org.testng.annotations.Test;

import utilities.PasswordValidator;

public class PasswordValidatorTest {

	// ========== NORMAL CASES ==========

	@Test
	public void testValidPassword_MinLength() {
		boolean result = PasswordValidator.isValid("abcd");
		Assert.assertTrue(result, "4-character password should be valid (minimum)");
	}

	@Test
	public void testValidPassword_MaxLength() {
		boolean result = PasswordValidator.isValid("a".repeat(20));
		Assert.assertTrue(result, "20-character password should be valid (maximum)");
	}

	@Test
	public void testValidPassword_WithSpecialChars() {
		boolean result = PasswordValidator.isValid("Test@1234");
		Assert.assertTrue(result, "Password with special chars should be valid");
	}

	// ========== NEGATIVE CASES ==========

	@Test
	public void testNullPassword_ReturnsFalse() {
		boolean result = PasswordValidator.isValid(null);
		Assert.assertFalse(result, "Null password should be rejected");
	}

	@Test
	public void testPassword_BelowMinLength_ReturnsFalse() {
		boolean result = PasswordValidator.isValid("abc");
		Assert.assertFalse(result, "Password shorter than 4 chars should be rejected");
	}

	@Test
	public void testPassword_AboveMaxLength_ReturnsFalse() {
		boolean result = PasswordValidator.isValid("a".repeat(21));
		Assert.assertFalse(result, "Password longer than 20 chars should be rejected");
	}

	@Test
	public void testEmptyPassword_ReturnsFalse() {
		boolean result = PasswordValidator.isValid("");
		Assert.assertFalse(result, "Empty password should be rejected");
	}

	// ========== BOUNDARY CASES ==========

	@Test
	public void testPassword_ExactlyMinBoundary() {
		Assert.assertTrue(PasswordValidator.isValid("abcd"), "Exactly 4 chars - boundary");
		Assert.assertFalse(PasswordValidator.isValid("abc"), "3 chars - below boundary");
	}

	@Test
	public void testPassword_ExactlyMaxBoundary() {
		Assert.assertTrue(PasswordValidator.isValid("a".repeat(20)), "Exactly 20 chars - boundary");
		Assert.assertFalse(PasswordValidator.isValid("a".repeat(21)), "21 chars - above boundary");
	}

	// ========== STRENGTH TESTS ==========

	@Test
	public void testWeakPassword_ReturnsWeak() {
		String strength = PasswordValidator.getStrength("abcd");
		Assert.assertEquals(strength, "WEAK", "Only lowercase letters should be WEAK");
	}

	@Test
	public void testMediumPassword_ReturnsMedium() {
		String strength = PasswordValidator.getStrength("Test1234");
		Assert.assertEquals(strength, "MEDIUM", "Uppercase + numbers should be MEDIUM");
	}

	@Test
	public void testStrongPassword_ReturnsStrong() {
		String strength = PasswordValidator.getStrength("Test@1234");
		Assert.assertEquals(strength, "STRONG", "Uppercase + lowercase + numbers + special should be STRONG");
	}

	@Test
	public void testInvalidPassword_StrengthReturnsInvalid() {
		String strength = PasswordValidator.getStrength("abc");
		Assert.assertEquals(strength, "INVALID", "Password below min length should be INVALID");
	}

	// ========== PROPERTY-BASED TESTS ==========

	@Test
	public void testStrengthConsistentProperty() {
		// Property: Calling getStrength() twice on same password returns same result
		String password = "Test@1234";
		boolean consistent = PasswordValidator.isStrengthConsistent(password);
		Assert.assertTrue(consistent, "Strength should be consistent across multiple calls");
	}

	@Test
	public void testLengthProperty_ValidRange() {
		// Property: Valid passwords must have length between 4 and 20 inclusive
		for (int length = 4; length <= 20; length++) {
			String password = "a".repeat(length);
			Assert.assertTrue(PasswordValidator.isValid(password), "Password of length " + length + " should be valid");
		}
	}
}