package org.fisco.bcos.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple1;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoder;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class Proofsubmit extends Contract {
    public static final String[] BINARY_ARRAY = {"60806040526040805190810160405280600f81526020017f70726f6f6654726565735461626c650000000000000000000000000000000000815250600090805190602001906200005192919062000230565b503480156200005f57600080fd5b50620000796200007f640100000000026401000000009004565b620002df565b600061100190508073ffffffffffffffffffffffffffffffffffffffff166356004b6a60006040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018060200180602001848103845285818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015620001645780601f10620001385761010080835404028352916020019162000164565b820191906000526020600020905b8154815290600101906020018083116200014657829003601f168201915b5050848103835260048152602001807f44617465000000000000000000000000000000000000000000000000000000008152506020018481038252600f8152602001807f50726f6f662c55736572735369676e0000000000000000000000000000000000815250602001945050505050602060405180830381600087803b158015620001ef57600080fd5b505af115801562000204573d6000803e3d6000fd5b505050506040513d60208110156200021b57600080fd5b81019080805190602001909291905050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200027357805160ff1916838001178555620002a4565b82800160010185558215620002a4579182015b82811115620002a357825182559160200191906001019062000286565b5b509050620002b39190620002b7565b5090565b620002dc91905b80821115620002d8576000816000905550600101620002be565b5090565b90565b610ded80620002ef6000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680633ad4319214610051578063d4d69c0c14610114575b600080fd5b34801561005d57600080fd5b506100fe600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506101d7565b6040518082815260200191505060405180910390f35b34801561012057600080fd5b506101c1600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506106ad565b6040518082815260200191505060405180910390f35b6000806000806000806000945061028760008054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561027d5780601f106102525761010080835404028352916020019161027d565b820191906000526020600020905b81548152906001019060200180831161026057829003601f168201915b5050505050610c9c565b93508373ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156102ed57600080fd5b505af1158015610301573d6000803e3d6000fd5b505050506040513d602081101561031757600080fd5b810190808051906020019092919050505092508273ffffffffffffffffffffffffffffffffffffffff1663e942b516886040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260098152602001807f55736572735369676e0000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b838110156103ea5780820151818401526020810190506103cf565b50505050905090810190601f1680156104175780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561043757600080fd5b505af115801561044b573d6000803e3d6000fd5b505050508373ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156104b357600080fd5b505af11580156104c7573d6000803e3d6000fd5b505050506040513d60208110156104dd57600080fd5b810190808051906020019092919050505091508373ffffffffffffffffffffffffffffffffffffffff1663bf2b70a18985856040518463ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825285818151815260200191508051906020019080838360005b838110156105de5780820151818401526020810190506105c3565b50505050905090810190601f16801561060b5780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b15801561062c57600080fd5b505af1158015610640573d6000803e3d6000fd5b505050506040513d602081101561065657600080fd5b81019080805190602001909291905050509050600181141561067b576000945061069f565b7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff94505b849550505050505092915050565b600080600080600080935061075b60008054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107515780601f1061072657610100808354040283529160200191610751565b820191906000526020600020905b81548152906001019060200180831161073457829003601f168201915b5050505050610c9c565b92508273ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156107c157600080fd5b505af11580156107d5573d6000803e3d6000fd5b505050506040513d60208110156107eb57600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663e942b516886040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260048152602001807f4461746500000000000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b838110156108be5780820151818401526020810190506108a3565b50505050905090810190601f1680156108eb5780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561090b57600080fd5b505af115801561091f573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663e942b516876040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260058152602001807f50726f6f66000000000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b838110156109e35780820151818401526020810190506109c8565b50505050905090810190601f168015610a105780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b158015610a3057600080fd5b505af1158015610a44573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663e942b5166040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260098152602001807f55736572735369676e000000000000000000000000000000000000000000000081525060200183810382526000815260200160200192505050600060405180830381600087803b158015610afc57600080fd5b505af1158015610b10573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff166331afac3688846040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015610bcf578082015181840152602081019050610bb4565b50505050905090810190601f168015610bfc5780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b158015610c1c57600080fd5b505af1158015610c30573d6000803e3d6000fd5b505050506040513d6020811015610c4657600080fd5b810190808051906020019092919050505090506001811415610c6b5760009350610c8f565b7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff93505b8394505050505092915050565b600080600061100191508173ffffffffffffffffffffffffffffffffffffffff1663f23f63c9856040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018281038252838181518152602001915080519060200190808383","60005b83811015610d2e578082015181840152602081019050610d13565b50505050905090810190601f168015610d5b5780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b158015610d7a57600080fd5b505af1158015610d8e573d6000803e3d6000fd5b505050506040513d6020811015610da457600080fd5b8101908080519060200190929190505050905080925050509190505600a165627a7a72305820abda13965a577ab66e9349c4c754b9e77cdf14aaf8ff03918037cd1b64025b440029"};

    public static final String BINARY = String.join("", BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"date\",\"type\":\"string\"},{\"name\":\"userssign\",\"type\":\"string\"}],\"name\":\"signSubmit\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"date\",\"type\":\"string\"},{\"name\":\"proof\",\"type\":\"string\"}],\"name\":\"proofSubmit\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"}]"};

    public static final String ABI = String.join("", ABI_ARRAY);

    public static final TransactionDecoder transactionDecoder = new TransactionDecoder(ABI, BINARY);

    public static final String FUNC_SIGNSUBMIT = "signSubmit";

    public static final String FUNC_PROOFSUBMIT = "proofSubmit";

    @Deprecated
    protected Proofsubmit(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Proofsubmit(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Proofsubmit(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Proofsubmit(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static TransactionDecoder getTransactionDecoder() {
        return transactionDecoder;
    }

    public RemoteCall<TransactionReceipt> signSubmit(String date, String userssign) {
        final Function function = new Function(
                FUNC_SIGNSUBMIT, 
                Arrays.<Type>asList(new Utf8String(date),
                new Utf8String(userssign)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void signSubmit(String date, String userssign, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_SIGNSUBMIT, 
                Arrays.<Type>asList(new Utf8String(date),
                new Utf8String(userssign)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String signSubmitSeq(String date, String userssign) {
        final Function function = new Function(
                FUNC_SIGNSUBMIT, 
                Arrays.<Type>asList(new Utf8String(date),
                new Utf8String(userssign)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple2<String, String> getSignSubmitInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SIGNSUBMIT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple2<String, String>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue()
                );
    }

    public Tuple1<BigInteger> getSignSubmitOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_SIGNSUBMIT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public RemoteCall<TransactionReceipt> proofSubmit(String date, String proof) {
        final Function function = new Function(
                FUNC_PROOFSUBMIT, 
                Arrays.<Type>asList(new Utf8String(date),
                new Utf8String(proof)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void proofSubmit(String date, String proof, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_PROOFSUBMIT, 
                Arrays.<Type>asList(new Utf8String(date),
                new Utf8String(proof)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String proofSubmitSeq(String date, String proof) {
        final Function function = new Function(
                FUNC_PROOFSUBMIT, 
                Arrays.<Type>asList(new Utf8String(date),
                new Utf8String(proof)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple2<String, String> getProofSubmitInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_PROOFSUBMIT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple2<String, String>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue()
                );
    }

    public Tuple1<BigInteger> getProofSubmitOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_PROOFSUBMIT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    @Deprecated
    public static Proofsubmit load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Proofsubmit(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Proofsubmit load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Proofsubmit(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Proofsubmit load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Proofsubmit(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Proofsubmit load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Proofsubmit(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Proofsubmit> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Proofsubmit.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Proofsubmit> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Proofsubmit.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Proofsubmit> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Proofsubmit.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Proofsubmit> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Proofsubmit.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
