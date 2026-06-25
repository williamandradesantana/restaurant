create table tb_tables (
  id bigserial primary key,
  number integer not null unique,
  description varchar(100),
  capacity integer not null default 4,
  status varchar(20) not null default 'FREE',
  check (status in ('FREE', 'OCCUPIED', 'RESERVED', 'INACTIVE'))
);

create table tb_product_categories (
  id bigserial primary key,
  name varchar(100) not null unique,
  active boolean not null default true
);

create table tb_products (
  id bigserial primary key,
  category_id bigint not null references tb_product_categories(id),
  name varchar(150) not null,
  description text,
  price numeric(10,2) not null check (price >= 0),
  available boolean not null default true,
  preparation_time_in_minutes integer,
  created_at timestamp not null default current_timestamp,
  updated_at timestamp
);

create index idx_product_name on tb_products(name);
create index idx_product_categories on tb_products(category_id);

create table tb_orders (
  id bigserial primary key,
  table_id bigint not null references tb_tables(id),
  opening_date timestamp not null default current_timestamp,
  closing_date timestamp,
  status varchar(30) not null default 'OPEN',
  observation text,
  check (status in ('OPEN', 'IN_PREPARATION', 'READY', 'DELIVERED', 'CLOSED', 'CANCELED'))
);

create index idx_table_orders on tb_orders(table_id);
create index idx_order_status on tb_orders(status);
create index idx_order_opening_date on tb_orders(opening_date);

create table tb_order_items (
  id bigserial primary key,
  order_id bigint not null references tb_orders(id),
  product_id bigint not null references tb_products(id),
  quantity integer not null check (quantity > 0),
  unit_price numeric(10,2) not null check (unit_price >= 0),
  observation text,
  status varchar(30) not null default 'PENDING',
  check (status in ('PENDING', 'IN_PREPARATION', 'READY', 'DELIVERED', 'CANCELED'))
);

create index idx_order_item_order on tb_order_items(order_id);
create index idx_order_item_products on tb_order_items(product_id);
create index idx_order_item_status on tb_order_items(status);

create table tb_payments (
  id bigserial primary key,
  order_id bigint not null references tb_orders(id),
  value numeric(10,2) not null check (value >= 0),
  payment_method varchar(30) not null,
  status varchar(30) not null default 'PENDING',
  external_transaction_code varchar(100),
  payment_date timestamp,
  created_at timestamp not null default current_timestamp,
  check (payment_method in ('MONEY', 'CREDIT_CARD', 'DEBIT_CARD', 'PIX')),
  check (status in ('PENDING', 'APPROVED', 'DECLINED', 'CANCELED'))
);

create index idx_order_payments on tb_payments(order_id);
create index idx_payment_status on tb_payments(status);

create table tb_account_closings (
  id bigserial primary key,
  order_id bigint not null unique references tb_orders(id),
  subtotal numeric(10,2) not null check (subtotal >= 0),
  service_fee numeric(10,2) not null default 0 check (service_fee >= 0),
  discount numeric(10,2) not null default 0 check (discount >= 0),
  total numeric(10,2) not null check (total >= 0),
  closing_date timestamp not null default current_timestamp
);