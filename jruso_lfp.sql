-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 17, 2020 at 01:16 PM
-- Server version: 5.5.65-MariaDB
-- PHP Version: 7.3.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jruso_lfp`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`JoseLFP`@`%` PROCEDURE `buscarEquipos` (IN `vnombre` VARCHAR(60))  NO SQL
BEGIN
SELECT Clubs.id_club, Clubs.nombre, Clubs.fecha_creacion, Clubs.nom_estadio, Historico.temporada
FROM Historico JOIN Futbolistas ON Historico.id_futbolista=Futbolistas.id_futbolista
JOIN Clubs ON Historico.id_club=Clubs.id_club
WHERE Futbolistas.nombre=vnombre;
END$$

CREATE DEFINER=`JoseLFP`@`%` PROCEDURE `buscarJugadores` (IN `vnomclub` VARCHAR(60))  NO SQL
BEGIN
SELECT Futbolistas.id_futbolista, Futbolistas.nif, Futbolistas.nombre, Futbolistas.apellidos, Futbolistas.fecha_nacimiento, Futbolistas.fecha_nacimiento, Futbolistas.nacionalidad, Historico.temporada
FROM Historico JOIN Futbolistas ON Historico.id_futbolista=Futbolistas.id_futbolista
JOIN Clubs ON Historico.id_club=Clubs.id_club
WHERE Clubs.nombre=vnomclub;
END$$

CREATE DEFINER=`JoseLFP`@`%` PROCEDURE `deleteClub` (IN `vclub` INT, IN `vnombre` VARCHAR(60))  NO SQL
BEGIN 
DELETE FROM Clubs WHERE id_club = vclub and nombre = vnombre;
END$$

CREATE DEFINER=`JoseLFP`@`%` PROCEDURE `deleteFutbolista` (IN `vfutbolista` INT)  NO SQL
BEGIN 
DELETE FROM Futbolistas WHERE id_futbolista = vfutbolista;
END$$

CREATE DEFINER=`JoseLFP`@`%` PROCEDURE `deleteHistorico` (IN `vid_futbolista` INT, IN `vtemporada` INT)  NO SQL
BEGIN
DELETE FROM Historico WHERE id_futbolista = vid_futbolista AND temporada = vtemporada;
END$$

CREATE DEFINER=`JoseLFP`@`%` PROCEDURE `insertClub` (IN `vnombre` VARCHAR(60), IN `vfecha_creacion` INT, IN `vnom_estadio` VARCHAR(60))  NO SQL
BEGIN
INSERT INTO Clubs
(nombre, fecha_creacion, nom_estadio) 
VALUES
(vnombre, vfecha_creacion, vnom_estadio);
END$$

CREATE DEFINER=`JoseLFP`@`%` PROCEDURE `insertFutbolista` (IN `vnif` VARCHAR(60), IN `vnombre` VARCHAR(60), IN `vapellidos` VARCHAR(60), IN `vfecha_nacimiento` DATE, IN `vnacionalidad` VARCHAR(60))  NO SQL
BEGIN
INSERT INTO Futbolistas
(nif, nombre, apellidos, fecha_nacimiento, nacionalidad) 
VALUES
(vnif, vnombre, vapellidos, vfecha_nacimiento, vnacionalidad);
END$$

CREATE DEFINER=`JoseLFP`@`%` PROCEDURE `insertHistorico` (IN `vid_club` INT, IN `vid_futbolista` INT, IN `vtemporada` INT)  NO SQL
BEGIN
INSERT INTO Historico
(id_club, id_futbolista, temporada)
VALUES
(vid_club, vid_futbolista, vtemporada);
END$$

CREATE DEFINER=`JoseLFP`@`%` PROCEDURE `updateClub` (IN `vid_club` INT, IN `vnombre` VARCHAR(60), IN `vfecha_creacion` INT, IN `vnom_estadio` VARCHAR(60))  NO SQL
BEGIN
UPDATE Clubs SET nombre = vnombre, fecha_creacion = vfecha_creacion,
nom_estadio = vnom_estadio WHERE id_club = vid_club;
END$$

CREATE DEFINER=`JoseLFP`@`%` PROCEDURE `updateFutbolista` (IN `vid_futbolista` INT, IN `vnif` VARCHAR(60), IN `vnombre` VARCHAR(60), IN `vapellidos` VARCHAR(60), IN `vfecha_nacimiento` DATE, IN `vnacionalidad` VARCHAR(60))  NO SQL
BEGIN
UPDATE Futbolistas SET nif = vnif, nombre = vnombre,
apellidos = vapellidos, fecha_nacimiento = vfecha_nacimiento, nacionalidad = vnacionalidad WHERE id_futbolista = vid_futbolista;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `Clubs`
--

CREATE TABLE `Clubs` (
  `id_club` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `fecha_creacion` int(11) DEFAULT NULL,
  `nom_estadio` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Clubs`
--

