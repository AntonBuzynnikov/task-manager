CREATE TABLE tasks (
id SERIAL,
title varchar(255) NOT NULL,
description varchar(255) NOT NULL,
deadline date NOT NULL,
user_id bigint NOT NULL,
PRIMARY KEY (id)
);