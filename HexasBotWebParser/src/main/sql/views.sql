
DROP view last_product_price;

CREATE OR REPLACE VIEW last_task_price AS
SELECT tp.task, tp.price, tp.date
FROM task_price AS tp
WHERE (tp.task, tp.date) IN (SELECT task, max(date) as date FROM task_price GROUP BY task)

CREATE OR REPLACE VIEW notification AS
SELECT t.id AS task, tp.date, t.type, t.url, p.marque, p.name, tp.price, p.notify_channel
FROM task_price AS tp 
INNER JOIN task AS t on tp.task = t.id 
INNER JOIN product AS p ON p.id = t.product 
WHERE tp.notified IS FALSE 
AND tp.price <= p.notify_price;


CREATE OR REPLACE VIEW last_product_price AS
SELECT p.id AS product_id, p.marque, p.name, 
  t.id AS task_id, t.url, 
  tp.date AS last_price_date, tp.price AS last_price 
FROM       product    AS p
INNER JOIN task       AS t ON p.id = t.product
INNER JOIN task_price AS tp ON t.id = tp.task
WHERE (tp.task, tp.date) IN (SELECT task, max(date) as date FROM task_price GROUP BY task)


