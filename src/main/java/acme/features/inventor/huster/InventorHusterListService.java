package acme.features.inventor.huster;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.husters.Huster;
import acme.entities.items.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.helpers.CollectionHelper;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorHusterListService implements AbstractListService<Inventor, Huster>{
	
	@Autowired
	protected InventorHusterRepository repository;
	 
	@Override
	public boolean authorise(final Request<Huster> request) {
		assert request != null;

		return true;
	}

	@Override
	public Collection<Huster> findMany(final Request<Huster> request) {
		assert request != null;
		
		Collection<Huster> result;
		int masterId;
		
		masterId = request.getModel().getInteger("masterId");
        result = this.repository.findManyHusterByItemId(masterId);
		return result;
	}
	
	//UMBIND NECESARIO PARA VALIDAR RN DE SOLO CREAR CHIMPUM SI Y SOLO SI ESE ITEM NO ESTA PUBLICADO Y ADEMAS QUE NO TENGA NINGUN CHIMPUM ASOCIADO
	
		@Override
		public void unbind(final Request<Huster> request, final Collection<Huster> entities, final Model model) {
			assert request != null;
			assert !CollectionHelper.someNull(entities);
			assert model != null;
			
			int masterId;
			Item item;
			final boolean showCreate;

			masterId = request.getModel().getInteger("masterId");
			item = this.repository.findOneItemById(masterId);
			//No esta publicado, es un Inventor y xk no esta vacio
			showCreate = (!item.isPublished() && 
				this.repository.findManyHusterByItemId(masterId).isEmpty());

			model.setAttribute("masterId", masterId);
			model.setAttribute("showCreate", showCreate);
			
		}
	@Override
	public void unbind(final Request<Huster> request, final Huster entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "themes", "statement", "starPeriod", "endPeriod", "provision");
		
		
	}
	
}
