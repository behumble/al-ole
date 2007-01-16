package net.hanjava.util;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

/**
 * 모든 CalcView의 액션은 이놈을 상속 받는다. 초기화 루틴들이 포함되어 있다. 하위 클래스들은 abstract 한놈들만 재정의 하고
 * String 상수로 NAME만 정의하면 된다.
 * 
 * @author accent
 */
public abstract class HanAbstractAction extends AbstractAction implements
		CheckableState.Listener {
	/**
	 * 메뉴에 나오는 리소스는 Short Description과 다르다. TFO의 UI는 Mnemonic이 등록되어 있어도 메뉴에 나타나지
	 * 않는다.
	 */
	public static final String MENU_CAPTION = "MenuCaption";

	public static final String POPUP_MNEMONIC = "popupMnemonic";

	/** Dialog Invoker 속성이 변경되었을 때 발생하는 이벤트 이름 */
	public static final String EVENT_NAME_DIALOG_INVOKER = "DialogInvoker";

	public static final String ICON_PATH = "IconPath";

	protected CheckableState[] checks;

	protected HanAbstractAction(String name, CheckableState[] checks) {
		this.checks = checks;
		if (name != null) {
			putValue(Action.NAME, name);
		}
		init();
	}

	/**
	 * 리소스를 읽어들여 속성들을 지정한다. Checkable 들에게 자신을 리스너로 등록한다.
	 */
	protected void init() {
		String iconPath = getIconPath();
		if (iconPath != null) {
			ClassLoader cl = HanAbstractAction.class.getClassLoader();
			ImageIcon icon = new ImageIcon(cl.getResource(iconPath));
			putValue(Action.SMALL_ICON, icon);
		}

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

		// checkable들에게 리스너 등록
		if (checks != null) {
			for (int i = 0; i < checks.length; i++) {
				checks[i].addListener(this);
			}
		}
	}

	/** Status Bar에 나올 설명 */
	protected abstract String getLongDescription();

	/** ToolTip Text */
	protected abstract String getShortDescription();

	protected abstract String getIconPath();

	/** 니모닉을 지정안하려면 냅두고 하려면 재정의 하셔 */
	protected int getMnemonicKey() {
		return -1;
	}

	protected int getPopupMnemonicKey() {
		return getMnemonicKey();
	}

	/** 메뉴에 표시될 캡션. Short Description과 다를 경우만 재정의 하면 된다. */
	protected String getMenuCaption() {
		return null;
	}

	/** 직접 호출하지 마세요. Callback 입니다 */
	public void needToUpdate(CheckableState checkable) {
		if (checks != null) {
			for (int i = 0; i < checks.length; i++) {
				// 하나라도 만족 못하면 disable 시킨다.
				if (!checks[i].isSatisfied()) {
					setEnabled(false);
					return;
				}
			}
			setEnabled(true);
		}
	}

	/**
	 * 액션을 수행하면 대화상자가 뜨는가? 이게 true이면 메뉴아이템의 label에 '...'이 붙는다
	 */
	public boolean isDialogInvoker() {
		return false;
	}
}