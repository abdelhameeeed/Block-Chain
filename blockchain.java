import java.security.PublicKey;
import java.util.ArrayList;
public class blockchain
{
     public ArrayList<Blocks> blockchains = new ArrayList<Blocks>() ;
     public  blockchain(){}
     public blockchain(ArrayList<Blocks> b )
     {
         for(int i = 0 ; i < b.size() ; i ++ )
         { blockchains.add ( b.get(i) ) ;  }
         //System.out.println("our block chain is " ) ;
         //String all_blockchain = new GsonBuilder().setPrettyPrinting().
           //      create().toJson(blockchains) ;
         //System.out.println(all_blockchain)   ;
     }
     public  Boolean  is_valid_chain()
     {
         Blocks cur_block  ;
         Blocks prev_block ;
        for(int i = 1 ; i < blockchains.size() ; i ++ )
        {
           prev_block = blockchains.get(i-1)      ;
           cur_block = blockchains.get(i)         ;
           if(! cur_block.prevhash.equals( prev_block.hash )   ) return  false ;
           if(!cur_block.hash.equals(cur_block.calc()) ) return  false ;
        }
        return true  ;
     }
     public  boolean check_valid_transaction( transaction tt )
     {
           int X = 0 ;
           for( int i = 1 ; i < blockchains.size() ; i ++ )
           {
               Blocks g = blockchains.get(i) ;
               if( g.t.reciever_public_key  == tt.sender_public_key )
               {
                   X += g.t.amout ;
               }
           }
           for(int i = 1 ; i < blockchains.size() ; i ++ )
           {

               Blocks g = blockchains.get(i) ;
               if(g.t.sender_public_key == tt.sender_public_key )
               {
                   X -= g.t.amout ;
               }
           }
         return  X >= tt.amout ;
     }
}