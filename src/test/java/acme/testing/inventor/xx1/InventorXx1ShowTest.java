package acme.testing.inventor.xx1;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorXx1ShowTest  extends TestHarness{
	

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/xx1/show.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int itemRecordIndex, final int recordIndex, final String code, final String xx2, final String xx3,  
		final String xx4, final String xx51, final String xx52, final String xx6, final String xx7) {
		
		super.signIn("inventor1", "inventor1");

		super.clickOnMenu("Inventor", "List my Items");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(itemRecordIndex);
		
		super.checkButtonExists("Xx1");
		super.clickOnButton("Xx1");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("xx2", xx2);
		super.checkInputBoxHasValue("xx3", xx3);
		super.checkInputBoxHasValue("xx4", xx4);
		super.checkInputBoxHasValue("xx51", xx51);
		super.checkInputBoxHasValue("xx52", xx52);
		super.checkInputBoxHasValue("xx6", xx6);
		super.checkInputBoxHasValue("xx7", xx7);
		
		super.signOut();
		
	}
	
	@Test
	@Order(20)
	public void negativeTest() {
		// HINT: this is a listing, which implies that no data must be entered in any forms.
		// HINT+ Then, there are not any negative test cases for this feature.
	}

	@Test
	@Order(30)
	public void hackingTest() {
		// HINT+ a) estando logueado como inventorX no poder ver los detalles de un chimpum que no sea suyo;
		// HINT+ b) estando logueado como patron no poder ver los detalles de un chimpum
		// HINT+ c) estando como anonimo no poder ver los detalles de un chimpum ;

	}

}
