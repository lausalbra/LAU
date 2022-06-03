package acme.features.inventor.huster;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Configuration;
import acme.entities.husters.Huster;
import acme.features.authenticated.moneyExchange.AuthenticatedMoneyExchangePerformService;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorHusterShowService implements AbstractShowService<Inventor, Huster>{
	
	@Autowired
	protected InventorHusterRepository repository;	
	 
	@Override
	public boolean authorise(final Request<Huster> request) {
		assert request != null;

		return true;
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
	protected MoneyExchange change(final Money money) {
		final AuthenticatedMoneyExchangePerformService moneyExchange = new AuthenticatedMoneyExchangePerformService();
		MoneyExchange change = new MoneyExchange();
		final Configuration configuration = this.repository.findConfiguration();
		
		if(!money.getCurrency().equals(configuration.getDefaultCurrency())) { //money usd y lo otro eur
			change = this.repository.findMoneyExchageByCurrencyAndAmount(money.getCurrency(),money.getAmount());//comprobar si esta en la cache
			if(change == null) {//no el precio es 0 necesito esto para que no pete
				change = moneyExchange.computeMoneyExchange(money, configuration.getDefaultCurrency());
				this.repository.save(change); // y la guardo en bbdd
			}
		}else {//Si tengo euro euro no necesito change
			change.setSource(money);
			change.setTarget(money);
			change.setCurrencyTarget(configuration.getDefaultCurrency());
			change.setDate(new Date(System.currentTimeMillis()));		
		}
		return change;
	}
	
	@Override
	public void unbind(final Request<Huster> request, final Huster entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		final MoneyExchange change = this.change(entity.getProvision());
		model.setAttribute("change", change.getTarget());
		
		request.unbind(entity, model, "code", "creationMoment", "themes", "statement", "starPeriod", "endPeriod", "provision", "additionalInfo");
		
		if(this.repository.findOneItemByHusterId(entity.getId()).isPublished()){
			model.setAttribute("canUpdateDelete", false);
		}else {
			model.setAttribute("canUpdateDelete", true);
		}
		
	}
	
}
