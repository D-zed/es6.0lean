package tonatyw.es.util;

import org.elasticsearch.index.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/***
 * 
 * 
 * @author dzd
 */
public class ESQueryBuilders implements ESCriterion {

    private List<QueryBuilder> list = new ArrayList<QueryBuilder>();

    /** 
      * 功能描述：Term 查询 
      * @param field 字段名 
      * @param value 值 
      */
    public ESQueryBuilders term(String field, Object value) {
        QueryBuilder queryBuilder = new ESSimpleExpression(field, value, Operator.TERM).toBuilder();
        list.add(queryBuilder);
        return this;
    }

    /** 
      * 功能描述：Terms 查询 
      * @param field 字段名 
      * @param values 集合值 
      */
    public ESQueryBuilders terms(String field, Collection<Object> values) {
        list.add(new ESSimpleExpression(field, values).toBuilder());
        return this;
    }

    /** 
      * 功能描述：fuzzy 查询 
      * @param field 字段名 
      * @param value 值 
      */
    public ESQueryBuilders fuzzy(String field, Object value) {
        list.add(new ESSimpleExpression(field, value, Operator.FUZZY).toBuilder());
        return this;
    }

    /** 
      * 功能描述：Range 查询 
      * @param from 起始值 
      * @param to 末尾值 
      */
    public ESQueryBuilders range(String field, Object from, Object to) {
        list.add(new ESSimpleExpression(field, from, to).toBuilder());
        return this;
    }

    /** 
      * 功能描述：通配符 查询 
      * @param queryString 查询语句 
      */
    public ESQueryBuilders queryString(String queryString) {
        list.add(new ESSimpleExpression(queryString, Operator.QUERY_STRING).toBuilder());
        return this;
    }

    public ESQueryBuilders matchPhrase(String fieldName, Object value) {
        list.add(new ESSimpleExpression(fieldName, value, Operator.MATCH_PHRASE).toBuilder());
        return this;
    }

    public ESQueryBuilders match(String fieldName, Object value) {
        list.add(new ESSimpleExpression(fieldName, value, Operator.MATCH).toBuilder());
        return this;
    }

    public List<QueryBuilder> listBuilders() {
        return list;
    }
}