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

![architecture](https://user-images.githubusercontent.com/46211420/91979318-35d58980-ed43-11ea-9a0f-00cbd799642e.jpeg)
