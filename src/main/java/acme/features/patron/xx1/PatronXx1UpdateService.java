package acme.features.patron.xx1;

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
import acme.framework.services.AbstractUpdateService;
import acme.roles.Patron;

@Service
public class PatronXx1UpdateService implements AbstractUpdateService<Patron, Xx1> {
	
	// Internal State 
	
	@Autowired
	protected PatronXx1Repository repository;
		
	//AbstractUpdateService<Patron, Xx1> interface
	
	@Override
	public boolean authorise(final Request<Xx1> request) {
		assert request != null;
		boolean result;
		int id;
		Item item;
		id = request.getModel().getInteger("id");
		item = this.repository.findOneItemByXx1Id(id);
		result = (!item.isPublished());
		
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
	public void unbind(final Request<Xx1> request, final Xx1 entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "xx2", "xx3", "xx4", "xx51", "xx52", "xx6", "xx7");
		
		
	}
	
	@Override
	public Xx1 findOne(final Request<Xx1> request) {
		assert request != null;

		Xx1 result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneXx1ById(id);

		return result;
	}
	
	@Override
	public void validate(final Request<Xx1> request, final Xx1 entity, final Errors errors) {

		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("code")) {
			Xx1 existing;

			existing = this.repository.findOneXx1ByCode(entity.getCode());
			errors.state(request, existing == null || existing.getId() == entity.getId(), "code", "Patron.chimpum.form.error.duplicated");

			//RN DE VALIDACION DEL CODIGO DE YYMMDD

			final LocalDate date = LocalDate.now();
			final int day = date.getDayOfMonth();

			final int monthValue = date.getMonthValue();
			final String month = String.format("%02d", monthValue);

			final int yearValue = date.getYear();
			final String year = String.format("%02d", yearValue).substring(2);

			final String correctlyDate = String.format("%s%s%d", year,month,day);
			errors.state(request,entity.getCode().contains(correctlyDate), "code", "Patron.chimpum.form.error.code");

		}

		if (!errors.hasErrors("xx6")) {
			final String[] currencies = this.repository.findConfiguration().getAcceptedCurrencies().split(",");
			boolean acceptedCurrencies = false;
			for(int i = 0; i< currencies.length; i++) {
				if(entity.getXx6().getCurrency().equals(currencies[i].trim())) {
					acceptedCurrencies=true;
				}
			}

			errors.state(request, acceptedCurrencies, "xx6", "Patron.xx1.form.error.non-accepted-xx6");
			errors.state(request, entity.getXx6().getAmount() > 0, "xx6", "Patron.xx1.form.error.negative-xx6");
		}

		if(!errors.hasErrors("xx51")) {
			final Date minimumStartDate=DateUtils.addMonths(entity.getXx2(), 1);
			errors.state(request,entity.getXx51().after(minimumStartDate), "xx51", "Patron.xx1.form.error.xx51");

		}

		if(!errors.hasErrors("xx52")) {
			final Date minimumFinishDate=DateUtils.addWeeks(entity.getXx51(), 1);
			errors.state(request,entity.getXx52().after(minimumFinishDate), "xx52", "Patron.xx1.form.error.xx52");

		}

}
		
	@Override
	public void update(final Request<Xx1> request, final Xx1 entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

}