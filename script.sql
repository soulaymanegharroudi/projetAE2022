DROP SCHEMA IF EXISTS pae CASCADE;
CREATE SCHEMA pae;

CREATE TABLE pae.addresses
(
    id_address      SERIAL PRIMARY KEY,
    street          VARCHAR(25),
    building_number INTEGER,
    postcode        INTEGER,
    city            varchar(25),
    unit_number     varchar(10)
);

CREATE TABLE pae.members
(
    id_member                  SERIAL PRIMARY KEY,
    password                   VARCHAR(60),
    username                   VARCHAR(25),
    last_name                  VARCHAR(25),
    first_name                 VARCHAR(25),
    address                    INTEGER REFERENCES pae.addresses (id_address),
    call_number                VARCHAR(10),
    isAdmin                    bool    DEFAULT FALSE,
    reason_for_conn_refusal    VARCHAR(300),
    state                      VARCHAR(10),
    count_object_not_collected INTEGER DEFAULT (0),
    count_object_given         INTEGER DEFAULT (0),
    count_object_got           INTEGER DEFAULT (0)
);

CREATE TABLE pae.types
(
    id_type SERIAL PRIMARY KEY,
    type    VARCHAR(60)
);

CREATE TABLE pae.ratings
(
    id_rating           SERIAL PRIMARY KEY,
    rating              INTEGER,
    comment             VARCHAR(300),
    id_recipient_member INTEGER REFERENCES pae.members (id_member)
);

CREATE TABLE pae.items
(
    id_item            SERIAL PRIMARY KEY,
    type               INTEGER REFERENCES pae.types (id_type),
    photo              VARCHAR(100),
    description        VARCHAR(300),
    availabilities     VARCHAR(300),
    item_condition     VARCHAR(10),
    rating             INTEGER REFERENCES pae.ratings (id_rating),
    id_offering_member INTEGER REFERENCES pae.members (id_member)
);



CREATE TABLE pae.offers
(
    id_offer   SERIAL PRIMARY KEY,
    date_offer DATE,
    id_item    INTEGER REFERENCES pae.items (id_item)
);

CREATE TABLE pae.interests
(
    id_interest   SERIAL PRIMARY KEY,
    id_item       INTEGER REFERENCES pae.items (id_item),
    id_member     INTEGER REFERENCES pae.members (id_member),
    isRecipient   bool DEFAULT FALSE,
    date_delivery DATE,
    came          bool DEFAULT FALSE
);

-- INSERT FAKE MEMBERS

INSERT INTO pae.addresses
    (street, building_number, postcode, city, unit_number)
VALUES ('Rue de la loi', 16, 1000, 'Bruxelles', 15);

INSERT INTO pae.members
(password, username, last_name, first_name, address, call_number, isadmin,
 reason_for_conn_refusal, state)
VALUES ('$2a$12$LkYpSJKgVUVn4NcuLddd7eZHm28tRQXTjqVQkTUgLYEP1mlPPRCRW', 'souli',
        'Gharroudi', 'Soulaymane', 1, '0489789546', true, default, 'pending');

INSERT INTO pae.members
(password, username, last_name, first_name, address, call_number, isadmin,
 reason_for_conn_refusal, state)
VALUES ('$2a$12$LkYpSJKgVUVn4NcuLddd7eZHm28tRQXTjqVQkTUgLYEP1mlPPRCRW', 'quentin',
        'Garwig', 'Quentin', 1, '0489789546', true, default, 'valid');

INSERT INTO pae.members
(password, username, last_name, first_name, address, call_number, isadmin,
 reason_for_conn_refusal, state)
VALUES ('$2a$12$LkYpSJKgVUVn4NcuLddd7eZHm28tRQXTjqVQkTUgLYEP1mlPPRCRW', 'stal',
        'Bouillon', 'Guillaume', 1, '0489789546', true, default, 'valid');

INSERT INTO pae.members
(password, username, last_name, first_name, address, call_number, isadmin,
 reason_for_conn_refusal, state)
VALUES ('$2a$12$LkYpSJKgVUVn4NcuLddd7eZHm28tRQXTjqVQkTUgLYEP1mlPPRCRW', 'max',
        'Lecocq', 'Maxime', 1, '0489789546', true, default, 'valid');


--Insert demo's types
INSERT INTO pae.types (type)
VALUES ('Accessoires pour animaux domestiques');
INSERT INTO pae.types (type)
VALUES ('Accessoires pour voiture');
INSERT INTO pae.types (type)
VALUES ('Décoration');
INSERT INTO pae.types (type)
VALUES ('Jouets');
INSERT INTO pae.types (type)
VALUES ('Literie');
INSERT INTO pae.types (type)
VALUES ('Matériel de cuisine');
INSERT INTO pae.types (type)
VALUES ('Matériel de jardinage');
INSERT INTO pae.types (type)
VALUES ('Meuble');
INSERT INTO pae.types (type)
VALUES ('Plantes');
INSERT INTO pae.types (type)
VALUES ('Produits cosmétiques');
INSERT INTO pae.types (type)
VALUES ('Vélo, trottinette');
INSERT INTO pae.types (type)
VALUES ('Vêtements');

