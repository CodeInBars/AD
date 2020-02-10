drop database if exists taller;
create database taller;
use taller;

-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: taller
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `dni` varchar(9) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `telefono` varchar(9) NOT NULL,
  PRIMARY KEY (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES ('11111111T','Juan Sánchez','623587412'),('22222222H','Pedro Díaz','741852932'),('33333333P','María Gil','765897654');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coche`
--

DROP TABLE IF EXISTS `coche`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coche` (
  `matricula` varchar(7) NOT NULL,
  `marca` varchar(50) NOT NULL,
  `modelo` varchar(50) NOT NULL,
  `cliente` varchar(9) NOT NULL,
  PRIMARY KEY (`matricula`),
  KEY `cliente` (`cliente`),
  CONSTRAINT `coche_ibfk_1` FOREIGN KEY (`cliente`) REFERENCES `cliente` (`dni`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coche`
--

LOCK TABLES `coche` WRITE;
/*!40000 ALTER TABLE `coche` DISABLE KEYS */;
INSERT INTO `coche` VALUES ('1111DDD','ford','fiesta','22222222H'),('1234CBK','opel','corsa','11111111T');
/*!40000 ALTER TABLE `coche` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pieza`
--

DROP TABLE IF EXISTS `pieza`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pieza` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `precio` float NOT NULL,
  `stock` int(11) NOT NULL,
  `alta` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pieza`
--

LOCK TABLES `pieza` WRITE;
/*!40000 ALTER TABLE `pieza` DISABLE KEYS */;
/*!40000 ALTER TABLE `pieza` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `piezareparacion`
--

DROP TABLE IF EXISTS `piezareparacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `piezareparacion` (
  `pieza` int(11) NOT NULL,
  `reparacion` int(11) NOT NULL,
  `precio` float NOT NULL,
  `cantidad` int(11) NOT NULL,
  PRIMARY KEY (`pieza`,`reparacion`),
  KEY `reparacion` (`reparacion`),
  CONSTRAINT `piezareparacion_ibfk_1` FOREIGN KEY (`pieza`) REFERENCES `pieza` (`codigo`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `piezareparacion_ibfk_2` FOREIGN KEY (`reparacion`) REFERENCES `reparacion` (`codigo`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `piezareparacion`
--

LOCK TABLES `piezareparacion` WRITE;
/*!40000 ALTER TABLE `piezareparacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `piezareparacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reparacion`
--

DROP TABLE IF EXISTS `reparacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reparacion` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` int(11) NOT NULL,
  `matricula` varchar(7) NOT NULL,
  `fecha` date NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `tipo` (`tipo`),
  KEY `matricula` (`matricula`),
  CONSTRAINT `reparacion_ibfk_1` FOREIGN KEY (`tipo`) REFERENCES `tiporep` (`codigo`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `reparacion_ibfk_2` FOREIGN KEY (`matricula`) REFERENCES `coche` (`matricula`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reparacion`
--

LOCK TABLES `reparacion` WRITE;
/*!40000 ALTER TABLE `reparacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `reparacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiporep`
--

DROP TABLE IF EXISTS `tiporep`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tiporep` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiporep`
--

LOCK TABLES `tiporep` WRITE;
/*!40000 ALTER TABLE `tiporep` DISABLE KEYS */;
INSERT INTO `tiporep` VALUES (1,'Revisión'),(2,'Cambio Ruedas'),(3,'Cambio Aceite'),(4,'Reparación Motor');
/*!40000 ALTER TABLE `tiporep` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-10 10:13:58
