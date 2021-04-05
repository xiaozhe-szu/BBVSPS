package org.fisco.bcos.cloud.schnorr;

import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.List;

@Service("SchnorrSignature")
public class SchnorrSignature {
    // 路径前缀
    private static final String PERFIX_PATH = GetProjectPath.getPath() + "/ra/";
    // 存放公共参数
    private static final String PARAM_PATH = PERFIX_PATH + "initParams.properties";
    // 存放公钥的路径
    private static final String PUBLIC_KEY_PATH = PERFIX_PATH + "publicKey.properties";

    /**
     * @param blq:选择的q的bit长度
     * @Description: 系统初始化阶段，把初始化的密码公共参数存放到文件中去
     * @Date:下午9:28:20
     */
    public static void initPara(int blq) {
        File file = new File(PARAM_PATH);
        if (file.exists()) {
            System.out.println("已经存在初始化参数，为不影响已经颁发的证书，如果你强制要重新产生参数，请备份所有文件到其他路径下，并重新生产密钥对并重新签名");
        } else {
            System.out.println("系统初始化中，生产公共参数... ...");
            BigInteger one = new BigInteger("1");
            BigInteger two = new BigInteger("2");
            BigInteger q, qp, p, a, g;
            int certainty = 100;
            SecureRandom sr = new SecureRandom();
            // blq长度的q， q是p-1的素因子
            //生成BigInteger伪随机数，它可能是（概率不小于1 - 1/2certainty）一个具有指定 bitLength 的素数
            q = new BigInteger(blq, certainty, sr);
            qp = BigInteger.ONE;
            do { // 选择一个素数 p
                p = q.multiply(qp).multiply(two).add(one);
                if (p.isProbablePrime(certainty))
                    break;
                qp = qp.add(BigInteger.ONE);
            } while (true);

            while (true) {
                a = (two.add(new BigInteger(blq, 100, sr))).mod(p);// (2+x) mod p
                BigInteger ga = (p.subtract(BigInteger.ONE)).divide(q);// (p-1)/q
                g = a.modPow(ga, p); // a^ga mod p = 1
                if (g.compareTo(BigInteger.ONE) != 0) // g!=1
                    break;
            }
            // 存放公共参数
            List<String> transArryToLi = KeyPairOperate.transArryToLi(new String[]{"blq=" + blq, "q=" + q, "p=" + p, "g=" + g});
            KeyPairOperate.writePublicKeyToFile(PARAM_PATH, transArryToLi, false);
            System.out.println("...");
            System.out.println("初始化完成！");
        }
    }

    /**
     * @param user：传入用户的身份
     * @Description: 为用户生成公私钥对
     * @Return:void
     * @Date:上午9:32:18
     */
    public static void generateKeyForUser(String user) {
        File file = new File(PERFIX_PATH + user + ".properties");
        if (file.exists()) {
            System.out.println(user + "已经颁发了密钥，请备份所有文件到其他路径下，并重新签名");
        } else {
            System.out.println("密钥颁发中，请稍后");
            System.out.println("... ...");
            BigInteger sk, pk;// 私钥和公钥
            int blq = Integer.parseInt(KeyPairOperate.getDataFromFile(PARAM_PATH, "blq"));
            SecureRandom sr = new SecureRandom();
            // 随机数作为私钥
            sk = new BigInteger(blq, sr);

            // 私钥的话名字命名
            List<String> toLiSK = KeyPairOperate.transArryToLi(new String[]{"sk=" + sk});
            KeyPairOperate.writePublicKeyToFile(PERFIX_PATH + user + ".properties", toLiSK, false);

            BigInteger g = new BigInteger(KeyPairOperate.getDataFromFile(PARAM_PATH, "g"));
            BigInteger p = new BigInteger(KeyPairOperate.getDataFromFile(PARAM_PATH, "p"));
            // 公钥
            pk = g.modPow(sk, p);// g^w mod p  -- 注意这儿是正，所以下面验证的时候是用的负
            List<String> toLiPK = KeyPairOperate.transArryToLi(new String[]{user + "=" + pk});
            KeyPairOperate.writePublicKeyToFile(PUBLIC_KEY_PATH, toLiPK, true);
            System.out.println(user + " 密钥颁发完成");
        }
    }

