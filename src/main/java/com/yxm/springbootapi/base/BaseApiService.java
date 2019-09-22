package com.yxm.springbootapi.base;

import com.yxm.springbootapi.utils.Constants;
import org.springframework.stereotype.Component;



@Component
public class BaseApiService {

	public ResponseBase setResultError(Integer code, String msg) {
		return setResult(code, msg, null);
	}

	/**
	 * 返回错误，可以传msg
	 * @param msg
	 * @return
	 */
	public ResponseBase setResultError(String msg) {
		return setResult(Constants.HTTP_RES_CODE_500, msg, null);
	}

	/**
	 * 返回成功，可以传data值
	 * @param msg
	 * @return
	 */
	public ResponseBase setResultSuccessData(Object data) {
		return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, data);
	}

	public ResponseBase setResultSuccessData(Integer code, Object data) {
		return setResult(code, Constants.HTTP_RES_CODE_200_VALUE, data);
	}

	/**
	 * 返回成功，沒有data值
	 * @param msg
	 * @return
	 */
	public ResponseBase setResultSuccess() {
		return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, null);
	}


	/**
	 *	返回成功，沒有data值
	 * @param msg
	 * @return
	 */
	public ResponseBase setResultSuccess(String msg) {
		return setResult(Constants.HTTP_RES_CODE_200, msg, null);
	}


	/**
	 * 通用封装
	 * @param msg
	 * @return
	 */
	public ResponseBase setResult(Integer code, String msg, Object data) {
		return new ResponseBase(code, msg, data);
	}

}
