package acme.entities.husters;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Huster extends AbstractEntity{
	
	//Serialisation identifier
	
	protected static final long serialVersionUID = 1L;
	
	//Attributes  
	
	@NotBlank
	@Column(unique = true)
	//@Pattern(regexp = "^\\w{2}\\d{2}-\\d{6}$") //yymmdd --> d{6} lo otro me lo dan
	
	@Pattern(regexp = "^\\d{6}/\\w{2,4}$") //yymmdd/tt --> d{6} lo otro me lo dan
	
	protected String code; //Code
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	protected Date creationMoment; // Creation Moment
	
	@NotBlank
	@Length(min = 1, max=100)
	protected String themes; // Title
	
	@NotBlank
	@Length(min = 1, max=255)
	protected String statement; // Description
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date starPeriod; //Start of the period
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date endPeriod; // End of the period (at least one month ahead and one week long)
	
	@Valid
	@NotNull
	protected Money provision; // Budget positive
	
	@URL
	protected String additionalInfo; // Optional Link
//	
//	@ManyToOne(optional = false)
//    @Valid
//    @NotNull
//    protected Item item; // Sera component o tool
// 
}
