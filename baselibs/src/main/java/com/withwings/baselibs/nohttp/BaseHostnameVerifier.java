package com.withwings.baselibs.nohttp;

import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.security.auth.x500.X500Principal;

/**
 * 配置 HostnameVerifier
 * 创建：WithWings 时间：2017/11/3.
 * Email:wangtong1175@sina.com
 */
public class BaseHostnameVerifier implements HostnameVerifier {

    /**
     * //证书绑定的域名或者ip
     */
    private static final String CERTIFICATE_BINDING = "CN";

/*
    public boolean verify(String hostname, SSLSession session) {
        // 在这里核实 address.url().host(), sslSocket.getSession()
        // 也就是核实服务器地址和 sslSocket 版本号
        // 因为 ssl 证书是跟同一个第三方申请下来的  所以会有 Session 的概念
        // 如果核实通过，则返回 true ，这里因为只是工具类，所以直接当作核实通过好了
        return false;
    }
*/

    /**
     * 验证逻辑
     * @param hostname 主机名
     * @param session 版本
     * @return 是否通过校验
     */
    public boolean verify(String hostname, SSLSession session) {
        try {
            String peerHost = session.getPeerHost(); //服务器返回的主机名
            X509Certificate[] peerCertificates = (X509Certificate[]) session
                    .getPeerCertificates();
            for (X509Certificate certificate : peerCertificates) {
                X500Principal subjectX500Principal = certificate
                        .getSubjectX500Principal();
                String name = subjectX500Principal.getName();
                String[] split = name.split(",");
                for (String str : split) {
                    if (str.startsWith(CERTIFICATE_BINDING)) {
                        if (str.contains(hostname)
                                && str.contains(peerHost)) {
                            return true;
                        }
                    }
                }
            }
        } catch (SSLPeerUnverifiedException e) {
            e.printStackTrace();
        }
        return false;
    }



}
