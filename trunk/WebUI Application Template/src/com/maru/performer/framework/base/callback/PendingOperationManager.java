package com.maru.performer.framework.base.callback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class PendingOperationManager {
	private static final String				CLASS_NAME	= "PendingOperationManager";
	private static List<PendingOperation>	mOperations	= new ArrayList<PendingOperation>();

	/**
	 * @param op
	 */
	public static void add(PendingOperation op) {
		mOperations.add(op);
	}

	/**
	 * @param id
	 * @return
	 */
	public static PendingOperation find(String id) {
		for (PendingOperation _op : mOperations) {
			if (_op.getOperationID() == Long.parseLong(id)) {
				return _op;
			}
		}

		return null;
	}

	/**
	 * @return
	 */
	public static long generateID() {
		synchronized (PendingOperationManager.class) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} /* finally */
			{
				return Calendar.getInstance().getTimeInMillis();
			}
		}
	}

	/**
	 * @param op
	 */
	public static void remove(PendingOperation op) {
		for (PendingOperation _op : mOperations) {
			if (_op.getOperationID() == op.getOperationID()) {
				// if (_op.isCompleted() == true)
				{
					mOperations.remove(_op);

					try {
						_op.finalize();
					} catch (Throwable e) {
						e.printStackTrace();
					}

					return;
				}
			}
		}
	}
}
