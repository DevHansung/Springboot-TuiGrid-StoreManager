-----User and Role table setting start-----
--DDL
CREATE TABLE IF NOT EXISTS user
(
	user_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	username VARCHAR(30) NOT NULL,
	password VARCHAR(100) NOT NULL,
	name VARCHAR(30) NOT NULL,
	email VARCHAR(50) NOT NULL,
	branch VARCHAR(50) NOT NULL,
	enabled INT(11)  DEFAULT 0
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS role
(
	role_id INT NOT NULL,
	role_name varchar(20) NOT NULL,
	PRIMARY KEY (role_id, role_name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS user_role
(
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES user (user_id),
  FOREIGN KEY (role_id) REFERENCES role (role_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--Default Role
INSERT IGNORE INTO role (role_id, role_name) VALUES (1, 'ROLE_ADMIN');
INSERT IGNORE INTO role (role_id, role_name) VALUES (2, 'ROLE_MANAGER');
INSERT IGNORE INTO role (role_id, role_name) VALUES (3, 'ROLE_DIRECTOR');
-----User and Role table setting end-----

-----Business table create start-----
--products table create
CREATE TABLE IF NOT EXISTS products
(
	product_seq BIGINT AUTO_INCREMENT NOT NULL UNIQUE,
	product_code VARCHAR(45) NOT NULL UNIQUE,
	product_name VARCHAR(45) NOT NULL UNIQUE,
	product_type VARCHAR(45) NOT NULL,
	product_color VARCHAR(45) NOT NULL,
	product_size VARCHAR(45) NOT NULL,
	product_purchase_price INT(11) NOT NULL,
	product_selling_price INT(11) NOT NULL,
	product_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(product_seq, product_code),
   	INDEX idx_product_code(product_code)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--orders table create
CREATE TABLE IF NOT EXISTS orders
(
	order_seq BIGINT AUTO_INCREMENT NOT NULL,
	product_code VARCHAR(45) NOT NULL,
	product_name VARCHAR(45) NOT NULL,
	product_type VARCHAR(45) NOT NULL,
	product_color VARCHAR(45) NOT NULL,
	product_size VARCHAR(45) NOT NULL,
	product_selling_price INT(11) NOT NULL,
	product_purchase_price INT(11) NOT NULL,
	order_quantity INT(11) NOT NULL,
	order_price INT(11) NOT NULL,
	order_state VARCHAR(45) NOT NULL,
	order_branch VARCHAR(45) NOT NULL,
	order_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(order_seq, product_code),
   	INDEX idx_product_code(product_code)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--sales table create
CREATE TABLE IF NOT EXISTS sales
(
	sales_seq BIGINT AUTO_INCREMENT NOT NULL,
	product_code VARCHAR(45) NOT NULL,
	product_name VARCHAR(45) NOT NULL,
	product_type VARCHAR(45) NOT NULL,
	product_color VARCHAR(45) NOT NULL,
	product_size VARCHAR(45) NOT NULL,
	product_selling_price INT(11) NOT NULL,
	sales_quantity INT(11) NOT NULL,
	sales_price INT(11) NOT NULL,
	sales_state VARCHAR(45) NOT NULL,
	sales_branch VARCHAR(45) NOT NULL,
	sales_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(sales_seq, product_code),
   	INDEX idx_product_code(product_code)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--stocks table create
CREATE TABLE IF NOT EXISTS stocks
(
	stock_seq BIGINT AUTO_INCREMENT NOT NULL,
	product_code VARCHAR(45) NOT NULL,
	product_name VARCHAR(45) NOT NULL,
	product_type VARCHAR(45) NOT NULL,
	product_color VARCHAR(45) NOT NULL,
	product_size VARCHAR(45) NOT NULL,
	product_selling_price INT(11) NOT NULL,
	order_total_quantity INT(11) NOT NULL,
	sales_total_quantity INT(11) NOT NULL DEFAULT '0',
	stock_total_quantity INT(11) NOT NULL DEFAULT '0',
	sales_rate INT(11) NOT NULL DEFAULT '0',
	stock_branch VARCHAR(45) NOT NULL,
	stock_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY(stock_seq, product_code)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--board table create
CREATE TABLE IF NOT EXISTS board
(
	board_num INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	board_title VARCHAR(45) NOT NULL,
	board_text VARCHAR(255) NOT NULL,
	board_writer VARCHAR(45) NOT NULL,
	board_branch VARCHAR(45) NOT NULL,
	board_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	board_count INT(11)  DEFAULT 0
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--board_reply table create
CREATE TABLE IF NOT EXISTS board_reply
(
  reply_num INT(11) AUTO_INCREMENT NOT NULL,
  board_num INT(11) NOT NULL,
  reply_text VARCHAR(255) NOT NULL,
  reply_writer VARCHAR(45) NOT NULL,
  reply_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (reply_num),
  FOREIGN KEY (board_num) REFERENCES board(board_num) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
-----Business table create end-----

-----Trigger create start----- 
-----orders trigger
DROP TRIGGER IF EXISTS update_order_stocks ^;
CREATE TRIGGER update_order_stocks
	AFTER UPDATE ON orders
	FOR EACH ROW 
BEGIN
	DECLARE req_stock_seq INTEGER;
	SET req_stock_seq = (SELECT stock_seq FROM stocks WHERE product_code = NEW.product_code 
				AND stock_branch = NEW.order_branch);
	IF NEW.order_state = '수령' THEN
		IF req_stock_seq IS NOT NULL  THEN 
			UPDATE stocks SET 
			order_total_quantity = order_total_quantity + NEW.order_quantity ,
 			stock_total_quantity = stock_total_quantity + NEW.order_quantity, 
            		sales_rate = sales_total_quantity / order_total_quantity * 100
			WHERE stock_seq =  req_stock_seq;
		ELSEIF req_stock_seq IS NULL THEN
		  	INSERT INTO stocks (product_code, product_name, product_type, product_color, product_size,
					   product_selling_price, order_total_quantity, stock_total_quantity, stock_branch )
		  	VALUES (NEW.product_code, NEW.product_name, NEW.product_type, NEW.product_color, 
				NEW.product_size, NEW.product_selling_price, NEW.order_quantity, NEW.order_quantity, NEW.order_branch );
		END IF;
	END IF;
END ^;

-----sales trigger
DROP TRIGGER IF EXISTS insert_sales_stocks  ^;
CREATE TRIGGER insert_sales_stocks
AFTER INSERT ON sales
FOR EACH ROW 
BEGIN
	DECLARE req_stock_seq INTEGER;
	DECLARE req_stock_total_quantity INTEGER;
	SET req_stock_seq = (SELECT stock_seq FROM stocks WHERE product_code = NEW.product_code 
							AND stock_branch = NEW.sales_branch);
	SET req_stock_total_quantity = (SELECT stock_total_quantity FROM stocks WHERE product_code = NEW.product_code 
							AND stock_branch = NEW.sales_branch);
	IF req_stock_total_quantity >= NEW.sales_quantity THEN
		IF req_stock_seq IS NOT NULL THEN 
				UPDATE stocks SET 
                sales_total_quantity = sales_total_quantity  + NEW.sales_quantity,
				stock_total_quantity = stock_total_quantity - NEW.sales_quantity,
				sales_rate = sales_total_quantity / order_total_quantity * 100
				WHERE stock_seq =  req_stock_seq;
		END IF;
	END IF;
END ^;

DROP TRIGGER IF EXISTS update_sales_stocks ^;
CREATE TRIGGER update_sales_stocks
AFTER UPDATE ON sales
FOR EACH ROW 
BEGIN
	DECLARE req_stock_seq INTEGER;
	DECLARE req_stock_total_quantity INTEGER;
	SET req_stock_seq = (SELECT stock_seq FROM stocks WHERE product_code = NEW.product_code 
							AND stock_branch = NEW.sales_branch);
	SET req_stock_total_quantity = (SELECT stock_total_quantity FROM stocks WHERE product_code = NEW.product_code 
							AND stock_branch = NEW.sales_branch);
	IF req_stock_total_quantity + OLD.sales_quantity >= NEW.sales_quantity THEN
		IF req_stock_seq IS NOT NULL THEN 
				UPDATE stocks SET 
				sales_total_quantity = sales_total_quantity - OLD.sales_quantity + NEW.sales_quantity,
				stock_total_quantity = stock_total_quantity + OLD.sales_quantity - NEW.sales_quantity,
				sales_rate = sales_total_quantity / order_total_quantity * 100
				WHERE stock_seq =  req_stock_seq;
		END IF;
   END IF;
END ^;

DROP TRIGGER IF EXISTS delete_sales_stocks ^;
CREATE TRIGGER delete_sales_stocks
AFTER DELETE ON sales
FOR EACH ROW 
BEGIN
	DECLARE req_stock_seq INTEGER;
	DECLARE req_stock_total_quantity INTEGER;
	SET req_stock_seq = (SELECT stock_seq FROM stocks WHERE product_code = OLD.product_code 
							AND stock_branch = OLD.sales_branch);
	SET req_stock_total_quantity = (SELECT stock_total_quantity FROM stocks WHERE product_code = OLD.product_code 
							AND stock_branch = OLD.sales_branch);
	IF req_stock_total_quantity + OLD.sales_quantity >= OLD.sales_quantity THEN
		IF req_stock_seq IS NOT NULL THEN 
				UPDATE stocks SET 
				sales_total_quantity = sales_total_quantity - OLD.sales_quantity,
				stock_total_quantity = stock_total_quantity + OLD.sales_quantity,
				sales_rate = sales_total_quantity / order_total_quantity * 100
				WHERE stock_seq =  req_stock_seq;
		END IF;
   END IF;
END ^;
-----Trigger create end-----

