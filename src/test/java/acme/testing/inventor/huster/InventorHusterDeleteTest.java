package acme.testing.inventor.huster;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.core.annotation.Order;

import acme.testing.TestHarness;

public class InventorHusterDeleteTest extends TestHarness{
		
		@ParameterizedTest
		@CsvFileSource(resources = "/inventor/huster/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
		@Order(10)
		public void positiveTest(final int itemRecordIndex, final int recordIndex, final String code, final String themes,  
			final String statement, final String starPeriod, final String endPeriod, final String provision, final String additionalInfo) {
			
			super.signIn("inventor1", "inventor1");
			super.clickOnMenu("Inventor", "List my Items");
			
			super.checkListingExists();
			super.sortListing(0, "asc");
			super.clickOnListingRecord(itemRecordIndex);
			
			super.checkButtonExists("Huster");
			super.clickOnButton("Huster");
			super.clickOnListingRecord(recordIndex);
			
			super.checkInputBoxHasValue("code", code);
			super.checkInputBoxHasValue("themes", themes);
			super.checkInputBoxHasValue("statement", statement);
			super.checkInputBoxHasValue("starPeriod", starPeriod);
			super.checkInputBoxHasValue("endPeriod", endPeriod);
			super.checkInputBoxHasValue("provision", provision);
			super.checkInputBoxHasValue("additionalInfo", additionalInfo);
			super.clickOnSubmit("Delete");
			
			super.signOut();
			
		}
		
		@ParameterizedTest
		@CsvFileSource(resources = "/inventor/huster/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
		@Order(10)
		public void negativeTest(final int itemRecordIndex) {
			
			super.signIn("inventor1", "inventor1");
			super.clickOnMenu("Inventor", "List my Items");
			
			super.checkListingExists();
			super.sortListing(0, "asc");
			super.clickOnListingRecord(itemRecordIndex);
			
			super.checkNotButtonExists("Delete");
			
			super.signOut();
		}

	}