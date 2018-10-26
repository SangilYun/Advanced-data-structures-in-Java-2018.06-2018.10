package roadgraph;

import java.util.LinkedList;
import java.util.List;

import geography.GeographicPoint;

public class MapNode {
    
    private GeographicPoint pos;
    private double length;
    private double predicted;
    private List<MapEdge> mapEdge;
    
    public MapNode(GeographicPoint pos) {
        this.pos=pos;
        length=Double.MAX_VALUE;
        predicted=Double.MAX_VALUE;
        this.mapEdge= new LinkedList<>();
    }
    
    public MapNode(MapNode mn) {
        this(mn.pos);
    }
    
    public double getPredicted() {
        return this.predicted;
    }
    
    public double setPredicted(double length) {
        return this.predicted=length;
    }
    
    public List<MapNode> getNeigborNodes() {
        List<MapNode> retList= new LinkedList<>(); 
        for(MapEdge each : this.mapEdge) {
            retList.add(new MapNode(each.getEnd()));
        }        
        return retList;
    }
    
//    public boolean sdf(MapNode mn) {
//        this.pos.distance(mn.getPos());
//        return true;
//    }

    public GeographicPoint getPos() {
        return pos;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getLength() {
        return length;
    }

    public List<MapEdge> getMapEdge() {
        return mapEdge;
    }
    
    public void addMapEdge(MapEdge edge) {
        mapEdge.add(edge);
    }
    
    public String toString() {
    	return pos.toString();
//        return pos.toString()+", length : "+this.length ;
//        return pos.toString()+", A* : " + (this.length + this.predicted);
    }
    
//    @Override
//    public int hashCode() {
//        return pos.hashCode();
//    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MapNode) {
            MapNode compare = (MapNode)obj;
            return this.pos.hashCode() == compare.pos.hashCode();
        }
        return super.equals(obj);
    }
}