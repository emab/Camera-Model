import java.util.ArrayList;
import java.util.List;

public class TrigonometryTest {
	public static void main(String[] args) {
		
		long x1 = -2;
		long y1 = 1;
		long x2 = 1;
		long y2 = 1;
		
		long x2sq = x2 * x2;
		long x2trig = x2sq;
		
		if (y1 > 0) {
			x2trig = (x2sq - 1) / 2;
		}
		else if (y1 < 0) {
			x2trig = (x2sq - 1) / -2;
		}
		
		double[] solutions = solve(x2trig);
		
		for (double x : solutions) {
			System.out.println(x);
		}
		
		long angle = checkSolutions(solutions,x1,y1,x2,y2);
		System.out.println(angle);
	}
	
	static double[] solve(long x) {
		double cosAns1 = Math.toDegrees(Math.acos(x));
		double cosAns2 = 360 - cosAns1;
		
		double sinAns1 = Math.toDegrees(Math.asin(x));
		double sinAns2 = 180 - sinAns1;
		if (sinAns1 > 180) {
			sinAns2 = 360 - (180 - sinAns1);
		}
		double[] ans = {cosAns1,cosAns2,sinAns1,sinAns2};
		return ans;
	}
	
	static long checkSolutions(double[] solutions, long x, long y, long expectedX, long expectedY) {
		List<Double> angles = new ArrayList<Double>();
		long angle = 404;
		for (double ang : solutions) {
			if (((x * Math.cos(Math.toRadians(ang))) - (y * Math.sin(Math.toRadians(ang))) <= expectedX + 0.0001)
					&& (x * Math.cos(Math.toRadians(ang))) - (y * Math.sin(Math.toRadians(ang))) >= expectedX - 0.0001) {
				angles.add(ang);
			}
		}
		for (double ang : angles) {
			if (((y * Math.cos(Math.toRadians(ang))) + (x * Math.sin(Math.toRadians(ang))) <= expectedY + 0.0001)
					&& (y * Math.cos(Math.toRadians(ang))) + (x * Math.sin(Math.toRadians(ang))) >= expectedY - 0.0001) {
				angle = Math.round(ang);
			}
		}
		if (angle == 404) {
			System.out.println("Error: angle not found.");
		}
		return angle;
	}
	
	double calcX(long x, long y, long theta) {
		return x * Math.cos(theta) - y * Math.sin(theta);
	}
	
	double calcY(long x, long y, long theta) {
		return y * Math.cos(theta) + x * Math.sin(theta);
	}
}
