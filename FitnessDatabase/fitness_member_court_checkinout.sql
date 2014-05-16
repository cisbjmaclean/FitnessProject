-- database should be called fitness

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

SET time_zone = "+00:00";

--

-- Database: `fitness`

--

--

-- Create schema 

--

CREATE DATABASE IF NOT EXISTS bjmac2_fitness;

USE bjmac2_fitness;

--

-- Table structure for table `court`

--

DROP TABLE `court`;

CREATE TABLE IF NOT EXISTS `court` (

 `court_number` int(2) NOT NULL COMMENT 'Court number ',

 `court_name` varchar(20) NOT NULL COMMENT 'Court name',

 PRIMARY KEY (`court_number`)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--

-- Table structure for table `court_bookings`

--

DROP TABLE `court_bookings`;

CREATE TABLE IF NOT EXISTS `court_bookings` (

 `court_number` int(2) NOT NULL COMMENT 'FK to court table',

 `booking_date` date NOT NULL COMMENT 'Booking date',

 `start_time` varchar(5) NOT NULL COMMENT 'Start time for booking',

 `member_id` int(6) NOT NULL COMMENT 'FK to member table',

 `member_id_opponent` int(11) NOT NULL COMMENT 'FK to member table',

 `notes` varchar(200) NOT NULL COMMENT 'Notes about reservation',

 `created_date` date NOT NULL COMMENT 'Date booking was made'

) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--

-- Table structure for table `court_times`

--

DROP TABLE `court_times`;# MySQL returned an empty result set (i.e. zero rows).

CREATE TABLE IF NOT EXISTS `court_times` (

 `start_time` varchar(5) NOT NULL COMMENT 'court booking start time',

 `end_time` varchar(5) NOT NULL COMMENT 'court booking end time'

) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--

-- Table structure for table `member`

--

DROP TABLE `member`;

CREATE TABLE IF NOT EXISTS `member` (

 `member_id` int(6) NOT NULL COMMENT 'Unique member id',

 `user_type` int(1) NOT NULL COMMENT '1=member 2=admin 3=manager',

 `last_name` varchar(20) NOT NULL COMMENT 'Last name',

 `first_name` varchar(20) NOT NULL COMMENT 'First name',

 `password` varchar(20) NOT NULL COMMENT 'Password',

 `email` varchar(30) NOT NULL COMMENT 'Email address',

 `phone_cell` varchar(10) NOT NULL COMMENT 'Cellphone number',

 `phone_home` varchar(10) NOT NULL COMMENT 'Home phone number',

 `phone_work` varchar(10) NOT NULL COMMENT 'Work phone number',

 `address` varchar(100) NOT NULL COMMENT 'Home/mailing address',

 `status` int(1) NOT NULL COMMENT '1=active 0=inactive',

 `membership_type` int(2) COMMENT '1=annual 2=6 months',

 `membership_date` datetime COMMENT 'Beginning of Membership',

 PRIMARY KEY (`member_id`)

) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='This table will hold all member 

information';

DELETE FROM `court` WHERE 1;

INSERT INTO `court` (`court_number`, `court_name`) VALUES ('1', ' Court 1');

INSERT INTO `court` (`court_number`, `court_name`) VALUES ('2', 'Court 2');

INSERT INTO `court` (`court_number`, `court_name`) VALUES ('3', 'Court 3');

INSERT INTO `court` (`court_number`, `court_name`) VALUES ('4', 'Court 4');

DELETE FROM `member` WHERE 1;

INSERT INTO `member` (`member_id`, `user_type`, `last_name`, `first_name`, 

`password`, `email`, `phone_cell`, `phone_home`, `phone_work`, `address`, `status`) VALUES 

('1', '2', 'MacLean', 'BJ', 'test', 'bjmaclean@hollandcollege.com', '', '', '', '', '1');

INSERT INTO `court_times`(`start_time`, `end_time`) VALUES ('08:00','08:40');

INSERT INTO `court_times`(`start_time`, `end_time`) VALUES ('08:40','09:20');

INSERT INTO `court_times`(`start_time`, `end_time`) VALUES ('09:20','10:00');

INSERT INTO `court_times`(`start_time`, `end_time`) VALUES ('10:00','10:40');

INSERT INTO `court_times`(`start_time`, `end_time`) VALUES ('10:40','11:20');

INSERT INTO `court_times`(`start_time`, `end_time`) VALUES ('11:20','12:00');

INSERT INTO `court_times`(`start_time`, `end_time`) VALUES ('12:00','12:40');

INSERT INTO `court_times`(`start_time`, `end_time`) VALUES ('12:40','13:20');

INSERT INTO `court_times`(`start_time`, `end_time`) VALUES ('13:20','14:00');

INSERT INTO `court_times`(`start_time`, `end_time`) VALUES ('14:00','14:40');

INSERT INTO `court_times`(`start_time`, `end_time`) VALUES ('14:40','15:20');

INSERT INTO `court_times`(`start_time`, `end_time`) VALUES ('15:20','16:00');

INSERT INTO `court_times`(`start_time`, `end_time`) VALUES ('16:00','16:40');

INSERT INTO `court_times`(`start_time`, `end_time`) VALUES ('16:40','17:20');

INSERT INTO `court_times`(`start_time`, `end_time`) VALUES ('17:20','18:00');

INSERT INTO `court_times`(`start_time`, `end_time`) VALUES ('18:00','18:40');

INSERT INTO `court_times`(`start_time`, `end_time`) VALUES ('18:40','19:20');

INSERT INTO `court_times`(`start_time`, `end_time`) VALUES ('19:20','20:00');

INSERT INTO `court_times`(`start_time`, `end_time`) VALUES ('20:00','20:40');

INSERT INTO `court_times`(`start_time`, `end_time`) VALUES ('20:40','21:20');



CREATE TABLE IF NOT EXISTS `check_in` (
  `check_in_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Check In ID',
  `membership_id` int(11) NOT NULL COMMENT 'Member ID',
  `check_in_time` datetime NOT NULL COMMENT 'Check In Time',
  `check_out_time` datetime DEFAULT NULL COMMENT 'Check Out Time',
  `towel_taken` tinyint(1) NOT NULL COMMENT 'Towel Taken',
  `towel_return` tinyint(1) DEFAULT NULL COMMENT 'Towel Returned',
  PRIMARY KEY (`check_in_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `check_in`
--
DELETE FROM `check_in` WHERE 1;

INSERT INTO `check_in` (`check_in_id`, `membership_id`, `check_in_time`, `check_out_time`, `towel_taken`, `towel_return`) VALUES
(1, 2, '2013-11-29 12:15:40', NULL, 1, NULL);
