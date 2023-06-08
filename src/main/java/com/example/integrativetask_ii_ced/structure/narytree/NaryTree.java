package com.example.integrativetask_ii_ced.structure.narytree;

import java.util.ArrayList;

public class NaryTree<V>{

    private Node<V> root;

    public NaryTree() {
    }

    public NaryTree(Node<V> root) {
        this.root = root;
    }


    public void deleteNode(V child, V father){
        if(child.equals(root.getElement())){
            if (!root.isLeaf()){
                root = null;
            }else System.out.println("No se puede eliminar la raíz");
        }else {
            Node<V> fatherNode = root.searchElement(father);
            if (fatherNode == null) System.out.println("No se encontró");
            else fatherNode.deleteChild( child);
        }
    }

    public int weight (){
        if (root == null) return 0;
        else return root.weight();
    }
    public int treeHegiht (){
        if (root == null) return 0;
        else return root.treeHeight();
    }

    public ArrayList<Node> preOrder(){
        if (root == null) return null;
        return root.preOrder(new ArrayList<Node>());
    }

    public ArrayList<Node> postOrder(){
        if (root == null) return null;
        return root.postOrder(new ArrayList<Node>());
    }

    public void insertNode (V toInsert, V father){
        if (root == null) {
            root = new Node<>(toInsert);
        }else{
            Node<V> nodeFather = root.searchElement(father);
            if (nodeFather != null)  nodeFather.insertNode(toInsert);
            else System.out.println("No se pudo insertar, padre no existe");
        }
    }

    public Node<V> searhcElement (V element){
        if (root == null)return null;
        else{
            return this.searhcElement(element);
        }
    }

    public Node<V> getRoot() {
        return root;
    }

    public void setRoot(Node<V> root) {
        this.root = root;
    }
}
