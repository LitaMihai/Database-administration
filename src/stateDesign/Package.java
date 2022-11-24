package stateDesign;

import states.InsertState;
import states.LoginState;

import javax.swing.*;

public class Package {
    private PackageState state;
    static public Package pkg;

    public Package(JFrame frame){
        this.state = new LoginState(frame);
        this.state.printStatus();
    }

    public void setState(PackageState state) {
        this.state = state;
    }
    public void setPackage(Package pkg){
        if(pkg != null)
            Package.pkg = pkg;
    }
}
