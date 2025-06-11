INSERT INTO institutes (name, location) 
SELECT 'МГУ', 'Москва'
WHERE NOT EXISTS (
    SELECT 1 FROM institutes WHERE name = 'МГУ'
);

INSERT INTO institutes (name, location)
SELECT 'СПбГУ', 'Санкт-Петербург'
WHERE NOT EXISTS (
    SELECT 1 FROM institutes WHERE name = 'СПбГУ'
);