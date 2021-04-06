<<<<<<< HEAD
This project is developed based on the FISCO BCOS platform. 
# File directory description
--logs  
--ra                         (The user's key in the schnorr signature)  
--src  
----main  
------java/org/fisco/bcos    (Core code)  
------resource               (Experimental data)  
----test  
--trees                      (Generated merkle tree)  

The instructions for using FISCO BCOS are as follows

# Spring Boot Starter
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)
[![Build Status](https://travis-ci.org/FISCO-BCOS/spring-boot-starter.svg?branch=master)](https://travis-ci.org/FISCO-BCOS/spring-boot-starter)
[![CodeFactor](https://www.codefactor.io/repository/github/fisco-bcos/spring-boot-starter/badge)](https://www.codefactor.io/repository/github/fisco-bcos/spring-boot-starter)
---

The sample spring boot project is based on [Web3SDK](https://fisco-bcos-documentation.readthedocs.io/en/latest/docs/sdk/sdk.html), which provides the basic framework and basic test cases for blockchain application and helps developers to quickly develop applications based on the FISCO BCOS blockchain. **The version only supports** [FISCO BCOS 2.0+](https://fisco-bcos-documentation.readthedocs.io/en/latest/).

## Quickstart

### Precodition

Build FISCO BCOS blockchain, please check out [here](https://fisco-bcos-documentation.readthedocs.io/en/latest/docs/installation.html)。

### Download

```
$ git clone https://github.com/FISCO-BCOS/spring-boot-starter.git
```
#### Certificate Configuration
Copy the `ca.crt`, `sdk.crt`, and `sdk.key` files in the node's directory `nodes/${ip}/sdk` to the project's `src/main/resources` directory.(Before FISCO BCOS 2.1, the certificate files are `ca.crt`, `node.crt` and `node.key`)

### Settings

The `application.yml` of the spring boot project is shown below, and the commented content is modified according to the blockchain node configuration.
  
```yml
encrypt-type: # 0:standard, 1:guomi
 encrypt-type: 0 
 
group-channel-connections-config:
  all-channel-connections:
  - group-id: 1  # group ID
    connections-str:
                    - 127.0.0.1:20200  # node listen_ip:channel_listen_port
                    - 127.0.0.1:20201
  - group-id: 2  
    connections-str:
                    - 127.0.0.1:20202  # node listen_ip:channel_listen_port
                    - 127.0.0.1:20203
 
channel-service:
  group-id: 1 # The specified group to which the SDK connects
  agency-name: fisco # agency name

accounts:
  pem-file: 0xcdcce60801c0a2e6bb534322c32ae528b9dec8d2.pem # PEM format account file
  p12-file: 0x98333491efac02f8ce109b0c499074d47e7779a6.p12 # PKCS12 format account file
  password: 123456 # PKCS12 format account password
```

A detail description of the SDK configuration for the project, please checkout [ here](https://fisco-bcos-documentation.readthedocs.io/en/latest/docs/sdk/sdk.html#sdk)。

### Introduce java SDK
Introduce Java SDK in build.gradle
```
compile ('org.fisco-bcos.java-sdk:fisco-bcos-java-sdk:2.7.2')
```
[Configure SDK license] (https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/sdk/java_sdk/configuration.html#id5) 

An example of copying the SDK certificate to the java sdk is as follows (here it is assumed that the SDK certificate is located in the ~/fisco/nodes/127.0.0.1/sdk directory):
```
mkdir -p conf && cp -r ~/fisco/nodes/127.0.0.1/sdk/* conf
```

### Prepare smart contract

Both console and java-sdk-demo provide tools to generate java tool classes from solidity contracts.  
**Download console**
```
$ mkdir -p ~/fisco && cd ~/fisco
# Get the console
$ curl -#LO https://github.com/FISCO-BCOS/console/releases/download/v2.7.1/download_console.sh

# If the above command cannot be executed for a long time due to network problems, please try the following command：
$ curl -#LO https://gitee.com/FISCO-BCOS/console/releases/download/v2.7.1/download_console.sh

$ bash download_console.sh
$ cd ~/fisco/console
```
Then, put the Solidity smart contract you want to use into the ~/fisco/console/contracts/solidity directory  

Next, generate a java class that calls the smart contract  

```
# Use sol2java.sh to compile all contracts under contracts/solidity to generate bin, abi, and java tool classes.
# Current directory~/fisco/console
$ bash sol2java.sh org.com.fisco# The parameter "org.com.fisco" in the above command is the name of the package to which the generated java class belongs.
# ./sol2java.sh [packageName] [solidityFilePath] [javaCodeOutputDir]
```

Finally, put the compiled HelloWorld.java into the application. Note: The location in the application must be the same as the package name we set.

### Run

Compile and run test cases:

```
$ cd BBVSPS
$ ./gradlew build
$ ./gradlew test
```

When all test cases run successfully, it means that the blockchain is running normally,and the project is connected to the blockchain through the SDK. You can develop your blockchain application based on the project。

**Note: If you run the demo project in IntelliJ IDEA or Eclipse, please use gradle wrapper mode. In addition, please enable `Annotation Processors` in `Settings` for IntelliJ IDEA.**

### Use the Java SDK to deploy and call smart contracts
Take the use of java sdk to call the getBlockNumber interface of group 1 to obtain the latest block height of group 1, and deploy and call the HelloWorld contract to group 1 as an example. The corresponding sample code is as follows:  

```java
public class BcosSDKTest
{
    // Get the configuration file path
    public final String configFile = BcosSDKTest.class.getClassLoader().getResource("config-example.toml").getPath();
     public void testClient() throws ConfigException {
           // Initialize BcosSDK
        BcosSDK sdk =  BcosSDK.build(configFile);
        // Initialize client for group 1
        Client client = sdk.getClient(Integer.valueOf(1));
    
        // Get the block height of group 1
        BlockNumber blockNumber = client.getBlockNumber();

        // Deploy HelloWorld contract to group 1
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().getCryptoKeyPair();
        HelloWorld helloWorld = HelloWorld.deploy(client, cryptoKeyPair);

        // Call the get interface of the HelloWorld contract
        String getValue = helloWorld.get();
        
        // Call the set interface of the HelloWorld contract
        TransactionReceipt receipt = helloWorld.set("Hello, fisco");
     }
}
```

Deploy the java tool class of the contract just generated according to the deployment and calling example methods of the helloworld contract.

### Usage test
Store the experimental data as the txt format in the ~/BBVSPS/src/main/resource/business directory. The specific format refers to the table in the paper.  

Call the java test method. The main test method is deployed under the path ~/BBVSPS/src/test/java/org/fisco/bcos/cloud  

```java
public void poolFunctionTest() throws Exception {
         pool.initial();          //Initialize the transaction pool and experimental data
         pool.all_submit();       //Submit data to the trading pool
         long startTime0 = System.currentTimeMillis();
         pool.packgeToMerkle();   //Transaction pool data packaging
         long endTime0 = System.currentTimeMillis();
         System.out.println("交易打包运行时间:" + (endTime0 - startTime0) + "ms");
         merkletree tree = pool.merkletrees.get(0);
         long startTime = System.currentTimeMillis();
         pool.keyGeneration(tree);      //Production user key
         long endTime = System.currentTimeMillis();
         System.out.println("密钥分发运行时间:" + (endTime - startTime) + "ms");
         long startTime2 = System.currentTimeMillis();
         pool.groupSigh(tree);          //Aggregate signature
         long endTime2 = System.currentTimeMillis();
         pool.checkGroupSigh(tree);     //Verify signature
         System.out.println("签名运行时间:" + (endTime2 - startTime2) + "ms");


         String sigh = pool.sighs.get(tree.date);
         String contractAddress = "0x15cb63a29ccb6e3f25901d615c2196ddea590d1f";

         Proofsubmit ps = Proofsubmit.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
         if (ps != null) {
             TransactionReceipt receipt1 = ps.proofSubmit(tree.date,tree.root).send();

             System.out.println("receipt1="+receipt1.toString());
             TransactionReceipt receipt2 = ps.signSubmit(tree.date,sigh).send();
             System.out.println("receipt2="+receipt2.toString());
         }//Data packaging on the blockchain

     }
```

After execution, the user's key is stored in the ~/BBVSPS/ra directory, and the root of the generated merkle tree is stored in the ~/BBVSPS/trees directory, named and arranged in chronological order, corresponding to the block height.
[ra] (https://github.com/xiaozhe-szu/BBVSPS/blob/main/ra.png)

=======
# BBVSPS
>>>>>>> 6ef43a8165198e99adfd03596bf554bb05f60cd9
