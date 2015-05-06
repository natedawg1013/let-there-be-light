package ltbl.algo;

import java.awt.Color;
import java.util.ArrayList;

import ltbl.control.Runner;
import ltbl.iface.FourierUpdateListener;
import ltbl.iface.Output;
import ltbl.iface.OutputSource;
import ltbl.util.Pair;

public class FFTBox implements FourierUpdateListener, OutputSource{

	// relative to the outer boundaries of the space
	private double[] relativeDimensions;
	private ArrayList<Pair<Integer, Integer>> settings;
	private ArrayList<Pair<Integer, Float>> outs;
	private boolean ready;
	private boolean autoHueX, autoHueY, autoValX, autoValY;
	private boolean rgb;

	public FFTBox ( Runner r ) {
		relativeDimensions = new double[4];
		settings = new ArrayList<Pair<Integer, Integer>>();
		outs = new ArrayList<Pair<Integer, Float>>();
		ready = false;
		r.getFourier().addUpdateListener(this);
	}

	public void setDimensions ( int[] dim, int xOut, int yOut ) {
		relativeDimensions[0] = ( (double) dim[0] ) / xOut;
		relativeDimensions[1] = ( (double) dim[1] ) / yOut;
		relativeDimensions[2] = ( (double) dim[2] ) / xOut;
		relativeDimensions[3] = ( (double) dim[3] ) / yOut;
		ready = true;
	}

	public int[] getDimensions ( int xDim, int yDim ) {
		int[] newDim = new int[4];
		if (ready) {
			newDim[0] = (int) (xDim*relativeDimensions[0]);
			newDim[1] = (int) (yDim*relativeDimensions[1]);
			newDim[2] = (int) (xDim*relativeDimensions[2]);
			newDim[3] = (int) (yDim*relativeDimensions[3]);
		}
		else {
			for ( int i = 0; i < newDim.length; ++i ) {
				newDim[i] = 0;
			}
		}
		return newDim;
	}

	public void set ( ArrayList<Pair<Integer, Integer>> channels, boolean ahx,
			boolean ahy, boolean avx, boolean avy, boolean rgb ) {
		settings = channels;
		autoHueX = ahx;
		autoHueY = ahy;
		autoValX = avx;
		autoValY = avy;
		this.rgb = rgb;
	}

	@Override
	public void update(FourierAnalysis a) {
		if (ready) {
			calcValues(a);
		}
	}

	@Override
	public void getOutputs(Output o) {
		for(Pair<Integer, Float> out : outs){
			o.setChannel(out.first,  out.second);
		}
	}

	private void calcValues(FourierAnalysis a){
		if(isInBox(a)){
			if(rgb){
				float r, g, b;
				float hue;
				float sat;
				float val;
				if(autoHueX || autoHueY){
					hue = getAuto(a, false);
					sat = 1.0f;
				}
				else{
					float[] hsv = Color.RGBtoHSB(settings.get(0).second, settings.get(1).second,
							settings.get(2).second, null);
					hue=hsv[0];
					sat=hsv[1];
				}
				if(autoValX || autoValY) val = getAuto(a, true);
				else if(autoHueX || autoHueY) val = settings.get(0).second/255f;
				else{
					float[] hsv = Color.RGBtoHSB(settings.get(0).second, settings.get(1).second,
							settings.get(2).second, null);
					val=hsv[2];
				}
				int color = Color.HSBtoRGB(hue, sat, val);
				r=((color&0x00FF0000)>>16)/255f;
				g=((color&0x0000FF00)>> 8)/255f;
				b=((color&0x000000FF)    )/255f;
				outs = new ArrayList<Pair<Integer, Float>>();
				Pair<Integer, Float> temp = new Pair<Integer, Float>();
				temp.first=settings.get(0).first;
				temp.second=r;
				outs.add(temp);
				temp = new Pair<Integer, Float>();
				temp.first=settings.get(1).first;
				temp.second=g;
				outs.add(temp);
				temp = new Pair<Integer, Float>();
				temp.first=settings.get(2).first;
				temp.second=b;
				outs.add(temp);
			}
			else{
				float val;
				if(autoValX || autoValY) val = getAuto(a, true);
				else val=settings.get(0).second/255f;
				outs = new ArrayList<Pair<Integer, Float>>();
				Pair<Integer, Float> temp = new Pair<Integer, Float>();
				temp.first=settings.get(0).first;
				temp.second=val;
				outs.add(temp);
			}
		}
		else{
			int ch=1;
			if(rgb) ch=3;
			outs = new ArrayList<Pair<Integer, Float>>();
			for(int i=0;i<ch;i++){
				Pair<Integer, Float> temp = new Pair<Integer, Float>();
				temp.first=settings.get(i).first;
				temp.second=0.0f;
				outs.add(temp);
			}
		}
	}

	private boolean isInBox(FourierAnalysis a){
		float [] points = a.getData();
		if(points==null) return false;
		int frqMin = (int) (points.length * relativeDimensions[0]);
		int frqMax = (int) (points.length * relativeDimensions[2]);
		for ( int i=frqMin; i<=frqMax; ++i ) {
			float relAmp = (points[i]);
			if ( relativeDimensions[1] < relAmp && relativeDimensions[3] > relAmp ) {
				return true;
			}
		}
		return false;
	}

	private float getAuto(FourierAnalysis a, boolean val){
		boolean flag=false;
		float hueVal=-1.0f;
		float [] points = a.getData();
		int frqMin = (int) (points.length * relativeDimensions[0]);
		int frqMax = (int) (points.length * relativeDimensions[2]);
		for ( int i=frqMin; i<=frqMax && !flag; ++i ) {
			float relAmp = (points[i]);
			if ( relativeDimensions[1] < relAmp && relativeDimensions[3] > relAmp ) {
				flag = true;
				if(val){
					if(autoValX){
						hueVal=((float)i)/(frqMax-frqMin);
					}
					if(autoValY){
						hueVal=(float) (relAmp/(relativeDimensions[3]-relativeDimensions[1]));
					}
				}
				else{
					if(autoHueX){
						hueVal=((float)i)/(frqMax-frqMin);
					}
					if(autoHueY){
						hueVal=(float) (relAmp/(relativeDimensions[3]-relativeDimensions[1]));
					}
				}
			}
		}
		return hueVal;
	}
}