package acme.testing.inventor.huster;

import java.time.LocalDate;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorHusterCreateTest extends TestHarness{
	
	// Lifecycle managment 
	
	// Test cases
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/huster/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest (final int itemRecordIndex, final int recordIndex, final String code, final String themes,  
		final String statement, final String starPeriod, final String endPeriod, final String provision, final String additionalInfo) {
		
		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List my Items");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(itemRecordIndex);
		super.checkButtonExists("Huster");
		super.clickOnButton("Huster");
		super.checkListingEmpty();
		
		super.clickOnButton("Create huster");
		final LocalDate date = LocalDate.now();
		
		final int dayValue = date.getDayOfMonth();
		final String day = String.format("%02d", dayValue);

		final int monthValue = date.getMonthValue();
		final String month = String.format("%02d", monthValue);

		final int yearValue = date.getYear();
		final String year = String.format("%02d", yearValue).substring(2);

		final String correctlyDate = String.format("%s%s%s", year,month,day);
		
		super.fillInputBoxIn("code", code + correctlyDate);
		super.fillInputBoxIn("themes", themes);
		super.fillInputBoxIn("statement", statement);
		super.fillInputBoxIn("starPeriod", starPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("provision", provision);
		super.fillInputBoxIn("additionalInfo", additionalInfo);
		super.clickOnSubmit("Create huster");
		
				
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code + correctlyDate);
		super.checkInputBoxHasValue("themes", themes);
		super.checkInputBoxHasValue("statement", statement);
		super.checkInputBoxHasValue("starPeriod", starPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("provision", provision);
		super.checkInputBoxHasValue("additionalInfo", additionalInfo);
		
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/huster/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final int itemRecordIndex, final int recordIndex, final String code, final String themes,  
		final String statement, final String starPeriod, final String endPeriod, final String provision, final String additionalInfo) {
		
		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List my Items");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(itemRecordIndex);
		super.checkButtonExists("Huster");
		super.clickOnButton("Huster");
		super.checkListingEmpty();
		
		super.clickOnButton("Create huster");
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("themes", themes);
		super.fillInputBoxIn("statement", statement);
		super.fillInputBoxIn("starPeriod", starPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("provision", provision);
		super.fillInputBoxIn("additionalInfo", additionalInfo);
		super.clickOnSubmit("Create huster");
	

		super.checkNotPanicExists();
		super.checkErrorsExist();

		super.signOut();
	}
	
	@Test
	@Order(30)
	public void hackingTest() {
		super.checkNotLinkExists("Account");
		super.navigate("/inventor/huster/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.navigate("/inventor/huster/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("patron1", "patron1");
		super.navigate("/patron/huster/create");
		super.checkPanicExists();
		super.signOut();
	}
}
