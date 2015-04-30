package ltbl.io;

public interface Output {	
    public abstract void setChannel(int channel, float value);
    public abstract void update();
}