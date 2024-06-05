SELECT
    a.attname as column_name,
    s.relname as sequence_name
FROM
    pg_class AS s
        JOIN pg_depend AS d ON d.objid = s.oid
        JOIN pg_class AS t ON d.refobjid = t.oid
        JOIN pg_attribute AS a ON a.attnum = d.refobjsubid AND a.attrelid = t.oid
WHERE
    s.relkind = 'S' AND t.relname = 'player';