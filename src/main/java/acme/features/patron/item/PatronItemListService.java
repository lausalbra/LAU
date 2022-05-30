package acme.features.patron.item;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.items.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Patron;

@Service
public class PatronItemListService implements AbstractListService<Patron, Item>{
	
	//Internal State
	
	@Autowired
	protected PatronItemRepository repository;
	
	//AbstractListService<Patron, Item> interface 
	
	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;
		
		return true; 
	}

	@Override
	public Collection<Item> findMany(final Request<Item> request){
		assert request != null;
		
		Collection<Item> result;
		result = this.repository.findAllItem();
		
		return result;
	}
	
	@Override
	public void unbind (final Request<Item> request, final Item entity, final Model model) {
		
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "name", "itemType", "code", "technology", "retailPrice");
		
		final String fullname = entity.getInventor().getUserAccount().getIdentity().getFullName();
		model.setAttribute("fullname", fullname);
		
	}
}