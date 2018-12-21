package code.example.test;

import com.alibaba.fastjson.JSONObject;
import com.libertad.demo.common.redis.RedisUtils;
import com.libertad.demo.common.utils.FSearchTool;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RedisTest extends Tester{
	
	@Resource
	private RedisUtils redisService;

	/*@Test
	public void ma(){
		redisService.set("name_2", "ccccc");
		redisService.expire("name_2", 60*60*2);
	}*/
	//@Test
	public void aaa() throws Exception{
		List<MDictionary> list = redisService.getList("DictionaryInit", MDictionary.class);
		//System.err.println(JSONObject.toJSONString(list));
		//分组(必须使用实体)
		/*Map<String, List<MDictionary>> collect = list.stream().collect(Collectors.groupingBy(MDictionary::getDkey));
		System.out.println(JSONObject.toJSONString(collect));*/
		//过滤出符合条件的数据
		List<MDictionary> filterList = list.stream().filter(a -> a.getDkey().equals("46") && a.getGroupid() == 51).collect(Collectors.toList());
		System.out.println(JSONObject.toJSONString(filterList));
		//去除重复
		List<MDictionary> collect = list.stream().filter(
				distinctByKey(o -> o.getDkey())
		).collect(Collectors.toList());
		System.err.println(JSONObject.toJSONString(collect));
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

}
