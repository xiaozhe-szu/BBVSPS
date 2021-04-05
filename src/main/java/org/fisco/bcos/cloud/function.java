package org.fisco.bcos.cloud;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service("function")
public class function {

    //private static final Logger logger = LoggerFactory.getLogger(function.class);

    @Autowired
    ResourceLoader resourceLoader;

    public  ArrayList<String> readfromtxt(String address) throws IOException {
        ArrayList<String> list = new ArrayList<String>();
        Resource resource = resourceLoader.getResource("classpath:"+address);

        InputStream is = resource.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String s = "";
        while ((s = br.readLine()) != null) {
            list.add(s);
        }
        return list;
    }

    public void test() throws IOException {

        ArrayList<String> list = readfromtxt("test.txt");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String date = df.format(new Date());
        System.out.println(date);

    }


}
