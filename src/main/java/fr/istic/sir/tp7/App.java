package fr.istic.sir.tp7;

import java.net.UnknownHostException;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import com.mongodb.MongoClient;

public class App
{
	public static void main( String[] args ) throws UnknownHostException
	{
		Morphia morphia = new Morphia();
		MongoClient mongo = new MongoClient();
		morphia.map(Person.class).map(Address.class).map(Article.class);

		Datastore ds = morphia.createDatastore(mongo, "test");

		// drop collection to avoid duplicates
		ds.getCollection(Article.class).drop();
		ds.getCollection(Person.class).drop();
		ds.getCollection(Address.class).drop();

		Person p1 = new Person("Tintin");
		Person p2 = new Person("Haddock");
		Person p3 = new Person("Tournesol");
		Person p4 = new Person("Dupont");
		Person p5 = new Person("Dupond");

		Address a1 = new Address("11th avenue", "New-York", "123456", "US");
		Address a2 = new Address("123 rue Jean Jaurès", "Rennes", "35000", "France");
		Address a3 = new Address("221B Baker street", "Londres", "020", "UK");
		Address a4 = new Address("112 Ocean Avenue", "Amityville", "666", "US");
		Address a5 = new Address("Parra Grande Ln., Montecito", "Santa Barbara", "0420", "US");
		Address a6 = new Address("9303 Roslyndale Avenue", "Los Angeles", "1", "US");
		Address a7 = new Address("Chateau de Moulinsard", "Cheverny", "41700", "France");

		Article ar1 = new Article("Fusée spatiale", 97);
		Article ar2 = new Article("Sous-marin", 93);
		Article ar3 = new Article("Statuette", 91);
		Article ar4 = new Article("Sceptre", 89);
		Article ar5 = new Article("Cigares", 88);
		Article ar6 = new Article("Jarre chinoise", 84);
		Article ar7 = new Article("Champignon géant", 81);

		p1.addAddress(a1);
		p1.addAddress(a2);
		p1.addAddress(a3);
		p2.addAddress(a2);
		p2.addAddress(a4);
		p3.addAddress(a5);
		p4.addAddress(a6);
		p5.addAddress(a7);

		ar1.addBuyer(p1);
		ar1.addBuyer(p2);
		ar1.addBuyer(p3);
		ar2.addBuyer(p4);
		ar3.addBuyer(p5);
		ar4.addBuyer(p2);
		ar5.addBuyer(p2);
		ar7.addBuyer(p3);

		// Save the POJO
		ds.save(ar1);
		ds.save(ar2);
		ds.save(ar3);
		ds.save(ar4);
		ds.save(ar5);
		ds.save(ar6);
		ds.save(ar7);

		for (Article a : ds.find(Article.class)){
			System.out.println(a.getName());
			for (Person p : a.getBuyers()){
				System.out.println("\t" + p.getName());
				for (Address ad : p.getAddresses()){
					System.out.println("\t\t" + ad.getStreet());
				}
			}
		}
	}

}
