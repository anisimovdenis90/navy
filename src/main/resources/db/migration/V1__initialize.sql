CREATE TABLE `ports` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `capacity` INT NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `ships` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `port_id` BIGINT NULL,
    `status` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`port_id`) REFERENCES `ports`(`id`)
);

INSERT INTO `ports` (`name`, `capacity`)
VALUES
('Дальний', 5),
('Солнечный', 10),
('Восточный', 7);

INSERT INTO `ships` (`name`, `port_id`, `status`)
VALUES
('Проворный', 1, 'PORT'),
('Санта Мария', 1, 'PORT'),
('Туман', 1, 'PORT'),
('Буря', 1, 'PORT'),
('Сказочный', 1, 'PORT'),
('Грозный', 2, 'PORT'),
('Восток', 2, 'PORT'),
('Кудесник', 3, 'PORT'),
('Арктика', NULL, 'SEA'),
('Москва', 3, 'PORT');
