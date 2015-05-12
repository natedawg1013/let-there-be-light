package ltbl.iface;

import java.util.ArrayList;

// The Output class is the last thing before being turned into either DMXOut or SimOut
// Functional cohesion. Output class works with the other output classes to contribute to the task of providing output for the user
// Output makes use of the "Visitor" design pattern
// Output collaborates with the following classes
// OutputSettings - Control coupling. OutputSettings manipulates the behavior of Output
// DMXOut - Control coupling. Output flags the output to either be of type DMXOut or SimOut and notifies the respective class to perform its tasks
// SimOut - Control coupling


public interface Output extends Runnable {
    public void setChannel(int channel, float value);
    public void update();
    public void addSource(OutputSource o);
    public void copy(Output other);
    public ArrayList<OutputSource> getSources();
    public void stop();
}