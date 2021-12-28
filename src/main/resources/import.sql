insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');

insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Gourmet', 10, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Delivery', 9.50, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Tuk Tuk Comida Indiana', 15, 2);

insert into estado (id, nome) values (1, 'Paraiba');
insert into estado (id, nome) values (2, 'São Paulo');

insert into cidade (id, nome, estado_id) values (1, 'João Pessoa', 1);
insert into cidade (id, nome, estado_id) values (2, 'Guarulhos', 2);
insert into cidade (id, nome, estado_id) values (3, 'Campina Grande', 1);

INSERT INTO forma_pagamento (id, descricao) values (1,'Cartão de crédito')
INSERT INTO forma_pagamento (id, descricao) values (2,'Cartão de débito')
INSERT INTO forma_pagamento (id, descricao) values (3,'Dinheiro')

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (2, 3),(2, 2), (3, 3)
