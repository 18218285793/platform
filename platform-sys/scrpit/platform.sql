/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50627
Source Host           : 192.168.1.100:33066
Source Database       : platform

Target Server Type    : MYSQL
Target Server Version : 50627
File Encoding         : 65001

Date: 2016-04-19 19:13:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_platform_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_platform_config`;
CREATE TABLE `tb_platform_config` (
  `CONFIG_ID` varchar(36) NOT NULL,
  `COMPONENT_NAME` varchar(100) DEFAULT NULL,
  `MODEL_CODE` varchar(100) DEFAULT NULL,
  `CONFIG_NAME` varchar(50) DEFAULT NULL,
  `CONFIG_VALUE` text,
  PRIMARY KEY (`CONFIG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_platform_config
-- ----------------------------
INSERT INTO `tb_platform_config` VALUES ('71AAAD8A-9A89-434A-8F02-A59BE1E3B21C', 'sys', null, 'platformId', 'cn.com.platform');
INSERT INTO `tb_platform_config` VALUES ('71AAAD8A-9A89-434A-8F02-S59BE1E3B21C', 'sys', null, 'innerplatfromId', 'cn.com.platform');

-- ----------------------------
-- Table structure for tb_platform_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_platform_permission`;
CREATE TABLE `tb_platform_permission` (
  `PERMISSION_ID` varchar(36) NOT NULL,
  `PERMISSION_CODE` varchar(100) DEFAULT NULL,
  `PERMISSION_NAME` varchar(100) DEFAULT NULL,
  `TARGET_TYPE` decimal(2,0) DEFAULT NULL,
  `MEMO` text,
  `MODEL_NAME` varchar(100) DEFAULT NULL,
  `COMPONENT` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_platform_permission
-- ----------------------------
INSERT INTO `tb_platform_permission` VALUES ('71AAAD8A-9A89-434A-8F02-A59BE1E3B21B', 'test1', 'test1权限', null, null, 'test模块', 'test');
INSERT INTO `tb_platform_permission` VALUES ('B4E3EA3A-6AB9-4232-93A2-6D05B4BF4B37', 'test2', 'test2权限', null, null, 'test模块', 'test');

-- ----------------------------
-- Table structure for tb_platform_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_platform_role`;
CREATE TABLE `tb_platform_role` (
  `ROLE_ID` varchar(36) NOT NULL,
  `ROLE_NAME` varchar(100) DEFAULT NULL,
  `ROLE_DESCRIPTION` text,
  `STATUS` decimal(2,0) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_platform_role
-- ----------------------------
INSERT INTO `tb_platform_role` VALUES ('E5BA133C-0604-4D2E-B639-825EE630A390', 'test角色', '牛逼', null);

-- ----------------------------
-- Table structure for tb_platform_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_platform_user`;
CREATE TABLE `tb_platform_user` (
  `USER_ID` varchar(36) NOT NULL,
  `USER_NAME` varchar(100) DEFAULT NULL,
  `PASSWORD` varchar(60) DEFAULT NULL,
  `STATUS` decimal(2,0) DEFAULT NULL COMMENT '0：正常',
  `LAST_LOGIN_TIME` datetime DEFAULT NULL,
  `IS_INTERNAL` decimal(2,0) DEFAULT NULL COMMENT '0 : 内部用户，1 : 外部用户',
  `SALT` varchar(100) NOT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_platform_user
-- ----------------------------
INSERT INTO `tb_platform_user` VALUES ('02b1fad0-de58-4c70-972a-e45ad325bf1c', 'admin', 'df3378aa487da91c51532c44ec51ed2e', null, null, null, '3a20822dd3e402a40ae58ea0e88b4880');

-- ----------------------------
-- Table structure for tb_role_permission_ref
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_permission_ref`;
CREATE TABLE `tb_role_permission_ref` (
  `REF_ID` varchar(36) NOT NULL,
  `ROLE_ID` varchar(36) DEFAULT NULL,
  `PERMISSION_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`REF_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role_permission_ref
-- ----------------------------
INSERT INTO `tb_role_permission_ref` VALUES ('E8470004-12F4-4A01-AC56-14BC633CA090', 'E5BA133C-0604-4D2E-B639-825EE630A390', 'B4E3EA3A-6AB9-4232-93A2-6D05B4BF4B37');
INSERT INTO `tb_role_permission_ref` VALUES ('F613C468-A838-4D6A-BA9C-2E778C8DC959', 'E5BA133C-0604-4D2E-B639-825EE630A390', '71AAAD8A-9A89-434A-8F02-A59BE1E3B21B');

-- ----------------------------
-- Table structure for tb_user_role_ref
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role_ref`;
CREATE TABLE `tb_user_role_ref` (
  `REF_ID` varchar(36) NOT NULL,
  `USER_ID` varchar(36) DEFAULT NULL,
  `ROLE_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`REF_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_role_ref
-- ----------------------------
INSERT INTO `tb_user_role_ref` VALUES ('02b1fad0-de58-4c70-972a-e45ss325bf1c', '02b1fad0-de58-4c70-972a-e45ad325bf1c', 'E5BA133C-0604-4D2E-B639-825EE630A390');
