INSERT INTO tb_tables (number, description, capacity) VALUES
  (1, 'Mesa próxima à entrada', 4),
  (2, 'Mesa central', 4),
  (3, 'Mesa próxima à janela', 2),
  (4, 'Mesa família', 6),
  (5, 'Mesa externa', 4);

INSERT INTO tb_product_categories (name) VALUES
   ('Entradas'),
   ('Pratos Principais'),
   ('Bebidas'),
   ('Sobremesas');

INSERT INTO tb_products (category_id, name, description, price, preparation_time_in_minutes)

SELECT id, 'Batata Frita', 'Porção de batata frita crocante', 28.90, 15
FROM tb_product_categories WHERE name = 'Entradas';

INSERT INTO tb_products (category_id, name, description, price, preparation_time_in_minutes)

SELECT id, 'X-Burger Artesanal', 'Hambúrguer artesanal com queijo e molho especial', 34.90, 20
FROM tb_product_categories WHERE name = 'Pratos Principais';

INSERT INTO tb_products (category_id, name, description, price, preparation_time_in_minutes)

SELECT id, 'Filé com Fritas', 'Filé grelhado acompanhado de batatas fritas', 59.90, 30
FROM tb_product_categories WHERE name = 'Pratos Principais';

INSERT INTO tb_products (category_id, name, description, price, preparation_time_in_minutes)

SELECT id, 'Suco Natural', 'Suco natural da fruta', 12.00, 5
FROM tb_product_categories WHERE name = 'Bebidas';

INSERT INTO tb_products (category_id, name, description, price, preparation_time_in_minutes)

SELECT id, 'Pudim', 'Pudim tradicional da casa', 14.90, 5
FROM tb_product_categories WHERE name = 'Sobremesas';