pragma solidity ^0.4.24;

import "./Table.sol";

contract proofsubmit{
    string private proof_table_name = "proofTreesTable";
    function byte32ToString(bytes32 name) view private returns(string){
        bytes memory newname = new bytes(name.length);
        
        for(uint i=0; i<name.length; i++){
            newname[i] = name[i];
        }
        return string(newname);
    }
    
    constructor() public{
        createTable();  // 
    }

    /*
     �������������ƻ��� 
    */
    function createTable() private{
        TableFactory tf = TableFactory(0x1001);

        // ���������ƻ���, key : vac_no
        // |         key|        |                value                  |
        // |-------------------- |-------------------|-------------------|
        // |         Date        |        Proof      |     UsersSign     |
        // |        String       |       String      |      String       | 
        // |---------------------|-------------------|-------------------|
        // ������
        tf.createTable(proof_table_name, "Date", "Proof,UsersSign");
    }
    
    function openTable(string table_name) private returns(Table){
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable(table_name);
        return table;
    }
    
    //=======================================================start=====================================================
    
    function proofSubmit(string date, string proof) public returns(int){
        int ret_code = 0;
        
        Table table = openTable(proof_table_name);
        Entry entry = table.newEntry();
        
        entry.set("Date", date);
        entry.set("Proof", proof);
        entry.set("UsersSign","");
        
        // ����
        int count = table.insert(date, entry);
        if(count == 1){
            ret_code = 0;   
        }
        else{
            ret_code = -1;  
        }
       
        return ret_code;
    }
    
    function signSubmit(string date, string userssign) public returns(int){
        int ret_code = 0;
        
        Table table = openTable(proof_table_name);
        Entry entry = table.newEntry();
        
        entry.set("UsersSign",userssign);
        Condition condition = table.newCondition();
        // ����
        int count = table.update(date, entry,condition);
        if(count == 1){
            ret_code = 0;   
        }
        else{
            ret_code = -1;  
        }
       
        return ret_code;
    }
    
}
