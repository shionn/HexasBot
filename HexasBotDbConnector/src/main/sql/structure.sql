-- phpMyAdmin SQL Dump
-- version 5.0.4deb2+deb11u1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le : ven. 08 août 2025 à 19:25
-- Version du serveur :  10.5.15-MariaDB-0+deb11u1
-- Version de PHP : 7.4.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Base de données : `hexas`
--
CREATE DATABASE IF NOT EXISTS `hexas` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `hexas`;

-- --------------------------------------------------------

--
-- Structure de la table `bundle`
--

CREATE TABLE IF NOT EXISTS `bundle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(1024) NOT NULL,
  `name` varchar(128) NOT NULL,
  `end_date` datetime NOT NULL,
  `notified` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `bundle_choice`
--

CREATE TABLE IF NOT EXISTS `bundle_choice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bundle` int(11) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `bundle_choice_to_bundle` (`bundle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `bundle_choice_game`
--

CREATE TABLE IF NOT EXISTS `bundle_choice_game` (
  `choice` int(11) NOT NULL,
  `game` varchar(256) NOT NULL,
  PRIMARY KEY (`choice`,`game`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `product`
--

CREATE TABLE IF NOT EXISTS `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `marque` varchar(32) NOT NULL,
  `meta_model` varchar(32) NOT NULL,
  `model` varchar(256) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `msrp` varchar(32) DEFAULT NULL,
  `url` varchar(1024) DEFAULT NULL,
  `last_price` decimal(10,2) DEFAULT NULL,
  `last_price_date` timestamp NULL DEFAULT NULL,
  `notify_price` decimal(10,2) DEFAULT NULL,
  `vendor` varchar(32) DEFAULT NULL,
  `notify` tinyint(1) NOT NULL DEFAULT 0,
  `notify_channel` varchar(32) DEFAULT NULL,
  `scanner` varchar(32) DEFAULT NULL,
  `include_pattern` varchar(128) DEFAULT NULL,
  `exclude_pattern` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`) USING HASH,
  KEY `url_2` (`url`(768))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `task`
--

CREATE TABLE IF NOT EXISTS `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent` int(11) DEFAULT NULL,
  `type` varchar(32) NOT NULL,
  `url` varchar(1024) NOT NULL,
  `product` int(11) NOT NULL,
  `include_pattern` varchar(32) DEFAULT NULL,
  `exclude_pattern` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product` (`product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `task_price`
--

CREATE TABLE IF NOT EXISTS `task_price` (
  `task` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `notified` tinyint(1) NOT NULL,
  PRIMARY KEY (`task`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `bundle_choice`
--
ALTER TABLE `bundle_choice`
  ADD CONSTRAINT `bundle_choice_to_bundle` FOREIGN KEY (`bundle`) REFERENCES `bundle` (`id`);

--
-- Contraintes pour la table `bundle_choice_game`
--
ALTER TABLE `bundle_choice_game`
  ADD CONSTRAINT `bundle_choice_game_to_bundle_choice` FOREIGN KEY (`choice`) REFERENCES `bundle_choice` (`id`);

--
-- Contraintes pour la table `task`
--
ALTER TABLE `task`
  ADD CONSTRAINT `task_to_product` FOREIGN KEY (`product`) REFERENCES `product` (`id`);
COMMIT;
