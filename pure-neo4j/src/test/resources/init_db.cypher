//COFFEE1: Bourbon Jungle
CREATE (coffee1:Coffee {name:"Bourbon Jungle"})

CREATE (farm1:Farm {name:"El Condor"})

CREATE (district1:District {name:"Santa Ana"})
CREATE (altitude1:Altitude  {meters:"1400"})
CREATE (country1:Country {name:"El Salvador"})
CREATE (bean1:BeanType  {name:"Bourbon"})

CREATE (f1:Flavor {name: "Apricot"})
CREATE (f2:Flavor {name: "Raisin"})

CREATE (brewer1:Brewer {name: "Påfyll"})
CREATE (cafe:Cafe {name: "Påfyll"})

CREATE (coffee1)-[:IS_FARMED_BY]->(farm1)
CREATE (coffee1)-[:BEAN_TYPE]->(bean1)
CREATE (coffee1)-[:TASTE]->(f1)
CREATE (coffee1)-[:TASTE]->(f2)

CREATE (coffee1)-[:BREWED_BY]->(brewer1)
CREATE (coffee1)-[:SOLD_BY]->(cafe)

CREATE (farm1)-[:IN_DISTRICT]->(district1)
CREATE (district1)-[:ALTITUDE]->(altitude1)
CREATE (district1)-[:COUNTRY]->(country1)


//COFFEE2: Layo Tiraga
CREATE (coffee2:Coffee {name:"Layo Tiraga"})

CREATE (farm2:Farm {name:"Layo Tiraga"})

CREATE (district2:District {name:"Uraga, Sidamo"})
CREATE (altitude2:Altitude  {meters:"2200"})
CREATE (country2:Country {name:"Etiopia"})
CREATE (bean2:BeanType  {name:"Typica"})

CREATE (f3:Flavor {name: "Lemon"})
CREATE (f4:Flavor {name: "Currants"})

CREATE (brewer2:Brewer {name: "Solberg og Hansen"})

CREATE (coffee2)-[:IS_FARMED_BY]->(farm2)
CREATE (coffee2)-[:BEAN_TYPE]->(bean1)
CREATE (coffee2)-[:BEAN_TYPE]->(bean2)
CREATE (coffee2)-[:TASTE]->(f3)
CREATE (coffee2)-[:TASTE]->(f4)
CREATE (coffee2)-[:BREWED_BY]->(brewer2)
CREATE (coffee2)-[:SOLD_BY]->(cafe)

CREATE (farm2)-[:IN_DISTRICT]->(district2)
CREATE (district2)-[:ALTITUDE]->(altitude2)
CREATE (district2)-[:COUNTRY]->(country2)
