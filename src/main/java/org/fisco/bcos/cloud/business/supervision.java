package org.fisco.bcos.cloud.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service("supervision")
public class supervision {

    public ArrayList<tag_orderI> tag_orderI_list;
    public ArrayList<tag_orderII> tag_orderII_list;



    @Autowired
    ResourceLoader resourceLoader;


    public static class tag_orderI{
        public String mau_num;
        public String bitch;
        public String scdc_num;
        public String icdc_num;
        public String tag_front;
        public String tag_tail;
        public String state;
        public String date;

        public tag_orderI(String mau_num,String bitch,String scdc_num,String icdc_num,String tag_front,String tag_tail){
            this.mau_num = mau_num;
            this.bitch = bitch;
            this.scdc_num = scdc_num;
            this.icdc_num = icdc_num;
            this.tag_front = tag_front;
            this.tag_tail = tag_tail;
            this.state = "0";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            String date = df.format(new Date());
            this.date = date;
        }

        public tag_orderI(String mau_num,String bitch,String scdc_num,String icdc_num,String tag_front,String tag_tail,String state,String date){
            this.mau_num = mau_num;
            this.bitch = bitch;
            this.scdc_num = scdc_num;
            this.icdc_num = icdc_num;
            this.tag_front = tag_front;
            this.tag_tail = tag_tail;
            this.state = state;
            this.date = date;
        }

        public void set_state(String state){
            this.state = state;
        }
    }

    public static class tag_orderII{
        public String cdc_num;
        public String mau_num;
        public String bitch;
        public String vi_num;
        public String tag_front;
        public String tag_tail;
        public String state;
        public String date;

        public tag_orderII(String cdc_num,String mau_num,String bitch,String vi_num,String tag_front,String tag_tail){
            this.cdc_num = cdc_num;
            this.mau_num = mau_num;
            this.bitch = bitch;
            this.vi_num = vi_num;
            this.tag_front = tag_front;
            this.tag_tail = tag_tail;
            this.state = "0";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            String date = df.format(new Date());
            this.date = date;
        }

        public tag_orderII(String cdc_num,String mau_num,String bitch,String vi_num,String tag_front,String tag_tail,String state,String date){
            this.cdc_num = cdc_num;
            this.mau_num = mau_num;
            this.bitch = bitch;
            this.vi_num = vi_num;
            this.tag_front = tag_front;
            this.tag_tail = tag_tail;
            this.state = state;
            this.date = date;
        }

        public void set_state(String state){
            this.state = state;
        }
    }

    public void initial() throws IOException {
        this.tag_orderI_list = read_tag_orderI();
        this.tag_orderII_list = read_tag_orderII();
    }

    public static ArrayList<tag_orderI> read_tag_orderI() throws IOException {

        ResourceLoader resourceLoader = null;
        ArrayList<tag_orderI> list = new ArrayList<>();
        Resource resource = resourceLoader.getResource("classpath:/business/supervisionI.txt");

        InputStream is = resource.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String s = "";

        while ((s = br.readLine()) != null) {
            String[] order_data  = s.split(" ");
            tag_orderI order = new tag_orderI(order_data[0],order_data[1],order_data[2],
                    order_data[3],order_data[4],order_data[5],order_data[6],order_data[7]);
            list.add(order);
        }
        return list;
    }

    public static ArrayList<tag_orderII> read_tag_orderII() throws IOException {

        ResourceLoader resourceLoader = null;
        ArrayList<tag_orderII> list = new ArrayList<tag_orderII>();
        Resource resource = resourceLoader.getResource("classpath:/business/supervisionII.txt");

        InputStream is = resource.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String s = "";

        while ((s = br.readLine()) != null) {
            String[] order_data  = s.split(" ");
            tag_orderII order = new tag_orderII(order_data[0],order_data[1],order_data[2],
                    order_data[3],order_data[4],order_data[5],order_data[6],order_data[7]);
            list.add(order);
        }
        return list;
    }

    public void tag_orderI_apply(String mau_num,String bitch,String scdc_num,String icdc_num,String tag_front,String tag_tail) throws IOException {
        tag_orderI order = new tag_orderI(mau_num,bitch,scdc_num,icdc_num,tag_front,tag_tail);
        this.tag_orderI_list.add(order);
    }