    /**
     * @param sourcefilePath ： 待签名文件路径
     * @param user：传入用户的身份
     * @Description: 产生签名
     * @Date:下午10:41:37
     */
    public static void makeSign(String sourcefilePath, String user) {
        System.out.println(user + "的文件" + KeyPairOperate.getFileName(sourcefilePath) + " 签名开始");
        System.out.println("... ...");
        BigInteger q = new BigInteger(KeyPairOperate.getDataFromFile(PARAM_PATH, "q")); // 素数 q
        BigInteger p = new BigInteger(KeyPairOperate.getDataFromFile(PARAM_PATH, "p")); // 素数 p
        BigInteger g = new BigInteger(KeyPairOperate.getDataFromFile(PARAM_PATH, "g")); // q的原根 a

        // 私钥
        BigInteger sk = new BigInteger(KeyPairOperate.getDataFromFile(PERFIX_PATH + user + ".properties", "sk")); // 私钥

        SecureRandom sr = new SecureRandom();
        BigInteger r, x, e, y;
        r = new BigInteger(q.bitLength(), sr); // 随机数
        x = g.modPow(r, p); // g^r mod p
        // e=H(M||x)
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            //md5.update(Files.readAllBytes(Paths.get(sourcefilePath)));
            Path sp = Paths.get(sourcefilePath);
            md5.update(Files.readAllBytes(sp));
            md5.update(x.toString().getBytes());
            byte[] digest = md5.digest();
            // e 将BigInteger的符号大小表示法转换成一个BigInteger值
            e = new BigInteger(1, digest);
            // y s2 = r
            y = (r.subtract(sk.multiply(e))).mod(q);
            List<String> transArryToLi = KeyPairOperate.transArryToLi(new String[]{"e=" + e, "y=" + y});
            String fileName = PERFIX_PATH + user + "_sign_" + KeyPairOperate.getFileName(sourcefilePath) + ".properties";
            KeyPairOperate.writePublicKeyToFile(fileName, transArryToLi, false);
            System.out.println(user + "的文件" + KeyPairOperate.getFileName(sourcefilePath) + "签名成功 !");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * @param sourcefilePath   : 原文件路径
     * @param user：传入用户的身份
     * @Description: 验证签名
     * @Return:void
     * @Date:上午11:07:04
     */
    public static void checkSign(String sourcefilePath, String user) {
        System.out.println("验证签名");

        BigInteger p = new BigInteger(KeyPairOperate.getDataFromFile(PARAM_PATH, "p")); // 素数 p
        BigInteger g = new BigInteger(KeyPairOperate.getDataFromFile(PARAM_PATH, "g")); // q的原根 a

        BigInteger pk = new BigInteger(KeyPairOperate.getDataFromFile(PUBLIC_KEY_PATH, user));//  公钥

        String fileName = PERFIX_PATH + user + "_sign_" + KeyPairOperate.getFileName(sourcefilePath) + ".properties";

        BigInteger e = new BigInteger(KeyPairOperate.getDataFromFile(fileName, "e")); // e 签名信息1： 产生的签名信息
        BigInteger y = new BigInteger(KeyPairOperate.getDataFromFile(fileName, "y"));
        ; // y 签名信息2: 加密后的消息

        // 计算的 x'
        BigInteger x1 = g.modPow(y, p); // g^y mod p -- y
        BigInteger x2 = (pk.modPow(e, p)); // pk^e mod p
        BigInteger x = x1.multiply(x2).mod(p); // x1*x2 mod p = (g^y)*(pk^e)mod p

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(Files.readAllBytes(Paths.get(sourcefilePath)));
            md5.update(x.toString().getBytes());
            byte[] digest = md5.digest();
            BigInteger h = new BigInteger(1, digest);
            System.out.println("... ...");
            if (e.equals(h))
                System.out.println(user + "的文件" + KeyPairOperate.getFileName(sourcefilePath) + "验证通过 !");
            else
                System.out.println(user + "的文件" + KeyPairOperate.getFileName(sourcefilePath) + "验证失败 !");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}

