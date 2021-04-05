package org.fisco.bcos.cloud;

import org.fisco.bcos.cloud.business.produce;
import org.fisco.bcos.cloud.business.purchase;
import org.fisco.bcos.cloud.business.supervision;
import org.fisco.bcos.cloud.business.transport;
import org.fisco.bcos.cloud.schnorr.GetProjectPath;
import org.fisco.bcos.cloud.schnorr.KeyPairOperate;
import org.fisco.bcos.cloud.schnorr.SchnorrSignature;
import org.fisco.bcos.contracts.Proofsubmit;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("tradepool")
public class tradepool {

    public ArrayList<produce.produce_order> pro_order_list;
    public ArrayList<purchase.purchase_order> pur_order_list;
    public ArrayList<transport.transport_order> tran_order_list;
    public ArrayList<supervision.tag_orderI> tagI_order_list;
    public ArrayList<supervision.tag_orderII> tagII_order_list;
    public ArrayList<transaction> pool_tran_list;
    public ArrayList<merkletree> merkletrees;
    public Map<String,String> sighs;
    public ArrayList<String> receiver_list;
    private static final String PERFIX_PATH = GetProjectPath.getPath() + "/trees/";
    private static final String RA_PATH = GetProjectPath.getPath() + "/ra/";
    private static final String PARAM_PATH = RA_PATH + "initParams.properties";

    private Credentials credentials;
    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");


    @Autowired
    ResourceLoader resourceLoader;
    public Web3j web3j;

    public static class transaction{

        private String types;
        Map<String,String> atrributes = new HashMap<String,String>();
        String hash;

        public transaction(String type,Map map) throws NoSuchAlgorithmException {
            this.types = type;
            this.atrributes.putAll(map);
            String sumvalue = "";
            for(String value :atrributes.values()){
                sumvalue += value;
            }
            MessageDigest m = MessageDigest.getInstance("SHA");
            m.update(sumvalue.getBytes());
            String result = new String(m.digest());
            this.hash = result;
        }
    }

    public void initial() throws IOException {
        pro_order_list = pro_read_order();
        pur_order_list = pur_read_order();
        tran_order_list = tran_read_order();
        tagI_order_list = read_tag_orderI();
        tagII_order_list = read_tag_orderII();
        pool_tran_list = new ArrayList<>();
        merkletrees = new ArrayList<>();
        receiver_list = new ArrayList<>();
        credentials = GenCredential.create();
        sighs = new HashMap<String,String>();
    }

    public ArrayList<produce.produce_order> pro_read_order() throws IOException {

        ArrayList<produce.produce_order> list = new ArrayList<>();
        Resource resource = resourceLoader.getResource("classpath:/business/produce.txt");

        InputStream is = resource.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String s = "";

        while ((s = br.readLine()) != null) {
            String[] order_data = s.split(" ");
            produce.produce_order order = new produce.produce_order(order_data[0],order_data[1],order_data[2],
                    order_data[3],order_data[4],order_data[5],order_data[6],order_data[7]);
            list.add(order);
        }
        return list;
    }

    public ArrayList<purchase.purchase_order> pur_read_order() throws IOException {

        ArrayList<purchase.purchase_order> list = new ArrayList<purchase.purchase_order>();
        Resource resource = resourceLoader.getResource("classpath:/business/purchase.txt");

        InputStream is = resource.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String s = "";

        while ((s = br.readLine()) != null) {
            String[] order_data = s.split(" ");
            purchase.purchase_order order = new purchase.purchase_order(order_data[0],order_data[1],order_data[2],
                    order_data[3],order_data[4],order_data[5],order_data[6],order_data[7]);
            list.add(order);
        }
        return list;
    }

    public ArrayList<transport.transport_order> tran_read_order() throws IOException {

        ArrayList<transport.transport_order> list = new ArrayList<transport.transport_order>();
        Resource resource = resourceLoader.getResource("classpath:/business/transport.txt");

        InputStream is = resource.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String s = "";

        while ((s = br.readLine()) != null) {
            String[] order_data = s.split(" ");
            transport.transport_order order = new transport.transport_order(order_data[0],order_data[1],order_data[2],
                    order_data[3],order_data[4],order_data[5]);
            list.add(order);
        }
        return list;
    }

