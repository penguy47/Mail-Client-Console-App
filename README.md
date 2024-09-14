# Run the server
1. test-mail-server-1.0.jar run with java version 8.
2. Run the server:
```sh
 & 'jre1.8.0_421\bin\java.exe' -jar test-mail-server-1.0.jar -s 2225 -p 3335 -m ./
```
# Compile and run client
1. This app is written this java 22
2. Compile and jar packaging
```sh
&'C:\Program Files\Java\jdk-22\bin\jar.exe' cfm MailClient.jar MANIFEST.MF -C bin  .
```
3. Run client
```sh
java -jar MailClient ./
```
