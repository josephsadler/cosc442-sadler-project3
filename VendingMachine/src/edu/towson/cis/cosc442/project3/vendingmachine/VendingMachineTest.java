package edu.towson.cis.cosc442.project3.vendingmachine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VendingMachineTest {

	VendingMachine vendor;
	VendingMachineItem item;

	// Setup the testing environment
	@Before
	public void setUp() throws Exception {
		vendor = new VendingMachine();
		item = new VendingMachineItem("TestItem", 1);
	}

	// Test addItem() and make sure it is in the Vending Machine
	@Test
	public void testAddItem() {
		vendor.addItem(item, "A");
		assertEquals(item, vendor.getItem("A"));
	}

	@Test(expected = VendingMachineException.class)
	public void testAddItemAlreadyOccupied() {
		vendor.addItem(item, "B");
		vendor.addItem(item, "B");
	}

	// Make sure that items can be removed
	@Test
	public void testRemoveItem() {
		vendor.addItem(item, "C");
		if (item == vendor.getItem("C")) {
			assertEquals(item, vendor.removeItem("C"));
		} else {
			fail("Item was not added, could not be removed");
		}
	}

	// Test null code value
	@Test(expected = VendingMachineException.class)
	public void testRemoveItemNullCode() {
		vendor.removeItem(null);
	}

	// Test removing an item from an empty slot
	@Test(expected = VendingMachineException.class)
	public void testRemoveItemNullItem() {
		vendor.removeItem("A");
	}

	// Test removing an item from a slot that does not exist
	@Test(expected = VendingMachineException.class)
	public void testRemoveItemInvalidCode() {
		vendor.removeItem("P");
	}

	// Make sure getBalance() is returning the accurate value
	@Test
	public void testGetBalance() {
		assertEquals(vendor.balance, vendor.getBalance(), .001);
	}

	// Test that items can be purchased with makePurchase()
	@Test
	public void testMakePurchase() {
		vendor.addItem(item, "D");
		if (item == vendor.getItem("D")) {
			vendor.insertMoney(1);
			assertTrue(vendor.makePurchase("D"));
		} else {
			fail("Item was not added, cannot be purchased");
		}
	}

	@Test(expected = VendingMachineException.class)
	public void testMakePurchaseNullCode() {
		vendor.makePurchase(null);
	}
	
	@Test
	public void testMakePurchaseNoMoney() {
		vendor.addItem(item, "D");
		assertFalse(vendor.makePurchase("D"));
	}

	@Test
	public void testMakePurchaseNullItem() {
		assertFalse(vendor.makePurchase("A"));
	}

	// Test insertMoney()
	@Test
	public void testInsertMoney() {
		vendor.insertMoney(1);
		assertEquals(1, vendor.balance, .001);
	}

	@Test(expected = VendingMachineException.class)
	public void testInsertNegativeMoney() {
		vendor.insertMoney(-1);
	}
	
	@Test (expected = VendingMachineException.class)
	public void testInsertNegativeMoney2() {
		vendor.insertMoney(1);
		vendor.insertMoney(-1);
		assertEquals(1, vendor.balance, .001);
	}

	// Test that returnChange() returns the proper change value
	@Test
	public void testReturnChange() {
		vendor.insertMoney(.5);
		assertEquals(.5, vendor.returnChange(), .001);
	}
}
