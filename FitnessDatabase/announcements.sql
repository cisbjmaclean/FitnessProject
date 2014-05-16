-- Table structure for table `announcements`
--

CREATE TABLE IF NOT EXISTS `announcements` (
  `announcement_id` int(11) NOT NULL AUTO_INCREMENT,
  `announcement_title` text NOT NULL,
  `announcement_description` text NOT NULL,
  PRIMARY KEY (`announcement_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `announcements`
--

INSERT INTO `announcements` (`announcement_id`, `announcement_title`, `announcement_description`) VALUES
(1, 'Test 1', 'Test 1 Text'),
(2, 'Test 2', 'Test 2 Text'),
(3, 'Test 3', 'Test 3 Text'),
(4, 'Test 4', 'Test 4 Text'),
(5, 'Please Enter A Title', 'Please Enter A Description Of The Announcement'),
(6, 'TEST5', 'Test1'),
(7, 'Please Enter A Title', 'Please Enter A Description Of The Announcement');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
