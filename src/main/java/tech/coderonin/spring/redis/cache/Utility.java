package tech.coderonin.spring.redis.cache;

public class Utility {
    public static boolean isEmpty(String str){
        return (str==null)?true:"".equals(str.trim());
    }

    public static boolean and( boolean ...predicates){
        boolean result = true;
        for( boolean predicate : predicates){
            result &= predicate;
        }
        return result;
    }

    public static boolean or( boolean ...predicates){
        boolean result = false;
        for( boolean predicate : predicates ){
            result |= predicate;
        }
        return result;
    }

    public static <T> T defaultTo( T value, T defaultValue ){
        return coalesce( value, defaultValue );
    }

    public static <T> T coalesce( T ...values){
        for( T value : values ){
            if( value != null ){
                return value;
            }
        }
        return null;
    }

    public static interface Predicate<T>{
        boolean test(T obj);
    }
}
