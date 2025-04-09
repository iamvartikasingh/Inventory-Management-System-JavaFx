USE stockDB;

CREATE TABLE stock (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  quantity INT,
  price DECIMAL(10, 2)
);