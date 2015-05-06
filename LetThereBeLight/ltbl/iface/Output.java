package ltbl.iface;

import java.util.ArrayList;

public interface Output extends Runnable {
    public void setChannel(int channel, float value);
    public void update();
    public void addSource(OutputSource o);
    public void copy(Output other);
    public ArrayList<OutputSource> getSources();
    public void stop();
}