CREATE TABLE `ftc_bank_cards` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `bank_code` varchar(10) DEFAULT NULL COMMENT '银行编码',
  `bank_name` varchar(30) DEFAULT NULL COMMENT '银行名称',
  `is_enable` varchar(10) DEFAULT NULL COMMENT '是否可用',
  `single_payment_limit` varchar(20) DEFAULT NULL COMMENT '单笔限额【元】',
  `day_payment_limit` varchar(20) DEFAULT NULL COMMENT '日累计限额【元】',
  `month_payment_limit` varchar(20) DEFAULT NULL COMMENT '月累计限额【元】',
  `bank_logo` varchar(155) DEFAULT NULL COMMENT '银行logo',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;



CREATE TABLE `vc_merchant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_appkey` varchar(20) NOT NULL COMMENT '商户key',
  `merchant_appsecret` varchar(20) NOT NULL COMMENT '商户秘钥',
  `source` varchar(20) NOT NULL COMMENT '订单来源',
  `channel` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;