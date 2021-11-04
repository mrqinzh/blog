/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MariaDB
 Source Server Version : 100307
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MariaDB
 Target Server Version : 100307
 File Encoding         : 65001

 Date: 03/11/2021 15:32:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL DEFAULT 0,
  `menu_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名',
  `component_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件名',
  `component_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件位置',
  `redirect` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `menu_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `cache` int(1) NOT NULL DEFAULT 0 COMMENT '是否缓存 0：缓存 1：不缓存',
  `hidden` int(1) NOT NULL DEFAULT 1 COMMENT '是否隐藏 0：隐藏 1：不隐藏',
  `menu_sort` int(5) NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL,
  `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE CURRENT_TIMESTAMP,
  `status` int(2) NOT NULL DEFAULT 0 COMMENT '0：未删 1：已删',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (3, 0, '权限管理', 'Authority', 'Layout', 'noRedirect', 'el-icon-medal', '/admin/authority', 1, 1, 1, NULL, NULL, '2021-10-26 15:26:23', '2021-10-29 11:20:31', 0);
INSERT INTO `sys_menu` VALUES (4, 3, '菜单管理', 'Menu', 'authority/menu/', NULL, 'el-icon-s-operation', '/admin/authority/menu', 1, 1, 3, NULL, NULL, '2021-10-26 14:58:28', '2021-10-29 13:30:38', 0);
INSERT INTO `sys_menu` VALUES (5, 3, '角色管理', 'Role', 'authority/role/', NULL, 'el-icon-user-solid', '/admin/authority/role', 1, 1, 2, NULL, NULL, '2021-10-26 14:58:29', '2021-10-29 13:30:47', 0);
INSERT INTO `sys_menu` VALUES (8, 0, '系统管理', 'System', 'Layout', NULL, 'el-icon-data-board', '/admin/system', 1, 1, 2, NULL, NULL, '2021-10-29 11:09:27', '2021-10-29 11:09:27', 0);
INSERT INTO `sys_menu` VALUES (9, 3, '用户管理', 'User', 'system/user/User', NULL, 'el-icon-s-custom', '/admin/authority/user', 0, 1, 1, NULL, NULL, '2021-10-29 11:15:52', '2021-10-29 11:17:15', 0);
INSERT INTO `sys_menu` VALUES (10, 8, '评论管理', 'Comment', 'system/comment/Comment', NULL, 'el-icon-chat-line-round', '/admin/system/comment', 1, 1, 2, NULL, NULL, '2021-10-29 11:24:08', '2021-10-29 14:05:37', 0);
INSERT INTO `sys_menu` VALUES (11, 8, '文章管理', 'Article', 'system/article/index', NULL, 'el-icon-reading', '/admin/system/article', 1, 1, 1, NULL, NULL, '2021-10-29 13:38:37', '2021-10-29 13:38:37', 0);
INSERT INTO `sys_menu` VALUES (12, 11, '文章列表', 'ArticleList', 'system/article/Article', NULL, 'el-icon-menu', '/admin/system/article/list', 1, 1, 1, NULL, NULL, '2021-10-29 13:40:33', '2021-10-29 15:41:36', 0);
INSERT INTO `sys_menu` VALUES (13, 8, '标签管理', 'Tag', 'system/tag/index', NULL, 'el-icon-collection-tag', '/admin/system/tag', 0, 1, 3, NULL, NULL, '2021-10-29 14:05:21', '2021-10-29 14:05:21', 0);
INSERT INTO `sys_menu` VALUES (14, 11, '写文章', 'ArticleAdd', 'system/article/add_copy', NULL, 'el-icon-edit', '/admin/system/article/add', 0, 1, 2, NULL, NULL, '2021-10-29 15:42:52', '2021-10-29 15:44:21', 0);
INSERT INTO `sys_menu` VALUES (15, 0, '图标库', 'Icons', 'Layout', '', 'el-icon-star-on', '/icon/index', 0, 1, 3, NULL, NULL, '2021-10-29 16:32:45', '2021-10-29 16:32:45', 1);
INSERT INTO `sys_menu` VALUES (17, 0, '邮件', 'Email', 'Layout', NULL, 'el-icon-s-promotion', '/email/index', 0, 1, 4, NULL, NULL, '2021-10-29 16:32:46', '2021-10-29 16:32:46', 1);
INSERT INTO `sys_menu` VALUES (18, 0, '个人中心', 'Account', 'Layout', NULL, 'el-icon-s-custom', '/account', 0, 0, 5, NULL, NULL, '2021-11-03 15:26:59', '2021-11-03 15:31:24', 0);
INSERT INTO `sys_menu` VALUES (19, 18, '个人页', 'Center', 'account/center/', NULL, 'el-icon-s-custom', '/account/center', 0, 1, 5, NULL, NULL, '2021-11-03 15:28:16', '2021-11-03 15:28:16', 0);

SET FOREIGN_KEY_CHECKS = 1;
