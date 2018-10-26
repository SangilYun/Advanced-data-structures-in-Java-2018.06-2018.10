package roadgraph;
import geography.GeographicPoint;
public class MapEdge {
 	private GeographicPoint start;
 	private GeographicPoint end;
 	private String streetName;
 	private String streetType;
 	private double length;
 	
 	public MapEdge(GeographicPoint start, GeographicPoint end, String streetName, String streetType) {
 		this.start=start;
 		this.end=end;
 		this.streetName=streetName;
 		this.streetType=streetType;
 	}
 	
 	public MapEdge(GeographicPoint start, GeographicPoint end, String streetName, String streetType, double length) {
 		this(start, end, streetName, streetType);
 		this.length = length;
 	}
 	public GeographicPoint getStart() {
 		return start;
 	}
 	public GeographicPoint getEnd() {
 		return end;
 	}
 	public String getStreetName() {
 		return streetName;
 	}
 	public double getLength() {
 		return length;
 	}
 	public String getStreetType() {
 		return streetType;
 	}
 	
 	public String toString() {
 		return "start -> " + start + ", end -> " + end; 
//				+ ", streename : " + streetName	+ ", streettype : " + streetType + ", length : " + length;
 	}
}	