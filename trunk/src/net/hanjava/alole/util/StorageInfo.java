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
 * Storage(Stream의 반대개념)에 대한 추가 정보.
 * OLE내부 Entry의 이름은 물리적 화일 시스템에 비해 제약이 적기 때문에 실제 화일이름으로 저장할 수 없다.
 * 뿐만 아니라 Storage의 경우 추가적으로 Class ID도 저장하여야 하기 때문에 물리적 화일로 옮겨갈 땐 추가 정보 화일이 생성된다.
 */
public class StorageInfo {
    public static final String FILENAME = "storage.ole";

    private ArrayList<String> table = new ArrayList<String>();
    private String classID = null;

    public int addEntry(String entry) {
        int id = table.size();
        // XMLEncoder의 버그로 인해 특수문자를 처리해 주어야 한다.
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