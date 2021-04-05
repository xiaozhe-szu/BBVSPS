package org.fisco.bcos.cloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Service("identity")
public class identiy {
    // Role identity

    private static final Logger logger = LoggerFactory.getLogger(function.class);

    @Autowired
    ResourceLoader resourceLoader;
    String address = String.format("%-50s","address");
    String num = String.format("%-10s","num");
    String name = String.format("%-40s","name");
    String level = String.format("%-5s","level");
    String location = String.format("%-30s","location");
    /*
     * The identity of Manufacturer
     */
    public class MAU{
        private String address;
        private String num;
        private String name;

        public MAU(String address,String num,String name){
            this.address = address;
            this.num = num;
            this.name = name;
        }
    }

    /*
     * The identity of Center for disease control and prevention(CDC)
     */
    public class CDC{
        private String address;
        private String num;
        private String name;
        private String level;
        private String location;

        public CDC(String address,String num,String name,String level,String location){
            this.address = address;
            this.num = num;
            this.name = name;
            this.level = level;
            this.location = location;
        }
    }

    /*
     * The identity of Inoculation institution
     */
    public class VI{
        private String address;
        private String num;
        private String name;
        private String location;


        public VI(String address,String num,String name,String location){
            this.address = address;
            this.num = num;
            this.name = name;
            this.location = location;
        }
    }

    /*
     * The identity of Transport enterprise
     */
    public class TE{
        private String address;
        private String num;
        private String name;


        public TE(String address,String num,String name){
            this.address = address;
            this.num = num;
            this.name = name;
        }
    }

    /*
     * Read the txt of MAU identity
     */
    public ArrayList<MAU> read_MAU_txt() throws IOException {

        ArrayList<MAU> list = new ArrayList<MAU>();
        Resource resource = resourceLoader.getResource("classpath:/identity/MAU.txt");

        InputStream is = resource.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String s = "";

        while ((s = br.readLine()) != null) {
            String[] mau_data = new String[3];
            mau_data = s.split(" ");
            MAU mau = new MAU(mau_data[0],mau_data[1],mau_data[2]);
            list.add(mau);
        }
        return list;
    }

    /*
     * Display the lsit of MAU
     */
    public void display_MAU_lsit() throws IOException {
        ArrayList<MAU> list = read_MAU_txt();
        for(int i=0;i<list.size();i++){
            MAU mau = list.get(i);

            System.out.println("list of MAU");
            System.out.println(address+num+name);
            String mau_address = String.format("%-50s",mau.address);
            String mau_num = String.format("%-10s",mau.num);
            String mau_name = String.format("%-40s",mau.name);
            System.out.println(mau_address+mau_num+mau_name);
            System.out.println("");
        }
    }

    /*
     * Read the txt of CDC identity
     */
    public ArrayList<CDC> read_CDC_txt() throws IOException {

        ArrayList<CDC> list = new ArrayList<CDC>();
        Resource resource = resourceLoader.getResource("classpath:/identity/CDC.txt");

        InputStream is = resource.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String s = "";

        while ((s = br.readLine()) != null) {
            String[] cdc_data = new String[5];
            cdc_data = s.split(" ");
            CDC cdc = new CDC(cdc_data[0],cdc_data[1],cdc_data[2],cdc_data[3],cdc_data[4]);
            list.add(cdc);
        }
        return list;
    }

    /*
     * Display the lsit of CDC
     */
    public void display_CDC_lsit() throws IOException {
        ArrayList<CDC> list = read_CDC_txt();
        for(int i=0;i<list.size();i++){
            CDC cdc = list.get(i);

            System.out.println("list of CDC");
            System.out.println(address+num+name+level+location);
            String cdc_address = String.format("%-50s",cdc.address);
            String cdc_num = String.format("%-10s",cdc.num);
            String cdc_name = String.format("%-40s",cdc.name);
            String cdc_level = String.format("%-5s",cdc.level);
            String cdc_location = String.format("%-30s",cdc.location);
            System.out.println(cdc_address+cdc_num+cdc_name+cdc_level+cdc_location);
            System.out.println("");
        }
    }

    /*
     * Read the txt of VI identity
     */
    public ArrayList<VI> read_VI_txt() throws IOException {

        ArrayList<VI> list = new ArrayList<VI>();
        Resource resource = resourceLoader.getResource("classpath:/identity/VI.txt");

        InputStream is = resource.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String s = "";

        while ((s = br.readLine()) != null) {
            String[] vi_data = new String[4];
            vi_data = s.split(" ");
            VI vi = new VI(vi_data[0],vi_data[1],vi_data[2],vi_data[3]);
            list.add(vi);
        }
        return list;
    }

    /*
     * Display the lsit of VI
     */
    public void display_VI_lsit() throws IOException {
        ArrayList<VI> list = read_VI_txt();
        for(int i=0;i<list.size();i++){
            VI vi = list.get(i);

            System.out.println("list of VI");
            System.out.println(address+num+name+location);
            String vi_address = String.format("%-50s",vi.address);
            String vi_num = String.format("%-10s",vi.num);
            String vi_name = String.format("%-40s",vi.name);
            String vi_location = String.format("%-30s",vi.location);
            System.out.println(vi_address+vi_num+vi_name+vi_location);
            System.out.println("");
        }
    }

    /*
     * Read the txt of TE identity
     */
    public ArrayList<TE> read_TE_txt() throws IOException {

        ArrayList<TE> list = new ArrayList<TE>();
        Resource resource = resourceLoader.getResource("classpath:/identity/TE.txt");

        InputStream is = resource.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String s = "";

        while ((s = br.readLine()) != null) {
            String[] te_data = new String[3];
            te_data = s.split(" ");
            TE vi = new TE(te_data[0],te_data[1],te_data[2]);
            list.add(vi);
        }
        return list;
    }

    /*
     * Display the lsit of TE
     */
    public void display_TE_lsit() throws IOException {
        ArrayList<TE> list = read_TE_txt();
        for(int i=0;i<list.size();i++){
            TE te = list.get(i);

            System.out.println("list of TE");
            System.out.println(address+num+name);
            String te_address = String.format("%-50s",te.address);
            String te_num = String.format("%-10s",te.num);
            String te_name = String.format("%-40s",te.name);
            System.out.println(te_address+te_num+te_name);
            System.out.println("");
        }
    }
}
