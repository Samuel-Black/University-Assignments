> /* (1) Save all information about the names of products suplied by a supplier Gai paturage into a collection products1. Display in a pretty format without document identifiers all documents in a collection products1. */;
> db.orders.aggregate([ {$match:{"SUPPLIER.company name":"Gai paturage"}},{$project:{"SUPPLIER.supplies.PRODUCT.product name":1,"_id":0}}, {$out:"products1"} ])
> db.products1.find({},{_id:0}).pretty()
{
	"SUPPLIER" : {
		"supplies" : [
			{
				"PRODUCT" : {
					"product name" : "Raclette Courdavault"
				}
			},
			{
				"PRODUCT" : {
					"product name" : "Camembert Pierrot"
				}
			}
		]
	}
}
> 
> 
> /* (2) Save all information about the names of products supplied by a supplier Gai paturage into a collection products2 that consists of the documents like {"product name": a-name-of-product}. Display in a pretty format without document identifiers all documents in a collection products2. */;
> db.orders.aggregate([ {$match:{"SUPPLIER.company name":"Gai paturage"}}, {$unwind:"$SUPPLIER.supplies"}, {$project:{"product name":"$SUPPLIER.supplies.PRODUCT.product name",_id:0}}, {$out:"products2"} ])
> db.products2.find({},{_id:0}).pretty()
{ "product name" : "Raclette Courdavault" }
{ "product name" : "Camembert Pierrot" }
> 
> 
> /* (3) Find the total number of products in a collection orders. Display a result in a format {"total number of products":integer-value}. */;
> db.orders.aggregate([ {$unwind:"$CUSTOMER.submits"}, {$unwind:"$CUSTOMER.submits.ORDER.consists of"}, {$count:"Total number of products ordered"} ])
{ "Total number of products ordered" : 254 }
> 
> 
> /* (4) List in the ascending order the ids of the first 3 employees who handled at least one order. Display the results in a format {"employee id":a-value-of employee-id}. List only distinct values. */;

> db.orders.aggregate([ {$unwind:"$CUSTOMER.submits"}, {$limit:3}, {$sort:{"CUSTOMER.submits.ORDER.employee id":1}}, {$project:{"employee id":"$CUSTOMER.submits.ORDER.employee id", _id:0}} ])
{ "employee id" : 2 }
{ "employee id" : 3 }
{ "employee id" : 8 }
> 
> /* (5) Find the company names of suppliers together with the total number of supplied products by each company. Display the results in a format {"total products":integer-value,"company name":a-company-name}. */;
> db.orders.aggregate([ {$unwind:"$SUPPLIER.supplies"}, {$group:{"_id":"$SUPPLIER.company name", "total products": {$sum:1}}}])
{ "_id" : "Pasta Buttini s.r.l.", "total products" : 2 }
{ "_id" : "Grandma Kelly's Homestead", "total products" : 3 }
{ "_id" : "Karkki Oy", "total products" : 3 }
{ "_id" : "Specialty Biscuits, Ltd.", "total products" : 4 }
{ "_id" : "Refrescos Americanas LTDA", "total products" : 1 }
{ "_id" : "Bigfoot Breweries", "total products" : 3 }
{ "_id" : "PB Knackebrod AB", "total products" : 2 }
{ "_id" : "Svensk Sjofoda AB", "total products" : 3 }
{ "_id" : "Zaanse Snoepfabriek", "total products" : 2 }
{ "_id" : "Ma Maison", "total products" : 2 }
{ "_id" : "Formaggi Fortini s.r.l.", "total products" : 3 }
{ "_id" : "Lyngbysild", "total products" : 2 }
{ "_id" : "Norske Meierier", "total products" : 3 }
{ "_id" : "Exotic Liquids", "total products" : 3 }
{ "_id" : "Cooperativa de Quesos 'Las Cabras'", "total products" : 2 }
{ "_id" : "Pavlova, Ltd.", "total products" : 6 }
{ "_id" : "Escargots Nouveaux", "total products" : 1 }
{ "_id" : "G'day, Mate", "total products" : 3 }
{ "_id" : "Heli Susswaren GmbH And Co. KG", "total products" : 3 }
{ "_id" : "Nord-Ost-Fisch Handelsgesellschaft mbH", "total products" : 1 }

