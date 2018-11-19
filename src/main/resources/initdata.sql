-- 菜单

INSERT IGNORE INTO `menu` (`id`, `active`, `crt_date`, `upd_date`, `expanded`, `icon`, `order_seq`, `parent_id`, `name`, `target`, `url`) VALUES
	(-1, b'1', '2018-10-25 10:35:26', '2018-10-25 10:38:19', b'1', 'fas fa-map', 0, NULL, 'Console Root', b'0', '/admin/root'),
	(0, b'1', '2018-10-25 10:35:26', '2018-11-02 17:33:55', b'1', 'fas fa-map', 0, NULL, 'Front Root', b'0', '/front/root'),
	(1, b'1', '2018-10-25 18:26:20', '2018-10-25 18:26:20', b'1', 'fas fa-home', 1, 0, 'Home', b'0', '/'),
	(2, b'1', '2018-10-25 18:26:20', '2018-10-25 18:26:20', b'1', 'fa fa-book', 2, 0, 'Post', b'0', '/post'),
	(3, b'1', '2018-10-25 18:26:20', '2018-10-25 18:26:20', b'1', 'fas fa-images', 3, 0, 'Album', b'0', '/album'),
	(4, b'1', '2018-10-25 18:26:20', '2018-10-25 18:26:20', b'1', 'fas fa-th-list', 1, -1, 'MenuManage', b'0', '/admin/menu'),
	(5, b'1', '2018-10-26 14:47:09', '2018-10-26 14:57:18', b'1', 'fas fa-clipboard-list', 2, -1, 'CategoryManage', b'0', '/admin/category'),
	(6, b'1', '2018-11-02 12:48:56', '2018-11-02 12:48:56', b'1', 'fa fa-sitemap', 1, 0, 'Site', b'0', '/site'),
	(7, b'1', '2018-11-09 18:39:24', '2018-11-09 18:39:24', b'1', 'fa fa-sitemap', 3, -1, 'SiteManage', b'0', '/admin/site'),
	(8, b'1', '2018-11-13 20:51:40', '2018-11-13 20:52:25', b'1', 'fa fa-picture-o', 4, -1, 'ImageManage', b'0', '/admin/image');

-- 分类
INSERT IGNORE INTO `category` (`id`, `active`, `crt_date`, `upd_date`, `expanded`, `icon`, `order_seq`, `parent_id`, `name`, `url`) VALUES
	(-1, b'1', '2018-11-02 12:35:47', '2018-11-02 12:40:45', b'1', 'fa fa-book', 0, NULL, 'Post', '/Post'),
	(0, b'1', '2018-11-02 12:35:47', '2018-11-02 12:40:45', b'1', 'fa fa-sitemap', 0, NULL, 'Site', '/Site'),
	(1, b'1', '2018-11-02 12:35:47', '2018-11-02 12:40:45', b'1', 'fa fa-clock-o', 0, -1, 'TimeLine', '/TimeLine'),
	(2, b'1', '2018-11-02 12:35:57', '2018-11-02 12:35:57', b'1', 'fa fa-desktop', 0, -1, 'Technology', '/Technology'),
	(3, b'1', '2018-11-02 15:32:53', '2018-11-02 15:32:53', b'1', 'fab fa-js-square', 1, 0, 'JavaScript', '/JavaScript'),
	(4, b'1', '2018-11-02 15:52:46', '2018-11-02 15:52:46', b'1', 'fab fa-chrome', 0, 3, 'Browser', '/Site/JavaScript/Browser'),
	(5, b'1', '2018-11-02 16:53:21', '2018-11-02 16:53:21', b'1', 'fas fa-server', 1, 3, 'Server-side', '/site/javascript/server-side');
