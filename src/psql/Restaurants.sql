DROP TABLE IF EXISTS "public"."Restaurants";
-- This script only contains the table creation statements and does not fully represent the table in the database. It's still missing: indices, triggers. Do not use it as a backup.

-- Table Definition
CREATE TABLE "public"."Restaurants" (
    "username" varchar,
    "name" varchar(100) NOT NULL,
    "hotline" varchar(100),
    "delivery_time" varchar(100),
    "delivery_fees" numeric,
    "delivery_hours" varchar(200),
    "description" text,
    CONSTRAINT "Restaurants_username_fkey" FOREIGN KEY ("username") REFERENCES "public"."Users"("username") ON DELETE CASCADE
);

INSERT INTO "public"."Restaurants" ("username", "name", "hotline", "delivery_time", "delivery_fees", "delivery_hours", "description") VALUES ('Ahmed', 'Macds', '19019', '60 mins', '15.5', '6:00 -> 11:00', 'PASSWORD'),
('TarekMorsi', 'Papa Johns', '16089', '70 mins', '20', '6:00 -> 12:00', 'SECRET'),
('Jailan', 'Dunkin donughts', '19019', '30 mins', '25', '8:00 -> 11:00', 'PASSWORD'),
('Hussein', 'KFC', '19000', '60 mins', '17.5', '6:00 -> 12:00', 'SECRET'),
('Reem', 'Khayrat el sham', '16016', '45 mins', '10', '12:00 -> 12:00', 'PASSWORD');
