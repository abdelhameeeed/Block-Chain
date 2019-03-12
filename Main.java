import java.security.Security;
import java.util.ArrayList   ;
public class Main {
    public static ArrayList<transaction> input_transaction = new ArrayList<transaction>();
    public static ArrayList<transaction> output_transaction = new ArrayList<transaction>();

    public static void main(String[] args) {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Wallet A = new Wallet() ;
        Wallet B = new Wallet() ;
        blockchain history = new blockchain() ;
        //System.out.println("Private and public keys:");
        //System.out.println(StringUtil.getStringFromKey(A.privateKey));
        //System.out.println(StringUtil.getStringFromKey(A.publicKey));
        Blocks b = new Blocks(null , "0") ;
        b.mine_block(5) ;
        history.blockchains.add( b ) ;
        transaction Transaction =
                new transaction(A.publicKey, B.publicKey, 5) ;
        int Last_ind = history.blockchains.size()-1  ;
        b = new Blocks(Transaction , history.blockchains.get(Last_ind).hash);
        b.mine_block(5)      ;
        history.blockchains.add(b) ;
        transaction Transaction1 =
                new transaction (B.publicKey , A.publicKey , 2 ) ;
        Transaction.generateSignature(A.privateKey)          ;
        Transaction1.generateSignature(B.privateKey)         ;
        if( history.check_valid_transaction(Transaction1) )
        {
            System.out.println("success !! ") ;
            Last_ind = history.blockchains.size()-1  ;
            b = new Blocks(Transaction1 , history.blockchains.get(Last_ind).hash) ;
            b.mine_block(5 )        ;
        }
        else
            System.out.println("invalid Transaction") ;
    }
}