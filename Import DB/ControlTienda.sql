-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-01-2025 a las 19:27:41
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tienda`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrito`
--

CREATE TABLE `carrito` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `nombre` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cantidad` int(11) NOT NULL DEFAULT 1,
  `precio` decimal(10,2) NOT NULL,
  `imgUrl` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `id_consola` int(11) DEFAULT NULL,
  `id_juego` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `consolas`
--

CREATE TABLE `consolas` (
  `nombre` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `potenciaCPU` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `potenciaGPU` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `compania` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `precio` decimal(10,0) NOT NULL,
  `unidades` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `imgUrl` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `consolas`
--

INSERT INTO `consolas` (`nombre`, `potenciaCPU`, `potenciaGPU`, `compania`, `precio`, `unidades`, `id`, `imgUrl`) VALUES
('Xbox One', '1.75 GHz', '1.31 TFLOPS', 'Microsoft', 299, 9, 1, 'img/consolas/xbox_one.webp'),
('Xbox Series X', '3.8 GHz', '12 TFLOPS', 'Microsoft', 499, 6, 2, 'img/consolas/xbox_x.webp'),
('Xbox Series S', '3.6 GHz', '4 TFLOPS', 'Microsoft', 299, 15, 3, 'img/consolas/xbox_s.webp'),
('Nintendo Switch', '1.02 GHz', '0.39 TFLOPS', 'Nintendo', 299, 11, 4, 'img/consolas/switch.webp'),
('Nintendo Switch Lite', '1.02 GHz', '0.39 TFLOPS', 'Nintendo', 199, 19, 5, 'img/consolas/switch_lite.webp'),
('PS4', '1.6 GHz', '1.84 TFLOPS', 'Sony', 399, 11, 6, 'img/consolas/ps4.webp'),
('PS5 con CD', '3.5 GHz', '10.28 TFLOPS', 'Sony', 499, 10, 7, 'img/consolas/ps5_conCD.webp'),
('PS5 sin CD', '3.5 GHz', '10.28 TFLOPS', 'Sony', 399, 15, 8, 'img/consolas/ps5_sinCD.webp');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `juegos`
--

CREATE TABLE `juegos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(20) DEFAULT NULL,
  `compania` varchar(20) DEFAULT NULL,
  `genero` varchar(20) DEFAULT NULL,
  `puntuacion` int(11) DEFAULT NULL,
  `precio` decimal(10,2) DEFAULT NULL,
  `unidades` int(11) DEFAULT NULL,
  `imgUrl` varchar(50) DEFAULT NULL,
  `id_consola` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `juegos`
--

