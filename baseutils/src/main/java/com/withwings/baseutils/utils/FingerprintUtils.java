package com.withwings.baseutils.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * 指纹识别工具类
 * <p>
 * 记得声明权限   <uses-permission android:name="android.permission.USE_FINGERPRINT"/>
 * <p>
 * 创建：WithWings 时间 2018/4/19
 * Email:wangtong1175@sina.com
 */
@SuppressWarnings("SameParameterValue")
public class FingerprintUtils {


    /**
     * 开启用户指纹识别
     *
     * @param context 上下文
     */
    @SuppressLint("MissingPermission")
    @TargetApi(Build.VERSION_CODES.M) // 因为 getFingerprintManager 当 SDK 小于23时会返回 null 所以可以当做支持
    public static void startFingerprint(final Context context, final OnFingerprintListener onFingerprintListener) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "记得要注册 USE_FINGERPRINT 权限哦！", Toast.LENGTH_SHORT).show();
            return;
        }
        FingerprintManager fingerprintManager = getFingerprintManager(context);
        if (fingerprintManager == null) { // 不支持指纹识别
            onFingerprintListener.onNonsupportFingerprint();
        } else { // 支持指纹识别
            if (fingerprintManager.hasEnrolledFingerprints()) { // 已经录入指纹
                fingerprintManager.authenticate(new FingerprintManager.CryptoObject(initCipher("Fingerprint")), new CancellationSignal(), 0, new FingerprintManager.AuthenticationCallback() {

                    // 错误次数过多
                    @Override
                    public void onAuthenticationError(int errorCode, CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        onFingerprintListener.onAuthenticationError(errorCode, errString);
                    }

                    @Override
                    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                        super.onAuthenticationHelp(helpCode, helpString);
                        onFingerprintListener.onAuthenticationHelp(helpCode, helpString);
                    }

                    // 识别成功
                    @Override
                    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        onFingerprintListener.onAuthenticationSucceeded(result);
                    }

                    // 识别失败
                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        onFingerprintListener.onAuthenticationFailed();
                    }
                }, null);
            } else {
                onFingerprintListener.onNotSetFingerprint();
            }
        }
    }

    /**
     * 获得指纹识别类
     *
     * @param context 上下文
     * @return 返回null代表手机不支持
     */
    @SuppressLint("MissingPermission")
    private static FingerprintManager getFingerprintManager(Context context) {
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
            if (fingerprintManager != null) {
                if (fingerprintManager.isHardwareDetected()) {
                    return fingerprintManager;
                }
            }
        }
        return null;
    }

    /**
     * 初始化 Cipher 对象加密
     *
     * @param keystoreAlias 秘钥名，可以随意
     * @return 加密器
     */
    @TargetApi(Build.VERSION_CODES.M)
    private static Cipher initCipher(@NonNull String keystoreAlias) {
        Cipher cipher;
        // 创建 Cipher
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("创建Cipher对象失败", e);
        }

        // 初始化 Cipher
        SecretKey secretKey = initKey(keystoreAlias);
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return cipher;
    }

    /**
     * 对称加密秘钥
     *
     * @param keystoreAlias 秘钥名
     * @return 秘钥
     */
    @TargetApi(Build.VERSION_CODES.M)
    private static SecretKey initKey(String keystoreAlias) {
        SecretKey secretKey = null;
        KeyStore keyStore = getKeyStore();
        try {
            keyStore.load(null);
            secretKey = (SecretKey) keyStore.getKey(keystoreAlias, null);
            if (secretKey != null) {
                return secretKey;
            }
            KeyGenerator keyGenerator = getKeyGenerator();
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(keystoreAlias, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_CBC).setUserAuthenticationRequired(true).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setInvalidatedByBiometricEnrollment(true);
            }
            keyGenerator.init(builder.build());
            secretKey = keyGenerator.generateKey();
            // 另一种密码获得方式
        } catch (CertificateException | NoSuchAlgorithmException | IOException | InvalidAlgorithmParameterException | KeyStoreException | UnrecoverableKeyException e) {
            e.printStackTrace();
        }

        return secretKey;
    }

    /**
     * KeyStore 是用于存储、获取密钥（Key）的容器
     *
     * @return KeyStore
     */
    private static KeyStore getKeyStore() {
        KeyStore keyStore;
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            throw new RuntimeException("初始化 KeyStore 失败。", e);
        }
        return keyStore;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static KeyGenerator getKeyGenerator() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("初始化 KeyGenerator 失败。", e);
        }
        return keyGenerator;
    }


    public interface OnFingerprintListener {

        /**
         * 错误次数哦超限
         *
         * @param errorCode 错误码
         * @param errString 错误信息
         */
        void onAuthenticationError(int errorCode, CharSequence errString);


        void onAuthenticationHelp(int helpCode, CharSequence helpString);

        /**
         * 指纹识别成功
         *
         * @param result 识别成功新
         */
        void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result);

        /**
         * 指纹识别失败
         */
        void onAuthenticationFailed();

        /**
         * 用户未录入指纹
         */
        void onNotSetFingerprint();

        /**
         * 手机不支持指纹识别
         */
        void onNonsupportFingerprint();
    }

}
