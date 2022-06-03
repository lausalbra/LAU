package acme.features.inventor.huster;

import java.time.LocalDate;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.husters.Huster;
import acme.entities.items.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;

@Service
public class InventorHusterCreateService implements AbstractCreateService<Inventor, Huster>{
	
	@Autowired
	protected InventorHusterRepository repository;
	 
	@Override
	public boolean authorise(final Request<Huster> request) {
		assert request != null;
		boolean result;
		int masterId;
		Item item;
		masterId = request.getModel().getInteger("masterId");
		item = this.repository.findOneItemById(masterId);
		result = (item != null && !item.isPublished());
		
		return result;
	}

	@Override
	public void bind(final Request<Huster> request, final Huster entity, final Errors errors) {
		assert request != null;
        assert entity != null;
        assert errors != null;
        
        request.bind(entity, errors, "code", "themes", "statement", "starPeriod", "endPeriod", "provision", "additionalInfo");
        

	}
	
	@Override
	public void validate(final Request<Huster> request, final Huster entity, final Errors errors) {		
			assert request != null;
			assert entity != null;
			assert errors != null;

			if (!errors.hasErrors("code")) {
				Huster existing;

				existing = this.repository.findOneHusterByCode(entity.getCode());
				errors.state(request, existing == null, "code", "inventor.huster.form.error.duplicated");

				//RN DE VALIDACION DEL CODIGO DE YYMMDD
				final LocalDate date = LocalDate.now();
				
				final int dayValue = date.getDayOfMonth();
				final String day = String.format("%02d", dayValue);

				final int monthValue = date.getMonthValue();
				final String month = String.format("%02d", monthValue);

				final int yearValue = date.getYear();
				final String year = String.format("%02d", yearValue).substring(2);

				final String correctlyDate = String.format("%s"+"/"+"%s%s", year,month,day);
				errors.state(request,entity.getCode().contains(correctlyDate), "code", "inventor.huster.form.error.code");

			}

			if (!errors.hasErrors("provision")) {
				final String[] currencies = this.repository.findConfiguration().getAcceptedCurrencies().split(",");
				boolean acceptedCurrencies = false;
				for(int i = 0; i< currencies.length; i++) {
					if(entity.getProvision().getCurrency().equals(currencies[i].trim())) {
						acceptedCurrencies=true;
					}
				}

				errors.state(request, acceptedCurrencies, "provision", "inventor.huster.form.error.non-accepted-provision");
				errors.state(request, entity.getProvision().getAmount() > 0, "provision", "inventor.huster.form.error.negative-provision");
			}

			if(!errors.hasErrors("starPeriod")) {
				final Date minimumStartDate=DateUtils.addMonths(entity.getCreationMoment(), 1);
				errors.state(request,entity.getStarPeriod().after(minimumStartDate), "starPeriod", "inventor.huster.form.error.start-period-not-enough");

			}

			if(!errors.hasErrors("endPeriod") && !errors.hasErrors("starPeriod")) {
				final Date minimumFinishDate=DateUtils.addWeeks(entity.getStarPeriod(), 1);
				errors.state(request,entity.getEndPeriod().after(minimumFinishDate), "endPeriod", "inventor.huster.form.error.end-period-one-week-before-start-period");

			}

}
        
	

	@Override
	public void unbind(final Request<Huster> request, final Huster entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "themes", "statement", "starPeriod", "endPeriod", "provision", "additionalInfo");
		
		
			model.setAttribute("masterId", request.getModel().getInteger("masterId"));
		
	}
	

	
	@Override
	public Huster instantiate(final Request<Huster> request) {
		assert request != null;
		
		Huster result;
		result = new Huster();
		
		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		result.setCreationMoment(moment);	
        
		return result;
	}
	
	@Override
	public void create(final Request<Huster> request, final Huster entity) {
		assert request != null;
		assert entity != null;

		int itemId;
		Item item;
		
		itemId = request.getModel().getInteger("masterId");
		item = this.repository.findOneItemById(itemId);
		item.setHuster(entity);
		
		this.repository.save(item);
		this.repository.save(entity);
	}
	
}
