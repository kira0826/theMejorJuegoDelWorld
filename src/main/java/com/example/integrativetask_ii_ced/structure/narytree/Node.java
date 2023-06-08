package com.example.integrativetask_ii_ced.structure.narytree;

import java.util.ArrayList;

public class Node<V> {

    private V element;
    private ArrayList<Node> children;

    public Node(V element) {
        this.element = element;
        children = new ArrayList<>();
    }


    public void deleteChild(V child){
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getElement().equals(child) ){
                if(children.get(i).isLeaf()) children.remove(i);
                else System.out.println("No eleiminable");
            }
        }
    }

    public int treeHeight(){
        if (isLeaf()) return 1;
        else {
            int  maxHeight = 0 ;
            for (int i = 0; i < children.size(); i++) {
                int  tempHeight = children.get(i).treeHeight();
                if (tempHeight > maxHeight) maxHeight = tempHeight;
            }
            return maxHeight +1;
        }
    }

    public int weight(){
        if (isLeaf()){
            return 1;
        }
        int acomulator = 1;
        for (int i = 0; i < children.size(); i++) {
            acomulator += children.get(i).weight();
        }
        return acomulator;
    }

    public ArrayList<Node> preOrder(ArrayList<Node> nodeList){
        if (isLeaf()){
            nodeList.add(this);
            return nodeList;
        }
        nodeList.add(this);
        for (int i = 0; i < children.size(); i++) {
            children.get(i).preOrder(nodeList);
        }
        return nodeList;
    }
    public ArrayList<Node> postOrder(ArrayList<Node> nodeList){
        if (isLeaf()){
            nodeList.add(this);
            return nodeList;
        }
        for (int i = 0; i < children.size(); i++) {
            children.get(i).postOrder(nodeList);
        }
        nodeList.add(this);
        return nodeList;
    }

    public Node<V> searchElement(V element){
        if (element.equals(this.getElement())) return this;
        if (this.isLeaf()) return null;

        for (int i  =0; i < children.size(); i++){
            Node<V> searchElement= getChildren().get(i).searchElement(element);
            if (searchElement != null) return searchElement;
        }
        return null;
    }

    public void insertNode(V toInsert){
        this.children.add(new Node(toInsert));
    }

    public boolean isLeaf(){
        if (children.size() == 0) return true;
        else return false;
    }


    public V getElement() {
        return element;
    }

    public void setElement(V element) {
        this.element = element;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }
}