    public void tag_orderII_apply(String cdc_num,String mau_num,String bitch,String vi_num,String tag_front,String tag_tail) throws IOException {
        tag_orderII order = new tag_orderII(cdc_num,mau_num,bitch,vi_num,tag_front,tag_tail);
        this.tag_orderII_list.add(order);
    }

    /*public void display_tag_orderI() throws IOException {

        for(int i=0;i<this.tag_orderI_list.size();i++){
            tag_orderI order = this.tag_orderI_list.get(i);
            String data = ""+String.format("%-10S",order.mau_num)+String.format("%-10s",order.bitch)+
                    String.format("%-10s",order.cdc_num)+String.format("%-20s",order.tag_front)+
                    String.format("%-20s",order.tag_tail)+String.format("%-5s",order.state)+String.format("%-20s",order.date);
            System.out.println(data);
        }
    }

    public void display_tag_orderII() throws IOException {

        for(int i=0;i<this.tag_orderII_list.size();i++){
            tag_orderII order = this.tag_orderII_list.get(i);
            String data = String.format("%-10s",order.cdc_num)+String.format("%-10S",order.mau_num)+String.format("%-10s",order.bitch)+
                    String.format("%-10s",order.vi_num)+String.format("%-20s",order.tag_front)+String.format("%-20s",order.tag_tail)
                    +String.format("%-5s",order.state)+String.format("%-20s",order.date);
            System.out.println(data);
        }
    }*/

    public int Inquire_tag_orderI(String mau_num,String scdc_num,String batch){
        for(int i=0;i<tag_orderI_list.size();i++){
            tag_orderI order_temp = tag_orderI_list.get(i);
            if(mau_num.equals(order_temp.mau_num) && scdc_num.equals(order_temp.scdc_num) && batch.equals(order_temp.bitch)){
                return i;
            }
        }
        return -1;
    }

    public int Inquire_tag_orderII(String cdc_num,String vi_num,String batch){
        for(int i=0;i<tag_orderI_list.size();i++){
            tag_orderII order_temp = tag_orderII_list.get(i);
            if(cdc_num.equals(order_temp.cdc_num) && vi_num.equals(order_temp.vi_num) && batch.equals(order_temp.bitch)){
                return i;
            }
        }
        return -1;
    }

    public void tag_orderI_process(String mau_num,String cdc_num,String batch){
        int index = Inquire_tag_orderI(mau_num,cdc_num,batch);
        tag_orderI order = tag_orderI_list.get(index);
        order.set_state("1");
        tag_orderI_list.set(index,order);
    }

    public void tag_orderII_process(String cdc_num,String vi_num,String batch){
        int index = Inquire_tag_orderII(cdc_num,vi_num,batch);
        tag_orderII order = tag_orderII_list.get(index);
        order.set_state("1");
        tag_orderII_list.set(index,order);
    }

    /*public void odrer_update() throws IOException {
        Resource resourceI = resourceLoader.getResource("classpath:/business/supervisionI.txt");
        Resource resourceII = resourceLoader.getResource("classpath:/business/supervisionII.txt");
        //get the path of fileInquire
        String filepathI = resourceI.getURI().getPath();
        String filepathII = resourceII.getURI().getPath();
        BufferedWriter bwI = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepathI)));
        BufferedWriter bwII = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepathII)));

        for(int i=0;i<tag_orderI_list.size();i++){
            tag_orderI order = tag_orderI_list.get(i);
            //tandardized data format
            String data = ""+String.format("%-10S",order.mau_num)+String.format("%-10s",order.bitch)+
                    String.format("%-10s",order.cdc_num)+String.format("%-20s",order.tag_front)+
                    String.format("%-20s",order.tag_tail)+String.format("%-5s",order.state)+String.format("%-20s",order.date);
            bwI.write(data);
        }
        bwI.flush();
        bwI.close();

        for(int i=0;i<tag_orderII_list.size();i++){
            tag_orderII order = tag_orderII_list.get(i);
            //tandardized data format
            String data = String.format("%-10s",order.cdc_num)+String.format("%-10S",order.mau_num)+String.format("%-10s",order.bitch)+
                    String.format("%-10s",order.vi_num)+String.format("%-20s",order.tag_front)+String.format("%-20s",order.tag_tail)
                    +String.format("%-5s",order.state)+String.format("%-20s",order.date);
            bwII.write(data);
        }
        bwII.flush();
        bwII.close();
    }*/
}
