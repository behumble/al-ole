package net.hanjava.util;

import java.util.ArrayList;

/**
 * isSatisfied()를 매번하지 않고 cache하도록 수정 - 2005/02/13 CVAbstractAction에서 주로 사용하지만 꼭
 * 그럴 필요는 없다. 만족하는지 아닌지를 확인할 수 있는 조건을 추상화 한 클래스. 하지만 값의 변화가 있을때
 * fireNotification()을 부르는 것은 서브클래스나 기타 외부에서 알아서 해야한다.
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

    /** 실제 check를 실행하는 부분. 결과를 cache할 꺼니까 좀 오래 걸려도 된다 */
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

    /** 값이 변경되었을 가능성이 있을때 명시적으로 호출한다.
     * 실제로 값이 변하지 않았으면 listener에게 공지가 가지는 않는다.
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

    /** 이름을 리턴한다. 아직은 사용되는 곳 없다 */
    public String getName() {
        return getClass().getName();
    }
}