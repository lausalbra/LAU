package acme.features.inventor.huster;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.husters.Huster;
import acme.framework.controllers.AbstractController;
import acme.roles.Inventor;

@Controller
public class InventorHusterController extends AbstractController<Inventor, Huster>{
	
	  @Autowired
	  protected InventorHusterListService    listService;
	  
	  @Autowired
	  protected InventorHusterShowService    showService;
	  
	  @Autowired
	  protected InventorHusterCreateService createService;
	  
	  @Autowired
	  protected InventorHusterUpdateService updateService;
	  
	  @Autowired
	  protected InventorHusterDeleteService deleteService;
	  
	  @PostConstruct
	  protected void initialise() {
	        super.addCommand("list", this.listService);
	        super.addCommand("show", this.showService);
	        super.addCommand("create", this.createService);
	        super.addCommand("update", this.updateService);
	        super.addCommand("delete", this.deleteService);
	    }

}
