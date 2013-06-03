package models;

import java.util.ArrayList;

import org.codehaus.jackson.JsonNode;

import play.libs.Comet;
import play.libs.F.Callback0;
import akka.actor.UntypedActor;


public class CometUserManager extends UntypedActor {

    ArrayList<Comet> cometList=new ArrayList<>();
    
    public void removeComet(CometAdvanced comet){
        cometList.remove(comet);
    }
    
    
    @Override
    public void onReceive(final Object arg0) throws Exception {
        
        //登録申請がきた
        if(arg0 instanceof CometAdvanced){
            cometList.add((CometAdvanced)arg0);
            ((CometAdvanced)arg0).onDisconnected(new Callback0() {
                
                @Override
                public void invoke() throws Throwable {
                   removeComet((CometAdvanced)arg0);
                }
            });
        }
        
        //コメントが来た
        if(arg0 instanceof Comment){
            JsonNode jnode=((Comment)arg0).toJson();
            for(Comet c : cometList){
                c.sendMessage(jnode);
            }
        }
    }

    public ArrayList<Comet> getCometList(){return cometList;}
    
}
