# ecommerce-kafka-producer
A scala producer to continously push data to kafka topics simulateneously

Topic schema and table Schema:

order details:
order_id:product_id:customer_id:quantity

product_details:
product_id:Product_price:product_category

Customer_details:
customer_id:customer_name:customer_mail_id

Joined_df:

customer_id:customer_name:sum(Amount):max(product_category)
