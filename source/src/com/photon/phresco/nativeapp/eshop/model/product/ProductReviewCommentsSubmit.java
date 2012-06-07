/**
 *
 */
package com.photon.phresco.nativeapp.eshop.model.product;

import java.io.Serializable;

import com.photon.phresco.nativeapp.eshop.interfaces.IPhrescoValidator;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.util.StringUtils;

/**
 * @author chandankumar_r
 *
 */
public class ProductReviewCommentsSubmit implements Serializable, IPhrescoValidator{

	private static final String TAG = "Model: ProductReviewCommentsSubmit ***** ";
	private static final long serialVersionUID = 1L;
	private float rating;
	private String comment;
	/**
	 * @return the rating
	 */
	public float getRating() {
		return rating;
	}
	/**
	 * @param f the rating to set
	 */
	public void setRating(float f) {
		this.rating = f;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Check if the required field is present, or left blank
	 *
	 * @param param
	 * @return boolean
	 */
	@Override
	public boolean isEmpty(String param) {
		StringUtils strUtilObj = new StringUtils();
		boolean isEmpty = false;
		try {
			isEmpty = strUtilObj.isEmpty(param);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - isEmpty  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
			isEmpty = true;
		}
		return isEmpty;
	}
}
