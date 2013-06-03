package models;

import play.libs.Comet;
import controllers.Application;

//Comet自身にUser情報をもたせる
public class CometAdvanced extends Comet {

    private final String channelURI;
    private final User user;

    public User getUser() {
        return getUser();
    }
    
    public CometAdvanced(String callbackMethod,String channelURI,User user) {
        super(callbackMethod);
        this.user = user;
        this.channelURI = channelURI;
    }

    @Override
    public void onConnected() {
        try{
            Application.addUserToChannel(channelURI, this);
        }catch(Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
