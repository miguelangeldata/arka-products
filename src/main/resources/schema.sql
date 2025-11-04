
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
 CREATE TABLE IF NOT EXISTS returns(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id VARCHAR(50) NOT NULL,
    returned_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    user_id VARCHAR(50) NOT NULL,
    user_email VARCHAR(50) NOT NULL,
    reason VARCHAR(100),
    status VARCHAR(20)

 );
 CREATE TABLE IF NOT EXISTS returns_items(
     return_id UUID NOT NULL REFERENCES returns(id) ON DELETE CASCADE,
     product_id BIGINT NOT NULL,
     quantity INTEGER NOT NULL
 )