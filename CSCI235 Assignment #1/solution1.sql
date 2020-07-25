SPOOL solution1
SET ECHO ON
SET FEEDBACK ON
SET LINESIZE 300
SET PAGESIZE 200
/*sib979*/
/*Task 1*/

/*******/
/***i***/
/*******/
EXPLAIN PLAN FOR SELECT *
FROM ORDER_DETAIL
WHERE PRODUCT_NAME = 'BOLT' AND
    QUANTITY > 100;
SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY);
    
CREATE INDEX ORDER_DETAIL_IDX_BOLT_QUANITITY ON ORDER_DETAIL(PRODUCT_NAME, QUANTITY);

EXPLAIN PLAN FOR SELECT *
FROM ORDER_DETAIL
WHERE PRODUCT_NAME = 'BOLT' AND
    QUANTITY > 100;
SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY);

DROP INDEX ORDER_DETAIL_IDX_BOLT_QUANITITY;

/*******/
/***ii**/
/*******/
EXPLAIN PLAN FOR SELECT DISTINCT CATEGORY_NAME
FROM PRODUCT;
SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY);

CREATE INDEX PRODUCT_IDX_CATEGORY_NAME ON PRODUCT(CATEGORY_NAME);

EXPLAIN PLAN FOR SELECT DISTINCT CATEGORY_NAME
FROM PRODUCT;
SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY);

DROP INDEX PRODUCT_IDX_CATEGORY_NAME;

/*******/
/**iii**/
/*******/
EXPLAIN PLAN FOR SELECT UNIT_PRICE
FROM ORDER_DETAIL
WHERE QUANTITY IN (100, 200, 300) OR
    DISCOUNT = 0.01;
SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY);
    
CREATE INDEX ORDER_DETAIL_IDX_UNIT_PRICE_QUANTITY_DISCOUNT ON ORDER_DETAIL(UNIT_PRICE, QUANTITY, DISCOUNT);   

EXPLAIN PLAN FOR SELECT UNIT_PRICE
FROM ORDER_DETAIL
WHERE QUANTITY IN (100, 200, 300) OR
    DISCOUNT = 0.01;
SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY);

DROP INDEX ORDER_DETAIL_IDX_UNIT_PRICE_QUANTITY_DISCOUNT;

/*******/
/***iv**/
/*******/
EXPLAIN PLAN FOR SELECT CATEGORY_NAME, SUPPLIER_NAME, COUNT(*)
FROM PRODUCT
GROUP BY CATEGORY_NAME, SUPPLIER_NAME;
SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY);

CREATE INDEX PRODUCT_IDX_CATEGORY_NAME_SUPPLIER_NAME ON PRODUCT(CATEGORY_NAME, SUPPLIER_NAME);

EXPLAIN PLAN FOR SELECT CATEGORY_NAME, SUPPLIER_NAME, COUNT(*)
FROM PRODUCT
GROUP BY CATEGORY_NAME, SUPPLIER_NAME;
SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY);

DROP INDEX PRODUCT_IDX_CATEGORY_NAME_SUPPLIER_NAME;

/*******/
/***v***/
/*******/
EXPLAIN PLAN FOR SELECT SUPPLIER_NAME, UNIT_PRICE
FROM PRODUCT
ORDER BY UNIT_PRICE, QUANTITY_PER_UNIT;
SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY);

CREATE INDEX PRODUCT_IDX_SUPPLIER_NAME_UNIT_PRICE_QUANTITY_PER_UNIT ON PRODUCT(SUPPLIER_NAME, UNIT_PRICE, QUANTITY_PER_UNIT);

EXPLAIN PLAN FOR SELECT SUPPLIER_NAME, UNIT_PRICE
FROM PRODUCT
ORDER BY UNIT_PRICE, QUANTITY_PER_UNIT;
SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY);

DROP INDEX PRODUCT_IDX_SUPPLIER_NAME_UNIT_PRICE_QUANTITY_PER_UNIT;
