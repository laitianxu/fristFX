import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Dispose {
    String dns = "http://www.modou28.com";
    String url;
    String cookie;

    @Test
    public void login(){
        String url = this.dns + "/User/Login?act=login";
        String data = "logintype=1&" + "tbUserAccount=" + "18617175011" + "&tbUserPwd=" + "4325224574pcy";
        HttpApi.postFrom(url,data);

    }


    public List<Data> getInitData(String cookie){
        String success = null;
        JSONObject s = null;
        List<Data> dataList = new ArrayList<>();

        for (int page = 1; page <= 10; page++) {
            String url = this.url + "?gamename=ftww&page=" + page;
            try {
                s = HttpApi.get(url,cookie);
                success = s.getString("result");
            }catch (Exception e){
                e.printStackTrace();
            }
            if (success.equals("success")){
                JSONArray openhistory = s.getJSONArray("openhistory");
                for (int i = 0; i < openhistory.size(); i++) {
                    JSONObject o = openhistory.getJSONObject(i);
                    String no = o.getString("no");
                    String data = o.getString("origindata");
                    Integer number = Integer.valueOf(no.substring(8));
                    Data data1 = new Data(no, data, number);
                    dataList.add(data1);
                }
            }
        }
        return dataList;
    }


    public List<Data> getFristData(String cookie){
        String success = null;
        JSONObject s = null;
        List<Data> dataList = new ArrayList<>();

        String url = this.url + "?gamename=ftww&page=" + 1;
        try {
            s = HttpApi.get(url,cookie);
            success = s.getString("result");
        }catch (Exception e){
            e.printStackTrace();
        }
        if (success.equals("success")){
            JSONArray openhistory = s.getJSONArray("openhistory");
            for (int i = 0; i < openhistory.size(); i++) {
                JSONObject o = openhistory.getJSONObject(i);
                String no = o.getString("no");
                String data = o.getString("origindata");
                Integer number = Integer.valueOf(no.substring(8));
                Data data1 = new Data(no, data, number);
                dataList.add(data1);
            }
        }

        return dataList;
    }


    public void csvWrite(List<Data> dataList) throws IOException {
        List<Data> dataRead = csvRead();
        List<Data> dataZ = dataList.stream().filter(data -> !StringUtils.isBlank(data.getData()))
                .collect(Collectors.toList());
        dataZ.addAll(dataRead);
        List<Data> collect = dataZ.stream().distinct().collect(Collectors.toList());
        File file = new File("data.CSV");
        Appendable printWriter = new PrintWriter(file,"UTF8");//指定GBK,解决Microsoft不兼容
        CSVPrinter csvPrinter = CSVFormat.DEFAULT.withHeader("no", "data", "number").print(printWriter);
        for (int i = 0; i < collect.size(); i++) {
            Data data = collect.get(i);
            csvPrinter.printRecord(data.getNo(),data.getData(),data.getNumber());
            csvPrinter.flush();
        }
        csvPrinter.close();

    }

    public List<Data> csvRead() throws IOException {
        List<Data> list = new ArrayList<>();
        File file = new File("data.CSV");
        Reader reader = new FileReader(file);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("no", "data", "number").withSkipHeaderRecord().parse(reader);
        for (CSVRecord csvRecord : records) {
            Data data = new Data(csvRecord.get(0), csvRecord.get(1), Integer.valueOf(csvRecord.get(2)));
            list.add(data);
        }
        reader.close();
        return list;
    }


    public List<PageVo> analyze() throws IOException {
        List<Data> dataList = csvRead().stream()
                .filter(data -> !data.getNo().contains(getToDay()))
                .collect(Collectors.toList());

        Map<Integer, List<Data>> map = dataList.stream().collect(Collectors.groupingBy(Data::getNumber));
        List<Data> yesterdayData = dataList.stream()
                .filter(data -> data.getNo().contains(getyesterday()))
                .collect(Collectors.toList());
        List<PageVo> collect = yesterdayData.stream().map(data -> {
            PageVo pageVo = new PageVo();
            pageVo.setNo(data.getNo());
            pageVo.setNumber(data.getNumber());
            pageVo.setData(data.getData());
            List<Data> list = map.get(data.getNumber());
            int[] ints = judgeData(list);
            pageVo.setTime(Arrays.toString(ints));
            return pageVo;
        }).collect(Collectors.toList());

        return collect;
    }

    public int[] judgeData(List<Data> list){
        List<Data> collect = list.stream().sorted(Comparator.comparing(Data::getNo).reversed()).collect(Collectors.toList());
        Integer[] integers = new Integer[]{0,0,0,0,0,0,0,0,0,0};
        Data frist = collect.get(0);
        JSONArray fristData = JSON.parseArray(frist.getData());
        for (int i = 1; i < collect.size(); i++) {
            JSONArray data =  JSON.parseArray(collect.get(i).getData());
            for (int j = 0; j < data.size(); j++) {
                int fristValue = fristData.getIntValue(j);
                int value = data.getIntValue(j);
                if (fristValue == value) {
                    if (integers[j] == null) continue;
                    integers[j]++;
                }else {
                    integers[j] = null;
                }
            }
        }
        return Arrays.stream(integers).mapToInt(value -> {
            if (value!=null) return value;
            return 0;
        }).toArray();
    }


//    数据模板为 ----   "[5,4,3,10,1,7,9,2,8,6]"
    public boolean judge(String s1,String s2){
        return false;
    }


    public String getToDay() {
        LocalDate now = LocalDate.now(ZoneOffset.ofHours(8));
        DateTimeFormatter yyyymmdd = DateTimeFormatter.ofPattern("yyyyMMdd");
        return now.format(yyyymmdd);
    }

    public String getyesterday() {
        LocalDate now = LocalDate.now(ZoneOffset.ofHours(8)).plusDays(-1);
        DateTimeFormatter yyyymmdd = DateTimeFormatter.ofPattern("yyyyMMdd");
        return now.format(yyyymmdd);
    }

    @Test
    public void test() throws IOException {
        System.out.println(Arrays.toString(new int[] {1,2,3,4,5}));
    }
}
