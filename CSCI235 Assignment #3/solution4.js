/* Start a command line interface mongo to MongoDB database server and process the following statements. */;
/* db.adminCommand( {setFeatureCompatibilityVersion:"4.2"} ); */;
/* use test; */;



> /* Use a method createCollection() to create a collection of documents task4 and use JSON schema validator to enforce the following constraints on the collection. */;
> /* (1) Information about products must be nested within information about suppliers. */;
> /* (2) Information about keywords describing products must be nested within information about products. */;
> /* (3) The values associated with the key names company name, city, (within SUPPLIER class), product name, keywords */;
> /*     (within PRODUCT class) must be of type string. */;
> /* (4)The values associated with a key postal code (within SUPPLIER class) must be of type integer in a range 0..9999. */;
> /* (5)The values associated with a key units in stock (within PRODUCT class) must be of type integer and must positive. */;
> /* (6) The values associated with a key unit price must be of type double and must be positive and less than 100.0. */;
> /* (7)A key company name is mandatory. */;
> 
> db.createCollection("task4", { "validator": { "$jsonSchema" : {
... "bsonType":"object",
... "required":["_id","SUPPLIER"],
... "properties":{"_id": {"bsonType":"string"},"SUPPLIER":{ "bsonType":"object",
...     "required":["company_name","city"],
...     "properties":{ "company_name":{"bsonType":"string"},
...    "city":{"bsonType":"string"},
...    "postal_code":{"bsonType":"int","minimum":0,"maximum":9999},
...    "supplies":{"bsonType":"array",
... "required":["PRODUCT"],
... "properties":{"PRODUCT":{  "bsonType":"object",
... "required":["product_name","keywords"],
... "properties":{ "product-name":{"bsonType":"string"},
... "unit_price":{"bsonType":"double","minimum":0.00,"maximum":100.00},
... "units_in_stock":{"bsonType":"int","minimum":0},
... "keywords":{"bsonType":"array",
...     "minItems":1,
...     "properties":{"words": {"bsonType":"string"}}}}}}}
...                  } 
...    }
... }}}});
{ "ok" : 1 }
> 
> 
> /* Next, insert into a collection task4 two sample documents. The first document must pass all validations of the constraints listed above. */
... db.task4.insert({"_id":"Yeah Good Industries","SUPPLIER": {"company_name":"Yeah Good Industries", "city":"Big City", "postal_code":NumberInt("1875"), "supplies": [{"PRODUCT": {"product_name":"yo-yo", keywords:["yo","-","yo"]} }] }})
WriteResult({ "nInserted" : 1 })
> 
> 
> /* The second document must fail validation of one of the constraints listed above. Provide information why a document fails a validation */;
> /* Reason: postal_code is not inside the allowed range */;
> 
> db.task4.insert({"_id":"VaN","SUPPLIER": {"company_name":"VaN", "city":"Small City", "postal_code":NumberInt("10000"), "supplies": [{"PRODUCT": {"product_name":"Water Bottle", keywords:["Water","Refreshing"]} }] }})
WriteResult({
	"nInserted" : 0,
	"writeError" : {
		"code" : 121,
		"errmsg" : "Document failed validation"
	}
})




