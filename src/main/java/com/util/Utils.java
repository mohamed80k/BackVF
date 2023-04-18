package com.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.poi.ss.usermodel.Workbook;
import org.modelmapper.ModelMapper;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public class Utils {

	/** map List to List **/
	public static <T, U> List<U> map(ModelMapper mapper, final Collection<T> source, final Class<U> destType) {

		final List<U> dest = new ArrayList<>();

		for (T element : source) {
			dest.add(mapper.map(element, destType));
		}

		return dest;
	}

	/** generate error **/
	public static List<String> fromBindingErrors(Errors errors) {
		List<String> validErrors = new ArrayList<>();
		for (ObjectError objectError : errors.getAllErrors()) {
			validErrors.add(objectError.getDefaultMessage());
		}
		return validErrors;
	}

	/** download file **/
	public static void upResponse(Workbook workbook, HttpServletResponse response) {
		try {
			if (workbook != null) {
				response.setHeader("Content-disposition", "attachment; filename=report.xlsx");
				workbook.write(response.getOutputStream());
				workbook.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** validate dto **/
	public static <T> List<String> validateDto(T dto) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<T>> violations = validator.validate(dto);
		List<String> errors = new ArrayList<String>();
		
		for (ConstraintViolation<T> violation : violations) {
			errors.add(violation.getMessage());
		}
		
		return errors;
	}
	
	/** add millisecond to date **/
	public static Date addMillisecond(Date date, Integer millisecond) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MILLISECOND, millisecond);
		return cal.getTime();
	}

	/** get date only **/
	public static Date removeTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/** get timezone from date **/
	public static int getTimeZoneOffSet(Date date) {
		int timeZoneOffSet = TimeZone.getTimeZone("Africa/Casablanca").getOffset(date.getTime());
		return timeZoneOffSet;
	}
 
}
