package acme.features.inventor.xx1;

import java.time.LocalDate;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.items.Item;
import acme.entities.xx1s.Xx1;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;

@Service
public class InventorXx1CreateService implements AbstractCreateService<Inventor, Xx1>{
	
	@Autowired
	protected InventorXx1Repository repository;
	 
	@Override
	public boolean authorise(final Request<Xx1> request) {
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
	public void bind(final Request<Xx1> request, final Xx1 entity, final Errors errors) {
		assert request != null;
        assert entity != null;
        assert errors != null;
        
        request.bind(entity, errors, "code", "xx3", "xx4", "xx51", "xx52", "xx6", "xx7");
        

	}
	
	@Override
	public void validate(final Request<Xx1> request, final Xx1 entity, final Errors errors) {		
			assert request != null;
			assert entity != null;
			assert errors != null;

			if (!errors.hasErrors("code")) {
				Xx1 existing;

				existing = this.repository.findOneXx1ByCode(entity.getCode());
				errors.state(request, existing == null, "code", "Inventor.xx1.form.error.duplicated");

				//RN DE VALIDACION DEL CODIGO DE YYMMDD

				final LocalDate date = LocalDate.now();
				final int day = date.getDayOfMonth();

				final int monthValue = date.getMonthValue();
				final String month = String.format("%02d", monthValue);

				final int yearValue = date.getYear();
				final String year = String.format("%02d", yearValue).substring(2);

				final String correctlyDate = String.format("%s%s%d", year,month,day);
				errors.state(request,entity.getCode().contains(correctlyDate), "code", "Inventor.xx1.form.error.code");

			}

			if (!errors.hasErrors("xx6")) {
				final String[] currencies = this.repository.findConfiguration().getAcceptedCurrencies().split(",");
				boolean acceptedCurrencies = false;
				for(int i = 0; i< currencies.length; i++) {
					if(entity.getXx6().getCurrency().equals(currencies[i].trim())) {
						acceptedCurrencies=true;
					}
				}

				errors.state(request, acceptedCurrencies, "xx6", "Inventor.xx1.form.error.non-accepted-xx6");
				errors.state(request, entity.getXx6().getAmount() > 0, "xx6", "Inventor.xx1.form.error.negative-xx6");
			}

			if(!errors.hasErrors("xx51")) {
				final Date minimumStartDate=DateUtils.addMonths(entity.getXx2(), 1);
				errors.state(request,entity.getXx51().after(minimumStartDate), "xx51", "inventor.xx1.form.error.start-period-not-enough");

			}

			if(!errors.hasErrors("xx52") && !errors.hasErrors("xx51")) {
				final Date minimumFinishDate=DateUtils.addWeeks(entity.getXx51(), 1);
				errors.state(request,entity.getXx52().after(minimumFinishDate), "xx52", "inventor.xx1.form.error.end-period-one-week-before-start-period");

			}

}
        
	

	@Override
	public void unbind(final Request<Xx1> request, final Xx1 entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "xx3", "xx4", "xx51", "xx52", "xx6", "xx7");
		
		
			model.setAttribute("masterId", request.getModel().getInteger("masterId"));
		
	}
	

	
	@Override
	public Xx1 instantiate(final Request<Xx1> request) {
		assert request != null;
		
		Xx1 result;
		result = new Xx1();
		
		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		result.setXx2(moment);	
        
		return result;
	}
	
	@Override
	public void create(final Request<Xx1> request, final Xx1 entity) {
		assert request != null;
		assert entity != null;

		int itemId;
		Item item;
		
		itemId = request.getModel().getInteger("masterId");
		item = this.repository.findOneItemById(itemId);
		item.setXx1(entity);
		
		this.repository.save(item);
		this.repository.save(entity);
	}
	
}
