alter table tb_order_items
    add column preparation_start_date timestamp,
    add column completion_date timestamp,
    add column delivery_date timestamp;