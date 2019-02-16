package pusheeneatstunasashimiandtunanigiri;

import java.util.*;

public class PusheenEatsTunaSashimiandTunaNigiri {
	static Scanner in=new Scanner(System.in);
	static int X;
	static int Y;
	static int N;
	static int xs;
	static int ys;
	static int xe;
	static int ye;
	static int t=0;
	static Position endPosition;
	static HashSet<Position> current=new HashSet<Position>();
	static HashSet<Position> next=new HashSet<Position>();
	static HashSet<Position> avoid=new HashSet<Position>();
	static HashSet<Point>wind=new HashSet<Point>();
	
	public static void main(String[] args) {
		X=in.nextInt();
		Y=in.nextInt();
		N=in.nextInt();
		xs=in.nextInt();
		ys=in.nextInt();
		xe=in.nextInt();
		ye=in.nextInt();
		if (xs!=xe || ys!=ye) {
			for(int i=0;i<N;i++) {
				Point windp=new Point (in.nextInt(),in.nextInt());
				wind.add(windp);
			}
			
			Position startPosition=new Position(xs,ys,0,0);
			current.add(startPosition);
			endPosition=new Position(xe,ye,0,0);
			go();
		}
		System.out.println(t);
	}
	
	private static void go() {
		boolean reachEnd=false;
		t=1;
		while(!reachEnd) {
			if(current.isEmpty()) {
				t=-1;
				break;
			}
			
			next=new HashSet<Position>();
			for(Position p:current) {
				reachEnd=AddNext(p);
				if (reachEnd) break;
			}
			current= next;
			t++;
		}
		
	}
	private static boolean AddNext(Position p) {
		
		if (aboutToReachEnd(p))
			return true;
		int x,y;
		// No speed change
		x=p.x+p.vx;
		y=p.y+p.vy;
		if(x>=0&&x<=X&&y>=0&&y<=Y&&notWind(p.x,p.y,x,y)) {
			Position pos=new Position(x,y,p.vx,p.vy);
			if (aboutToReachEnd(pos)) return true;
			if(!avoid.contains(pos)) {
				next.add(pos);
				avoid.add(pos);
			}
		}
		
		//X-1
		x=p.x+p.vx-1;
		y=p.y+p.vy;
		if(x>=0&&x<=X&&y>=0&&y<=Y&&notWind(p.x,p.y,x,y)) {
			Position leftPosition=new Position(x,y,p.vx-1,p.vy);
			if (aboutToReachEnd(leftPosition)) return true;
			if(!avoid.contains(leftPosition)) {
				next.add(leftPosition);
				avoid.add(leftPosition);
			}
		}
		//X+1
		x=p.x+p.vx+1;
		y=p.y+p.vy;
		if(x>=0&&x<=X&&y>=0&&y<=Y&&notWind(p.x,p.y,x,y)) {
			Position rightPosition=new Position(x,y,p.vx+1,p.vy);
			if (aboutToReachEnd(rightPosition)) return true;
			if(!avoid.contains(rightPosition)) {
				next.add(rightPosition);
				avoid.add(rightPosition);
			}
		}
		//Y+1
		x=p.x+p.vx;
		y=p.y+p.vy+1;
		if(x>=0&&x<=X&&y>=0&&y<=Y&&notWind(p.x,p.y,x,y)) {
			Position upPosition=new Position(x,y,p.vx,p.vy+1);
			if (aboutToReachEnd(upPosition)) return true;
			if(!avoid.contains(upPosition)) {
				next.add(upPosition);
				avoid.add(upPosition);
			}
		}
		//Y-1
		x=p.x+p.vx;
		y=p.y+p.vy-1;
		if(x>=0&&x<=X&&y>=0&&y<=Y&&notWind(p.x,p.y,x,y)) {
			Position downPosition=new Position(x,y,p.vx,p.vy-1);
			if (aboutToReachEnd(downPosition)) return true;
			if(!avoid.contains(downPosition)) {
				next.add(downPosition);
				avoid.add(downPosition);
			}
		}
		return false;
		
	}
	private static boolean aboutToReachEnd(Position pos) {
		// TODO Auto-generated method stub
		if (pos.x!=xe || pos.y!=ye) return false;
		int vdis=Math.abs(pos.vx)+Math.abs(pos.vy);
		return vdis<=1;
	}

	public static boolean notWind(int XS,int YS,int XE, int YE) {
		int Xdistance=XE-XS;
		int Ydistance=YE-YS;
		if(Xdistance==0&&Ydistance==0) {
			return true;
		}
		else if(Ydistance==0) {
			int x=XS;
			int xplus=(Xdistance>0?1:-1);
			while(x!=XE) {
				x+=xplus;
				Point checkpoint=new Point(x,YS);
				if(wind.contains(checkpoint))
					return false;
			}
		}
		else if(Xdistance==0) {
			int y=YS;
			int yplus=(Ydistance>0?1:-1);
			while(y!=XE) {
				y+=yplus;
				Point checkpoint=new Point(XS,y);
				if(wind.contains(checkpoint))
					return false;
			}
		}
		int Max=findMax(Xdistance,Ydistance);
		int xplus=Xdistance/Max;
		int yplus=Ydistance/Max;
		int x=XS;
		int y=YS;
		while(x!=XE&&y!=YE) {
			x+=xplus;
			y+=yplus;
			Point checkpoint=new Point(x,y);
			if(wind.contains(checkpoint))
				return false;
			
		}
		if(wind.contains(new Point(XE,YE)))
			return false;
		return true;
	}
	public static int findMax(int a, int b) {
		int x=Math.max(Math.abs(a), Math.abs(b));
		int y=Math.min(Math.abs(a), Math.abs(b));
		int f,l;
		while(y!=0) {
			f=x/y;
			l=x-f*y;
			x=y;
			y=l;
		}
		return x;
		
	}

}
class Position{
	int x;
	int y;
	int vx;
	int vy;
	public Position(int x,int y,int vx,int vy) {
		this.x=x;
		this.y=y;
		this.vx=vx;
		this.vy=vy;
	}
	@Override
	public boolean equals(Object o) {
		Position other=(Position)o;
		return this.x==other.x&&this.y==other.y&&this.vx==other.vx&&this.vy==other.vy;
		
	}
	@Override
	public int hashCode() {
		int hash=17;
		hash=73*hash+this.x;
		hash=73*hash+this.y;
		hash=73*hash+this.vx;
		hash=73*hash+this.vy;
		return hash;
		
	}
}
class Point{
	int x;
	int y;
	public Point(int x,int y) {
		this.x=x;
		this.y=y;
	}
	@Override
	public boolean equals(Object o) {
		Point other=(Point)o;
		return this.x==other.x&&this.y==other.y;
	}
	@Override
	public int hashCode() {
		int hash=17;
		hash=73*hash+this.x;
		hash=73*hash+this.y;
		return hash;
	}
}