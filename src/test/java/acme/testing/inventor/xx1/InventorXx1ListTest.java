package acme.testing.inventor.xx1;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorXx1ListTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/xx1/list-mine.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int itemRecordIndex, final int recordIndex, final String code, final String xx3,  
		final String xx4, final String xx51, final String xx52, final String xx6) {
		
		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List my Items");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(itemRecordIndex);
		
		super.checkButtonExists("Xx1");
		super.clickOnButton("Xx1");
		super.checkListingExists();
				
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, xx3);
		super.checkColumnHasValue(recordIndex, 2, xx4);
		super.checkColumnHasValue(recordIndex, 3, xx51);
		super.checkColumnHasValue(recordIndex, 4, xx52);
		super.checkColumnHasValue(recordIndex, 5, xx6);
		
		super.signOut();
	}
	
}
