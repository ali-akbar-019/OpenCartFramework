package utilities;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductQuantityValidatorTest {

	// Business rule method - what OpenCart SHOULD enforce
	private boolean isValidQuantity(int quantity) {
		return quantity > 0 && quantity <= 9999;
	}

	private String getQuantityErrorMessage(int quantity) {
		if (quantity <= 0) {
			return "Quantity must be greater than zero";
		}
		if (quantity > 9999) {
			return "Quantity exceeds maximum limit of 9999";
		}
		return "Valid";
	}

	// ========== NORMAL CASES ==========

	@Test
	public void testValidQuantity_One() {
		Assert.assertTrue(isValidQuantity(1), "Quantity 1 should be valid");
	}

	@Test
	public void testValidQuantity_Ten() {
		Assert.assertTrue(isValidQuantity(10), "Quantity 10 should be valid");
	}

	@Test
	public void testValidQuantity_Hundred() {
		Assert.assertTrue(isValidQuantity(100), "Quantity 100 should be valid");
	}

	@Test
	public void testValidQuantity_Thousand() {
		Assert.assertTrue(isValidQuantity(1000), "Quantity 1000 should be valid");
	}

	// ========== BOUNDARY CASES ==========

	@Test
	public void testQuantity_ZeroIsInvalid() {
		Assert.assertFalse(isValidQuantity(0), "Quantity 0 should be invalid");
		Assert.assertEquals(getQuantityErrorMessage(0), "Quantity must be greater than zero");
	}

	@Test
	public void testQuantity_NegativeOneIsInvalid() {
		Assert.assertFalse(isValidQuantity(-1), "Quantity -1 should be invalid");
		Assert.assertEquals(getQuantityErrorMessage(-1), "Quantity must be greater than zero");
	}

	@Test
	public void testQuantity_NegativeHundredIsInvalid() {
		Assert.assertFalse(isValidQuantity(-100), "Negative quantity should be invalid");
	}

	@Test
	public void testQuantity_MaxBoundary_9999IsValid() {
		Assert.assertTrue(isValidQuantity(9999), "9999 should be valid (maximum allowed)");
	}

	@Test
	public void testQuantity_ExceedsMax_10000IsInvalid() {
		Assert.assertFalse(isValidQuantity(10000), "10000 should exceed maximum limit");
		Assert.assertEquals(getQuantityErrorMessage(10000), "Quantity exceeds maximum limit of 9999");
	}

	@Test
	public void testQuantity_ExceedsMax_20000IsInvalid() {
		Assert.assertFalse(isValidQuantity(20000), "20000 should exceed maximum limit");
	}

	// ========== EDGE CASES ==========

	@Test
	public void testQuantity_MinimumValidValue() {
		Assert.assertTrue(isValidQuantity(1), "1 is the minimum valid quantity");
		Assert.assertFalse(isValidQuantity(0), "0 is invalid - boundary crossed");
	}

	@Test
	public void testQuantity_MaximumValidValue() {
		Assert.assertTrue(isValidQuantity(9999), "9999 is the maximum valid quantity");
		Assert.assertFalse(isValidQuantity(10000), "10000 is invalid - boundary crossed");
	}

	// ========== PROPERTY-BASED TEST (Invariant) ==========

	@Test
	public void testQuantityProperty_ValidRangeOnly() {
		// Property: Valid quantity must always be between 1 and 9999 inclusive
		for (int qty = -100; qty <= 10100; qty += 100) {
			if (qty >= 1 && qty <= 9999) {
				Assert.assertTrue(isValidQuantity(qty), qty + " should be valid (in range 1-9999)");
			} else {
				Assert.assertFalse(isValidQuantity(qty), qty + " should be invalid (outside range 1-9999)");
			}
		}
	}

	@Test
	public void testQuantityProperty_PositiveInvariant() {
		// Property: isValidQuantity() should return true ONLY for positive numbers
		// within limit
		// Test all boundary values around zero
		int[] testValues = { -5, -2, -1, 0, 1, 2, 5, 9998, 9999, 10000, 10001 };

		for (int qty : testValues) {
			if (qty > 0 && qty <= 9999) {
				Assert.assertTrue(isValidQuantity(qty), qty + " should be valid");
			} else {
				Assert.assertFalse(isValidQuantity(qty), qty + " should be invalid");
			}
		}
	}

	@Test
	public void testErrorMessageProperty_ConsistentWithValidation() {
		// Property: Error message should be meaningful when validation fails
		for (int qty : new int[] { -5, -1, 0, 10000, 20000 }) {
			boolean isValid = isValidQuantity(qty);
			String errorMsg = getQuantityErrorMessage(qty);

			Assert.assertFalse(isValid, qty + " should be invalid");
			Assert.assertNotEquals(errorMsg, "Valid", "Error message should not say 'Valid' for invalid quantity");
			Assert.assertTrue(errorMsg.length() > 0, "Error message should not be empty");
		}
	}
}