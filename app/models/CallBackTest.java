package models;

import play.libs.F.Callback0;

public class CallBackTest implements Callback0{

    @Override
    public void invoke() throws Throwable {
        System.out.println("invoke callback");
        
    }

}
