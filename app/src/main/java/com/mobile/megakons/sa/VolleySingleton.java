package com.mobile.megakons.sa;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class VolleySingleton {

        private static VolleySingleton volleySingleton;
        private RequestQueue requestQueue;
        private static Context mctx;


        private VolleySingleton(Context context) {
            this.mctx = context;
            this.requestQueue = getRequestQueue();

        }

        public RequestQueue getRequestQueue() {
            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(mctx.getApplicationContext());
            }
            return requestQueue;
        }

        public static synchronized VolleySingleton getInstance(Context context) {
            if (volleySingleton == null) {
                volleySingleton = new VolleySingleton(context);
            }
            return volleySingleton;
        }

        public <T> void addToRequestQue(Request<T> request) {
            requestQueue.add(request);

        }


        public HostnameVerifier getHostnameVerifier() {
            return new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    //return true;
                    // verify always returns true, which could cause  insecure network traffic due to     trusting TLS/SSL server certificates for wrong hostnames
                    HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                    return hv.verify("192.168.1.1", session);
                }
            };

        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public SSLSocketFactory getGlobalSSlFactory() {
            try {

//Use the certificate from raw folder...use below line
                ////InputStream inputStream = mctx.getResources().openRawResource(R.raw.test);
//Use the certificate as a String.. I've done the conversion here for String
                String certificate = "-----BEGIN CERTIFICATE-----\n" +
                        "MIIHlzCCBX+gAwIBAgIQC10KBrzMXIommIJgd9+rKTANBgkqhkiG9w0BAQsFADBc\n" +
                        "MQswCQYDVQQGEwJVUzEXMBUGA1UEChMORGlnaUNlcnQsIEluYy4xNDAyBgNVBAMT\n" +
                        "K1JhcGlkU1NMIEdsb2JhbCBUTFMgUlNBNDA5NiBTSEEyNTYgMjAyMiBDQTEwHhcN\n" +
                        "MjIxMDI5MDAwMDAwWhcNMjMwNzIxMjM1OTU5WjAZMRcwFQYDVQQDDA4qLm1lZ2Fr\n" +
                        "b25zLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMVXNEKZDhMK\n" +
                        "/gGdB73y3kUekfq4itxjzDjfBVO9qtGpRXUB5jPjKEEezo52mqOaEYscGidqbgT/\n" +
                        "Ixe33ttjn552FCbRE+j3tmTlIxWG6/BHb6K8EoXWaNYD1i9f4XEiT2XfQWtc7Xox\n" +
                        "W8uBfPYThqVSspA1BEO7JMyMowTS+EUEd+4qrtnkBRYG++Jsjt+RvxNs3Mx2Dh/f\n" +
                        "PZP0fRhCFOaz/MpnoxBdokSGR+zFiM59dO1GB0geWqS0Y4+m2T5wMxV3ECYbNgrb\n" +
                        "61Jzr5/MaaAq4pAOXt4CDbvau3zCEUTiEVgDAKk53inOTttsu7SCj0MCoXTsVyFY\n" +
                        "ytP88h1d8d0CAwEAAaOCA5YwggOSMB8GA1UdIwQYMBaAFPCchf2in32PyWi71dSJ\n" +
                        "TR2+05D/MB0GA1UdDgQWBBR/OpDsTZYCDzB8/PVT/yP+qd4avjAnBgNVHREEIDAe\n" +
                        "gg4qLm1lZ2Frb25zLmNvbYIMbWVnYWtvbnMuY29tMA4GA1UdDwEB/wQEAwIFoDAd\n" +
                        "BgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwgZ8GA1UdHwSBlzCBlDBIoEag\n" +
                        "RIZCaHR0cDovL2NybDMuZGlnaWNlcnQuY29tL1JhcGlkU1NMR2xvYmFsVExTUlNB\n" +
                        "NDA5NlNIQTI1NjIwMjJDQTEuY3JsMEigRqBEhkJodHRwOi8vY3JsNC5kaWdpY2Vy\n" +
                        "dC5jb20vUmFwaWRTU0xHbG9iYWxUTFNSU0E0MDk2U0hBMjU2MjAyMkNBMS5jcmww\n" +
                        "PgYDVR0gBDcwNTAzBgZngQwBAgEwKTAnBggrBgEFBQcCARYbaHR0cDovL3d3dy5k\n" +
                        "aWdpY2VydC5jb20vQ1BTMIGHBggrBgEFBQcBAQR7MHkwJAYIKwYBBQUHMAGGGGh0\n" +
                        "dHA6Ly9vY3NwLmRpZ2ljZXJ0LmNvbTBRBggrBgEFBQcwAoZFaHR0cDovL2NhY2Vy\n" +
                        "dHMuZGlnaWNlcnQuY29tL1JhcGlkU1NMR2xvYmFsVExTUlNBNDA5NlNIQTI1NjIw\n" +
                        "MjJDQTEuY3J0MAkGA1UdEwQCMAAwggF/BgorBgEEAdZ5AgQCBIIBbwSCAWsBaQB2\n" +
                        "AOg+0No+9QY1MudXKLyJa8kD08vREWvs62nhd31tBr1uAAABhCSYrhgAAAQDAEcw\n" +
                        "RQIhALYrjiQWuOGLYzCJeOOs8q1OPG870RCOKJCMLxchDfrvAiASqOqhc6UeqMrs\n" +
                        "X3922qfkfgXjHFH7dkxIy3ksjbv6agB2ALNzdwfhhFD4Y4bWBancEQlKeS2xZwwL\n" +
                        "h9zwAw55NqWaAAABhCSYrjIAAAQDAEcwRQIgYFGepmZDjET1Qo64q+N5D2RxdRi+\n" +
                        "iuOG8Pu4i8YP9W0CIQDgTQ1LzJoPUiyHtrOau80TVUly9zZ02YOJQRCBe6DAkgB3\n" +
                        "ALc++yTfnE26dfI5xbpY9Gxd/ELPep81xJ4dCYEl7bSZAAABhCSYrgEAAAQDAEgw\n" +
                        "RgIhANwjhhjtGWLIk02kvFqdafM1mGvAqXrkiHYi0Ug77ETXAiEAtUS/wrSYAywC\n" +
                        "kMrY51r6wgq1M9P3onhUW1UjCYKMsrUwDQYJKoZIhvcNAQELBQADggIBAGYLOI/4\n" +
                        "9Lvd4QARUqZHxzpBcnOLVD6Z2zvozKoaxluQK+MpFnY8qAy/hOMiCszRFilK11nA\n" +
                        "6ALYlh9bs6/LamZghV8Oq2SDsLvRfaFSTnXOG8+cj71X46XBovAZOcoWv7Ki53lZ\n" +
                        "bgoqmc+dVDD4/Wulx/NnnASk0y9Ie/zSbozWqysfbK2BC3VQNDqVNwWrvVGHEOV1\n" +
                        "EjzhAUaYs+x2fw4e6pD58lUOYUBgTMbvqX+f7Vr4gW2yTT2TtzqV2e22nc61Pbik\n" +
                        "KEZmL5PuAOKdZGBOsgAX5NHcN4FUPP041MZv6ls+hdFceRJxpqm8FiW3OPNxwwD3\n" +
                        "nl1p8NEBe174+PtnuzlE4AIbWEtf+TcVyxXVNYx1w4foCZB0vwxNbI9gQ4eqH3/H\n" +
                        "CO5agVCgt6sMUxqL+KxRDZtNBjK0IZ5u2pS4PIXBI8QrKMxsIx2WAoSRpavQxt97\n" +
                        "Nt0TpP00GbPvJRzvoWNrQVVww3Jgr7lHwk2ylD8caMO+Px7VGJOH8x/MQB8CRMvU\n" +
                        "peeFvGdfFuksHoEl0cSF0kVLL57Nc5r80ooDDXNPIHUYkASfHto/Da9EamGv01eK\n" +
                        "N9a34lsY0tpJsZoxUjaV0iDbCBG+Vq1xKUpLl7nTQE36VhYLoKA1e3WetmzHlzzZ\n" +
                        "dwDcbb5ngR1uxswtpRK3xxb1u2uo7GdBHTIO\n" +
                        "-----END CERTIFICATE-----\n";
                byte encodedCert[] = Base64.getDecoder().decode(certificate);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(encodedCert);

                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                Certificate ca = cf.generateCertificate(inputStream);
                inputStream.close();
                KeyStore keyStore = KeyStore.getInstance("BKS");
                keyStore.load(null, null);

                keyStore.setCertificateEntry("ca", ca);

                String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                tmf.init(keyStore);

                KeyManagerFactory kmf = KeyManagerFactory.getInstance(
                        KeyManagerFactory.getDefaultAlgorithm());
                kmf.init(keyStore, "xxxxxxx".toCharArray());

                final SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, tmf.getTrustManagers(), null);
                return sslContext.getSocketFactory();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnrecoverableKeyException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public static X509Certificate convertToX509Cert(String certificateString) throws CertificateException {
            X509Certificate certificate = null;
            CertificateFactory cf = null;
            try {
                if (certificateString != null && !certificateString.trim().isEmpty()) {
                    certificateString = certificateString.replace("-----BEGIN CERTIFICATE-----\n", "")
                            .replace("-----END CERTIFICATE-----", ""); // NEED FOR PEM FORMAT CERT STRING
                    byte[] certificateData = Base64.getDecoder().decode(certificateString);
                    cf = CertificateFactory.getInstance("X509");
                    certificate = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(certificateData));
                }
            } catch (CertificateException e) {
                throw new CertificateException(e);
            }
            return certificate;
        }
    }