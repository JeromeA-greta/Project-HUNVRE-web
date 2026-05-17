-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le : dim. 17 mai 2026 à 12:23
-- Version du serveur : 8.0.30
-- Version de PHP : 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `hunvre`
--

-- --------------------------------------------------------

--
-- Structure de la table `carte`
--

CREATE TABLE `carte` (
  `id_carte` int UNSIGNED NOT NULL,
  `recto` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `couleur` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `valeur` int NOT NULL,
  `ref_visuel` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `carte`
--

INSERT INTO `carte` (`id_carte`, `recto`, `couleur`, `valeur`, `ref_visuel`) VALUES
(105, '1', 'trefle', 11, 1),
(106, '2', 'trefle', 2, 1),
(107, '3', 'trefle', 3, 1),
(108, '4', 'trefle', 4, 1),
(109, '5', 'trefle', 5, 1),
(110, '6', 'trefle', 6, 1),
(111, '7', 'trefle', 7, 1),
(112, '8', 'trefle', 8, 1),
(113, '9', 'trefle', 9, 1),
(114, '10', 'trefle', 10, 1),
(115, '11', 'trefle', 11, 1),
(116, '12', 'trefle', 11, 1),
(117, '13', 'trefle', 11, 1),
(118, '14', 'carreau', 11, 1),
(119, '15', 'carreau', 2, 1),
(120, '16', 'carreau', 3, 1),
(121, '17', 'carreau', 4, 1),
(122, '18', 'carreau', 5, 1),
(123, '19', 'carreau', 6, 1),
(124, '20', 'carreau', 7, 1),
(125, '21', 'carreau', 8, 1),
(126, '22', 'carreau', 9, 1),
(127, '23', 'carreau', 10, 1),
(128, '24', 'carreau', 11, 1),
(129, '25', 'carreau', 11, 1),
(130, '26', 'carreau', 11, 1),
(131, '27', 'coeur', 11, 1),
(132, '28', 'coeur', 2, 1),
(133, '29', 'coeur', 3, 1),
(134, '30', 'coeur', 4, 1),
(135, '31', 'coeur', 5, 1),
(136, '32', 'coeur', 6, 1),
(137, '33', 'coeur', 7, 1),
(138, '34', 'coeur', 8, 1),
(139, '35', 'coeur', 9, 1),
(140, '36', 'coeur', 10, 1),
(141, '37', 'coeur', 11, 1),
(142, '38', 'coeur', 11, 1),
(143, '39', 'coeur', 11, 1),
(144, '40', 'pique', 11, 1),
(145, '41', 'pique', 2, 1),
(146, '42', 'pique', 3, 1),
(147, '43', 'pique', 4, 1),
(148, '44', 'pique', 5, 1),
(149, '45', 'pique', 6, 1),
(150, '46', 'pique', 7, 1),
(151, '47', 'pique', 8, 1),
(152, '48', 'pique', 9, 1),
(153, '49', 'pique', 10, 1),
(154, '50', 'pique', 11, 1),
(155, '51', 'pique', 11, 1),
(156, '52', 'pique', 11, 1);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id_utilisateur` int UNSIGNED NOT NULL,
  `pseudo` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mdp` varchar(72) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mail` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `blinde` int DEFAULT NULL,
  `score` int UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id_utilisateur`, `pseudo`, `mdp`, `mail`, `role`, `blinde`, `score`) VALUES
(6, 'mich', '$2a$10$hS7Jl2fIZ2A25WHUVAdTzuFoLE36eH.4xJ3rgdHg.YGMlG/oHBi/q', 'mich@mich.com', 'joueur', NULL, NULL),
(7, 'pain', '$2a$10$0GKxox1fUhZ4qXSJg7PEGeadkGYN8wVPBe1HGfVJ0Y1HZ7SnZWNM6', 'jean@pain.com', 'joueur', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `visuel`
--

CREATE TABLE `visuel` (
  `id_visuel` int UNSIGNED NOT NULL,
  `verso` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `visuel`
--

INSERT INTO `visuel` (`id_visuel`, `verso`) VALUES
(1, '1');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `carte`
--
ALTER TABLE `carte`
  ADD PRIMARY KEY (`id_carte`),
  ADD KEY `ref_visuel` (`ref_visuel`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id_utilisateur`);

--
-- Index pour la table `visuel`
--
ALTER TABLE `visuel`
  ADD PRIMARY KEY (`id_visuel`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `carte`
--
ALTER TABLE `carte`
  MODIFY `id_carte` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=157;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id_utilisateur` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `visuel`
--
ALTER TABLE `visuel`
  MODIFY `id_visuel` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `carte`
--
ALTER TABLE `carte`
  ADD CONSTRAINT `carte_ibfk_1` FOREIGN KEY (`ref_visuel`) REFERENCES `visuel` (`id_visuel`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