--Insert demo's adresses TODO escape simple quote
INSERT INTO pae.addresses
    (street, building_number, postcode, city, unit_number)
VALUES ('Rue de l Eglise', 11, 4987, 'Stoumont', 'B1');

INSERT INTO pae.addresses
    (street, building_number, postcode, city, unit_number)
VALUES ('Rue de Renkin', 7, 4800, 'Verviers', '7');

INSERT INTO pae.addresses
    (street, building_number, postcode, city, unit_number)
VALUES ('Rue Haute Folie', 6, 4800, 'Verviers', 'A103');

INSERT INTO pae.addresses
    (street, building_number, postcode, city)
VALUES ('Haut-Vinâve', 13, 4845, 'Jalhay');


--Insert demo's users
/*
mdp = Mdpuser.1
mdp2= Rad;293 uniquement pour le dernier membre
*/
INSERT INTO pae.members
(password, username, last_name, first_name, address,
 reason_for_conn_refusal, state)
VALUES ('$2y$10$GeZQI8Mx/eUl2JD7SgSxAeJgJ4uDwnM8wGvQuh3kCZDCQO4.CAOja', 'caro',
        'Line', 'Caroline', 2, 'Il faudra patienter un jour ou deux.', 'denied');
INSERT INTO pae.members
    (password, username, last_name, first_name, address, state)
VALUES ('$2y$10$GeZQI8Mx/eUl2JD7SgSxAeJgJ4uDwnM8wGvQuh3kCZDCQO4.CAOja', 'achil',
        'Ile', 'Achille', 3, 'valid');
INSERT INTO pae.members
    (password, username, last_name, first_name, address, state)
VALUES ('$2y$10$GeZQI8Mx/eUl2JD7SgSxAeJgJ4uDwnM8wGvQuh3kCZDCQO4.CAOja', 'bazz',
        'Ile', 'Basile', 4, 'confirm');
INSERT INTO pae.members
    (password, username, last_name, first_name, address, state)
VALUES ('$2y$10$DHvrRhRwxSs46UUSP1p3EOY3EsnG7jUCQ8nHw9tv/iSI91TOSxcpi', 'bri',
        'Lehmann', 'Brigitte', 5, 'confirm');

-- Insert demo's items
INSERT INTO pae.items
(id_offering_member, type, availabilities, description, item_condition, photo)
VALUES (7, 3, 'Mardi de 17h à 22h', 'Décoration de Noël de couleur rouge', 'given', null);

INSERT INTO pae.items
(id_offering_member, type, availabilities, description, item_condition, photo)
VALUES (7, 3, 'Lundi de 18h à 22h', 'Cadre représentant un chien noir sur un fond noir', 'given',
        null);

INSERT INTO pae.items
(id_offering_member, type, availabilities, description, item_condition, photo)
VALUES (8, 8, 'Tous les jours de 15h à 18h', 'Ancien bureau d écolier', 'given', null);

INSERT INTO pae.items
(id_offering_member, type, availabilities, description, item_condition, photo)
VALUES (7, 2, 'Tous les jours de 15h à 18h', 'Item de test published', 'published', null);

INSERT INTO pae.items
(id_offering_member, type, availabilities, description, item_condition, photo)
VALUES (7, 2, 'Tous les jours de 15h à 18h', 'Item de test canceled', 'canceled', null);

--Insert demo's offers
INSERT INTO pae.offers
    (date_offer, id_item)
VALUES ('21-03-2022', 1);

INSERT INTO pae.offers
    (date_offer, id_item)
VALUES ('25-03-2022', 2);

INSERT INTO pae.offers
    (date_offer, id_item)
VALUES ('25-03-2022', 3);

INSERT INTO pae.offers (date_offer, id_item)
VALUES (now(), 4);

INSERT  INTO pae.items (type,photo, description, availabilities, item_condition,id_offering_member) VALUES(1,'url','description','availabiliteis','hfhg',4);
-- SELECT it.id_item,
--        it.type,
--        it.description,
--        it.availabilities,
--        it.item_condition,
--        it.photo,
--        it.rating,
--        it.id_offering_member,
--        ty.type,
--        of.date_offer
-- FROM pae.items it,
--      pae.types ty,
--      pae.offers of
-- WHERE it.type = ty.id_type
--   AND of.id_item = it.id_item
-- ORDER BY date_offer DESC,
--          it.type;

SELECT * FROM pae.items;

SELECT * FROM pae.types ;

INSERT  INTO pae.items (type,photo, description, availabilities, item_condition,id_offering_member)  VALUES(4, null, 'kjhk','ikjknjn','knknk',4) RETURNING type,photo,description,availabilities,item_condition,id_offering_member;


VALUES ('10-03-2022', 4);

INSERT INTO pae.offers (date_offer, id_item)
VALUES ('10-03-2022', 5);

UPDATE pae.members SET state='confirmed', isAdmin =false WHERE username='souli' RETURNING *;

SELECT * FROM pae.interests;

SELECT * FROM pae.items;

SELECT * FROM pae.members