    public ArrayList<supervision.tag_orderI> read_tag_orderI() throws IOException {

        ArrayList<supervision.tag_orderI> list = new ArrayList<>();
        Resource resource = resourceLoader.getResource("classpath:/business/supervisionI.txt");

        InputStream is = resource.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String s = "";

        while ((s = br.readLine()) != null) {
            String[] order_data  = s.split(" ");
            supervision.tag_orderI order = new supervision.tag_orderI(order_data[0],order_data[1],order_data[2],
                    order_data[3],order_data[4],order_data[5],order_data[6],order_data[7]);
            list.add(order);
        }
        return list;
    }

    public ArrayList<supervision.tag_orderII> read_tag_orderII() throws IOException {

        ArrayList<supervision.tag_orderII> list = new ArrayList<supervision.tag_orderII>();
        Resource resource = resourceLoader.getResource("classpath:/business/supervisionII.txt");

        InputStream is = resource.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String s = "";

        while ((s = br.readLine()) != null) {
            String[] order_data  = s.split(" ");
            supervision.tag_orderII order = new supervision.tag_orderII(order_data[0],order_data[1],order_data[2],
                    order_data[3],order_data[4],order_data[5],order_data[6],order_data[7]);
            list.add(order);
        }
        return list;
    }
    public void trans_submit(String type,Map map) throws NoSuchAlgorithmException, IOException {
        transaction trans = new transaction(type,map);
        this.pool_tran_list.add(trans);
        switch (type){
            case "0": //produce
                receiver_list.add((String) map.get("mau_num"));
                break;
            case "1": //purchase
                receiver_list.add((String)map.get("cdc_num"));
                break;
            case "2": //transport
                receiver_list.add((String)map.get("te_num"));
                break;
            case "3": //supervisionI
                receiver_list.add((String)map.get("icdc_num"));
                break;
            case "4": //superbisionII
                receiver_list.add((String)map.get("vi_num"));
                break;
            default:
                System.out.println("请输入正确的交易种类");
                break;

        }
    }

    public void all_submit() throws IOException, NoSuchAlgorithmException {
        int count = 0;
        int x = 30000;
        for(int i=0;i<pro_order_list.size();i++){
            Map<String,String > map = produceToMap(pro_order_list.get(i));
            trans_submit("0",map);
            count++;
            if (count>=x)
                return;
        }
        for(int i=0;i<pur_order_list.size();i++){
            Map<String,String> map = purchaseToMap(pur_order_list.get(i));
            trans_submit("1",map);
            count++;
            if (count>=x)
                return;
        }
        for(int i=0;i<tran_order_list.size();i++){
            Map<String,String> map = transportToMap(tran_order_list.get(i));
            trans_submit("2",map);
            count++;
            if (count>=x)
                return;
        }
        for(int i=0;i<read_tag_orderI().size();i++){
            Map<String,String> map = supIToMap(read_tag_orderI().get(i));
            trans_submit("3",map);
            count++;
            if (count>=x)
                return;
        }
        for(int i=0;i<read_tag_orderII().size();i++){
            Map<String,String> map = supIIToMap(read_tag_orderII().get(i));
            trans_submit("4",map);
            count++;
            if (count>=x)
                return;
        }
    }
    public static Map<String,String> produceToMap(produce.produce_order pro){
        Map<String,String> map = new HashMap<String,String>();
        map.put("mau_num",pro.mau_num);
        map.put("bitch",pro.bitch);
        map.put("vaccine_name",pro.vaccine_name);
        map.put("vaccine_num",pro.vaccine_num);
        map.put("materials",pro.materials);
        return map;
    }

    public static Map<String,String> purchaseToMap(purchase.purchase_order pur){
        Map<String,String> map = new HashMap<String,String>();
        map.put("mau_num",pur.mau_num);
        map.put("bitch",pur.bitch);
        map.put("vaccine_name",pur.vaccine_name);
        map.put("vaccine_num",pur.vaccine_num);
        map.put("cdc_num",pur.cdc_num);
        map.put("order_num",pur.order_num);
        return map;
    }

    public static Map<String,String> transportToMap(transport.transport_order trans){
        Map<String,String> map = new HashMap<String,String>();
        map.put("te_num",trans.te_num);
        map.put("mau_num",trans.mau_num);
        map.put("order_num",trans.order_num);
        map.put("transport_point",trans.transport_point);
        return map;
    }

