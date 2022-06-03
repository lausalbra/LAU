package acme.features.inventor.huster;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Configuration;
import acme.entities.husters.Huster;
import acme.entities.items.Item;
import acme.forms.MoneyExchange;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorHusterRepository extends AbstractRepository {
	

	//Listado
	@Query("select i.huster from Item i where i.id = :id")
	Collection<Huster> findManyHusterByItemId (int id);
	
	//Solo poder crear un chimpun si y solo si el item no esta publicado y no tiene chimpun asociados
	@Query("select i from Item i where i.id = :id")
	Item findOneItemById(int id);
	
	//Para el show
	@Query("select x from Huster x where x.id = :id")
	Huster findOneHusterById(int id);
	
	//Reglas de negocio del create
	@Query("select c from Huster c where c.code = :code")
	Huster findOneHusterByCode(String code);
	
	//El update
	@Query("select i from Item i where i.huster.id = :id")
	Item findOneItemByHusterId(int id);
	
	// Para la conversion de monedas
	@Query("select c from Configuration c")
	Configuration findConfiguration();

	@Query("select me from MoneyExchange me where me.source.currency = :currency and me.source.amount = :amount")
    MoneyExchange findMoneyExchageByCurrencyAndAmount(String currency, Double amount);
}
