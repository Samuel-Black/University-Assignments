SPOOL solution3
SET SERVEROUTPUT ON
SET ECHO ON
SET FEEDBACK ON
SET LINESIZE 100
SET PAGESIZE 200
SET SERVEROUTPUT ON
/*sib979*/
/*Task 3*/
CREATE OR REPLACE PROCEDURE INSERT_ORDER_DETAIL (
orderID        IN ORDER_DETAIL.ORDER_ID%TYPE, 
productName    IN ORDER_DETAIL.PRODUCT_NAME%TYPE, 
unitPrice      IN ORDER_DETAIL.UNIT_PRICE%TYPE, 
quantity        IN ORDER_DETAIL.QUANTITY%TYPE, 
discount        IN ORDER_DETAIL.DISCOUNT%TYPE) IS

    discontinued PRODUCT.DISCONTINUED%TYPE;
    
    discontinuedProduct EXCEPTION;
    
BEGIN
        SELECT PRODUCT.DISCONTINUED
        INTO discontinued
        FROM PRODUCT
        WHERE PRODUCT.PRODUCT_NAME = productName;
        IF discontinued = 'Y' THEN
            RAISE discontinuedProduct;
        ELSE
            INSERT INTO ORDER_DETAIL
            VALUES ( orderID, productName, unitPrice, quantity, discount );
            dbms_output.put_line('Order details succesfully added.');
            COMMIT;
        END IF;
EXCEPTION
    WHEN discontinuedProduct THEN
        dbms_output.put_line('Error adding order details, product has been discontinued.');
        ROLLBACK;

END INSERT_ORDER_DETAIL;
/
EXECUTE INSERT_ORDER_DETAIL(248, 'Grandma''s Boysenberry Spread', 20, 1, 0);
EXECUTE INSERT_ORDER_DETAIL(248, 'Chef Anton''s Gumbo Mix', 20, 1, 0);






ALTER TABLE CUSTOMER ADD (TOTAL_ORDERS NUMBER(7));
UPDATE CUSTOMER
SET TOTAL_ORDERS = ( SELECT count(*)
FROM ORDERS
WHERE ORDERS.CUSTOMER_CODE = CUSTOMER.CUSTOMER_CODE );

CREATE OR REPLACE PROCEDURE INSERT_ORDER(oid IN NUMBER,
ccode IN VARCHAR,
eid IN NUMBER) IS
total CUSTOMER.TOTAL_ORDERS%TYPE;
BEGIN
INSERT INTO ORDERS VALUES(oid, ccode, eid, sysdate, sysdate + 5, sysdate + 1,
NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
SELECT TOTAL_ORDERS
INTO total
FROM CUSTOMER
WHERE CUSTOMER.CUSTOMER_CODE = ccode;
total := total + 1;
UPDATE CUSTOMER
SET TOTAL_ORDERS = total
WHERE CUSTOMER.CUSTOMER_CODE = ccode;
COMMIT;
EXCEPTION
WHEN OTHERS THEN
DBMS_OUTPUT.PUT_LINE( SQLERRM );
ROLLBACK;
END;
/