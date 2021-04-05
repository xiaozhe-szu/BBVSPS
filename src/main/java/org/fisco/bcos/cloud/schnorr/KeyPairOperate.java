package org.fisco.bcos.cloud.schnorr;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class KeyPairOperate {
    public static String getFileName(String path) {
        String[] str = path.split("/");
        return str[str.length-1];
    }

    /**
     * @Description: 工具类，数组转集合
     * @param para
     * @return
     * @Return:List<String>
     * @Date:上午10:24:33
     */
    public static List<String> transArryToLi(String[] para){
        List<String> li = new ArrayList<String>();
        for(int i=0; i<para.length; i++) {
            li.add(para[i]);
        }
        return li;
    }

    /**
     * @Description: 获取配置文件参数
     * @param path : 文件路径
     * @param key ： 获取参数的key
     * @Date:上午9:46:26
     */
    public static String getDataFromFile(String path, String key) {
        String para = null;
        try {
            Properties pro = new Properties();
            pro.load(new FileInputStream(path));
            para = (String) pro.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return para;
    }

    /**
     * @Description:写公共密钥到文件  -- 公钥证书列表
     * @param path : 存储的路径
     * @param param ： 参数
     * @param flag ： 是否是追加存储，公共参数不是追加，公钥是追加； 追加： true， 覆盖： flase
     * @Return:void
     * @Date:上午10:20:25
     */
    public static void writePublicKeyToFile(String path, List<String> param, Boolean flag) {
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(path,flag));
            for(String element : param) {
                printWriter.println(element);
            }
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
