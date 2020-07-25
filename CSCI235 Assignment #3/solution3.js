> /* (1) Find the company name, contact name, and contact title of the customers with a given company name. */;
> db.orders.createIndex( {"CUSTOMER.company name":1}, {"unique":false} )
{
	"createdCollectionAutomatically" : false,
	"numIndexesBefore" : 1,
	"numIndexesAfter" : 2,
	"ok" : 1
}
> db.orders.getIndexes()
[
	{
		"v" : 2,
		"key" : {
			"_id" : 1
		},
		"name" : "_id_",
		"ns" : "test.orders"
	},
	{
		"v" : 2,
		"key" : {
			"CUSTOMER.company name" : 1
		},
		"name" : "CUSTOMER.company name_1",
		"ns" : "test.orders"
	}
]
> db.orders.find({"CUSTOMER.company name":"QUICK-Stop"}, {"CUSTOMER.company name":1, "CUSTOMER.contact name":1, "CUSTOMER.contact title":1, _id:0}).explain()
{
	"queryPlanner" : {
		"plannerVersion" : 1,
		"namespace" : "test.orders",
		"indexFilterSet" : false,
		"parsedQuery" : {
			"CUSTOMER.company name" : {
				"$eq" : "QUICK-Stop"
			}
		},
		"queryHash" : "C164FE73",
		"planCacheKey" : "92F728C1",
		"winningPlan" : {
			"stage" : "PROJECTION_DEFAULT",
			"transformBy" : {
				"CUSTOMER.company name" : 1,
				"CUSTOMER.contact name" : 1,
				"CUSTOMER.contact title" : 1,
				"_id" : 0
			},
			"inputStage" : {
				"stage" : "FETCH",
				"inputStage" : {
					"stage" : "IXSCAN",
					"keyPattern" : {
						"CUSTOMER.company name" : 1
					},
					"indexName" : "CUSTOMER.company name_1",
					"isMultiKey" : false,
					"multiKeyPaths" : {
						"CUSTOMER.company name" : [ ]
					},
					"isUnique" : false,
					"isSparse" : false,
					"isPartial" : false,
					"indexVersion" : 2,
					"direction" : "forward",
					"indexBounds" : {
						"CUSTOMER.company name" : [
							"[\"QUICK-Stop\", \"QUICK-Stop\"]"
						]
					}
				}
			}
		},
		"rejectedPlans" : [ ]
	},
	"serverInfo" : {
		"host" : "csci235-VirtualBox",
		"port" : 4000,
		"version" : "4.2.2",
		"gitVersion" : "a0bbbff6ada159e19298d37946ac8dc4b497eadf"
	},
	"ok" : 1
}
> db.orders.dropIndex("CUSTOMER.company name_1")
{ "nIndexesWas" : 2, "ok" : 1 }
> 
> /* (2) Find the company name of the customers who submitted an order with a give date. */;
> db.orders.createIndex( {"CUSTOMER.submits.ORDER.order date":1}, {"unique":false} )
{
	"createdCollectionAutomatically" : false,
	"numIndexesBefore" : 1,
	"numIndexesAfter" : 2,
	"ok" : 1
}
> db.orders.getIndexes()
[
	{
		"v" : 2,
		"key" : {
			"_id" : 1
		},
		"name" : "_id_",
		"ns" : "test.orders"
	},
	{
		"v" : 2,
		"key" : {
			"CUSTOMER.submits.ORDER.order date" : 1
		},
		"name" : "CUSTOMER.submits.ORDER.order date_1",
		"ns" : "test.orders"
	}
]
> db.orders.find({"CUSTOMER.submits.ORDER.order date":new Date("1997-01-14")}, {"CUSTOMER.company name":1, _id:0}).explain()
{
	"queryPlanner" : {
		"plannerVersion" : 1,
		"namespace" : "test.orders",
		"indexFilterSet" : false,
		"parsedQuery" : {
			"CUSTOMER.submits.ORDER.order date" : {
				"$eq" : ISODate("1997-01-14T00:00:00Z")
			}
		},
		"queryHash" : "A1E86853",
		"planCacheKey" : "055C64E3",
		"winningPlan" : {
			"stage" : "PROJECTION_DEFAULT",
			"transformBy" : {
				"CUSTOMER.company name" : 1,
				"_id" : 0
			},
			"inputStage" : {
				"stage" : "FETCH",
				"inputStage" : {
					"stage" : "IXSCAN",
					"keyPattern" : {
						"CUSTOMER.submits.ORDER.order date" : 1
					},
					"indexName" : "CUSTOMER.submits.ORDER.order date_1",
					"isMultiKey" : true,
					"multiKeyPaths" : {
						"CUSTOMER.submits.ORDER.order date" : [
							"CUSTOMER.submits"
						]
					},
					"isUnique" : false,
					"isSparse" : false,
					"isPartial" : false,
					"indexVersion" : 2,
					"direction" : "forward",
					"indexBounds" : {
						"CUSTOMER.submits.ORDER.order date" : [
							"[new Date(853200000000), new Date(853200000000)]"
						]
					}
				}
			}
		},
		"rejectedPlans" : [ ]
	},
	"serverInfo" : {
		"host" : "csci235-VirtualBox",
		"port" : 4000,
		"version" : "4.2.2",
		"gitVersion" : "a0bbbff6ada159e19298d37946ac8dc4b497eadf"
	},
	"ok" : 1
}
> db.orders.dropIndex("CUSTOMER.submits.ORDER.order date_1")
{ "nIndexesWas" : 2, "ok" : 1 }
> 
> /* (3) Find the names of products ordered by the customers living in a given city. */;
> db.orders.createIndex( {"CUSTOMER.city":1}, {"unique":false} )
{
	"createdCollectionAutomatically" : false,
	"numIndexesBefore" : 1,
	"numIndexesAfter" : 2,
	"ok" : 1
}
> db.orders.getIndexes()
[
	{
		"v" : 2,
		"key" : {
			"_id" : 1
		},
		"name" : "_id_",
		"ns" : "test.orders"
	},
	{
		"v" : 2,
		"key" : {
			"CUSTOMER.city" : 1
		},
		"name" : "CUSTOMER.city_1",
		"ns" : "test.orders"
	}
]
> db.orders.find({"CUSTOMER.city":"São Paulo"}, {"CUSTOMER.submits.ORDER.consists of.ORDER DETAIL.product name":1, _id:0}).explain()
{
	"queryPlanner" : {
		"plannerVersion" : 1,
		"namespace" : "test.orders",
		"indexFilterSet" : false,
		"parsedQuery" : {
			"CUSTOMER.city" : {
				"$eq" : "São Paulo"
			}
		},
		"queryHash" : "239FA103",
		"planCacheKey" : "F066C626",
		"winningPlan" : {
			"stage" : "PROJECTION_DEFAULT",
			"transformBy" : {
				"CUSTOMER.submits.ORDER.consists of.ORDER DETAIL.product name" : 1,
				"_id" : 0
			},
			"inputStage" : {
				"stage" : "FETCH",
				"inputStage" : {
					"stage" : "IXSCAN",
					"keyPattern" : {
						"CUSTOMER.city" : 1
					},
					"indexName" : "CUSTOMER.city_1",
					"isMultiKey" : false,
					"multiKeyPaths" : {
						"CUSTOMER.city" : [ ]
					},
					"isUnique" : false,
					"isSparse" : false,
					"isPartial" : false,
					"indexVersion" : 2,
					"direction" : "forward",
					"indexBounds" : {
						"CUSTOMER.city" : [
							"[\"São Paulo\", \"São Paulo\"]"
						]
					}
				}
			}
		},
		"rejectedPlans" : [ ]
	},
	"serverInfo" : {
		"host" : "csci235-VirtualBox",
		"port" : 4000,
		"version" : "4.2.2",
		"gitVersion" : "a0bbbff6ada159e19298d37946ac8dc4b497eadf"
	},
	"ok" : 1
}
> db.orders.dropIndex("CUSTOMER.city_1")
{ "nIndexesWas" : 2, "ok" : 1 }
> 
> /* (4) Find the names of countries the customers live in. */;
> db.orders.createIndex( {"CUSTOMER.customer code":1, "CUSTOMER.country":1}, { "unique": true, "sparse": true } )
{
	"createdCollectionAutomatically" : false,
	"numIndexesBefore" : 1,
	"numIndexesAfter" : 2,
	"ok" : 1
}
> db.orders.getIndexes()
[
	{
		"v" : 2,
		"key" : {
			"_id" : 1
		},
		"name" : "_id_",
		"ns" : "test.orders"
	},
	{
		"v" : 2,
		"unique" : true,
		"key" : {
			"CUSTOMER.customer code" : 1,
			"CUSTOMER.country" : 1
		},
		"name" : "CUSTOMER.customer code_1_CUSTOMER.country_1",
		"ns" : "test.orders",
		"sparse" : true
	}
]
> db.orders.find({"CUSTOMER.customer code":{$ne:null}}, {"CUSTOMER.country":1, _id:0}).explain()
{
	"queryPlanner" : {
		"plannerVersion" : 1,
		"namespace" : "test.orders",
		"indexFilterSet" : false,
		"parsedQuery" : {
			"CUSTOMER.customer code" : {
				"$not" : {
					"$eq" : null
				}
			}
		},
		"queryHash" : "B746CA71",
		"planCacheKey" : "A68D6AA6",
		"winningPlan" : {
			"stage" : "PROJECTION_DEFAULT",
			"transformBy" : {
				"CUSTOMER.country" : 1,
				"_id" : 0
			},
			"inputStage" : {
				"stage" : "IXSCAN",
				"keyPattern" : {
					"CUSTOMER.customer code" : 1,
					"CUSTOMER.country" : 1
				},
				"indexName" : "CUSTOMER.customer code_1_CUSTOMER.country_1",
				"isMultiKey" : false,
				"multiKeyPaths" : {
					"CUSTOMER.customer code" : [ ],
					"CUSTOMER.country" : [ ]
				},
				"isUnique" : true,
				"isSparse" : true,
				"isPartial" : false,
				"indexVersion" : 2,
				"direction" : "forward",
				"indexBounds" : {
					"CUSTOMER.customer code" : [
						"[MinKey, undefined)",
						"(null, MaxKey]"
					],
					"CUSTOMER.country" : [
						"[MinKey, MaxKey]"
					]
				}
			}
		},
		"rejectedPlans" : [ ]
	},
	"serverInfo" : {
		"host" : "csci235-VirtualBox",
		"port" : 4000,
		"version" : "4.2.2",
		"gitVersion" : "a0bbbff6ada159e19298d37946ac8dc4b497eadf"
	},
	"ok" : 1
}
> db.orders.dropIndex("CUSTOMER.customer code_1_CUSTOMER.country_1")
{ "nIndexesWas" : 2, "ok" : 1 }

