package acme.features.patron.xx1;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Configuration;
import acme.entities.items.Item;
import acme.entities.xx1s.Xx1;
import acme.forms.MoneyExchange;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface PatronXx1Repository extends AbstractRepository {
	

	//Listado
	@Query("select i.xx1 from Item i where i.id = :id")
	Collection<Xx1> findManyXx1ByItemId (int id);
	
	//Solo poder crear un chimpun si y solo si el item no esta publicado y no tiene chimpun asociados
	@Query("select i from Item i where i.id = :id")
	Item findOneItemById(int id);
	
	//Para el show
	@Query("select x from Xx1 x where x.id = :id")
	Xx1 findOneXx1ById(int id);
	
	//Reglas de negocio del create
	@Query("select c from Xx1 c where c.code = :code")
	Xx1 findOneXx1ByCode(String code);
	
	//El update
	@Query("select i from Item i where i.xx1.id = :id")
	Item findOneItemByXx1Id(int id);
	
	// Para la conversion de monedas
	@Query("select c from Configuration c")
	Configuration findConfiguration();

	@Query("select me from MoneyExchange me where me.source.currency = :currency and me.source.amount = :amount")
    MoneyExchange findMoneyExchageByCurrencyAndAmount(String currency, Double amount);
}
