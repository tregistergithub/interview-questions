package com.exam.tbz.q2;

import java.util.Date;

public interface DateService {
	default Date getDate() {
		return new Date();
	}
}
