package acme.features.inventor.huster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.husters.Huster;
import acme.entities.items.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;

@Service
public class InventorHusterDeleteService implements AbstractDeleteService<Inventor, Huster>{
	
	@Autowired
	protected InventorHusterRepository repository;
	 
	@Override
	public boolean authorise(final Request<Huster> request) {
		assert request != null;
		boolean result;
		int id;
		Item item;
		id = request.getModel().getInteger("id");
		item = this.repository.findOneItemByHusterId(id);
		result = (!item.isPublished());
		
		return result;
	}

	@Override
	public void bind(final Request<Huster> request, final Huster entity, final Errors errors) {
		assert request != null;
        assert entity != null;
        assert errors != null;
        
        request.bind(entity, errors, "creationMoment", "themes", "statement", "starPeriod", "endPeriod", "endPeriod", "provision", "additionalInfo");
		
	}
	
	@Override
	public void validate(final Request<Huster> request, final Huster entity, final Errors errors) {
		assert request != null;
        assert entity != null;
        assert errors != null;
        
	}

	@Override
	public Huster findOne(final Request<Huster> request) {
		assert request != null;
		
		Huster result;
		int id;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findOneHusterById(id);
		
		return result;
	}

	@Override
	public void delete(final Request<Huster> request, final Huster entity) {
		assert request != null;
		assert entity != null;
		
		Item item;
		item = this.repository.findOneItemByHusterId(entity.getId());
		//this.repository.delete(item); Borraba al padre tambien
		item.setHuster(null); //Para no borrar al padre
		this.repository.delete(entity);
	}

	@Override
	public void unbind(final Request<Huster> request, final Huster entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "themes", "statement", "starPeriod", "endPeriod", "provision", "additionalInfo");
		
		
			model.setAttribute("masterId", request.getModel().getAttribute("masterID"));
		
		
	}
	
}
