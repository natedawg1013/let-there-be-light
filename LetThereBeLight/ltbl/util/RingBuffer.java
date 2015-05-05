package ltbl.util;
public class RingBuffer{
	private byte[] ring;
	private int next=0;
	int length=0;
	int size;
	
	public RingBuffer(int size){
		ring = new byte[size];
		this.size=size;
	}
	
	public synchronized void write(byte[] in, int len){
		int remaining = ring.length-next;
		int inStart=Math.max(0,len-ring.length);
		if(len>ring.length) len=ring.length;
		int n = Math.min(remaining, len);
		System.arraycopy(in, inStart, ring, next, n);
		length+=n;
		next+=n;
		length=Math.min(length, ring.length);
		next%=ring.length;
		if(n==remaining && len>remaining){
			System.arraycopy(in, inStart+n, ring, next, len-n);
			next+=(len-n);
			length+=n;
			length=Math.min(length, ring.length);
			next%=ring.length;
		}
	}
	
	public synchronized byte[] peek(int len){
		int n = Math.min(length, len);
		byte[] buf = new byte[n];
		int start = (next-length+ring.length)%ring.length;
		int s = Math.min(ring.length-start, len);
		System.arraycopy(ring, start, buf, 0, s);
		if(s<len){
			System.arraycopy(ring, 0, buf, s, n-s);
		}
		return buf;
	}
	
	public synchronized byte[] read(int len){
		byte[] buf=peek(len);
		length-=buf.length;
		return buf;
	}
	
	public int length(){
		return length;
	}
	
	public int getSize(){
		return size;
	}
}