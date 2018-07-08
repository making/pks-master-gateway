```
openssl pkcs12 -export \
        -name pks-master-gateway \
        -in $(pwd)/src/test/resources/kube.crt  \
        -inkey $(pwd)/src/test/resources/kube.key \
        -out /tmp/keystore.p12 \
        -password pass:foobar

keytool -importkeystore \
        -destkeystore /tmp/keystore.jks \
        -srckeystore /tmp/keystore.p12 \
        -deststoretype pkcs12 \
        -srcstoretype pkcs12 \
        -alias pks-master-gateway \
        -deststorepass changeme \
        -destkeypass changeme \
        -srcstorepass foobar \
        -srckeypass foobar \
        -noprompt
```
