
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

