
DROP view last_product_price;

CREATE OR REPLACE VIEW last_task_price AS
SELECT tp.task, tp.price, tp.date
FROM task_price AS tp
WHERE (tp.task, tp.date) IN (SELECT task, max(date) as date FROM task_price GROUP BY task)


