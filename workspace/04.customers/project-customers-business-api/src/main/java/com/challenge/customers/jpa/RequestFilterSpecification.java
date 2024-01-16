package com.challenge.customers.jpa;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestFilterSpecification<T> implements Specification<T> {
 
   private static final long serialVersionUID = 1L;
   private final RequestFilterParams requestFilterParams;
   
   public RequestFilterSpecification(final RequestFilterParams requestFilterParams){
      super();
      this.requestFilterParams = requestFilterParams;
   }
   
   @Override
   public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder){
	   
	   SimpleDateFormat sdfEN1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	   SimpleDateFormat sdfEN2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	   SimpleDateFormat sdfEN3 = new SimpleDateFormat("yyyy/MM/dd");
	   
	   SimpleDateFormat sdfPT1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	   SimpleDateFormat sdfPT2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	   SimpleDateFormat sdfPT3 = new SimpleDateFormat("dd/MM/yyyy");

	   SimpleDateFormat sdfPT4 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	   SimpleDateFormat sdfPT5 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	   SimpleDateFormat sdfPT6 = new SimpleDateFormat("dd-MM-yyyy");
	   
	   SimpleDateFormat sdfOracle1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   SimpleDateFormat sdfOracle2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	   SimpleDateFormat sdfOracle3 = new SimpleDateFormat("yyyy-MM-dd");
	   
	   Path<Object> obj	   	   = null;
	   String  argStringLike1  = null;
	   String  argStringEqual1 = null;
	   Date argDate1    	   = null;
	   Date    argDate2        = null;
	   Double  argNumber1      = null;
	   Double  argNumber2      = null;
	   //Boolean argBoolean1     = null;
	   
	   String[] strJoins = requestFilterParams.getKey().split("\\.");
	   if (strJoins.length == 1) {
		   obj = root.get(strJoins[0]);
	   } else if (strJoins.length == 2) {
		   obj = root.get(strJoins[0]).get(strJoins[1]);
	   } else if (strJoins.length == 3) {
		   obj = root.get(strJoins[0]).get(strJoins[1]).get(strJoins[2]);
	   } else if (strJoins.length == 4) {
		   obj = root.get(strJoins[0]).get(strJoins[1]).get(strJoins[2]).get(strJoins[3]);
	   } else if (strJoins.length == 5) {
		   obj = root.get(strJoins[0]).get(strJoins[1]).get(strJoins[2]).get(strJoins[3]).get(strJoins[4]);
	   } 
	   
	   if (obj.getJavaType().equals(Long.class) ||
		   obj.getJavaType().equals(Double.class) ||
		   obj.getJavaType().equals(Integer.class) ||
		   obj.getJavaType().equals(java.math.BigDecimal.class)) {
		   
		   argNumber1 = requestFilterParams.getArg1()==null? null: (Double.valueOf(requestFilterParams.getArg1()));
		   argNumber2 = requestFilterParams.getArg2()==null? null: (Double.valueOf(requestFilterParams.getArg2()));
	   }
	   
//	   if (obj.getJavaType().equals(java.lang.Boolean.class)) {
//		   argBoolean1 = Boolean.valueOf(requestsFilterParams.getArg1());
//	   }
	   
//	   if (obj.getJavaType().equals(java.lang.Character.class)) {
//		   //Nothing
//	   }
	   
	   if (obj.getJavaType().equals(String.class)) {
		   argStringLike1 = "%" + requestFilterParams.getArg1() + "%";
		   argStringEqual1= requestFilterParams.getArg1();
	   }
	   
      switch (requestFilterParams.getFilterOperationEnum()) {
	      case LIKE: 
	    	  return runLike(criteriaQuery, root, criteriaBuilder, strJoins, argStringLike1);
	      case EQUAL_STRING:
	    	  return runEqualString(criteriaQuery, root, criteriaBuilder, strJoins, argStringEqual1);
	      case EQUAL_NUMBER:
	    	  return runEqualNumber(criteriaQuery, root, criteriaBuilder, strJoins, argNumber1);
	      case EQUAL_DATE:
	    	  return runEqualDate(criteriaQuery, root, criteriaBuilder, strJoins, argDate1);	  
	      case BETWEEN_NUMBERS:
	    	  return runBetweenNumbers(criteriaQuery, root, criteriaBuilder, strJoins, argNumber1, argNumber2);
	      case BETWEEN_DATES:
	    	  return runBetweenDates(criteriaQuery, root, criteriaBuilder, strJoins, argDate1, argDate2);
	      case GREATER_NUMBER_THAN: 
	    	  return runGreaterNumberThan(criteriaQuery, root, criteriaBuilder, strJoins, argNumber1);
	      case GREATER_DATE_THAN: 
	    	  return runGreaterDateThan(criteriaQuery, root, criteriaBuilder, strJoins, argDate1);
	      case GREATER_NUMBER_THAN_OR_EQUAL:
	    	  return runGreaterNumberThanOrEqual(criteriaQuery, root, criteriaBuilder, strJoins, argNumber1);
	      case GREATER_DATE_THAN_OR_EQUAL:
	    	  return runGreaterDateThanOrEqual(criteriaQuery, root, criteriaBuilder, strJoins, argDate1);
	      case LESS_NUMBER_THAN:
	    	  return runLessNumberThan(criteriaQuery, root, criteriaBuilder, strJoins, argNumber1);
	      case LESS_DATE_THAN:
	    	  return runLessDateThan(criteriaQuery, root, criteriaBuilder, strJoins, argDate1);	  
	      case LESS_NUMBER_THAN_OR_EQUAL:            
	    	  return runLessNumberThanOrEqual(criteriaQuery, root, criteriaBuilder, strJoins, argNumber1);
	      case LESS_DATE_THAN_OR_EQUAL:            
	    	  return runLessDateThanOrEqual(criteriaQuery, root, criteriaBuilder, strJoins, argDate1);	  
		  default:
			  break;
      }

	   return null;
   }
 
   private Predicate runLike(CriteriaQuery<?> criteriaQuery, 
							Root<T> root, CriteriaBuilder 
							criteriaBuilder, 
							String[] strJoinArgs, 
							String argLike) {
				
		return criteriaBuilder.like(criteriaBuilder.upper(
								    strJoinArgs.length==1 ? root.get(strJoinArgs[0]): 
									strJoinArgs.length==2 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]):
									strJoinArgs.length==3 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]):
									strJoinArgs.length==4 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]):
									strJoinArgs.length==5 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]).get(strJoinArgs[4]):
									null
									),
									argLike);
	}
	
	private Predicate runEqualString(CriteriaQuery<?> criteriaQuery, 
									Root<T> root, CriteriaBuilder 
									criteriaBuilder, 
									String[] strJoinArgs, 
									String argEqualString) {
		
		return criteriaBuilder.equal(criteriaBuilder.upper(strJoinArgs.length==1 ? root.get(strJoinArgs[0]): 
									strJoinArgs.length==2 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]):
									strJoinArgs.length==3 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]):
									strJoinArgs.length==4 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]):
									strJoinArgs.length==5 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]).get(strJoinArgs[4]):
									null
									), 
									argEqualString);
	}
	private Predicate runEqualNumber(CriteriaQuery<?> criteriaQuery, 
									Root<T> root, CriteriaBuilder 
									criteriaBuilder, 
									String[] strJoinArgs, 
									Double argEqualNumber) {
		
		return criteriaBuilder.equal((strJoinArgs.length==1 ? root.get(strJoinArgs[0]): 
									strJoinArgs.length==2 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]):
									strJoinArgs.length==3 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]):
									strJoinArgs.length==4 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]):
									strJoinArgs.length==5 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]).get(strJoinArgs[4]):
									null
									), 
									argEqualNumber);
	}
	
	private Predicate runEqualDate(CriteriaQuery<?> criteriaQuery, 
									Root<T> root, CriteriaBuilder 
									criteriaBuilder, 
									String[] strJoinArgs, 
									Date argEqualDate) {
		
		return criteriaBuilder.equal((strJoinArgs.length==1 ? root.get(strJoinArgs[0]): 
									strJoinArgs.length==2 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]):
									strJoinArgs.length==3 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]):
									strJoinArgs.length==4 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]):
									strJoinArgs.length==5 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]).get(strJoinArgs[4]):
									null
									), 
									argEqualDate);
	}
	
	private Predicate runBetweenNumbers(CriteriaQuery<?> criteriaQuery, 
										Root<T> root, CriteriaBuilder 
										criteriaBuilder, 
										String[] strJoinArgs, 
										Double argNumberStarts, 
										Double argNumberEnds) {
		
		return criteriaBuilder.between((strJoinArgs.length==1 ? root.get(strJoinArgs[0]): 
										strJoinArgs.length==2 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]):
										strJoinArgs.length==3 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]):
										strJoinArgs.length==4 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]):
										strJoinArgs.length==5 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]).get(strJoinArgs[4]):
										null
										), 
										argNumberStarts, argNumberEnds);
	}	 
	
	private Predicate runBetweenDates(CriteriaQuery<?> criteriaQuery, 
									Root<T> root, CriteriaBuilder 
									criteriaBuilder, 
									String[] strJoinArgs, 
									Date argDateStars, 
									Date argDateEnds) {
				
		return criteriaBuilder.between((strJoinArgs.length==1 ? root.get(strJoinArgs[0]): 
									strJoinArgs.length==2 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]):
									strJoinArgs.length==3 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]):
									strJoinArgs.length==4 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]):
									strJoinArgs.length==5 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]).get(strJoinArgs[4]):
									null
									), 
									argDateStars, argDateEnds);
	}	  

	private Predicate runGreaterNumberThan(CriteriaQuery<?> criteriaQuery, 
											Root<T> root, CriteriaBuilder 
											criteriaBuilder, 
											String[] strJoinArgs, 
											Double argNumber) {
		
		return criteriaBuilder.greaterThan((strJoinArgs.length==1 ? root.get(strJoinArgs[0]): 
										strJoinArgs.length==2 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]):
										strJoinArgs.length==3 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]):
										strJoinArgs.length==4 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]):
										strJoinArgs.length==5 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]).get(strJoinArgs[4]):
										null
										), 
										argNumber);
	}	 

	private Predicate runGreaterNumberThanOrEqual(CriteriaQuery<?> criteriaQuery, 
													Root<T> root, CriteriaBuilder 
													criteriaBuilder, 
													String[] strJoinArgs, 
													Double argNumber) {
	
		return criteriaBuilder.greaterThanOrEqualTo((strJoinArgs.length==1 ? root.get(strJoinArgs[0]): 
										strJoinArgs.length==2 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]):
										strJoinArgs.length==3 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]):
										strJoinArgs.length==4 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]):
										strJoinArgs.length==5 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]).get(strJoinArgs[4]):
										null
										), 
										argNumber);
	}	 

	private Predicate runGreaterDateThan(CriteriaQuery<?> criteriaQuery, 
										Root<T> root, CriteriaBuilder 
										criteriaBuilder, 
										String[] strJoinArgs, 
										Date argDateStars) {
	
		return criteriaBuilder.greaterThan((strJoinArgs.length==1 ? root.get(strJoinArgs[0]): 
										strJoinArgs.length==2 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]):
										strJoinArgs.length==3 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]):
										strJoinArgs.length==4 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]):
										strJoinArgs.length==5 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]).get(strJoinArgs[4]):
										null
										), 
										argDateStars);
	}	 
	
	private Predicate runGreaterDateThanOrEqual(CriteriaQuery<?> criteriaQuery, 
												Root<T> root, CriteriaBuilder 
												criteriaBuilder, 
												String[] strJoinArgs, 
												Date argDateStars) {
	
		return criteriaBuilder.greaterThanOrEqualTo((strJoinArgs.length==1 ? root.get(strJoinArgs[0]): 
										strJoinArgs.length==2 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]):
										strJoinArgs.length==3 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]):
										strJoinArgs.length==4 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]):
										strJoinArgs.length==5 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]).get(strJoinArgs[4]):
										null
										), 
										argDateStars);
	}	

	private Predicate runLessNumberThan(CriteriaQuery<?> criteriaQuery, 
										Root<T> root, CriteriaBuilder 
										criteriaBuilder, 
										String[] strJoinArgs, 
										Double argNumber) {
	
		return criteriaBuilder.lessThan((strJoinArgs.length==1 ? root.get(strJoinArgs[0]): 
										strJoinArgs.length==2 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]):
										strJoinArgs.length==3 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]):
										strJoinArgs.length==4 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]):
										strJoinArgs.length==5 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]).get(strJoinArgs[4]):
										null
										), 
										argNumber);
	}	 
	
	private Predicate runLessNumberThanOrEqual(CriteriaQuery<?> criteriaQuery, 
												Root<T> root, CriteriaBuilder 
												criteriaBuilder, 
												String[] strJoinArgs, 
												Double argNumber) {
	
		return criteriaBuilder.lessThanOrEqualTo((strJoinArgs.length==1 ? root.get(strJoinArgs[0]): 
										strJoinArgs.length==2 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]):
										strJoinArgs.length==3 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]):
										strJoinArgs.length==4 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]):
										strJoinArgs.length==5 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]).get(strJoinArgs[4]):
										null
										), 
										argNumber);
	}	 
	
	private Predicate runLessDateThan(CriteriaQuery<?> criteriaQuery, 
										Root<T> root, CriteriaBuilder 
										criteriaBuilder, 
										String[] strJoinArgs, 
										Date argDateStars) {
		
		return criteriaBuilder.lessThan((strJoinArgs.length==1 ? root.get(strJoinArgs[0]): 
										strJoinArgs.length==2 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]):
										strJoinArgs.length==3 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]):
										strJoinArgs.length==4 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]):
										strJoinArgs.length==5 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]).get(strJoinArgs[4]):
										null
										), 
										argDateStars);
	}	 
	
	private Predicate runLessDateThanOrEqual(CriteriaQuery<?> criteriaQuery, 
											Root<T> root, CriteriaBuilder 
											criteriaBuilder, 
											String[] strJoinArgs, 
											Date argDateStars) {
		
		return criteriaBuilder.lessThanOrEqualTo((strJoinArgs.length==1 ? root.get(strJoinArgs[0]): 
										strJoinArgs.length==2 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]):
										strJoinArgs.length==3 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]):
										strJoinArgs.length==4 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]):
										strJoinArgs.length==5 ? root.get(strJoinArgs[0]).get(strJoinArgs[1]).get(strJoinArgs[2]).get(strJoinArgs[3]).get(strJoinArgs[4]):
										null
										), 
										argDateStars);
	}	

}


