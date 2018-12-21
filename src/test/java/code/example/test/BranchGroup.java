package code.example.test;

import org.apache.poi.ss.formula.functions.T;

import java.util.*;

public class BranchGroup {
    /**
     * map key 排序
     * @param map
     * @return
     */
    /*public static Map<Byte, List<T>> sortMapByKey(Map<Byte, List<T>> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<Byte, List<T>> sortMap = new TreeMap<Byte, List<RegionBeanTree>>(
                new Comparator<T>() {
                    @Override
                    public int compare(T o1, T o2) {

                        return ((Byte)o1) - (Byte)o2;
                    }
                });

        sortMap.putAll(map);

        return sortMap;
    }*/
    /**
     * list 集合分组
     *
     * @param list    待分组集合
     * @param groupBy 分组Key算法
     * @param <K>     分组Key类型
     * @param <V>     行数据类型
     * @return 分组后的Map集合
     */
    public static <K, V> Map<K, List<V>> groupBy(List<V> list, GroupBy<K, V> groupBy) {
        return groupBy((Collection<V>) list, groupBy);
    }
    /**
     * list 集合分组
     *
     * @param list    待分组集合
     * @param groupBy 分组Key算法
     * @param <K>     分组Key类型
     * @param <V>     行数据类型
     * @return 分组后的Map集合
     */
    public static <K, V> Map<K, List<V>> groupBy(Collection<V> list, GroupBy<K, V> groupBy) {
        Map<K, List<V>> resultMap = new LinkedHashMap<K, List<V>>();

        for (V e : list) {

            K k = groupBy.groupBy(e);
            if (resultMap.containsKey(k)) {
                resultMap.get(k).add(e);
            } else {
                List<V> tmp = new LinkedList<V>();
                tmp.add(e);
                resultMap.put(k, tmp);
            }
        }
        return resultMap;
    }

    /**
     * List分组
     *
     * @param <K> 返回分组Key
     * @param <V> 分组行
     */
    public interface GroupBy<K, V> {
        K groupBy(V row);
    }
}
