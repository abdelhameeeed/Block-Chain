import java.security.PrivateKey;
import java.security.PublicKey;
public class transaction {
    public float amout  ;
    public PublicKey sender_public_key ;
    public String transaction_hash ;
    public PublicKey reciever_public_key ;
    public byte[] signature ;
    private int seq ;
    public transaction(PublicKey from, PublicKey to, float amount ) {
        this.sender_public_key = from ;
        this.reciever_public_key = to ;
        this.amout = amount ;
    }
    public String calc_trans_hash() {
        seq++;
        return StringUtil.applySha256(
                StringUtil.getStringFromKey(sender_public_key) +
                        StringUtil.getStringFromKey(reciever_public_key) +
                        Float.toString(amout) + seq
        );
    }
    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey(sender_public_key) + StringUtil.getStringFromKey(reciever_public_key)
                + Float.toString(amout);
        signature = StringUtil.applyECDSASig(privateKey , data);
    }
    public boolean verifiySignature() {
        String data = StringUtil.getStringFromKey(sender_public_key)
                + StringUtil.getStringFromKey(reciever_public_key) + Float.toString(amout);
        return StringUtil.verifyECDSASig(sender_public_key , data , signature);
    }
}