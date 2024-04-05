/*
Navicat MySQL Data Transfer

Source Server         : 我得Mysql
Source Server Version : 50735
Source Host           : localhost:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50735
File Encoding         : 65001

Date: 2023-03-03 11:06:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(40) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `sex` tinyint(1) DEFAULT '1',
  `enabled` tinyint(1) DEFAULT '1',
  `password` varchar(41) DEFAULT NULL,
  `department` varchar(128) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL COMMENT '创建人',
  `updated_by` int(11) DEFAULT NULL COMMENT '更新人',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk` (`user_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'root', '管理员', '1', '1', '*E6CC90B878B948C35E92B003C792C46C58C4AF40', '管理员', null, 'root', '1', '1', '2021-01-01 08:00:00', '2023-01-11 11:41:40');
INSERT INTO `admin` VALUES ('2', 'test', '测试账户', '0', '1', '*E6CC90B878B948C35E92B003C792C46C58C4AF40', null, null, null, null, '1', '2023-01-10 22:14:16', '2023-01-11 13:00:57');

-- ----------------------------
-- Table structure for admin_priv
-- ----------------------------
DROP TABLE IF EXISTS `admin_priv`;
CREATE TABLE `admin_priv` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `admin_id` varchar(40) NOT NULL,
  `mod_id` varchar(64) NOT NULL,
  `priv` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `iUnique` (`admin_id`,`mod_id`,`priv`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_priv
-- ----------------------------
INSERT INTO `admin_priv` VALUES ('14', '2', 'admin', 'page');
INSERT INTO `admin_priv` VALUES ('2', '3', 'admin', 'add');
INSERT INTO `admin_priv` VALUES ('3', '3', 'admin', 'delete');

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department_name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(100) DEFAULT NULL COMMENT '联系电话',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_by` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `updated_by` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('1', '通信所3444', '张三', 'ddd', '这是一条备注', '2019-12-03 17:31:28', '2023-03-03 10:36:23', '1', '1', '0');
INSERT INTO `department` VALUES ('2', '通信所', '张三', '1532384234234', '这是一条备注', '2019-12-03 17:48:06', '2023-03-03 10:36:29', '1', '1', '0');

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(64) NOT NULL,
  `ip_address` varchar(46) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `os` varchar(128) DEFAULT NULL,
  `browser` varchar(128) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=436 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of login_log
-- ----------------------------
INSERT INTO `login_log` VALUES ('1', 'root', '127.0.0.1', '管理员', 'Windows 10', 'Chrome 10 108.0.0.0', '2023-01-11 15:45:16');
INSERT INTO `login_log` VALUES ('2', 'root', '127.0.0.1', '管理员', 'Windows 10', 'Chrome 10 V108.0.0.0', '2023-01-11 15:45:53');

//学生成绩管理系统新建表内容
-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
                         `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学生ID',
                         `studentName` varchar(32) DEFAULT NULL COMMENT '学生名称',
                         `user_code` varchar(40) NOT NULL COMMENT '学号',
                         `sex` tinyint(1) DEFAULT '1' COMMENT '性别(1为男，2为女)',
                         `parentName` varchar(32) DEFAULT NULL COMMENT '家长',
                         `phone` varchar(32) DEFAULT NULL COMMENT '家长手机号',
                         `classId` int(11) NOT NULL COMMENT '班级号',
                         `created_by` int(11) DEFAULT NULL COMMENT '创建人',
                         `updated_by` int(11) DEFAULT NULL COMMENT '更新人',
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `uk` (`user_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '张三', '202200101', '1', '张大三', '18899475847', '0203', '1', '1');
INSERT INTO `student` VALUES ('2', '李四', '202200124', '1', '李大四', '16552143698', '0204', '1', '1');
INSERT INTO `student` VALUES ('3', '王小美', '202200214', '2', '王大美', '14789632541', '0203', '1', '1');

-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes` (
                         `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                         `user_code` varchar(40) NOT NULL COMMENT '班级ID',
                         `className` varchar(32) DEFAULT NULL COMMENT '班级名称',
                         `teacherName` varchar(32) DEFAULT NULL COMMENT '班主任名称',
                         `teacherPhone` varchar(32) DEFAULT NULL COMMENT '班主任电话',
                         `position` varchar(32) DEFAULT NULL COMMENT '班级位置(如一楼105)',
                         `number` varchar(40) NOT NULL COMMENT '班级人数',
                         `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `uk` (`user_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classes
-- ----------------------------
INSERT INTO `classes` VALUES ('1', '0203', '二年级三班', '李老班', '15986548745', '一楼103', '35' ,'2021-01-01 08:00:00', '2023-01-11 11:41:40');
INSERT INTO `classes` VALUES ('2', '0204', '二年级四班', '李小班', '15632541254', '一楼104', '35' ,'2021-01-01 08:00:00', '2023-01-11 11:41:40');

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
                         `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                         `name` varchar(32) DEFAULT NULL COMMENT '姓名',
                         `user_code` varchar(40) NOT NULL COMMENT '学号',
                         `classId` varchar(40) NOT NULL COMMENT '班级ID',
                         `Chinese_grade` float(11) NOT NULL COMMENT '语文成绩',
                         `Math_grade` float(11) NOT NULL COMMENT '数学成绩',
                         `English_grade` float(11) NOT NULL COMMENT '英语成绩',
                         `recordTime` varchar(40) NOT NULL COMMENT '录入时间(如2024-3-10)',
                         `academicYear` varchar(40) NOT NULL COMMENT '学年',
                         `term` varchar(40) NOT NULL COMMENT '学期',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES ('1', '张三', '202200101', '203','89', '91', '78', '2021-3-4', '2021', '2021春季');
INSERT INTO `grade` VALUES ('2', '李四', '202200124', '204','98', '74', '89', '2021-3-4', '2021', '2021春季');
INSERT INTO `grade` VALUES ('3', '王小美', '202200214', '204','80', '100', '76', '2021-3-4', '2021', '2021春季');
