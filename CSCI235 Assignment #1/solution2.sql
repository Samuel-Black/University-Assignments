SPOOL solution2
SET SERVEROUTPUT ON
SET ECHO ON
SET FEEDBACK ON
SET LINESIZE 100
SET PAGESIZE 200
SET SERVEROUTPUT ON
/*sib979*/
/*Task 2*/
DECLARE
loopIndex NUMBER := 0;
loopBreak NUMBER := 5;
loopException EXCEPTION;
ORDER_ID ORDERS.ORDER_ID%TYPE;
ORDER_DATE ORDERS.ORDER_DATE%TYPE;
COMPANY_NAME CUSTOMER.COMPANY_NAME%TYPE;
CURSOR C_ORDER IS
    SELECT ORDERS.ORDER_ID, ORDERS.ORDER_DATE, CUSTOMER.COMPANY_NAME 
    FROM ORDERS 
    LEFT OUTER JOIN CUSTOMER ON ORDERS.CUSTOMER_CODE = CUSTOMER.CUSTOMER_CODE
    ORDER BY ORDER_DATE DESC;
BEGIN
 OPEN C_ORDER; 
   LOOP 
   FETCH C_ORDER INTO ORDER_ID, ORDER_DATE, COMPANY_NAME;
    loopIndex := loopIndex+1;
    dbms_output.put_line('Order id: ' || ORDER_ID);
    dbms_output.put_line('Order date: ' || ORDER_DATE);
    dbms_output.put_line('company name: ' || COMPANY_NAME);
    dbms_output.put_line('=======================================');
    IF loopIndex >= loopBreak THEN 
        RAISE loopException;
        EXIT;
    END IF;
    END LOOP;
    CLOSE C_ORDER;
EXCEPTION
WHEN loopException THEN
    dbms_output.put_line('Five most recent records retrieved.');
END;
