-- phpMyAdmin SQL Dump
-- version 5.0.4deb2+deb11u1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le : jeu. 28 août 2025 à 06:19
-- Version du serveur :  10.5.15-MariaDB-0+deb11u1
-- Version de PHP : 7.4.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `hexas`
--

-- --------------------------------------------------------

--
-- Structure de la table `api_key`
--

CREATE TABLE `api_key` (
  `id` int(11) NOT NULL,
  `user` varchar(32) NOT NULL,
  `value` varchar(512) NOT NULL,
  `type` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `bundle`
--

CREATE TABLE `bundle` (
  `id` int(11) NOT NULL,
  `url` varchar(1024) NOT NULL,
  `name` varchar(128) NOT NULL,
  `end_date` datetime NOT NULL,
  `notified` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `bundle_choice`
--

CREATE TABLE `bundle_choice` (
  `id` int(11) NOT NULL,
  `bundle` int(11) NOT NULL,
  `price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `bundle_choice_game`
--

CREATE TABLE `bundle_choice_game` (
  `choice` int(11) NOT NULL,
  `game` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
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
  `exclude_pattern` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `task`
--

CREATE TABLE `task` (
  `id` int(11) NOT NULL,
  `parent` int(11) DEFAULT NULL,
  `type` varchar(32) NOT NULL,
  `url` varchar(1024) NOT NULL,
  `product` int(11) NOT NULL,
  `include_pattern` varchar(32) DEFAULT NULL,
  `exclude_pattern` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `task_price`
--

CREATE TABLE `task_price` (
  `task` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `notified` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `api_key`
--
ALTER TABLE `api_key`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `bundle`
--
ALTER TABLE `bundle`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`,`end_date`);

--
-- Index pour la table `bundle_choice`
--
ALTER TABLE `bundle_choice`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bundle_choice_to_bundle` (`bundle`);

--
-- Index pour la table `bundle_choice_game`
--
ALTER TABLE `bundle_choice_game`
  ADD PRIMARY KEY (`choice`,`game`);

--
-- Index pour la table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `url` (`url`) USING HASH,
  ADD KEY `url_2` (`url`(768));

--
-- Index pour la table `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`id`),
  ADD KEY `product` (`product`);

--
-- Index pour la table `task_price`
--
ALTER TABLE `task_price`
  ADD PRIMARY KEY (`task`,`date`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `api_key`
--
ALTER TABLE `api_key`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `bundle`
--
ALTER TABLE `bundle`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `bundle_choice`
--
ALTER TABLE `bundle_choice`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `task`
--
ALTER TABLE `task`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

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

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
