package fr.istic.sir.tp7;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Article {
	@Id
	private ObjectId id;
	private String name;
	private int stars;
	
	@Embedded
	private ArrayList<Person> buyers;
	
	public Article() {
		super();
		this.buyers = new ArrayList<Person>();
	}

	public Article(String name, int stars) {
		super();
		this.buyers = new ArrayList<Person>();
		this.name = name;
		this.stars = stars;
	}

	public ArrayList<Person> getBuyers() {
		return buyers;
	}

	public void setBuyers(ArrayList<Person> buyers) {
		this.buyers = buyers;
	}
	
	public void addBuyer(Person buyer) {
		this.buyers.add(buyer);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}
}
