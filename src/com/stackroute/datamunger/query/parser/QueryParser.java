package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryParser {

	private QueryParameter queryParameter = new QueryParameter();
	
	String baseQuery;
	/*
	 * this method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
	public QueryParameter parseQuery(String queryString) {
	
		/*
		 * extract the name of the file from the query. File name can be found after the
		 * "from" clause.
		 */
		if(queryString.contains(" where ")) {
			queryParameter.setQUERY_TYPE("WHERE_CLAUSE_QUERY");
		}
		else {
			queryParameter.setQUERY_TYPE("SIMPLE_QUERY");
		}
		
		baseQuery = queryString.split("where|order by|group by")[0].trim();
		//String file = baseQuery.split("from")[1].trim().split("\\s+")[0].trim();
		queryParameter.setBaseQuery(baseQuery);
		queryParameter.setFileName(getFileName(queryString));
		queryParameter.setRestrictions(getRestrictions(queryString));
		queryParameter.setFields(getFields(queryString));
		queryParameter.setLogicalOperators(getLogicalOperators(queryString));
		queryParameter.setAggregateFunctions(getAggregateFunctions(queryString));
		queryParameter.setGroupByFields(getGroupByFields(queryString));
		queryParameter.setOrderByFields(getOrderByFields(queryString));
		groupByOrderBy(queryString);
		return queryParameter;
	}
		
		private String getFileName(String queryString) {
		// TODO Auto-generated method stub
			String file = baseQuery.split("from")[1].trim().split("\\s+")[0].trim();
		return file;
	}

		/*
		 * extract the order by fields from the query string. Please note that we will
		 * need to extract the field(s) after "order by" clause in the query, if at all
		 * the order by clause exists. For eg: select city,winner,team1,team2 from
		 * data/ipl.csv order by city from the query mentioned above, we need to extract
		 * "city". Please note that we can have more than one order by fields.
		 */
		public List<String> getOrderByFields(String queryString) {
			
			List<String> listStringOrder = new ArrayList<String>();
			
			if(queryString.contains("order by")) {
				 listStringOrder.add(queryString.split("order by")[1].trim());
			}
		    return listStringOrder;
		}
		
		/*
		 * extract the group by fields from the query string. Please note that we will
		 * need to extract the field(s) after "group by" clause in the query, if at all
		 * the group by clause exists. For eg: select city,max(win_by_runs) from
		 * data/ipl.csv group by city from the query mentioned above, we need to extract
		 * "city". Please note that we can have more than one group by fields.
		 */
		public List<String> getGroupByFields(String queryString) {
			
			 List<String> groupByFields = new ArrayList<String>();
			 
			 if(queryString.contains("group by")) {
				 groupByFields.add(queryString.split("group by")[1].trim());
			}
			 return groupByFields;
			/*if(queryString.contains("group by") && !queryString.contains("order by")) {
				groupByFields.add(queryString.split("group by")[1].trim());
				System.out.println("Hello***************************"+groupByFields);
				return groupByFields;
			}
			else if(queryString.contains("group by") && queryString.contains("order by")) {
				groupByFields.add(queryString.split("order by")[0].trim().split("group by")[1].trim());
				return groupByFields;
			}
			
			return null;*/
		    }
		public void groupByOrderBy(String queryString) {
	        if(queryString.contains(" order by ") && queryString.contains("group by")) {
	            String splitStringGroupBy = "";
	                splitStringGroupBy = queryString.split("group by")[1].trim().split("order by")[0].trim();
	                String str[] = splitStringGroupBy.split(",");
	                for(String s:str) {
	                    System.out.println("GroupByis:"+s);
	                }
	                List<String> list = Arrays.asList(str);
	                queryParameter.setGroupByFields(list);
	                
	            String splitStringOrderBy = queryString.split("order by")[1].trim();
	                String str2[] = splitStringOrderBy.split(" ");
	                for(String s:str2) {
	                    System.out.println("OrderByis:"+s);
	                }
	                List list2 = Arrays.asList(str2);
	                
	                queryParameter.setOrderByFields(list2);            
	            
	        }
	        
	    }
		/*
		 * extract the selected fields from the query string. Please note that we will
		 * need to extract the field(s) after "select" clause followed by a space from
		 * the query string. For eg: select city,win_by_runs from data/ipl.csv from the
		 * query mentioned above, we need to extract "city" and "win_by_runs". Please
		 * note that we might have a field containing name "from_date" or "from_hrs".
		 * Hence, consider this while parsing.
		 */
		public List<String> getFields(String queryString){
			 List<String> fields = new ArrayList<String>();
			 
			 String[] splitString = queryString.split("select")[1].trim().split("from")[0].trim().split(",");
			  for(int i=0; i<splitString.length; i++) {
				  fields.add(splitString[i].trim());
			  }
			 return fields;
		}
		
		
		
		
		/*
		 * extract the conditions from the query string(if exists). for each condition,
		 * we need to capture the following: 
		 * 1. Name of field 
		 * 2. condition 
		 * 3. value
		 * 
		 * For eg: select city,winner,team1,team2,player_of_match from data/ipl.csv
		 * where season >= 2008 or toss_decision != bat
		 * 
		 * here, for the first condition, "season>=2008" we need to capture: 
		 * 1. Name of field: season 
		 * 2. condition: >= 
		 * 3. value: 2008
		 * 
		 * the query might contain multiple conditions separated by OR/AND operators.
		 * Please consider this while parsing the conditions.
		 * 
		 */
		public List<Restriction> getRestrictions(String queryString)
	    {
		if(queryString.contains(" where "))
        {
            String query=queryString.split("where ")[1];
            if(query.contains("='"))
            {
                query=query.replace("='", "= ");
                query=query.replace("'", "");
                query=query.replace("=", " =");
                query=query.replace("  ", " ");
            }
            
            if(query.contains(" and ") | query.contains(" or "))
            {
                if(query.contains(" or "))
                {
                    query=query.replace(" or ", " and ");
                }
            }
            String[] conditions=query.split(" and ");
            List<Restriction> restrictionFunctionList=new ArrayList<Restriction>();
            for (int i = 0; i < conditions.length; i++) {
                System.out.println(conditions[i]);
                String[] restrictionQuery=conditions[i].split(" ");
                String propertyName=restrictionQuery[0].trim();
                String propertyValue=restrictionQuery[2].trim();
                String condition=restrictionQuery[1].trim();
                System.out.println(propertyName+" "+propertyValue+" "+condition);
                Restriction r = new Restriction(propertyName,propertyValue,condition);
                restrictionFunctionList.add(r);
            }

            return restrictionFunctionList;
        }
        else
        {
            return null;
        }
		
	    }

		
		/*
		 * extract the logical operators(AND/OR) from the query, if at all it is
		 * present. For eg: select city,winner,team1,team2,player_of_match from
		 * data/ipl.csv where season >= 2008 or toss_decision != bat and city =
		 * bangalore
		 * 
		 * the query mentioned above in the example should return a List of Strings
		 * containing [or,and]
		 */

		public List<String> getLogicalOperators(String queryString) {

			List<String> logicalCondition = new ArrayList<>();
			if (queryString.contains(" where ")) {
				String[] str = queryString.toLowerCase().split(" ");
				int l = 0;
				for (int i = 0; i < str.length; i++) {
					// System.out.println(str[i]);
					if (str[i].equals("and") | str[i].equals("or")) {
						l++;
					}
				}
				String[] logic = new String[l];        
				l = 0;
				for (int i = 0; i < str.length; i++) {
					// System.out.println(str[i]);
					if (str[i].equals("and") | str[i].equals("or")) {
						logic[l] = str[i];
						//System.out.println(logic[l]);
						l++;
					}
				}
				for(String s:logic) {
					logicalCondition.add(s);
				}
				return logicalCondition;
			}
			else {
				return null;
			}
		}
	
			
		
		/*
		 * extract the aggregate functions from the query. The presence of the aggregate
		 * functions can determined if we have either "min" or "max" or "sum" or "count"
		 * or "avg" followed by opening braces"(" after "select" clause in the query
		 * string. in case it is present, then we will have to extract the same. For
		 * each aggregate functions, we need to know the following: 
		 * 1. type of aggregate function(min/max/count/sum/avg) 
		 * 2. field on which the aggregate function is being applied
		 * 
		 * Please note that more than one aggregate function can be present in a query
		 * 
		 * 
		 */
		
	
