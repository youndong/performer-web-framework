package com.maru.performer.framework.base.callback;


/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class DeviceAPIError extends GenericError {
	static public final int	INVALID_ARGUMENT_ERROR	= 10001;
	static public final int	IO_ERROR				= 10004;
	static public final int	NOT_FOUND_ERROR			= 10002;
	static public final int	NOT_SUPPORTED_ERROR		= 10005;
	static public final int	PENDING_OPERATION_ERROR	= 10003;
	static public final int	UNKNOWN_ERROR			= 10000;
}
