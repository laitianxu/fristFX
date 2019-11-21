import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    public TableColumn haoma;
    public TableColumn cishu;
    public TableView tview;
    public TableColumn qishu;
    public HBox box;
    public TextField cookie;
    public TextField url;


    public void submit(ActionEvent actionEvent) throws IOException {
        String urlText = url.getText();
        String cookieText = cookie.getText();
        File file = new File("user.txt");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(urlText);
        fileWriter.write("\r\n");
        fileWriter.write(cookieText);
        fileWriter.close();
//1，获取10页全部数据，存入csv，去重方法已经在存入中实现了，2，读取csv，讲结果带入showList
//        3，开启定时，没5分钟获取第一页数据，出入csv，重复2操作
        Dispose dispose = new Dispose();
        dispose.url = url.getText();
        dispose.cookie = cookie.getText();
        List<Data> initData = dispose.getInitData(cookie.getText());
        dispose.csvWrite(initData);
        List<PageVo> analyze = dispose.analyze();
        box.setVisible(false);
        showList(analyze);
        task(dispose);
    }

    public void showList(List<PageVo> dataAll){
        tview.setVisible(true);
        ObservableList<PageVo> list = FXCollections.observableArrayList();
        qishu.setCellValueFactory(new PropertyValueFactory("no"));//映射
        haoma.setCellValueFactory(new PropertyValueFactory("data"));
        cishu.setCellValueFactory(new PropertyValueFactory("time"));
        list.clear();
        list.addAll(dataAll);    //list添加值对象
        tview.setItems(list); //tableview添加list
    }


    public void task(Dispose dispose) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                List<Data> data = dispose.getInitData(dispose.cookie);
                try {
                    dispose.csvWrite(data);
                    List<PageVo> analyze = dispose.analyze();
                    showList(analyze);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0,24 * 60 * 60 * 1000);
        Share.timer = timer;
    }

    public boolean login(String username, String password, String dns) {

        return username.equals("yjy");
    }

    public void Init() throws IOException {
        File file = new File("user.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader buff = new BufferedReader(fileReader);
        String url = buff.readLine();
        String cookie = buff.readLine();
        this.cookie.setText(cookie);
        this.url.setText(url);
        buff.close();
    }
}
