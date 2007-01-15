package net.hanjava.alole.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Storage(Stream�� �ݴ밳��)�� ���� �߰� ����.
 * OLE���� Entry�� �̸��� ������ ȭ�� �ý��ۿ� ���� ������ ���� ������ ���� ȭ���̸����� ������ �� ����.
 * �Ӹ� �ƴ϶� Storage�� ��� �߰������� Class ID�� �����Ͽ��� �ϱ� ������ ������ ȭ�Ϸ� �Űܰ� �� �߰� ���� ȭ���� �����ȴ�.
 */
public class StorageInfo {
    public static final String FILENAME = "storage.ole";

    private ArrayList<String> table = new ArrayList<String>();
    private String classID = null;

    public int addEntry(String entry) {
        int id = table.size();
        // XMLEncoder�� ���׷� ���� Ư�����ڸ� ó���� �־�� �Ѵ�.
        String encodedEntry = null;
        try {
            encodedEntry = URLEncoder.encode(entry, "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        table.add(encodedEntry);
        return id;
    }

    public String getEntry(int id) {
        String rawString = table.get(id);
        try {
            return URLDecoder.decode(rawString, "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(OutputStream os) {
        XMLEncoder encoder = new XMLEncoder(os);
        encoder.writeObject(this);
        encoder.close();
    }

    public static StorageInfo load(InputStream is) {
        XMLDecoder decoder = new XMLDecoder(is);
        StorageInfo instance = (StorageInfo) decoder.readObject();
        decoder.close();
        return instance;
    }
    
    public String getClassID() {
        return classID;
    }
    
    public void setClassID(String classID) {
        this.classID = classID;
    }
    
    public void setTable(ArrayList<String> table) {
        this.table = table;
    }
    
    public ArrayList<String> getTable() {
        return this.table;
    }
}