import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ClientException, ApiException, IOException, InterruptedException, ClassNotFoundException {
        ApiVk apiVk = new ApiVk();
        apiVk.init();
        GraphUtils.printGraphInSVG(apiVk.getGraph(), "D:\\java_new\\graph\\graph_execute.svg");
        GraphUtils.printGraphInVZ(apiVk.getGraph(), "D:\\java_new\\graph\\graph_execute.vz");
        GraphUtils.saveGraph(apiVk.getGraph(), "D:\\java_new\\graph\\graph_execute.ser");
        GraphUtils.printGraphInCSV(apiVk.getGraph(), "D:\\java_new\\graph\\graph_execute.csv");
        GraphUtils.printStats(apiVk.getGraph(), "D:\\java_new\\graph\\stats_execute.txt");
    }
}