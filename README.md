## Mongodb/Morphia



Morphia propose l'annotation `@Embedded` qui permet d'encapsuler un objet dans un autre. Au lieu d'avoir une liste d'objets *à plat*, on a donc un *arbre* d'objets (cf ci-dessous).

```java
@Entity
public class Person {
	@Id
	private ObjectId id;
	private String name;

	@Embedded
	private ArrayList<Address> addresses;

    ...
}
```
Après avoir ajouté différents éléments dans la base de données suivant le modèle créé en Java, on interroge dans mongo shell la base de données utilisée et on récupère le contenu de la collection Article. L'Article est le plus haut niveau de l'arbre selon le modèle (voir `@Embedded`), c'est pourquoi on recupère une collection d'articles.

```bash
> db.Article.find().pretty()
```

Voilà ce que nous retourne la requête, et on voit tout de suite une des limites du modèle ainsi définit.

```json
{
	"_id" : ObjectId("5714e01744aea5065eab0b86"),
	"className" : "fr.istic.sir.tp7.Article",
	"name" : "Fusée spatiale",
	"stars" : 97,
	"buyers" : [
		{
			"name" : "Tintin",
			"addresses" : [
				{
					"street" : "11th avenue",
					"city" : "New-York",
					"postCode" : "123456",
					"country" : "US"
				},
				{
					"street" : "123 rue Jean Jaurès",
					"city" : "Rennes",
					"postCode" : "35000",
					"country" : "France"
				},
				{
					"street" : "221B Baker street",
					"city" : "Londres",
					"postCode" : "020",
					"country" : "UK"
				}
			]
		},
		{
			"name" : "Haddock",
			"addresses" : [
				{
					"street" : "123 rue Jean Jaurès",
					"city" : "Rennes",
					"postCode" : "35000",
					"country" : "France"
				},
				{
					"street" : "112 Ocean Avenue",
					"city" : "Amityville",
					"postCode" : "666",
					"country" : "US"
				}
			]
		},
		{
			"name" : "Tournesol",
			"addresses" : [
				{
					"street" : "Parra Grande Ln., Montecito",
					"city" : "Santa Barbara",
					"postCode" : "0420",
					"country" : "US"
				}
			]
		}
	]
}
{
	"_id" : ObjectId("5714e01744aea5065eab0b87"),
	"className" : "fr.istic.sir.tp7.Article",
	"name" : "Sous-marin",
	"stars" : 93,
	"buyers" : [
		{
			"name" : "Dupont",
			"addresses" : [
				{
					"street" : "9303 Roslyndale Avenue",
					"city" : "Los Angeles",
					"postCode" : "1",
					"country" : "US"
				}
			]
		}
	]
}
{
	"_id" : ObjectId("5714e01744aea5065eab0b88"),
	"className" : "fr.istic.sir.tp7.Article",
	"name" : "Statuette",
	"stars" : 91,
	"buyers" : [
		{
			"name" : "Dupond",
			"addresses" : [
				{
					"street" : "Chateau de Moulinsard",
					"city" : "Cheverny",
					"postCode" : "41700",
					"country" : "France"
				}
			]
		}
	]
}
{
	"_id" : ObjectId("5714e01744aea5065eab0b89"),
	"className" : "fr.istic.sir.tp7.Article",
	"name" : "Sceptre",
	"stars" : 89,
	"buyers" : [
		{
			"name" : "Haddock",
			"addresses" : [
				{
					"street" : "123 rue Jean Jaurès",
					"city" : "Rennes",
					"postCode" : "35000",
					"country" : "France"
				},
				{
					"street" : "112 Ocean Avenue",
					"city" : "Amityville",
					"postCode" : "666",
					"country" : "US"
				}
			]
		}
	]
}il s'agit d'
{
	"_id" : ObjectId("5714e01744aea5065eab0b8a"),
	"className" : "fr.istic.sir.tp7.Article",
	"name" : "Cigares",
	"stars" : 88,
	"buyers" : [
		{
			"name" : "Haddock",
			"addresses" : [
				{
					"street" : "123 rue Jean Jaurès",
					"city" : "Rennes",
					"postCode" : "35000",
					"country" : "France"
				},
				{
					"street" : "112 Ocean Avenue",
					"city" : "Amityville",
					"postCode" : "666",
					"country" : "US"
				}
			]
		}
	]
}
{
	"_id" : ObjectId("5714e01744aea5065eab0b8b"),
	"className" : "fr.istic.sir.tp7.Article",
	"name" : "Jarre chinoise",
	"stars" : 84
}
{
	"_id" : ObjectId("5714e01744aea5065eab0b8c"),
	"className" : "fr.istic.sir.tp7.Article",
	"name" : "Champignon géant",
	"stars" : 81,
	"buyers" : [
		{
			"name" : "Tournesol",
			"addresses" : [
				{
					"street" : "Parra Grande Ln., Montecito",
					"city" : "Santa Barbara",
					"postCode" : "0420",
					"country" : "US"
				}
			]
		}
	]
}

```

Prenons l'adresse du **123 rue Jean Jaurès**, elle a pour occupants __*Tintin*__ et __*Haddock*__, c'est à dire qu'elle va être répliquée autant de fois que les personnes __*Tintin*__ et __*Haddock*__ apparaissent, à savoir au moins deux fois.  
De plus, __*Tintin*__ et __*Haddock*__ peuvent apparaitre chacun également plusieurs fois puisque, selon notre modèle, ils peuvent être acheteurs de plusieurs objets.  
Ainsi __*Haddock*__ apparait dans la liste des acheteurs pour la __*Fusée spatiale*__, un __*Sceptre*__ et des __*Cigares*__. Il est donc répliqué 3 fois ainsi que ses adresses.  
L'adresse **123 rue Jean Jaurès** est dupliqué 4 fois dans la collection, il y a une forte redondance des données.  
Il faudrait utiliser l'annotation `@Reference` afin d'éviter cette redondance.


##### La flexibilité des schémas sur une base orientée document, plus généralement sur des bases nosql, permet d'ajouter à la volée des éléments très divers et sans structure fixe, mais cela va complexifier les requêtes sur les documents en les déplaçant sur l'application elle-même. De plus le chargement des données va être très variable, avec notamment des effets de seuils. Enfin toutes les bdd orientées document ne respectent pas forcément les propriétés ACID.
##### On cherche avec ce genre de bdd de la rapidité de traitement plutôt que de la cohérence dans les données.