INSERT INTO `Clubs` (`id_club`, `nombre`, `fecha_creacion`, `nom_estadio`) VALUES
(1, 'Real Betis Balompié', 1907, 'Benito Villamarin'),
(2, 'Sevilla F.C.', 1905, 'Sánchez Pizjuan'),
(3, 'F.C. Barcelona', 1899, 'Camp Nou'),
(4, 'Real Madrid C.F.', 1902, 'Santiago Bernabeu'),
(5, 'Valencia C.F.', 1915, 'Mestalla'),
(6, 'RCD Espanyol', 1909, 'RCDE Stadium');

-- --------------------------------------------------------

--
-- Table structure for table `Futbolistas`
--

CREATE TABLE `Futbolistas` (
  `id_futbolista` int(11) NOT NULL,
  `nif` varchar(9) NOT NULL,
  `nombre` varchar(60) DEFAULT NULL,
  `apellidos` varchar(120) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `nacionalidad` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Futbolistas`
--

INSERT INTO `Futbolistas` (`id_futbolista`, `nif`, `nombre`, `apellidos`, `fecha_nacimiento`, `nacionalidad`) VALUES
(1, '22849029R', 'Joaquín', 'Sanchez', '1981-07-21', 'Español'),
(2, '41656616M', 'Diego', 'Carlos', '1993-03-15', 'Brasileño'),
(3, '31664121K', 'Karim', 'Benzema', '1987-12-19', 'Frances'),
(5, '37901534H', 'Lionel', 'Messi', '1987-07-24', 'Argentino'),
(7, '42144166R', 'Ferran', 'Torres', '1986-02-08', 'Español'),
(10, '29779499L', 'Jose Ángel', 'Cote', '2020-05-09', 'Español'),
(11, '31013644F', 'Raul', 'De Tomas', '1987-03-08', 'Español'),
(13, '27872162A', 'Pedro', 'León', '1998-03-01', 'Español');

-- --------------------------------------------------------

--
-- Table structure for table `Historico`
--

CREATE TABLE `Historico` (
  `id_club` int(11) NOT NULL,
  `id_futbolista` int(11) NOT NULL,
  `temporada` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Historico`
--

INSERT INTO `Historico` (`id_club`, `id_futbolista`, `temporada`) VALUES
(1, 1, 2020),
(2, 2, 2017),
(3, 5, 2019),
(4, 1, 1992),
(4, 3, 2005),
(5, 7, 2020);

--
-- Triggers `Historico`
--
DELIMITER $$
CREATE TRIGGER `registro` AFTER INSERT ON `Historico` FOR EACH ROW INSERT INTO RegistroHistorico
    (id_club, id_futbolista, temporada)
    VALUES
    (new.id_club, new.id_futbolista, new.temporada)
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `tempRepetida` BEFORE INSERT ON `Historico` FOR EACH ROW IF EXISTS
(SELECT temporada, id_futbolista FROM Historico WHERE new.temporada = temporada AND new.id_futbolista = id_futbolista)
THEN
SIGNAL SQLSTATE VALUE '45000'
SET MESSAGE_TEXT = '[table:Historico] - temporada repetida';
END IF
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `RegistroHistorico`
--

CREATE TABLE `RegistroHistorico` (
  `id_club` int(11) DEFAULT NULL,
  `id_futbolista` int(11) DEFAULT NULL,
  `temporada` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `RegistroHistorico`
--

INSERT INTO `RegistroHistorico` (`id_club`, `id_futbolista`, `temporada`) VALUES
(1, 1, 2020),
(2, 2, 2020),
(2, 3, 2020),
(4, 3, 2020),
(3, 4, 2020),
(1, 2, 2019),
(1, 3, 2019),
(1, 4, 2019),
(1, 2, 2019),
(2, 1, 2005),
(3, 5, 2020),
(1, 1, 2015),
(1, 1, 2003),
(1, 1, 1999),
(4, 3, 2017),
(2, 9, 2020),
(1, 5, 2019),
(5, 8, 2018),
(5, 1, 2018),
(2, 1, 2019),
(5, 7, 2020),
(6, 11, 2020);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Clubs`
--
ALTER TABLE `Clubs`
  ADD PRIMARY KEY (`id_club`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indexes for table `Futbolistas`
--
ALTER TABLE `Futbolistas`
  ADD PRIMARY KEY (`id_futbolista`),
  ADD UNIQUE KEY `nif` (`nif`);

--
-- Indexes for table `Historico`
--
ALTER TABLE `Historico`
  ADD PRIMARY KEY (`id_club`,`id_futbolista`,`temporada`),
  ADD KEY `id_futbolista` (`id_futbolista`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Clubs`
--
ALTER TABLE `Clubs`
  MODIFY `id_club` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `Futbolistas`
--
ALTER TABLE `Futbolistas`
  MODIFY `id_futbolista` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Historico`
--
ALTER TABLE `Historico`
  ADD CONSTRAINT `Historico_ibfk_1` FOREIGN KEY (`id_club`) REFERENCES `Clubs` (`id_club`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Historico_ibfk_2` FOREIGN KEY (`id_futbolista`) REFERENCES `Futbolistas` (`id_futbolista`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
