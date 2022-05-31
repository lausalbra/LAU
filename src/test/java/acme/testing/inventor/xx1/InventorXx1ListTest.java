package acme.testing.inventor.xx1;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorXx1ListTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/patron/xx1/list-mine.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex,final String name, final String type, final String code,  
								final String inventor, final String retailPrice, final String published) {
		
		super.signIn("patron1", "patron1");
		super.clickOnMenu("Patron", "List my Items");
		super.checkListingExists();
		
		super.checkColumnHasValue(recordIndex, 0, name);
		super.checkColumnHasValue(recordIndex, 1, type);
		super.checkColumnHasValue(recordIndex, 2, code);
		super.checkColumnHasValue(recordIndex, 3, inventor);
		super.checkColumnHasValue(recordIndex, 4, retailPrice);
		super.checkColumnHasValue(recordIndex, 5, published);
		
		super.signOut();
	}
	
}
