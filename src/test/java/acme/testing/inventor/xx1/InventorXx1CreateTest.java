package acme.testing.inventor.xx1;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorXx1CreateTest extends TestHarness{
	
	// Lifecycle managment 
	
	// Test cases
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/xx1/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest (final int itemRecordIndex, final int recordIndex, final String code, final String xx3,  
		final String xx4, final String xx51, final String xx52, final String xx6, final String xx7) {
		
		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List my Items");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(itemRecordIndex);
		super.checkButtonExists("Create o view xx1");
		super.clickOnButton("Create o view xx1");
		super.checkListingEmpty();
		
		super.clickOnButton("Create");
				
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("xx3", xx3);
		super.fillInputBoxIn("xx4", xx4);
		super.fillInputBoxIn("xx51", xx51);
		super.fillInputBoxIn("xx52", xx52);
		super.fillInputBoxIn("xx6", xx6);
		super.fillInputBoxIn("xx7", xx7);
		super.clickOnSubmit("Create");
		
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, xx3);
		
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("xx3", xx3);
		super.checkInputBoxHasValue("xx4", xx4);
		super.checkInputBoxHasValue("xx51", xx51);
		super.checkInputBoxHasValue("xx52", xx52);
		super.checkInputBoxHasValue("xx6", xx6);
		super.checkInputBoxHasValue("xx7", xx7);
		
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/xx1/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final int itemRecordIndex, final int recordIndex, final String code, final String xx3,  
		final String xx4, final String xx51, final String xx52, final String xx6, final String xx7) {
		
		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List my Items");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(itemRecordIndex);
		super.checkButtonExists("Create o view xx1");
		super.clickOnButton("Create o view xx1");
		super.checkListingEmpty();
		
		super.clickOnButton("Create");
				
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("xx3", xx3);
		super.fillInputBoxIn("xx4", xx4);
		super.fillInputBoxIn("xx51", xx51);
		super.fillInputBoxIn("xx52", xx52);
		super.fillInputBoxIn("xx6", xx6);
		super.fillInputBoxIn("xx7", xx7);
		super.clickOnSubmit("Create");
	

		super.checkNotPanicExists();
		super.checkErrorsExist();

		super.signOut();
	}
	
	@Test
	@Order(30)
	public void hackingTest() {
		super.checkNotLinkExists("Account");
		super.navigate("/inventor/xx1/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.navigate("/inventor/xx1/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("patron1", "patron1");
		super.navigate("/patron/xx1/create");
		super.checkPanicExists();
		super.signOut();
	}
}
