package com.oracle.casb;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.cert.*;

public class VerifyCert {

    public static void main(String[] args) {
        VerifyCert vc = new VerifyCert();
        try {
            vc.verify();
        } catch (CertificateException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void verify() throws CertificateException, NoSuchAlgorithmException {
        byte[] array = "some Random String".getBytes();
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509", new BouncyCastleProvider());
        InputStream is = new ByteArrayInputStream(array);
        CertPath certPath = certificateFactory.generateCertPath(is, "PKCS7"); // Throws Certificate Exception when a cert path cannot be generated
        CertPathValidator certPathValidator = CertPathValidator.getInstance("PKIX", new BouncyCastleProvider());
        /*PKIXParameters parameters = new PKIXParameters(KeyTool.getCacertsKeyStore());

        PKIXCertPathValidatorResult validatorResult = (PKIXCertPathValidatorResult) certPathValidator.validate(certPath, parameters);*/
    }
}
