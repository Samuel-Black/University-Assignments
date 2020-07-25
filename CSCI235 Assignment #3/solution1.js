> /* (1) Append a new product Changde Noodles that belongs to a category Noodles to a list of products supplies by a supplier located in a city Zaandam. All other information is unknown at the moment. Display the names of products supplied by a supplier located in a city Zaandam. */;
> db.orders.update({"SUPPLIER.city":"Zaandam"}, {$push:{"SUPPLIER.supplies":{"PRODUCT":{"product name":"Changde Noodles"}}}})
WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
> db.orders.find({"SUPPLIER.city":"Zaandam"}, {_id:0,"SUPPLIER.supplies.PRODUCT.product name":1}).pretty()
{
	"SUPPLIER" : {
		"supplies" : [
			{
				"PRODUCT" : {
					"product name" : "Zaanse koeken"
				}
			},
			{
				"PRODUCT" : {
					"product name" : "Chocolade"
				}
			},
			{
				"PRODUCT" : {
					"product name" : "Changde Noodles"
				}
			}
		]
	}
}
> 
> 
> /* (2)Remove information about a product Longlife Tofu supplied by a supplier Tokyo Traders. Display the names of products supplied by a supplier Tokyo Traders. */;
> db.orders.update({"SUPPLIER.company name":"Tokyo Traders"}, {$pull:{"SUPPLIER.supplies":{"PRODUCT.product name":"Longlife Tofu"}}})
WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
> db.orders.find({"SUPPLIER.company name":"Tokyo Traders"}, {_id:0,"SUPPLIER.supplies.PRODUCT.product name":1}).pretty()
{
	"SUPPLIER" : {
		"supplies" : [
			{
				"PRODUCT" : {
					"product name" : "Mishi Kobe Niku"
				}
			},
			{
				"PRODUCT" : {
					"product name" : "Ikura"
				}
			}
		]
	}
}
> 
> 
> /* (3) Increase a unit price of a product Flotemysost by 100%. Display the product name and the changed unit price in a pretty format. */;
> db.orders.update({"SUPPLIER.supplies.PRODUCT.product name":"Flotemysost"}, {$mul:{"SUPPLIER.supplies.$.PRODUCT.unit price":2}})
WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
> db.orders.find({"SUPPLIER.supplies.PRODUCT.product name":"Flotemysost"}, {"SUPPLIER.supplies.PRODUCT.product name":1,"SUPPLIER.supplies.PRODUCT.unit price":1,_id:0}).pretty()
{
	"SUPPLIER" : {
		"supplies" : [
			{
				"PRODUCT" : {
					"product name" : "Geitost",
					"unit price" : 2.5
				}
			},
			{
				"PRODUCT" : {
					"product name" : "Gudbrandsdalsost",
					"unit price" : 36
				}
			},
			{
				"PRODUCT" : {
					"product name" : "Flotemysost",
					"unit price" : 43
				}
			}
		]
	}
}
> 
> 
> /* (4) Rename a key submits to a key sends in the orders submitted by a customer FAMIA. Display all information about a customer FAMIA. */; 
> db.orders.update({"CUSTOMER.customer code":"FAMIA"}, {$rename:{"CUSTOMER.submits":"CUSTOMER.sends"}})
WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
> db.orders.find({"CUSTOMER.customer code":"FAMIA"}).pretty()
{
	"_id" : "FAMIA",
	"CUSTOMER" : {
		"customer code" : "FAMIA",
		"company name" : "Familia Arquibaldo",
		"contact name" : "Aria Cruz",
		"contact title" : "Marketing Assistant",
		"address" : "Rua Oros, 92",
		"city" : "São Paulo",
		"region" : "SP",
		"postal code" : "05442-030",
		"country" : "Brazil",
		"phone" : "(11) 555-9857",
		"fax" : null,
		"sends" : [
			{
				"ORDER" : {
					"order id" : 328,
					"employee id" : 9,
					"order date" : ISODate("1996-12-18T00:00:00Z"),
					"required date" : ISODate("1997-01-01T00:00:00Z"),
					"shipped date" : ISODate("1996-12-25T00:00:00Z"),
					"ship via" : "Federal Shipping",
					"freight" : 13.99,
					"ship name" : "Familia Arquibaldo",
					"ship address" : "Rua Orós, 92",
					"ship city" : "São Paulo",
					"ship region" : "SP",
					"ship postal code" : "05442-030",
					"ship country" : "Brazil",
					"consists of" : [
						{
							"ORDER DETAIL" : {
								"product name" : "Louisiana Fiery Hot Pepper Sauce",
								"unit price" : 16.8,
								"quantity" : 40,
								"discount" : 0
							}
						},
						{
							"ORDER DETAIL" : {
								"product name" : "Raclette Courdavault",
								"unit price" : 44,
								"quantity" : 9,
								"discount" : 0
							}
						},
						{
							"ORDER DETAIL" : {
								"product name" : "Scottish Longbreads",
								"unit price" : 10,
								"quantity" : 10,
								"discount" : 0
							}
						}
					]
				}
			},
			{
				"ORDER" : {
					"order id" : 356,
					"employee id" : 2,
					"order date" : ISODate("1997-01-14T00:00:00Z"),
					"required date" : ISODate("1997-02-11T00:00:00Z"),
					"shipped date" : ISODate("1997-01-17T00:00:00Z"),
					"ship via" : "Federal Shipping",
					"freight" : 21.48,
					"ship name" : "Familia Arquibaldo",
					"ship address" : "Rua Orós, 92",
					"ship city" : "São Paulo",
					"ship region" : "SP",
					"ship postal code" : "05442-030",
					"ship country" : "Brazil",
					"consists of" : [
						{
							"ORDER DETAIL" : {
								"product name" : "Filo Mix",
								"unit price" : 5.6,
								"quantity" : 20,
								"discount" : 0
							}
						}
					]
				}
			}
		]
	}
}
> 
> 
> /* (5) An order with order_id equal to 310 is now handled by an employee with employee id equal to 7. Update the database. After update display order_id and employee id in a pretty format. */;
> db.orders.update({"CUSTOMER.submits.ORDER.order id":310}, {$set:{"CUSTOMER.submits.$.ORDER.employee id":7}})
WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
> db.orders.find({"CUSTOMER.submits.ORDER.order id":310}, {"CUSTOMER.submits.ORDER.order id":1,"CUSTOMER.submits.ORDER.employee id":1, _id:0}).pretty()
{
	"CUSTOMER" : {
		"submits" : [
			{
				"ORDER" : {
					"order id" : 310,
					"employee id" : 7
				}
			},
			{
				"ORDER" : {
					"order id" : 355,
					"employee id" : 3
				}
			},
			{
				"ORDER" : {
					"order id" : 367,
					"employee id" : 6
				}
			}
		]
	}
}

