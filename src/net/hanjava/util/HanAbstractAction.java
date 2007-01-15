package net.hanjava.util;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * ��� CalcView�� �׼��� �̳��� ��� �޴´�. �ʱ�ȭ ��ƾ���� ���ԵǾ� �ִ�. ���� Ŭ�������� abstract �ѳ�鸸 ������ �ϰ�
 * String ����� NAME�� �����ϸ� �ȴ�.
 * 
 * @author accent
 */
public abstract class HanAbstractAction extends AbstractAction implements
        CheckableState.Listener {
    public static final String DISABLED_ICON = "DisabledIcon";

    public static final String ROLLOVER_ICON = "RolloverIcon";

    /**
     * �޴��� ������ ���ҽ��� Short Description�� �ٸ���. TFO�� UI�� Mnemonic�� ��ϵǾ� �־ �޴��� ��Ÿ����
     * �ʴ´�.
     */
    public static final String MENU_CAPTION = "MenuCaption";

    public static final String POPUP_MNEMONIC = "popupMnemonic";

    /** Dialog Invoker �Ӽ��� ����Ǿ��� �� �߻��ϴ� �̺�Ʈ �̸� */
    public static final String EVENT_NAME_DIALOG_INVOKER = "DialogInvoker";

    protected CheckableState[] checks;

    protected HanAbstractAction(String name, CheckableState[] checks) {
        this.checks = checks;
        if (name != null) {
            putValue(Action.NAME, name);
        }
        init();
    }

    /**
     * ���ҽ��� �о�鿩 �Ӽ����� �����Ѵ�. Checkable �鿡�� �ڽ��� �����ʷ� ����Ѵ�.
     */
    protected void init() {
        String shortDescription = getShortDescription();
        if (shortDescription != null) {
            putValue(Action.SHORT_DESCRIPTION, shortDescription);
        }

        String longDescription = getLongDescription();
        if (longDescription != null) {
            putValue(Action.LONG_DESCRIPTION, longDescription);
        }

        int mnemonic = getMnemonicKey();
        if (mnemonic > 0) {
            putValue(Action.MNEMONIC_KEY, new Integer(mnemonic));
        }

        String menuCaption = getMenuCaption();
        if (menuCaption != null) {
            putValue(HanAbstractAction.MENU_CAPTION, menuCaption);
        }

        int popupMnemonic = getPopupMnemonicKey();
        if (popupMnemonic > 0) {
            putValue(POPUP_MNEMONIC, new Integer(popupMnemonic));
        }

        // checkable�鿡�� ������ ���
        if (checks != null) {
            for (int i = 0; i < checks.length; i++) {
                checks[i].addListener(this);
            }
        }
    }

    /** Status Bar�� ���� ���� */
    protected abstract String getLongDescription();

    /** ToolTip Text */
    protected abstract String getShortDescription();

    /** �ϸ���� �������Ϸ��� ���ΰ� �Ϸ��� ������ �ϼ� */
    protected int getMnemonicKey() {
        return -1;
    }

    protected int getPopupMnemonicKey() {
        return getMnemonicKey();
    }

    /** �޴��� ǥ�õ� ĸ��. Short Description�� �ٸ� ��츸 ������ �ϸ� �ȴ�. */
    protected String getMenuCaption() {
        return null;
    }

    /** ���� ȣ������ ������. Callback �Դϴ� */
    public void needToUpdate(CheckableState checkable) {
        if (checks != null) {
            for (int i = 0; i < checks.length; i++) {
                // �ϳ��� ���� ���ϸ� disable ��Ų��.
                if (!checks[i].isSatisfied()) {
                    setEnabled(false);
                    return;
                }
            }
            setEnabled(true);
        }
    }

    /**
     * �׼��� �����ϸ� ��ȭ���ڰ� �ߴ°�? �̰� true�̸� �޴��������� label�� '...'�� �ٴ´�
     */
    public boolean isDialogInvoker() {
        return false;
    }
}