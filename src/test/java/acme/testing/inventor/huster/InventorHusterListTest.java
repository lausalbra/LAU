package acme.testing.inventor.huster;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorHusterListTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/huster/list-mine.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int itemRecordIndex, final int recordIndex, final String code, final String themes,  
		final String statement, final String starPeriod, final String endPeriod, final String provision) {
		
		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List my Items");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(itemRecordIndex);
		
		super.checkButtonExists("Huster");
		super.clickOnButton("Huster");
		super.checkListingExists();
				
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, themes);
		super.checkColumnHasValue(recordIndex, 2, statement);
		super.checkColumnHasValue(recordIndex, 3, starPeriod);
		super.checkColumnHasValue(recordIndex, 4, endPeriod);
		super.checkColumnHasValue(recordIndex, 5, provision);
		
		super.signOut();
	}
	
}
