package com.withwings.baselibs.nohttp;

import com.yanzhenjie.nohttp.ssl.SSLUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * TODO
 * 创建：WithWings 时间：2017/11/3.
 * Email:wangtong1175@sina.com
 */
public class SSLSocketFactoryUtils {

    // 如果证书需要密码
    private static final String KEY_STORE_PASSWORD = "";

    private static synchronized SSLSocketFactory getDefaultSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, null);
            return SSLUtils.fixSSLLowerThanLollipop(sslContext.getSocketFactory());
        } catch (GeneralSecurityException e) {
            throw new AssertionError(); // The system has no TLS. Just give up.
        }
    }

    private static synchronized SSLSocketFactory getSSLSocketFactory() {
        try {
//            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new BaseX509TrustManager()}, null);
            return SSLUtils.fixSSLLowerThanLollipop(sslContext.getSocketFactory());
        } catch (GeneralSecurityException e) {
            throw new AssertionError(); // The system has no TLS. Just give up.
        }
    }

    private static synchronized SSLSocketFactory getSSLSocketFactoryByFile(String filePath) {
        // 取到证书的输入流
        try {
            InputStream is = new FileInputStream(filePath);
            CertificateFactory cf;
            cf = CertificateFactory.getInstance("X.509");
            Certificate ca;
            ca = cf.generateCertificate(is);

            // 创建 Keystore 包含我们的证书
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore;
            keyStore = KeyStore.getInstance(keyStoreType);
            if (keyStore != null) {
                keyStore.load(null);
            }
            if (keyStore != null) {
                keyStore.setCertificateEntry("anchor", ca);
            }

            // 创建一个 TrustManager 仅把 Keystore 中的证书 作为信任的锚点
            String algorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(algorithm);
            trustManagerFactory.init(keyStore);
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
            keyManagerFactory.init(keyStore, KEY_STORE_PASSWORD.toCharArray());
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
            // 用 TrustManager 初始化一个 SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagers, trustManagers, null);

            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getSSLSocketFactory();
    }

}