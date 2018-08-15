-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 15, 2018 at 09:14 PM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 7.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `intrasharedb`
--

-- --------------------------------------------------------

--
-- Table structure for table `userdetails`
--

CREATE TABLE `userdetails` (
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `confirmpassword` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userdetails`
--

INSERT INTO `userdetails` (`firstname`, `lastname`, `username`, `password`, `confirmpassword`) VALUES
('Pranay', 'Patro', 'pp', 'pp', 'pp'),
('Pranay', 'Patro', 'pranaypatro', 'pranay', 'pranay'),
('anu', 'bhatia', 'anub', 'anu', 'anu'),
('pri', 'fgf', 'qw', '123', '123'),
('john', 'doe', 'jd', 'john123', 'john123'),
('Pranay', 'Partho', 'pranycoder', 'pc', 'pc'),
('Victor', 'FrankEnstein', 'vfrank', 'vvv', 'vvv'),
('Ajit', 'Pillai', 'AP', 'AP', 'AP'),
('fxdemo', 'fxdemo', 'fxdemo', 'fxdemo', 'fxdemo'),
('jfx', 'jfx', 'jfx', 'jfx', 'jfx'),
('', '', '', '', '');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
