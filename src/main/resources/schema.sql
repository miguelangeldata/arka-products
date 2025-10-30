
CREATE TABLE IF NOT EXISTS products(
     id SERIAL PRIMARY KEY,
     sku VARCHAR(25) NOT NULL,
     name VARCHAR(25),
     category VARCHAR(255),
     description VARCHAR(250),
     price DECIMAL(10, 2),
     total_stock INTEGER,
     reserved_stock INTEGER
 );


 CREATE TABLE IF NOT EXISTS stock_history(
     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
     product_sku VARCHAR(25),
     create_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
     previous_stock INTEGER,
     new_stock INTEGER,
     reason VARCHAR(255)
 );