/*
SQLyog Ultimate v8.32 
MySQL - 5.5.40 : Database - graduate
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`graduate` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `graduate`;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `uid` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `birthday` date NOT NULL,
  `sex` varchar(20) NOT NULL,
  `telephone` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `status` varchar(10) NOT NULL,
  `code` varchar(50) NOT NULL,
  `root` varchar(10) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`uid`,`username`,`password`,`name`,`birthday`,`sex`,`telephone`,`email`,`status`,`code`,`root`) values (1,'zhangsan','123456789','张三','2019-05-03','男','12345679812','2671193566@qq.com','Y','null','N'),(2,'lisi','132465798','李四','2019-04-23','男','13246579825','2671193566@qq.com','N','6afb7fc3a31f40538f3c0e91b5974ee1','N'),(4,'wangwu','123456789','王五','2019-05-30','男','12345678999','2671193566@qq.com','Y','null','N'),(100,'root','root','管理员','2019-05-03','男','12345678911','2671193566@qq.com','Y','666','Y'),(102,'asdfsadfsa','132465798','陈志波','2016-05-12','男','13246579825','2671193566@qq.com','Y','null','N');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
