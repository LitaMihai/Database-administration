package stateDesign;

import states.LoginState;

public class Package {
    private PackageState state;
    static public Package pkg;

    public Package(){
        this.state = new LoginState();
    }

    public void previousState() {
        state.prev(this);
    }

    public void nextState() {
        state.next(this);
    }

    public void printStatus() {
        state.printStatus();
    }

    public void setState(PackageState state) {
        this.state = state;
    }
    public void setPackage(Package pkg){
        this.pkg = pkg;
    }
    public Package getPackage(){
        return this.pkg;
    }
}
