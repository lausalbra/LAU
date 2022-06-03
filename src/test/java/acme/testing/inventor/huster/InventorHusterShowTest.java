package acme.testing.inventor.huster;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorHusterShowTest  extends TestHarness{
	

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/huster/show.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int itemRecordIndex, final int recordIndex, final String code, final String creationMoment, final String themes,  
		final String statement, final String starPeriod, final String endPeriod, final String provision, final String additionalInfo) {
		
		super.signIn("inventor1", "inventor1");

		super.clickOnMenu("Inventor", "List my Items");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(itemRecordIndex);
		
		super.checkButtonExists("Huster");
		super.clickOnButton("Huster");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("creationMoment", creationMoment);
		super.checkInputBoxHasValue("themes", themes);
		super.checkInputBoxHasValue("statement", statement);
		super.checkInputBoxHasValue("starPeriod", starPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("provision", provision);
		super.checkInputBoxHasValue("additionalInfo", additionalInfo);
		
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
