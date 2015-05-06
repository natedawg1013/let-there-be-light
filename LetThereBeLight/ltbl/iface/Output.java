package ltbl.iface;

public interface Output {
    public void setChannel(int channel, float value);
    public void update();
    public void addSource(OutputSource o);
}