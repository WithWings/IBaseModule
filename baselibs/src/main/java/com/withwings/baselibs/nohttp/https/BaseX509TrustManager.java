package com.withwings.baselibs.nohttp.https;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * 证书验证器
 * 创建：WithWings 时间：2017/11/3.
 * Email:wangtong1175@sina.com
 */
public class BaseX509TrustManager implements X509TrustManager {

    private X509TrustManager mX509TrustManager;

    public BaseX509TrustManager() {
        try {
            // create a "default" JSSE X509TrustManager.
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream("trustedCerts"),
                    "passphrase".toCharArray());
            TrustManagerFactory tmf =
                    TrustManagerFactory.getInstance("SunX509", "SunJSSE");
            tmf.init(ks);
            TrustManager tms[] = tmf.getTrustManagers();
        /*
         * Iterate over the returned trustmanagers, look
         * for an instance of X509TrustManager.  If found,
         * use that as our "default" trust manager.
         */
            for (TrustManager tm : tms) {
                if (tm instanceof X509TrustManager) {
                    mX509TrustManager = (X509TrustManager) tm;
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证客户端证书
     * @param chain 证书链
     * @param authType 认证类型
     * @throws CertificateException 证书异常
     */
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        try {
            mX509TrustManager.checkClientTrusted(chain, authType);
        } catch (CertificateException e) {
            e.printStackTrace();
            // do any special handling here, or rethrow exception.
        }
    }

    /**
     * 验证服务端证书
     * @param chain 证书链
     * @param authType 认证类型
     * @throws CertificateException 证书异常
     */
    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        try {
            mX509TrustManager.checkServerTrusted(chain, authType);
        } catch (CertificateException e) {
            /*
             * 可能弹出一个证书提示是否信任该证书
             */
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return mX509TrustManager.getAcceptedIssuers();
    }
}