    public static Map<String,String> supIToMap(supervision.tag_orderI supI){
        Map<String,String> map = new HashMap<String,String>();
        map.put("mau_num",supI.mau_num);
        map.put("bitch",supI.bitch);
        map.put("scdc_num",supI.scdc_num);
        map.put("icdc_num",supI.icdc_num);
        map.put("tag_front",supI.tag_front);
        map.put("tag_tail",supI.tag_tail);
        return map;
    }

    public static Map<String,String> supIIToMap(supervision.tag_orderII supII){
        Map<String,String> map = new HashMap<String,String>();
        map.put("cdc_num",supII.cdc_num);
        map.put("mau_num",supII.mau_num);
        map.put("bitch",supII.bitch);
        map.put("vi_num",supII.vi_num);
        map.put("tag_front",supII.tag_front);
        map.put("tag_tail",supII.tag_tail);
        return map;
    }

    public void packgeToMerkle(){
        inilpara();
        ArrayList<String> Txlist = new ArrayList<String >();
        for(int i=0;i<this.pool_tran_list.size();i++){
            Txlist.add(pool_tran_list.get(i).hash);
        }
        merkletree tree = new merkletree(Txlist,this.receiver_list);
        tree.merkle_tree();
        this.merkletrees.add(tree);
        this.pool_tran_list.clear();

        String fileName = PERFIX_PATH + tree.date + ".properties";
        File file = new File(fileName);
        List<String> toLiSK = KeyPairOperate.transArryToLi(new String[]{"root=" + tree.root});
        KeyPairOperate.writePublicKeyToFile(fileName, toLiSK, false);
        this.receiver_list.clear();
    }

    public void inilpara(){
        SchnorrSignature.initPara(256);
    }

    public void keyGeneration(merkletree tree){
        String filePath = RA_PATH+tree.date+"/";
        File filedir = new File(filePath);
        if (filedir.exists()) {
            System.out.println(tree.date + "已经颁发了密钥，请备份所有文件到其他路径下，并重新签名");
        }
        else {
            filedir.mkdir();
            System.out.println("密钥颁发中，请稍后");
            System.out.println("... ...");
            BigInteger grouppk =new BigInteger(String.valueOf(1));
            for (int i = 0; i < tree.users.size(); i++) {

                BigInteger sk, pk;// 私钥和公钥
                int blq = Integer.parseInt(KeyPairOperate.getDataFromFile(PARAM_PATH, "blq"));
                SecureRandom sr = new SecureRandom();
                // 随机数作为私钥
                sk = new BigInteger(blq, sr);

                // 私钥的话名字命名
                List<String> toLiSK = KeyPairOperate.transArryToLi(new String[]{"sk=" + sk});
                KeyPairOperate.writePublicKeyToFile(filePath + tree.users.get(i) + ".properties", toLiSK, false);

                BigInteger g = new BigInteger(KeyPairOperate.getDataFromFile(PARAM_PATH, "g"));
                BigInteger p = new BigInteger(KeyPairOperate.getDataFromFile(PARAM_PATH, "p"));
                // 公钥
                pk = g.modPow(sk, p);// g^w mod p
                List<String> toLiPK = KeyPairOperate.transArryToLi(new String[]{tree.users.get(i) + "=" + pk});
                KeyPairOperate.writePublicKeyToFile(filePath+"publicKey.properties", toLiPK, true);
                //System.out.println(tree.users.get(i) + " 密钥颁发完成");

                grouppk = (grouppk.multiply(pk)).mod(p);
            }
            List<String> toLiPK = KeyPairOperate.transArryToLi(new String[]{"grouppk" + "=" + grouppk});
            KeyPairOperate.writePublicKeyToFile(filePath+"publicKey.properties", toLiPK, true);
            System.out.println("组公钥聚合完成");
        }
    }

