package per.posse.tool.utils.Json;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class JsonUtil {
//	private static ObjectMapper om = new ObjectMapper();
//
//	static{
//		om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
//	}
//
//	public static void setFormatYYY(){
//		om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
//	}
//	public static void setFormatYYYSS(){
//		om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//	}
//
//
//	public static ObjectMapper getOm() {
//		return om;
//	}
//
//	public static String toJson(Object ob) {
//		try {
//			return om.writeValueAsString(ob);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
//
//	public static List<Map> toListMap(String ob) {
//		try {
//			return om.readValue(ob, List.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public static <T> List<T> toListJavaBean(Class type,String ob) {
//		try {
//			return om.readValue(ob,getCollectionType(ArrayList.class, type));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public static Map toMap(String ob) {
//		try {
//			return om.readValue(ob, Map.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public static LinkedHashMap toLinkedHashMap(String ob) {
//		try {
//			return om.readValue(ob, LinkedHashMap.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public static void toMap(Object ob, Writer w) {
//		try {
//			om.writeValue(w, ob);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void toWriter(Object ob, HttpServletResponse resp) {
//		try {
//			resp.setContentType("application/json;charset=UTF-8");
//			resp.setCharacterEncoding("UTF-8");
//			om.writeValue(resp.getWriter(), ob);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static JavaType getCollectionType(Class<?> collectionClass,Class<?>... elementClasses) {
//		return om.getTypeFactory().constructParametricType(collectionClass,elementClasses);
//	}
//
//	public static void main(String[] args) {
//
//		List<Map> map = JsonUtil
//				.toListMap("[{'phone' : '','businessType' : 4,'registrationNo' : '',	'companyName' : '感觉可不能入','registeredCapital' : null,'business' : '发把附表二额二人','addresss' : '','id' : 3,	'businessTerm' : '','registeredAddress' : '',	'createDate' : '2013-08-20','legalRepresentative' : '分设备股份巴塞罗那','registrationAgency' : null}]"
//						.toString().replaceAll("'", "\""));
//		System.out.println(map);
//	}
}