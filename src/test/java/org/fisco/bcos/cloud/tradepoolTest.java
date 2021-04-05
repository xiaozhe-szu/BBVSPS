package org.fisco.bcos.cloud;

import org.fisco.bcos.Application;
import org.fisco.bcos.contracts.Proofsubmit;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class tradepoolTest {

    private Credentials credentials;
    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    @Autowired
    private Web3j web3j;
    @Autowired
    private tradepool pool;

    @Before
    public void setUp() throws Exception {
        credentials = GenCredential.create();

    }

    @After
    public void tearDown() {
    }

    @Test
    public void DeployAsset() throws Exception {
        Proofsubmit ps = Proofsubmit.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
        if (ps != null) {
            System.out.println("HelloWorld address is: " + ps.getContractAddress());
        }
    }

    @Test
    public void caonima(){

        System.out.println("shabi");

    }
    @Test
    public void submit() throws Exception {
        String contractAddress = "0x15cb63a29ccb6e3f25901d615c2196ddea590d1f";
        Proofsubmit ps = Proofsubmit.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        if (ps != null) {
            TransactionReceipt receipt = ps.proofSubmit("2020-07-10-05:52:18","16cc45412d224b457d896c19a17b8c7d558b2c145dd5cf1cd9cd80ee4cdcd439").send();
            System.out.println("receipt="+receipt.toString());
        }
    }

    @Test
    public void haha() throws Exception {
         pool.initial();
         pool.all_submit();
         long startTime0 = System.currentTimeMillis();
         pool.packgeToMerkle();
         long endTime0 = System.currentTimeMillis();
         System.out.println("交易打包运行时间:" + (endTime0 - startTime0) + "ms");
         merkletree tree = pool.merkletrees.get(0);
         long startTime = System.currentTimeMillis();
         pool.keyGeneration(tree);
         long endTime = System.currentTimeMillis();
         System.out.println("密钥分发运行时间:" + (endTime - startTime) + "ms");
         long startTime2 = System.currentTimeMillis();
         pool.groupSigh(tree);
         long endTime2 = System.currentTimeMillis();
         pool.checkGroupSigh(tree);
         System.out.println("签名运行时间:" + (endTime2 - startTime2) + "ms");


         String sigh = pool.sighs.get(tree.date);
         String contractAddress = "0x15cb63a29ccb6e3f25901d615c2196ddea590d1f";

         Proofsubmit ps = Proofsubmit.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
         if (ps != null) {
             TransactionReceipt receipt1 = ps.proofSubmit(tree.date,tree.root).send();

             System.out.println("receipt1="+receipt1.toString());
             TransactionReceipt receipt2 = ps.signSubmit(tree.date,sigh).send();
             System.out.println("receipt2="+receipt2.toString());
         }

     }
     @Test
    public void hehe() throws Exception {
        for(int i=0;i<10;i++){
            haha();
            System.out.println("==========================================================");
        }

     }

}