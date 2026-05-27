package unitTests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductQuantityValidatorTest {

	// ========== NORMAL CASES ==========

	@Test
	public void testValidQuantity_Standard() {
		Assert.assertTrue(isValidQuantity(1), "Quantity 1 should be valid");
		Assert.assertTrue(isValidQuantity(5), "Quantity 5 should be valid");
		Assert.assertTrue(isValidQuantity(10), "Quantity 10 should be valid");
	}

	// ========== BOUNDARY CASES ==========

	@Test
	public void testQuantity_ZeroIsInvalid() {
		Assert.assertFalse(isValidQuantity(0), "Quantity 0 should be invalid");
	}

	@Test
	public void testQuantity_NegativeIsInvalid() {
		Assert.assertFalse(isValidQuantity(-1), "Negative quantity should be invalid");
		Assert.assertFalse(isValidQuantity(-5), "Negative quantity should be invalid");
	}

	@Test
	public void testQuantity_MaxBoundary() {
		// OpenCart typically max 9999
		Assert.assertTrue(isValidQuantity(9999), "9999 should be valid");
		Assert.assertFalse(isValidQuantity(10000), "10000 should exceed max");
	}

	// ========== PROPERTY-BASED TEST ==========

	@Test
	public void testQuantityProperty_NonNegative() {
		// Property: Valid quantity must always be > 0
		for (int qty = -10; qty <= 10; qty++) {
			if (qty > 0 && qty <= 9999) {
				Assert.assertTrue(isValidQuantity(qty), qty + " should be valid");
			} else {
				Assert.assertFalse(isValidQuantity(qty), qty + " should be invalid");
			}
		}
	}

	// Business rule method (what OpenCart SHOULD do)
	private boolean isValidQuantity(int quantity) {
		return quantity > 0 && quantity <= 9999;
	}
}