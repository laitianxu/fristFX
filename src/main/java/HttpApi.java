import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;


public class HttpApi {
    String uri = "http://127.0.0.1:7080/simpleweb";

    /**
     * Get方法
     */
    @Test
    public static JSONObject get(String url,String cookie) {
        try {
            URL urll = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urll.openConnection();

            connection.setDoOutput(true); // 设置该连接是可以输出的
            connection.setRequestMethod("GET"); // 设置请求方式
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            if (cookie != null) connection.setRequestProperty("Cookie",cookie);
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line = null;
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) { // 读取数据
                result.append(line + "\n");
            }
            connection.disconnect();

            return JSON.parseObject(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
            return null;
    }

    /**
     * Post方法发送form表单
     */
    @Test
    public static void postFrom(String u,String data) {
        try {
            URL url = new URL(u);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true); // 设置可输入
            connection.setDoOutput(true); // 设置该连接是可以输出的
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36");
            connection.setRequestProperty("Referer", "http://www.modou28.com/User/Login?act=login");
            connection.setRequestProperty("Cookie", "td_cookie=169260528; td_cookie=169210459; sflag=1; PHPSESSID=nf6iv6httaiem4n27s06ip5m96; sjb=-1; visit_flag=1; __51cke__=; sytcTime=1574215659; __tins__20183281=%7B%22sid%22%3A%201574215658688%2C%20%22vd%22%3A%203%2C%20%22expires%22%3A%201574217508458%7D; __51laig__=3");
            PrintWriter pw = new PrintWriter(new BufferedOutputStream(connection.getOutputStream()));
            pw.write(data);
            pw.flush();
            pw.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line = null;
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) { // 读取数据
                result.append(line + "\n");
            }
            String cookie = connection.getHeaderField("Set-Cookie");
            connection.disconnect();
            System.out.println(result.toString());
            System.out.println(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Post方法发送json数据
     */
//    @Test
//    public void test3() {
//        try {
//            URL url = new URL(uri + "/test2");
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//            connection.setDoInput(true); // 设置可输入
//            connection.setDoOutput(true); // 设置该连接是可以输出的
//            connection.setRequestMethod("POST"); // 设置请求方式
//            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, Object> data = new HashMap<String, Object>();
//            data.put("code", "001");
//            data.put("name", "测试");
//            PrintWriter pw = new PrintWriter(new BufferedOutputStream(connection.getOutputStream()));
//            pw.write(objectMapper.writeValueAsString(data));
//            pw.flush();
//            pw.close();
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
//            String line = null;
//            StringBuilder result = new StringBuilder();
//            while ((line = br.readLine()) != null) { // 读取数据
//                result.append(line + "\n");
//            }
//            connection.disconnect();
//
//            System.out.println(result.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}