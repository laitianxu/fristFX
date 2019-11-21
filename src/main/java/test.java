import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class test {
    @Test
    public void test1() throws IOException {
        Dispose dispose = new Dispose();
        dispose.url = "http://www.modou28.com/Ajax/Game/Index";
        List<Data> initData = dispose.getInitData("td_cookie=169210459; sflag=1; PHPSESSID=nf6iv6httaiem4n27s06ip5m96; sjb=-1; visit_flag=1; __51cke__=; sytcTime=1574215659; password=9002c9ec797f9a0d318c5cd38739eb3b; usersid=54545; safstr=57ccc; cSound=1; zdsx=1; noticTime=2022-09-09%2011%3A00%3A00; __tins__20183281=%7B%22sid%22%3A%201574218407898%2C%20%22vd%22%3A%202%2C%20%22expires%22%3A%201574221176509%7D; __51laig__=6; neargame=ftww");
        System.out.println(initData);
    }


    @Test
    public void test2(){
        List<Integer> collect = Stream.of(6, 8, 95, 12, 4, 0).collect(Collectors.toList());
        Data data1 = new Data("2019","test1",1);
        Data data2 = new Data("2011","test2",3);
        Data data3 = new Data("2056","test3",4);
        Data data4 = new Data("2009","test4",2);

        List<Data> list = new ArrayList<>();
        list.add(data1);
        list.add(data2);
        list.add(data3);
        list.add(data4);

        list.stream().sorted(Comparator.comparing(Data::getNo).reversed()).forEach(System.out::println);



    }

    @Test
    public void test3() {
        URL resource = this.getClass().getResource("user.txt");


    }
}
