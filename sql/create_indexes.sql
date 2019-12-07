CREATE INDEX on_hand_test_nyc
ON part_nyc
(on_hand);

CREATE INDEX on_hand_test_sfo
ON part_sfo
(on_hand);

CREATE INDEX supplier_test
ON supplier
(supplier_id);