package fr.istic.sir.tp7;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Person {
	@Id
	private ObjectId id;
	private String name;
	
	@Embedded
	private ArrayList<Address> addresses;
	
	public Person() {
		super();
		this.addresses = new ArrayList<Address>();
	}

	public Person(String name) {
		super();
		this.addresses = new ArrayList<Address>();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(ArrayList<Address> addresses) {
		this.addresses = addresses;
	}
	
	public void addAddress(Address address) {
		this.addresses.add(address);
	}
}