INSERT INTO `juegos` (`id`, `nombre`, `compania`, `genero`, `puntuacion`, `precio`, `unidades`, `imgUrl`, `id_consola`) VALUES
(1, 'Halo 5', '343 Industries', 'FPS', 84, 60.99, 5, 'img/juegos/xbox_one.jpeg', 1),
(3, 'Gears of War 4', 'The Coalition', 'Acción', 85, 40.99, 4, 'img/juegos/xbox_one.jpeg', 1),
(4, 'Sea of Thieves', 'Rare', 'Aventura', 75, 40.99, 6, 'img/juegos/xbox_one.jpeg', 1),
(5, 'Minecraft', 'Mojang', 'Sandbox', 93, 20.99, 12, 'img/juegos/xbox_one.jpeg', 1),
(9, 'State of Decay 2', 'Undead Labs', 'Survival', 69, 30.99, 9, 'img/juegos/xbox_one.jpeg', 1),
(11, 'Halo Infinite', '343 Industries', 'FPS', 87, 69.99, 7, 'img/juegos/xbox_x.jpeg', 2),
(12, 'Forza Horizon 5', 'Playground Games', 'Carreras', 92, 70.99, 12, 'img/juegos/xbox_x.jpeg', 2),
(15, 'Flight Simulator', 'Asobo Studio', 'Simulación', 91, 59.99, 5, 'img/juegos/xbox_x.jpeg', 2),
(16, 'Psychonauts 2', 'Double Fine', 'Plataformas', 88, 60.99, 9, 'img/juegos/xbox_x.jpeg', 2),
(17, 'Fable', 'Playground Games', 'RPG', NULL, 59.99, 6, 'img/juegos/xbox_x.jpeg', 2),
(22, 'Grounded', 'Obsidian Entertainme', 'Supervivencia', 82, 39.99, 13, 'img/juegos/xbox_s.jpeg', 3),
(24, 'Doom Eternal', 'id Software', 'FPS', 89, 39.99, 7, 'img/juegos/xbox_s.jpeg', 3),
(27, 'GTA V', 'Rockstar Games', 'Acción', 96, 29.99, 6, 'img/juegos/xbox_s.jpeg', 3),
(28, 'Cyberpunk 2077', 'CD Projekt Red', 'RPG', 86, 59.99, 9, 'img/juegos/xbox_s.jpeg', 3),
(30, 'Resident Evil Villag', 'Capcom', 'Terror', 84, 59.99, 7, 'img/juegos/xbox_s.jpeg', 3),
(31, 'The Legend of Zelda:', 'Nintendo', 'Aventura', 97, 59.99, 11, 'img/juegos/switch.jpeg', 4),
(32, 'Super Mario Odyssey', 'Nintendo', 'Plataformas', 97, 49.99, 12, 'img/juegos/switch.jpeg', 4),
(33, 'Mario Kart 8 Deluxe', 'Nintendo', 'Carreras', 92, 49.99, 14, 'img/juegos/switch.jpeg', 4),
(38, 'Luigi’s Mansion 3', 'Next Level Games', 'Aventura', 86, 59.99, 8, 'img/juegos/switch.jpeg', 4),
(40, 'Metroid Dread', 'MercurySteam', 'Acción', 88, 59.99, 11, 'img/juegos/switch.jpeg', 4),
(41, 'God of War', 'Santa Monica Studio', 'Acción', 94, 39.99, 12, 'img/juegos/ps4.jpeg', 6),
(42, 'The Last of Us Part ', 'Naughty Dog', 'Aventura', 93, 59.99, 8, 'img/juegos/ps4.jpeg', 6),
(43, 'Spider-Man', 'Insomniac Games', 'Acción', 87, 49.99, 9, 'img/juegos/ps4.jpeg', 6),
(44, 'Bloodborne', 'FromSoftware', 'RPG', 92, 39.99, 7, 'img/juegos/ps4.jpeg', 6),
(45, 'Uncharted 4', 'Naughty Dog', 'Aventura', 93, 39.99, 9, 'img/juegos/ps4.jpeg', 6),
(51, 'Demon’s Souls', 'Bluepoint Games', 'RPG', 92, 69.99, 8, 'img/juegos/ps5_conCD.jpeg', 7),
(52, 'Ratchet & Clank: Rif', 'Insomniac Games', 'Acción', 88, 69.99, 7, 'img/juegos/ps5_conCD.jpeg', 7),
(54, 'Spider-Man: Miles Mo', 'Insomniac Games', 'Acción', 85, 49.99, 10, 'img/juegos/ps5_conCD.jpeg', 7),
(55, 'Sackboy: A Big Adven', 'Sumo Digital', 'Plataformas', 79, 59.99, 12, 'img/juegos/ps5_conCD.jpeg', 7),
(57, 'Resident Evil Villag', 'Capcom', 'Terror', 84, 59.99, 5, 'img/juegos/ps5_conCD.jpeg', 7),
(61, 'Astro’s Playroom', 'Japan Studio', 'Plataformas', 83, 0.00, 20, 'img/juegos/ps5_sinCD.jpeg', 8),
(64, 'Hitman 3', 'IO Interactive', 'Acción', 85, 59.99, 8, 'img/juegos/ps5_sinCD.jpeg', 8),
(65, 'Little Nightmares II', 'Tarsier Studios', 'Terror', 83, 39.99, 12, 'img/juegos/ps5_sinCD.jpeg', 8),
(66, 'Control: Ultimate Ed', 'Remedy Entertainment', 'Acción', 85, 39.99, 10, 'img/juegos/ps5_sinCD.jpeg', 8),
(68, 'Elden Ring', 'FromSoftware', 'RPG', 95, 69.99, 9, 'img/juegos/ps5_sinCD.jpeg', 8),
(71, 'Luigi’s Mansion 3', 'Nintendo', 'Aventura', 86, 59.99, 7, 'img/juegos/swichtL.jpeg', 5),
(72, 'Splatoon 2', 'Nintendo', 'Shooter', 83, 49.99, 10, 'img/juegos/swichtL.jpeg', 5),
(73, 'Xenoblade Chronicles', 'Monolith Soft', 'RPG', 83, 59.99, 5, 'img/juegos/swichtL.jpeg', 5),
(74, 'Fire Emblem: Three H', 'Intelligent Systems', 'Estrategia', 89, 59.99, 6, 'img/juegos/swichtL.jpeg', 5),
(75, 'Hollow Knight', 'Team Cherry', 'Metroidvania', 90, 14.99, 8, 'img/juegos/swichtL.jpeg', 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `usuario` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `clave` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `usuario`, `clave`, `admin`) VALUES
(1, 'admin', '1234', 1),
(2, 'user', '1234', 0),
(7, 'prueba', '1234', NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `carrito`
--
ALTER TABLE `carrito`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_usuario` (`id_usuario`),
  ADD KEY `id_consola` (`id_consola`),
  ADD KEY `id_juego` (`id_juego`);

--
-- Indices de la tabla `consolas`
--
ALTER TABLE `consolas`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `juegos`
--
ALTER TABLE `juegos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_consola` (`id_consola`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `usuario` (`usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `carrito`
--
ALTER TABLE `carrito`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=83;

--
-- AUTO_INCREMENT de la tabla `consolas`
--
ALTER TABLE `consolas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `juegos`
--
ALTER TABLE `juegos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `carrito`
--
ALTER TABLE `carrito`
  ADD CONSTRAINT `carrito_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `carrito_ibfk_2` FOREIGN KEY (`id_consola`) REFERENCES `consolas` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `carrito_ibfk_3` FOREIGN KEY (`id_juego`) REFERENCES `juegos` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `juegos`
--
ALTER TABLE `juegos`
  ADD CONSTRAINT `fk_consola` FOREIGN KEY (`id_consola`) REFERENCES `consolas` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `juegos_ibfk_1` FOREIGN KEY (`id_consola`) REFERENCES `consolas` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
