-- Datos de prueba
INSERT INTO event (id, name, date_time, description)
SELECT 1,
       'Concierto Soda Stereo',
       '2025-09-10 18:00:00',
       'Un concierto de otro mundo' WHERE NOT EXISTS (SELECT 1 FROM event WHERE id = 1);

INSERT INTO concert (id)
SELECT 1 WHERE NOT EXISTS (SELECT 1 FROM concert WHERE id = 1);

INSERT INTO ticket (type, price, total_stock, available_stock, event_id)
SELECT 'Campo',
       15000,
       50000,
       50000,
       1 WHERE NOT EXISTS (SELECT 1 FROM ticket WHERE event_id = 1 AND type = 'Campo');

INSERT INTO ticket (type, price, total_stock, available_stock, event_id)
SELECT 'Platea',
       35000,
       5000,
       5000,
       1 WHERE NOT EXISTS (SELECT 1 FROM ticket WHERE event_id = 1 AND type = 'Platea');

INSERT INTO ticket (type, price, total_stock, available_stock, event_id)
SELECT 'Palco',
       70000,
       1000,
       1000,
       1 WHERE NOT EXISTS (SELECT 1 FROM ticket WHERE event_id = 1 AND type = 'Palco');

INSERT INTO event (id, name, date_time, description)
SELECT 2,
       'Concierto Artic Monkeys',
       '2025-09-11 18:00:00',
       'Un concierto muy bueno' WHERE NOT EXISTS (SELECT 1 FROM event WHERE id = 2);

INSERT INTO concert (id)
SELECT 2 WHERE NOT EXISTS (SELECT 1 FROM concert WHERE id = 2);

INSERT INTO ticket (type, price, total_stock, available_stock, event_id)
SELECT 'Campo',
       40000,
       45000,
       45000,
       2 WHERE NOT EXISTS (SELECT 1 FROM ticket WHERE event_id = 2 AND type = 'Campo');

INSERT INTO ticket (type, price, total_stock, available_stock, event_id)
SELECT 'Platea',
       90000,
       3000,
       3000,
       2 WHERE NOT EXISTS (SELECT 1 FROM ticket WHERE event_id = 2 AND type = 'Platea');

INSERT INTO event (id, name, date_time, description)
SELECT 3,
       'Conferencia de AWS',
       '2025-09-12 18:00:00',
       'Una conferencia muy interesante' WHERE NOT EXISTS (SELECT 1 FROM event WHERE id = 3);

INSERT INTO talk (id)
SELECT 3 WHERE NOT EXISTS (SELECT 1 FROM talk WHERE id = 3);

INSERT INTO ticket (type, price, total_stock, available_stock, event_id)
SELECT 'General',
       2000,
       100,
       100,
       3 WHERE NOT EXISTS (SELECT 1 FROM ticket WHERE event_id = 3 AND type = 'General');

INSERT INTO event (id, name, date_time, description)
SELECT 4,
       'Charla con Martin Fowler',
       '2025-09-13 18:00:00',
       'Una charla muy interesante' WHERE NOT EXISTS (SELECT 1 FROM event WHERE id = 4);

INSERT INTO talk (id)
SELECT 4 WHERE NOT EXISTS (SELECT 1 FROM talk WHERE id = 4);

INSERT INTO ticket (type, price, total_stock, available_stock, event_id)
SELECT 'General',
       15000,
       20,
       20,
       4 WHERE NOT EXISTS (SELECT 1 FROM ticket WHERE event_id = 4 AND type = 'General');

INSERT INTO ticket (type, price, total_stock, available_stock, event_id)
SELECT 'Meet and Greet',
       35000,
       5,
       5,
       4 WHERE NOT EXISTS (SELECT 1 FROM ticket WHERE event_id = 4 AND type = 'Meet and Greet');

INSERT INTO event (id, name, date_time, description)
SELECT 5,
       'Obra de teatro Hamlet',
       '2025-09-14 18:00:00',
       'Una obra de teatro muy buena' WHERE NOT EXISTS (SELECT 1 FROM event WHERE id = 5);

INSERT INTO theater_play (id)
SELECT 5 WHERE NOT EXISTS (SELECT 1 FROM theater_play WHERE id = 5);
INSERT INTO ticket (type, price, total_stock, available_stock, event_id)
SELECT 'General',
       15000,
       50000,
       50000,
       5 WHERE NOT EXISTS (SELECT 1 FROM ticket WHERE event_id = 5 AND type = 'General');

INSERT INTO ticket (type, price, total_stock, available_stock, event_id)
SELECT 'VIP',
       35000,
       5000,
       5000,
       5 WHERE NOT EXISTS (SELECT 1 FROM ticket WHERE event_id = 5 AND type = 'VIP');

SELECT setval(pg_get_serial_sequence('event', 'id'), (SELECT MAX(id) FROM event));
SELECT setval(pg_get_serial_sequence('ticket', 'id'), (SELECT MAX(id) FROM ticket));
SELECT setval(pg_get_serial_sequence('concert', 'id'), (SELECT MAX(id) FROM concert));
SELECT setval(pg_get_serial_sequence('talk', 'id'), (SELECT MAX(id) FROM talk));
SELECT setval(pg_get_serial_sequence('theater_play', 'id'), (SELECT MAX(id) FROM theater_play));
