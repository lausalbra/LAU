package acme.features.patron.item;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.items.Item;
import acme.framework.controllers.AbstractController;
import acme.roles.Patron;

@Controller
public class PatronItemController extends AbstractController<Patron, Item>{

	//Internal state 
	
	@Autowired
	protected PatronItemListService		listService;
	
	@Autowired
	protected PatronItemShowService		showService;
		
	//Constructors
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("show", this.showService);
		super.addCommand("list", this.listService);
	}
	
	
}
