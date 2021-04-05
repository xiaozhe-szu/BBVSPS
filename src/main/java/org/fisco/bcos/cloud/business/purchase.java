package org.fisco.bcos.cloud.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service("purchase")
public class purchase {

    public ArrayList<purchase_order> order_list;


    @Autowired
    ResourceLoader resourceLoader;

    public static class purchase_order{
        public String mau_num;
        public String bitch;
        public String vaccine_name;
        public String vaccine_num;
        public String date;
        public String cdc_num;
        public String state;
        public String order_num;

        public purchase_order(String mau_num,String bitch,String vaccine_name,String vaccine_num,String cdc_num,String order_num){
            this.mau_num = mau_num;
            this.bitch = bitch;
            this.vaccine_name = vaccine_name;
            this.vaccine_num = vaccine_num;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            String date = df.format(new Date());
            this.date = date;
            this.cdc_num = cdc_num;
            this.state = "0";
            this.order_num = order_num;
        }

        public purchase_order(String mau_num,String bitch,String vaccine_name,String vaccine_num,String date,String cdc_num,String state,String order_num){
            this.mau_num = mau_num;
            this.bitch = bitch;
            this.vaccine_name = vaccine_name;
            this.vaccine_num = vaccine_num;
            this.date = date;
            this.cdc_num = cdc_num;
            this.state = state;
            this.order_num = order_num;
        }

        public void set_state(String state){
            this.state = state;
        }
    }


    public void initial() throws IOException {
        this.order_list = read_order();
    }

    public static ArrayList<purchase_order> read_order() throws IOException {

        ResourceLoader resourceLoader = null;
        ArrayList<purchase_order> list = new ArrayList<purchase_order>();
        Resource resource = resourceLoader.getResource("classpath:/business/purchase.txt");

        InputStream is = resource.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String s = "";

        while ((s = br.readLine()) != null) {
            String[] order_data = s.split(" ");
            purchase_order order = new purchase_order(order_data[0],order_data[1],order_data[2],
                    order_data[3],order_data[4],order_data[5],order_data[6],order_data[7]);
            list.add(order);
        }
        return list;
    }

    public void purchase_apply(String mau_num,String bitch,String vaccine_name,String vaccine_num,String cdc_num,String order_num) throws IOException {
        purchase_order order = new purchase_order(mau_num,bitch,vaccine_name,vaccine_num,cdc_num,order_num);
        this.order_list.add(order);
    }

    public void display_order() throws IOException {


        for(int i=0;i<this.order_list.size();i++){
            purchase_order order = this.order_list.get(i);
            String data = ""+String.format("%-10S",order.mau_num)+String.format("%-10s",order.bitch)+
                    String.format("%-20s",order.vaccine_name)+String.format("%-10s",order.vaccine_num)+
                    String.format("%-30s",order.date)+String.format("%-10s",order.cdc_num)+String.format("%-5s",order.state)+
                    String.format("%-10s",order.order_num);
            System.out.println(data);
        }
    }

    public int Inquire(String order_num){
        for(int i=0;i<order_list.size();i++){
            purchase_order order_temp = order_list.get(i);
            if(order_num.equals(order_temp.order_num)){
                return i;
            }
        }
        return -1;
    }

    public void order_process(String order_num){
        int index = Inquire(order_num);
        purchase_order order = order_list.get(index);
        order.set_state("1");
        order_list.set(index,order);
    }

    public void odrer_update() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/business/purchase.txt");
        //get the path of fileInquire
        String filepath = resource.getURI().getPath();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath)));

        for(int i=0;i<order_list.size();i++){
            purchase_order order = order_list.get(i);
            //tandardized data format
            String data = ""+String.format("%-10S",order.mau_num)+String.format("%-10s",order.bitch)+
                    String.format("%-20s",order.vaccine_name)+String.format("%-10s",order.vaccine_num)+
                    String.format("%-30s",order.date)+String.format("%-10s",order.cdc_num)+String.format("%-5s",order.state)+
                    String.format("%-10s",order.order_num)+"\r";
            bw.write(data);
        }
        bw.flush();
        bw.close();
    }
}
