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
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;

@Service
public class InventorHusterUpdateService implements AbstractUpdateService<Inventor, Huster> {
	
	// Internal State 
	
	@Autowired
	protected InventorHusterRepository repository;
		
	//AbstractUpdateService<Inventor, Huster> interface
	
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
        
        request.bind(entity, errors, "code", "themes", "statement", "starPeriod", "endPeriod", "provision", "additionalInfo");
        
	}
	
	@Override
	public void unbind(final Request<Huster> request, final Huster entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "creationMoment", "themes", "statement", "starPeriod", "endPeriod", "provision", "additionalInfo");
		
		
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
	public void validate(final Request<Huster> request, final Huster entity, final Errors errors) {

		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("code")) {
			Huster existing;

			existing = this.repository.findOneHusterByCode(entity.getCode());
			errors.state(request, existing == null || existing.getId() == entity.getId(), "code", "Inventor.chimpum.form.error.duplicated");

			//RN DE VALIDACION DEL CODIGO DE YYMMDD
			final LocalDate date = LocalDate.now();
			
			final int dayValue = date.getDayOfMonth();
			final String day = String.format("%02d", dayValue);

			final int monthValue = date.getMonthValue();
			final String month = String.format("%02d", monthValue);

			final int yearValue = date.getYear();
			final String year = String.format("%02d", yearValue).substring(2);

			final String correctlyDate = String.format("%s%s%s", year,month,day);
			errors.state(request,entity.getCode().contains(correctlyDate), "code", "Inventor.Huster.form.error.code");

		}

		if (!errors.hasErrors("provision")) {
			final String[] currencies = this.repository.findConfiguration().getAcceptedCurrencies().split(",");
			boolean acceptedCurrencies = false;
			for(int i = 0; i< currencies.length; i++) {
				if(entity.getProvision().getCurrency().equals(currencies[i].trim())) {
					acceptedCurrencies=true;
				}
			}

			errors.state(request, acceptedCurrencies, "provision", "Inventor.Huster.form.error.non-accepted-provision");
			errors.state(request, entity.getProvision().getAmount() > 0, "provision", "Inventor.Huster.form.error.negative-provision");
		}

		if(!errors.hasErrors("starPeriod")) {
			final Date minimumStartDate=DateUtils.addMonths(entity.getCreationMoment(), 1);
			errors.state(request,entity.getStarPeriod().after(minimumStartDate), "starPeriod", "inventor.Huster.form.error.start-period-not-enough");

		}

		if(!errors.hasErrors("endPeriod") && !errors.hasErrors("starPeriod")) {
			final Date minimumFinishDate=DateUtils.addWeeks(entity.getStarPeriod(), 1);
			errors.state(request,entity.getEndPeriod ().after(minimumFinishDate), "endPeriod", "inventor.Huster.form.error.end-period-one-week-before-start-period");

		}

}
		
	@Override
	public void update(final Request<Huster> request, final Huster entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

}