public List<AggregateFunction> getAggregateFunctions(String queryString){
	
	 if (hasAggregateFunctions(queryString)) {
          queryString = queryString.trim();
          String aggregateFunctions[] = queryString.split("from")[0].split("select")[1].split(",");
          int size = aggregateFunctions.length;
          String aggregate;
          String function;
          String aggregateField;
          List<AggregateFunction> agregateFunctionList = new ArrayList<AggregateFunction>();
          AggregateFunction agregateFunction;
          for (int i = 0; i < size; i++) {
              aggregate = aggregateFunctions[i].trim();
              if (aggregate.contains("(")) {
                  function = aggregate.split("\\(")[0].trim();
                  aggregateField = aggregate.split("\\(")[1].trim().split("\\)")[0];
                  agregateFunction = new AggregateFunction(function,aggregateField);
                  //agregateFunction.setField(aggregateField);
                  //agregateFunction.setFunction(function);
                  agregateFunctionList.add(agregateFunction);
              }
          }
          System.out.println(agregateFunctionList);
          return agregateFunctionList;
      }
      return null;
  }
   public boolean hasAggregateFunctions(String queryString) {
      if (queryString.contains("sum") || queryString.contains("min") || queryString.contains("max")
              || queryString.contains("avg") || queryString.contains("count")) {
         // queryParameter.setQuery_Type("AGGREGATE_QUERY");
          return true;
      }
      return false;
  }
	


}
