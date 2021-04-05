package org.fisco.bcos.cloud.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class transport {

    public ArrayList<transport_order> order_list;

    @Autowired
    ResourceLoader resourceLoader;

    public static class transport_order{
        public String te_num;
        public String mau_num;
        public String order_num;
        public String transport_point;
        public String date;
        public String state;

        public transport_order(String te_num,String mau_num,String order_num,String transport_point){
            this.te_num = te_num;
            this.mau_num = mau_num;
            this.order_num = order_num;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            String date = df.format(new Date());
            this.date = date;
            this.transport_point = transport_point;
            this.state = "0";
        }

        public transport_order(String te_num,String mau_num,String order_num,String transport_point,String date,String state){
            this.te_num = te_num;
            this.mau_num = mau_num;
            this.order_num = order_num;
            this.date = date;
            this.transport_point = transport_point;
            this.state = state;
        }

        public void set_state(String state){
            this.state = state;
        }
    }

    public void initial() throws IOException {
        this.order_list = read_order();
    }

    public static ArrayList<transport_order> read_order() throws IOException {

        ResourceLoader resourceLoader = null;
        ArrayList<transport_order> list = new ArrayList<transport_order>();
        Resource resource = resourceLoader.getResource("classpath:/business/transport.txt");

        InputStream is = resource.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String s = "";

        while ((s = br.readLine()) != null) {
            String[] order_data = s.split(" ");
            transport_order order = new transport_order(order_data[0],order_data[1],order_data[2],
                    order_data[3],order_data[4],order_data[5]);
            list.add(order);
        }
        return list;
    }

    public void transport_apply(String te_num,String mau_num,String order_num,String transport_point) throws IOException {
        transport_order order = new transport_order(te_num,mau_num,order_num,transport_point);
        this.order_list.add(order);
    }

    public void display_order() throws IOException {

        for(int i=0;i<this.order_list.size();i++){
            transport_order order = this.order_list.get(i);
            String data = ""+String.format("%-10S",order.te_num)+String.format("%-10s",order.mau_num)+
                    String.format("%-10s",order.order_num)+String.format("%-20s",order.transport_point)+
                    String.format("%-30s",order.date)+String.format("%-5s",order.state);
            System.out.println(data);
        }
    }

    public int Inquire(String order_num){
        for(int i=0;i<order_list.size();i++){
            transport_order order_temp = order_list.get(i);
            if(order_num.equals(order_temp.order_num)){
                return i;
            }
        }
        return -1;
    }

    public void order_process(String order_num){
        int index = Inquire(order_num);
        transport_order order = order_list.get(index);
        order.set_state("1");
        order_list.set(index,order);
    }

    public void odrer_update() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/business/transport.txt");
        //get the path of fileInquire
        String filepath = resource.getURI().getPath();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath)));

        for(int i=0;i<order_list.size();i++){
            transport_order order = order_list.get(i);
            //tandardized data format
            String data = ""+String.format("%-10S",order.te_num)+String.format("%-10s",order.mau_num)+
                    String.format("%-10s",order.order_num)+String.format("%-20s",order.transport_point)+
                    String.format("%-30s",order.date)+String.format("%-5s",order.state);
            bw.write(data);
        }
        bw.flush();
        bw.close();
    }
}
