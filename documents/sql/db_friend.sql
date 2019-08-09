# Date: 2019-08-08 22:45:27
# Generator: MySQL-Front 6.1  (Build 1.26)


#
# Structure for table "tb_friend"
#

DROP TABLE IF EXISTS `tb_friend`;
CREATE TABLE `tb_friend` (
  `userid` varchar(20) NOT NULL COMMENT '用户ID',
  `friendid` varchar(20) NOT NULL COMMENT '好友ID',
  `islike` varchar(1) DEFAULT NULL COMMENT '是否互相喜欢',
  PRIMARY KEY (`userid`,`friendid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "tb_friend"
#

INSERT INTO `tb_friend` VALUES ('1','100','1');

#
# Structure for table "tb_nofriend"
#

DROP TABLE IF EXISTS `tb_nofriend`;
CREATE TABLE `tb_nofriend` (
  `userid` varchar(20) NOT NULL DEFAULT '' COMMENT '用户ID',
  `friendid` varchar(20) NOT NULL DEFAULT '' COMMENT '好友ID',
  PRIMARY KEY (`userid`,`friendid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "tb_nofriend"
#