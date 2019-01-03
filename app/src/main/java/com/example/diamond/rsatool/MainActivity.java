package com.example.diamond.rsatool;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class MainActivity extends AppCompatActivity {

    final String KEY_ALIAS = "sample";
    final String KEY_PROVIDER = "AndroidKeyStore";
    final String ALGORITHM = "SHA-256withRSA";

    KeyStore keyStore;
    X509Certificate x509Certificate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareKeyStore();

        try {
            x509Certificate = (X509Certificate) keyStore.getCertificate(KEY_ALIAS);

        } catch (KeyStoreException e) {
            e.printStackTrace();
        }


    }


    void prepareKeyStore(){
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);

            createNewKey();

        } catch (KeyStoreException | CertificateException |
                NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }


    void createNewKey(){
        try {
            if (!keyStore.containsAlias(KEY_ALIAS)){
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
                        KeyProperties.KEY_ALGORITHM_RSA, KEY_PROVIDER);

                keyPairGenerator.initialize(
                        new KeyGenParameterSpec.Builder(
                                KEY_ALIAS,
                                KeyProperties.PURPOSE_DECRYPT)
                        .setDigests(KeyProperties.DIGEST_SHA256)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                        .build()
                );
                keyPairGenerator.generateKeyPair();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
