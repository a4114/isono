package models;

import java.util.ArrayList;

import org.codehaus.jackson.JsonNode;

import com.sun.media.jai.opimage.ClampCRIF;

import javassist.expr.Instanceof;

import play.api.libs.iteratee.internal;
import play.libs.Comet;
import play.libs.F.Callback0;
import play.libs.Json;
import scala.reflect.internal.Trees.This;
import views.html.newChannel;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;


public class CometUserManager extends UntypedActor {

    ArrayList<Comet> cometList=new ArrayList<>();
    
    public void RemoveComet(CometAddvanced comet){
        cometList.remove(comet);
    }
    
    @Override
    public void onReceive(final Object arg0) throws Exception {
        
        //登録申請がきた
        if(arg0 instanceof CometAddvanced){
            cometList.add((CometAddvanced)arg0);
            ((CometAddvanced)arg0).onDisconnected(new Callback0() {
                
                @Override
                public void invoke() throws Throwable {
                   RemoveComet((CometAddvanced)arg0);
                }
            });
        }
        
        //コメントが来た
        if(arg0 instanceof Comment){
            JsonNode jcomment = Json.toJson((Comment)arg0);
            for(Comet c : cometList){
                c.sendMessage(jcomment);
            }
        }
    }

    public ArrayList<Comet> getCometList(){return cometList;}
    
}
