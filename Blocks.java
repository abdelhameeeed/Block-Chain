import java.util.Date;
public class Blocks {
    String hash ;
    String prevhash ;
    transaction t   ;
    long timestamp  ;
    int rand = 0    ;
    public Blocks(transaction tt  , String phash ) {
         this.t = tt ;
        this.prevhash = phash ;
        this.timestamp = new Date().getTime();
        this.hash = calc() ;
        this.rand = 0 ;
     }
    public  String calc()
    {
        String calculatedhash = StringUtil.applySha256(
                prevhash +
                        Long.toString(timestamp) +
                        t + Integer.toString(rand)
        );
        return calculatedhash;
    }
    public void mine_block( int factor )
    {
        String X = "" ;
        for(int i = 0 ; i < factor ; i ++ ) X = X + '0' ;
        while (!hash.substring( 0 , factor  ).equals ( X ) )
        {
            ++rand        ;
            hash = calc() ;
        }
        System.out.println("block is mined " + hash  ) ;
    }
}