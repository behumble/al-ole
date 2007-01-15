package net.hanjava.util;

import java.util.ArrayList;

/**
 * isSatisfied()�� �Ź����� �ʰ� cache�ϵ��� ���� - 2005/02/13 CVAbstractAction���� �ַ� ��������� ��
 * �׷� �ʿ�� ����. �����ϴ��� �ƴ����� Ȯ���� �� �ִ� ������ �߻�ȭ �� Ŭ����. ������ ���� ��ȭ�� ������
 * fireNotification()�� �θ��� ���� ����Ŭ������ ��Ÿ �ܺο��� �˾Ƽ� �ؾ��Ѵ�.
 * 
 * @author accent
 */
public abstract class CheckableState {
    /** cached value */
    protected boolean satisfied = true;

    public interface Listener {
        void needToUpdate(CheckableState checkable);
    }

    protected ArrayList listeners = new ArrayList();

    public boolean isSatisfied() {
        // return cached value
        return satisfied;
    }

    /** ���� check�� �����ϴ� �κ�. ����� cache�� ���ϱ� �� ���� �ɷ��� �ȴ� */
    protected abstract boolean checkSatisfied();

    public void addListener(Listener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public void removeListener(Listener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    /** ���� ����Ǿ��� ���ɼ��� ������ ��������� ȣ���Ѵ�.
     * ������ ���� ������ �ʾ����� listener���� ������ ������ �ʴ´�.
     */
    public void fireNotification() {
        boolean oldValue = isSatisfied();
        satisfied = checkSatisfied();
        boolean differ = oldValue != satisfied;
        if (differ) {
            synchronized (listeners) {
                int size = listeners.size();
                for (int i = 0; i < size; i++) {
                    Listener listener = (Listener) listeners.get(i);
                    listener.needToUpdate(this);
                }
            }
        }
    }

    /** �̸��� �����Ѵ�. ������ ���Ǵ� �� ���� */
    public String getName() {
        return getClass().getName();
    }
}