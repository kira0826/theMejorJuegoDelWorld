package com.example.integrativetask_ii_ced.model.map;


import com.example.integrativetask_ii_ced.model.drawing.Coordinate;
import com.example.integrativetask_ii_ced.model.drawing.Drawable;
import com.example.integrativetask_ii_ced.model.drawing.HitBox;
import com.example.integrativetask_ii_ced.model.entities.objects.functional.PressurePlate;
import com.example.integrativetask_ii_ced.structure.graph.AdjencyListGraph;
import javafx.scene.canvas.GraphicsContext;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameMap implements Drawable {

    private ArrayList<ArrayList<MapNode>> mapGuide;
     private AdjencyListGraph<Coordinate> graph;

    private double width;
    private double height;

    private  double nodeSize;

    private double chunkSize;

    private CopyOnWriteArrayList<MapNode> nodeNoNavigable = new CopyOnWriteArrayList<>();

    @Override
    public void draw(GraphicsContext gc) {
        for (ArrayList<MapNode> mapNodes : mapGuide) {
            for (MapNode mapNode : mapNodes) {
                mapNode.draw(gc);
            }
        }
    }

    public GameMap(double width, double height, double nodeSize, double chunkSize) {
        this.width = width;
        this.height = height;
        this.nodeSize = nodeSize;
        this.mapGuide = new ArrayList<>();
        this.graph=  new AdjencyListGraph<>(false,false);
        this.chunkSize = chunkSize;
    }

    public CopyOnWriteArrayList<PressurePlate> creatingPressurePlates(CopyOnWriteArrayList<PressurePlate> pressurePlates) {
        Random random = new Random();
        for (int k = 0; k < 3; k++) {
            for (int i = 2; i < height / nodeSize; i += chunkSize+4) {
                double yRange = i + chunkSize;
                for (int j = 4; j < width / nodeSize; j += chunkSize+3) {
                    int rowNodeSelection= 0;
                    int columnNodeSelection = 0;
                    while(true){
                        rowNodeSelection = random.nextInt((int) yRange - i) + i;
                        columnNodeSelection = random.nextInt((int) chunkSize) + j;
                        if (!((rowNodeSelection>2 && rowNodeSelection <6) && (columnNodeSelection >5 && columnNodeSelection <9))) break;
                    }
                    if (getMapGuide().get(rowNodeSelection).get(columnNodeSelection).isNavigable()) {
                        pressurePlates.add(new PressurePlate(
                                getMapGuide().get(rowNodeSelection).get(columnNodeSelection).getPosition().getX(),
                                getMapGuide().get(rowNodeSelection).get(columnNodeSelection).getPosition().getY()
                        ));
                    }
                    if ( pressurePlates.size() == 4) return pressurePlates;
                }
            }
        }
        return pressurePlates;
    }


    public void initialFillingOfMapWithNodesAndCoordinates(){
        double yPosition = -40;

        for (int i = 0; i < height/nodeSize; i++) {

            yPosition+= nodeSize;
            double xPosition= -40;
            mapGuide.add(new ArrayList<MapNode>());

            for (int j = 0; j < width/nodeSize ; j++) {
                xPosition+= nodeSize ;

                MapNode node =  new MapNode(xPosition, yPosition, true, true);
                new Thread(node).start();
                getMapGuide().get(i).add(node);
            }
        }
    }


    public MapNode associateMapNode(double x, double y){
        MapNode temporal = new MapNode();
        double distance = Double.MAX_VALUE;

        for (int i = 0; i < height/nodeSize ; i++) {
            for (int j = 0; j < width / nodeSize; j++) {
                MapNode actual = getMapGuide().get(i).get(j);
                if (!actual.navigable) continue;
                double pitagoras =
                        Math.sqrt(Math.pow(actual.getPosition().getX() - x ,2) +
                                Math.pow(actual.getPosition().getY() - y,2));
                if (pitagoras <= distance) {
                    temporal = actual;
                    distance = pitagoras;
                }
            }
        }
        return temporal;
    }

    public Stack<Coordinate> shortestPathUsingListAdjacencyBFS(Coordinate from, Coordinate to){

        return graph.bfsForOneNode(from, to);

    }

    public Stack<Coordinate> shortestPathUsingListAdjacencyDFS(Coordinate from, Coordinate to){

        return graph.dfsForOneNode(from, to);
    }




    public void creatingNotNavigableObstacles( ){

        Random random  =new Random();
        for (int i = 0; i < height/nodeSize; i+=chunkSize) {
            double yRange = i + chunkSize;
            for (int j = 0; j < width / nodeSize; j+=chunkSize) {

                if ((i >2 && i< 6) && (j<9 && j>5)) continue;
                
                int rowNodeSelection = random.nextInt(i, (int) yRange);
                int columnNodeSelection = random.nextInt(j, j+ (int)chunkSize);
                getMapGuide().get(rowNodeSelection).get(columnNodeSelection).setNavigable(false);
                nodeNoNavigable.add(getMapGuide().get(rowNodeSelection).get(columnNodeSelection));

                int blocks = 1;
                while(blocks <= chunkSize-2){

                    switch (random.nextInt(0,5)){
                        case 0: // up
                            if (rowNodeSelection -1 >=i) {
                                getMapGuide().get(rowNodeSelection -1).get(columnNodeSelection).setNavigable(false);
                                nodeNoNavigable.add(getMapGuide().get(rowNodeSelection -1).get(columnNodeSelection));
                                blocks++;
                            }
                            break;
                        case 1: // down
                            if (rowNodeSelection + 1 < yRange) {
                                getMapGuide().get(rowNodeSelection + 1).get(columnNodeSelection).setNavigable(false);
                                nodeNoNavigable.add(getMapGuide().get(rowNodeSelection + 1).get(columnNodeSelection));
                                blocks++;
                            }
                            break;
                        case 2: // left
                            if (columnNodeSelection -1 >= j) {
                                getMapGuide().get(rowNodeSelection ).get(columnNodeSelection-1).setNavigable(false);
                                nodeNoNavigable.add(getMapGuide().get(rowNodeSelection ).get(columnNodeSelection-1));
                                blocks++;
                            }
                            break;
                        case 3: // Right
                            if (columnNodeSelection +1 < j + chunkSize) {
                                getMapGuide().get(rowNodeSelection ).get(columnNodeSelection + 1).setNavigable(false);
                                nodeNoNavigable.add(getMapGuide().get(rowNodeSelection ).get(columnNodeSelection + 1));
                                blocks++;
                            }
                            break;

                    }

                }
            }
        }
    }
    
    public void establishGraphMapRepresentationForMinimumPaths(){

        Set<Coordinate> coordinateSet = new HashSet<>();
        for (int i = 0; i < height/nodeSize ; i++) {
            for (int j = 0; j < width/nodeSize; j++) {

                Coordinate actualNode = new Coordinate();
                actualNode.setX(getMapGuide().get(i).get(j).getPosition().getX());
                actualNode.setY(getMapGuide().get(i).get(j).getPosition().getY());

                if (getMapGuide().get(i).get(j).isNavigable() && !coordinateSet.contains(actualNode) ){
                    graph.insertVertex(actualNode);
                    coordinateSet.add(actualNode);
                }

                if (getMapGuide().get(i).get(j).isNavigable() && j != width/nodeSize -1 ){

                    if (getMapGuide().get(i).get(j+1).isNavigable()){

                        Coordinate coordinate = new Coordinate();
                        coordinate.setX(getMapGuide().get(i).get(j+1).getPosition().getX());
                        coordinate.setY(getMapGuide().get(i).get(j+1).getPosition().getY());
                        graph.insertVertex(coordinate);
                        coordinateSet.add(coordinate);
                        graph.insertEdge(actualNode,coordinate);

                    }
                }

                if (i == 0) continue;

                if (getMapGuide().get(i).get(j).isNavigable() && getMapGuide().get(i-1).get(j).isNavigable()) {

                    Coordinate coordinate = new Coordinate();
                    coordinate.setX(getMapGuide().get(i-1).get(j).getPosition().getX());
                    coordinate.setY(getMapGuide().get(i-1).get(j).getPosition().getY());

                    if (!coordinateSet.contains(coordinate)){
                        graph.insertVertex(coordinate);
                        coordinateSet.add(coordinate);
                    }
                        graph.insertEdge(actualNode,coordinate);

                    }
                }
            }
        }




    public boolean mapCollision(HitBox hitBox){

        Coordinate coordinateUp= new Coordinate(hitBox.getX0(), hitBox.getY0());
        Coordinate coordinateDown = new Coordinate(hitBox.getX1(), hitBox.getY1());
        List<Coordinate> coordinates = new ArrayList<>();

        coordinates.add( coordinateDown);
        coordinates.add( coordinateUp);
        coordinates.add(new Coordinate(hitBox.getX1(),hitBox.getY0()));
        coordinates.add(new Coordinate(hitBox.getX0(),hitBox.getY1()));

        boolean isCollision = false;
        for (Coordinate coordinate: coordinates
             ) {
            int yPosition = (int)  Math.floor(coordinate.getY() / getNodeSize());
            int xPosition  = (int) Math.floor(coordinate.getX() / getNodeSize());

            isCollision = !getMapGuide().get(yPosition).get(xPosition).isNavigable() || isCollision;
            if(isCollision == true) return true;
        }
        return false;
    }


    public boolean mapLimit(HitBox hitBox){

        if (hitBox.getX0() < 3 || hitBox.getY0()< 3
                || hitBox.getX1() >getWidth()-3 || hitBox.getY1()> getHeight()-3 ) return true;
        return false;


    }
    public ArrayList<ArrayList<MapNode>> getMapGuide() {
        return mapGuide;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setMapGuide(ArrayList<ArrayList<MapNode>> mapGuide) {
        this.mapGuide = mapGuide;
    }

    public AdjencyListGraph<Coordinate> getGraph() {
        return graph;
    }

    public void setGraph(AdjencyListGraph<Coordinate> graph) {
        this.graph = graph;
    }

    public double getNodeSize() {
        return nodeSize;
    }

    public void setNodeSize(double nodeSize) {
        this.nodeSize = nodeSize;
    }

    public double getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(double chunkSize) {
        this.chunkSize = chunkSize;
    }

}
