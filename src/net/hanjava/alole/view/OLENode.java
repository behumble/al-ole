package net.hanjava.alole.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.poifs.filesystem.EntryNode;

class OLENode implements TreeNode {
    private Entry entry;

    private ArrayList children = new ArrayList();

    private TreeNode parent;

    OLENode(Entry entry, TreeNode parent) {
        this.entry = entry;
        this.parent = parent;
    }

    public TreeNode getChildAt(int index) {
        return (TreeNode) children.get(index);
    }

    public int getChildCount() {
        return children.size();
    }

    public TreeNode getParent() {
        return parent;
    }

    public int getIndex(TreeNode child) {
        return children.indexOf( child );
    }

    public boolean getAllowsChildren() {
        return true;
    }

    public boolean isLeaf() {
        return children.size()==0;
    }

    public Enumeration children() {
        return Collections.enumeration( children );
    }

    public Entry getEntry() {
        return entry;
    }

    public void addChild(OLENode child) {
        children.add( child );
    }

    public String toString() {
        return ((EntryNode)entry).getName();
    }
}