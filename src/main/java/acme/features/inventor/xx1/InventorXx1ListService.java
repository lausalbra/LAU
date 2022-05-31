package acme.features.inventor.xx1;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.items.Item;
import acme.entities.xx1s.Xx1;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.helpers.CollectionHelper;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorXx1ListService implements AbstractListService<Inventor, Xx1>{
	
	@Autowired
	protected InventorXx1Repository repository;
	 
	@Override
	public boolean authorise(final Request<Xx1> request) {
		assert request != null;

		return true;
	}

	@Override
	public Collection<Xx1> findMany(final Request<Xx1> request) {
		assert request != null;
		
		Collection<Xx1> result;
		int masterId;
		
		masterId = request.getModel().getInteger("masterId");
        result = this.repository.findManyXx1ByItemId(masterId);
		return result;
	}
	
	//UMBIND NECESARIO PARA VALIDAR RN DE SOLO CREAR CHIMPUM SI Y SOLO SI ESE ITEM NO ESTA PUBLICADO Y ADEMAS QUE NO TENGA NINGUN CHIMPUM ASOCIADO
	
		@Override
		public void unbind(final Request<Xx1> request, final Collection<Xx1> entities, final Model model) {
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
				this.repository.findManyXx1ByItemId(masterId).isEmpty());

			model.setAttribute("masterId", masterId);
			model.setAttribute("showCreate", showCreate);
			
		}
	@Override
	public void unbind(final Request<Xx1> request, final Xx1 entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "xx3", "xx6");
		
		
	}
	
}