    public void groupSigh(merkletree tree){
        BigInteger q = new BigInteger(KeyPairOperate.getDataFromFile(PARAM_PATH, "q")); // 素数 q
        BigInteger p = new BigInteger(KeyPairOperate.getDataFromFile(PARAM_PATH, "p")); // 素数 p
        BigInteger g = new BigInteger(KeyPairOperate.getDataFromFile(PARAM_PATH, "g")); // q的原根 a

        ArrayList<BigInteger> ri_list = new ArrayList<BigInteger>();
        ArrayList<BigInteger> Ri_list = new ArrayList<BigInteger>();
        ArrayList<BigInteger> sk_list = new ArrayList<BigInteger>();
        BigInteger R = new BigInteger(String.valueOf(1));
        String filepath = RA_PATH + tree.date+"/";
        for(int i=0;i<tree.users.size();i++){
            BigInteger sk = new BigInteger(KeyPairOperate.getDataFromFile(filepath + tree.users.get(i) + ".properties", "sk")); // 私钥
            sk_list.add(sk);
            SecureRandom sr = new SecureRandom();
            BigInteger ri = new BigInteger(q.bitLength(),sr);  //随机数ri
            ri_list.add(ri);
            BigInteger Ri = g.modPow(ri,p);//Ri=g^ri mod p
            Ri_list.add(Ri);
            R = (R.multiply(Ri)).mod(p);
        }
        try {
            BigInteger S = new BigInteger(String.valueOf(0));
            for(int i=0;i<tree.users.size();i++){
                BigInteger c,si;
                String sourcePath = PERFIX_PATH + tree.date + ".properties";
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                Path sp = Paths.get(sourcePath);
                md5.update(Files.readAllBytes(sp));
                md5.update(R.toString().getBytes());

                byte[] digest = md5.digest();
                c = new BigInteger(1, digest);
                si = (ri_list.get(i).add(sk_list.get(i).multiply(c))).mod(q);
                S = (S.add(si)).mod(q);

            }
            List<String> transArryToLi = KeyPairOperate.transArryToLi(new String[]{"R=" + R, "S=" + S});
            this.sighs.put(tree.date,"R="+R+","+"S="+S);
            String fileName = filepath+"groupSigh"+ ".properties";
            KeyPairOperate.writePublicKeyToFile(fileName, transArryToLi, false);
            System.out.println(tree.date+"文件treeRoot聚合签名成功");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static void checkGroupSigh(merkletree tree){
        System.out.println("验证签名");
        BigInteger p = new BigInteger(KeyPairOperate.getDataFromFile(PARAM_PATH, "p")); // 素数 p
        BigInteger g = new BigInteger(KeyPairOperate.getDataFromFile(PARAM_PATH, "g")); // q的原根 a

        String filepath = RA_PATH+tree.date+"/";
        BigInteger X = new BigInteger(KeyPairOperate.getDataFromFile(filepath+"publicKey.properties","grouppk"));//  公钥
        String fileName = filepath+"groupSigh"+ ".properties";

        //读取签名信息
        BigInteger R = new BigInteger(KeyPairOperate.getDataFromFile(fileName, "R"));
        BigInteger S = new BigInteger(KeyPairOperate.getDataFromFile(fileName, "S"));

        //计算c
        try {
            BigInteger c;
            String sourcePath = PERFIX_PATH + tree.date + ".properties";
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            Path sp = Paths.get(sourcePath);
            md5.update(Files.readAllBytes(sp));
            md5.update(R.toString().getBytes());

            byte[] digest = md5.digest();
            c = new BigInteger(1, digest);
            BigInteger g_S = g.modPow(S,p); //g^S
            BigInteger RX_c = (R.multiply(X.modPow(c,p))).mod(p);  //RX^c
            //System.out.println("g_S "+g_S);
            //System.out.println("RX_c "+RX_c);
            if (g_S.equals(RX_c)) {
                System.out.println(tree.date + "的文件" + "验证通过 !");
            }
            else
                System.out.println(tree.date + "的文件" + "验证失败 !");

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void deploy_psc() throws Exception {
        Proofsubmit ps = Proofsubmit.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
        if (ps != null) {
            System.out.println("HelloWorld address is: " + ps.getContractAddress());
        }
    }
    public void proofSubmit(merkletree tree){
        //读取配置文件，SDK与区块链节点建立连接，获取web3j对象

    }
    /*public void test() throws IOException, NoSuchAlgorithmException {
        produce.produce_order pro_order = pro_order_list.get(0);
        purchase.purchase_order pur_order = pur_order_list.get(0);
        Map<String,String> pro_map = produceToMap(pro_order);
        trans_submit("0",pro_map);
        Map<String,String> pur_map = purchaseToMap(pur_order);
        trans_submit("1",pur_map);
        packgeToMerkle();
        SchnorrSignature.initPara(12);
        keyGeneration(merkletrees.get(0));
        groupSigh(merkletrees.get(0));
        checkGroupSigh((merkletrees.get(0)));
    }*/

    public void test() {
        System.out.println("nimamasile");
    }
}
