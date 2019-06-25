package com.zhangpk.test;

import com.zhangpk.util.QRCodeUtil;

/**
 * 测试一下生成的二维码怎么样
 *
 * @Auther: Zhang Peike
 * @Date: 2019/6/25 16:41
 */
public class QRCodeTest {

    public static void main(String[] args) throws Exception {
        QRCodeUtil.encode("https://uacoding.cn/",
                "C:\\Users\\Zhang Peike\\Desktop\\照片\\稿定设计导出-20190427-193618.png",
                System.getProperty("user.dir"), true);
    }
}
