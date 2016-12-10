package com.exam.tbz.q1.parser;

/**
 * Statement expression related exception, mainly parsing issues
 *
 */
public class StatementException extends RuntimeException {

	private static final long serialVersionUID = 4157889010662739773L;
	
	public static final String CODE_PREFIX  = "ERR-";
	
	public static String makeErrCode(int errCode) {
		return String.format(CODE_PREFIX + "%03d: ", errCode);
	} 
	
	public StatementException(int errCode, String message) {
		super(makeErrCode(errCode) + message);
	}
}
