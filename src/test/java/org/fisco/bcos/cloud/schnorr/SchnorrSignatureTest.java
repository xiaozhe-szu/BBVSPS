package org.fisco.bcos.cloud.schnorr;

import org.fisco.bcos.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SchnorrSignatureTest {
    String pathFile ="/home/ppm/IdeaProjects/spring-boot-starter-master/src/main/resources/schnorr/www.properties";


    @Test
    public void initPara() {
        SchnorrSignature.initPara(12);
    }

    @Test
    public void generateKeyForUser() {
        SchnorrSignature.generateKeyForUser("yangye");
        //SchnorrSignature.generateKeyForUser("xiaowang");
    }

    @Test
    public void makeSign() {
        SchnorrSignature.makeSign(pathFile,"xiaoming");
        SchnorrSignature.makeSign(pathFile,"xiaowang");
    }

    @Test
    public void checkSign() {
        SchnorrSignature.checkSign( pathFile ,"xiaoming");
        SchnorrSignature.checkSign( pathFile ,"xiaowang");
    }
}