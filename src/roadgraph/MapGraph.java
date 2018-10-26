/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;



/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
    //TODO: Add your member variables here in WEEK 3
    private HashMap<GeographicPoint,MapNode> map;
    
    /** 
     * Create a new empty MapGraph 
     */
    public MapGraph()
    {
        // TODO: Implement in this constructor in WEEK 3
        map=new HashMap<GeographicPoint,MapNode>();
    }
    
    /**
     * Get the number of vertices (road intersections) in the graph
     * @return The number of vertices in the graph.
     */
    public int getNumVertices()
    {
        //TODO: Implement this method in WEEK 3
        return map.size();
    }
    
    /**
     * Return the intersections, which are the vertices in this graph.
     * @return The vertices in this graph as GeographicPoints
     */
    public Set<GeographicPoint> getVertices()
    {
        //TODO: Implement this method in WEEK 3
//        HashSet<GeographicPoint> retHashSet = new HashSet<>();
//        for(GeographicPoint each : map.keySet()) {
//            retHashSet.add(new GeographicPoint(each.getX(),each.getY()));
//        }
        return map.keySet();
    }
    
    /**
     * Get the number of road segments in the graph
     * @return The number of edges in the graph.
     */
    public int getNumEdges()
    {
        //TODO: Implement this method in WEEK 3
        int totalNumEdges=0;
        for(MapNode each : map.values()) {
            totalNumEdges+=each.getMapEdge().size();
        }
        return totalNumEdges;
    }

    
    
    /** Add a node corresponding to an intersection at a Geographic Point
     * If the location is already in the graph or null, this method does 
     * not change the graph.
     * @param location  The location of the intersection
     * @return true if a node was added, false if it was not (the node
     * was already in the graph, or the parameter is null).
     */
    public boolean addVertex(GeographicPoint location)
    {
        // TODO: Implement this method in WEEK 3
        if(location ==null || map.containsKey(location)) {
            return false;
        }
        map.put(location, new MapNode(location));
        return true;
    }
    
    /**
     * Adds a directed edge to the graph from pt1 to pt2.  
     * Precondition: Both GeographicPoints have already been added to the graph
     * @param from The starting point of the edge
     * @param to The ending point of the edge
     * @param roadName The name of the road
     * @param roadType The type of the road
     * @param length The length of the road, in km
     * @throws IllegalArgumentException If the points have not already been
     *   added as nodes to the graph, if any of the arguments is null,
     *   or if the length is less than 0.
     */
    public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
            String roadType, double length) throws IllegalArgumentException {

        //TODO: Implement this method in WEEK 3
        if(from==null || to==null || roadName==null || roadType==null || length <0 ||
                !map.containsKey(from) || !map.containsKey(to)) {
            throw new IllegalArgumentException();
        }else {
            MapNode node = map.get(from);
//            System.out.println("\nfrom : "+ from+", to : "+to + " roadname : "+roadName+", roadtype : " + roadType
//                    +", length : " + length);
//            List<MapEdge> list = node.getMapEdge();
//            list.add(new MapEdge(from, to, roadName, roadType, length));
            node.addMapEdge(new MapEdge(from, to, roadName, roadType, length));
//            System.out.println("list : " + list);

        }    
    }
    

    /** Find the path from start to goal using breadth first search
     * 
     * @param start The starting location
     * @param goal The goal location
     * @return The list of intersections that form the shortest (unweighted)
     *   path from start to goal (including both start and goal).
     */
    public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
        // Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
    }
    
    /** Find the path from start to goal using breadth first search
     * 
     * @param start The starting location
     * @param goal The goal location
     * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
     * @return The list of intersections that form the shortest (unweighted)
     *   path from start to goal (including both start and goal).
     */
    public List<GeographicPoint> bfs(GeographicPoint start, 
                                      GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
    {
        // TODO: Implement this method in WEEK 3
        if(start==null||goal==null)
            throw new NullPointerException("cannot find route from or to null node");
        //Initialize
        Queue<MapNode> queue = new LinkedList<>();
        HashMap<MapNode,MapNode> parent = new HashMap<>();
        HashSet<GeographicPoint> visited = new HashSet<>();	
        //BFS
        queue.add(map.get(start));
        visited.add(start);
        MapNode curr=null;
        while(!queue.isEmpty()) {
//            System.out.println("\nqueue : " + queue);
            curr = queue.remove();
            nodeSearched.accept(curr.getPos());
            if(curr == map.get(goal)) {          	
//                System.out.println("break;");
            	break;
            }
            List<MapEdge> edgeList = curr.getMapEdge();
            for(MapEdge edge : edgeList) {
//            	System.out.println("sieze of edgeList : " + edgeList.size());
//                System.out.println("edge : " + edge);
//                System.out.println("visited : " + visited);
                if(!visited.contains(edge.getEnd())) {
                    MapNode neighborNode = map.get(edge.getEnd());
//                    System.out.println("========================================");
//                    System.out.println("neighborNode : "+neighborNode+", curr : " + curr);
//                    System.out.println("neighborNode.getPost() : " + neighborNode.getPos());
                    visited.add(neighborNode.getPos());
                    parent.put(neighborNode,curr);
//                    for(MapNode eachMap : parent.keySet()) {
//                    	System.out.println("key : " + eachMap +", value : " + parent.get(eachMap));
//                    }
//                    System.out.println("========================================");
                    queue.add(neighborNode);
                }
            }        
        }
//        System.out.println("broke");
        if (!curr.equals(map.get(goal))) {
//			System.out.println("No path found from " +start+ " to " + goal);
			return null;
        }
//        System.out.println("found");
        //reconstruct the path
        for(MapNode eachMap : parent.keySet()) {
//        	System.out.println("key : " + eachMap +", value : " + parent.get(eachMap));
        }
        LinkedList<GeographicPoint> path = reconstruct(start, goal, parent);
        return path; 
    }
    

    /** Find the path from start to goal using Dijkstra's algorithm
     * 
     * @param start The starting location
     * @param goal The goal location
     * @return The list of intersections that form the shortest path from 
     *   start to goal (including both start and goal).
     */
    public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
        // Dummy variable for calling the search algorithms
        // You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
    }
    
    /** Find the path from start to goal using Dijkstra's algorithm
     * 
     * @param start The starting location
     * @param goal The goal location
     * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
     * @return The list of intersections that form the shortest path from 
     *   start to goal (including both start and goal).
     */
    public List<GeographicPoint> dijkstra(GeographicPoint start, 
                                          GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
    {
        // TODO: Implement this method in WEEK 4
        //Initialize
        PriorityQueue<MapNode> pq = new PriorityQueue<>(5, new Comparator<MapNode>() {
            @Override
            public int compare(MapNode o1, MapNode o2) {
                return Double.compare(o1.getLength(), o2.getLength());
            }
        });
        HashSet<MapNode> visited = new HashSet<>();
        HashMap<MapNode,MapNode> parent = new HashMap<>();
        MapNode startNode = map.get(start);
        MapNode goalNode = map.get(goal);
        
        boolean found = performDijkstra(startNode, goalNode, nodeSearched, pq, visited, parent);
        if(!found) {
//            System.out.println("No path exists");
            return new LinkedList<GeographicPoint>();
        }
        
        //reconstruct the path
        LinkedList<GeographicPoint> path = reconstruct(start, goal, parent);
        return path;        
    }
    
    public boolean performDijkstra(MapNode startNode, MapNode goalNode,
            Consumer<GeographicPoint> nodeSearched, PriorityQueue<MapNode> pq,
            HashSet<MapNode> visited, HashMap<MapNode,MapNode> parent) {
        
        boolean found=false;
        int count = 0;
        startNode.setLength(0);
        pq.add(startNode);
//        System.out.println("pq : "+pq);
        while(!pq.isEmpty()) {
            MapNode curr = pq.poll();
            count++;
//            System.out.println("================================================");
//            System.out.println("curr : " + curr);
//            System.out.println("count : " + count);
            nodeSearched.accept(curr.getPos());
            if(!visited.contains(curr)) {
                visited.add(curr);
            
                if(curr == goalNode) {
                    found=true;
                    break;
                }
                for(MapEdge neighbor : curr.getMapEdge()) {
                    //get mapNode from GeographicPoint
                    MapNode neighborNode = map.get(neighbor.getEnd());
                    if(!visited.contains(neighborNode)) {
//                        System.out.println("edgeLength : "+neighbor.getLength());
//                        System.out.println("edgeName : " + neighbor.getStreetName());
//                        System.out.println("endNode : " + neighbor.getEnd());

                        if(curr.getLength()+neighbor.getLength()<neighborNode.getLength()) {
                            neighborNode.setLength(curr.getLength()+neighbor.getLength());
                            parent.put(neighborNode, curr);
                            pq.add(neighborNode);
//                            System.out.println("after adding :" + pq);
//                            System.out.println();
                        }
                    }
                }
            }
        }
//        System.out.println("dijkstra total count : " + count);
        if(!found) 
            return false;
        else
            return true;
    }
    
    /** Find the path from start to goal using A-Star search
     * 
     * @param start The starting location
     * @param goal The goal location
     * @return The list of intersections that form the shortest path from 
     *   start to goal (including both start and goal).
     */
    public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
        // Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
    }
    
    /** Find the path from start to goal using A-Star search
     * 
     * @param start The starting location
     * @param goal The goal location
     * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
     * @return The list of intersections that form the shortest path from 
     *   start to goal (including both start and goal).
     */
    public List<GeographicPoint> aStarSearch(GeographicPoint start, 
                                             GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
    {
        // TODO: Implement this method in WEEK 4
                //Initialize
                PriorityQueue<MapNode> pq = new PriorityQueue<>(5, new Comparator<MapNode>() {
                    @Override
                    public int compare(MapNode o1, MapNode o2) {
                        
                        return Double.compare(o1.getLength()+o1.getPredicted(), 
                                o2.getLength()+o2.getPredicted());
                    }
                });
                HashSet<MapNode> visited = new HashSet<>();
                HashMap<MapNode,MapNode> parent = new HashMap<>();
                MapNode startNode = map.get(start);
                MapNode goalNode = map.get(goal);
                
                boolean found = performAStar(startNode, goalNode, nodeSearched, pq, visited, parent);
                if(!found) {
//                    System.out.println("No path exists");
                    return new LinkedList<GeographicPoint>();
                }
                
                //reconstruct the path
                LinkedList<GeographicPoint> path = reconstruct(start, goal, parent);
                return path;    
    }
    
    public boolean performAStar(MapNode startNode, MapNode goalNode,
            Consumer<GeographicPoint> nodeSearched, PriorityQueue<MapNode> pq,
            HashSet<MapNode> visited, HashMap<MapNode,MapNode> parent) {
        int count=0;
        boolean found=false;
        startNode.setLength(0);
        startNode.setPredicted(startNode.getPos().distance(goalNode.getPos()));
        pq.add(startNode);
//        System.out.println("pq : "+pq);
        while(!pq.isEmpty()) {
            MapNode curr = pq.poll();
            count++;
//            System.out.println("================================================");
//            System.out.println("curr : " + curr);
            nodeSearched.accept(curr.getPos());
            if(!visited.contains(curr)) {
                visited.add(curr);
                
                if(curr == goalNode) {
                    found=true;
                    break;
                }
                for(MapEdge neighbor : curr.getMapEdge()) {
                    //get mapNode from GeographicPoint
                    MapNode neighborNode = map.get(neighbor.getEnd());
                    if(!visited.contains(neighborNode)) {
//                        System.out.println("edgeLength : "+neighbor.getLength());
//                        System.out.println("edgeName : " + neighbor.getStreetName());
//                        System.out.println("endNode : " + neighbor.getEnd());
                        double currLength = curr.getLength();
                        double neighborLength = neighbor.getLength()+currLength;
                        double currToGoal = curr.getPos().distance(goalNode.getPos());
                        double neighborToGoal = neighborNode.getPos().distance(goalNode.getPos());
                        
                        if(currLength+currToGoal <= neighborLength+neighborToGoal) {
                            //where the problem occur
                            neighborNode.setLength(neighborLength);
                            neighborNode.setPredicted(neighborToGoal);
                            parent.put(neighborNode, curr);
                            pq.add(neighborNode);
//                            System.out.println("after adding :" + pq);
//                            System.out.println();
                        }
                    }
                }
            }
        }
//        System.out.println("A* total count : " + count);
        if(!found) 
            return false;
        else
            return true;
    }
    
    public LinkedList<GeographicPoint> reconstruct(
            GeographicPoint start, GeographicPoint goal,HashMap<MapNode,MapNode> parent) {
        LinkedList<GeographicPoint> path = new LinkedList<>();
//        System.out.println("reconstructing...");
        GeographicPoint curr = goal;
        while(!curr.equals(start)) {
//            System.out.println("path : " + path);
        	path.addFirst(curr);
            curr=(parent.get(map.get(curr))).getPos();
        }
        path.addFirst(start);
        
        return path; 
    }

    public static void main(String[] args)
    {
        
//        System.out.print("Making a new map...");
//        MapGraph firstMap = new MapGraph();
//        System.out.print("DONE. \nLoading the map...");
//        GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
//        System.out.println("DONE.");
//        
////        System.out.println("getVertices : "+firstMap.getNumVertices());
////        System.out.println("getEdges : "+firstMap.getNumEdges());
//        System.out.println("bfs : " );
//        System.out.println(firstMap.bfs(new GeographicPoint(1, 1), new GeographicPoint(8, -1)));
//        System.out.println(firstMap.dijkstra(new GeographicPoint(1, 1), new GeographicPoint(8, -1)));
//        
        // You can use this method for testing.  
        
        
        /* Here are some test cases you should try before you attempt 
         * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
         * programming assignment.
         */

//        MapNode test = new MapNode(new GeographicPoint(1, 1));
//        test.setLength(10);
//        System.out.println("test : " + test);
//        
//        MapNode test2 = new MapNode(test);
//        System.out.println("test2 : " + test2);
//        
//        System.out.println("test.equals(test2)"+test.equals(test2));
//        
//        MapGraph simpleTestMap = new MapGraph();
//        GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
//        
//        GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
//        GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
//        
//        System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
//        List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
//        List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);        
//        
//        MapGraph testMap = new MapGraph();
//        GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
//        
//        
//        // A very simple test using real data
//        System.out.println("********************************************************");
//        testStart = new GeographicPoint(32.869423, -117.220917);
//        testEnd = new GeographicPoint(32.869255, -117.216927);
//        System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
//        testroute = testMap.dijkstra(testStart,testEnd);
//        testroute2 = testMap.aStarSearch(testStart,testEnd);
        
        
        
        // A slightly more complex test using real data
//        System.out.println("********************************************************");
//        testStart = new GeographicPoint(32.8674388, -117.2190213);
//        testEnd = new GeographicPoint(32.8697828, -117.2244506);
//        System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
//        testroute = testMap.dijkstra(testStart,testEnd);
//        testroute2 = testMap.aStarSearch(testStart,testEnd);

        
        
        /* Use this code in Week 3 End of Week Quiz */
        
//        System.out.println("********quiz********");
//        MapGraph theMap = new MapGraph();
//        System.out.print("DONE. \nLoading the map...");
//        GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
//        System.out.println("DONE.");
//
//        GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
//        GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
//        
//        
//        List<GeographicPoint> route = theMap.dijkstra(start,end);
//        List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
        
        
    }
    
}