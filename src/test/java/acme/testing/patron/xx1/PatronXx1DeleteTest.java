package acme.testing.patron.xx1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.core.annotation.Order;

import acme.testing.TestHarness;

public class PatronXx1DeleteTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/patron/xx1/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int itemRecordIndex, final int recordIndex, final String code, final String xx3,  
		final String xx4, final String xx51, final String xx52, final String xx6, final String xx7) {
		
		super.signIn("patron1", "patron1");
		super.clickOnMenu("Patron", "List All Item");
		
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(itemRecordIndex);
		
		super.checkButtonExists("Xx1");
		super.clickOnButton("Xx1");
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("xx3", xx3);
		super.checkInputBoxHasValue("xx4", xx4);
		super.checkInputBoxHasValue("xx51", xx51);
		super.checkInputBoxHasValue("xx52", xx52);
		super.checkInputBoxHasValue("xx6", xx6);
		super.checkInputBoxHasValue("xx7", xx7);
		super.clickOnSubmit("Delete");
		
		super.signOut();
		
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/patron/xx1/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeTest(final int itemRecordIndex) {
		
		super.signIn("patron1", "patron1");
		super.clickOnMenu("Patron", "List my Items");
		
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(itemRecordIndex);
		
		super.checkNotButtonExists("Delete");
		
		super.signOut();
	}

}
